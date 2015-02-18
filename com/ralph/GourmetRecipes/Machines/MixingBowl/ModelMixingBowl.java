package com.ralph.GourmetRecipes.Machines.MixingBowl;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMixingBowl extends ModelBase {
	//fields
	ModelRenderer bowl;
	ModelRenderer motor;
	ModelRenderer beater;

	public ModelMixingBowl()
	{
//		int textureWidth = 64;
//		int textureHeight = 64;

		bowl = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);;
		bowl.addBox(0F, 0F, 0F, 16, 8, 16, 0F);
		bowl.setRotationPoint(0F, 0F, 0F);
		bowl.mirror = false;
		setRotation(bowl, 0F, 0F, 0F);
		
		// ModelRenderer(ModelBase, textureOffset(x,y))
		motor = new ModelRenderer(this, 0, 33).setTextureSize(64, 64);
		// addBox( (x1, y1, z1) (x.width, y.height, z.depth)) - dims scaled by f5 in render(...)
		motor.addBox(4F, 9F,4F, 8, 7, 8, 0F);
		motor.setRotationPoint(0F, 0F, 0F);
		motor.mirror = false;
		//      setRotation(motor, 1.570796F, 0F, 0F);
		setRotation(motor, 0F, 0F, 0F);

		beater = new ModelRenderer(this, 0, 49).setTextureSize(64, 64);
		beater.addBox(-6F, -0.5F,-0.5F, 12, 1, 1, 0F);
		beater.setRotationPoint(7.5F, 8.5F, 7.5F);
		beater.mirror = false;
		
//		setRotation(beater, 0F, 90 / (180F / (float)Math.PI), 0F);
		
		setRotation(beater, 0F, 0F, 0F);
	
	}

	public void setBeaterAngle( float angle ) {
		setRotation(beater, 0F, angle, 0F);
	}
	
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		bowl.render(f5);
		motor.render(f5);
		beater.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		// Keep all rotation angles 0
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		/*
    this.bowl.rotateAngleX = f4 / (180F / (float)Math.PI);
    this.bowl.rotateAngleY = f3 / (180F / (float)Math.PI);
    this.motor.rotateAngleX = ((float)Math.PI / 2F);
		 */
	}

}
