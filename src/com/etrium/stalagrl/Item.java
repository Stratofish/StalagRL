package com.etrium.stalagrl;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.etrium.stalagrl.Map;
import com.etrium.stalagrl.Assets;

public class Item
{
  private String name;
  
  public Item(String p_name)
  {
    name = p_name;
  }
    
  public String GetIconName()
  {
     return Assets.iconPapers;
  }
  
  public String GetName()
  {
     return name;
  }
}
