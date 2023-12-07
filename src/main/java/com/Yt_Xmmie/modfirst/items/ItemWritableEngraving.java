package com.Yt_Xmmie.modfirst.items;

import com.Yt_Xmmie.modfirst.Main;
import com.Yt_Xmmie.modfirst.init.ModBlocks;
import com.Yt_Xmmie.modfirst.init.ModItems;
import com.Yt_Xmmie.modfirst.util.EngravingUtils;
import com.Yt_Xmmie.modfirst.util.IHasModel;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
public class ItemWritableEngraving extends Item implements IHasModel{

	public ItemWritableEngraving (String name,CreativeTabs tab) {
		
		setUnlocalizedName("ytmodfirst."+name);
		setRegistryName(name);
		setCreativeTab(tab);
		setMaxStackSize(1);
		ModItems.ITEMS.add(this);
	}
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
  //      ItemStack itemstack = new ItemStack(ModBlocks.PRINTING_MACHINE,1);
        ItemStack itemstack1 = playerIn.getHeldItem(handIn);
		//       itemstack1.shrink(1);
 /*       if (itemstack1.isEmpty())
        {
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }
        else
        {
            if (!playerIn.inventory.addItemStackToInventory(itemstack.copy()))
            {
                playerIn.dropItem(itemstack, false);
            }

            playerIn.addStat(StatList.getObjectUseStats(this));
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack1);
        }*/
    	EngravingUtils.bindName(new ItemStack(ModItems.WRITTEN_ENGRAVING), worldIn, playerIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack1);
    }
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
		
	}

}
