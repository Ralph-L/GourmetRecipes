package com.ralph.GourmetRecipes;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class SmileyDrop extends Item {
	public SmileyDrop() {
		setHarvestLevel("shovel", 4);
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("smileyDrop");
		setTextureName("newmod:SmileyDrop");
	}
}
