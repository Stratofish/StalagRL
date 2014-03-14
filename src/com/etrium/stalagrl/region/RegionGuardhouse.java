package com.etrium.stalagrl.region;

import com.etrium.stalagrl.Assets;
import com.etrium.stalagrl.Map;
import com.etrium.stalagrl.MapCell;

public class RegionGuardhouse extends MapRegion
{
	public RegionGuardhouse(Map p_map)
	{
		super.SetMap(p_map);
		width = 50;
		height = 19;
		type = Map.FLOOR_CONCRETE;
		modelType = Assets.modelGuardhouse;
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
		
		//Borders
		AddBlock(0, 10, 14, 9);
		AddBlock(0, 0, 1, 10);
		AddBlock(1, 0, 1, 1);
		AddBlock(3, 0, 34, 1);
		AddBlock(38, 0, 7, 1);
		AddBlock(45, 0, 5, 10);
		AddBlock(41, 10, 9, 9);
		AddBlock(30, 18, 11, 1);
		AddBlock(14, 16, 16, 3);
		
		// Cell + storage bit
		AddBlock(4, 9, 1, 1);
		AddBlock(4, 1, 1, 1);
		AddBlock(4, 3, 1, 5);
		AddBlock(5, 4, 6, 1);
		AddBlock(11, 1, 3, 9);
		
		// Corridor bits
		AddBlock(23, 1, 7, 4);
		AddBlock(23, 5, 1, 1);
		AddBlock(23, 7, 1, 1);
		AddBlock(23, 8, 2, 8);
		AddBlock(29, 15, 1, 1);
		AddBlock(29, 13, 1, 1);
		AddBlock(28, 8, 2, 5);
		
		// Between offices
		AddBlock(30, 10, 11, 1);
		AddBlock(34, 11, 1, 3);
		AddBlock(34, 15, 1, 3);
		
		// Add collision values for furniture */
		AddBlock(7, 3, 2, 1);
		
		AddBlock(14, 13, 2, 1);
		AddBlock(14, 10, 2, 1);
		AddBlock(14, 6, 2, 1);
		AddBlock(14, 3, 2, 1);
		AddBlock(21, 13, 2, 1);
		AddBlock(21, 10, 2, 1);
		AddBlock(21, 3, 2, 1);
		AddBlock(18, 14, 1, 2);
		AddBlock(18, 1, 1, 2);
		
		AddBlock(31, 15, 3, 1);
		AddBlock(32, 16, 1, 1);
		AddBlock(33, 11, 1, 2);
		
		AddBlock(37, 17, 2, 1);	
		AddBlock(38, 13, 1, 3);
		AddBlock(39, 14, 1, 1);
		
		lightCount = 3;
		lights = new RegionLight[11];
		
		RegionLight light = new RegionLight();
		light.x = 2.5f;
		light.y = 5.5f;
		light.z = 3.5f;
		light.r = 1.0f;
		light.g = 1.0f;
		light.b = 1.0f;
		light.intensity = 5.0f;
		lights[0] = light;
		
		light = new RegionLight();
		light.x = 8f;
		light.y = 7.5f;
		light.z = 3.5f;
		light.r = 1.0f;
		light.g = 1.0f;
		light.b = 1.0f;
		light.intensity = 5.0f;
		lights[1] = light;
		
		light = new RegionLight();
		light.x = 8f;
		light.y = 2.5f;
		light.z = 3.5f;
		light.r = 1.0f;
		light.g = 1.0f;
		light.b = 1.0f;
		light.intensity = 5.0f;
		lights[2] = light;
		
		light = new RegionLight();
		light.x = 18.5f;
		light.y = 13f;
		light.z = 3.5f;
		light.r = 1.0f;
		light.g = 1.0f;
		light.b = 1.0f;
		light.intensity = 5.0f;
		lights[3] = light;
		
		light = new RegionLight();
		light.x = 18.5f;
		light.y = 4f;
		light.z = 3.5f;
		light.r = 1.0f;
		light.g = 1.0f;
		light.b = 1.0f;
		light.intensity = 5.0f;
		lights[4] = light;
		
		light = new RegionLight();
		light.x = 26.5f;
		light.y = 14f;
		light.z = 3.5f;
		light.r = 1.0f;
		light.g = 1.0f;
		light.b = 1.0f;
		light.intensity = 5.0f;
		lights[5] = light;
		
		light = new RegionLight();
		light.x = 26.5f;
		light.y = 6.5f;
		light.z = 3.5f;
		light.r = 1.0f;
		light.g = 1.0f;
		light.b = 1.0f;
		light.intensity = 5.0f;
		lights[6] = light;
		
		light = new RegionLight();
		light.x = 32f;
		light.y = 14.5f;
		light.z = 3.5f;
		light.r = 1.0f;
		light.g = 1.0f;
		light.b = 1.0f;
		light.intensity = 5.0f;
		lights[7] = light;
		
		light = new RegionLight();
		light.x = 38f;
		light.y = 14.5f;
		light.z = 3.5f;
		light.r = 1.0f;
		light.g = 1.0f;
		light.b = 1.0f;
		light.intensity = 5.0f;
		lights[8] = light;
		
		light = new RegionLight();
		light.x = 33f;
		light.y = 5.5f;
		light.z = 3.5f;
		light.r = 1.0f;
		light.g = 1.0f;
		light.b = 1.0f;
		light.intensity = 5.0f;
		lights[9] = light;
		
		light = new RegionLight();
		light.x = 42f;
		light.y = 5.5f;
		light.z = 3.5f;
		light.r = 1.0f;
		light.g = 1.0f;
		light.b = 1.0f;
		light.intensity = 5.0f;
		lights[10] = light;
		
		texture = null;
	}
	
	@Override
	public void MakeModel(MapRegionRecord record)
	{
		texture = null;
		super.MakeModel(record);
	}
	
	private void AddBlock( int p_x, int p_y, int p_width, int p_height)
	{
		for (int x = 0; x < p_width; x++)
		{
			collisionMap[p_x+x][p_y] |= MapCell.SOUTH;
			collisionMap[p_x+x][p_y + p_height - 1] |= MapCell.NORTH;
		}
		
		for (int y = 0; y < p_height; y++)
		{
			collisionMap[p_x][p_y+y] |= MapCell.WEST;
			collisionMap[p_x + p_width - 1][p_y + y] |= MapCell.EAST;
		}
	}
}
