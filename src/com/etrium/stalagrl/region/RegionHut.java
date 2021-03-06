package com.etrium.stalagrl.region;

import com.badlogic.gdx.math.Vector2;
import com.etrium.stalagrl.Assets;
import com.etrium.stalagrl.Map;
import com.etrium.stalagrl.MapCell;

public class RegionHut extends MapRegion
{
	public RegionHut(Map p_map)
	{
		super.SetMap(p_map);
		width = 10;
		height = 5;
		type = Map.FLOOR_STONES;
		modelType = Assets.modelHut;
		floorLevel = 0.3f;			
		
		/* Allocate array for collision map */
		collisionMap = new int[width][height];
		
		for (int w = 0; w < width; w++)
	    {
	      for (int h = 0; h < height; h++)
	      {                
	        collisionMap[w][h] = 0;               
	        
	        if (!((w == 1) && (h == 0))) // Door        
	        {
	          // Hut walls
	          if (h == 0) 
	            collisionMap[w][h] |= MapCell.SOUTH;  
	          
	          if (h == height - 1)
	            collisionMap[w][h] |= MapCell.NORTH;
	          
	          if (w == 0)
	            collisionMap[w][h] |= MapCell.WEST;
	          
	          if (w == width - 1)
	            collisionMap[w][h] |= MapCell.EAST;
	        }
	      }           
	    }
		
	  /* Add collision values for beds */
		addBed( 4, 0);
		addBed( 6, 0);
		addBed( 8, 0);
		
		addBed( 4, 3);
	    addBed( 6, 3);
	    addBed( 8, 3);
    
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
		
		texture = map.hutTexture;
	}
	
	/*public void SetMap(Map p_map)
	{
		super.SetMap(map);
		
		texture = map.hutTexture;
	}*/
	
	@Override
	public void MakeModel(MapRegionRecord record)
	{
		super.MakeModel(record);
	}
	
	private void addBed( int p_bedx, int p_bedy)
	{
	   collisionMap[p_bedx][p_bedy]     |= MapCell.WEST | MapCell.EAST | MapCell.SOUTH;
	   collisionMap[p_bedx][p_bedy + 1] |= MapCell.WEST | MapCell.EAST | MapCell.NORTH;
	   
	   hiddingPlaces.add( new Vector2( (float)p_bedx, (float)p_bedy)); 
	   hiddingPlaces.add( new Vector2( (float)p_bedx, (float)p_bedy + 1));
	}
}
