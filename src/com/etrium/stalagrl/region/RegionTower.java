package com.etrium.stalagrl.region;

import com.etrium.stalagrl.Assets;
import com.etrium.stalagrl.Map;

public class RegionTower extends MapRegion
{
	public RegionTower(Map p_map)
	{
		super.SetMap(p_map);
		
		width = 3;
		height = 3;
		type = Map.FLOOR_CONCRETE;
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
		
		lightCount = 0;
	}
}
