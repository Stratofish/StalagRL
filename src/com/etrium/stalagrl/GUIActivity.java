package com.etrium.stalagrl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GUIActivity
{
	Label label = null;
	Skin skin = null;
	Stage stage = null;
	
	public GUIActivity(Skin p_skin, Stage p_stage)
	{
		skin = p_skin;
		stage = p_stage;
		
		label = new Label("", skin);
		//label.setPosition((Gdx.graphics.getWidth() - 200.0f) / 2.0f, Gdx.graphics.getHeight() - 100.0f);
		label.setPosition(50.0f, 140.0f);
		label.setColor(Color.CYAN);
		label.setFontScale(2.0f);
		
		stage.addActor(label);
	}
	
	public void SetActivity(String p_name, boolean p_lead)
	{
		String str = p_name;
		
		if (p_lead)
			str += " starts soon";
			
		label.setText(str);
	}
}