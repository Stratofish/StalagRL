package com.etrium.stalagrl;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Vector2;

public class FloorItem
{
  private Map map;
  private Vector2 coordinates;  
  private Texture texture;
  ModelInstance instance;
  Environment environment; 
  
  public FloorItem( Map p_map, Vector2 p_coordinates, Texture p_texture, Environment p_environment)
  {
    map = p_map;
    coordinates = p_coordinates;
    texture = p_texture;
    environment = p_environment;
    
    instance = new ModelInstance(map.assets.get(Assets.modelItemMesh, Model.class));
    instance.transform.translate(coordinates.x, coordinates.y, 0.0f);    

    Node node = instance.getNode("Walls");
    if (node != null)
    {
      node.parts.get(0).material.set(new BlendingAttribute(true, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, 1.0f));
    }
    
    int count = instance.materials.size;
    for (int j = 0; j < count; j++)
    {
      instance.materials.get(j).set(TextureAttribute.createDiffuse(texture));
    }    
  }
  
  public void Render( ModelBatch modelBatch)
  {
   System.out.println("Test");
    modelBatch.render(instance, environment);
  }
}
