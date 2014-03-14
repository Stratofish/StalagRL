package com.etrium.stalagrl;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.math.Vector2;
import com.etrium.stalagrl.inventory.Item;

public class MapCell
{
	static public final int NORTH = 1;
	static public final int EAST  = 2;
	static public final int SOUTH = 4;
	static public final int WEST  = 8;
  
	public int type;
	public float floorLevel;
	public int collision = 0;
	public int charCollision = 0;
	
	public boolean hiddingPlace = false;
	public Item hiddenItem = null;
	
	public ArrayList<Item> floorItems = new ArrayList<Item>();
	
	private Map map;
	private int x;
	private int y;
	
	public MapCell( Map p_map, int p_x, int p_y)	
	{
	  map = p_map;
	  x = p_x;
	  y = p_y;
	}
	
	public int GetCollision()
	{
		return collision | charCollision;		
	}
	
	public int FloorItemCount()
	{
	  return floorItems.size();
	}
	
	public void DropFloorItem( Item p_item, Environment p_environment)
  {
	  int floorItemCount = FloorItemCount();
	  	  
	  if (floorItemCount != 0)	  
	  {
	    /* Get the renderer from the first item and remove it from the map and the item itself */
	    ItemRenderer renderer = floorItems.get(0).GetItemRenderer();
	    map.floorItems.remove(renderer);	    
	    floorItems.get(0).SetItemRenderer(null);
	  }
	    
	  /* Create a new renderer for the new item that will be on the floor */
	  ItemRenderer renderer = new ItemRenderer( map
                                            , new Vector2( (float)x, (float)y)
                                            , map.itemTextures[p_item.GetItemType().ordinal()]
                                            , map.environment);
                                            //p_environment);
    p_item.SetItemRenderer(renderer); 
	  map.floorItems.add(renderer);
	  
	  floorItems.add( 0, p_item);
  }
	
  public Item PickupFloorItem( Environment p_environment)
  {
    int floorItemCount = floorItems.size();
    
    if (floorItemCount != 0)
    {
      Item pickupItem = floorItems.get(0);
      ItemRenderer renderer = pickupItem.GetItemRenderer();
      map.floorItems.remove(renderer);
      pickupItem.SetItemRenderer(null);
      floorItems.remove(pickupItem);
      
      if (floorItems.size() > 0)
      {
        Item topItem = floorItems.get(0);
        
        /* Create a new renderer for the new item that will be on the floor */
        renderer = new ItemRenderer( map
                                   , new Vector2( (float)x, (float)y)
                                   , map.itemTextures[topItem.GetItemType().ordinal()]
                                   , map.environment);
                                   //p_environment);
        topItem.SetItemRenderer(renderer); 
        map.floorItems.add(0, renderer);        
      }
      
      return pickupItem;
    }
    
    return null;
  }	
}