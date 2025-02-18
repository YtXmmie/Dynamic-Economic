package com.ytxmmie.ytmodthird.block;

import com.mojang.serialization.MapCodec;
import com.ytxmmie.ytmodthird.entity.ModBlockEntities;
import com.ytxmmie.ytmodthird.entity.PrinterBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class MoneyPrinter extends BlockWithEntity implements BlockEntityProvider {

    public static final MapCodec<MoneyPrinter> CODEC = createCodec(MoneyPrinter::new);

    public static final BooleanProperty ENABLED = Properties.ENABLED;

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public static final VoxelShape SHAPE_N = Stream.of(
            Block.createCuboidShape(3, 1, 5, 4, 4, 14),
            Block.createCuboidShape(12, 1, 5, 13, 4, 14),
            Block.createCuboidShape(4, 1, 13, 12, 4, 14),
            Block.createCuboidShape(5, 0, 2, 11, 1, 4),
            Block.createCuboidShape(4, 3, 5, 12, 4, 12),
            Block.createCuboidShape(4, 2, 2, 12, 2, 10),
            Block.createCuboidShape(4, 3, 4, 12, 3, 12),
            Block.createCuboidShape(4, 4, 13, 12, 7, 14),
            Block.createCuboidShape(4, 0, 13, 12, 8, 13),
            Block.createCuboidShape(3, 1, 4, 4, 3, 5),
            Block.createCuboidShape(12, 1, 4, 13, 3, 5),
            Block.createCuboidShape(4, 0, 12, 12, 8, 12),
            Block.createCuboidShape(2, 0, 5, 3, 3, 14),
            Block.createCuboidShape(3, 0, 4, 13, 1, 14)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape SHAPE_W = Stream.of(
            Block.createCuboidShape(5, 1, 12, 14, 4, 13),
            Block.createCuboidShape(5, 1, 3, 14, 4, 4),
            Block.createCuboidShape(13, 1, 4, 14, 4, 12),
            Block.createCuboidShape(2, 0, 5, 4, 1, 11),
            Block.createCuboidShape(5, 3, 4, 12, 4, 12),
            Block.createCuboidShape(2, 2, 4, 10, 2, 12),
            Block.createCuboidShape(4, 3, 4, 12, 3, 12),
            Block.createCuboidShape(13, 4, 4, 14, 7, 12),
            Block.createCuboidShape(13, 0, 4, 13, 8, 12),
            Block.createCuboidShape(4, 1, 12, 5, 3, 13),
            Block.createCuboidShape(4, 1, 3, 5, 3, 4),
            Block.createCuboidShape(12, 0, 4, 12, 8, 12),
            Block.createCuboidShape(5, 0, 13, 14, 3, 14),
            Block.createCuboidShape(4, 0, 3, 14, 1, 13)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape SHAPE_S = Stream.of(
            Block.createCuboidShape(12, 1, 2, 13, 4, 11),
            Block.createCuboidShape(3, 1, 2, 4, 4, 11),
            Block.createCuboidShape(4, 1, 2, 12, 4, 3),
            Block.createCuboidShape(5, 0, 12, 11, 1, 14),
            Block.createCuboidShape(4, 3, 4, 12, 4, 11),
            Block.createCuboidShape(4, 2, 6, 12, 2, 14),
            Block.createCuboidShape(4, 3, 4, 12, 3, 12),
            Block.createCuboidShape(4, 4, 2, 12, 7, 3),
            Block.createCuboidShape(4, 0, 3, 12, 8, 3),
            Block.createCuboidShape(12, 1, 11, 13, 3, 12),
            Block.createCuboidShape(3, 1, 11, 4, 3, 12),
            Block.createCuboidShape(4, 0, 4, 12, 8, 4),
            Block.createCuboidShape(13, 0, 2, 14, 3, 11),
            Block.createCuboidShape(3, 0, 2, 13, 1, 12)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape SHAPE_E = Stream.of(
            Block.createCuboidShape(2, 1, 3, 11, 4, 4),
            Block.createCuboidShape(2, 1, 12, 11, 4, 13),
            Block.createCuboidShape(2, 1, 4, 3, 4, 12),
            Block.createCuboidShape(12, 0, 5, 14, 1, 11),
            Block.createCuboidShape(4, 3, 4, 11, 4, 12),
            Block.createCuboidShape(6, 2, 4, 14, 2, 12),
            Block.createCuboidShape(4, 3, 4, 12, 3, 12),
            Block.createCuboidShape(2, 4, 4, 3, 7, 12),
            Block.createCuboidShape(3, 0, 4, 3, 8, 12),
            Block.createCuboidShape(11, 1, 3, 12, 3, 4),
            Block.createCuboidShape(11, 1, 12, 12, 3, 13),
            Block.createCuboidShape(4, 0, 4, 4, 8, 12),
            Block.createCuboidShape(2, 0, 2, 11, 3, 3),
            Block.createCuboidShape(2, 0, 3, 12, 1, 13)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    public MoneyPrinter(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(ENABLED, Boolean.valueOf(false)));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

        return switch (state.get(FACING)) {
            case WEST -> SHAPE_W;
            case EAST -> SHAPE_E;
            case SOUTH -> SHAPE_S;
            default -> SHAPE_N;
        };
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(ENABLED, Boolean.valueOf(false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, ENABLED);

    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PrinterBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {

        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PrinterBlockEntity) {
                ItemScatterer.spawn(world, pos, (PrinterBlockEntity) blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }

    }
    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock())) {
            this.updateEnabled(world, pos, state);
        }
    }
    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        this.updateEnabled(world, pos, state);
    }

    private void updateEnabled(World world, BlockPos pos, BlockState state) {
        boolean bl = world.isReceivingRedstonePower(pos);
        if (bl != (Boolean)state.get(ENABLED)) {
            world.setBlockState(pos, state.with(ENABLED, Boolean.valueOf(bl)), Block.NOTIFY_LISTENERS);

        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            //This will call the createScreenHandlerFactory method from BlockWithEntity, which will return our blockEntity casted to
            //a namedScreenHandlerFactory. If your block class does not extend BlockWithEntity, it needs to implement createScreenHandlerFactory.
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

            if (screenHandlerFactory != null) {
                //With this call the server will request the client to open the appropriate Screenhandler
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ?
                    validateTicker(type, ModBlockEntities.PRINTER_BLOCK_ENTITY, PrinterBlockEntity::clienttick)
                    :validateTicker(type, ModBlockEntities.PRINTER_BLOCK_ENTITY, PrinterBlockEntity::servertick);
    }
}

