package com.etrium.stalagrl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.etrium.stalagrl.system.ControlType;
import com.etrium.stalagrl.system.EtriumEvent;
import com.etrium.stalagrl.system.EventManager;
import com.etrium.stalagrl.system.EventType;

public class KeyMap
{
    private EventManager evtMgr = null;
    private boolean upHeld = false;
    private boolean downHeld = false;
    private boolean leftHeld = false;
    private boolean rightHeld = false;
    private boolean spaceHeld = false;
    private boolean quitHeld = false;
    
    private boolean godModeHeld = false; 
    private boolean noClipHeld = false; 
    private boolean revealMapHeld = false;
    
    protected boolean inventory1Held = false;
    protected boolean inventory2Held = false;
    protected boolean inventory3Held = false;
    protected boolean inventory4Held = false;
    protected boolean inventory5Held = false;    
    protected boolean inventoryBackHeld = false;
    protected boolean inventoryFWDHeld = false; 
    
    protected boolean useHeld = false;
    protected boolean dropHeld = false;
    
    public KeyMap()
    {
        evtMgr = new EventManager();
    }
    
    public void CheckKeys()
    {
        // Up
        if (Gdx.input.isKeyPressed(Keys.UP) ||
            Gdx.input.isKeyPressed(Keys.W))
        {
            if (!upHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlDown;
                evt.data = (Object)ControlType.UP;
                evtMgr.SendEvent(evt,  true);
                
                upHeld = true;
            }
        } else
        {
            if (upHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlUp;
                evt.data = (Object)ControlType.UP;
                evtMgr.SendEvent(evt,  true);
                
                upHeld = false;
            }
        }
        
        // Down
        if (Gdx.input.isKeyPressed(Keys.DOWN) ||
            Gdx.input.isKeyPressed(Keys.S))
        {
            if (!downHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlDown;
                evt.data = (Object)ControlType.DOWN;
                evtMgr.SendEvent(evt,  true);
                
                downHeld = true;
            }
        } else
        {
            if (downHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlUp;
                evt.data = (Object)ControlType.DOWN;
                evtMgr.SendEvent(evt,  true);
                
                downHeld = false;
            }
        }
        
        // Left
        if (Gdx.input.isKeyPressed(Keys.LEFT) ||
            Gdx.input.isKeyPressed(Keys.A))
        {
            if (!leftHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlDown;
                evt.data = (Object)ControlType.LEFT;
                evtMgr.SendEvent(evt,  true);
                
                leftHeld = true;
            }
        } else
        {
            if (leftHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlUp;
                evt.data = (Object)ControlType.LEFT;
                evtMgr.SendEvent(evt,  true);
                
                leftHeld = false;
            }
        }
        
        // Right
        if (Gdx.input.isKeyPressed(Keys.RIGHT) ||
            Gdx.input.isKeyPressed(Keys.D))
        {
            if (!rightHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlDown;
                evt.data = (Object)ControlType.RIGHT;
                evtMgr.SendEvent(evt,  true);
                
                rightHeld = true;
            }
        } else
        {
            if (rightHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlUp;
                evt.data = (Object)ControlType.RIGHT;
                evtMgr.SendEvent(evt,  true);
                
                rightHeld = false;
            }
        }

        // Inventory 1
        if (Gdx.input.isKeyPressed(Keys.NUM_1))
        {
            if (!inventory1Held)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlDown;
                evt.data = (Object)ControlType.INVENTORY1;
                evtMgr.SendEvent(evt,  true);
                
                inventory1Held = true;
            }
        } else
        {
            if (inventory1Held)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlUp;
                evt.data = (Object)ControlType.INVENTORY1;
                evtMgr.SendEvent(evt,  true);
                
                inventory1Held = false;
            }
        }                      
        
        // Inventory 2
        if (Gdx.input.isKeyPressed(Keys.NUM_2))
        {
            if (!inventory2Held)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlDown;
                evt.data = (Object)ControlType.INVENTORY2;
                evtMgr.SendEvent(evt,  true);
                
                inventory2Held = true;
            }
        } else
        {
            if (inventory2Held)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlUp;
                evt.data = (Object)ControlType.INVENTORY2;
                evtMgr.SendEvent(evt,  true);
                
                inventory2Held = false;
            }
        }
        
        // Inventory 3
        if (Gdx.input.isKeyPressed(Keys.NUM_3))
        {
            if (!inventory3Held)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlDown;
                evt.data = (Object)ControlType.INVENTORY3;
                evtMgr.SendEvent(evt,  true);
                
                inventory3Held = true;
            }
        } else
        {
            if (inventory3Held)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlUp;
                evt.data = (Object)ControlType.INVENTORY3;
                evtMgr.SendEvent(evt,  true);
                
                inventory3Held = false;
            }
        }
        
        // Inventory 4
        if (Gdx.input.isKeyPressed(Keys.NUM_4))
        {
            if (!inventory4Held)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlDown;
                evt.data = (Object)ControlType.INVENTORY4;
                evtMgr.SendEvent(evt,  true);
                
                inventory4Held = true;
            }
        } else
        {
            if (inventory4Held)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlUp;
                evt.data = (Object)ControlType.INVENTORY4;
                evtMgr.SendEvent(evt,  true);
                
                inventory4Held = false;
            }
        }
        
        // Inventory 5
        if (Gdx.input.isKeyPressed(Keys.NUM_5))
        {
            if (!inventory5Held)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlDown;
                evt.data = (Object)ControlType.INVENTORY5;
                evtMgr.SendEvent(evt,  true);
                
                inventory5Held = true;
            }
        } else
        {
            if (inventory5Held)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlUp;
                evt.data = (Object)ControlType.INVENTORY5;
                evtMgr.SendEvent(evt,  true);
                
                inventory5Held = false;
            }
        }
        
        // Inventory Back
        if (Gdx.input.isKeyPressed(Keys.LEFT_BRACKET))
        {
            if (!inventoryBackHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlDown;
                evt.data = (Object)ControlType.INVENTORYBACK;
                evtMgr.SendEvent(evt,  true);
                
                inventoryBackHeld = true;
            }
        } else
        {
            if (inventoryBackHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlUp;
                evt.data = (Object)ControlType.INVENTORYBACK;
                evtMgr.SendEvent(evt,  true);
                
                inventoryBackHeld = false;
            }
        }
        
        // Inventory Fwd
        if (Gdx.input.isKeyPressed(Keys.RIGHT_BRACKET))
        {
            if (!inventoryFWDHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlDown;
                evt.data = (Object)ControlType.INVENTORYFWD;
                evtMgr.SendEvent(evt,  true);
                
                inventoryFWDHeld = true;
            }
        } else
        {
            if (inventoryFWDHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlUp;
                evt.data = (Object)ControlType.INVENTORYFWD;
                evtMgr.SendEvent(evt,  true);
                
                inventoryFWDHeld = false;
            }
        }
                       
        // Use key
        if (Gdx.input.isKeyPressed(Keys.SPACE) ||
            Gdx.input.isKeyPressed(Keys.E))
        {
            if (!useHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlDown;
                evt.data = (Object)ControlType.USE;
                evtMgr.SendEvent(evt,  true);
                
                useHeld = true;
            }
        } else
        {
            if (useHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlUp;
                evt.data = (Object)ControlType.USE;
                evtMgr.SendEvent(evt,  true);
                
                useHeld = false;
            }
        }
        
        // Drop key
        if (Gdx.input.isKeyPressed(Keys.BACKSPACE) ||
            Gdx.input.isKeyPressed(Keys.F))
        {
            if (!dropHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlDown;
                evt.data = (Object)ControlType.DROP;
                evtMgr.SendEvent(evt,  true);
                
                dropHeld = true;
            }
        } else
        {
            if (dropHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlUp;
                evt.data = (Object)ControlType.DROP;
                evtMgr.SendEvent(evt,  true);
                
                dropHeld = false;
            }
        }
        
        // God mode
        if (Gdx.input.isKeyPressed(Keys.F1))
        {
            if (!godModeHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlDown;
                evt.data = (Object)ControlType.GODMODE;
                //evtMgr.SendEvent(evt,  true);
                
                godModeHeld = true;
            }
        } else
        {
            if (godModeHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlUp;
                evt.data = (Object)ControlType.GODMODE;
                //evtMgr.SendEvent(evt,  true);
                
                godModeHeld = false;
            }
        }
        
        // Reveal map
        if (Gdx.input.isKeyPressed(Keys.F2))
        {
            if (!revealMapHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlDown;
                evt.data = (Object)ControlType.REVEALMAP;
                //evtMgr.SendEvent(evt,  true);
                
                revealMapHeld = true;
            }
        } else
        {
            if (revealMapHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlUp;
                evt.data = (Object)ControlType.REVEALMAP;
                //evtMgr.SendEvent(evt,  true);
                
                revealMapHeld = false;
            }
        }
        
        // Noclip mode
        if (Gdx.input.isKeyPressed(Keys.F3))
        {
            if (!noClipHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlDown;
                evt.data = (Object)ControlType.NOCLIP;
                //evtMgr.SendEvent(evt,  true);
                
                noClipHeld = true;
            }
        } else
        {
            if (noClipHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlUp;
                evt.data = (Object)ControlType.NOCLIP;
                //evtMgr.SendEvent(evt,  true);
                
                noClipHeld = false;
            }
        }
        
        // Quit
        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
        {
            if (!quitHeld)
            {
                EtriumEvent evt = new EtriumEvent();
                evt.type = EventType.evtControlDown;
                evt.data = (Object)ControlType.QUIT;
                evtMgr.SendEvent(evt,  true);
                
                quitHeld = true;
            }
        } else
        {
            if (quitHeld)
            {
                quitHeld = false;
            }
        }
    }
}
