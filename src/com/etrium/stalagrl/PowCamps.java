package com.etrium.stalagrl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.etrium.stalagrl.inventory.Inventory;
import com.etrium.stalagrl.inventory.InventoryHandler;
import com.etrium.stalagrl.inventory.Item;
import com.etrium.stalagrl.inventory.ItemType;
import com.etrium.stalagrl.character.Guard;
import com.etrium.stalagrl.character.Player;
import com.etrium.stalagrl.character.Character;
import com.etrium.stalagrl.system.ControlType;
import com.etrium.stalagrl.system.EventType;
import com.etrium.stalagrl.system.EtriumEvent;
import com.etrium.stalagrl.system.EventListener;
import com.etrium.stalagrl.system.EventManager;
import com.etrium.stalagrl.system.Log;

public class PowCamps implements EventListener
{
	private static final int POW_COUNT = 20;
	private static final int GUARD_COUNT = 20;
	
	private EventManager evtMgr;
	private KeyMap keyMap;
	private Map map;
	private Camera camera;
	private ModelBatch modelBatch;
	private Stage guiStage;
	private Skin guiSkin;
	private Inventory inventory;
	private boolean listening;
	
	private Window quitDialog = null;
	private Label quitLabel = null;
	private TextButton quitYesButton = null;
	private TextButton quitNoButton = null;
	private boolean quitDialogVisible = false;
	protected Player player = null;
	protected Character pows[] = new Character[POW_COUNT];
	protected Guard guards[] = new Guard[GUARD_COUNT];
	
	protected CampTime campTime = new CampTime();
	
	protected GUITime guiTime = null;
	protected GUIActivity guiActivity = null;

	public PowCamps()
	{
		evtMgr = new EventManager();
		this.StartListening();
		
		keyMap = new KeyMap();
		
		guiStage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		Gdx.input.setInputProcessor(guiStage);
        
		guiSkin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		guiTime = new GUITime(guiSkin, guiStage);
		guiActivity = new GUIActivity(guiSkin, guiStage);
	    
		Log.CreateLog( guiStage, guiSkin);
		
		evtMgr.RegisterListener(this, EventType.evtLogActivity);
		evtMgr.RegisterListener(this, EventType.evtCharDead);
		evtMgr.RegisterListener(this, EventType.evtUpLevel);
		evtMgr.RegisterListener(this, EventType.evtDownLevel);
		evtMgr.RegisterListener(this, EventType.evtControlDown);
		evtMgr.RegisterListener(this, EventType.evtControlUp);
		evtMgr.RegisterListener(this, EventType.evtPlayerUIChanged);
		evtMgr.RegisterListener(this, EventType.evtQuitConfirm);
		evtMgr.RegisterListener(this, EventType.evtResize);
		evtMgr.RegisterListener(this, EventType.evtActivityLeadStart);
		evtMgr.RegisterListener(this, EventType.evtActivityStart);
		
		modelBatch = new ModelBatch(new DefaultShaderProvider());
		
		float ar = ((float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()) * 1.5f;
		
		camera = new OrthographicCamera(20, 18*ar);
		camera.position.set(-4.0f, -4.0f, 6.0f);
		camera.lookAt(0.0f, 0.0f, 0.0f);
		camera.up.x = 0.0f;
		camera.up.y = 0.0f;
		camera.up.z = 1.0f;
		camera.near = -50.0f;
		camera.far = 1000.0f;
		camera.update(true);
		
		map = new Map(100, 100);
		map.SetCamera(camera);
		
		player = new Player(50, 35);
		player.SetCamera(camera);
		player.SetMap(map);
		map.SetPlayer(player);
		
		for (int i = 0; i < POW_COUNT; i++)
		{
			int x = (int) (35 + (Math.random() * 50));
			int y = (int) (15 + (Math.random() * 50));
			pows[i] = new Character(x, y);
			pows[i].SetMap(map);
		}
		
		for (int i = 0; i < GUARD_COUNT; i++)
		{
			guards[i] = new Guard();
			guards[i].SetMap(map);
		}

		InventoryHandler inventoryHandler = new InventoryHandler( guiStage, guiSkin);
		inventory = new Inventory( 5, inventoryHandler);

		inventory.AddItem(new Item( ItemType.COMPASS));
		inventory.AddItem(new Item( ItemType.CROWBAR));
		inventory.AddItem(new Item( ItemType.KEY));
		
		player.SetInventory(inventory);
		player.SetGuards(guards, GUARD_COUNT);
		
		campTime.SetTime(6, 00);
	}

	public void dispose()
	{
	}

	public void render()
	{
		evtMgr.DispatchEvents();
        
        if (!quitDialogVisible )
        {
            keyMap.CheckKeys();
        }
        
        guiTime.SetTime(campTime.hour, campTime.minute);

        if (player.DoControl())
        {
	        for (int i = 0; i < POW_COUNT; i++)
			{
	        	pows[i].DoControl();
			}
	        for (int i = 0; i < GUARD_COUNT; i++)
			{
	        	guards[i].DoControl();
			}
        }
        player.CenterMapWindowOnPlayer();
        
        inventory.DoControl();
        
        modelBatch.begin(camera);
        map.Render(modelBatch, player);
        player.Render(modelBatch);
        for (int i = 0; i < POW_COUNT; i++)
		{
        	pows[i].Render(modelBatch);
		}
        for (int i = 0; i < GUARD_COUNT; i++)
		{
        	guards[i].Render(modelBatch);
		}
        modelBatch.end();
        
        guiStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        guiStage.draw();
	}

	public void ShowQuitDialog()
    {
        quitDialog = new Window("Really quit", guiSkin);
        quitDialog.setPosition((Gdx.graphics.getWidth()-350)/2, 400);
        quitDialog.setSize(300, 200);
        quitLabel = new Label("You will lose all progress if you\nreturn to the start screen.\nAre you sure?", guiSkin);
        quitYesButton = new TextButton("Yes, quit", guiSkin);
        quitNoButton = new TextButton("No, carry on", guiSkin);
        
        quitNoButton.addListener(new ChangeListener()
        {
            public void changed (ChangeEvent event, Actor actor)
            {
                quitDialog.remove();
                quitDialogVisible = false;
            }
        });
        
        quitYesButton.addListener(new ChangeListener()
        {
            public void changed (ChangeEvent event, Actor actor)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtStartScreen;
                evtMgr.SendEvent(evt, false);
            }
        });
        
        quitDialog.row().expandX().expandY().fill();
        quitDialog.add(quitLabel);
        quitDialog.row().expandX().expandY().fill();
        quitDialog.add(quitYesButton);
        quitDialog.add(quitNoButton);
        
        guiStage.addActor(quitDialog); 
        
        quitDialogVisible = true;
    }

	public void resize(int p_width, int p_height)
	{
		Gdx.gl.glViewport(0, 0, p_width, p_height);
		guiStage.setViewport(p_width, p_height, true);
		
		float ar = ((float)p_height / (float)p_width) * 1.5f;

		camera.viewportWidth = 20;
		camera.viewportHeight = 18*ar;
		camera.update(true);		
	}
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public boolean ReceiveEvent(EtriumEvent p_event)
	{
		if (!listening)
			return false;

		switch (p_event.type)
		{
			case evtControlDown:
			{
				ControlType ct = (ControlType)p_event.data;
                boolean handled = false;
                switch(ct)
                {
                    case QUIT:
                    {
                        ShowQuitDialog();
                        break;
                    }
                }
                
                if (handled)
                {
                    return true;
                }
                break;
			}
			case evtResize:
			{
				resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				
				Log.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				
				// Let other receivers get this event
				return false;
			}
			case evtActivityLeadStart:
			{
				Activity act = (Activity)p_event.data;
				map.SetCurrentActivity(act, true);
				
				for (int i = 0; i < POW_COUNT; i++)
				{
					pows[i].ResetPath();
				}
				
				guiActivity.SetActivity(act.name,  true);
				
				break;
			}
			case evtActivityStart:
			{
				Activity act = (Activity)p_event.data;
				map.SetCurrentActivity(act, false);
				
				guiActivity.SetActivity(act.name,  false);
				
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