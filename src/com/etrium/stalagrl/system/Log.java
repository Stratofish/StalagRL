package com.etrium.stalagrl.system;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class Log
{
  static private Window window;
  static private ScrollPane scrollPane;
  static private List list;
  static private ArrayList<String> s = new ArrayList<String>();
  
  static public void CreateLog(Stage p_guiStage, Skin p_guiSkin)
  {    
    list = new List( s.toArray(), p_guiSkin);
    list.setHeight(12.0f);

    scrollPane = new ScrollPane(list, p_guiSkin);
    scrollPane.setOverscroll(false,  false);
    scrollPane.setFadeScrollBars(false);
    window = new Window("Activity", p_guiSkin);
    window.setPosition(1024-10-(16), -300.0f);
    window.setSize(300.0f, 100.0f);
    window.row().fill().expandX().expandY();
    window.add(scrollPane);
    p_guiStage.addActor(window);
  }

  static public void Render()
  {
    //scrollPane.setScrollPercentY(0.0f);
  }
  
  static public void action( String p_text)
  {
    s.add( 0,  p_text);    
    list.setItems(s.toArray());
  }
}