package com.ralph.GourmetRecipes.Machines.MixingBowl;

import java.util.Random;

import com.ralph.GourmetRecipes.GourmetRecipes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MixingBowl extends BlockContainer {

	private static boolean keepInventory = false;
	/**
	 * Is the random generator used by furnace to drop the inventory contents in random directions.
	 */
	private Random goldRand;


	public MixingBowl(Material material) {
		super(material);
		this.setHardness(1.0F);
		this.setStepSound(Block.soundTypeGravel);
		this.setBlockName("mixingBowl");
		this.setBlockTextureName("gourmetrecipes:mixingbowl");
		this.setCreativeTab(CreativeTabs.tabBlock);
	}


	//@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityMixingBowl();
	}


	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		// TODO Auto-generated method stub
		return new TileEntityMixingBowl();
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	public void onBlockAdded(World par1World, int x, int y, int z)
	{
		super.onBlockAdded(par1World, x, y, z);
		//	 setDefaultDirection(par1World, x, y, z);
		par1World.markBlockForUpdate(x, y, z);
	}

	/**
	 * called everytime the player right clicks this block
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float a, float b, float c)
	{

		TileEntityMixingBowl mixingBowl = (TileEntityMixingBowl)world.getTileEntity(x, y, z);
		player.openGui(GourmetRecipes.instance, 0, world, x, y, z);

		return true;

	}

	/**
	 * ejects contained items into the world, and notifies neighbours of an update, as appropriate
	 */
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
	{
		if (!keepInventory)
		{
			TileEntityMixingBowl gold = (TileEntityMixingBowl)par1World.getTileEntity(par2, par3, par4);
			if (gold != null)
			{
				for (int var8 = 0; var8 < gold.getSizeInventory(); ++var8)
				{
					ItemStack item = gold.getStackInSlot(var8);
					if (item != null)
					{
						float var10 = this.goldRand.nextFloat() * 0.8F + 0.1F;
						float var11 = this.goldRand.nextFloat() * 0.8F + 0.1F;
						float var12 = this.goldRand.nextFloat() * 0.8F + 0.1F;
						while (item.stackSize > 0)
						{
							int var13 = this.goldRand.nextInt(21) + 10;
							if (var13 > item.stackSize)
							{
								var13 = item.stackSize;
							}
							item.stackSize -= var13;
							EntityItem var14 = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(item.getItem(), var13, item.getItemDamage()));
							if (item.hasTagCompound())
							{
								var14.getEntityItem().setTagCompound((NBTTagCompound)item.getTagCompound().copy());
							}
							float var15 = 0.05F;
							var14.motionX = (double)((float)this.goldRand.nextGaussian() * var15);
							var14.motionY = (double)((float)this.goldRand.nextGaussian() * var15 + 0.2F);
							var14.motionZ = (double)((float)this.goldRand.nextGaussian() * var15);
							par1World.spawnEntityInWorld(var14);
						}
					}
				}
			}
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
}
