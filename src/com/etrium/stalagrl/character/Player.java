package com.etrium.stalagrl.character;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.etrium.stalagrl.Assets;
import com.etrium.stalagrl.MapCell;
import com.etrium.stalagrl.system.ControlType;
import com.etrium.stalagrl.system.EtriumEvent;
import com.etrium.stalagrl.system.EventListener;
import com.etrium.stalagrl.system.EventManager;
import com.etrium.stalagrl.system.EventType;

public class Player extends Character implements EventListener
{
	protected boolean upHeld = false;
	protected boolean downHeld = false;
	protected boolean leftHeld = false;
	protected boolean rightHeld = false;
	
	private EventManager evtMgr = new EventManager();
	private Camera camera = null;
	private boolean listening = true;
	
	public Player(int p_x, int p_y)
	{
		x = p_x;
		y = p_y;
		z = 0.0f;
		
		collidable = false;
				
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1.0f, 1.0f, 1.0f, 1.0f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1.0f, -0.8f, -0.2f));
		
		assets = new AssetManager();
		assets.load(Assets.modelPlayer, Model.class);
		assets.finishLoading();
		
		evtMgr.RegisterListener(this, EventType.evtControlUp);
		evtMgr.RegisterListener(this, EventType.evtControlDown);
	}
	
	public void SetCamera(Camera p_camera)
	{
		camera = p_camera;
	}
	
	public boolean upClear()
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

	public boolean downClear()
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
	
	public boolean leftClear()
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
	
	public boolean rightClear()
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
	public void DoControl()
	{
		boolean handled = false;
		
		/* Implement collisions here */
		float newRot = 0.0f;
		
		if (upHeld)
		{
			if (upClear())
			{
				upHeld = false;
				y++;
			}
			newRot = 180.0f;
			handled = true;
		}
		
		if (downHeld)
		{
			if (downClear())
			{
				downHeld = false;
				y--;			  
			}
			newRot = 0.0f;
			handled = true;
		}
		
		if (leftHeld)
		{
			if (leftClear())
			{
				leftHeld = false;
				x--;
			}
			newRot = 270.0f;
			handled = true;
		}
		
		if (rightHeld)
		{ 
			if (rightClear())
			{		
				rightHeld = false;
				x++;
			}
			newRot = 90.0f;
			handled = true;
		}
		
		if ((handled) &&
			(assets.update()))
		{
			float movZ = z;
			z = map.floorMap[(int)x][(int)y].floorLevel;
			movZ = z - movZ;
			
			SetPosition(x, y, z);
			instance.transform.rotate(0.0f,  0.0f,  1.0f, newRot);
			
			rot = newRot;
			
			map.CheckPlayerPosition((int)x, (int)y);
		}
	}
	
	public void CenterMapWindowOnPlayer()
	{
		camera.position.set(x-4.0f, y-4.0f, 6.0f);
		camera.lookAt(x, y, 0.0f);
		camera.update();
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public boolean ReceiveEvent(EtriumEvent p_event)
	{
		if (!listening)
			return false;
		
		switch (p_event.type)
		{
			case evtControlUp:
			{
				ControlType ct = (ControlType)p_event.data;
                boolean handled = false;
                switch(ct)
                {
                    case UP:
                    {
                        upHeld = false;
                        handled = true;
                        break;
                    }
                    case DOWN:
                    {
                        downHeld = false;
                        handled = true;
                        break;
                    }
                    case LEFT:
                    {
                        leftHeld = false;
                        handled = true;
                        break;
                    }
                    case RIGHT:
                    {
                        rightHeld = false;
                        handled = true;
                        break;
                    }
                }
                
                if (handled)
                {
                    return true;
                }
                
                break;
			}
			case evtControlDown:
			{
				ControlType ct = (ControlType)p_event.data;
                boolean handled = false;
                switch(ct)
                {
                    case UP:
                    {
                        upHeld = true;
                        handled = true;
                        break;
                    }
                    case DOWN:
                    {
                        downHeld = true;
                        handled = true;
                        break;
                    }
                    case LEFT:
                    {
                        leftHeld = true;
                        handled = true;
                        break;
                    }
                    case RIGHT:
                    {
                        rightHeld = true;
                        handled = true;
                        break;
                    }
                }
                
                if (handled)
                {
                    return true;
                }
                
				break;
			}
		}
		
		return false;
	}

	@Override
	public void StartListening()
	{
		listening = true;
	}

	@Override
	public void StopListening()
	{
		listening = false;
	}
}
