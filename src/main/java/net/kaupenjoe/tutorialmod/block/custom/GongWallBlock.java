package net.kaupenjoe.tutorialmod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.shapes.CollisionContext;

import java.util.Map;

public class GongWallBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<GongWallBlock> CODEC = simpleCodec(GongWallBlock::new);
    private static final VoxelShape PLANK_NORTHSOUTH = Block.box(0.0, 14.0, 6.0, 16.0, 16.0, 10.0);
    private static final VoxelShape PLANK_EASTWEST = Block.box(6.0, 14.0, 0.0, 10.0, 16.0, 16.0);
    private static final VoxelShape SHAPE_NORTHSOUTH = Shapes.or(PLANK_NORTHSOUTH, Block.box(2.0, 0.0, 7.0, 14.0, 12.0, 9.0));
    private static final VoxelShape SHAPE_EASTWEST = Shapes.or(PLANK_EASTWEST, Block.box(7.0, 0.0, 2.0, 9.0, 12.0, 14.0));


    public GongWallBlock(Properties pProperties) {
        super(pProperties);
    }

    private static final Map<Direction, VoxelShape> AABBS = Map.of(
            Direction.NORTH, SHAPE_NORTHSOUTH,
            Direction.SOUTH, SHAPE_NORTHSOUTH,
            Direction.EAST,  SHAPE_EASTWEST,
            Direction.WEST,  SHAPE_EASTWEST
    );

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        Direction clockwise = facing.getClockWise();
        Direction counterClockwise = facing.getCounterClockWise();

        return canAttachTo(level, pos.relative(clockwise), counterClockwise)
                || canAttachTo(level, pos.relative(counterClockwise), clockwise);
    }

    private boolean canAttachTo(LevelReader level, BlockPos pos, Direction direction) {
        BlockState adjacent = level.getBlockState(pos);
        return adjacent.isFaceSturdy(level, pos, direction);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case EAST, WEST -> PLANK_EASTWEST;
            default -> PLANK_NORTHSOUTH;
        };
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AABBS.getOrDefault(state.getValue(FACING), Shapes.block());
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
}
