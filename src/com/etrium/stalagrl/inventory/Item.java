package com.etrium.stalagrl.inventory;

import com.etrium.stalagrl.Assets;
import com.etrium.stalagrl.inventory.ItemType;

public class Item
{
  public static class ItemDetail
  {
    ItemType itemType;
    String   icon;
    String   name;
    String   handlerClass;
    
    public ItemDetail( ItemType p_itemType, String p_icon, String p_name, String p_handlerClass)
    {
      itemType     = p_itemType;
      icon         = p_icon;
      name         = p_name;      
      handlerClass = p_handlerClass;
    }
  }
   
  private static final ItemDetail[] itemDetails = new ItemDetail[]
      { new ItemDetail( ItemType.EMPTY      , Assets.iconBorder     , "Empty"      , null) 
      , new ItemDetail( ItemType.COMPASS    , Assets.iconCompass    , "Compass"    , "itemHandlerCompass")
      , new ItemDetail( ItemType.CROWBAR    , Assets.iconCrowbar    , "Crowbar"    , "itemHandlerCrowbar")
      , new ItemDetail( ItemType.KEY        , Assets.iconKey        , "Key"        , "itemHandlerKey")
      , new ItemDetail( ItemType.LOCKPICK   , Assets.iconLockpick   , "Lockpick"   , "itemHandlerLockpick")
      , new ItemDetail( ItemType.PAPERS     , Assets.iconPapers     , "Papers"     , "itemHandlerPapers")
      , new ItemDetail( ItemType.SPADE      , Assets.iconSpade      , "Spade"      , "itemHandlerSpade")
      , new ItemDetail( ItemType.WATCH      , Assets.iconWatch      , "Watch"      , "itemHandlerWatch")
      , new ItemDetail( ItemType.WIRECUTTERS, Assets.iconWirecutters, "Wirecutters", "itemHandlerWirecutters")
      };
  
  private static final int itemDetailCount = 9; 
  
  private ItemDetail Details;  
  private ItemHandler itemHandler;
  
  public Item(ItemType p_itemType) 
  {    
    Details = null;
    
    for (int d = 0; d < itemDetailCount; d++)
    {
      if (itemDetails[d].itemType == p_itemType)
        Details = itemDetails[d];
    }
    
    itemHandler = null;    
    if (Details != null)
    {
      if (Details.handlerClass != null)
      try {
        itemHandler = (ItemHandler) Class.forName(Details.handlerClass).newInstance();
      }
      catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
        itemHandler = null;
      }
    }
  }

  public ItemType GetItemType()
  {
     return Details.itemType;
  }
  
  public String GetIconName()
  {
     return Details.icon;
  }
  
  public String GetName()
  {
     return Details.name;
  }
}
