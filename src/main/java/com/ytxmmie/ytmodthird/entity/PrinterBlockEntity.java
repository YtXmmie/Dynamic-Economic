package com.ytxmmie.ytmodthird.entity;

import com.ytxmmie.ytmodthird.block.MoneyPrinter;
import com.ytxmmie.ytmodthird.client.screen.PrinterScreenHandler;
import com.ytxmmie.ytmodthird.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

public class PrinterBlockEntity extends BlockEntity implements SidedInventory, NamedScreenHandlerFactory {


    public static int INVENTORY_SIZE = 4;
    public DefaultedList<ItemStack> inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);


    public int processProgress;
    public int FACING;
    private int maxProcessProgress;
    private int inkProgress;
    private int maxInkProgress;
    private int ink;
    private int maxInk;

    public static int SLOT_INPUT = 0;
    public static int SLOT_ADDITION = 1;
    public static int SLOT_OUTPUT = 2;
    public static int SLOT_INK = 3;

    private static final int[] TOP_SLOTS = new int[]{1};
    private static final int[] BOTTOM_SLOTS = new int[]{2};
    private static final int[] BACK_SLOTS = new int[]{0};
    private static final int[] SIDE_SLOTS = new int[]{3};



    public static int PROPERTY_DELEGATE_SIZE = 6;
    public final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> processProgress;
                case 1 -> maxProcessProgress;
                case 2 -> inkProgress;
                case 3 -> maxInkProgress;
                case 4 -> ink;
                default -> maxInk;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index){
                case 0 -> processProgress = value;
                case 1 -> maxProcessProgress = value;
                case 2 -> inkProgress = value;
                case 3 -> maxInkProgress = value;
                case 4 -> ink = value;
                default -> maxInk = value;
            };
        }

        @Override
        public int size() {
            return PROPERTY_DELEGATE_SIZE;
        }
    };;


    public ItemStack lastInputStack = null;
    public ItemStack lastAdditionStack = null;

    public ItemStack result = null;


    public PrinterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PRINTER_BLOCK_ENTITY, pos, state);
        //this.propertyDelegate
        maxProcessProgress = 60;
        maxInkProgress = 20;
        maxInk = 64;
        FACING = 0;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, inventory, registryLookup);

        processProgress = nbt.getInt("ProcessProgress");
        inkProgress = nbt.getInt("inkProgress");
        ink = nbt.getInt("ink");

    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        Inventories.writeNbt(nbt, inventory, registryLookup);

        nbt.putInt("ProcessProgress", processProgress);
        nbt.putInt("inkProgress", inkProgress);
        nbt.putInt("ink", ink);


    }

    public void updateResult() {

        ItemStack additionStack = this.getStack(SLOT_ADDITION).copy();
        ItemStack inputStack = this.getStack(SLOT_INPUT).copy();
        if(inputStack.isOf(Items.PAPER)){

            result = new ItemStack(ModItems.MONEY, 1);
        }
        else if(inputStack.isOf(ModItems.WRITABLE_ENGRAVING)){

            result = new ItemStack(ModItems.WRITTEN_ENGRAVING, 1);
        }
        else {
            result = new ItemStack(Items.AIR);
        }
        NbtComponent nbtComponent = additionStack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);

        NbtComponent.set(DataComponentTypes.CUSTOM_DATA, result, nbtComponent.copyNbt());
        result.setCount(Math.min(additionStack.getCount(), inputStack.getCount()));
        if (result.isEmpty()) {
            return;
        }


        String newName = "";
        if(!additionStack.isEmpty()){
            newName = additionStack.getName().getString();
        }

        if (StringUtils.isBlank(newName)) {
            if (result.contains(DataComponentTypes.CUSTOM_NAME)) {
                result.remove(DataComponentTypes.CUSTOM_NAME);
            }
        } else if (!newName.equals(result.getName().getString())) {
            result.set(DataComponentTypes.CUSTOM_NAME, Text.of(newName));
        }

    }

    public boolean canAddInk() {
        ItemStack Ink = getStack(SLOT_INK);
        return (!Ink.isEmpty() && (Ink.isOf(Items.INK_SAC) || Ink.isOf(Items.GLOW_INK_SAC))&& ink + 16 <= maxInk);
    }
    public void doAddInk(){

        ItemStack Ink = getStack(SLOT_INK);
        Ink.decrement(1);
        ink += 16;
        if(ink > maxInk)
            ink = maxInk;
    }


    public boolean canPrint()
    {
        ItemStack Paper = getStack(SLOT_INPUT);
        ItemStack Engraving = getStack(SLOT_ADDITION);
        ItemStack Money = getStack(SLOT_OUTPUT);
        if(result!= null){

            return (!Paper.isEmpty()) && (Paper.isOf(Items.PAPER) || Paper.isOf(ModItems.WRITABLE_ENGRAVING)) && Math.min(Engraving.getCount(), Paper.getCount()) >= result.getCount()
                    && Engraving.isOf(ModItems.WRITTEN_ENGRAVING) &&  Engraving.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).equals(result.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT)) &&
                    (Money.isEmpty() || (Money.getCount() + result.getCount() <= Money.getMaxCount() && ItemStack.areItemsAndComponentsEqual(Money, result)))
                    && ink > 0;
        }
        else{
            return false;
        }
    }

    public void doProcess(){
        ItemStack Input = getStack(SLOT_INPUT);
        ItemStack Output = getStack(SLOT_OUTPUT);

        if(Output.isEmpty()){
            setStack(2, result.copy());
        }
        else{
            Output.increment(result.getCount());
        }
        Input.decrement(result.getCount());
        ink -= result.getCount();
    }

    public static void clienttick(World world, BlockPos pos, BlockState state, PrinterBlockEntity blockEntity) {


        switch (state.get(MoneyPrinter.FACING)){
            case Direction.WEST -> blockEntity.FACING = 1;
            case Direction.EAST -> blockEntity.FACING = 2;
            case Direction.SOUTH -> blockEntity.FACING = 3;
            default -> blockEntity.FACING = 4;
        };
        ItemStack input = blockEntity.getStack(SLOT_INPUT).copy();
        ItemStack addition = blockEntity.getStack(SLOT_ADDITION).copy();

        if(blockEntity.processProgress == 0 &&
                (blockEntity.lastInputStack == null ||
                        (!blockEntity.lastInputStack.equals(input)) ||
                        blockEntity.lastAdditionStack == null ||
                        (!blockEntity.lastAdditionStack.equals(addition)) ||
                        blockEntity.result == null)
        ) {
            blockEntity.lastInputStack = input.copy();
            blockEntity.lastAdditionStack = addition.copy();
            blockEntity.updateResult();
        }

    }
    public static void servertick(World world, BlockPos pos, BlockState state, PrinterBlockEntity blockEntity) {

        boolean toMarkDirty = false;
        switch (state.get(MoneyPrinter.FACING)){
            case Direction.WEST -> blockEntity.FACING = 1;
            case Direction.EAST -> blockEntity.FACING = 2;
            case Direction.SOUTH -> blockEntity.FACING = 3;
            default -> blockEntity.FACING = 4;
        };
            ItemStack input = blockEntity.getStack(SLOT_INPUT).copy();
            ItemStack addition = blockEntity.getStack(SLOT_ADDITION).copy();

            if(blockEntity.processProgress == 0 &&
                    (blockEntity.lastInputStack == null ||
                            (!blockEntity.lastInputStack.equals(input)) ||
                            blockEntity.lastAdditionStack == null ||
                            (!blockEntity.lastAdditionStack.equals(addition)) ||
                            blockEntity.result == null)
            ) {
                blockEntity.lastInputStack = input.copy();
                blockEntity.lastAdditionStack = addition.copy();
                blockEntity.updateResult();
            }
            if(blockEntity.canAddInk()){
                if((Boolean)state.get(MoneyPrinter.ENABLED)){

                    blockEntity.inkProgress++;
                    toMarkDirty = true;
                }
                if(blockEntity.inkProgress >= blockEntity.maxInkProgress){
                    blockEntity.inkProgress = 0;
                    blockEntity.doAddInk();;
                    toMarkDirty = true;
                }
            }
            else {
                if(blockEntity.inkProgress != 0){
                    blockEntity.inkProgress = 0;
                    toMarkDirty = true;
                }
            }



        if(blockEntity.canPrint()){
            if((Boolean)state.get(MoneyPrinter.ENABLED)){
                blockEntity.processProgress++;
                toMarkDirty = true;
            }
            if(blockEntity.processProgress >= blockEntity.maxProcessProgress){
                blockEntity.processProgress = 0;
                blockEntity.doProcess();
                toMarkDirty = true;
            }
        }
        else {
            if(blockEntity.processProgress != 0) {
                blockEntity.processProgress = 0;
                toMarkDirty = true;
            }
        }
        if (toMarkDirty) {
            blockEntity.markDirty();
        }

    }
    @Override
    public void markDirty() {
        boolean serverSide = this.hasWorld() && !this.getWorld().isClient();

        super.markDirty();

        if (serverSide) {
            ((ServerWorld) world).getChunkManager().markForUpdate(getPos());
        }
    }
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }


    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new PrinterScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return ((stack.isOf(Items.PAPER) || stack.isOf(ModItems.WRITABLE_ENGRAVING)) && slot == SLOT_INPUT) ||
                (stack.isOf(ModItems.WRITTEN_ENGRAVING) && slot == SLOT_ADDITION) ||
                ((stack.isOf(ModItems.WRITTEN_ENGRAVING) || stack.isOf(ModItems.MONEY)) && slot == SLOT_OUTPUT) ||
                ((stack.isOf(Items.INK_SAC) || stack.isOf(Items.GLOW_INK_SAC)) &&slot == SLOT_INK);
    }
    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return BOTTOM_SLOTS;
        }
        else if(side == Direction.UP){
            return TOP_SLOTS;
        }
        else {
            if (side == Direction.EAST && FACING == 1)
            {
                return BACK_SLOTS;
            }
            else if (side == Direction.EAST && FACING == 2)
            {
                return BOTTOM_SLOTS;
            }
            else if (side == Direction.WEST && FACING == 2)
            {
                return BACK_SLOTS;
            }
            else if (side == Direction.WEST && FACING == 1)
            {
                return BOTTOM_SLOTS;
            }
            else if (side == Direction.NORTH && FACING == 3)
            {
                return BACK_SLOTS;
            }
            else if (side == Direction.NORTH && FACING == 4)
            {
                return BOTTOM_SLOTS;
            }
            else if (side == Direction.SOUTH && FACING == 4)
            {
                return BACK_SLOTS;

            }
            else if (side == Direction.SOUTH && FACING == 3)
            {
                return BOTTOM_SLOTS;
            }
        }
        return SIDE_SLOTS;
    }
    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir)
    {
        return isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir)
    {
        return dir == Direction.DOWN && slot == SLOT_OUTPUT;
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStack(int slot) {

        return inventory.get(slot);

    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if(amount == 0){
            return ItemStack.EMPTY;
        }

        if(amount == inventory.get(slot).getCount()){
            return removeStack(slot);
        }

        ItemStack toRet = inventory.get(slot).copy();
        inventory.get(slot).decrement(amount);
        toRet.setCount(amount);

        return toRet;

    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack toRet = inventory.get(slot).copy();
        inventory.set(slot, ItemStack.EMPTY);

        return toRet;

    }

    @Override
    public void setStack(int slot, ItemStack stack) {

        inventory.set(slot, stack);

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {

        inventory.clear();

    }
    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}
