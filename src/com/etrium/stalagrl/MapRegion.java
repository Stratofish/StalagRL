package com.etrium.stalagrl;

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
}
