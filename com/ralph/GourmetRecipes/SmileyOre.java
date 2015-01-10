package com.ralph.GourmetRecipes;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.block.material.Material;

public class SmileyOre extends Block
{
         public SmileyOre(Material material) 
     {
             super(material);
                
                setHardness(4.0F); // 33% harder than diamond
                setStepSound(Block.soundTypeGravel);
                setBlockTextureName("newmod:SmileyOre");
                setCreativeTab(CreativeTabs.tabBlock);
                setHarvestLevel("pickaxe", 3);
        		setBlockName("SmileyOre");
        }
        

        //If the block's drop is an item.
        @Override
        public Item getItemDropped(int metadata, Random random, int fortune) {
            return GourmetRecipes.smileyDrop;
        }

/*
        //If the block's drop is a block.
        @Override
        public Item getItemDropped(int metadata, Random random, int fortune) {
            return Item.getItemFromBlock(NewMod.genericDirt);
        }
        
        //If the block's drop is a vanilla item.
        @Override
        public Item getItemDropped(int metadata, Random random, int fortune) {
            return Item.getItemById(Id);
        }
        
        //If the block's drop is a vanilla block.
        @Override
        public Item getItemDropped(int metadata, Random random, int fortune) {
            return Item.getItemFromBlock(Block.getBlockById(id));
        }
*/
}