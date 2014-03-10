package com.etrium.stalagrl.character;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.etrium.stalagrl.system.ControlType;
import com.etrium.stalagrl.KeyMap;
import com.etrium.stalagrl.Map;
import com.etrium.stalagrl.system.EtriumEvent;
import com.etrium.stalagrl.system.EventListener;
import com.etrium.stalagrl.system.EventManager;
import com.etrium.stalagrl.system.EventType;

public class Player implements EventListener
{
	protected int x;
	protected int y;
	protected float z;
	
	protected boolean upHeld = false;
	protected boolean downHeld = false;
	protected boolean leftHeld = false;
	protected boolean rightHeld = false;
	
	private EventManager evtMgr = new EventManager();
	private Map map = null;
	private Camera camera = null;
	private boolean listening = true;
	
	private AssetManager assets;
	private Texture dirtTexture;
	private Environment environment;
	protected ModelInstance instance = null;
	
	public Player(int p_x, int p_y)
	{
		x = p_x;
		y = p_y;
		z = 0.0f;
		
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1.0f, 1.0f, 1.0f, 1.0f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1.0f, -0.8f, -0.2f));
		
		assets = new AssetManager();
		assets.load("data/models/player2.g3db", Model.class);
		assets.finishLoading();
		
		//dirtTexture = new Texture(Gdx.files.internal("data/textures/dirt.png"));
		//dirtTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		evtMgr.RegisterListener(this, EventType.evtControlUp);
		evtMgr.RegisterListener(this, EventType.evtControlDown);
	}
	
	public void SetMap(Map p_map)
	{
		map = p_map;
		z = map.floorMap[x][y].floorLevel;
	}
	
	public void SetCamera(Camera p_camera)
	{
		camera = p_camera;
	}
	
	public void DoControl()
	{
		boolean handled = false;
		
		int movX = 0;
		int movY = 0;
		
		if (upHeld)
		{
			upHeld = false;
			y++;
			movY++;
			handled = true;
		}
		
		if (downHeld)
		{
			downHeld = false;
			y--;
			movY--;
			handled = true;
		}
		
		if (leftHeld)
		{
			leftHeld = false;
			x--;
			movX--;
			handled = true;
		}
		
		if (rightHeld)
		{
			rightHeld = false;
			x++;
			movX++;
			handled = true;
		}
		
		if ((handled) &&
			(assets.update()))
		{
			float movZ = z;
			z = map.floorMap[x][y].floorLevel;
			movZ = z - movZ;
			instance.transform.translate(movX, movY, movZ);
			
			map.CheckPlayerPosition(x, y);
		}
	}
	
	public void Render(ModelBatch batch)
	{
		assert map != null;
		
		if ((instance == null) &&
			(assets.update()))
		{
			instance = new ModelInstance(assets.get("data/models/player2.g3db", Model.class));
			
			instance.transform.translate(x, y, z);
		}
		
		if (instance != null)
		{
			batch.render(instance, environment);
		}
	}
	
	public void CenterMapWindowOnPlayer()
	{
		camera.position.set(x-4.0f, y-4.0f, 6.0f);
		camera.lookAt(x, y, 0.0f);
		camera.update();
	}

	@Override
	public boolean ReceiveEvent(EtriumEvent p_event)
	{
		if (!listening)
			return false;
		
		switch (p_event.type)
		{
			case evtControlUp:
			{
				ControlType ct = (ControlType)p_event.data;
                boolean handled = false;
                switch(ct)
                {
                    case UP:
                    {
                        upHeld = false;
                        handled = true;
                        break;
                    }
                    case DOWN:
                    {
                        downHeld = false;
                        handled = true;
                        break;
                    }
                    case LEFT:
                    {
                        leftHeld = false;
                        handled = true;
                        break;
                    }
                    case RIGHT:
                    {
                        rightHeld = false;
                        handled = true;
                        break;
                    }
                }
                
                if (handled)
                {
                    return true;
                }
                
                break;
			}
			case evtControlDown:
			{
				ControlType ct = (ControlType)p_event.data;
                boolean handled = false;
                switch(ct)
                {
                    case UP:
                    {
                        upHeld = true;
                        handled = true;
                        break;
                    }
                    case DOWN:
                    {
                        downHeld = true;
                        handled = true;
                        break;
                    }
                    case LEFT:
                    {
                        leftHeld = true;
                        handled = true;
                        break;
                    }
                    case RIGHT:
                    {
                        rightHeld = true;
                        handled = true;
                        break;
                    }
                }
                
                if (handled)
                {
                    return true;
                }
                
				break;
			}
		}
		
		return false;
	}

	@Override
	public void StartListening()
	{
		listening = true;
	}

	@Override
	public void StopListening()
	{
		listening = false;
	}
}
