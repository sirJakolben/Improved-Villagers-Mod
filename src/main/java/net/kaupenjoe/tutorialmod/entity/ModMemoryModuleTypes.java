package net.kaupenjoe.tutorialmod.entity;

import net.kaupenjoe.tutorialmod.TutorialMod;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class ModMemoryModuleTypes {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES =
            DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, TutorialMod.MOD_ID);

    // TRADING_SITE: Der aktuell beanspruchte Trading-Block
    // Wenn ein Villager das hat, "besitzt" er einen Trading-Block
    public static final RegistryObject<MemoryModuleType<GlobalPos>> TRADING_SITE =
            MEMORY_MODULE_TYPES.register("trading_site", () -> new MemoryModuleType<>(Optional.empty()));

    // POTENTIAL_TRADING_SITE: Ein Trading-Block, den der Villager gefunden hat
    // Zwischenspeicher, bevor er ihn beansprucht
    public static final RegistryObject<MemoryModuleType<GlobalPos>> POTENTIAL_TRADING_SITE =
            MEMORY_MODULE_TYPES.register("potential_trading_site", () -> new MemoryModuleType<>(Optional.empty()));

    // HEARD_BELL_TIME: Wann hat der Villager zuletzt eine Glocke gehört
    // Damit reagieren sie nur kurz nach dem Glockengeräusch
    public static final RegistryObject<MemoryModuleType<Long>> HEARD_BELL_TIME =
            MEMORY_MODULE_TYPES.register("heard_bell_time", () -> new MemoryModuleType<>(Optional.empty()));
}