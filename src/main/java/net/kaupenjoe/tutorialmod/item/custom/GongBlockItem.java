package net.kaupenjoe.tutorialmod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
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
        Direction face = ctx.getClickedFace();

        // 1) Decke: DOWN → Ceiling‑Gong
        if (face == Direction.DOWN) {
            return ceilingBlock.getStateForPlacement(ctx);
        }
        // 2) Wand: NORTH, EAST, SOUTH, WEST → Wall‑Gong
        if (face.getAxis().isHorizontal()) {
            return wallBlock.getStateForPlacement(ctx);
        }
        return null;
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

        // Sound abspielen
        SoundType sound = s.getSoundType();
        lvl.playSound(null, pos, sound.getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);

        return InteractionResult.sidedSuccess(lvl.isClientSide());
    }
}