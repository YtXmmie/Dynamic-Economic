package com.ytxmmie.ytmodthird.client;

import com.ytxmmie.ytmodthird.YtModThird;
import com.ytxmmie.ytmodthird.client.screen.PictureScreen;
import com.ytxmmie.ytmodthird.network.EngravingPayload;
import com.ytxmmie.ytmodthird.network.UploadImagePayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;

@Environment(EnvType.CLIENT)
public class EngravingScreen extends Screen {

    private static final String TITLE_KEY = "gui.ytmodthird.title";


    private String selectedFilePath;
    private String imageBase64;


    private TextFieldWidget textField;
    private TextFieldWidget value;
    private ButtonWidget cancelButton;
    private ButtonWidget doneButton;
    private ButtonWidget pictureButton;

    private final ClientPlayerEntity player;
    private final ItemStack itemStack;
    public static final Identifier ENGRAVING_TEXTURE = Identifier.of(YtModThird.Mod_ID, "textures/gui/engraving.png");

    private int left;
    private  int top;

    public EngravingScreen(ClientPlayerEntity player, ItemStack stack) {
        super(Text.translatable(TITLE_KEY));
        this.player = player;
        this.itemStack = stack;
    }

    @Override
    protected void init() {
        if (this.client == null) {
            return;
        }

        this.clearChildren();

        int halfWidth = this.width / 2;
        int halfHeight = this.height / 2;
        this.left = (this.width - 176) / 2;
        this.top = this.height / 4;

        // New name input field
        this.textField = new TextFieldWidget(
                this.client.textRenderer,
                this.left + 62,
                this.top + 26,
                103,
                12,
                Text.empty()
        );
        this.textField.setMaxLength(50);
        this.textField.setChangedListener(this::onTextChanged);
        this.textField.setDrawsBackground(false);
        this.addDrawableChild(this.textField);

        this.value = new TextFieldWidget(
                this.client.textRenderer,
                this.left + 62,
                this.top + 26 + 32,
                103,
                12,
                Text.empty()
        );
        this.value.setMaxLength(50);
        this.value.setChangedListener(this::onTextChanged);
        this.value.setDrawsBackground(false);
        this.addDrawableChild(this.value);

        this.doneButton = ButtonWidget.builder(ScreenTexts.DONE, this::onButtonClicked)
                .dimensions(this.width / 2 - 100, this.top + 48 + 10 + 32, 98, 20)
                .tooltip(Tooltip.of(ScreenTexts.DONE))
                .build();
        this.doneButton.active = true;
        this.addDrawableChild(doneButton);

        this.cancelButton = ButtonWidget.builder(ScreenTexts.CANCEL, this::onButtonClicked)
                .dimensions(this.width / 2 + 2, this.top + 48 + 10 + 32, 98, 20)
                .tooltip(Tooltip.of(ScreenTexts.CANCEL))
                .build();
        this.cancelButton.active = true;
        this.addDrawableChild(cancelButton);

        this.pictureButton = ButtonWidget.builder(Text.translatable("gui.ytmodthird.pictureButton"), this::onButtonClicked)
                .dimensions(this.width / 2 -49, this.top + 48 + 10 + 32 + 30, 98, 20)
                .tooltip(Tooltip.of(ScreenTexts.CANCEL))
                .build();
        this.pictureButton.active = true;
        this.addDrawableChild(pictureButton);



        this.setInitialFocus(this.textField);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // 257 - Enter Key
        if (keyCode == 257) {
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void onTextChanged(String string) {
    }

    private boolean canApply() {
        return true;
    }

    private void onButtonClicked(ButtonWidget button) {

        if (button == this.doneButton && !this.textField.getText().isEmpty() && !this.value.getText().isEmpty()) {

            NbtComponent nbtComponent = this.itemStack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);

            NbtCompound nbt = nbtComponent.copyNbt();

            nbt.putString("money_code", this.textField.getText());
            nbt.putString("money_value", this.value.getText());

            ClientPlayNetworking.send(new EngravingPayload(nbt));

            if (imageBase64 != null) {
                // 发送网络包到服务端（伪代码，需根据你的mod网络实现补充）
                // ModNetwork.sendToServer(new UploadImagePacket(imageBase64));
                //ClientPlayNetworking.send(new UploadImagePayload(imageBase64));
                nbt.putString("image", imageBase64);
            }

            this.close();
        }else if (button == this.cancelButton) {
            this.close();
        } else if (button == this.pictureButton) {
            // 打开图片上传界面
            //this.client.setScreen(new PictureScreen());
            PointerBuffer filterPatterns = MemoryUtil.memAllocPointer(3);
            filterPatterns.put(MemoryUtil.memUTF8("*.png"));
            filterPatterns.put(MemoryUtil.memUTF8("*.jpg"));
            filterPatterns.put(MemoryUtil.memUTF8("*.jpeg"));
            filterPatterns.flip();

            String filePath = TinyFileDialogs.tinyfd_openFileDialog(
                    "Select an Image",
                    null,
                    filterPatterns,
                    "Image Files",
                    false
            );

            MemoryUtil.memFree(filterPatterns);

            if (filePath != null) {
                selectedFilePath = filePath;
                try {
                    byte[] bytes = Files.readAllBytes(new File(filePath).toPath());
                    imageBase64 = Base64.getEncoder().encodeToString(bytes);
                } catch (Exception e) {
                    e.printStackTrace(); // 打印异常方便调试
                    imageBase64 = null;
                }
            }
        }
    }


    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        assert this.client != null;
        if (this.client.world == null) {
            this.renderPanoramaBackground(context, delta);
        }

        this.applyBlur(delta);
        this.renderDarkening(context);
        context.drawTexture(ENGRAVING_TEXTURE, this.left, this.top, 0, 0, 176, 80);

    }


    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        int halfWidth = this.width / 2;
        int halfHeight = this.height / 2;

        this.renderBackground(drawContext, mouseX, mouseY, delta);
        this.textRenderer.draw(
                this.getTitle(),
                this.left + 62 -5,
                this.top + 10,
                0xFFFFFF,
                true,
                drawContext.getMatrices().peek().getPositionMatrix(),
                drawContext.getVertexConsumers(),
                TextRenderer.TextLayerType.NORMAL,
                0,
                0x0000F0
        );

        Text numText = Text.translatable("gui.ytmodthird.title2");

        this.textRenderer.draw(
                numText,
                this.left + 62 - 5,
                this.top + 10 + 32,
                0xFFFFFF,
                true,
                drawContext.getMatrices().peek().getPositionMatrix(),
                drawContext.getVertexConsumers(),
                TextRenderer.TextLayerType.NORMAL,
                0,
                0x0000F0
        );
        super.render(drawContext, mouseX, mouseY, delta);

        // 在 EngravingScreen.java 的 render 方法末尾添加
        // 在 render 方法里修改如下：

        int base64Length = imageBase64 != null ? imageBase64.length() : 0;
        boolean imageTooLarge = base64Length > 65535;

        // 移动提示文字位置（比如 Y 坐标改为 this.top + 140、160、180）
        // 渲染提示文字，Y 坐标更紧凑
        // 渲染提示文字，Y 坐标更靠上
        // 按钮底部 Y 坐标
        // 计算最下面按钮底部 Y 坐标
        int pictureButtonBottomY = this.top + 48 + 10 + 32 + 30 + 20;
        int textStartY = pictureButtonBottomY + 8; // 与按钮间隔 8 像素

        drawContext.drawCenteredTextWithShadow(this.textRenderer,
                "图片建议尺寸 <= 128x128，Base64长度 <= 65535字符", this.width / 2, textStartY, 0xFF5555);

        drawContext.drawCenteredTextWithShadow(this.textRenderer,
                "当前图片Base64长度: " + base64Length, this.width / 2, textStartY + 14, imageTooLarge ? 0xFF0000 : 0xAAAAAA);

        if (imageTooLarge) {
            drawContext.drawCenteredTextWithShadow(this.textRenderer,
                    "图片过大，无法上传！", this.width / 2, textStartY + 28, 0xFF0000);
        }

        // 设置 done 按钮状态
        this.doneButton.active = !imageTooLarge;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    public static void open(PlayerEntity player, ItemStack stack) {
        MinecraftClient.getInstance().setScreen(new EngravingScreen((ClientPlayerEntity) player, stack));
    }
}