package com.ralph.GourmetRecipes;

//This Import list will grow longer with each additional tutorial.
//It's not pruned between full class postings, unlike other tutorial code.
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.block.material.Material;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler; // used in 1.6.2
//import cpw.mods.fml.common.Mod.PreInit;    // used in 1.5.2
//import cpw.mods.fml.common.Mod.Init;       // used in 1.5.2
//import cpw.mods.fml.common.Mod.PostInit;   // used in 1.5.2
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
//import cpw.mods.fml.common.network.NetworkMod; // not used in 1.7
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="RalphsMod", name="GourmetRecipes", version="0.0.0")
//@NetworkMod(clientSideRequired=true) // not used in 1.7
public class GourmetRecipes {

	// See Basic items tutorial for Generic Ingot
	public static Item smileyDrop;
	public static Item smileyIngot;
	public static Block smileyBlock;
	public static Block smileyOre;


	// The instance of your mod that Forge uses.
	@Instance(value = "RalphsMod")
	public static GourmetRecipes instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="com.ralph.GourmetRecipes.client.ClientProxy", serverSide="com.ralph.GourmetRecipes.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler // used in 1.6.2
	//@PreInit    // used in 1.5.2
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println("Instantiating: ground");
		smileyBlock = new SmileyBlock();

		smileyOre = new SmileyOre(Material.rock);
		smileyDrop = new SmileyDrop();
		smileyIngot = new SmileyIngot();
		
		GameRegistry.registerBlock(smileyBlock, "smileyBlock");
		GameRegistry.registerBlock(smileyOre, "smileyOre");
		GameRegistry.registerItem(smileyDrop, "smileyDrop");
		// End Basic Blocks

		proxy.registerRenderers();
	}

	@EventHandler // used in 1.6.2
	//@Init       // used in 1.5.2
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();
		//*****
        ItemStack smileyBlockStack            = new ItemStack(smileyBlock);
        ItemStack smileyOreStack  = new ItemStack(smileyOre);
        ItemStack smileyDropStack  = new ItemStack(smileyDrop);
        ItemStack smileyIngotStack        = new ItemStack(smileyIngot);


        
        
        
//        GameRegistry.addShapelessRecipe(diamondsStack, dirtStack);

//        GameRegistry.addShapelessRecipe(diamondsStack, dirtStack, dirtStack,
//                dirtStack, dirtStack, dirtStack, dirtStack, new ItemStack(
//                        Block.sand), gravelStack, cobbleStack);
/*
        GameRegistry.addRecipe(new ItemStack(Block.cobblestone), "xy", "yx",
                'x', dirtStack, 'y', gravelStack);

        GameRegistry.addRecipe(new ItemStack(Block.stone), "xyx", "y y", "xyx",
                'x', dirtStack, 'y', gravelStack);

        GameRegistry.addSmelting(Block.stone.blockID, new ItemStack(
                Block.stoneBrick), 0.1f);

        FurnaceRecipes.smelting().addSmelting(Block.cloth.blockID, 15,
                new ItemStack(Block.cloth, 1, 0), 0.1f);
        //*****
*/
	}

	@EventHandler // used in 1.6.2
	//@PostInit   // used in 1.5.2
	public void postInit(FMLPostInitializationEvent event) {
		// Stub Method
	}
}
