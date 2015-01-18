package com.ralph.GourmetRecipes.Machines.MixingBowl;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

/**
 *	See also: <a src="http://www.minecraftforge.net/wiki/Containers_and_GUIs">http://www.minecraftforge.net/wiki/Containers_and_GUIs</a>
 *  Original inspiritation from: http://www.minecraftforum.net/forums/archive/tutorials/931088-1-4-7-forge-blaueseichoerns-gui-tutorial
 */
public class TileEntityMixingBowl extends TileEntity implements IInventory{

	private ItemStack mixingbowlInv[];

	/** The number of ticks this recipe needs to process
	 * Typical values between 30 and 300 (1 - 10 s) */
	public int mixingbowlMixTime;

	private boolean isActive;

	/**
	 * The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for
	 */
	public int mixingbowlItemMixTime;

	/** The number of ticks that the current item has been cooking for */
	public int mixingbowlMixedTime;

	public int front;

	public TileEntityMixingBowl()
	{
		mixingbowlInv = new ItemStack[10];
		mixingbowlMixTime = 300;
		mixingbowlItemMixTime = 0;
		mixingbowlMixedTime = 0;
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return mixingbowlInv.length;
	}


	/**
	 * Returns the stack in int slot
	 */
	@Override
	public ItemStack getStackInSlot(int slot) {
		// TODO Auto-generated method stub
		return mixingbowlInv[slot];
	}

	/**
	 * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
	 * stack.
	 */
	/*
	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}
	*/
	
	
	@Override
	public ItemStack decrStackSize(int slot, int amt)
	{
		if (mixingbowlInv[slot] != null)
		{
			if (mixingbowlInv[slot].stackSize <= amt)
			{
				ItemStack itemstack = mixingbowlInv[slot];
				mixingbowlInv[slot] = null;
				return itemstack;
			}

			ItemStack itemstack1 = mixingbowlInv[slot].splitStack(amt);

			if (mixingbowlInv[slot].stackSize == 0)
			{
				mixingbowlInv[slot] = null;
			}

			return itemstack1;
		}
		else
		{
			return null;
		}
	}

	/**
	 * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
	 * like when you close a workbench GUI.
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		if (mixingbowlInv[slot] != null)
		{
			ItemStack itemstack = mixingbowlInv[slot];
			mixingbowlInv[slot] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack)
	{
		mixingbowlInv[slot] = itemStack;

		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	/**
	 * Returns the name of the inventory.
	 */
	@Override
	public String getInventoryName() {
		// TODO Auto-generated method stub
		return "container.mixingBowl";
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
	 * this more of a set than a get?*
	 */
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes with Container
	 */
	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
		{
			return false;
		}

		return par1EntityPlayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		NBTTagList nbttaglist = tagCompound.getTagList("Items", front);
		mixingbowlInv = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			//		 NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.getCompoundTagAt(i);

			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < mixingbowlInv.length)
			{
				mixingbowlInv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}

		front = tagCompound.getInteger("FrontDirection");
		mixingbowlMixTime = tagCompound.getShort("BurnTime");
		mixingbowlMixedTime = tagCompound.getShort("CookTime");
		mixingbowlItemMixTime = getItemBurnTime(mixingbowlInv[1]);

//		System.out.println("front:" + front);
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("FrontDirection", (int)front);
		par1NBTTagCompound.setShort("BurnTime", (short)mixingbowlMixTime);
		par1NBTTagCompound.setShort("CookTime", (short)mixingbowlMixedTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < mixingbowlInv.length; i++)
		{
			if (mixingbowlInv[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				mixingbowlInv[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		par1NBTTagCompound.setTag("Items", nbttaglist);
//		System.out.println("write:" + front);
//		System.out.println("burn:" + mixingbowlMixTime);
	}



	/**
	 * Returns an integer between 0 and the passed value representing how close the current item is to being completely
	 * cooked
	 */
	public int getCookProgressScaled(int par1)
	{
		return (mixingbowlMixedTime * par1) / 300;
	}

	/**
	 * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
	 * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
	 */
	public int getBurnTimeRemainingScaled(int scalefactor)
	{
		int timeRem = mixingbowlMixTime - mixingbowlMixedTime;
		System.out.println("getBurnTimeremainingScaled: " + mixingbowlMixedTime + ", " + mixingbowlMixTime);
		System.out.println("getBurnTimeremainingScaled: " + timeRem);
//		return (mixingbowlMixedTime * par1) / mixingbowlMixTime;
		return timeRem/scalefactor;
	}

	/**
	 * Returns true if the furnace is currently burning
	 */
	public boolean isBurning()
	{
	//	return mixingbowlMixTime > 0; // ***************************************
		return true;
	}

	/**
	 * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
	 * ticks and creates a new spawn inside its implementation.
	 */
	public void updateEntity()
	{
		boolean var1 = this.mixingbowlMixTime > 0;
		boolean var2 = false;
		mixingbowlMixedTime++;
		if (mixingbowlMixedTime >= mixingbowlMixTime) {
			mixingbowlMixedTime = 0;
		}
		/*
		if (this.mixingbowlMixTime > 0)
		{
			--this.mixingbowlMixTime;
		}
		if (!this.worldObj.isRemote)
		{
			if (this.mixingbowlMixTime == 0 && this.canSmelt())
			{
				this.mixingbowlItemMixTime = this.mixingbowlMixTime = getItemBurnTime(this.mixingbowlInv[1]);
				if (this.mixingbowlMixTime > 0)
				{
					var2 = true;
					if (this.mixingbowlInv[1] != null)
					{
						--this.mixingbowlInv[1].stackSize;
						if (this.mixingbowlInv[1].stackSize == 0)
						{
							Item var3 = this.mixingbowlInv[1].getItem().getContainerItem();
							this.mixingbowlInv[1] = var3 == null ? null : new ItemStack(var3);
						}
					}
				}
			}
			if (this.isBurning() && this.canSmelt())
			{
				++this.mixingbowlMixedTime;
				if (this.mixingbowlMixedTime == 200)
				{
					this.mixingbowlMixedTime = 0;
					this.smeltItem();
					var2 = true;
				}
			}
			else
			{
				this.mixingbowlMixedTime = 0;
			}
			if (var1 != this.mixingbowlMixTime > 0)
			{
				var2 = true;
				this.validate();
			}
		}
		boolean check = isActive;
		isActive = isBurning();
		if(isActive != check)
		{
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
		if (var2)
		{
			this.onInventoryChanged();
		}
		*/
	}

	private void onInventoryChanged() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
	 */
	private boolean canSmelt()
	{
		if (mixingbowlInv[0] == null)
		{
			return false;
		}

		ItemStack itemstack = RecipesMixingBowl.smelting().getSmeltingResult(mixingbowlInv[0].getItem());

		if (itemstack == null)
		{
			return false;
		}

		if (mixingbowlInv[2] == null)
		{
			return true;
		}

		if (!mixingbowlInv[2].isItemEqual(itemstack))
		{
			return false;
		}

		if (mixingbowlInv[2].stackSize < getInventoryStackLimit() && mixingbowlInv[2].stackSize < mixingbowlInv[2].getMaxStackSize())
		{
			return true;
		}

		return mixingbowlInv[2].stackSize < itemstack.getMaxStackSize();
	}

	/**
	 * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
	 */
	public void smeltItem()
	{
		if (this.canSmelt())
		{
			ItemStack var1 = RecipesMixingBowl.smelting().getSmeltingResult(this.mixingbowlInv[0].getItem());
			if (this.mixingbowlInv[2] == null)
			{
				this.mixingbowlInv[2] = var1.copy();
			}
			else if (this.mixingbowlInv[2] == var1)
			{
				++this.mixingbowlInv[2].stackSize;
			}
			--this.mixingbowlInv[0].stackSize;
			if (this.mixingbowlInv[0].stackSize == 0)
			{
				Item var2 = this.mixingbowlInv[0].getItem().getContainerItem();
				this.mixingbowlInv[0] = var2 == null ? null : new ItemStack(var2);
			}
		}
	}

	/**
	 * Return true if item is a fuel source (getItemBurnTime() > 0).
	 */
	public static boolean isItemFuel(ItemStack par0ItemStack)
	{
		return getItemBurnTime(par0ItemStack) > 0;
	}

	/**
	 * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
	 * fuel
	 */
	public static int getItemBurnTime(ItemStack par1ItemStack)
	{
		if (par1ItemStack == null)
		{
			return 0;
		}
		else
		{
			return 150;
		}

		/*
		int i = par1ItemStack.getItem().itemID;

		if (i < 256 && Block.blocksList[i].blockMaterial == Material.wood)
		{
			return 300;
		}

		if (i == Item.stick.itemID)
		{
			return 100;
		}

		if (i == Item.coal.itemID)
		{
			return 1600;
		}

		if (i == Item.bucketLava.itemID)
		{
			return 20000;
		}

		if (i == Block.sapling.blockID)
		{
			return 100;
		}

		if (i == Item.blazeRod.itemID)
		{
			return 2400;
		}
		if (i == Block.dirt.blockID)
		{
			return 200;
		}
		else
		{
			return ModLoader.addAllFuel(par1ItemStack.itemID, par1ItemStack.getItemDamage());
		}
		 */
	}


	public void openChest()
	{
	}

	public void closeChest()
	{
	}

	public boolean isActive()
	{
	return this.isActive;
	}
	
}
