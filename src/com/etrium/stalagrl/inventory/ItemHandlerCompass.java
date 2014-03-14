package com.etrium.stalagrl.inventory;

import com.etrium.stalagrl.system.Log;

public class ItemHandlerCompass extends ItemHandler
{
  public ItemHandlerCompass()
  {
  }
  
  @Override
  public boolean UseItem()
  {
    Log.action("Wow it points north!");

    return true;
  }
}