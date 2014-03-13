package com.etrium.stalagrl.region;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Vector2;
import com.etrium.stalagrl.Map;
import com.etrium.stalagrl.MapCell;

public class MapRegion
{  
  public int width = 1;
	public int height = 1;
	public int type = Map.FLOOR_DIRT;
	public String modelType = "";
	public RegionLight lights[] = null;
	public int lightCount = 0;
	public float floorLevel = 0.0f;
	public int collisionMap[][] = null;
	public ArrayList<Vector2> hiddingPlaces = new ArrayList<Vector2>();	
	protected Texture texture = null;
	
	Map map = null;

	public MapRegion()
	{
	}
	
	public void SetMap(Map p_map)
	{
		map = p_map;
		
		texture = map.woodFloorTexture;
	}

	public void AddCollisionData(MapRegionRecord record)
	{
		for (int w = 0; w < width; w++)
	    {
			for (int h = 0; h < height; h++)
			{
				map.floorMap[record.x + w][record.y + h].collision |= record.region.collisionMap[w][h]; 
			}
	    }
	}
	
	public void AddHiddingPlaceData(MapRegionRecord record)
  {
    for (int i = 0; i < record.region.hiddingPlaces.size(); i++)
    {
      Vector2 hiddingPlace = record.region.hiddingPlaces.get(i);
      MapCell mapCell = map.floorMap[(int) (record.x + hiddingPlace.x)][(int) (record.y + hiddingPlace.y)];
      
      if (!mapCell.hiddingPlace)
      {
        mapCell.hiddingPlace = true;
        map.hiddingPlaces.add(mapCell);
      }
    }
  } 	
	
	public void AddRegionToMap(MapRegionRecord record)
	{
		MakeModel(record);
		
		record.environment = new Environment();
		record.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1.0f));
		
		for (int x = 0; x < record.region.width; x++)
		{
			for (int y = 0; y < record.region.height; y++)
			{
				if (record.region.type > 0)
					map.floorMap[x+record.x][y+record.y].type = record.region.type;
				
				map.floorMap[x+record.x][y+record.y].floorLevel = record.region.floorLevel;
			}
		}
		
		for (int j = 0; j < record.region.lightCount; j++)
		{
			RegionLight light = record.region.lights[j];
			record.environment.add(new PointLight().set(light.r, light.g, light.b, record.x+light.x, record.y+light.y, light.z, light.intensity));
			if (light.external)
				map.environment.add(new PointLight().set(light.r, light.g, light.b, record.x+light.x, record.y+light.y, light.z, light.intensity));
		}
	}
	
	public void MakeModel(MapRegionRecord record)
	{
		if (modelType != "")
		{
			ModelInstance instance = new ModelInstance(map.assets.get(record.region.modelType, Model.class));
			instance.transform.translate(record.x, record.y, 0.0f);
			
			record.modelInstances.add(instance);
		}
		
		int instanceCount = record.modelInstances.size();
		if (instanceCount > 0)
		{
			ModelInstance instance = record.modelInstances.get(0);
				
			int count = instance.materials.size;
			for (int j = 0; j < count; j++)
			{
				instance.materials.get(j).set(TextureAttribute.createDiffuse(texture));
			}
		}
	}
}
