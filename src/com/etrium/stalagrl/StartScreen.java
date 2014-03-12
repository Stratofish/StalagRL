package com.etrium.stalagrl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.etrium.stalagrl.system.EtriumEvent;
import com.etrium.stalagrl.system.EventListener;
import com.etrium.stalagrl.system.EventManager;
import com.etrium.stalagrl.system.EventType;

public class StartScreen implements EventListener
{
    private EventManager evtMgr = new EventManager(); 
    
    private Stage stage;
    private Skin skin;
    private Window window = null;
    private TextButton startGame = null;
    private TextButton quitGame = null;
    
    public StartScreen()
    {
        stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        
        window = new Window("7-day roguelike challenge - \"StalagRL\"", skin);
        window.setPosition((Gdx.graphics.getWidth()-350) / 2, 500);
        window.setSize(400, 150f);
        window.row().fill().expandX().expandY();
        
        startGame = new TextButton("New game", skin);
        quitGame = new TextButton("Quit", skin);
        
        
        startGame.addListener(new ChangeListener()
        {
            public void changed (ChangeEvent event, Actor actor)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtGameStart;
                evt.data = null;
                evtMgr.SendEvent(evt,  true);
            }
        });
        
        quitGame.addListener(new ChangeListener()
        {
            public void changed (ChangeEvent event, Actor actor)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtGameQuit;
                evt.data = null;
                evtMgr.SendEvent(evt,  true);
            }
        });
        
        window.add(startGame);
        window.row().fill().expandX().expandY();
        window.add(quitGame);
        
        stage.addActor(window);
        
        evtMgr.RegisterListener(this, EventType.evtResize);
    }

    public void render()
    {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

	@Override
	public boolean ReceiveEvent(EtriumEvent p_event)
	{
		if (p_event.type == EventType.evtResize)
		{
			stage.setViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
			window.setPosition((Gdx.graphics.getWidth()-350) / 2, 500);
		}
		
		return false;
	}

	@Override
	public void StartListening()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void StopListening()
	{
		// TODO Auto-generated method stub
		
	}
}
