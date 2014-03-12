package com.etrium.stalagrl.character;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.etrium.stalagrl.Assets;
import com.etrium.stalagrl.Map;
import com.etrium.stalagrl.MapCell;
import com.etrium.stalagrl.system.EtriumEvent;
import com.etrium.stalagrl.system.EventListener;
import com.etrium.stalagrl.system.EventManager;
import com.etrium.stalagrl.system.EventType;

public class Character implements EventListener
{
	protected int x;
	protected int y;
	protected float z;
	
	protected int oldX;
	protected int oldY;
	
	protected boolean collidable = true;
	
	protected boolean upHeld = false;
	protected boolean downHeld = false;
	protected boolean leftHeld = false;
	protected boolean rightHeld = false;
	
	protected float rot = 0.0f;
	
	protected Map map = null;
	
	protected AssetManager assets;
	protected Environment environment;
	protected ModelInstance instance = null;
	protected EventManager evtMgr = new EventManager();
	
	public Character()
	{
	}
	
	public Character(int p_x, int p_y)
	{
		x = p_x;
		y = p_y;
		z = 0.0f;
		
		oldX = 0;
		oldY = 0;
		
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1.0f, 1.0f, 1.0f, 1.0f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1.0f, -0.8f, -0.2f));
		
		assets = new AssetManager();
		assets.load(Assets.modelPlayer, Model.class);
		assets.finishLoading();
		
		evtMgr.RegisterListener(this, EventType.evtGlobalLightLevel);
	}
	
	public void SetPosition(float p_x, float p_y, float p_z)
	{
		x = (int)p_x;
		y = (int)p_y;
		z = p_z;
		
		map.floorMap[oldX][oldY].charCollision = 0;
		
		oldX = x;
		oldY = y;
		
		if (collidable)
			map.floorMap[x][y].charCollision = MapCell.NORTH | MapCell.SOUTH | MapCell.EAST | MapCell.WEST;
		
		if (instance != null)
		{
			instance.transform.idt();
			instance.transform.translate(x+0.5f, y+0.5f, z);
		}
	}
	
	public int GetX()
	{
		return x;
	}
	
	public int GetY()
	{
		return y;
	}
	
	public void SetMap(Map p_map)
	{
		map = p_map;
		z = map.floorMap[(int)x][(int)y].floorLevel;
		
		SetPosition(x,  y,  z);
	}
	
	public void DoControl()
	{
	}
	
	public void Render(ModelBatch batch)
	{
		assert map != null;
		
		if ((instance == null) &&
			(assets.update()))
		{
			instance = new ModelInstance(assets.get(Assets.modelPlayer, Model.class));
			
			instance.transform.translate(x+0.5f, y+0.5f, z);
		}
		
		if (instance != null)
		{
			batch.render(instance, environment);
		}
	}

	@Override
	public boolean ReceiveEvent(EtriumEvent p_event)
	{
		if (p_event.type == EventType.evtGlobalLightLevel)
		{
			float level = (float)p_event.data;
			environment.set(new ColorAttribute(ColorAttribute.AmbientLight, level, level, level, 1.0f));
			return false;
		}
		
		return false;
	}

	@Override
	public void StartListening()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void StopListening()
	{
		// TODO Auto-generated method stub
		
	}
}
