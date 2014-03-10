package com.etrium.stalagrl;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.model.Node;

public class MapRegionRecord
{
	public MapRegion region;
	public int x = 0;
	public int y = 0;
	public ModelInstance modelInstance = null;
	public Environment environment = null;
	
	public void HideRoof()
	{
		Node node = modelInstance.getNode("Roof");
		node.parts.get(0).material.set(ColorAttribute.createDiffuse(1.0f, 1.0f, 1.0f, 0.0f));
		node.parts.get(0).material.set(new BlendingAttribute(true, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, 0.0f));
	}
	
	public void ShowRoof()
	{
		Node node = modelInstance.getNode("Roof");
		node.parts.get(0).material.set(ColorAttribute.createDiffuse(1.0f, 1.0f, 1.0f, 1.0f));
		node.parts.get(0).material.set(new BlendingAttribute(true, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, 1.0f));
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
}
