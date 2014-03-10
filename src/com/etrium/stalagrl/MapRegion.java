package com.etrium.stalagrl;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.etrium.stalagrl.Map;

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
	
	Map map = null;

	public MapRegion()
	{
	}
	
	public void SetMap(Map p_map)
	{
		map = p_map;
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
	
	public void AddRegionToMap(MapRegionRecord record)
	{
		record.modelInstance = new ModelInstance(map.assets.get(record.region.modelType, Model.class));
		record.modelInstance.transform.translate(record.x, record.y, 0.0f);
		
		int count = record.modelInstance.materials.size;
		for (int j = 0; j < count; j++)
		{
			record.modelInstance.materials.get(j).set(TextureAttribute.createDiffuse(map.woodFloorTexture));
		}
		
		record.environment = new Environment();
		record.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1.0f));
		
		for (int x = 0; x < record.region.width; x++)
		{
			for (int y = 0; y < record.region.height; y++)
			{
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
}
