package net.kaupenjoe.tutorialmod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class GongCeilingBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<GongCeilingBlock> CODEC = simpleCodec(GongCeilingBlock::new);

    private static final VoxelShape SHAPE_NORTHSOUTH = Block.box(2,0,7,14,12,9);
    private static final VoxelShape SHAPE_EASTWEST  =  Block.box(7,0,2,9,12,14);

    private static final Map<Direction, VoxelShape> AABBS = Map.of(
            Direction.NORTH, SHAPE_NORTHSOUTH,
            Direction.SOUTH, SHAPE_NORTHSOUTH,
            Direction.EAST,  SHAPE_EASTWEST,
            Direction.WEST,  SHAPE_EASTWEST
    );

    public GongCeilingBlock(Properties props) {
        super(props);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        // nur wenn Block über dir stützt
        return level.getBlockState(pos.above())
                .isFaceSturdy(level, pos.above(), Direction.DOWN);
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
