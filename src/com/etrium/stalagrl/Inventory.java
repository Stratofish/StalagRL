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

public class Inventory
{ 
  private InventoryRenderer renderer = null;  
  
  public List<Item> items = new ArrayList<Item>();  

  private int selected;
  private int maxItems; 

  private void init(int p_maxItems)
  {     
    maxItems = p_maxItems;
    
    // Initially the list will be empty
    items.clear();
    
    /* Start off pointing at first entry */
    selected = 1;        
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
    if (renderer != null)
      renderer.DoControl();
  }
}
