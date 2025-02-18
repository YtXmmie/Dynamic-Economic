package com.ytxmmie.ytmodthird.client;

import com.ytxmmie.ytmodthird.YtModThird;
import com.ytxmmie.ytmodthird.client.screen.PrinterScreenHandler;
import com.ytxmmie.ytmodthird.entity.PrinterBlockEntity;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class PrinterScreen extends HandledScreen <PrinterScreenHandler>{
    private static final Identifier TEXTURE = Identifier.of(YtModThird.Mod_ID, "textures/gui/printer_gui.png");

    public PrinterScreen(PrinterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 176;
        this.backgroundHeight = 184;
        this.titleY = 4;
        this.playerInventoryTitleY = 92;
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        //ink progress
        int inkProgress = handler.getInkProgress();
        context.drawTexture(TEXTURE, x + 5, y + 42, 176, 55, 18, inkProgress);

        int ink = handler.getInk();
        context.drawTexture(TEXTURE, x + 37, y + 18 + (72 - ink), 180, 85, 6, ink);

        //process progress
        int processProgress = handler.getProcessProgress();
        context.drawTexture(TEXTURE, x + 90, y + 47, 176, 14, processProgress + 1, 16);

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(client == null || client.player == null || client.player.isSpectator()){
            return super.mouseClicked(mouseX, mouseY, button);
        }

        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;


        return super.mouseClicked(mouseX, mouseY, button);
    }

    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, 4210752, false);

        if(mouseY >= y + 46 && mouseY <= y + 62 && mouseX >= x + 68 && mouseX <= x + 84 && handler.getSlot(PrinterBlockEntity.SLOT_INPUT).getStack().isEmpty()) {

                context.drawTooltip(
                        this.textRenderer,
                        List.of(

                                Text.translatable("gui.ytmodthird.printer.tooltip.input1"),
                                Text.translatable("gui.ytmodthird.printer.tooltip.input2"),
                                Text.translatable("gui.ytmodthird.printer.tooltip.input3")
                                ),
                        mouseX - this.x,
                        mouseY - this.y
                );
            }
        if (mouseX >= x + 93 && mouseX <= x + 109 && mouseY >= y + 21 && mouseY <= y + 37 && handler.getSlot(PrinterBlockEntity.SLOT_ADDITION).getStack().isEmpty()) {
               //Rename
               context.drawTooltip(
                       this.textRenderer,
                        List.of(
                            Text.translatable("gui.ytmodthird.printer.tooltip.addition1"),
                                Text.translatable("gui.ytmodthird.printer.tooltip.addition2"),
                                Text.translatable("gui.ytmodthird.printer.tooltip.addition3")
                        ),
                        mouseX - this.x,
                        mouseY - this.y
               );
        }

    }
}
