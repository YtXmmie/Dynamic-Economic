package com.ytxmmie.ytmodthird.item;

import com.ytxmmie.ytmodthird.client.screen.EngravingInfoScreen;
import com.ytxmmie.ytmodthird.client.screen.MoneyInfoScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class MoneyItem extends Item {

    public MoneyItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);

        NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);

        NbtCompound nbt = nbtComponent.copyNbt();

        NbtComponent.set(DataComponentTypes.CUSTOM_DATA, stack, nbt);

        if(nbt.contains("money_value")) {
            tooltip.add(Text.translatable("money.value", stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getString("money_code"), NbtComponent.DEFAULT).formatted(Formatting.GRAY));
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            ItemStack stack = user.getStackInHand(hand);
            NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
            NbtCompound nbt = nbtComponent.copyNbt();
            String code = nbt.getString("money_code");
            String value = nbt.getString("money_value");
            String imageBase64 = nbt.getString("image");
            MinecraftClient.getInstance().setScreen(new MoneyInfoScreen(code, value, imageBase64));
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }


}