package com.ralph.GourmetRecipes.Machines.MixingBowl;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;



public class ContainerMixingBowl extends Container {

	protected TileEntityMixingBowl mixingBowl;
	private int lastmixingBowlMixTime;

//	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
//	public IInventory craftResult = new InventoryCraftResult();
	
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;
	
	/** Number of slots in mixing grid */
	public static int inputSlotNumber = 9;
	
	/** Number of slots in mixing grid including output slot */
	public static int inOutputSlotNumber = inputSlotNumber + 1;
	
	/** Inventory slots including hotbar */
	public static int inventorySlotNumber = 36;
	
	/** Number of inventory slots including hotbar and output slot */
	public static int inventoryOutputSlotNumber = inventorySlotNumber + 1;

	/** Total number of all slots */
	public static int fullSlotNumber = inventorySlotNumber + inOutputSlotNumber;
	
	public ContainerMixingBowl(InventoryPlayer inventory,
			TileEntityMixingBowl tile) {
		// TODO Auto-generated constructor stub
		lastmixingBowlMixTime = 0;
		mixingBowl = tile;
		worldObj = tile.getWorldObj();
		posX = tile.xCoord;
		posY = tile.yCoord;
		posZ = tile.zCoord;

		/*
		Slot(IInventory, slot, xDisplayPos, yDisplayPos)
		addSlotToContainer(slot)
		*/
		
		/* Output result */
//		addSlotToContainer(new SlotCrafting(inventory.player, this.craftMatrix, craftResult, 9, 123, 19));
//		addSlotToContainer(new Slot(this.craftResult, 9, 123, 19));
		
		addSlotToContainer(new SlotMixingBowl(inventory.player, mixingBowl, 9, 123, 19));

		
		/* Input ingredients */
		for (int i=0; i<3; ++i) {
			for (int j=0; j<3; ++j) {
				this.addSlotToContainer(new Slot(mixingBowl, j + i * 3, 19 + 18*j, 18 + 18*i)); // ***
			}
		}

		/* Player Inventory */
		for (int i = 0; i < 3; i++)
		{
			for (int k = 0; k < 9; k++)
			{
				addSlotToContainer(new Slot(inventory, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
			}
		}

		/* Player hotbar inventory */
		for (int j = 0; j < 9; j++)
		{
			addSlotToContainer(new Slot(inventory, j, 8 + j * 18, 142));
		}
		
//		this.onCraftMatrixChanged(this.craftMatrix);
		
	}

	@Override
	public void onCraftMatrixChanged(IInventory par1Inventory) {
//		System.out.println("mixingBowl: " + mixingBowl);
//		System.out.println("craftMatrix: " + craftMatrix);
//		System.out.println("WorldObj: " + worldObj);
//		System.out.println("Instance: " + CraftingManagerCrafter.getInstance());
		
// //		mixingBowl.setMixResult(CraftingManagerCrafter.getInstance().findMatchingRecipe(craftMatrix, worldObj));
//		mixingBowl.setInventorySlotContents(9, mixResult);
		useUpIngredients();
	}
	
	public void useUpIngredients() {
		for (int i=0; i<9; i++) {
//			System.out.println("craftMatrix: "); //) + i + ", " + this.craftMatrix.getStackInSlot(i));
//			if (this.craftMatrix.getStackInSlot(i) != null) {
//				System.out.println("Size: " + this.craftMatrix.getStackInSlot(i).stackSize);
//				this.craftMatrix.decrStackSize(i, 1);
//			}
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		// TODO Auto-generated method stub
		return mixingBowl.isUseableByPlayer(par1EntityPlayer);
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

			if (slotnumber == 9)
			{
				if (!mergeItemStack(itemstack1, 10, 45, true)) // changed 3 to 10, 39.. 45
				{
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
//			else if (slotnumber == 1 || slotnumber == 0)
			else if (slotnumber >= 0 && slotnumber <= 8)
			{
				if (!mergeItemStack(itemstack1, 10, 45, false)) // changed 3 to 10, 39.. 45
				{
					return null;
				}
			}
			/** This needs fixing!!!!!!!!!!!!!!!!!!!!!!!
			//		 else if (RecipesGoldOven.smelting().getSmeltingResult(itemstack1.getItem().itemID) != null)
			else if (MixingBowlRecipes.smelting().getSmeltingResult(itemstack1.getItem()) != null)
			{
				if (!mergeItemStack(itemstack1, 0, 1, false))
				{
					return null;
				}
			}
			*/
			else if (slotnumber >= 10 && slotnumber < 37) // changed 3 to 10, 30.. 37
			{
				if (!mergeItemStack(itemstack1, 37, 45, false)) // 30.. 37, 39.. 45
				{
					return null;
				}
			}
			else if (slotnumber >= 37 && slotnumber < 45 && !mergeItemStack(itemstack1, 10, 30, false)) // 3.. 10, 30.. 37, 39.. 45
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
