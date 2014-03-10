package com.etrium.stalagrl;

public class RegionTower extends MapRegion
{
	public RegionTower(Map p_map)
	{
		super.SetMap(p_map);
		
		width = 3;
		height = 3;
		type = Map.FLOOR_GRASS;
		modelType = Assets.modelTower;
		floorLevel = 0.0f;
		
		/* Allocate array for collision map */
		collisionMap = new int[width][height];
		
		for (int w = 0; w < width; w++)
	    {
	      for (int h = 0; h < height; h++)
	      {
	    	  collisionMap[w][h] = 0;
	      }
	    }
		
		lightCount = 3;
		lights = new RegionLight[3];
		
		RegionLight light = new RegionLight();
		light.x = 1.5f;
		light.y = 0.2f;
		light.z = 2.0f;
		light.r = 1.0f;
		light.g = 1.0f;
		light.b = 1.0f;
		light.intensity = 2.0f;
		light.external = true;
		lights[0] = light;
		
		light = new RegionLight();
		light.x = 2.5f;
		light.y = 2.5f;
		light.z = 2.2f;
		light.r = 1.0f;
		light.g = 1.0f;
		light.b = 1.0f;
		light.intensity = 8.0f;
		lights[1] = light;
		
		light = new RegionLight();
		light.x = 7.5f;
		light.y = 2.5f;
		light.z = 2.2f;
		light.r = 1.0f;
		light.g = 1.0f;
		light.b = 1.0f;
		light.intensity = 8.0f;
		lights[2] = light;
	}
}
