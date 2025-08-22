package com.ytxmmie.ytmodthird.network;

import com.ytxmmie.ytmodthird.item.ModItems;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.stat.Stats;

import static com.ytxmmie.ytmodthird.network.EngravingPayload.CODE_PACKET_ID;
import static com.ytxmmie.ytmodthird.network.UploadImagePayload.UPLOAD_IMAGE_PAYLOAD_ID;

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

        PayloadTypeRegistry.playC2S().register(UPLOAD_IMAGE_PAYLOAD_ID, UploadImagePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(UPLOAD_IMAGE_PAYLOAD_ID, (payload, context) -> {
            String base64 = payload.base64();
            PlayerEntity user = context.player();
            ItemStack stack = user.getMainHandStack();

            if (stack.isOf(ModItems.WRITABLE_ENGRAVING)) {
            // 替换原来的 NbtComponent.set(DataComponentTypes.CUSTOM_DATA, stack, base64);
                NbtCompound nbt = new NbtCompound();
                nbt.putString("image", base64);
                ItemStack itemStack2 = new ItemStack(ModItems.WRITTEN_ENGRAVING, 1);
                itemStack2.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
                user.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                stack.decrementUnlessCreative(1, user);

                if (!user.getInventory().insertStack(itemStack2.copy())) {
                    user.dropItem(itemStack2, false);
                }
            } else {
                // Handle the case where the item is not a WRITTEN_ENGRAVING
                // You might want to send a message to the player or log an error
            }
        });

    }


}
