package com.etrium.stalagrl.inventory;

import java.util.ArrayList;

public class Inventory
{
  private InventoryHandler inventoryHandler = null;

  public ArrayList<Item> items = new ArrayList<Item>();

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
  
  public void RemoveItem(Item item)
  {
    int id = -1;
    
    for (int i = 0; i < items.size(); i++)
    {
      if (items.get(i) == item)    
        id = i;
    }
    
    if (id != -1)
      items.remove(id);
    
    if (inventoryHandler != null) 
      inventoryHandler.Update();
  }
  
  public void DoControl()
  {
    if (inventoryHandler != null)
      inventoryHandler.DoControl();
  }
}
