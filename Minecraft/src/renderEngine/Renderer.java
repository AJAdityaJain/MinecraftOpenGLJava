package renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import blocks.Block;
import entities.Entity;
import models.TexturedModel;
import shaders.StaticShader;
import toolbox.Maths;

public class Renderer {
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;

	public static final Vector3f RGB = new Vector3f(0.5f,0.8f,0.9f);
	
	private Matrix4f projMatrix;
	private StaticShader shader;
	private Vector3f pos = new Vector3f(0,0,0);
	private Matrix4f nm = new Matrix4f();
	
	private List<Block> batch;
	private float aspectRatio,y_scale,x_scale,frustum_length;
	
	public Renderer(StaticShader shader){
		this.shader = shader;
		MasterRenderer.enableCulling();

		//may fail
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		createProjectionMatrix();
		shader.start();
		shader.loadProjMatrix(projMatrix);
		shader.stop();
	}

	public void prepare() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(RGB.x, RGB.y, RGB.z, 1);
	}

	public void render(Map<TexturedModel,List<Block>> bls	) {
		for(TexturedModel model:bls.keySet()) {
			prepareTModel(model);
			batch = bls.get(model);
			for(Block b:batch) {
				prepareBlocks(b);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

			}
			unbindTModel();			
		}
	}
	
	public void render(Entity entity) {
		prepareTModel(entity.getModel());
		shader.loadTransMatrix(Maths.createTransformationMatrix(entity.getPosition(),entity.rotX,entity.rotY,entity.rotZ));
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		unbindTModel();
	}
	
	private void prepareTModel(TexturedModel TModel) {
		GL30.glBindVertexArray(TModel.getRawModel().getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		if(TModel.getModelTexture().isHasTransparency()) {
			MasterRenderer.disableCulling();
		}
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, TModel.getModelTexture().getID());
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER	, GL11.GL_NEAREST);
	}
	
	private void unbindTModel() {
		MasterRenderer.enableCulling();
		GL30.glBindVertexArray(0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	private void prepareBlocks(Block bl) {
		pos.x = bl.x;
		pos.y = bl.y;
		pos.z = bl.z;
		shader.loadTransMatrix(Maths.createTransformationMatrix(pos,0,0,0));
	}
	
	private void createProjectionMatrix(){
		aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		x_scale = y_scale / aspectRatio;
		frustum_length = FAR_PLANE - NEAR_PLANE;

		projMatrix = nm;
		projMatrix.m00 = x_scale;
		projMatrix.m11 = y_scale;
		projMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projMatrix.m23 = -1;
		projMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projMatrix.m33 = 0;
	}

}


























/*public void render(Entity entity, StaticShader shader) {
TexturedModel model = entity.getModel();
RawModel rawModel = model.getRawModel();
GL30.glBindVertexArray(rawModel.getVaoID());
GL20.glEnableVertexAttribArray(0);
GL20.glEnableVertexAttribArray(1);
Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(),
		entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
shader.loadTransMatrix(transformationMatrix);
GL13.glActiveTexture(GL13.GL_TEXTURE0);
GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getModelTexture().getID());
GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
GL20.glDisableVertexAttribArray(0);
GL20.glDisableVertexAttribArray(1);
GL30.glBindVertexArray(0);
}
*/
/*
 
 TexturedModel model = entity.getModel();
	RawModel rawModel = model.getRawModel();
	
	GL30.glBindVertexArray(rawModel.getVaoID());
	GL20.glEnableVertexAttribArray(0);
	GL20.glEnableVertexAttribArray(1);
	
	if(model.getModelTexture().isHasTransparency()) {
		MasterRenderer.disableCulling();
	}
	GL13.glActiveTexture(GL13.GL_TEXTURE0);
	GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getModelTexture().getID());
	GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER	, GL11.GL_NEAREST);
	
	GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
	
	Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(),entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
	shader.loadTransMatrix(transformationMatrix);

	MasterRenderer.enableCulling();
	GL30.glBindVertexArray(0);
	GL20.glDisableVertexAttribArray(0);
	GL20.glDisableVertexAttribArray(1);
 */