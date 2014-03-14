package com.etrium.stalagrl.character;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.etrium.stalagrl.Assets;
import com.etrium.stalagrl.MapCell;
import com.etrium.stalagrl.system.EventManager;
import com.etrium.stalagrl.system.EventType;

public class Guard extends Character
{
	protected int direction = MapCell.SOUTH; 
	
	private EventManager evtMgr = new EventManager();
	
	protected GuardRole role = new GuardRolePerimeter(); 
	
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
	
	public boolean UpClear()
	{
	  boolean result = true; 
	  
	  // Check for current cell wall collision
	  if ((map.floorMap[(int)x][(int)y].GetCollision() & MapCell.NORTH) != 0) 	  
		  result = false;
	  else
	  {
		  // Check neighbouring cell wall collision.
		  if ((map.floorMap[(int)x][(int)y + 1].GetCollision() & MapCell.SOUTH) != 0)    
			  result = false;
	  }
	  
	  return result;
	}

	public boolean DownClear()
  {
    boolean result = true; 
    
    // Check for current cell wall collision
    if ((map.floorMap[(int)x][(int)y].GetCollision() & MapCell.SOUTH) != 0)    
      result = false;
    else
    {
      // Check neighbouring cell wall collision.
      if ((map.floorMap[(int)x][(int)y - 1].GetCollision() & MapCell.NORTH) != 0)    
        result = false;
    }
    
    return result;
  }
	
	public boolean LeftClear()
  {
    boolean result = true;
	  
    // Check for current cell wall collision
    if ((map.floorMap[(int)x][(int)y].GetCollision() & MapCell.WEST) != 0)    
      result = false;
    else
    {
      // Check neighbouring cell wall collision.
      if ((map.floorMap[(int)x - 1][(int)y].GetCollision() & MapCell.EAST) != 0)    
        result = false;
    }
    
    return result;
  }
	
	public boolean RightClear()
  {
    boolean result = true;
	  
	  // Check for current cell wall collision
    if ((map.floorMap[(int)x][(int)y].GetCollision() & MapCell.EAST) != 0)    
      result = false;
    else
    {
      // Check neighbouring cell wall collision.
      if ((map.floorMap[(int)x + 1][(int)y].GetCollision() & MapCell.WEST) != 0)    
        result = false;
      
    }

    return result;
  }

	@Override
	public boolean DoControl()
	{
		super.DoControl();
		return false;
	}}
