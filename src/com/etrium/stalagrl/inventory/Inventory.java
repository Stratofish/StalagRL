package com.etrium.stalagrl.inventory;

import java.util.ArrayList;
import java.util.List;

public class Inventory
{ 
  private InventoryHandler inventoryHandler = null;  
  
  public List<Item> items = new ArrayList<Item>();  
  
  private int maxItems; 

  private void init(int p_maxItems)
  {     
    maxItems = p_maxItems;
    
    // Initially the list will be empty
    items.clear();
  }
  
  public Inventory(int p_maxItems)    
  {
    init( p_maxItems);
  }
  
  public Inventory(int p_maxItems, InventoryHandler p_handler)
  {
    init( p_maxItems);

    inventoryHandler = p_handler;
    inventoryHandler.SetInventory(this);
  }

  public int GetItemCount()
  {
    return items.size();
  }

  public int GetMaxItems()
  {
    return maxItems;
  }
  
  public boolean AddItem(Item p_item)
  {
    if (items.size() < 6)
    {
      items.add(p_item);    
      if (inventoryHandler != null) 
        inventoryHandler.Update();
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
    if (inventoryHandler != null)
      inventoryHandler.DoControl();
  }
}
