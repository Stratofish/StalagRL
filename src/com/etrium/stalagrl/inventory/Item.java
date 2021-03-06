package com.etrium.stalagrl.inventory;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.etrium.stalagrl.Assets;
import com.etrium.stalagrl.ItemRenderer;
import com.etrium.stalagrl.inventory.ItemType;

public class Item
{
  public static class ItemDetail
  {
    ItemType itemType;
    String   icon;
    String   oldInfo;
    String   newInfo;
    String   name;
    String   handlerClass;    
    
    public ItemDetail( ItemType p_itemType, String p_icon, String p_oldInfo, String p_newInfo, String p_name, String p_handlerClass)
    {
      itemType     = p_itemType;
      icon         = p_icon;
      oldInfo      = p_oldInfo; 
      newInfo      = p_newInfo;
      name         = p_name;      
      handlerClass = p_handlerClass;
    }
  }
   
  private static final ItemDetail[] itemDetails = new ItemDetail[]
      { new ItemDetail( ItemType.EMPTY      , Assets.iconBorder     , ""   , "" , "Empty"      , null) 
      , new ItemDetail( ItemType.COMPASS    , Assets.iconCompass    , "The", "A", "Compass"    , "ItemHandlerCompass")
      , new ItemDetail( ItemType.CROWBAR    , Assets.iconCrowbar    , "The", "A", "Crowbar"    , "ItemHandlerCrowbar")
      , new ItemDetail( ItemType.KEY        , Assets.iconKey        , "The", "A", "Key"        , "ItemHandlerKey")
      , new ItemDetail( ItemType.LOCKPICK   , Assets.iconLockpick   , "The", "A", "Lockpick"   , "ItemHandlerLockpick")
      , new ItemDetail( ItemType.PAPERS     , Assets.iconPapers     , "The", "" , "Papers"     , "ItemHandlerPapers")
      , new ItemDetail( ItemType.SPADE      , Assets.iconSpade      , "The", "A", "Spade"      , "ItemHandlerSpade")
      , new ItemDetail( ItemType.WATCH      , Assets.iconWatch      , "The", "A", "Watch"      , "ItemHandlerWatch")
      , new ItemDetail( ItemType.WIRECUTTERS, Assets.iconWireCutters, "The", "" , "Wirecutters", "ItemHandlerWirecutters")
      };  
  private static final int itemDetailCount = 9; 
  
  private ItemDetail Details;  
  private ItemHandler itemHandler;
  private ItemRenderer itemRenderer;
  
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
        itemHandler = (ItemHandler) Class.forName("com.etrium.stalagrl.inventory." + Details.handlerClass).newInstance();
      }
      catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {        
        itemHandler = null;
        System.out.println(e.toString());
      }
    }
  }

  public String GetItemOldPrefix()
  {
    return Details.oldInfo;
  }

  public String GetItemNewPrefix()
  {
    return Details.newInfo;
  }

  public ItemRenderer GetItemRenderer()
  {
    return itemRenderer;
  }
  
  public void SetItemRenderer( ItemRenderer p_renderer)
  {
    itemRenderer = p_renderer;
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
  
  public void Render(ModelBatch modelBatch)
  {
    if (itemRenderer != null)
    {
      itemRenderer.Render(modelBatch);
    }
  }
  
  public boolean UseItem()
  {    
   
    if (itemHandler != null)
    {
      return itemHandler.UseItem();
    }
    else
      return false;
  }
}

