package net.kaupenjoe.tutorialmod.entity.custom.VillagerAi;

import net.kaupenjoe.tutorialmod.entity.ModMemoryModuleTypes;
import net.kaupenjoe.tutorialmod.entity.ModPoiTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.npc.Villager;

public class ReactToBell {

    // Wie lange nach Glocke reagieren Villager (in Ticks: 20 Ticks = 1 Sekunde)
    private static final int BELL_REACTION_TIME = 200; // 10 Sekunden

    public static BehaviorControl<Villager> create() {
        return BehaviorBuilder.create(
                instance -> instance.group(
                        instance.present(ModMemoryModuleTypes.HEARD_BELL_TIME.get()),  // Muss Glocke gehört haben
                        instance.absent(ModMemoryModuleTypes.POTENTIAL_TRADING_SITE.get()), // Noch keinen Trading-Block gefunden
                        instance.absent(ModMemoryModuleTypes.TRADING_SITE.get())       // Noch keinen Trading-Block beansprucht
                ).apply(instance, (bellTimeMemory, potentialSiteMemory, tradingSiteMemory) ->
                        (level, villager, gameTime) -> {
                            // Prüfen ob Glocke kürzlich gehört wurde
                            long bellTime = instance.get(bellTimeMemory);
                            if (gameTime - bellTime > BELL_REACTION_TIME) {
                                // Zu lange her - Memory löschen
                                bellTimeMemory.erase();
                                return false;
                            }

                            // Nur erwachsene Villager ohne festen Beruf
                            if (villager.isBaby() || villager.getVillagerData().getProfession().name() != "none") {
                                return false;
                            }

                            // Nächsten freien Trading-Block suchen
                            ServerLevel serverLevel = (ServerLevel) level;
                            PoiManager poiManager = serverLevel.getPoiManager();
                            BlockPos villagerPos = villager.blockPosition();

                            return poiManager.findClosest(
                                    poiType -> poiType.is(ModPoiTypes.TRADING_POI.get()), // Nur Trading POIs
                                    villagerPos,
                                    48, // Suchradius: 48 Blöcke
                                    PoiManager.Occupancy.HAS_SPACE // Nur freie Plätze
                            ).map(blockPos -> {
                                // Trading-Block gefunden! In POTENTIAL_TRADING_SITE speichern
                                GlobalPos globalPos = GlobalPos.of(level.dimension(), blockPos);
                                potentialSiteMemory.set(globalPos);
                                return true;
                            }).orElse(false);
                        }
                )
        );
    }
}