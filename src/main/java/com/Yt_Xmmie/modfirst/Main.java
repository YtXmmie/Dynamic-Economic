//包可以自动导入
package com.Yt_Xmmie.modfirst;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.Yt_Xmmie.modfirst.network.EngravingGuiHandler;
import com.Yt_Xmmie.modfirst.packet.PacketEngraving;
import com.Yt_Xmmie.modfirst.packet.PacketName;
import com.Yt_Xmmie.modfirst.proxy.CommonProxy;
import com.Yt_Xmmie.modfirst.util.Reference;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.creativetab.CreativeTabs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

@Mod(modid = Reference.Mod_ID, name = Reference.NAME, version=Reference.VERSION)    
public class Main {

	@Instance
	public static Main instance;
	public static final Logger logger = LogManager.getLogger(Reference.Mod_ID);
	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.Mod_ID);
	
	public static HashMap<EntityPlayer, ItemStack> formingEngraving = new HashMap<EntityPlayer, ItemStack>();
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	private static Configuration config;
	private static float exhaustionCoefficient;
	private static Integer[] disabledDestinations;
	private static Integer[] disabledLeaving;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event)
	{
		
/*		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		exhaustionCoefficient = (float)config.get("tweaks", "exhaustion_coefficient", 0.0f).getDouble(10.0);
		int[] disabledDestinationsP = config.get("features", "disabled_destination_dimensions", new int[] {}).getIntList();
		int[] disabledLeavingP = config.get("features", "disabled_departing_dimensions", new int[] {}).getIntList();
		disabledDestinations = new Integer[disabledDestinationsP.length];
		disabledLeaving = new Integer[disabledLeavingP.length];
		for (int i = 0; i < disabledDestinationsP.length; ++i) {
			disabledDestinations[i] = disabledDestinationsP[i];
		}
		for (int i = 0; i < disabledLeavingP.length; ++i) {
			disabledLeaving[i] = disabledLeavingP[i];
		}
		config.save();*/
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new EngravingGuiHandler()); 
		
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent event)
	{
		
		int disc = 0;
		MinecraftForge.EVENT_BUS.register(proxy);
		MinecraftForge.EVENT_BUS.register(this);
		network.registerMessage(PacketEngraving.class, PacketEngraving.class, disc++, Side.SERVER);
        network.registerMessage(PacketName.PacketNameHandler.class, PacketName.class, disc++, Side.SERVER);
	}

    public static CreativeTabs ECONOMICAL_TAB = new EconomicalTab( );
	
    //你可以添加更多的个人物品栏
    //public static CreativeTabs BLOCK_TAB = new BlockTab( );
	
}
