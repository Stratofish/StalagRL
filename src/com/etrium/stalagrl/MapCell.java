package com.etrium.stalagrl;

public class MapCell
{
  static public final int NORTH = 1;
  static public final int EAST  = 2;
  static public final int SOUTH = 4;
  static public final int WEST  = 8;
  
	public int type;
	public float floorLevel;
	public int collision;
}
