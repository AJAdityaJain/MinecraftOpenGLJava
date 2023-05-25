package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import blocks.Block;
import entities.Camera;
import entities.Entity;
import models.Models;
import models.TexturedModel;
import shaders.StaticShader;


public class MasterRenderer {
	
	private StaticShader shader = new StaticShader();
	private Renderer renderer = new Renderer(shader);
	private Map<TexturedModel,List<Block>> blocks = new HashMap<TexturedModel,List<Block>>();
	private List<Entity> ent;
	private TexturedModel eModel;
	private List<Block> batch;
	private List<Block> newBatch;
	
	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void render(Camera Cam) {
		renderer.prepare();
		shader.start();
		shader.loadSkyColour(Renderer.RGB);
		shader.loadViewMatrix(Cam);
		renderer.render(blocks);
		for(Entity e : ent) {
			renderer.render(e);		
		}
		shader.stop();
		blocks.clear();
	}
	
	public void processBlock(Block e) {
		eModel = Models.blockModels[e.getModel()];
		batch = blocks.get(eModel);
		if(batch!= null) {
			batch.add(e);
		}
		else {
			newBatch = new ArrayList<Block>();
			newBatch.add(e);
			blocks.put(eModel, newBatch);	
		}
	}
	
	public void cleanup() {
		shader.cleanUp();
	}

	public void processEntity(List<Entity> entities) {
		this.ent = entities;
	}
}
