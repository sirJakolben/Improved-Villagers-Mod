package net.kaupenjoe.tutorialmod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class GongBlockItem extends BlockItem {
    private final Block ceilingBlock, wallBlock;

    public GongBlockItem(Block ceiling, Block wall, Item.Properties props) {
        super(wall, props);
        this.ceilingBlock = ceiling;
        this.wallBlock = wall;
    }

    @Override
    protected BlockState getPlacementState(BlockPlaceContext ctx) {
        Direction f = ctx.getClickedFace();
        Block choice = (f == Direction.DOWN ? ceilingBlock : wallBlock);
        return choice.getStateForPlacement(ctx);
    }

    @Override
    public InteractionResult place(BlockPlaceContext ctx) {
        BlockState s = getPlacementState(ctx);
        if (s == null) return InteractionResult.FAIL;
        Level lvl = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        if (!lvl.setBlock(pos, s, 11)) return InteractionResult.FAIL;
        super.updateCustomBlockEntityTag(pos, lvl, ctx.getPlayer(), ctx.getItemInHand(), s);
        ctx.getItemInHand().shrink(1);
        return InteractionResult.sidedSuccess(lvl.isClientSide);
    }
}