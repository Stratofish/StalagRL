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

import com.etrium.stalagrl.Assets;

public class Map
{
	public final static int FLOOR_DIRT = 1;
	public final static int FLOOR_PLANKS = 2;
	public final static int FLOOR_STONES = 3;
	public final static int FLOOR_GRASS = 4;
	public final static int FLOOR_CONCRETE = 5;
	
	public Object mapWindow;
	public String curLevel;
	
	protected int width = 1;
	protected int height = 1;
	
	private Camera camera = null;
	private List<ModelInstance> floorTiles;
	protected int floorMap[][];
	protected List<MapRegion> regions;
	protected List<MapRegionRecord> regionRecords;
	private Environment environment;
	private AssetManager assets;
	private Texture dirtTexture;
	private Texture woodFloorTexture;
	private Texture stonesTexture;
	private Texture concreteTexture;
	private Texture grassTexture;
	protected Texture textures[];

	public Map(int p_width, int p_height)
	{
		width = p_width;
		height = p_height;
		assert width > 0;
		assert height > 0;
		
		// Setup fake regions
		regionRecords = new ArrayList<MapRegionRecord>();
		MapRegion mr1 = new MapRegion();
		mr1.width = 2;
		mr1.height = 3;
		mr1.type = FLOOR_PLANKS;
		
		MapRegion mr2 = new MapRegion();
		mr2.width = 2;
		mr2.height = 3;
		mr2.type = FLOOR_STONES;
		
		MapRegion mr3 = new MapRegion();
		mr3.width = 2;
		mr3.height = 3;
		mr3.type = FLOOR_CONCRETE;
		
		MapRegion mr4 = new MapRegion();
		mr4.width = 2;
		mr4.height = 3;
		mr4.type = FLOOR_GRASS;
		
		MapRegionRecord mrr = new MapRegionRecord();
		mrr.region = mr1;
		mrr.x = 1;
		mrr.y = 2;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = mr2;
		mrr.x = 4;
		mrr.y = 2;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = mr3;
		mrr.x = 1;
		mrr.y = 6;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = mr4;
		mrr.x = 4;
		mrr.y = 6;
		regionRecords.add(mrr);
		
		// create floor map
		floorMap = new int[width][height];
		
		for (int w = 0; w < width; w++)
		{
			for (int h = 0; h < height; h++)
			{
				floorMap[w][h] = FindTileType(w, h);
			}
		}
		
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1.0f, 1.0f, 1.0f, 1.0f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1.0f, -0.8f, -0.2f));
		
		assets = new AssetManager();
		assets.load(Assets.modelDirt, Model.class);
		assets.finishLoading();
		
		dirtTexture = new Texture(Gdx.files.internal(Assets.textureDirt));
		dirtTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		woodFloorTexture = new Texture(Gdx.files.internal(Assets.texturePlanks));
		woodFloorTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		stonesTexture = new Texture(Gdx.files.internal(Assets.textureStones));
		stonesTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		concreteTexture = new Texture(Gdx.files.internal(Assets.textureConcrete));
		concreteTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		grassTexture = new Texture(Gdx.files.internal(Assets.textureGrass));
		grassTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		textures = new Texture[6];
		textures[FLOOR_DIRT] = dirtTexture;
		textures[FLOOR_PLANKS] = woodFloorTexture;
		textures[FLOOR_STONES] = stonesTexture;
		textures[FLOOR_CONCRETE] = concreteTexture;
		textures[FLOOR_GRASS] = grassTexture;
		
		floorTiles = new ArrayList<ModelInstance>();
	}
	
	protected int FindTileType(int x, int y)
	{
		int c = regionRecords.size();
		
		for (int i = 0; i < c; i++)
		{
			MapRegionRecord mrr = regionRecords.get(i);
			
			if ((x >= mrr.x) &&
				(y >= mrr.y) &&
				(x < mrr.x + mrr.region.width) &&
				(y < mrr.y + mrr.region.height))
			{
				return mrr.region.type; 
			}
		}
		
		return FLOOR_DIRT;
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
					ModelInstance instance = new ModelInstance(assets.get(Assets.modelDirt, Model.class));
					
					instance.transform.translate(i, j, 0);
					
					Material mat = instance.materials.get(0);
					mat.set(TextureAttribute.createDiffuse(textures[floorMap[i][j]]));
					
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
