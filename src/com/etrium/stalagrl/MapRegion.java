package com.etrium.stalagrl;

import java.awt.peer.LightweightPeer;

import com.etrium.stalagrl.Map;

public class MapRegion
{
	public int width = 1;
	public int height = 1;
	public int type = Map.FLOOR_DIRT;
	public String modelType;
	public RegionLight lights[];
	public int lightCount = 0;
	public float floorLevel = 0.0f;
}
