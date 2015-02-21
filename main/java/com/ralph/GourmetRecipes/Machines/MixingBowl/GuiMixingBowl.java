package com.ralph.GourmetRecipes.Machines.MixingBowl;

import org.lwjgl.opengl.GL11;


import cpw.mods.fml.common.network.IGuiHandler;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiMixingBowl extends GuiContainer {

	private TileEntityMixingBowl tileEntity;
	
	public GuiMixingBowl(InventoryPlayer inventory, TileEntityMixingBowl tile)
	{
		super(new ContainerMixingBowl(inventory, tile));
		tileEntity = tile;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String txt = "Mixing Bowl: ";
		txt = txt + tileEntity.mixingbowlMixedTime + "/" + tileEntity.mixingbowlMixTime;
        //draw text and stuff here
        //the parameters for drawString are: string, x, y, color
		fontRendererObj.drawString(txt, 8, 6, 4210752);
        //draws "Inventory" or your regional equivalent
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, (ySize - 96) + 2, 0xffffff);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		final ResourceLocation texture = new ResourceLocation("gourmetrecipes:textures/gui/mixingbowlgui.png");
		mc.renderEngine.bindTexture(texture);
		/* Calculate how to center image on screen */
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		/* Draw the background
		 * j,k top left where image will go
		 * 0,0 Offset in .png file where image obtained from
		 * x,y size of image to draw */
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

		/* Render the mixing progress bar animation */
		if (tileEntity.isMixing())
		{
			/* This calculates the pixel width of the coloured box to draw */
			int w = tileEntity.getMixProgress() * 43 / 100;
			/* j+76, k+20 marks the top left of where the progress bar is to be placed
			 * 176,60 marks the top left of the coloured bar to copy
			 * w,17 is width,height of coloured bar to copy, remember h+ is down
			 * */
			drawTexturedModalRect(j + 76, k + 20, 176, 60, w, 17);
		}
	}

}
