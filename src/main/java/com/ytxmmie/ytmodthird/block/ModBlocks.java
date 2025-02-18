package com.ytxmmie.ytmodthird.block;


import com.ytxmmie.ytmodthird.YtModThird;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block MONEY_PRINTER = register("money_printer",
            new MoneyPrinter(AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.ANVIL)
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(1.0F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK)
            ));


    public static void registerBlockItems(String id, Block block) {
        Item item = Registry.register(Registries.ITEM, Identifier.of(YtModThird.Mod_ID, id), new BlockItem(block, new Item.Settings()));
        if(item instanceof BlockItem){
            ((BlockItem) item).appendBlocks(Item.BLOCK_ITEMS, item);
        }

    }

    public static Block register(String id, Block block) {
        registerBlockItems(id, block);
        return Registry.register(Registries.BLOCK, Identifier.of(YtModThird.Mod_ID, id), block);
    }


    public static void registerModBlocks(){
        YtModThird.LOGGER.info("Registering Blocks");
    }
}
