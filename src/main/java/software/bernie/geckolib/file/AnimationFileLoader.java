package software.bernie.geckolib.file;

import com.eliotlash.molang.MolangParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.util.JSONException;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.animation.builder.Animation;
import software.bernie.geckolib.util.json.JsonAnimationUtils;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

public class AnimationFileLoader
{

	public AnimationFile loadAllAnimations(MolangParser parser, boolean loopByDefault, ResourceLocation location)
	{
		AnimationFile animationFile = new AnimationFile();
		JsonObject jsonRepresentation = loadFile(location);
		Set<Map.Entry<String, JsonElement>> entrySet = JsonAnimationUtils.getAnimations(jsonRepresentation);
		for (Map.Entry<String, JsonElement> entry : entrySet)
		{
			String animationName = entry.getKey();
			Animation animation;
			try
			{
				animation = JsonAnimationUtils.deserializeJsonToAnimation(JsonAnimationUtils.getAnimation(jsonRepresentation, animationName), parser);
				if (loopByDefault)
				{
					animation.loop = true;
				}
				animationFile.putAnimation(animationName, animation);
			}
			catch (JSONException e)
			{
				GeckoLib.LOGGER.error("Could not load animation: {}", animationName, e);
				throw new RuntimeException(e);
			}
		}
		return animationFile;
	}


	/**
	 * Internal method for handling reloads of animation files. Do not override.
	 */
	private JsonObject loadFile(ResourceLocation location)
	{
		String content = getFileAsString(location);
		Gson GSON = new Gson();
		return JSONUtils.fromJson(GSON, content, JsonObject.class);
	}


	public String getFileAsString(ResourceLocation location)
	{
		try (InputStream inputStream = getStreamForResourceLocation(location))
		{
			return IOUtils.toString(inputStream);
		}
		catch (Exception e)
		{
			String message = "Couldn't load " + location;
			GeckoLib.LOGGER.error(message, e);
			throw new RuntimeException(new FileNotFoundException(location.toString()));
		}
	}

	public InputStream getStreamForResourceLocation(ResourceLocation resourceLocation)
	{
		return new BufferedInputStream(GeckoLib.class.getResourceAsStream("/assets/" + resourceLocation.getNamespace() + "/" + resourceLocation.getPath()));
	}
}