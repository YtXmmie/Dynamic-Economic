package com.ytxmmie.ytmodthird.client.screen;
import com.ytxmmie.ytmodthird.client.EngravingScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.text.Text;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.DrawContext;

import java.util.Base64;
import java.io.ByteArrayInputStream;

public class EngravingInfoScreen extends Screen {
    private final String code;
    private final String value;
    private final String imageBase64;
    private Identifier imageId;

    public EngravingInfoScreen(String code, String value, String imageBase64) {
        super(Text.translatable("gui.ytmodthird.info"));
        this.code = code;
        this.value = value;
        this.imageBase64 = imageBase64;

        if (imageBase64 != null && !imageBase64.isEmpty()) {
            try {
                byte[] bytes = Base64.getDecoder().decode(imageBase64);
                NativeImage nativeImage = NativeImage.read(new ByteArrayInputStream(bytes));
                NativeImageBackedTexture texture = new NativeImageBackedTexture(nativeImage);
                imageId = MinecraftClient.getInstance().getTextureManager().registerDynamicTexture("engraving_image", texture);
            } catch (Exception e) {
                e.printStackTrace(); // 打印异常方便调试
                imageId = null;
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        // 信息和图片整体上移
        context.drawCenteredTextWithShadow(this.textRenderer, "Code: " + code, this.width / 2, 40, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, "Value: " + value, this.width / 2, 60, 0xFFFFFF);

        int maxSize = 128;
        if (imageId != null) {
            NativeImage nativeImage = MinecraftClient.getInstance().getTextureManager().getTexture(imageId) instanceof NativeImageBackedTexture tex
                    ? tex.getImage() : null;
            if (nativeImage != null) {
                int imgW = nativeImage.getWidth();
                int imgH = nativeImage.getHeight();
                float scale = Math.min((float)maxSize / imgW, (float)maxSize / imgH);
                int drawW = (int)(imgW * scale);
                int drawH = (int)(imgH * scale);
                int drawX = this.width / 2 - drawW / 2;
                int drawY = 80; // 图片整体上移
                context.drawTexture(imageId, drawX, drawY, 0, 0, drawW, drawH, drawW, drawH);
            }
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    public static void open(PlayerEntity user, Hand hand) {

        ItemStack stack = user.getStackInHand(hand);
        NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
        NbtCompound nbt = nbtComponent.copyNbt();
        String code = nbt.getString("money_code");
        String value = nbt.getString("money_value");
        String imageBase64 = nbt.getString("image");
        MinecraftClient.getInstance().setScreen(new EngravingInfoScreen(code, value, imageBase64));
    }
}