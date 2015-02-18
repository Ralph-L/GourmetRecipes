Custom Block renders

http://www.minecraftforge.net/wiki/Upgrading_To_Forge_for_1.3.1#Custom_Block_renders
http://www.minecraftforge.net/forum/index.php?topic=5050.0
http://www.minecraftforum.net/forums/archive/tutorials/931249-universal-subarakis-one-stop-custom-block
http://greyminecraftcoder.blogspot.co.at/p/list-of-topics-outdated.html


These are not a whole lot different from the way ModLoader does it, but it still has some changes. Ofcourse, because rendering is all done client-side only, you will need to make sure that you are doing all of this stuff in your client-side proxy. Or something that is called from the proxy.
Anyway, start simple.
Make a class that implements ISimpleBlockRenderingHandler.
This is what you class should look like at the moment:
public class yourBlockRenderClass implements ISimpleBlockRenderingHandler
{
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
         return false;
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer);
    {
         return false;
    }

    public boolean shouldRender3DInInventory();
    {
         return false;
    }

    public int getRenderId();
    {
         return 0;
    }
}
Looks familiar enough right? Do yourself a favor and dump all the code that you used to have inside you basemod renderWorldBlock() and renderInventoryBlock() methods into the new ones.
Now you have a class that does rendering. If you recompile and stuff now, you will see absolutely no change. Thats because we need register it.
Put this in some method inside your client proxy class, this is called during the init phase. (phase doesn't matter, but it has to be just before your blocks start using the custom render info.):
     RenderingRegistry.instance().registerBlockHandler(new yourBlockrenderClass()); // Or 'this' if your proxy happens to be the one that implements the block render interface.
     // This variable renderId? this thing has got to be static somewhere. not necessarily public, but we need it available form everywhere. at the minimum we need public accessors. I personally have this variable as a public int in my common @mod class.
     renderId = this.getRenderId();
After recompiling, It still won't work.
Time for some more edits here:
public class yourBlockRenderClass implements ISimpleBlockRenderingHandler
{
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
         // Your rendering code
         
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
         // Your renderring code
         return false;
    }

    public boolean shouldRender3DInInventory()
    {
         // This is where it asks if you want the renderInventory part called or not.
         return false; // Change to 'true' if you want the Inventory render to be called.
    }

    public int getRenderId()
    {
         // This is one place we need that renderId from earlier.
         return renderId;
    }
}
Ok... so now we have our custom render, how do you make your blocks use this render? That's done rather easily.
Make sure you have this in your Block file:
    @Override
    public int getRenderType()
    {
        // You know that render ID we talked about earlier? You need to access it here.
        return renderId;
    }
This method is nice and all, however, this means that you must have exactly 1 renderID for each Handler class. You CAN do multiple blocks in 1 handler by simply doing ID checks in your render method.
