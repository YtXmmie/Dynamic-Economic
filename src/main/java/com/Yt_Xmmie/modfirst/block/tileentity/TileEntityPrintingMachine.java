package com.Yt_Xmmie.modfirst.block.tileentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityPrintingMachine extends TileEntity{


    private ItemStackHandler inventory = new ItemStackHandler(6) {

        @Override
        @Nonnull
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return super.insertItem(slot, stack, simulate);
        }

        @Override
        protected void onContentsChanged(int slot) {
        	TileEntityPrintingMachine.this.markDirty();
        }
    };

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("Inventory", inventory.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("Inventory"));
        super.readFromNBT(compound);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) inventory : super.getCapability(capability, facing);
    }
}
