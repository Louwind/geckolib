package software.bernie.geckolib.example.client.renderer.tile;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib.example.block.tile.FertilizerTileEntity;
import software.bernie.geckolib.example.client.renderer.model.tile.FertilizerModel;
import software.bernie.geckolib.geo.render.GeoBlockRenderer;

public class FertilizerTileRenderer extends GeoBlockRenderer<FertilizerTileEntity>
{
	public FertilizerTileRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn, new FertilizerModel());
	}

	@Override
	public ResourceLocation getTexture(FertilizerTileEntity entity)
	{
		return new ResourceLocation("geckolib" + ":textures/block/fertilizer.png");
	}
}