package com.ytxmmie.ytmodthird.entity;

import com.mojang.datafixers.types.Type;
import com.ytxmmie.ytmodthird.YtModThird;
import com.ytxmmie.ytmodthird.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class ModBlockEntities {

    public static final BlockEntityType<PrinterBlockEntity> PRINTER_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, Identifier.of(YtModThird.Mod_ID, "printer_block_entity"),
            BlockEntityType.Builder.create(PrinterBlockEntity::new,
                    ModBlocks.MONEY_PRINTER).build());

    public static void registerBlockEntities() {
        YtModThird.LOGGER.info("Registering Block Entities");
    }
}
