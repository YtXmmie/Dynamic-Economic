package com.ytxmmie.ytmodthird.datagen;

import com.ytxmmie.ytmodthird.YtModThird;
import com.ytxmmie.ytmodthird.block.ModBlocks;
import com.ytxmmie.ytmodthird.item.ModItemGroups;
import com.ytxmmie.ytmodthird.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends FabricRecipeProvider {

    public ModRecipesProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.MONEY_PRINTER, 1)
                .pattern("ISI")
                .pattern("RWG")
                .pattern("III")
                .input('I', Ingredient.ofItems(Items.IRON_INGOT))
                .criterion("has item", RecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .input('R', Ingredient.ofItems(Items.REDSTONE))
                .criterion("has item", RecipeProvider.conditionsFromItem(Items.REDSTONE))
                .input('S', Ingredient.ofItems(Items.STRING))
                .criterion("has item", RecipeProvider.conditionsFromItem(Items.STRING))
                .input('W', Ingredient.ofItems(Items.WRITABLE_BOOK))
                .criterion("has item", RecipeProvider.conditionsFromItem(Items.WRITABLE_BOOK))
                .input('G', Ingredient.ofItems(Items.GOLD_INGOT))
                .criterion("has item", RecipeProvider.conditionsFromItem(Items.GOLD_INGOT))
                .offerTo(exporter, Identifier.of(YtModThird.Mod_ID, "craft_printer"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WRITABLE_ENGRAVING)
                .input(Items.GOLD_INGOT)
                .criterion("has item", RecipeProvider.conditionsFromItem(Items.GOLD_INGOT))
                .input(Items.WRITABLE_BOOK)
                .criterion("has item", RecipeProvider.conditionsFromItem(Items.WRITABLE_BOOK))
                .input(Items.INK_SAC)
                .criterion("has item", RecipeProvider.conditionsFromItem(Items.INK_SAC))
                .offerTo(exporter, Identifier.of(YtModThird.Mod_ID, "craft_engraving"));

    }
}
