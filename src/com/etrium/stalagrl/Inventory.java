package com.etrium.stalagrl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class Inventory
{
  public class ItemData
  {
    public Texture texture;
    public TextureRegion textureRegion;
    public Sprite token;
    public Image image;
    public Item item;
    public Window tooltip;
  }
  
  private Stage stage;
  private Skin skin;
  public Window window;
  
  public Item items[] = new Item[5];    
  public List<ItemData> itemData = new ArrayList<ItemData>();
  
  private int selected;
  
  public Inventory(Stage p_stage, Skin p_skin)
  {
    stage = p_stage;
    skin = p_skin;               
    
    for (int i = 0; i < 5; i++)
    {
      items[i] = null;          
    }
    itemData.clear();
   
    window = new Window("Inventory", skin);
    window.setPosition((Gdx.graphics.getWidth() - 350) / 2, 5);
    window.setSize(350, 96);
    window.setLayoutEnabled(false);

    stage.addActor(window);
    
    /* Start off pointing at first entry */
    selected = 2;
  }

  public boolean AddItem(Item p_item)
  {
    int itemSlot = -1;
    
    int i = 0;
    while (itemSlot == -1 && i < 5)
    {
      if (items[i] == null)
      {        
        itemSlot = i;
        items[i] = p_item;
      }
      
      i++;
    }
    
    if (itemSlot == -1)
      return false;
        
    Update();
    return true;
  }
  
  public void Update()
  {
    window.clear();
    itemData.clear();

    for (int i = 0; i < 5; i++)
    {                                      
      Item item = items[i];
      
      ItemData id = new ItemData();
      
      if (item != null)
      {            
        id.texture = new Texture(Gdx.files.internal(item.GetIconName()));
        id.texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        id.textureRegion = new TextureRegion(id.texture, 0, 0, 64, 64);            
        
        id.token = new Sprite(id.textureRegion);
        id.token.setSize(48, 48);
        id.token.setOrigin(0, 0);
        
        Drawable img = new SpriteDrawable(id.token);
        id.image = new Image(img);
        id.image.setX( 20 + (i * 66));
        id.image.setY( 24);
        
        window.add(id.image);
        
        Label label = new Label( item.GetName(), skin);
        label.setWidth(62);
        label.setX( 13 + (i * 66));
        label.setY( 1);
        label.setAlignment(0);
        label.setFontScale(0.75f);
        
        window.add(label);
      }
      else
      {
        id.texture = null;
        id.textureRegion = null;          
        id.token = null;
        id.image = null;
        
        Label label = new Label( "Empty", skin);
        label.setWidth(62);
        label.setX( 13 + (i * 66));
        label.setY( 1);
        label.setAlignment(0);
        label.setFontScale(0.75f);
        
        window.add(label);
      }
      
      if (i + 1 == selected) 
      {                              
        Texture texture = new Texture(Gdx.files.internal(Assets.iconHighlight));
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        TextureRegion textureRegion = new TextureRegion(texture, 0, 0, 64, 64);            
        
        Sprite token = new Sprite(textureRegion);
        token.setSize(48, 48);
        token.setOrigin(0, 0);
        
        Drawable img = new SpriteDrawable(token);
        Image image = new Image(img);
        image.setX( 20 + (i * 66));
        image.setY( 24);
        
        window.add(image);
        
      }
    }
  }

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
}
