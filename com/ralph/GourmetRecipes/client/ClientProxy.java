package com.ralph.GourmetRecipes.client;

import com.ralph.GourmetRecipes.CommonProxy;
import com.ralph.GourmetRecipes.GourmetRecipes;
import com.ralph.GourmetRecipes.Machines.MixingBowl.MixingBowl;
import com.ralph.GourmetRecipes.Machines.MixingBowl.MixingBowlRenderer;
import com.ralph.GourmetRecipes.Machines.MixingBowl.TileEntityMixingBowl;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
        
        @Override
        public void registerRenderers() {
                // This is for rendering entities and so forth later on
//        	ISimpleBlockRenderingHandler mixingBowlRenderer = new MixingBowlRenderer();
//        	RenderingRegistry.registerBlockHandler(MixingBowl.getRenderId(), new MixingBowlRenderer());
        	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMixingBowl.class, new MixingBowlRenderer());
        }


}
