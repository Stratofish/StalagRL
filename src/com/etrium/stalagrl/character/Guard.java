package com.etrium.stalagrl.character;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.etrium.stalagrl.Assets;
import com.etrium.stalagrl.Map;
import com.etrium.stalagrl.MapCell;
import com.etrium.stalagrl.system.EventManager;
import com.etrium.stalagrl.system.EventType;

public class Guard extends Character
{
	protected int direction = MapCell.SOUTH; 
	
	private EventManager evtMgr = new EventManager();
	
	protected GuardRole role; 
	
	public Guard(int p_x, int p_y)
	{
		x = p_x;
		y = p_y;
		z = 0.0f;
		
		collidable = false;
				
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1.0f, 1.0f, 1.0f, 1.0f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1.0f, -0.8f, -0.2f));
		
		evtMgr.RegisterListener(this, EventType.evtControlUp);
		evtMgr.RegisterListener(this, EventType.evtControlDown);
		evtMgr.RegisterListener(this, EventType.evtGlobalLightLevel);
		
		modelName = Assets.modelGuard;
		
	}
	
	public void SetMap(Map p_map)
	{
		super.SetMap(p_map);
		
		role = new GuardRolePerimeter(map, this);
	}
	
	@Override
	public boolean DoControl()
	{
		role.DoControl();
		
		//super.DoControl();
		return false;
	}}
