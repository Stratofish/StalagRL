package com.etrium.stalagrl;

import java.util.ArrayList;

import com.etrium.stalagrl.inventory.Item;

public class MapCell
{
	static public final int NORTH = 1;
	static public final int EAST  = 2;
	static public final int SOUTH = 4;
	static public final int WEST  = 8;
  
	public int type;
	public float floorLevel;
	public int collision = 0;
	public int charCollision = 0;
	
	public boolean hiddingPlace = false;
	public Item hiddenItem = null;
	
	public ArrayList<Item> floorItems = new ArrayList<Item>();

	public int GetCollision()
	{
		return collision | charCollision;
	}
}