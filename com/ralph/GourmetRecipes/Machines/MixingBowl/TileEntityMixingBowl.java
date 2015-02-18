package com.ralph.GourmetRecipes.Machines.MixingBowl;


import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 *	See also: <a src="http://www.minecraftforge.net/wiki/Containers_and_GUIs">http://www.minecraftforge.net/wiki/Containers_and_GUIs</a>
 *  Original inspiritation from: http://www.minecraftforum.net/forums/archive/tutorials/931088-1-4-7-forge-blaueseichoerns-gui-tutorial
 */
public class TileEntityMixingBowl extends TileEntity implements IInventory{

	/**  Mixing bowl crafting matrix<br>
	 *   Slot 10 is output result */
	public ItemStack[] mixingbowlInv;
	
	private boolean isActive;

	/** The number of ticks that the current item has been cooking for */
	public int mixingbowlMixedTime = 0;

	/** The number of ticks this recipe needs to process
	 * Typical values between 30 and 300 (1 - 10 s) */
	public int mixingbowlMixTime = 200;

	public int front;

	/** Variable to store what the craft result will be when mixing time is up */
	private ItemStack mixResult;

	public TileEntityMixingBowl()
	{
		mixingbowlInv = new ItemStack[10];
		mixingbowlMixTime = 200;
		mixingbowlMixedTime = 0;
		mixResult = null;
		this.canUpdate();
	}

	/*
	 * (non-Javadoc)
	 * @see net.minecraft.tileentity.TileEntity#getDescriptionPacket()
	 * @see http://www.minecraftforge.net/wiki/Custom_Tile_Entity_Renderer
	public Packet getDescriptionPacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
        }

        public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
        readFromNBT(packet.customParam1);
        }
       */
       
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
		//		System.out.println("TileEntity.setInventorySlotContents...");
		// ??		MixingBowlRecipes.findMatchingRecipe(null, worldObj);
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
	 * Ratio of Mixed Time over Total time required * 100
	 * @return Percent progress
	 */
	public int getMixProgress() {
		if (mixingbowlMixTime == 0) return 0;
		return mixingbowlMixedTime * 100 / mixingbowlMixTime;
	}

	/**
	 * Returns true if the furnace is currently burning
	 */
	public boolean isMixing()
	{
		return mixingbowlMixedTime > 0; // ***************************************
	}

	/**
	 * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
	 * ticks and creates a new spawn inside its implementation.
	 */
	@Override
	public void updateEntity()
	{

		boolean var1 = this.mixingbowlMixTime > 0;
		boolean var2 = false;

		if (var1) {
			isActive = true;
		}

		//		if (!this.worldObj.isRemote)
		//		{
		if (this.canMix()) { // (this.isMixing() && this.canSmelt()) {
			++this.mixingbowlMixedTime;
			if (this.mixingbowlMixedTime > mixingbowlMixTime)
			{
				this.mixingbowlMixedTime = 0;
				this.mixItem();
				var2 = true;
			}
		}
		else
		{
			this.mixingbowlMixedTime = 0;
		}
		//		}

		boolean check = isActive;
		isActive = isMixing();
		if(isActive != check)
		{
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
		if (var2)
		{
			this.onInventoryChanged();
		}
	
		/*
		if (mixingbowlInv[4] != null) {
			if (mixingbowlInv[4].getItem() == Items.egg) {
				ItemStack itemstack = new ItemStack(Items.cake);
				mixingbowlInv[9] = itemstack;
			}
		} else {
			mixingbowlInv[9] = null;
		}
		 */
	}


	private void onInventoryChanged() {
		// TODO Auto-generated method stub
		System.out.println("Inv change: " + mixResult);
	}

	/**
	 * Returns true if the mixing bowl can mix a recipe, i.e. has source items, destination stack isn't full, etc.
	 */
	private boolean canMix()
	{
		// Make a copy of the current recipe
		ItemStack[] currentRecipe = new ItemStack[9];
		for (int i=0; i<9; ++i) {
			currentRecipe[i] = null;
			if (mixingbowlInv[i] != null) {
				currentRecipe[i] = mixingbowlInv[i];
				//				System.out.println("recipe: " + i + ", " + currentRecipe[i].getItem());
			}
		}

		// Now see if it matches an existing recipe
		
		mixResult = CraftingManagerCrafter.getInstance().getMatchingRecipe(currentRecipe);


		//		System.out.println("Result: " + itemstack);

		/* No recipe */
		if (mixResult == null) return false;

		/* Recipe, and empty o/p slot */
		if (mixingbowlInv[9] == null) return true;

		//		System.out.println("Inv[9]: " + mixingbowlInv[9]);
		//        if (!this.furnaceItemStacks[2].isItemEqual(itemstack)) return false;

		if (!this.mixingbowlInv[9].isItemEqual(mixResult)) return false;

		if (this.mixingbowlInv[9].stackSize < getInventoryStackLimit() && this.mixingbowlInv[9].stackSize < this.mixingbowlInv[9].getMaxStackSize())
		{
			return true;
		}

		return this.mixingbowlInv[9].stackSize < mixResult.getMaxStackSize();
	}

	/**
	 * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
	 */
	public void mixItem()
	{
		if (this.canMix())
		{


			ItemStack var1 = getMixResult(); //MixingBowlRecipes.getSmeltingResult(this.mixingbowlInv[0].getItem());

			if (this.mixingbowlInv[9] == null)
			{
				this.mixingbowlInv[9] = var1.copy();
			}
			else if (this.mixingbowlInv[9] == var1)
			{
				++this.mixingbowlInv[9].stackSize;
			}
			/* Now take from crafting inventory
			 * 
			 */
			//			List recipes = CraftingManagerCrafter.getInstance().getRecipeList();
			//			for (int k = 0; k < recipes.size(); k++) {
			//				IRecipe irecipe = (IRecipe)recipes.get(k);
			//				System.out.println("Irecipe: " + irecipe);
			for (int i=0; i<9; i++) {
				//				System.out.println("decr: " + i + ", " + mixingbowlInv[i]); // + ", " + mixingbowlInv[i].stackSize);
				if (mixingbowlInv[i] != null && mixingbowlInv[i].stackSize > 0) decrStackSize(i,1); // WRONG INV!
			}

			/*

			--this.mixingbowlInv[0].stackSize;
			if (this.mixingbowlInv[0].stackSize == 0)
			{
				Item var2 = this.mixingbowlInv[0].getItem().getContainerItem();
				this.mixingbowlInv[0] = var2 == null ? null : new ItemStack(var2);
			}
			 */
		}
	}

	public ItemStack getMixResult() {
		return mixResult;
	}

	public void setMixResult(ItemStack mixResult) {
		this.mixResult = mixResult;
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
