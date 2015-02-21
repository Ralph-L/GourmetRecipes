package main.java.com.ralph.GourmetRecipes.client;


import main.java.com.ralph.GourmetRecipes.CommonProxy;
import main.java.com.ralph.GourmetRecipes.Machines.MixingBowl.MixingBowlRenderer;
import main.java.com.ralph.GourmetRecipes.Machines.MixingBowl.TileEntityMixingBowl;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
        
        @Override
        public void registerRenderers() {
        	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMixingBowl.class, new MixingBowlRenderer());
        }


}
