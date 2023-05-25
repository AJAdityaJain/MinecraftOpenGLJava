package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public abstract class Entity {

	private TexturedModel model;
	protected Vector3f position;
	public float rotX,rotY,rotZ;
	
	public Entity(TexturedModel model, int x, int y, int z, float rotX, float rotY, float rotZ) {
		super();
		this.model = model;
		this.position = new Vector3f(x,y,z);
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
	}
	
	public void increasePosition(float x, float y,float z) {
		this.position.x += x;
		this.position.y += y;
		this.position.z += z;
	}
	
	public void increaseRotation(float x, float y,float z) {
		this.rotX += x;
		this.rotY += y;
		this.rotZ += z;
	}

	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
}
