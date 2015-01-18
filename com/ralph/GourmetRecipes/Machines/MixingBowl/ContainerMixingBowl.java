package com.ralph.GourmetRecipes.Machines.MixingBowl;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.Iterator;
import java.util.List;



public class ContainerMixingBowl extends Container {

	private TileEntityMixingBowl goldOven;
	private int lastGoldOvenCookTime;
	private int lastGoldOvenBurnTime;
	private int lastGoldOvenItemBurnTime;
	//	protected TileEntityMixingBowl tileEntity;

	public ContainerMixingBowl(InventoryPlayer par1InventoryPlayer,
			TileEntityMixingBowl par2TileEntityGoldOven) {
		// TODO Auto-generated constructor stub
		lastGoldOvenCookTime = 0;
		lastGoldOvenBurnTime = 0;
		lastGoldOvenItemBurnTime = 0;
		goldOven = par2TileEntityGoldOven;
		/*
		addSlotToContainer(new SlotMixingBowl(par1InventoryPlayer.player, par2TileEntityGoldOven, 0, 90, 56));
		addSlotToContainer(new SlotMixingBowl(par1InventoryPlayer.player, par2TileEntityGoldOven, 1, 54, 56));
		addSlotToContainer(new SlotMixingBowl(par1InventoryPlayer.player, par2TileEntityGoldOven, 2, 51, 17));
		*/
		/* Input ingredients */
		int n=0;
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				addSlotToContainer(new SlotMixingBowl(par1InventoryPlayer.player, par2TileEntityGoldOven, n, 18*(i+1), 18*(j+1)));
				n++;
			}
		}

		/* Output result */
		addSlotToContainer(new SlotMixingBowl(par1InventoryPlayer.player, par2TileEntityGoldOven, 9, 122, 18));

		/* Player Inventory */
		for (int i = 0; i < 3; i++)
		{
			for (int k = 0; k < 9; k++)
			{
				addSlotToContainer(new Slot(par1InventoryPlayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
			}
		}

		for (int j = 0; j < 9; j++)
		{
			addSlotToContainer(new Slot(par1InventoryPlayer, j, 8 + j * 18, 142));
		}
	}

	/**
	 * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
	 */
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		Iterator var1 = this.crafters.iterator();
		while (var1.hasNext())
		{
			ICrafting var2 = (ICrafting)var1.next();
			if (this.lastGoldOvenCookTime != this.goldOven.mixingbowlMixedTime)
			{
				var2.sendProgressBarUpdate(this, 0, this.goldOven.mixingbowlMixedTime);
			}
			if (this.lastGoldOvenBurnTime != this.goldOven.mixingbowlMixTime)
			{
				var2.sendProgressBarUpdate(this, 1, this.goldOven.mixingbowlMixTime);
			}
			if (this.lastGoldOvenItemBurnTime != this.goldOven.mixingbowlItemMixTime)
			{
				var2.sendProgressBarUpdate(this, 2, this.goldOven.mixingbowlItemMixTime);
			}
		}
		this.lastGoldOvenCookTime = this.goldOven.mixingbowlMixedTime;
		this.lastGoldOvenBurnTime = this.goldOven.mixingbowlMixTime;
		this.lastGoldOvenItemBurnTime = this.goldOven.mixingbowlItemMixTime;
	}

	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
		{
			goldOven.mixingbowlMixedTime = par2;
		}

		if (par1 == 1)
		{
			goldOven.mixingbowlMixTime = par2;
		}

		if (par1 == 2)
		{
			goldOven.mixingbowlItemMixTime = par2;
		}
	}


	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		// TODO Auto-generated method stub
		return goldOven.isUseableByPlayer(par1EntityPlayer);
		//		return false;
	}

	/**
	 * Called to transfer a stack from one inventory to the other eg. when shift clicking.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotnumber)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)inventorySlots.get(slotnumber);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotnumber == 2)
			{
				if (!mergeItemStack(itemstack1, 3, 39, true))
				{
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (slotnumber == 1 || slotnumber == 0)
			{
				if (!mergeItemStack(itemstack1, 3, 39, false))
				{
					return null;
				}
			}
			//		 else if (RecipesGoldOven.smelting().getSmeltingResult(itemstack1.getItem().itemID) != null)
			else if (RecipesMixingBowl.smelting().getSmeltingResult(itemstack1.getItem()) != null)
			{
				if (!mergeItemStack(itemstack1, 0, 1, false))
				{
					return null;
				}
			}
			else if (TileEntityMixingBowl.isItemFuel(itemstack1))
			{
				if (!mergeItemStack(itemstack1, 1, 2, false))
				{
					return null;
				}
			}
			else if (slotnumber >= 3 && slotnumber < 30)
			{
				if (!mergeItemStack(itemstack1, 30, 39, false))
				{
					return null;
				}
			}
			else if (slotnumber >= 30 && slotnumber < 39 && !mergeItemStack(itemstack1, 3, 30, false))
			{
				return null;
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack(null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(player, itemstack);
		}

		return itemstack;
	}

}
