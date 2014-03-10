package com.etrium.stalagrl;

public class RegionHut extends MapRegion
{
	public RegionHut()
	{
		width = 10;
		height = 5;
		type = Map.FLOOR_STONES;
		modelType = Assets.modelHut;
		floorLevel = 0.3f;
		
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
	}
}
