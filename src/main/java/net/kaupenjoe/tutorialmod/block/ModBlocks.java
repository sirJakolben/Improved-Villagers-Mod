package net.kaupenjoe.tutorialmod.block;

import net.kaupenjoe.tutorialmod.TutorialMod;
import net.kaupenjoe.tutorialmod.block.custom.*;
import net.kaupenjoe.tutorialmod.item.ModItems;
import net.kaupenjoe.tutorialmod.item.custom.GongBlockItem;
import net.kaupenjoe.tutorialmod.sound.ModSounds;
import net.kaupenjoe.tutorialmod.worldgen.tree.ModTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TutorialMod.MOD_ID);

    public static final RegistryObject<Block> ALEXANDRITE_BLOCK = registerBlock("alexandrite_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)),true);
    public static final RegistryObject<Block> RAW_ALEXANDRITE_BLOCK = registerBlock("raw_alexandrite_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops()),true);

    public static final RegistryObject<Block> ALEXANDRITE_ORE = registerBlock("alexandrite_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4), BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops()),true);
    public static final RegistryObject<Block> ALEXANDRITE_DEEPSLATE_ORE = registerBlock("alexandrite_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 6), BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)),true);
    public static final RegistryObject<Block> ALEXANDRITE_END_ORE = registerBlock("alexandrite_end_ore",
            () -> new DropExperienceBlock(UniformInt.of(5, 9),
                    BlockBehaviour.Properties.of().strength(7f).requiresCorrectToolForDrops()),true);
    public static final RegistryObject<Block> ALEXANDRITE_NETHER_ORE = registerBlock("alexandrite_nether_ore",
            () -> new DropExperienceBlock(UniformInt.of(1, 5),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()),true);



    public static final RegistryObject<Block> MAGIC_BLOCK = registerBlock("magic_block",
            () -> new MagicBlock(BlockBehaviour.Properties.of().strength(2f).noLootTable().sound(ModSounds.MAGIC_BLOCK_SOUNDS)),true);

    public static final RegistryObject<StairBlock> ALEXANDRITE_STAIRS = registerBlock("alexandrite_stairs",
            () -> new StairBlock(ModBlocks.ALEXANDRITE_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()),true);
    public static final RegistryObject<SlabBlock> ALEXANDRITE_SLAB = registerBlock("alexandrite_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()),true);

    public static final RegistryObject<PressurePlateBlock> ALEXANDRITE_PRESSURE_PLATE = registerBlock("alexandrite_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()),true);
    public static final RegistryObject<ButtonBlock> ALEXANDRITE_BUTTON = registerBlock("alexandrite_button",
            () -> new ButtonBlock(BlockSetType.IRON,1, BlockBehaviour.Properties.of().strength(3f)
                    .requiresCorrectToolForDrops().noCollission()),true);

    public static final RegistryObject<FenceBlock> ALEXANDRITE_FENCE = registerBlock("alexandrite_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()),true);
    public static final RegistryObject<FenceGateBlock> ALEXANDRITE_FENCE_GATE = registerBlock("alexandrite_fence_gate",
            () -> new FenceGateBlock(WoodType.ACACIA, BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()),true);
    public static final RegistryObject<WallBlock> ALEXANDRITE_WALL = registerBlock("alexandrite_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()),true);

    public static final RegistryObject<DoorBlock> ALEXANDRITE_DOOR = registerBlock("alexandrite_door",
            () -> new DoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().noOcclusion()),true);
    public static final RegistryObject<TrapDoorBlock> ALEXANDRITE_TRAPDOOR = registerBlock("alexandrite_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().noOcclusion()),true);

    public static final RegistryObject<Block> ALEXANDRITE_LAMP = registerBlock("alexandrite_lamp",
            () -> new AlexandriteLampBlock(BlockBehaviour.Properties.of().strength(3f)
                    .lightLevel(state -> state.getValue(AlexandriteLampBlock.CLICKED) ? 15 : 0)),true);

    public static final RegistryObject<Block> KOHLRABI_CROP = BLOCKS.register("kohlrabi_crop",
            () -> new KohlrabiCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final RegistryObject<Block> HONEY_BERRY_BUSH = BLOCKS.register("honey_berry_bush",
            () -> new HoneyBerryBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));

    public static final RegistryObject<RotatedPillarBlock> WALNUT_LOG = registerBlock("walnut_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)),true);
    public static final RegistryObject<RotatedPillarBlock> WALNUT_WOOD = registerBlock("walnut_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)),true);
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_WALNUT_LOG = registerBlock("stripped_walnut_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)),true);
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_WALNUT_WOOD = registerBlock("stripped_walnut_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)),true);

    public static final RegistryObject<Block> WALNUT_PLANKS = registerBlock("walnut_planks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            },true);

    public static final RegistryObject<Block> WALNUT_LEAVES = registerBlock("walnut_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            },true);

    public static final RegistryObject<Block> WALNUT_SAPLING = registerBlock("walnut_sapling",
            () -> new ModSaplingBlock(ModTreeGrowers.WALNUT, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING), () -> Blocks.END_STONE),true);

    public static final RegistryObject<Block> CHAIR = registerBlock("chair",
            () -> new TradingBlock(BlockBehaviour.Properties.of().noOcclusion()),true);

    public static final RegistryObject<Block> TRADING_BLOCK = registerBlock("trading_block",
            () -> new TradingBlock(BlockBehaviour.Properties
                    .of()
                    .strength(2.0f)
                    .sound(SoundType.WOOD)
                    .noOcclusion()),true);
    public static final RegistryObject<Block> GONG_WALL = registerBlock("gong_wall",
            () -> new GongWallBlock(BlockBehaviour.Properties.of()
                    .strength(2.0f)
                    .sound(SoundType.COPPER)
                    .noOcclusion()), false);
    public static final RegistryObject<Block> GONG_CEILING = registerBlock("gong_ceiling",
            () -> new GongCeilingBlock(BlockBehaviour.Properties.of()
                    .strength(2.0f)
                    .sound(SoundType.COPPER)
                    .noOcclusion()), false);

    public static final RegistryObject<Item> GONG = ModItems.ITEMS.register("gong",
            () -> new GongBlockItem(GONG_CEILING.get(), GONG_WALL.get(), new Item.Properties()));



    public static final RegistryObject<Block> PEDESTAL = registerBlock("pedestal",
            () -> new PedestalBlock(BlockBehaviour.Properties.of().noOcclusion()),true);

    public static final RegistryObject<Block> GROWTH_CHAMBER = registerBlock("growth_chamber",
            () -> new GrowthChamberBlock(BlockBehaviour.Properties.of()),true);

    private static <T extends Block> RegistryObject<T> registerBlock(
            String name, Supplier<T> sup, boolean withItem) {
        RegistryObject<T> ro = BLOCKS.register(name, sup);
        if (withItem)
            ModItems.ITEMS.register(name, () -> new BlockItem(ro.get(), new Item.Properties()));
        return ro;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
