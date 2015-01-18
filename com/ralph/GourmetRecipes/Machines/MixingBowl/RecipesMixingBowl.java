package com.ralph.GourmetRecipes.Machines.MixingBowl;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipesMixingBowl {

	private static final RecipesMixingBowl goldOvenBase = new RecipesMixingBowl();

	/** The list of smelting results. */
	private Map goldOvenList = new HashMap();
	private Map goldOvenExperience = new HashMap();

	/**
	* Used to call methods addSmelting and getSmeltingResult.
	*/
	public static final RecipesMixingBowl smelting()
	{
	return goldOvenBase;
	}

	private RecipesMixingBowl()
	{
	addSmelting(new ItemStack(Blocks.dirt, 1, 0), new ItemStack(Blocks.cobblestone, 1, 0), 0.7F);
	}

	/**
	* Adds a smelting recipe.
	*/
	public void addSmelting(ItemStack itemStackIn, ItemStack itemStackOut, float experience)
	{
	goldOvenList.put(itemStackIn, itemStackOut);
	// NO XP!
	//this.goldOvenExperience.put(Integer.valueOf(itemStack.itemID), Float.valueOf(experience));
	}

	/**
	* Returns the smelting result of an item.
	*/
	public ItemStack getSmeltingResult(Item item)
	{
		ItemStack stone = new ItemStack(Blocks.stone);
	return stone;//(ItemStack)goldOvenList.get(Integer.valueOf(item));
	}

	public Map getSmeltingList()
	{
	return goldOvenList;
	}
	public float getExperience(int par1)
	{
	return this.goldOvenExperience.containsKey(Integer.valueOf(par1)) ? ((Float)this.goldOvenExperience.get(Integer.valueOf(par1))).floatValue() : 0.0F;
	}
	/*
	public static Object smelting() {
		// TODO Auto-generated method stub
		return null;
	}
	*/

}
