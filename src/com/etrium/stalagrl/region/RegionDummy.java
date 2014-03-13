package com.etrium.stalagrl.region;

import com.etrium.stalagrl.Map;

public class RegionDummy extends MapRegion
{
	public RegionDummy(Map p_map, int p_width, int p_height)
	{
		super.SetMap(p_map);
		
		width = p_width;
		height = p_height;
		
		lightCount = 0;
	}
}
