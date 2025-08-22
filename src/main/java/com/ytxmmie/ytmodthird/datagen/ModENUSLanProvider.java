package com.ytxmmie.ytmodthird.datagen;

import com.ytxmmie.ytmodthird.block.ModBlocks;
import com.ytxmmie.ytmodthird.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModENUSLanProvider extends FabricLanguageProvider {
    public ModENUSLanProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.MONEY, "Money");
        translationBuilder.add(ModItems.WRITABLE_ENGRAVING, "Writable Engraving");
        translationBuilder.add(ModItems.WRITTEN_ENGRAVING, "Written Engraving");
        translationBuilder.add(ModBlocks.MONEY_PRINTER, "Money Printer");
        translationBuilder.add("itemGroup.dynamic_economy", "Dynamic Economy");
        translationBuilder.add("money.code", "%s");
        translationBuilder.add("money.value", "%s");
        translationBuilder.add("gui.ytmodthird.picture.select", "select");
        translationBuilder.add("gui.ytmodthird.picture.upload", "upload");
        translationBuilder.add("gui.ytmodthird.picture.title", "Please upload a picture");
        translationBuilder.add("gui.ytmodthird.picture.button", "Picture");
        translationBuilder.add("gui.ytmodthird.picture.suggested", "Suggested image size <= 128x128，Base64 length <= 65535 characters");
        translationBuilder.add("gui.ytmodthird.picture.now", "Image Base64 length now :");
        translationBuilder.add("gui.ytmodthird.picture.warning", "The iamge is too large to upload!");
        translationBuilder.add("gui.ytmodthird.Engraving.title.code", "Please enter the code.");
        translationBuilder.add("gui.ytmodthird.Engraving.title.value", "Please enter the value.");
        translationBuilder.add("gui.ytmodthird.printer.tooltip.input1",
                "When paper put, the output would be money, ");
        translationBuilder.add("gui.ytmodthird.printer.tooltip.input2",
                "while writable engravings put, ");
        translationBuilder.add("gui.ytmodthird.printer.tooltip.input3",
                "the ouput would be written engraving.");
        translationBuilder.add("gui.ytmodthird.printer.tooltip.addition1",
                "When the written engrvaing put,");
        translationBuilder.add("gui.ytmodthird.printer.tooltip.addition2",
                " the output would extends its ");
        translationBuilder.add("gui.ytmodthird.printer.tooltip.addition3", "name, code, value and picture.");

        System.out.println("ModENUSLanProvider generateTranslations called");
    }
}
