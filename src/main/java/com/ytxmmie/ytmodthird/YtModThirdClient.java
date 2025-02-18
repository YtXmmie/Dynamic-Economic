package com.ytxmmie.ytmodthird;

import com.ytxmmie.ytmodthird.client.PrinterBlockEntityRenderer;
import com.ytxmmie.ytmodthird.client.PrinterScreen;
import com.ytxmmie.ytmodthird.client.screen.ModScreenHandler;
import com.ytxmmie.ytmodthird.entity.ModBlockEntities;
import com.ytxmmie.ytmodthird.entity.PrinterBlockEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class YtModThirdClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        HandledScreens.register(ModScreenHandler.PRINTER_SCREEN_HANDLER, PrinterScreen::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.PRINTER_BLOCK_ENTITY, PrinterBlockEntityRenderer::new);
    }
}
