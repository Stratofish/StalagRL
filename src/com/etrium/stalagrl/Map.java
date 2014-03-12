package com.etrium.stalagrl;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
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
import com.badlogic.gdx.math.Vector2;
import com.etrium.stalagrl.character.Character;
import com.etrium.stalagrl.Assets;
import com.etrium.stalagrl.system.Dijkstra;

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
	private ModelInstance floorTiles[][];
	public MapCell floorMap[][];
	protected List<ModelInstance> staticMeshes;
	protected List<MapRegionRecord> regionRecords;
	public Environment environment;
	public AssetManager assets;
	private Texture dirtTexture;
	public Texture woodFloorTexture;
	private Texture stonesTexture;
	private Texture concreteTexture;
	private Texture grassTexture;
	public Texture barbedWireTexture;
	protected Texture textures[];
	protected long timestamp = 0; 

	public Map(int p_width, int p_height)
	{
		width = p_width;
		height = p_height;
		assert width > 0;
		assert height > 0;
		
		assets = new AssetManager();
		assets.load(Assets.modelFloor, Model.class);
		assets.load(Assets.modelHut, Model.class);
		assets.load(Assets.modelTower, Model.class);
		assets.load(Assets.modelWall1, Model.class);
		assets.load(Assets.modelBarbedWire, Model.class);
		assets.finishLoading();
		
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1.0f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1.0f, -0.8f, -0.2f));
		
		// Setup regions
		regionRecords = new ArrayList<MapRegionRecord>();
		MapRegion hutRegion = new RegionHut(this);
		MapRegion towerRegion = new RegionTower(this);
		MapRegion rollCallRegion = new RegionFloor(this, FLOOR_STONES, 5, 10);
		MapRegion wireSouthRegion = new RegionExtrude(this, RegionExtrude.WEST, 50, Assets.modelBarbedWire);
		MapRegion wireSouthOuterRegion = new RegionExtrude(this, RegionExtrude.WEST, 60, Assets.modelBarbedWire);
		MapRegion wireNorthRegion = new RegionExtrude(this, RegionExtrude.EAST, 50, Assets.modelBarbedWire);
		MapRegion wireNorthOuterRegion = new RegionExtrude(this, RegionExtrude.EAST, 60, Assets.modelBarbedWire);
		MapRegion wireWestRegion = new RegionExtrude(this, RegionExtrude.NORTH, 50, Assets.modelBarbedWire);
		MapRegion wireWestOuterRegion = new RegionExtrude(this, RegionExtrude.NORTH, 60, Assets.modelBarbedWire);
		MapRegion wireEastRegion = new RegionExtrude(this, RegionExtrude.SOUTH, 50, Assets.modelBarbedWire);
		MapRegion wireEastOuterRegion = new RegionExtrude(this, RegionExtrude.SOUTH, 60, Assets.modelBarbedWire);
	
		// Setup region instances
		MapRegionRecord mrr = new MapRegionRecord();
		mrr.region = wireSouthRegion;
		mrr.x = 54;
		mrr.y = 5;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = wireSouthOuterRegion;
		mrr.x = 59;
		mrr.y = 0;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = wireWestRegion;
		mrr.x = 5;
		mrr.y = 5;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = wireWestOuterRegion;
		mrr.x = 0;
		mrr.y = 0;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = wireEastRegion;
		mrr.x = 54;
		mrr.y = 54;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = wireEastOuterRegion;
		mrr.x = 59;
		mrr.y = 59;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = wireNorthRegion;
		mrr.x = 5;
		mrr.y = 54;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = wireNorthOuterRegion;
		mrr.x = 0;
		mrr.y = 59;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = towerRegion;
		mrr.x = 7;
		mrr.y = 7;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = towerRegion;
		mrr.x = 7;
		mrr.y = 50;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = towerRegion;
		mrr.x = 50;
		mrr.y = 7;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = towerRegion;
		mrr.x = 50;
		mrr.y = 50;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = hutRegion;
		mrr.x = 15;
		mrr.y = 30;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = hutRegion;
		mrr.x = 27;
		mrr.y = 30;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = hutRegion;
		mrr.x = 15;
		mrr.y = 37;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = hutRegion;
		mrr.x = 27;
		mrr.y = 37;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = rollCallRegion;
		mrr.x = 42;
		mrr.y = 31;
		regionRecords.add(mrr);
		

		
		
		
		// Create floor map
		floorMap = new MapCell[width][height];
		
		for (int w = 0; w < width; w++)
		{
			for (int h = 0; h < height; h++)
			{
				MapCell mc = new MapCell();
				mc.type = FLOOR_DIRT;
				floorMap[w][h] = mc;
			}
		} 
		
		/* Cycle through regions and copy collision map over to floor map */
		for (int r = 0; r < regionRecords.size(); r++)
			regionRecords.get(r).AddCollisionData();
			
	    /* Set collision zone for edges of map */
			for (int w = 0; w < width; w++)
	    {
			  floorMap[w][0].collision |= MapCell.SOUTH; 
			  floorMap[w][height - 1].collision |= MapCell.NORTH;
	    }
			
	    for (int h = 0; h < height; h++)
	    {
	      floorMap[0][h].collision |= MapCell.WEST;
	      floorMap[width - 1][h].collision |= MapCell.EAST;
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
		
		barbedWireTexture = new Texture(Gdx.files.internal(Assets.textureBarbedWire));
		barbedWireTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		textures = new Texture[6];
		textures[FLOOR_DIRT] = dirtTexture;
		textures[FLOOR_PLANKS] = woodFloorTexture;
		textures[FLOOR_STONES] = stonesTexture;
		textures[FLOOR_CONCRETE] = concreteTexture;
		textures[FLOOR_GRASS] = grassTexture;
		
		floorTiles = new ModelInstance[width][height];
		
		MakeModels();
		
		/* Test code here */
		Dijkstra dj = new Dijkstra(floorMap, width, height);
		List sh = dj.shortestPath(0, 0, 6, 0);
			
		for (int i = 0; i < sh.size(); i++)
		{
		  Vector2 vec = (Vector2) sh.get(i);
		  floorMap[(int) vec.x][(int) vec.y].type = FLOOR_STONES;
		}
		
		timestamp = System.currentTimeMillis() % 1000;
	}
	
	public void SetCamera(Camera p_camera)
	{
		camera = p_camera;
	}
	
	public void MakeModels()
	{
		while (!assets.update())
		{
		}

		int size = regionRecords.size();

		for (int i = 0; i < size; i++)
			regionRecords.get(i).AddRegiontoMap();
		
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				ModelInstance instance = new ModelInstance(assets.get(Assets.modelFloor, Model.class));
				
				instance.transform.translate(i, j, 0);
				
				Material mat = instance.materials.get(0);
				mat.set(TextureAttribute.createDiffuse(textures[floorMap[i][j].type]));
				
				floorTiles[i][j] = instance;
			}
		}
	}
	
	public void Render(ModelBatch modelBatch, Character player)
	{
		assert camera != null;
		
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		int x = player.GetX();
		int y = player.GetY();
		
		int xMin = x - 18;
		int xMax = x + 18;
		int yMin = y - 18;
		int yMax = y + 18;
		
		if (xMin < 0) xMin = 0;
		if (xMax > width) xMax = width;
		if (yMin < 0) yMin = 0;
		if (yMax > height) yMax = height;
		
		if (floorTiles[0][0] != null)
		{
			for (int w = xMin; w < xMax; w++)
			{
				for (int h = yMin; h < yMax; h++)
				{
					modelBatch.render(floorTiles[w][h], environment);
				}
			}
		}
		
		int size = regionRecords.size();
		for (int i = 0; i < size; i++)
			regionRecords.get(i).Render(modelBatch);
		
		long newTime = System.currentTimeMillis() % 1000;
		long timeDiff = newTime - timestamp;
		timestamp = newTime;
		
		//System.out.println("Frame time: " + timeDiff + "ms");
	}
	
	public void CheckPlayerPosition(int p_x, int p_y)
	{
		int count = regionRecords.size();
		for (int i = 0; i < count; i++)
		{
			MapRegionRecord record = regionRecords.get(i);
			if (record.InRegion(p_x, p_y))
				record.HideRoof();
			else
				record.ShowRoof();
		}
	}
}
