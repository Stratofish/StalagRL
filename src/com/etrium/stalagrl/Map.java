package com.etrium.stalagrl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.GLES10ShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Map
{

	public Object mapWindow;
	public String curLevel;
	
	private PerspectiveCamera camera = null;
	private Model model;
	private ModelInstance instance = null;
	private ModelInstance instance2 = null;
	private ModelBatch modelBatch;
	private Environment environment;
	private AssetManager assets;
	private Texture dirtTexture;

	public Map()
	{
		camera = new PerspectiveCamera(67, 1024, 768);
		camera.position.set(0.0f, -4.0f, 4.0f);
		camera.lookAt(0.0f, 0.0f, 0.0f);
		camera.near = 0.1f;
		camera.far = 100.0f;
		camera.update();
		
		modelBatch = new ModelBatch(new GLES10ShaderProvider());
		
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1.0f, 1.0f, 1.0f, 1.0f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1.0f, -0.8f, -0.2f));
		
		assets = new AssetManager();
		assets.load("data/models/plain-floor.g3db", Model.class);
		assets.finishLoading();
		
		dirtTexture = new Texture(Gdx.files.internal("data/textures/dirt.png"));
		
		ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(0.5f, 0.5f, 0.5f, 
            new Material(ColorAttribute.createDiffuse(Color.GREEN)),
            Usage.Position | Usage.Normal);

        instance2 = new ModelInstance(model);
	}
	
	public void Render()
	{
		if (assets.update()) {
			instance = new ModelInstance(assets.get("data/models/plain-floor.g3db", Model.class));
			Material mat = instance.materials.get(0);
			mat.set(TextureAttribute.createDiffuse(dirtTexture));
		}
		
		Gdx.gl.glViewport(0, 0, 1024, 768);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		if (instance != null)
		{
			modelBatch.begin(camera);
			modelBatch.render(instance, environment);
			//modelBatch.render(instance2, environment);
			modelBatch.end();
		}
	}
	
	public void CenterMapWindowOnPlayer() {
		// TODO Auto-generated method stub
		
	}
}
