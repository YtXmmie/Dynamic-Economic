package com.Yt_Xmmie.modfirst.init;

import java.util.ArrayList;
import java.util.List;

import com.Yt_Xmmie.modfirst.block.BlockPrintingMachine;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {
	
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	public static final Block PRINTING_MACHINE = new BlockPrintingMachine("printing_machine", Material.ANVIL);
	
}
