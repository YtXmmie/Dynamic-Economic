package com.ytxmmie.ytmodthird.datagen;

import com.ytxmmie.ytmodthird.block.ModBlocks;
import com.ytxmmie.ytmodthird.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelsProvider extends FabricModelProvider {
    public ModModelsProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.MONEY_PRINTER);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.MONEY, Models.GENERATED);
        itemModelGenerator.register(ModItems.WRITTEN_ENGRAVING, Models.GENERATED);
        itemModelGenerator.register(ModItems.WRITABLE_ENGRAVING, Models.GENERATED);
    }
}
