package com.ralph.GourmetRecipes.Machines.MixingBowl;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class SlotMixingBowl extends Slot {
	/** The player that is using the GUI where this slot resides. */
	private EntityPlayer player;
	private int field_75228_b;
	public SlotMixingBowl(EntityPlayer par1EntityPlayer, IInventory par2IInventory, int par3, int par4, int par5)
	{
		 super(par2IInventory, par3, par4, par5);
		 this.player = par1EntityPlayer;
//		 System.out.println("SlotMixingBowl: " + par1EntityPlayer + ", " + par2IInventory + ", " + par3 + ", " + par4 + ", " + par5);
	}
	/**
		 * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
		 */
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		 return false;
	}
	/**
		 * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
		 * stack.
		 */
	public ItemStack decrStackSize(int par1)
	{
		 if (this.getHasStack())
		 {
			 this.field_75228_b += Math.min(par1, this.getStack().stackSize);
		 }
		 return super.decrStackSize(par1);
	}
	/**
		 * Called when the player picks up an item from an inventory slot
		 */
	public void func_82870_a(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
	{
		 this.onCrafting(par2ItemStack);
		 super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
	}
	/**
		 * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
		 * internal count then calls onCrafting(item).
		 */
	protected void onCrafting(ItemStack par1ItemStack, int par2)
	{
		 this.field_75228_b += par2;
		 this.onCrafting(par1ItemStack);
	}
	/**
		 * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
		 */
	protected void onCrafting(ItemStack par1ItemStack)
	{
		 par1ItemStack.onCrafting(this.player.worldObj, this.player, this.field_75228_b);
		 if (!this.player.worldObj.isRemote)
		 {
			 int var2 = this.field_75228_b;
			 float var3 = 0.8F; // RecipesGoldOven.smelting().getExperience(par1ItemStack.itemID);
			 int var4;
			 if (var3 == 0.0F)
			 {
				 var2 = 0;
			 }
			 else if (var3 < 1.0F)
			 {
				 var4 = MathHelper.floor_float((float)var2 * var3);
				 if (var4 < MathHelper.ceiling_float_int((float)var2 * var3) && (float)Math.random() < (float)var2 * var3 - (float)var4)
				 {
					 ++var4;
				 }
				 var2 = var4;
			 }
			 while (var2 > 0)
			 {
				 var4 = EntityXPOrb.getXPSplit(var2);
				 var2 -= var4;
				 this.player.worldObj.spawnEntityInWorld(new EntityXPOrb(this.player.worldObj, this.player.posX, this.player.posY + 0.5D, this.player.posZ + 0.5D, var4));
			 }
		 }
		 this.field_75228_b = 0;
	}
}
