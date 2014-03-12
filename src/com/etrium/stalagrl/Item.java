package com.etrium.stalagrl;

import com.etrium.stalagrl.Assets;
import com.etrium.stalagrl.ItemType;

public class Item
{
  public static class ItemDetail
  {
    ItemType itemType;
    String   icon;
    String   name;
    
    public ItemDetail( ItemType p_itemType, String p_icon, String p_name)
    {
      itemType = p_itemType;
      icon     = p_icon;
      name     = p_name;
    } 
  }
  
  private static final ItemDetail[] itemDetails = new ItemDetail[]
      { new ItemDetail( ItemType.EMPTY      , Assets.iconBorder     , "Empty")
      , new ItemDetail( ItemType.COMPASS    , Assets.iconCompass    , "Compass")
      , new ItemDetail( ItemType.CROWBAR    , Assets.iconCrowbar    , "Crowbar")
      , new ItemDetail( ItemType.KEY        , Assets.iconKey        , "Key")
      , new ItemDetail( ItemType.LOCKPICK   , Assets.iconLockpick   , "Lockpick")
      , new ItemDetail( ItemType.PAPERS     , Assets.iconPapers     , "Papers")
      , new ItemDetail( ItemType.SPADE      , Assets.iconSpade      , "Spade")
      , new ItemDetail( ItemType.WATCH      , Assets.iconWatch      , "Watch")
      , new ItemDetail( ItemType.WIRECUTTERS, Assets.iconWirecutters, "Wirecutters")
      };
  
  private static final int itemDetailCount = 9; 
  
  private ItemDetail Details;
  
  public Item(ItemType p_itemType)
  {    
    Details = null;
    
    for (int d = 0; d < itemDetailCount; d++)
    {
      if (itemDetails[d].itemType == p_itemType)
        Details = itemDetails[d];
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
