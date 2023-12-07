package com.Yt_Xmmie.modfirst.event;
//这段代码是声明包，读者不用管他
 
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
//由于这些代码是我从我的代码中复制出来的，所以会有很多没用的import
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 
@EventBusSubscriber
public class EventHandler {
    
    //这个方法是一个事件监听器，即使没有在任何地方调用，但在触法相应事件时就会自动调用
    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinWorldEvent event){
        Entity entity = event.getEntity();
        
        if (!entity.world.isRemote&&entity instanceof EntityPlayer){
/*            String message = "Hey! FUCK U,"+entity.getName()+"!";
            //这是字符串，可以修改
            TextComponentString text = new TextComponentString(message);
            //这段代码很重要，因为mc不能直接发送字符串
            entity.sendMessage(text);
*/            //发送字符串，参数是一个TextComponentString类
        }
    }
}
 