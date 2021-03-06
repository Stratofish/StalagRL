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
import com.etrium.stalagrl.character.Character;
import com.etrium.stalagrl.character.Player;
import com.etrium.stalagrl.inventory.Item;
import com.etrium.stalagrl.inventory.ItemType;
import com.etrium.stalagrl.region.MapRegion;
import com.etrium.stalagrl.region.MapRegionRecord;
import com.etrium.stalagrl.region.MapRegionType;
import com.etrium.stalagrl.region.RegionActivity;
import com.etrium.stalagrl.region.RegionDummy;
import com.etrium.stalagrl.region.RegionFloor;
import com.etrium.stalagrl.region.RegionFood;
import com.etrium.stalagrl.region.RegionGuardhouse;
import com.etrium.stalagrl.region.RegionHut;
import com.etrium.stalagrl.region.RegionTower;
import com.etrium.stalagrl.region.RegionExtrude;
import com.etrium.stalagrl.system.EtriumEvent;
import com.etrium.stalagrl.system.EventListener;
import com.etrium.stalagrl.system.EventManager;
import com.etrium.stalagrl.system.EventType;
import com.etrium.stalagrl.Assets;

public class Map implements EventListener
{
	public final static int FLOOR_DIRT = 1;
	public final static int FLOOR_PLANKS = 2;
	public final static int FLOOR_STONES = 3;
	public final static int FLOOR_GRASS = 4;
	public final static int FLOOR_CONCRETE = 5;
	
	public Object mapWindow;
	public String curLevel;
	
	public int width = 1;
	public int height = 1;
	
	private Camera camera = null;
	private ModelInstance floorTiles[][];
	public MapCell floorMap[][];
	public List<MapCell> hiddingPlaces;
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
	public Texture hutTexture;
	public Texture foodTexture;
	public Texture compassTexture;
	public Texture crowbarTexture;
	public Texture keyTexture;
	public Texture lockPickTexture;
	public Texture papersTexture;
	public Texture spadeTexture;
	public Texture watchTexture;
	public Texture wireCuttersTexture;
	protected Texture textures[];
	public Texture itemTextures[];
	protected long timestamp = 0;
	public Activity currentActivity = null;
	public boolean currentActivityLead = false;
	public RegionActivity currentRegionActivity = null;
	public List<RegionActivity> activityRegions = null;
	
	public ArrayList<ItemRenderer> floorItems = new ArrayList<ItemRenderer>();
	
	protected EventManager evtMgr = new EventManager();
	public Player player = null;

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
		assets.load(Assets.modelFood, Model.class);
		assets.load(Assets.modelGuardhouse, Model.class);
		assets.load(Assets.modelPlayer, Model.class);
		assets.load(Assets.modelGuard, Model.class);
		assets.load(Assets.modelItemMesh, Model.class);
		assets.finishLoading();
		
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1.0f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1.0f, -0.8f, -0.2f));
		
		// Load textures
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
		
		textures = new Texture[7];
		textures[FLOOR_DIRT] = dirtTexture;
		textures[FLOOR_PLANKS] = woodFloorTexture;
		textures[FLOOR_STONES] = stonesTexture;
		textures[FLOOR_CONCRETE] = concreteTexture;
		textures[FLOOR_GRASS] = grassTexture;				
		
		hutTexture = new Texture(Gdx.files.internal(Assets.textureHut));
		hutTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		foodTexture = new Texture(Gdx.files.internal(Assets.textureFood));
		foodTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		compassTexture = new Texture(Gdx.files.internal(Assets.itemCompass));
		compassTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    
		crowbarTexture = new Texture(Gdx.files.internal(Assets.itemCrowbar));
		crowbarTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		keyTexture = new Texture(Gdx.files.internal(Assets.itemKey));
		keyTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		lockPickTexture = new Texture(Gdx.files.internal(Assets.itemLockpick));
		lockPickTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		papersTexture = new Texture(Gdx.files.internal(Assets.itemPapers));
		papersTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		spadeTexture = new Texture(Gdx.files.internal(Assets.itemSpade));
		spadeTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		watchTexture = new Texture(Gdx.files.internal(Assets.itemWatch));
		watchTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		wireCuttersTexture = new Texture(Gdx.files.internal(Assets.itemWireCutters));
		wireCuttersTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		    
		itemTextures = new Texture[9];
		itemTextures[ItemType.COMPASS.ordinal()] = compassTexture;
		itemTextures[ItemType.CROWBAR.ordinal()] = crowbarTexture;
		itemTextures[ItemType.KEY.ordinal()] = keyTexture;
		itemTextures[ItemType.LOCKPICK.ordinal()] = lockPickTexture;
		itemTextures[ItemType.PAPERS.ordinal()] = papersTexture;
		itemTextures[ItemType.SPADE.ordinal()] = spadeTexture;
		itemTextures[ItemType.WATCH.ordinal()] = watchTexture;
		itemTextures[ItemType.WIRECUTTERS.ordinal()] = wireCuttersTexture;

    
    	// TODO: Load from a file
    
		// Setup regions
		regionRecords = new ArrayList<MapRegionRecord>();
		MapRegion hutRegion = new RegionHut(this);
		MapRegion towerRegion = new RegionTower(this);
		MapRegion floorRegion = new RegionFloor(this, FLOOR_STONES, 5, 10);
		MapRegion freetimeRegion = new RegionDummy(this, 50, 50);
		MapRegion sleepRegion = new RegionDummy(this, 21, 11);
		MapRegion excerciseRegion = new RegionDummy(this, 20, 72);
		MapRegion foodRegion = new RegionFood(this);
		MapRegion guardhouseRegion = new RegionGuardhouse(this);
		MapRegion wireSouthRegion = new RegionExtrude(this, RegionExtrude.WEST, 50, Assets.modelBarbedWire);
		MapRegion wireSouthOuterRegion = new RegionExtrude(this, RegionExtrude.WEST, 60, Assets.modelBarbedWire);
		MapRegion wireWestRegion1 = new RegionExtrude(this, RegionExtrude.NORTH, 30, Assets.modelBarbedWire);
		MapRegion wireWestRegion2 = new RegionExtrude(this, RegionExtrude.NORTH, 15, Assets.modelBarbedWire);
		MapRegion wireWestOuterRegion1 = new RegionExtrude(this, RegionExtrude.NORTH, 35, Assets.modelBarbedWire);
		MapRegion wireWestOuterRegion2 = new RegionExtrude(this, RegionExtrude.NORTH, 33, Assets.modelBarbedWire);
		MapRegion wireEastRegion = new RegionExtrude(this, RegionExtrude.SOUTH, 50, Assets.modelBarbedWire);
		MapRegion wireEastOuterRegion = new RegionExtrude(this, RegionExtrude.SOUTH, 73, Assets.modelBarbedWire);
		MapRegion wireSouthExcerciseRegion = new RegionExtrude(this, RegionExtrude.WEST, 20, Assets.modelBarbedWire);
		MapRegion wireNorthExcerciseRegion = new RegionExtrude(this, RegionExtrude.EAST, 20, Assets.modelBarbedWire);
		MapRegion wireWestExcerciseRegion = new RegionExtrude(this, RegionExtrude.NORTH, 73, Assets.modelBarbedWire);
	
		// Setup region instances
		activityRegions = new ArrayList<RegionActivity>();
		
		MapRegionRecord mrr = new MapRegionRecord();
		mrr.region = wireSouthRegion;
		mrr.x = 84;
		mrr.y = 15;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = wireSouthOuterRegion;
		mrr.x = 89;
		mrr.y = 10;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = wireWestRegion1;
		mrr.x = 35;
		mrr.y = 15;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = wireWestRegion2;
		mrr.x = 35;
		mrr.y = 50;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = wireWestOuterRegion1;
		mrr.x = 30;
		mrr.y = 10;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = wireWestOuterRegion2;
		mrr.x = 30;
		mrr.y = 50;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = wireEastRegion;
		mrr.x = 84;
		mrr.y = 64;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = wireEastOuterRegion;
		mrr.x = 89;
		mrr.y = 83;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = wireWestExcerciseRegion;
		mrr.x = 10;
		mrr.y = 10;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = wireNorthExcerciseRegion;
		mrr.x = 10;
		mrr.y = 82;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = wireSouthExcerciseRegion;
		mrr.x = 29;
		mrr.y = 10;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = towerRegion;
		mrr.x = 29;
		mrr.y = 6;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = towerRegion;
		mrr.x = 37;
		mrr.y = 60;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = towerRegion;
		mrr.x = 91;
		mrr.y = 6;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = towerRegion;
		mrr.x = 91;
		mrr.y = 60;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = towerRegion;
		mrr.x = 6;
		mrr.y = 6;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = towerRegion;
		mrr.x = 6;
		mrr.y = 34;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = towerRegion;
		mrr.x = 6;
		mrr.y = 63;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = hutRegion;
		mrr.x = 45;
		mrr.y = 40;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = hutRegion;
		mrr.x = 57;
		mrr.y = 40;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = hutRegion;
		mrr.x = 45;
		mrr.y = 47;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = hutRegion;
		mrr.x = 57;
		mrr.y = 47;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord();
		mrr.region = floorRegion;
		mrr.x = 72;
		mrr.y = 41;
		regionRecords.add(mrr);
		RegionActivity ar = new RegionActivity(mrr, MapRegionType.ROLLCALL);
		activityRegions.add(ar);
			
		mrr = new MapRegionRecord();
		mrr.region = foodRegion;
		mrr.x = 65;
		mrr.y = 66;
		ar = new RegionActivity(mrr, MapRegionType.FOOD);
		regionRecords.add(mrr);
		activityRegions.add(ar);
		
		mrr = new MapRegionRecord();
		mrr.region = guardhouseRegion;
		mrr.x = 35;
		mrr.y = 65;
		regionRecords.add(mrr);
		
		mrr = new MapRegionRecord(true);
		mrr.region = excerciseRegion;
		mrr.x = 10;
		mrr.y = 10;
		ar = new RegionActivity(mrr, MapRegionType.EXCERCISE);
		activityRegions.add(ar);
		
		mrr = new MapRegionRecord(true);
		mrr.region = sleepRegion;
		mrr.x = 45;
		mrr.y = 40;
		ar = new RegionActivity(mrr, MapRegionType.SLEEP);
		activityRegions.add(ar);
		
		mrr = new MapRegionRecord(true);
		mrr.region = freetimeRegion;
		mrr.x = 35;
		mrr.y = 15;
		ar = new RegionActivity(mrr, MapRegionType.FREE_TIME);
		activityRegions.add(ar);
		
		// Create floor map
		floorMap = new MapCell[width][height];
		
		for (int w = 0; w < width; w++)
		{
			for (int h = 0; h < height; h++)
			{
				MapCell mc = new MapCell(this, w, h);
				mc.type = FLOOR_DIRT;
				floorMap[w][h] = mc;
			}
		}

		hiddingPlaces = new ArrayList<MapCell>(); 
		
		/* Cycle through regions and copy collision map and hiding places over to floor map */
		for (int r = 0; r < regionRecords.size(); r++)
		{
			regionRecords.get(r).AddCollisionData();
			regionRecords.get(r).AddHiddingPlaces();
			
		}

		/* TODO For now just put an item at each hidden location */
		for (int i = 0; i < hiddingPlaces.size(); i++)
		{
		  MapCell hiddingPlace = hiddingPlaces.get(i);
		  Item item = new Item( ItemType.PAPERS);
		  hiddingPlace.hiddenItem = item;
		}
				
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

		floorTiles = new ModelInstance[width][height];
		
		MakeModels();
				
		timestamp = System.currentTimeMillis() % 1000;
		
		evtMgr.RegisterListener(this, EventType.evtGlobalLightLevel);
	}
	
	public void SetCamera(Camera p_camera)
	{
		camera = p_camera;
	}
	
	public void SetPlayer(Player p_player)
	{
		player = p_player;
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
		
		/* Render each of the items that is on the floor */
		for (int i = 0; i < floorItems.size(); i++)
		{
		  floorItems.get(i).Render(modelBatch);
		}
		
		long newTime = System.currentTimeMillis() % 1000;
		//long timeDiff = newTime - timestamp;
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

	public void SetCurrentActivity(Activity p_activity, boolean p_lead)
	{
		currentActivity = p_activity;
		currentActivityLead = p_lead;
		
		if (!p_lead)
		{
			currentRegionActivity = null;
			for (int i = 0; i < activityRegions.size(); i++)
			{
				if (activityRegions.get(i).type == currentActivity.regionType)
				{
					currentRegionActivity = activityRegions.get(i);
				}
			}
		} else
			currentRegionActivity = null;
	}
	
	@Override
	public boolean ReceiveEvent(EtriumEvent p_event)
	{
		if (p_event.type == EventType.evtGlobalLightLevel)
		{
			float level = (float)p_event.data;
			environment.set(new ColorAttribute(ColorAttribute.AmbientLight, level, level, level, 1.0f));
			return false;
		}
		
		return false;
	}

	@Override
	public void StartListening()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void StopListening()
	{
		// TODO Auto-generated method stub
		
	}
}
