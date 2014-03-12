package com.etrium.stalagrl.region;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.etrium.stalagrl.Map;
import com.etrium.stalagrl.MapCell;

public class RegionExtrude extends MapRegion
{
	public static final int EAST = 0;
	public static final int SOUTH = 1;
	public static final int WEST = 2;
	public static final int NORTH = 3;
	
	protected int direction;
	protected int length;
	public int xOffset = 0;
	public int yOffset = 0;
	protected int xinc = 0;
	protected int yinc = 0;
	protected String modelName;
	protected int collEdge = 0;
	protected float modelX;
	protected float modelY;
	
	public RegionExtrude(Map p_map, int p_direction, int p_length, String p_modelName)
	{
		super.SetMap(p_map);
		
		direction = p_direction;
		length = p_length;
		modelName = p_modelName;
		width = 1;
		height = 1;
		
		
		if (direction == EAST)
		{
			width = length;
			xinc = 1;
			collEdge = MapCell.NORTH;
			modelX = 0.5f;
			modelY = 1.0f;
		}
		
		if (direction == SOUTH)
		{
			height = length;
			yOffset = length-1;
			yinc = -1;
			collEdge = MapCell.EAST;
			modelX = 1.0f;
			modelY = 0.5f;
		}
		
		if (direction == WEST)
		{
			width = length;
			xOffset = length-1;
			xinc = -1;
			collEdge = MapCell.SOUTH;
			modelX = 0.5f;
			modelY = 0.0f;
		}
		
		if (direction == NORTH)
		{
			height = length;
			yinc = 1;
			collEdge = MapCell.WEST;
			modelX = 0.0f;
			modelY = 0.5f;
		}
		
		type = 0;
		this.modelType = modelName;
		type = Map.FLOOR_STONES;
		floorLevel = 0.0f;
		
		/* Allocate array for collision map */
		collisionMap = new int[width][height];
		
		for (int i = 0; i < length; i++)
		{
	    	 collisionMap[(i * Math.abs(xinc))][(i * Math.abs(yinc))] = collEdge;
	    }
		
		lightCount = 0;
	}
	
	@Override
	public void AddRegionToMap(MapRegionRecord record)
	{
		MakeModel(record);
		
		record.environment = new Environment();
		record.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1.0f));
		
		for (int w = 0; w < width; w++)
	    {
			for (int h = 0; h < height; h++)
			{
				int x = record.x + w - xOffset;
				int y = record.y + h - yOffset;
				
				if ((x >= 0) &&
					(y >= 0) &&
					(x < map.width) &&
					(y < map.height))
				{
					if (record.region.type > 0)
						map.floorMap[x][y].type = record.region.type;
					
					map.floorMap[x][y].floorLevel = record.region.floorLevel;
				}
			}
	    }
		
		for (int j = 0; j < record.region.lightCount; j++)
		{
			RegionLight light = record.region.lights[j];
			record.environment.add(new PointLight().set(light.r, light.g, light.b, record.x+light.x + xOffset, record.y+light.y + yOffset, light.z, light.intensity));
			if (light.external)
				map.environment.add(new PointLight().set(light.r, light.g, light.b, record.x+light.x + xOffset, record.y+light.y + yOffset, light.z, light.intensity));
		}
	}
	
	@Override
	public void AddCollisionData(MapRegionRecord record)
	{
		for (int w = 0; w < width; w++)
	    {
			for (int h = 0; h < height; h++)
			{
				int x = record.x + w - xOffset;
				int y = record.y + h - yOffset;
				
				if ((x >= 0) &&
					(y >= 0) &&
					(x < map.width) &&
					(y < map.height))
					map.floorMap[x][y].collision |= record.region.collisionMap[w][h]; 
			}
	    }
	}

	@Override
	public void MakeModel(MapRegionRecord record)
	{
		for (int i = 0; i < length; i++)
		{
			int x = record.x + (i * xinc);
			int y = record.y + (i * yinc);
			
			ModelInstance instance = new ModelInstance(map.assets.get(record.region.modelType, Model.class));
			instance.transform.translate(x + modelX, y + modelY, 0.0f);
			instance.transform.rotate(0.0f,  0.0f, 1.0f, (direction * -90.0f));

			Node node = instance.getNode("Walls");
			if (node != null)
			{
				node.parts.get(0).material.set(new BlendingAttribute(true, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, 1.0f));
			}
				
			record.modelInstances.add(instance);
			
			int count = instance.materials.size;
			for (int j = 0; j < count; j++)
			{
				instance.materials.get(j).set(TextureAttribute.createDiffuse(map.barbedWireTexture));
			}
		}
	}
}
