package net.kaupenjoe.tutorialmod.entity.custom.VillagerAi;

import java.util.List;
import java.util.Optional;

import net.kaupenjoe.tutorialmod.TutorialMod; // WICHTIG: Import hinzufügen!

import net.kaupenjoe.tutorialmod.entity.ModMemoryModuleTypes;
import net.kaupenjoe.tutorialmod.entity.ModPoiTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.pathfinder.Path;

public class YieldTradingSite {

    public static BehaviorControl<Villager> create(float pSpeedModifier) {
        return BehaviorBuilder.create(
                p_258916_ -> p_258916_.group(
                                p_258916_.present(ModMemoryModuleTypes.POTENTIAL_TRADING_SITE.get()), // Geändert: Sauberer Import
                                p_258916_.absent(ModMemoryModuleTypes.TRADING_SITE.get()),            // Geändert: Sauberer Import
                                p_258916_.present(MemoryModuleType.NEAREST_LIVING_ENTITIES),
                                p_258916_.registered(MemoryModuleType.WALK_TARGET),
                                p_258916_.registered(MemoryModuleType.LOOK_TARGET)
                        )
                        .apply(
                                p_258916_,
                                (p_258901_, p_258902_, p_258903_, p_258904_, p_258905_) -> (p_258912_, p_258913_, p_258914_) -> {
                                    if (p_258913_.isBaby()) {
                                        return false;
                                    } else if (p_258913_.getVillagerData().getProfession() != VillagerProfession.NONE) {
                                        return false;
                                    } else {
                                        BlockPos blockpos = p_258916_.<GlobalPos>get(p_258901_).pos();
                                        Optional<Holder<PoiType>> optional = p_258912_.getPoiManager().getType(blockpos);
                                        if (optional.isEmpty()) {
                                            return true;
                                        } else {
                                            p_258916_.<List<LivingEntity>>get(p_258903_)
                                                    .stream()
                                                    .filter(p_258898_ -> p_258898_ instanceof Villager && p_258898_ != p_258913_)
                                                    .map(p_258896_ -> (Villager)p_258896_)
                                                    .filter(LivingEntity::isAlive)
                                                    .filter(p_258919_ -> nearbyWantsTradingsite(optional.get(), p_258919_, blockpos))
                                                    .findFirst()
                                                    .ifPresent(p_326912_ -> {
                                                        p_258904_.erase();
                                                        p_258905_.erase();
                                                        p_258901_.erase();
                                                        if (p_326912_.getBrain().getMemory(ModMemoryModuleTypes.TRADING_SITE.get()).isEmpty()) { // Geändert: Sauberer Import
                                                            BehaviorUtils.setWalkAndLookTargetMemories(p_326912_, blockpos, pSpeedModifier, 1);
                                                            p_326912_.getBrain()
                                                                    .setMemory(ModMemoryModuleTypes.POTENTIAL_TRADING_SITE.get(), GlobalPos.of(p_258912_.dimension(), blockpos)); // Geändert: Sauberer Import
                                                            DebugPackets.sendPoiTicketCountPacket(p_258912_, blockpos);
                                                        }
                                                    });
                                            return true;
                                        }
                                    }
                                }
                        )
        );
    }

    private static boolean nearbyWantsTradingsite(Holder<PoiType> pPoi, Villager pVillager, BlockPos pPos) {
        boolean flag = pVillager.getBrain().getMemory(ModMemoryModuleTypes.POTENTIAL_TRADING_SITE.get()).isPresent(); // Geändert: Sauberer Import
        if (flag) {
            return false;
        } else {
            Optional<GlobalPos> optional = pVillager.getBrain().getMemory(ModMemoryModuleTypes.TRADING_SITE.get()); // Geändert: Sauberer Import

            // Prüfen ob dieser POI ein Trading-POI ist
            if (pPoi.is(ModPoiTypes.TRADING_POI.get())) {
                return optional.isEmpty() ? canReachPos(pVillager, pPos, pPoi.value()) : optional.get().pos().equals(pPos);
            } else {
                return false;
            }
        }
    }

    private static boolean canReachPos(PathfinderMob pMob, BlockPos pPos, PoiType pPoi) {
        Path path = pMob.getNavigation().createPath(pPos, pPoi.validRange());
        return path != null && path.canReach();
    }
}