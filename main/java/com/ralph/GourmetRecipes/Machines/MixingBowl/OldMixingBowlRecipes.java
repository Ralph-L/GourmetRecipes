package com.ralph.GourmetRecipes.Machines.MixingBowl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

/**
 * Store a private list of recipes
 * @see Source: http://www.minecraftforge.net/forum/index.php?topic=4743.0
 */
public class OldMixingBowlRecipes {

	private static final OldMixingBowlRecipes instance = new OldMixingBowlRecipes();
	private static List recipes = new ArrayList();

	public static final OldMixingBowlRecipes getInstance() {
		return instance;
	}
	public OldMixingBowlRecipes() {

		addRecipe(new ItemStack(Items.egg), new Object[]
				{
			"   ",
			" e ",
			"   ",
			Character.valueOf('e'), Items.egg
				});

	}

	void addRecipe(ItemStack par1ItemStack, Object par2ArrayOfObj[])
	{
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;

		if (par2ArrayOfObj[i] instanceof String[])
		{
			String as[] = (String[])par2ArrayOfObj[i++];

			for (int l = 0; l < as.length; l++)
			{
				String s2 = as[l];
				k++;
				j = s2.length();
				s = (new StringBuilder()).append(s).append(s2).toString();
			}
		}
		else
		{
			while (par2ArrayOfObj[i] instanceof String)
			{
				String s1 = (String)par2ArrayOfObj[i++];
				k++;
				j = s1.length();
				s = (new StringBuilder()).append(s).append(s1).toString();
			}
		}

		HashMap hashmap = new HashMap();

		for (; i < par2ArrayOfObj.length; i += 2)
		{
			Character character = (Character)par2ArrayOfObj[i];
			ItemStack itemstack = null;

			if (par2ArrayOfObj[i + 1] instanceof Item)
			{
				itemstack = new ItemStack((Item)par2ArrayOfObj[i + 1]);
			}
			else if (par2ArrayOfObj[i + 1] instanceof Block)
			{
				itemstack = new ItemStack((Block)par2ArrayOfObj[i + 1], 1, -1);
			}
			else if (par2ArrayOfObj[i + 1] instanceof ItemStack)
			{
				itemstack = (ItemStack)par2ArrayOfObj[i + 1];
			}

			hashmap.put(character, itemstack);
		}

		ItemStack aitemstack[] = new ItemStack[j * k];

		for (int i1 = 0; i1 < j * k; i1++)
		{
			char c = s.charAt(i1);

			if (hashmap.containsKey(Character.valueOf(c)))
			{
				aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c))).copy();
			}
			else
			{
				aitemstack[i1] = null;
			}
		}

		recipes.add(new ShapedRecipes(j, k, aitemstack, par1ItemStack));
	}

	public void addShapelessRecipe(ItemStack par1ItemStack, Object par2ArrayOfObj[])
	{
		ArrayList arraylist = new ArrayList();
		Object aobj[] = par2ArrayOfObj;
		int i = aobj.length;

		for (int j = 0; j < i; j++)
		{
			Object obj = aobj[j];

			if (obj instanceof ItemStack)
			{
				arraylist.add(((ItemStack)obj).copy());
				continue;
			}

			if (obj instanceof Item)
			{
				arraylist.add(new ItemStack((Item)obj));
				continue;
			}

			if (obj instanceof Block)
			{
				arraylist.add(new ItemStack((Block)obj));
			}
			else
			{
				throw new RuntimeException("Invalid shapeless recipe!");
			}
		}

		recipes.add(new ShapelessRecipes(par1ItemStack, arraylist));
	}

	public static ItemStack findMatchingRecipe(InventoryCrafting par1InventoryCrafting, World par2World)
	{
		System.out.println("Finding matching recipe...");
		int i = 0;
		ItemStack itemstack = null;
		ItemStack itemstack1 = null;

		for (int j = 0; j < par1InventoryCrafting.getSizeInventory(); j++)
		{
			ItemStack itemstack2 = par1InventoryCrafting.getStackInSlot(j);

			if (itemstack2 == null)
			{
				continue;
			}

			if (i == 0)
			{
				itemstack = itemstack2;
			}

			if (i == 1)
			{
				itemstack1 = itemstack2;
			}

			i++;
		}

		if (i == 2 && itemstack.getItem() == itemstack1.getItem() && itemstack.stackSize == 1 && itemstack1.stackSize == 1 && itemstack.getItem().isDamageable()) //    Item.itemsList[itemstack.getItem()].isDamageable())
		{
			Item item = itemstack.getItem(); // = Item.itemsList[itemstack.getItem()];
			int l = item.getMaxDamage() - itemstack.getItemDamageForDisplay();
			int i1 = item.getMaxDamage() - itemstack1.getItemDamageForDisplay();
			int j1 = l + i1 + (item.getMaxDamage() * 10) / 100;
			int k1 = item.getMaxDamage() - j1;

			if (k1 < 0)
			{
				k1 = 0;
			}

			return new ItemStack(itemstack.getItem(), 2, k1);
		}

		for (int k = 0; k < recipes.size(); k++)
		{
			IRecipe irecipe = (IRecipe)recipes.get(k);

			if (irecipe.matches(par1InventoryCrafting, par2World))
			{
				return irecipe.getCraftingResult(par1InventoryCrafting);
			}
		}

		
		/* Test for egg in middle slot
		ItemStack itemstack2 = par1InventoryCrafting.getStackInSlot(4);
		if (itemstack2.getItem() == Items.egg) {
			System.out.println("Egg in slot 4!");
//			TileEntityMixingBowl.mixingbowlMixTime = 300;
			return null;
		}
		*/
		return null;
	}

	/**
	 * returns the List<> of all recipes
	 */
	public List getRecipeList()
	{
		return recipes;
	}
	
	/** NEW! Needs to be fixed!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * 
	 * @return
	 */
	public static Object smelting() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static ItemStack getSmeltingResult(Item item) {
		return new ItemStack(Blocks.stone);
	}
	
}

