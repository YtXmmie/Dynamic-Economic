package com.ytxmmie.ytmodthird.item;

import com.ytxmmie.ytmodthird.YtModThird;
import com.ytxmmie.ytmodthird.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup DYNAMIC_ECONOMY = Registry.register(Registries.ITEM_GROUP, Identifier.of(YtModThird.Mod_ID, "dynamic_economy"),
            ItemGroup.create(null, -1).displayName(Text.translatable("itemGroup.dynamic_economy"))
                    .icon(() -> new ItemStack(ModItems.MONEY))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.MONEY);
                        entries.add(ModItems.WRITABLE_ENGRAVING);
                        entries.add(ModItems.WRITTEN_ENGRAVING);
                        entries.add(ModBlocks.MONEY_PRINTER);
                    }).build());

    public static void registerModItemGroups(){
        YtModThird.LOGGER.info("Regostering Item Groups");
    }
}
