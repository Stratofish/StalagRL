package com.etrium.stalagrl.character;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.etrium.stalagrl.MapCell;
import com.etrium.stalagrl.inventory.Inventory;
import com.etrium.stalagrl.inventory.Item;
import com.etrium.stalagrl.system.ControlType;
import com.etrium.stalagrl.system.EtriumEvent;
import com.etrium.stalagrl.system.EventListener;
import com.etrium.stalagrl.system.EventManager;
import com.etrium.stalagrl.system.EventType;
import com.etrium.stalagrl.system.Log;

public class Player extends Character implements EventListener
{
	protected boolean upHeld = false;
	protected boolean downHeld = false;
	protected boolean leftHeld = false;
	protected boolean rightHeld = false;
	protected boolean useHeld = false;
	protected boolean dropHeld = false;
	
	protected int direction = MapCell.SOUTH; 
	
	private EventManager evtMgr = new EventManager();
	private Camera camera = null;
	private boolean listening = true;	
	private Inventory inventory = null;
	
	public Player(int p_x, int p_y)
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
	}
	
	public void SetInventory(Inventory p_inventory)
	{
	  inventory = p_inventory;
	}
	
	public void SetCamera(Camera p_camera)
	{
		camera = p_camera;
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
	
	private boolean TakeItemFromCell( MapCell p_takeFromCell)
	{       
	  Item item = p_takeFromCell.hiddenItem;
	  
	  if (item != null)
	  {	  
	    if (inventory.AddItem(item))
	    {
	      /* If item is successfully applied to players inventory then remove it from the MapCell object. */
	      p_takeFromCell.hiddenItem = null;
	      return true;
	    }
	  }
	  return false;
	}		
	
  private boolean TakeItemFromFloor( MapCell p_takeFromCell)
  {
    Item item = p_takeFromCell.PickupFloorItem(null);
   
    if (item != null)
    {
      if (inventory.AddItem(item))
      {
        return true;
      }
      
      /* If the inventory was full then put the item back on the floor */
      p_takeFromCell.DropFloorItem(item, null);
    }
    return false;
  }
	
	public void UseItem()
	{
	  /* First of all call the item handler UseItem method for the currently selected item */
	  Item currentPlayerItem = inventory.GetCurrentItem();
	  
	  boolean actionTaken = false;
	  
	  if (currentPlayerItem != null)
	    actionTaken = currentPlayerItem.UseItem();	  
	  
	  /* If use item has taken action already then we don't need to do any further processing */
	  if (!actionTaken)
	  {	  
  	  MapCell testCell = map.floorMap[x][y];
  	  	  
  	  /* Check the cell the player is in */
  	  if ((testCell.hiddingPlace) && (testCell.hiddenItem != null))
  	  {
        Item hiddenItem = testCell.hiddenItem;
  	  
  	    if (TakeItemFromCell( testCell))        
          Log.action("You have found " + hiddenItem.GetItemNewPrefix().toLowerCase() + " " + hiddenItem.GetName().toLowerCase());         
        else
          Log.action("Your inventory is full");
  	  }
  	  else
  	  {
  	    /* Check the cell the player is facing */
  	    switch (direction)
  	    {
  	      case MapCell.NORTH:
  	      {
  	        testCell = map.floorMap[x][y + 1];
  	        break;
  	      }
  	      case MapCell.SOUTH:
          {
            testCell = map.floorMap[x][y - 1];
            break;
          }
  	      case MapCell.WEST:
          {
            testCell = map.floorMap[x - 1][y];
            break;
          }
  	      case MapCell.EAST:
          {
            testCell = map.floorMap[x + 1][y];
            break;
          }
  	    }	    	   
  	    	    
  	    Item hiddenItem = testCell.hiddenItem;
  	    
  	    if ((testCell.hiddingPlace) && (testCell.hiddenItem != null))	    	     
    	    if (TakeItemFromCell( testCell))
    	      Log.action("You have found " + hiddenItem.GetItemNewPrefix().toLowerCase() + " " + hiddenItem.GetName().toLowerCase());
  	      else
    	      Log.action("Your inventory is full");	            	   
  	    else	    
  	    {
  	      /* No hidden item tile in front of the player so check the floor of the cell
  	         the player is standing on */
  	      MapCell playersCell = map.floorMap[x][y];
  	      
  	      if (playersCell.FloorItemCount() > 0)
  	      {	        
  	        Item floorItem = playersCell.floorItems.get(0);
  	        
  	        if (TakeItemFromFloor( playersCell))	          
  	          Log.action("You have picked up " + floorItem.GetItemNewPrefix().toLowerCase() + " " + floorItem.GetName().toLowerCase() + " onto the floor");	        
  	        else
  	          Log.action("Your inventory is full");	        
  	      }
  	      else
  	        Log.action("There is nothing here");
  	    }
	    }
	  }
	}
	
	private void DropItem()
	{
	  Item removedItem = inventory.RemoveSelectedItem();
	  
	  if (removedItem == null)
	  {
	    Log.action("No item to drop");
	  }
	  else
	  {
	    MapCell testCell = map.floorMap[x][y];
      
	    /* Check the cell the player is in */
	    if ((testCell.hiddingPlace) && (testCell.hiddenItem == null))           
	    {
	      testCell.hiddenItem = removedItem;	      
	      Log.action("You have hidden " + removedItem.GetItemOldPrefix().toLowerCase() + " " + removedItem.GetName().toLowerCase());
	    }                      
	    else
	    {
	      /* Check the cell the player is facing */
	      switch (direction)
	      {  
	        case MapCell.NORTH:
	        {
	          testCell = map.floorMap[x][y + 1];
	          break;
	        }
	        case MapCell.SOUTH:
	        {
	          testCell = map.floorMap[x][y - 1];
	          break;
	        }
	        case MapCell.WEST:
	        {
	          testCell = map.floorMap[x - 1][y];
	          break;
	        }
	        case MapCell.EAST:
	        {
	          testCell = map.floorMap[x + 1][y];
	          break;
	        }
	      }

	      if ((testCell.hiddingPlace) && (testCell.hiddenItem == null))            
	      {
	        testCell.hiddenItem = removedItem;       
	        Log.action("You have hidden " + removedItem.GetItemOldPrefix().toLowerCase() + " " + removedItem.GetName().toLowerCase());  
	      }                  
	      else      
	      {
	        map.floorMap[x][y].DropFloorItem( removedItem, null);
	    
	        Log.action("You have dropped " + removedItem.GetItemOldPrefix().toLowerCase() + " " + removedItem.GetName().toLowerCase() + " onto the floor");	        
	      }
	    }
	  }
	}
	
	@Override
	public boolean DoControl()
	{
		boolean handled = false;
		
		/* Implement collisions here */
		float newRot = rot; 
		
		if (upHeld)
		{
			if (UpClear())
			{				
			  y++;
			}
			upHeld = false;
			newRot = 180.0f;
			direction = MapCell.NORTH;
			handled = true;
		}
		
		if (downHeld)
		{
			if (DownClear())
			{
				y--;			  
			}
			downHeld = false;
			newRot = 0.0f;
			direction = MapCell.SOUTH;
			handled = true;
		}
		
		if (leftHeld)
		{
			if (LeftClear())
			{
				x--;
			}
			leftHeld = false;
			newRot = 270.0f;
			direction = MapCell.WEST;
			handled = true;
		}
		
		if (rightHeld)
		{ 
			if (RightClear())
			{		
				x++;
			}
			rightHeld = false;
			newRot = 90.0f;
			direction = MapCell.EAST;
			handled = true;
		}

		if (useHeld)
		{
			UseItem();
			useHeld = false;           
			handled = true;
		}
		
		if (dropHeld)
		{
		  DropItem();
		  dropHeld = false;
		  handled = true;
		}
    
		if ((handled) &&
			(map.assets.update()))
		{
			float movZ = z;
			z = map.floorMap[(int)x][(int)y].floorLevel;
			movZ = z - movZ;
			
			SetPosition(x, y, z);
			instance.transform.rotate(0.0f,  0.0f,  1.0f, newRot);
			
			rot = newRot;
			
			map.CheckPlayerPosition((int)x, (int)y);
			
			EtriumEvent evt = new EtriumEvent();
			evt.type = EventType.evtTimeIncrease;
			evt.data = null;
			evtMgr.SendEvent(evt, false);
			
			return true;
		}
		
		return false;
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
                    case USE:
                    {
                      useHeld = false;
                      handled = true;
                      break;
                    }
                    case DROP:
                    {
                      dropHeld = false;
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
                    case USE:
                    {
                      useHeld = true;
                      handled = true;
                      break;
                    }
                    case DROP:
                    {
                      dropHeld = true;
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
		
		return super.ReceiveEvent(p_event);
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
