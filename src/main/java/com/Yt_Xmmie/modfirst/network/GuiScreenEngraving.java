package com.Yt_Xmmie.modfirst.network;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import com.Yt_Xmmie.modfirst.Main;
import com.Yt_Xmmie.modfirst.packet.PacketEngraving;

@SideOnly(Side.CLIENT)
public class GuiScreenEngraving extends GuiScreen
{
    /** Text field containing the command block's command. */
    private GuiTextField moneykey;
    private GuiButton doneButton;
    
    public GuiScreenEngraving(EntityPlayer entityplayer)
    {
    }

    @Override
    public void updateScreen()
    {
    	moneykey.updateCursorCounter();
    }

    @Override
    public void initGui()
    {
    	Keyboard.enableRepeatEvents(true);
    	buttonList.clear();
    	doneButton = new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12 , I18n.format("gui.done"));
    	buttonList.add(doneButton);
    	buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12 +22 , I18n.format("gui.cancel")));
    	moneykey = new GuiTextField(0, fontRenderer, width / 2 - 150, 60, 300, 20);
    	moneykey.setMaxStringLength(50);
    	moneykey.setText(I18n.format("moneykey"));
    	moneykey.setFocused(true);
    	doneButton.enabled = moneykey.getText().trim().length() > 0;
    }
    @Override
    public void onGuiClosed()
    {
    	Keyboard.enableRepeatEvents(false);
    }
    @Override
    protected void actionPerformed(GuiButton par1GuiButton)
    {
    	if(par1GuiButton == doneButton)
    	{
    		if(par1GuiButton.enabled)
    		{
    			String key = moneykey.getText(); 
    			PacketEngraving packet = new PacketEngraving(key);
    			Main.network.sendToServer(packet);
    		}
    	}
    	mc.displayGuiScreen((GuiScreen)null);
    }
    
    @Override
    protected void keyTyped(char par1,int par2)
    {
    	moneykey.textboxKeyTyped(par1, par2);
    	doneButton.enabled = moneykey.getText().trim().length() > 0;
    	
    	if(!(par2 != 28 && par2 != 156))
    	{
    		actionPerformed(doneButton);
    	}
    }
    
    @Override
    protected void mouseClicked(int par1, int par2, int par3)
    {
    	try 
    	{
    		super.mouseClicked(par1, par2, par3);
    	}
    	catch (IOException e)
    	{
    		e.printStackTrace();
    	}
    	moneykey.mouseClicked(par1, par2, par3);
    }
    
    @Override
    public void drawScreen(int par1, int par2, float par3)
    {
    	drawDefaultBackground();
    	moneykey.drawTextBox();
    	super.drawScreen(par1, par2, par3);;
    }
}