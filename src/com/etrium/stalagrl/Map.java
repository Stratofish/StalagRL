package com.etrium.stalagrl;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.etrium.stalagrl.Assets;
import com.etrium.stalagrl.RegionLight;

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
	protected MapCell floorMap[][];
	protected List<ModelInstance> staticMeshes;
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
		
		assets = new AssetManager();
		assets.load(Assets.modelFloor, Model.class);
		assets.load(Assets.modelHut, Model.class);
		assets.finishLoading();
		
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.0f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1.0f, -0.8f, -0.2f));
		
		// Setup fake regions
		regionRecords = new ArrayList<MapRegionRecord>();
		MapRegion mr1 = new MapRegion();
		mr1.width = 10;
		mr1.height = 5;
		mr1.type = FLOOR_DIRT;
		mr1.modelType = Assets.modelHut;
		mr1.lightCount = 3;
		mr1.lights = new RegionLight[3];
		RegionLight light = new RegionLight();
		light.x = 1.5f;
		light.y = 0.2f;
		light.z = 2.0f;
		light.r = 1.0f;
		light.g = 1.0f;
		light.b = 1.0f;
		light.intensity = 2.0f;
		light.external = true;
		mr1.lights[0] = light;
		light = new RegionLight();
		light.x = 2.5f;
		light.y = 2.5f;
		light.z = 2.2f;
		light.r = 1.0f;
		light.g = 1.0f;
		light.b = 1.0f;
		light.intensity = 8.0f;
		mr1.lights[1] = light;
		light = new RegionLight();
		light.x = 7.5f;
		light.y = 2.5f;
		light.z = 2.2f;
		light.r = 1.0f;
		light.g = 1.0f;
		light.b = 1.0f;
		light.intensity = 8.0f;
		mr1.lights[2] = light;
		
		
		MapRegionRecord mrr = new MapRegionRecord();
		mrr.region = mr1;
		mrr.x = 1;
		mrr.y = 2;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = mr1;
		mrr.x = 1;
		mrr.y = 9;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = mr1;
		mrr.x = 13;
		mrr.y = 2;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = mr1;
		mrr.x = 13;
		mrr.y = 9;
		regionRecords.add(mrr);
		
		// create floor map
		floorMap = new MapCell[width][height];
		
		for (int w = 0; w < width; w++)
		{
			for (int h = 0; h < height; h++)
			{
				MapCell mc = new MapCell();
				mc.type = FindTileType(w, h);
				floorMap[w][h] = mc;
			}
		}
		
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
		
		if (assets.update())
		{
			if (floorTiles.size() == 0)
			{
				for (int i = 0; i < width; i++)
				{
					for (int j = 0; j < height; j++)
					{
						ModelInstance instance = new ModelInstance(assets.get(Assets.modelFloor, Model.class));
						
						instance.transform.translate(i, j, 0);
						
						Material mat = instance.materials.get(0);
						mat.set(TextureAttribute.createDiffuse(textures[floorMap[i][j].type]));
						
						floorTiles.add(instance);
					}
				}
			}
			
			if (regionRecords.get(0).modelInstance == null)
			{
				int size = regionRecords.size();
				for (int i = 0; i < size; i++)
				{
					MapRegionRecord record = regionRecords.get(i);
					record.modelInstance = new ModelInstance(assets.get(record.region.modelType, Model.class));
					record.modelInstance.transform.translate(record.x, record.y, 0.0f);
					
					Material mat = record.modelInstance.materials.get(0);
					mat.set(TextureAttribute.createDiffuse(woodFloorTexture));
					
					record.environment = new Environment();
					record.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.0f));
					
					for (int j = 0; j < record.region.lightCount; j++)
					{
						RegionLight light = record.region.lights[j];
						record.environment.add(new PointLight().set(light.r, light.g, light.b, record.x+light.x, record.y+light.y, light.z, light.intensity));
						if (light.external)
							environment.add(new PointLight().set(light.r, light.g, light.b, record.x+light.x, record.y+light.y, light.z, light.intensity));
					}
				}
			}
		}
		
		Gdx.gl.glViewport(0, 0, 1024, 768);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		if (floorTiles.size() > 0)
		{
			for (int i = 0; i < floorTiles.size(); i++)
				modelBatch.render(floorTiles.get(i), environment);
		}
		
		if (regionRecords.get(0).modelInstance != null)
		{
			int size = regionRecords.size();
			for (int i = 0; i < size; i++)
			{
				MapRegionRecord record = regionRecords.get(i);
				modelBatch.render(record.modelInstance, record.environment);
			}
		}
	}
}
