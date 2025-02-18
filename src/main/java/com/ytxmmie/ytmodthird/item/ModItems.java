package com.ytxmmie.ytmodthird.item;

import com.ytxmmie.ytmodthird.YtModThird;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {


    public static  final Item WRITABLE_ENGRAVING = registerItems("writable_engraving", new WritableEngravingItem(new Item.Settings()));
    public static  final Item WRITTEN_ENGRAVING = registerItems("written_engraving", new WrittenEngravingItem(new Item.Settings()));
    public static  final Item MONEY = registerItems("money", new MoneyItem(new Item.Settings()));

    private  static Item registerItems(String id, Item item){

        return Registry.register(Registries.ITEM, Identifier.of(YtModThird.Mod_ID, id), item);
    }
/*
    private static void addItemToItemGroup(FabricItemGroupEntries fabricItemGroupEntries){
        fabricItemGroupEntries.add(WRITABLE_ENGRAVING);
        fabricItemGroupEntries.add(WRITTEN_ENGRAVING);
        fabricItemGroupEntries.add(MONEY);
    }
   */

    public static void registerModItems(){
        YtModThird.LOGGER.info("Registering Items");
    }

}
