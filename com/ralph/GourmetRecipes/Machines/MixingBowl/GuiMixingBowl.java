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

	private TileEntityMixingBowl mixingbowlInventory;

	public GuiMixingBowl(InventoryPlayer inventory, TileEntityMixingBowl gold)
	{
		super(new ContainerMixingBowl(inventory, gold));
//		System.out.println("GuiMixingBowl construct");
		mixingbowlInventory = gold;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
        //draw text and stuff here
        //the parameters for drawString are: string, x, y, color
		String txt = new String("Mixing Bowl");
		txt = txt + ": " + mixingbowlInventory.getBurnTimeRemainingScaled(14);
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
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
		System.out.println("drawTexturedModalRect: " + xSize + ", " + ySize);

		/*
		 * Render the mixing progress bar animation
		 */
		if (mixingbowlInventory.isBurning())
		{
			int burn = mixingbowlInventory.getBurnTimeRemainingScaled(14);
			drawTexturedModalRect(j + 85, k+100, 175, 0, 15, burn);
		}

//		int update = mixingbowlInventory.getCookProgressScaled(16);
//		drawTexturedModalRect(j+ 73, k+36, 176, 77,-update , -update);
	}

}
