package com.etrium.stalagrl.region;

public class RegionActivity
{
	public MapRegionRecord record = null;
	public MapRegionType type = MapRegionType.NONE;
	
	public RegionActivity(MapRegionRecord p_record, MapRegionType p_type)
	{
		record = p_record;
		type = p_type;
	}
}
