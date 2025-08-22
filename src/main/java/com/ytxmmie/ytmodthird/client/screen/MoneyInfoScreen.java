package com.ytxmmie.ytmodthird.client.screen;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.text.Text;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.DrawContext;

import java.util.Base64;
import java.io.ByteArrayInputStream;

public class MoneyInfoScreen extends Screen {
    private final String code;
    private final String value;
    private final String imageBase64;
    private Identifier imageId;

    public MoneyInfoScreen(String code, String value, String imageBase64) {
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
        //context.drawCenteredTextWithShadow(this.textRenderer, "Code: " + code, this.width / 2, 60, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, "Value: " + value, this.width / 2, 80, 0xFFFFFF);
        if (imageId != null) {
            // 图片居中显示，大小64x64
            context.drawTexture(imageId, this.width / 2 - 32, 110, 0, 0, 64, 64, 64, 64);
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}