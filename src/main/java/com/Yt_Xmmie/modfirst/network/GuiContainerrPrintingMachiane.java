package com.Yt_Xmmie.modfirst.network;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import com.Yt_Xmmie.modfirst.Main;
import com.Yt_Xmmie.modfirst.block.tileentity.ContainerPrintingMachiane;
import com.Yt_Xmmie.modfirst.packet.PacketName;
import com.Yt_Xmmie.modfirst.util.Reference;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiContainerrPrintingMachiane extends GuiContainer implements IContainerListener {

    public static final int WIDTH = 176;
    public static final int HEIGHT = 161;

    private static final ResourceLocation GUI_BACKGROUND = new ResourceLocation(Reference.Mod_ID, "textures/gui/gui_printing_machine.png");

    private final ContainerPrintingMachiane printingMachine;
    private GuiTextField nameField;

    public GuiContainerrPrintingMachiane(ContainerPrintingMachiane container) {
        super(container);
        this.printingMachine = container;
        this.xSize = WIDTH;
        this.ySize = HEIGHT;
    }

    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.nameField = new GuiTextField(0, this.fontRenderer, i + 62, j + 24, 103, 12);
        this.nameField.setTextColor(-1);
        this.nameField.setDisabledTextColour(-1);
        this.nameField.setEnableBackgroundDrawing(false);
        this.nameField.setMaxStringLength(50);
        this.inventorySlots.removeListener(this);
        this.inventorySlots.addListener(this);
        this.nameField.setText(I18n.format("moneyvalue"));
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
        this.inventorySlots.removeListener(this);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        this.nameField.drawTextBox();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (this.nameField.textboxKeyTyped(typedChar, keyCode))
        {
            this.setValue();
        }
        else
        {
            super.keyTyped(typedChar, keyCode);
        }
    }

    private void setValue()
    {
        String value = this.nameField.getText();
        this.printingMachine.updateMoneyValue(value);
        Main.network.sendToServer(new PacketName(value));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.nameField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_BACKGROUND);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(i + 59, j + 20, 0, this.ySize + (this.inventorySlots.getSlot(6).getHasStack()
                ? 0 : 16), 110, 16);

        if (this.inventorySlots.getSlot(0).getHasStack() && !this.inventorySlots.getSlot(6).getHasStack())
        {
            this.drawTexturedModalRect(i + 120, j + 47, this.xSize, 0, 28, 21);
        }
    }

    @Override
    public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList)
    {
        this.sendSlotContents(containerToSend, 6, containerToSend.getSlot(6).getStack());
    }

    @Override
    public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack)
    {
        if (slotInd == 6) {

            this.nameField.setText(stack.isEmpty() ? "" : this.nameField.getText());
            this.nameField.setEnabled(!stack.isEmpty());
        }
    }

    public void sendWindowProperty(Container containerIn, int varToUpdate, int newValue)
    {
        //NO-OP
    }

    public void sendAllWindowProperties(Container containerIn, IInventory inventory)
    {
        //NO-OP
    }
}