package com.ytxmmie.ytmodthird.client.screen;

import com.ytxmmie.ytmodthird.entity.ModBlockEntities;
import com.ytxmmie.ytmodthird.entity.PrinterBlockEntity;
import com.ytxmmie.ytmodthird.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class PrinterScreenHandler extends ScreenHandler {
    public Inventory inventory;
    public PropertyDelegate propertyDelegate;

    public PrinterScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(PrinterBlockEntity.INVENTORY_SIZE), new ArrayPropertyDelegate(PrinterBlockEntity.PROPERTY_DELEGATE_SIZE));
    }

    public PrinterScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreenHandler.PRINTER_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.addProperties(this.propertyDelegate);

        //some inventories do custom logic when a player opens it.
        inventory.onOpen(playerInventory.player);


        this.addSlot(new InputSlot(inventory, PrinterBlockEntity.SLOT_INPUT, 68, 46));

        this.addSlot(new AdditionSlot(inventory, PrinterBlockEntity.SLOT_ADDITION, 93, 21));

        this.addSlot(new OutputSlot(inventory, PrinterBlockEntity.SLOT_OUTPUT, 118, 46));

        this.addSlot(new InkSlot(inventory, PrinterBlockEntity.SLOT_INK, 16, 19));


        //The player inventory
        for (int m = 0; m < 3; ++m) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 102 + m * 18));
            }
        }

        //The player Hotbar
        for (int m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 160));
        }
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStackNoCallbacks(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public int getProcessProgress(){
        int processProgress = this.propertyDelegate.get(0);
        int maxProcessProgress = this.propertyDelegate.get(1);

        if(maxProcessProgress == 0){
            return 0;
        }

        return 24 * processProgress / maxProcessProgress;
    }
    public int getInkProgress(){
        int inkProgress = this.propertyDelegate.get(2);
        int maxInkProgress = this.propertyDelegate.get(3);

        if(maxInkProgress == 0){
            return 0;
        }

        return 26 * inkProgress / maxInkProgress;
    }

    public int getInk(){
        int expProgress100 = this.propertyDelegate.get(4);//from 0 to 100
        float value = (float)expProgress100 / ((float) this.propertyDelegate.get(5));//from 0 to 1
        return Math.round(value * 72f);
    }

    class InputSlot extends Slot {
        public InputSlot(final Inventory inventory, final int index, final int x, final int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return stack.isOf(Items.PAPER) || stack.isOf(ModItems.WRITABLE_ENGRAVING);
        }
    }
    class AdditionSlot extends Slot {
        public AdditionSlot(final Inventory inventory, final int index, final int x, final int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return stack.isOf(ModItems.WRITTEN_ENGRAVING);
        }
    }
    class InkSlot extends Slot {
        public InkSlot(final Inventory inventory, final int index, final int x, final int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return stack.isOf(Items.INK_SAC) || stack.isOf(Items.GLOW_INK_SAC);
        }
    }
    class OutputSlot extends Slot {
        public OutputSlot(final Inventory inventory, final int index, final int x, final int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return stack.isOf(ModItems.WRITTEN_ENGRAVING) || stack.isOf(ModItems.MONEY);
        }
    }

}
