package com.ytxmmie.ytmodthird.item;

import com.ytxmmie.ytmodthird.client.screen.EngravingInfoScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class WrittenEngravingItem extends  Item{
    public WrittenEngravingItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);


        NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);

        NbtCompound nbt = nbtComponent.copyNbt();

        if(nbt.contains("money_code")){
            tooltip.add(Text.translatable("money.code", nbt.getString("money_code")).formatted(Formatting.GRAY));
        }
        else
        {
            tooltip.add(Text.translatable("money.code", "NULL").formatted(Formatting.GRAY));
        }
        if(nbt.contains("money_value")){
            tooltip.add(Text.translatable("money.value", nbt.getString("money_value")).formatted(Formatting.GRAY));
        }
        else
        {
            tooltip.add(Text.translatable("money.value", "NULL").formatted(Formatting.GRAY));
        }

    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            EngravingInfoScreen.open(user, hand);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

}
