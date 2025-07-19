package net.kaupenjoe.tutorialmod.entity.custom.VillagerAi;

import net.kaupenjoe.tutorialmod.entity.ModMemoryModuleTypes;
import net.kaupenjoe.tutorialmod.entity.ModPoiTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.npc.Villager;

public class AcquireTradingSite {

    public static BehaviorControl<Villager> create() {
        return BehaviorBuilder.create(
                instance -> instance.group(
                        instance.present(ModMemoryModuleTypes.POTENTIAL_TRADING_SITE.get()), // Hat einen potentiellen Block
                        instance.absent(ModMemoryModuleTypes.TRADING_SITE.get()),            // Hat noch keinen beansprucht
                        instance.absent(MemoryModuleType.WALK_TARGET)                        // Läuft gerade nicht woanders hin
                ).apply(instance, (potentialSite, tradingSite, walkTarget) ->
                        (level, villager, gameTime) -> {
                            GlobalPos globalPos = instance.get(potentialSite);
                            BlockPos blockPos = globalPos.pos();

                            // Prüfen ob Villager nah genug am Block ist (3 Blöcke)
                            if (villager.blockPosition().distManhattan(blockPos) <= 3) {
                                ServerLevel serverLevel = (ServerLevel) level;
                                PoiManager poiManager = serverLevel.getPoiManager();

                                // Prüfen ob Block noch frei ist
                                if (poiManager.getType(blockPos).map(poi -> poi.is(ModPoiTypes.TRADING_POI.get())).orElse(false)) {
                                    // Block beanspruchen!
                                    if (poiManager.take(villager, ModPoiTypes.TRADING_POI.get(), blockPos, 1)) {
                                        // Erfolgreich beansprucht
                                        tradingSite.set(globalPos);
                                        potentialSite.erase();
                                        walkTarget.erase();

                                        // Debug-Output
                                        System.out.println("Villager " + villager.getName().getString() +
                                                " hat Trading-Block bei " + blockPos + " beansprucht!");
                                        return true;
                                    }
                                }

                                // Block nicht mehr verfügbar - aufgeben
                                potentialSite.erase();
                                return false;
                            }

                            return false; // Noch nicht nah genug
                        }
                )
        );
    }
}