package com.ytxmmie.ytmodthird.client.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;


import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryUtil;

public class PictureScreen extends Screen {

    private ButtonWidget selectButton;
    private ButtonWidget uploadButton;
    private String selectedFilePath;
    private String imageBase64;

    public PictureScreen() {
        super(Text.translatable("gui.ytmodthird.picture.title"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        selectButton = ButtonWidget.builder(
                Text.translatable("gui.ytmodthird.picture.select"),
                button -> onSelectPicture()
        ).dimensions(centerX - 60, centerY - 20, 120, 20).build();
        this.addDrawableChild(selectButton);

        uploadButton = ButtonWidget.builder(
                Text.translatable("gui.ytmodthird.picture.upload"),
                button -> onUploadPicture()
        ).dimensions(centerX - 60, centerY + 10, 120, 20).build();
        this.addDrawableChild(uploadButton);
    }


    private void onSelectPicture() {
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
                imageBase64 = null;
            }
        }
    }

    private void onUploadPicture() {
        if (imageBase64 != null) {
            // 发送网络包到服务端（伪代码，需根据你的mod网络实现补充）
            // ModNetwork.sendToServer(new UploadImagePacket(imageBase64));
        }
        this.close();
    }

    @Override
    public void close() {
        this.client.setScreen(null);
    }
}