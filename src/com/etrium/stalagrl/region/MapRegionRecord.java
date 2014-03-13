package com.etrium.stalagrl.region;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.model.Node;

public class MapRegionRecord
{
	public MapRegion region;
	public int x = 0;
	public int y = 0;
	public List<ModelInstance> modelInstances = null;
	public Environment environment = null;
	public boolean dummy = false;
	
	public MapRegionRecord()
	{
	}
	
	public MapRegionRecord(boolean p_dummy)
	{
		dummy = p_dummy;
	}
	
	public void HideRoof()
	{
		if (!dummy)
		{
			int instanceCount = modelInstances.size();
			if (instanceCount > 0)
			{
				for (int i = 0; i < instanceCount; i++)
				{
					Node node = modelInstances.get(i).getNode("Roof");
					if (node != null)
					{
						node.parts.get(0).material.set(ColorAttribute.createDiffuse(1.0f, 1.0f, 1.0f, 0.0f));
						node.parts.get(0).material.set(new BlendingAttribute(true, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, 0.0f));
					}
				}
			}
		}
	}
	
	public void ShowRoof()
	{
		if (!dummy)
		{
			int instanceCount = modelInstances.size();
			if (instanceCount > 0)
			{
				for (int i = 0; i < instanceCount; i++)
				{
					Node node = modelInstances.get(i).getNode("Roof");
					if (node != null)
					{
						node.parts.get(0).material.set(ColorAttribute.createDiffuse(1.0f, 1.0f, 1.0f, 1.0f));
						node.parts.get(0).material.set(new BlendingAttribute(true, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, 1.0f));
					}
				}
			}
		}
	}

	public boolean InRegion(int p_x, int p_y)
	{
		if ((p_x >= x) &&
			(p_y >= y) &&
			(p_x < x + region.width) &&
			(p_y < y + region.height))
			return true;
		
		return false;
	}
	
	public void AddCollisionData()
	{
		if (!dummy)
			region. AddCollisionData(this);
	}
	
	public void AddHiddingPlaces()
	{
		if (!dummy)
			region.AddHiddingPlaceData(this);
	}
	
	public void AddRegiontoMap()
	{
		if (!dummy)
		{
			modelInstances = new ArrayList<ModelInstance>();
			region.AddRegionToMap(this);
		}
	}
	
	public void Render(ModelBatch modelBatch)
	{
		if (!dummy)
		{
			int instanceCount = modelInstances.size();
			if (instanceCount > 0)
			{
				for (int i = 0; i < instanceCount; i++)
				{
					ModelInstance instance = modelInstances.get(i);
					if (instance != null)
						modelBatch.render(instance, environment);
				}
			}
		}
	}
}
