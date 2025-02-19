package com.ytxmmie.ytmodthird;

import com.ytxmmie.ytmodthird.block.ModBlocks;
import com.ytxmmie.ytmodthird.client.screen.ModScreenHandler;
import com.ytxmmie.ytmodthird.entity.ModBlockEntities;
import com.ytxmmie.ytmodthird.item.ModItemGroups;
import com.ytxmmie.ytmodthird.item.ModItems;
import com.ytxmmie.ytmodthird.network.ModNetwork;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YtModThird implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String Mod_ID = "ytmodthird";
    public static final Logger LOGGER = LoggerFactory.getLogger(Mod_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();
		ModBlocks.registerModBlocks();
		ModNetwork.onInitialize();
		ModBlockEntities.registerBlockEntities();
		ModScreenHandler.registerScreenHandlers();
		//ModComponentTypes.registerModComponentTypes();
		LOGGER.info("Hello Fabric world!");
	}
}