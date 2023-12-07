package com.Yt_Xmmie.modfirst.block.tileentity;

import com.Yt_Xmmie.modfirst.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {

    public static void registerTileEntities()
    {
    	GameRegistry.registerTileEntity(TileEntityPrintingMachine.class, new ResourceLocation(Reference.Mod_ID + ":printing_machine"));
    }
}
