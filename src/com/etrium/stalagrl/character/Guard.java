package com.etrium.stalagrl.character;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.etrium.stalagrl.Assets;
import com.etrium.stalagrl.Map;
import com.etrium.stalagrl.MapCell;
import com.etrium.stalagrl.system.EtriumEvent;
import com.etrium.stalagrl.system.EventManager;
import com.etrium.stalagrl.system.EventType;
import com.etrium.stalagrl.system.Log;

public class Guard extends Character
{
	protected int direction = MapCell.SOUTH; 
	
	private EventManager evtMgr = new EventManager();
	
	protected GuardRole role;
	public boolean patrolling = true;
	
	public Guard()
	{
		x = 0;
		y = 0;
		z = 0.0f;
		
		collidable = false;
				
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1.0f, 1.0f, 1.0f, 1.0f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1.0f, -0.8f, -0.2f));
		
		evtMgr.RegisterListener(this, EventType.evtGlobalLightLevel);
		evtMgr.RegisterListener(this, EventType.evtPlayerOOB);
		
		modelName = Assets.modelGuard;
	}
	
	public void SetMap(Map p_map)
	{
		super.SetMap(p_map);
		
		float rnd = (float) Math.random();
		System.out.println(rnd);
		if (rnd < 0.25f)
			role = new GuardRolePerimeter(map, this);
		else if (rnd < 0.5f)
			role = new GuardRoleExcercise(map, this);
		else if (rnd < 0.9f)
			role = new GuardRoleCompound(map, this);
		else
			role = new GuardRoleGuardhouse(map, this);
	}
	
	@Override
	public boolean DoControl()
	{
		role.DoControl(patrolling);
		
		//super.DoControl();
		return false;
	}

	@Override
	public boolean ReceiveEvent(EtriumEvent p_event)
	{
		if (p_event.type == EventType.evtPlayerOOB)
		{
			Guard guard = (Guard)p_event.data;
			if (guard == this)
			{
				if (patrolling)
					Log.action("Out of bounds! - You should be at " + map.currentRegionActivity.type);
				
				patrolling = false;
				
				return true;
			}
		}
		
		return super.ReceiveEvent(p_event);
	}
}