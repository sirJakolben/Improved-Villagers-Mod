package net.kaupenjoe.tutorialmod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
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

    @Override
    protected InteractionResult useWithoutItem(BlockState state,
                                               Level level,
                                               BlockPos pos,
                                               Player player,
                                               BlockHitResult hit) {
        if (!level.isClientSide && this.onHit(level, state, pos, player, hit)) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    protected boolean onHit(Level level,
                            BlockState state,
                            BlockPos pos,
                            Player player,
                            BlockHitResult hit) {
        Direction face = hit.getDirection();
        double hitY    = hit.getLocation().y - pos.getY();

        // 1) Bei Decken‑Block: nur seitliche Treffer
        if (this instanceof GongCeilingBlock) {
            if (face.getAxis() == Direction.Axis.Y) return false;
        }
        // 2) Bei Wand‑Block: nur seitliche Treffer auf der richtigen Wandseite
        else if (this instanceof GongCeilingBlock) {
            Direction facing = state.getValue(FACING);
            // wir wollen z. B. nur treffen, wenn face != UP/DOWN und face.axis != facing.axis
            if (face.getAxis() == Direction.Axis.Y) return false;
            if (face.getAxis() == facing.getAxis())  return false;
        }

        // Alles gut: hier läuten
        level.playSound(null, pos, SoundEvents.BELL_BLOCK, SoundSource.BLOCKS, 1.0F, 1.0F);
        // blockEvent abfeuern, falls du eine Animation per BlockEntityRenderer starten willst
        level.blockEvent(pos, this, BellBlock.EVENT_BELL_RING, face.get3DDataValue());
        return true;
    }
}
