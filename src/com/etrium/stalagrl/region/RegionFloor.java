package com.etrium.stalagrl.region;

import com.etrium.stalagrl.Map;

public class RegionFloor extends MapRegion
{
	public RegionFloor(Map p_map, int p_type, int p_width, int p_height)
	{
		super.SetMap(p_map);

		width = p_width;
		height = p_height;
		type = p_type;
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
