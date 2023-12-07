package com.Yt_Xmmie.modfirst.block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.Yt_Xmmie.modfirst.Main;
import com.Yt_Xmmie.modfirst.block.tileentity.TileEntityPrintingMachine;
import com.Yt_Xmmie.modfirst.init.ModBlocks;
import com.Yt_Xmmie.modfirst.init.ModItems;
import com.Yt_Xmmie.modfirst.network.EngravingGuiHandler;
import com.Yt_Xmmie.modfirst.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockPrintingMachine extends Block implements IHasModel{

	protected static final AxisAlignedBB PRINTING_MACHINE_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.25D, 0.9D);
	public BlockPrintingMachine(String name, Material material) {
		
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.ECONOMICAL_TAB);
        setHardness(50.0F);
        setSoundType(SoundType.ANVIL);
		setResistance(500.0f);
		setHarvestLevel("pickaxe", 3);
        
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(!worldIn.isRemote)
		{

            player.openGui(Main.instance, EngravingGuiHandler.PRINTING_MONEY, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityPrintingMachine) {
            IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            if (itemHandler != null) {
                for (int i = 0; i < itemHandler.getSlots(); i++) {
                    ItemStack stack = itemHandler.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
                        world.spawnEntity(item);
                    }
                }
            }
        }
        super.breakBlock(world, pos, state);
    }

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return PRINTING_MACHINE_AABB;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state)
    {
        return new TileEntityPrintingMachine();
    }
	@Override
	public void registerModels() {
		
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
		
	}
}
