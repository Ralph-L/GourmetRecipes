package main.java.com.ralph.GourmetRecipes;


import main.java.com.ralph.GourmetRecipes.Foods.Chocolate;
import main.java.com.ralph.GourmetRecipes.Foods.Meringue;
import main.java.com.ralph.GourmetRecipes.Machines.MixingBowl.MixingBowl;
import main.java.com.ralph.GourmetRecipes.Machines.MixingBowl.MixingBowlGuiHandler;
import main.java.com.ralph.GourmetRecipes.Machines.MixingBowl.TileEntityMixingBowl;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

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
import cpw.mods.fml.common.network.NetworkRegistry; // not used in 1.7
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="GourmetRecipes", name="GourmetRecipes", version="1.0.0")
public class GourmetRecipes {

	public static final String MODID = "GourmetRecipes";
	public static Item chocolate;
	public static Item meringue;
	public static Block mixingBowl;
	

	// The instance of your mod that Forge uses.
	@Instance(value = "GourmetRecipes")
	public static GourmetRecipes instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="main.java.com.ralph.GourmetRecipes.client.ClientProxy", serverSide="main.java.com.ralph.GourmetRecipes.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler // used in 1.6.2
	public void preInit(FMLPreInitializationEvent event) {
		
		// Mixing Bowl
		
		mixingBowl = new MixingBowl(Material.ground);

		GameRegistry.registerBlock(mixingBowl, "mixingBowl");
		GameRegistry.registerTileEntity(TileEntityMixingBowl.class, "mixingBowl");
		
		GameRegistry.registerItem(chocolate = new Chocolate("chocolate", 2, 0.2f, false, 
			    new PotionEffect(Potion.moveSpeed.id, 1200, 1), 
			    new PotionEffect(Potion.jump.id, 600, 0), 
			    new PotionEffect(Potion.regeneration.id, 200, 1))
			    .setAlwaysEdible(), "chocolate");

		GameRegistry.registerItem(meringue = new Meringue("meringue", 2, 0.2f, false, 
			    new PotionEffect(Potion.moveSpeed.id, 1200, 1), 
			    new PotionEffect(Potion.jump.id, 600, 0), 
			    new PotionEffect(Potion.regeneration.id, 200, 1))
			    .setAlwaysEdible(), "meringue");

		proxy.registerRenderers();
	}

	@EventHandler // used in 1.6.2
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();
	
	    NetworkRegistry.INSTANCE.registerGuiHandler(this, new MixingBowlGuiHandler());
		
		//*****
		ItemStack cobblestoneStack = new ItemStack(Blocks.cobblestone);
        ItemStack chocolateStack = new ItemStack(chocolate);
        ItemStack cocaoStack = new ItemStack(Items.dye, 1, 3);
        ItemStack sugarStack = new ItemStack(Items.sugar);
        ItemStack ironStack = new ItemStack(Items.iron_ingot);
        ItemStack eggStack = new ItemStack(Items.egg);
        ItemStack meringueStack = new ItemStack(meringue);
        ItemStack mixingBowlStack = new ItemStack(mixingBowl);
        
        GameRegistry.addRecipe(mixingBowlStack, " i ", "c c", "ccc",
        		'i', ironStack, 'c', cobblestoneStack);
        GameRegistry.addRecipe(chocolateStack, "xyx", "yxy", "xyx",
                'x', cocaoStack, 'y', sugarStack);
        GameRegistry.addRecipe(meringueStack, "   ", "xxx", "yyy",
                'x', sugarStack, 'y', eggStack);

	}

	@EventHandler // used in 1.6.2
	//@PostInit   // used in 1.5.2
	public void postInit(FMLPostInitializationEvent event) {
		// Stub Method
	}
}

