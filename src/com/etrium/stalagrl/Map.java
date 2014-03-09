package com.etrium.stalagrl;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;

public class Map
{

	public Object mapWindow;
	public String curLevel;
	
	protected int width = 1;
	protected int height = 1;
	
	private Camera camera = null;
	private List<ModelInstance> floorTiles;
	private Environment environment;
	private AssetManager assets;
	private Texture dirtTexture;

	public Map(int p_width, int p_height)
	{
		width = p_width;
		height = p_height;
		assert width > 0;
		assert height > 0;
		
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1.0f, 1.0f, 1.0f, 1.0f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1.0f, -0.8f, -0.2f));
		
		assets = new AssetManager();
		assets.load("data/models/plain-floor.g3db", Model.class);
		assets.finishLoading();
		
		dirtTexture = new Texture(Gdx.files.internal("data/textures/dirt.png"));
		dirtTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		floorTiles = new ArrayList<ModelInstance>();
	}
	
	public void SetCamera(Camera p_camera)
	{
		camera = p_camera;
	}
	
	public void Render(ModelBatch modelBatch)
	{
		assert camera != null;
		
		if ((floorTiles.size() == 0) &&
			(assets.update()))
		{
			for (int i = 0; i < width; i++)
			{
				for (int j = 0; j < height; j++)
				{
					ModelInstance instance = new ModelInstance(assets.get("data/models/plain-floor.g3db", Model.class));
					
					instance.getNode("Plane").translation.x = 5.0f-i;
					instance.getNode("Plane").translation.y = 5.0f-j;
					instance.getNode("Plane").calculateTransforms(false);
					
					Material mat = instance.materials.get(0);
					mat.set(TextureAttribute.createDiffuse(dirtTexture));
					
					floorTiles.add(instance);
				}
			}
		}
		
		Gdx.gl.glViewport(0, 0, 1024, 768);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		if (floorTiles.size() > 0)
		{
			for (int i = 0; i < floorTiles.size(); i++)
				modelBatch.render(floorTiles.get(i), environment);
		}
	}
}
