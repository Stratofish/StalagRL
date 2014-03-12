package com.etrium.stalagrl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.etrium.stalagrl.character.Player;
import com.etrium.stalagrl.character.Character;
import com.etrium.stalagrl.system.ControlType;
import com.etrium.stalagrl.system.EventType;
import com.etrium.stalagrl.system.EtriumEvent;
import com.etrium.stalagrl.system.EventListener;
import com.etrium.stalagrl.system.EventManager;
import com.etrium.stalagrl.Inventory;
import com.etrium.stalagrl.Item;
import com.etrium.stalagrl.ItemType;

public class PowCamps implements EventListener
{

	private EventManager evtMgr;
	private KeyMap keyMap;
	private Map map;
	private Camera camera;
	private ModelBatch modelBatch;
	private Label levelLabel;
	private ScrollPane logScrollPane = null;
	private Window logWindow = null;
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
	protected Character pow = null;

	public PowCamps()
	{
		evtMgr = new EventManager();
		this.StartListening();
		
		keyMap = new KeyMap();
		
		guiStage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        Gdx.input.setInputProcessor(guiStage);
        
		guiSkin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		levelLabel = new Label("* * * Dungeon level 35 * * *", guiSkin);
        levelLabel.setPosition(40.0f, 730.0f);

        String[] s = {""};
        List list = new List(s, guiSkin);
        list.setHeight(12.0f);
        logScrollPane = new ScrollPane(list, guiSkin);
        logScrollPane.setOverscroll(false,  false);
        logScrollPane.setFadeScrollBars(false);
        logWindow = new Window("Activity", guiSkin);
        logWindow.setPosition(1024-10-(16), -300.0f);
        logWindow.setSize(300.0f, 100.0f);
        logWindow.row().fill().expandX().expandY();
        logWindow.add(logScrollPane);
        //guiStage.addActor(logWindow);

        evtMgr.RegisterListener(this, EventType.evtLogActivity);
        evtMgr.RegisterListener(this, EventType.evtCharDead);
        evtMgr.RegisterListener(this, EventType.evtUpLevel);
        evtMgr.RegisterListener(this, EventType.evtDownLevel);
        evtMgr.RegisterListener(this, EventType.evtControlDown);
        evtMgr.RegisterListener(this, EventType.evtControlUp);
        evtMgr.RegisterListener(this, EventType.evtPlayerUIChanged);
        evtMgr.RegisterListener(this, EventType.evtQuitConfirm);
        
        modelBatch = new ModelBatch(new DefaultShaderProvider());
        
        camera = new OrthographicCamera(20, 20);
        //camera = new PerspectiveCamera(65, 1024, 768);
		camera.position.set(-4.0f, -4.0f, 6.0f);
		camera.lookAt(0.0f, 0.0f, 0.0f);
		camera.up.x = 0.0f;
		camera.up.y = 0.0f;
		camera.up.z = 1.0f;
		camera.near = -50.0f;
		camera.far = 1000.0f;
		camera.update();
		
		map = new Map(100, 100);
		map.SetCamera(camera);
		
		player = new Player(20, 25);
		player.SetCamera(camera);
		player.SetMap(map);
		
		pow = new Character(25, 20);
		pow.SetMap(map);
		
		inventory = new Inventory( guiStage, guiSkin);		
		inventory.Update();
		
		inventory.AddItem(new Item( ItemType.COMPASS)); 
		inventory.AddItem(new Item( ItemType.CROWBAR));
		inventory.AddItem(new Item( ItemType.KEY));
	}

	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}

	public void render()
	{
		evtMgr.DispatchEvents();
        
        if (!quitDialogVisible )
        {
            keyMap.CheckKeys();
        }

        //boolean playerAction = player.DoControl();
        
        // Only update map stuff if player had a turn
        //if (playerAction)
        //{
        //  map.update();
        //  playerAction = false;
        //}
        

        player.DoControl();
        player.CenterMapWindowOnPlayer();
        
        inventory.DoControl();
        
        //Rectangle scissors = new Rectangle();
        //Rectangle clipBounds = new Rectangle((map.mapWindow.x)-(1024-640-85) - (1024/2),
        //                                     (map.mapWindow.y)-(768-640+420)-(768/2),640,640);
        //ScissorStack.calculateScissors(camera, batch.getTransformMatrix(), clipBounds, scissors);
        //ScissorStack.pushScissors(scissors);
        
        //batch.setProjectionMatrix(camera.combined);
        modelBatch.begin(camera);
            map.Render(modelBatch, player);
            player.Render(modelBatch);
            pow.Render(modelBatch);
        modelBatch.end();
        
        //ScissorStack.popScissors();

        levelLabel.setText("* * * Dungeon level "+map.curLevel+" * * *");
        
        logScrollPane.setScrollPercentY(100.0f);
        guiStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        guiStage.draw();
        
        //tooltip.Update();
        //tooltip.UpdateGear();		
	}

	public void ShowQuitDialog()
    {
        quitDialog = new Window("Really quit", guiSkin);
        quitDialog.setPosition(350, 400);
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
		camera.update();
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