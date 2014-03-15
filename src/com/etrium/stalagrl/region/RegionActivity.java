package com.etrium.stalagrl.region;

public class RegionActivity
{
	public MapRegionRecord record = null;
	public MapRegionType type = MapRegionType.NONE;
	public boolean outOfBounds[][];
	
	public RegionActivity(MapRegionRecord p_record, MapRegionType p_type)
	{
		record = p_record;
		type = p_type;
		
		outOfBounds = new boolean[record.region.map.width][record.region.map.height];
		
		// Set the whole map as out of bounds
		for (int x = 0; x < record.region.map.width; x++)
		{
			for (int y = 0; y < record.region.map.height; y++)
			{
				outOfBounds[x][y] = true;
			}
		}
		
		// Fill in the region activity area as safe.
		for (int x = 0; x < record.region.width; x++)
		{
			for (int y = 0; y < record.region.height; y++)
			{
				outOfBounds[record.x + x][record.y + y] = false;
			}
		}
	}
}
