package com.etrium.stalagrl.region;

import com.badlogic.gdx.math.Vector2;
import com.etrium.stalagrl.Assets;
import com.etrium.stalagrl.Map;
import com.etrium.stalagrl.MapCell;

public class RegionFood extends MapRegion
{
	public RegionFood(Map p_map)
	{
		super.SetMap(p_map);
		width = 15;
		height = 9;
		type = Map.FLOOR_GRASS;
		modelType = Assets.modelFood;
		floorLevel = 0.0f;			
		
		/* Allocate array for collision map */
		collisionMap = new int[width][height];
		
		
		/* Add collision values for tables */
		addTable(1, 2);
		addTable(1, 6);
		addTable(6, 2);
		addTable(6, 6);
		
		collisionMap[13][1]     |= MapCell.WEST | MapCell.EAST | MapCell.SOUTH;
		collisionMap[13][2]     |= MapCell.WEST | MapCell.EAST;
		collisionMap[13][3]     |= MapCell.WEST | MapCell.EAST;
		collisionMap[13][4]     |= MapCell.WEST | MapCell.EAST;
		collisionMap[13][5]     |= MapCell.WEST | MapCell.EAST;
		collisionMap[13][6]     |= MapCell.WEST | MapCell.EAST;
		collisionMap[13][7]     |= MapCell.WEST | MapCell.EAST | MapCell.NORTH;
	    
	    hiddingPlaces.add( new Vector2( 13, 1));
	    hiddingPlaces.add( new Vector2( 13, 7));
    
		lightCount = 0;

		texture = map.foodTexture;
	}
	
	/*public void SetMap(Map p_map)
	{
		super.SetMap(map);
		
		texture = map.hutTexture;
	}*/
	
	private void addTable( int p_x, int p_y)
	{
	   collisionMap[p_x][p_y]     |= MapCell.WEST | MapCell.SOUTH | MapCell.NORTH;
	   collisionMap[p_x+1][p_y]     |= MapCell.SOUTH | MapCell.NORTH;
	   collisionMap[p_x+2][p_y]     |= MapCell.SOUTH | MapCell.NORTH;
	   collisionMap[p_x+3][p_y]     |= MapCell.EAST | MapCell.SOUTH | MapCell.NORTH;
	}
}
