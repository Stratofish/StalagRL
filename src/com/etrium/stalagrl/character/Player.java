package com.etrium.stalagrl.character;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.etrium.stalagrl.system.ControlType;
import com.etrium.stalagrl.Assets;
import com.etrium.stalagrl.Map;
import com.etrium.stalagrl.MapCell;
import com.etrium.stalagrl.system.EtriumEvent;
import com.etrium.stalagrl.system.EventListener;
import com.etrium.stalagrl.system.EventManager;
import com.etrium.stalagrl.system.EventType;

public class Player implements EventListener
{
	protected int x;
	protected int y;
	protected float z;
	
	protected boolean upHeld = false;
	protected boolean downHeld = false;
	protected boolean leftHeld = false;
	protected boolean rightHeld = false;
	
	protected float rot = 0.0f;
	
	private EventManager evtMgr = new EventManager();
	private Map map = null;
	private Camera camera = null;
	private boolean listening = true;
	
	private AssetManager assets;
	private Environment environment;
	protected ModelInstance instance = null;
	
	public Player(int p_x, int p_y)
	{
		x = p_x;
		y = p_y;
		z = 0.0f;
		
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1.0f, 1.0f, 1.0f, 1.0f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1.0f, -0.8f, -0.2f));
		
		assets = new AssetManager();
		assets.load(Assets.modelPlayer, Model.class);
		assets.finishLoading();
		
		//dirtTexture = new Texture(Gdx.files.internal("data/textures/dirt.png"));
		//dirtTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		evtMgr.RegisterListener(this, EventType.evtControlUp);
		evtMgr.RegisterListener(this, EventType.evtControlDown);
	}
	
	public void SetMap(Map p_map)
	{
		map = p_map;
		z = map.floorMap[x][y].floorLevel;
	}
	
	public void SetCamera(Camera p_camera)
	{
		camera = p_camera;
	}
	
	public boolean upClear()
	{
	  boolean result = true; 
	  
	  // Check for current cell wall collision
	  if ((map.floorMap[x][y].collision & MapCell.NORTH) != 0) 	  
	    result = false;
	  else
	  {
  	  // Check neighbouring cell wall collision.
      if ((map.floorMap[x][y + 1].collision & MapCell.SOUTH) != 0)    
        result = false;
	  }
	  
	  return result;
	}

	public boolean downClear()
  {
    boolean result = true; 
    
    // Check for current cell wall collision
    if ((map.floorMap[x][y].collision & MapCell.SOUTH) != 0)    
      result = false;
    else
    {
      // Check neighbouring cell wall collision.
      if ((map.floorMap[x][y - 1].collision & MapCell.NORTH) != 0)    
        result = false;
    }
    
    return result;
  }
	
	public boolean leftClear()
  {
    boolean result = true;
	  
    // Check for current cell wall collision
    if ((map.floorMap[x][y].collision & MapCell.WEST) != 0)    
      result = false;
    else
    {
      // Check neighbouring cell wall collision.
      if ((map.floorMap[x - 1][y].collision & MapCell.EAST) != 0)    
        result = false;
    }
    
    return result;
  }
	
	public boolean rightClear()
  {
    boolean result = true;
	  
	  // Check for current cell wall collision
    if ((map.floorMap[x][y].collision & MapCell.EAST) != 0)    
      result = false;
    else
    {
      // Check neighbouring cell wall collision.
      if ((map.floorMap[x + 1][y].collision & MapCell.WEST) != 0)    
        result = false;
      
    }
	
    return result;
  }
	
	public void DoControl()
	{
		boolean handled = false;
		
		/* Implement collisions here */
		int movX = 0;
		int movY = 0;
		float newRot = 0.0f;
		
		if (upHeld)
		{
			if (upClear())
			{
				upHeld = false;
				y++;
				movY++;
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
				movY--;
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
				movX--;
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
				movX++;
			}
			newRot = 90.0f;
			handled = true;
		}
		
		if ((handled) &&
			(assets.update()))
		{
			float movZ = z;
			z = map.floorMap[x][y].floorLevel;
			movZ = z - movZ;
			
			//instance.transform.translate(movX, movY, movZ);
			instance.transform.idt();
			instance.transform.translate(x+0.5f, y+0.5f, z);
			instance.transform.rotate(0.0f,  0.0f,  1.0f, newRot);
			
			rot = newRot;
			//instance.transform.translate(0.0f, 0.0f, 0.0f);
			
			map.CheckPlayerPosition(x, y);
		}
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
