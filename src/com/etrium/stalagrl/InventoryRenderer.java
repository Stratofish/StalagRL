package com.etrium.stalagrl;

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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.etrium.stalagrl.system.EventType;
import com.etrium.stalagrl.Inventory;

public class InventoryRenderer
{
  public class ItemData
  {      
    public Texture texture;
    public TextureRegion textureRegion;
    public Sprite token;
    public Image image;    
  }
  
  private Stage stage;
  private Skin skin;
  public Window window;
  
  private List<ItemData> itemData = new ArrayList<ItemData>();
  private Inventory inventory;
  
  public InventoryRenderer( Stage p_stage, Skin p_skin)
  {
    stage = p_stage;
    skin = p_skin;
    
    itemData.clear();

    window = new Window("Inventory", skin);
    window.setPosition((Gdx.graphics.getWidth() - 350) / 2, 5);
    window.setSize(350, 96);
    window.setLayoutEnabled(false);

    stage.addActor(window);
    
    inventory = null;
  }
  
  public void SetInventory( Inventory p_inventory)
  {
    inventory = p_inventory;
    Update();
  }
  
  public void Update()
  {
    if (inventory == null)
      return;
    
    int maxItems = inventory.GetMaxItems();
    int itemCount = inventory.GetItemCount();
    int selected = inventory.GetSelected();
    
    window.clear();
    itemData.clear();
    
    for (int i = 0; i < maxItems; i++)
    {
      Item item = null;
      if (i < itemCount)        
        item = inventory.items.get(i);
      else
      {
        item = new Item(ItemType.EMPTY); 
      }
      
      ItemData id = new ItemData();

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
      label.setY( 5);
      label.setAlignment(0);
      label.setFontScale(0.80f);
        
      window.add(label);
      
      if (i + 1 == selected)
      {                              
        Texture texture = new Texture(Gdx.files.internal(Assets.iconHighlight));
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        TextureRegion textureRegion = new TextureRegion(texture, 0, 0, 64, 64);            
        
        Sprite token = new Sprite(textureRegion);
        token.setSize(48, 48);
        token.setOrigin(0, 0);
        
        img = new SpriteDrawable(token);
        Image image = new Image(img);
        image.setX( 20 + (i * 66));
        image.setY( 24);
        
        window.add(image);
      }
    }
  }  
}
