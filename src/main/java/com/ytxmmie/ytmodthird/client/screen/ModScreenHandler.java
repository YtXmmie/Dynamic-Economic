package com.ytxmmie.ytmodthird.client.screen;

import com.ytxmmie.ytmodthird.YtModThird;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandler {
    public static final ScreenHandlerType<PrinterScreenHandler> PRINTER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(YtModThird.Mod_ID, "printer"),
                    new ScreenHandlerType<>(PrinterScreenHandler::new, FeatureSet.empty()));
    public static void registerScreenHandlers(){

        YtModThird.LOGGER.info("registering Screen Handlers");
    }
}
