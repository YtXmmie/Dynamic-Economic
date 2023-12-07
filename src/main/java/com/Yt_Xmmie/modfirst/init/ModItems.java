package com.Yt_Xmmie.modfirst.init;

import java.util.ArrayList;
import java.util.List;

import com.Yt_Xmmie.modfirst.Main;
import com.Yt_Xmmie.modfirst.items.ItemBase;
import com.Yt_Xmmie.modfirst.items.ItemMoney;
import com.Yt_Xmmie.modfirst.items.ItemWritableEngraving;
import com.Yt_Xmmie.modfirst.items.ItemWrittenEngraving;

import net.minecraft.item.Item;
import net.minecraft.creativetab.CreativeTabs;
public class ModItems {

	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	 //要全部大写,可以加下划线    //你的物品名称    //放在哪个物品栏           
	public static final Item MONEY = new ItemMoney("money", Main.ECONOMICAL_TAB);
//	MONEY.setMaxStackSize(10);
//	public static Item MONEY = new ItemBase("money",CreativeTabs.COMBAT);
	public static final Item WRITABLE_ENGRAVING = new ItemWritableEngraving("writable_engraving",Main.ECONOMICAL_TAB);
	public static final Item WRITTEN_ENGRAVING = new ItemWrittenEngraving("written_engraving",Main.ECONOMICAL_TAB);
}