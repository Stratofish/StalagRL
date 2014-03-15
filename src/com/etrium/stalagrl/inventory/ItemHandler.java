package com.etrium.stalagrl.inventory;

public class ItemHandler
{
  @SuppressWarnings("unused")
private Item item;
  
  public ItemHandler()
  {
  }
  
  public void setItem( Item p_item)
  {
    item = p_item;
  }
  
  public boolean UseItem()
  {
    return false;
  }
}
