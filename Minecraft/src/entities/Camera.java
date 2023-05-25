package entities;

import org.lwjgl.util.vector.Vector3f;

import engineTester.Main;

public class Camera {
	
	private Vector3f position = new Vector3f(0,0,0);
	private float pitch;
	private float yaw;
	private float roll;
	
	public Camera(){}
	
	public void move() {
		position = Main.Player.getPosition();
		yaw = 360-Main.Player.rotY;
		pitch = Main.Player.rotX;
 	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
}