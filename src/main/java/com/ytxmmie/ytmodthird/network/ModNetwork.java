package com.ytxmmie.ytmodthird.network;

import com.ytxmmie.ytmodthird.item.ModItems;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;

import static com.ytxmmie.ytmodthird.network.EngravingPayload.CODE_PACKET_ID;

public class ModNetwork {

    public static void onInitialize() {
        PayloadTypeRegistry.playC2S().register(CODE_PACKET_ID, EngravingPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(EngravingPayload.CODE_PACKET_ID, (payload, context) -> {

            ItemStack stack = context.player().getMainHandStack();
            PlayerEntity user = context.player();
            user.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
            stack.decrementUnlessCreative(1, user);
            ItemStack itemStack2 = new ItemStack(ModItems.WRITTEN_ENGRAVING, 1);

            NbtComponent.set(DataComponentTypes.CUSTOM_DATA, itemStack2, payload.nbt());

             
            if (!user.getInventory().insertStack(itemStack2.copy())) {
                user.dropItem(itemStack2, false);
            }
        });

    }


}
