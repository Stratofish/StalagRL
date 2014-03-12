package com.etrium.stalagrl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.etrium.stalagrl.system.ControlType;
import com.etrium.stalagrl.system.EtriumEvent;
import com.etrium.stalagrl.system.EventListener;
import com.etrium.stalagrl.system.EventManager;
import com.etrium.stalagrl.system.EventType;

public class Inventory implements EventListener
{
  protected boolean inventory1Held = false;
  protected boolean inventory2Held = false;
  protected boolean inventory3Held = false;
  protected boolean inventory4Held = false;
  protected boolean inventory5Held = false;    
  protected boolean inventoryBackHeld = false;
  protected boolean inventoryFWDHeld = false;

  private EventManager evtMgr = new EventManager();
  private InventoryRenderer renderer = null;  
  
  public List<Item> items = new ArrayList<Item>();  

  private int selected;
  private int maxItems;

  private boolean listening = true;

  private void init(int p_maxItems)
  {     
    maxItems = p_maxItems;
    
    // Initially the list will be empty
    items.clear();
    
    /* Start off pointing at first entry */
    selected = 1;
        
    evtMgr.RegisterListener(this, EventType.evtControlUp);
    evtMgr.RegisterListener(this, EventType.evtControlDown);
    evtMgr.RegisterListener(this, EventType.evtResize);
  }
  
  public Inventory(int p_maxItems)    
  {
    init( p_maxItems);
  }
  
  public Inventory(int p_maxItems, InventoryRenderer p_renderer)
  {
    init( p_maxItems);

    renderer = p_renderer;
    renderer.SetInventory(this);
  }

  public int GetItemCount()
  {
    return items.size();
  }

  public int GetMaxItems()
  {
    return maxItems;
  }
  
  public int GetSelected()
  {
    return selected;
  }
  
  public boolean AddItem(Item p_item)
  {
    if (items.size() < 6)
    {
      items.add(p_item);    
      if (renderer != null) 
        renderer.Update();
      return true;
    }
    
    return false;
  }
  


  /*
  public void RemoveItem(Item item)
  {
    ItemData id = null;
    
    for (int i = 0; i < itemData.size(); i++)
    {
      if (itemData.get(i).item == item)
      {
        id = itemData.get(i);
      }
    }
    
    if (id != null)
    {
      itemData.remove(id);
    }
    
    //items.remove(item);
    
    Update();
  }
  */
  
  public void DoControl()
  {
    boolean handled = false;
    
    if (inventory1Held)    
    {
      inventory1Held = false;
      selected = 1;
      handled = true;
    }
    
    if (inventory2Held)    
    {
      inventory2Held = false;
      selected = 2;
      handled = true;
    }
    
    if (inventory3Held)    
    {
      inventory3Held = false;
      selected = 3;
      handled = true;
    }
    
    if (inventory4Held)    
    {
      inventory4Held = false;
      selected = 4;
      handled = true;
    }
    
    if (inventory5Held)    
    {
      inventory5Held = false;
      selected = 5;
      handled = true;
    }
    
    if (inventoryBackHeld)    
    {
      inventoryBackHeld = false;
      
      if ( selected == 1)
        selected = 5;
      else
        selected--;
        
      handled = true;
    }
    
    if (inventoryFWDHeld)    
    {
      inventoryFWDHeld = false;
      
      if ( selected == 5)
        selected = 1;
      else
        selected++;
        
      handled = true;
    }    

    if (handled)
      if (renderer != null)
        renderer.Update();
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
                    case INVENTORY1:
                    {
                      inventory1Held = false;
                      handled = true;
                      break;
                    }
                    case INVENTORY2:
                    {
                      inventory2Held = false;
                      handled = true;
                      break;
                    }
                    case INVENTORY3:
                    {
                      inventory3Held = false;
                      handled = true;
                      break;
                    }
                    case INVENTORY4:
                    {
                      inventory4Held = false;
                      handled = true;
                      break;
                    }
                    case INVENTORY5:
                    {
                      inventory5Held = false;
                      handled = true;
                      break;
                    }
                    case INVENTORYBACK:
                    {
                      inventoryBackHeld = false;
                      handled = true;
                      break;
                    }
                    case INVENTORYFWD:
                    {
                      inventoryFWDHeld = false;
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
                  case INVENTORY1:
                  {
                    inventory1Held = true;
                    handled = true;
                    break;
                  }
                  case INVENTORY2:
                  {
                    inventory2Held = true;
                    handled = true;
                    break;
                  }
                  case INVENTORY3:
                  {
                    inventory3Held = true;
                    handled = true;
                    break;
                  }
                  case INVENTORY4:
                  {
                    inventory4Held = true;
                    handled = true;
                    break;
                  }
                  case INVENTORY5:
                  {
                    inventory5Held = true;
                    handled = true;
                    break;
                  }
                  case INVENTORYBACK:
                  {
                    inventoryBackHeld = true;
                    handled = true;
                    break;
                  }
                  case INVENTORYFWD:
                  {
                    inventoryFWDHeld = true;
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
      case evtResize:
      {
    	  window.setPosition((Gdx.graphics.getWidth() - 350) / 2, 5);
    	  
    	  // Let other receivers get this event
    	  return false;
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
