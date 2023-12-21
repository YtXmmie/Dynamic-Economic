package com.Yt_Xmmie.modfirst.block.tileentity;

import javax.annotation.Nonnull;

import com.Yt_Xmmie.modfirst.init.ModItems;
import com.Yt_Xmmie.modfirst.items.ItemWrittenEngraving;
import com.Yt_Xmmie.modfirst.proxy.CommonProxy;

import net.minecraft.block.BlockCake;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlockSpecial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.apache.commons.lang3.StringUtils;

public class ContainerPrintingMachiane extends Container{
	
    private final InventoryCraftResult outputSlot = new InventoryCraftResult();
    private final IItemHandler ingredientHandler;
    private final BlockPos pos;
    private final World world;
    private String moneyvalue;
    private TileEntityPrintingMachine printingMachine;

    public ContainerPrintingMachiane(InventoryPlayer playerInventory, final World world, final BlockPos pos, TileEntityPrintingMachine te) {
        this.pos = pos;
        this.world = world;
        this.printingMachine = te;
        this.ingredientHandler = printingMachine.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        initSlots(playerInventory);
        this.updateMoneyOutput();
    }

    private void initSlots(InventoryPlayer playerInventory) {
        this.addSlotToContainer(new SlotEngraving(ingredientHandler, 0, 10, 50));

        for (int i = 1; i < 6; ++i) {
            this.addSlotToContainer(new SlotLayeredIngredient(ingredientHandler, i, 12 + i * 18, 50));
        }
        this.addSlotToContainer(new SlotMoney(this.outputSlot, 6, 150, 50));
        addPlayerSlots(playerInventory);
    }

    private void addPlayerSlots(InventoryPlayer playerInventory) {

        //Main inventory
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 79 + row * 18));
            }
        }

        //Hotbar
        for (int hotbar = 0; hotbar < 9; hotbar++)
        {
            this.addSlotToContainer(new Slot(playerInventory, hotbar, 8 + hotbar * 18, 137));
        }
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
        return playerIn.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public void updateMoneyOutput() {
        ItemStack engraving = this.ingredientHandler.getStackInSlot(0);

        if (engraving.isEmpty() ||!( engraving.getItem() instanceof ItemWrittenEngraving)) {
            this.outputSlot.setInventorySlotContents(0, ItemStack.EMPTY);
            this.detectAndSendChanges();
            return;
        }
        ItemStack paper = this.ingredientHandler.getStackInSlot(3);
        if(paper.isEmpty() || ! (paper.getItem() == Items.PAPER))
        {
            this.outputSlot.setInventorySlotContents(0, ItemStack.EMPTY);
            this.detectAndSendChanges();
            return;
        }
        if(!engraving.isEmpty() && !paper.isEmpty() && paper.getItem() == Items.PAPER && engraving.getItem() instanceof ItemWrittenEngraving)
        {
        	ItemStack output = new ItemStack(ModItems.MONEY,1);
        	output.setCount(1);
        	output.setTagCompound(new NBTTagCompound());
        	String key="NULL",Name = engraving.getDisplayName();
        	if(engraving.hasTagCompound() && engraving.getTagCompound().hasKey("moneykey"))
        		key = engraving.getTagCompound().getString("moneykey");
        	output.getTagCompound().setString("moneykey", key);
            output.setStackDisplayName(Name);
            if (!StringUtils.isBlank(this.moneyvalue))
            {
            	output.getTagCompound().setString("moneyvalue", this.moneyvalue);
            }
            else {

            	output.getTagCompound().setString("moneyvalue", "NULL");
            }
            this.outputSlot.setInventorySlotContents(0, output);
            this.detectAndSendChanges();
        }
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 6)
            {
                if (!this.mergeItemStack(itemstack1, 7, 43, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index >= 7)
            {
                if (index < 43)
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false) && !this.mergeItemStack(itemstack1, 1, 6,
                            false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            else if (!this.mergeItemStack(itemstack1, 7, 43, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

    public void updateMoneyValue(String newName)
    {
        this.moneyvalue = newName;
        this.updateMoneyOutput();
    }

    private class SlotEngraving extends SlotItemHandler
    {
        public SlotEngraving(IItemHandler handler, int index, int xPosition, int yPosition)
        {
            super(handler, index, xPosition, yPosition);
        }

        @Override
        public void onSlotChanged()
        {
        	ContainerPrintingMachiane.this.updateMoneyOutput();
        }

        @Override
        public boolean isItemValid(ItemStack stack)
        {
            return true;
        }
    }

    private class SlotLayeredIngredient extends SlotItemHandler
    {
        public SlotLayeredIngredient(IItemHandler handler, int index, int xPosition, int yPosition)
        {
            super(handler, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack)
        {
            return true;
        }

        @Override
        public void onSlotChanged()
        {
        	ContainerPrintingMachiane.this.updateMoneyOutput();
        }
    }

    private class SlotMoney extends Slot
    {
        public SlotMoney(IInventory inventory, int index, int xPosition, int yPosition)
        {
            super(inventory, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) { return false; }

        @Override
        public void onSlotChanged()
        {
        	ContainerPrintingMachiane.this.updateMoneyOutput();
        }

        @Nonnull
        @Override
        public ItemStack onTake(EntityPlayer thePlayer, @Nonnull ItemStack stack)
        {
            IItemHandler ingredients = ContainerPrintingMachiane.this.ingredientHandler;
            if (ingredients != null) {
                for (int i = 0; i < ingredients.getSlots(); i++) {
                    ItemStack slot = ingredients.getStackInSlot(i);
                    ItemStack container = slot.getItem().getContainerItem(slot);
                    if(i!=0) slot.shrink(1);
                    if (!container.isEmpty()) {
                        ingredients.insertItem(i, container, false);
                    }
                }
            }
            ContainerPrintingMachiane.this.updateMoneyOutput();
            return stack;
        }
    }
}