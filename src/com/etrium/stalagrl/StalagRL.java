package com.etrium.stalagrl;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.etrium.stalagrl.system.EtriumEvent;
import com.etrium.stalagrl.system.EventListener;
import com.etrium.stalagrl.system.EventManager;
import com.etrium.stalagrl.system.EventType;

public class StalagRL implements ApplicationListener, EventListener
{
	private GameState state = GameState.STARTSCREEN;
	//private GameState state = GameState.GAME;
	
	private PowCamps powCamps = null;
	private StartScreen startScreen = null;
	private WinScreen winScreen = null;
	private LoseScreen loseScreen = null;
	private EventManager evtMgr = new EventManager();
	
	protected int width = 1024;
	protected int height = 768;
	
	
	@Override
	public void create() {		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		startScreen = new StartScreen();
		
		evtMgr.RegisterListener(this, EventType.evtGameStart);
		evtMgr.RegisterListener(this, EventType.evtGameQuit);
		evtMgr.RegisterListener(this, EventType.evtGameWon);
		evtMgr.RegisterListener(this, EventType.evtGameLost);
		evtMgr.RegisterListener(this, EventType.evtStartScreen);
	}

	@Override
	public void dispose()
	{
	    if (powCamps != null)
	    {
	        powCamps.dispose();
	    }
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		switch (state)
		{
		    case STARTSCREEN:
            {
                startScreen.render();
                break;
            }
		    case WIN:
            {
                winScreen.render();
                break;
            }
		    case LOSE:
            {
                loseScreen.render();
                break;
            }
		    case GAME:
		    {
		    	if (powCamps != null)
		    		powCamps.render();
		        break;
		    }
		}
	}

	@Override
	public void resize(int p_width, int p_height)
	{
		width = p_width;
		height = p_height;
		
		switch (state)
		{
		    case STARTSCREEN:
            {
                //startScreen.resize(width, height);
                break;
            }
		    case WIN:
            {
                //winScreen.resize(width, height);
                break;
            }
		    case LOSE:
            {
                //loseScreen.resize(width, height);
                break;
            }
		    case GAME:
		    {
		    	if (powCamps != null)
		    		powCamps.resize(width, height);
		        break;
		    }
		}
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

    
	@Override
    public boolean ReceiveEvent(EtriumEvent p_event)
    {
        switch (p_event.type)
        {
            case evtStartScreen:
            {
                startScreen = new StartScreen();
                //dungeon = null;
                state = GameState.STARTSCREEN;
                return true;
            }
            case evtGameStart:
            {
                startScreen = null;
                powCamps = new PowCamps();
                state = GameState.GAME;
                return true;
            }
            case evtGameWon:
            {
                winScreen = new WinScreen();
                startScreen = null;
                powCamps = null;
                state = GameState.WIN;
                return true;
            }
            case evtGameLost:
            {
                loseScreen = new LoseScreen();
                startScreen = null;
                powCamps = null;
                state = GameState.LOSE;
                return true;
            }
            case evtGameQuit:
            {
                Gdx.app.exit();
                return true;
            }
        }
        
        return false;
    }
    

    @Override
    public void StartListening()
    {
    }
    

    @Override
    public void StopListening()
    {
    }
}
