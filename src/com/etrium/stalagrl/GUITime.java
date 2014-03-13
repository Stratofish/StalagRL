package com.etrium.stalagrl;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GUITime
{
	Label label = null;
	Skin skin = null;
	Stage stage = null;
	
	public GUITime(Skin p_skin, Stage p_stage)
	{
		skin = p_skin;
		stage = p_stage;
		
		label = new Label("Test", skin);
		label.setPosition(50.0f, 50.0f);
		label.setFontScale(2.0f);
		
		stage.addActor(label);
	}
	
	public void SetTime(int hours, int minutes)
	{
		label.setText(String.format("%02d", hours) + ":" + String.format("%02d", minutes));
	}
}