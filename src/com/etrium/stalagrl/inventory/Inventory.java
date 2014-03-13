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
    if (items.size() < maxItems)
    {
      items.add(p_item);    
      if (inventoryHandler != null) 
        inventoryHandler.Update();
      return true;
    }
    
    return false;
  }
  
  public Item RemoveSelectedItem()
  {
    if (inventoryHandler != null)
    {
      int selected = inventoryHandler.selected;
      
      if (selected < GetItemCount())
      {
        Item item = items.get(selected);
        items.remove(inventoryHandler.selected);
        inventoryHandler.Update();
        return item;
      }
    }
      
    return null;
  }   
  
  public void DoControl()
  {
    if (inventoryHandler != null)
      inventoryHandler.DoControl();
  }
}
