package net.kaupenjoe.tutorialmod.entity;

import com.google.common.collect.ImmutableSet;
import net.kaupenjoe.tutorialmod.TutorialMod;
import net.kaupenjoe.tutorialmod.block.ModBlocks;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModPoiTypes {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, TutorialMod.MOD_ID);

    // Trading POI - hier können Villager temporär handeln
    public static final RegistryObject<PoiType> TRADING_POI = POI_TYPES.register("trading_poi",
            () -> new PoiType(getAllBlockStates(ModBlocks.TRADING_BLOCK.get()), 1, 1));

    // Hilfsmethode: Alle BlockStates eines Blocks sammeln
    private static Set<BlockState> getAllBlockStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }
}
