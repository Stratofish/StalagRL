package com.etrium.stalagrl;

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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.etrium.stalagrl.system.ControlType;
import com.etrium.stalagrl.system.EtriumEvent;
import com.etrium.stalagrl.system.EventListener;
import com.etrium.stalagrl.system.EventManager;
import com.etrium.stalagrl.system.EventType;
import com.etrium.stalagrl.Inventory;

public class InventoryRenderer implements EventListener
{
  public class ItemData
  {      
    public Texture texture;
    public TextureRegion textureRegion;
    public Sprite token;
    public Image image;    
  }

  protected boolean inventory1Held = false;
  protected boolean inventory2Held = false;
  protected boolean inventory3Held = false;
  protected boolean inventory4Held = false;
  protected boolean inventory5Held = false;    
  protected boolean inventoryBackHeld = false;
  protected boolean inventoryFWDHeld = false;
  
  private EventManager evtMgr = new EventManager();
  private boolean listening = true;
  
  private Stage stage;
  private Skin skin;
  public Window window;
  
  private List<ItemData> itemData = new ArrayList<ItemData>();
  private Inventory inventory;
  
  public InventoryRenderer( Stage p_stage, Skin p_skin)
  {
    stage = p_stage;
    skin = p_skin;
    
    itemData.clear();

    window = new Window("Inventory", skin);
    window.setPosition((Gdx.graphics.getWidth() - 350) / 2, 5);
    window.setSize(350, 96);
    window.setLayoutEnabled(false);

    stage.addActor(window);
    
    inventory = null;
    
    evtMgr.RegisterListener(this, EventType.evtControlUp);
    evtMgr.RegisterListener(this, EventType.evtControlDown);
    evtMgr.RegisterListener(this, EventType.evtResize);
  }
  
  public void SetInventory( Inventory p_inventory)
  {
    inventory = p_inventory;
    Update();
  }
  
  public void Update()
  {
    if (inventory == null)
      return;
    
    int maxItems = inventory.GetMaxItems();
    int itemCount = inventory.GetItemCount();
    int selected = inventory.GetSelected();
    
    window.clear();
    itemData.clear();
    
    for (int i = 0; i < maxItems; i++)
    {
      Item item = null;
      if (i < itemCount)        
        item = inventory.items.get(i);
      else
      {
        item = new Item(ItemType.EMPTY); 
      }
      
      ItemData id = new ItemData();

      id.texture = new Texture(Gdx.files.internal(item.GetIconName()));
      id.texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
      id.textureRegion = new TextureRegion(id.texture, 0, 0, 64, 64);
        
      id.token = new Sprite(id.textureRegion);
      id.token.setSize(48, 48);
      id.token.setOrigin(0, 0);
        
      Drawable img = new SpriteDrawable(id.token);
      id.image = new Image(img);
      id.image.setX( 20 + (i * 66));
      id.image.setY( 24);
        
      window.add(id.image);
        
      Label label = new Label( item.GetName(), skin);
      label.setWidth(62);
      label.setX( 13 + (i * 66));
      label.setY( 5);
      label.setAlignment(0);
      label.setFontScale(0.80f);
        
      window.add(label);
      
      if (i + 1 == selected)
      {                              
        Texture texture = new Texture(Gdx.files.internal(Assets.iconHighlight));
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        TextureRegion textureRegion = new TextureRegion(texture, 0, 0, 64, 64);            
        
        Sprite token = new Sprite(textureRegion);
        token.setSize(48, 48);
        token.setOrigin(0, 0);
        
        img = new SpriteDrawable(token);
        Image image = new Image(img);
        image.setX( 20 + (i * 66));
        image.setY( 24);
        
        window.add(image);
      }
    }
  }  

  public void DoControl()
  {
    boolean handled = false;
    
    if (inventory1Held)    
    {
      inventory1Held = false;
      //selected = 1;
      handled = true;
    }
    
    if (inventory2Held)    
    {
      inventory2Held = false;
      //selected = 2;
      handled = true;
    }
    
    if (inventory3Held)    
    {
      inventory3Held = false;
      //selected = 3;
      handled = true;
    }
    
    if (inventory4Held)    
    {
      inventory4Held = false;
      //selected = 4;
      handled = true;
    }
    
    if (inventory5Held)    
    {
      inventory5Held = false;
      //selected = 5;
      handled = true;
    }
    
    if (inventoryBackHeld)    
    {
      inventoryBackHeld = false;
      
      //if ( selected == 1)
        //selected = 5;
      //else
        //selected--;
        
      handled = true;
    }
    
    if (inventoryFWDHeld)    
    {
      inventoryFWDHeld = false;
      
      //if ( selected == 5)
        //selected = 1;
      //else
//        selected++;
        
      handled = true;
    }    

    if (handled)      
      Update();
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
