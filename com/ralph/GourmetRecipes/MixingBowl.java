package com.ralph.GourmetRecipes;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class MixingBowl extends Block {

	public MixingBowl(Material material) {
		super(material);
		this.setHardness(1.0F);
		this.setStepSound(Block.soundTypeGravel);
		this.setBlockName("mixingBowl");
		this.setBlockTextureName("gourmetrecipes:mixingbowl");
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
}
