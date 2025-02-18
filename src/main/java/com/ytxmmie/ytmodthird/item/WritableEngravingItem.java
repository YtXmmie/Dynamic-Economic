package com.ytxmmie.ytmodthird.item;

import com.ytxmmie.ytmodthird.client.EngravingScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class WritableEngravingItem extends Item{


    public WritableEngravingItem(Item.Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack itemStack = user.getStackInHand(hand);
        if (world.isClient) {
            EngravingScreen.open(user, itemStack);
        }
        else {
            itemStack.decrementUnlessCreative(1, user);
        }
        return TypedActionResult.consume(itemStack);
    }
}
