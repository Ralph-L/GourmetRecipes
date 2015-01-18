package com.ralph.GourmetRecipes.Foods;

import com.ralph.GourmetRecipes.GourmetRecipes;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Chocolate extends ItemFood {

	private PotionEffect[] effects;
	
	public Chocolate(String unlocalizedName, 
						int healAmount, 
						float saturationModifier, 
						boolean wolvesFavorite, 
						PotionEffect... effects) {
		// TODO Auto-generated constructor stub
	    super(healAmount, saturationModifier, wolvesFavorite);
	    this.setUnlocalizedName(unlocalizedName);
	    System.out.println("[GourmetRecipes]: " + GourmetRecipes.MODID + ":" + unlocalizedName);
	    this.setTextureName(GourmetRecipes.MODID + ":" + unlocalizedName);
	    this.setCreativeTab(CreativeTabs.tabFood);
	    this.effects = effects;

	}

	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
	    super.onFoodEaten(stack, world, player);
	    
	    for (int i = 0; i < effects.length; i ++) {
	        if (!world.isRemote && effects[i] != null && effects[i].getPotionID() > 0)
	            player.addPotionEffect(new PotionEffect(this.effects[i].getPotionID(), this.effects[i].getDuration(), this.effects[i].getAmplifier(), this.effects[i].getIsAmbient()));
	    } 

	}
	
}
