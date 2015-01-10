package com.ralph.GourmetRecipes;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class SmileyBlock extends Block 
{

	public SmileyBlock () 
	{
		super(Material.ground);
		setHarvestLevel("shovel", 0);
		setHardness(0.5F);
		setStepSound(Block.soundTypeGravel);
		setCreativeTab(CreativeTabs.tabBlock);
		setBlockTextureName("newmod:SmileyBlock");
		setBlockName("SmileyBlock");
		setLightLevel(15);

	}

}
