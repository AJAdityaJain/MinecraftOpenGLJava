package entities;

import org.lwjgl.input.Keyboard;

import models.Models;
import renderEngine.DisplayManager;

public class Player extends Entity {

	private int[] chunk = new int[] {0,0};
	
	private static final int Speed = 10;
	private static final int TurnSpeed = 160;
	
	private float dx,dz,dx2,dz2;
	
	private int CSpeedT = 0;
	private int CSpeedY = 0;
	private int CSpeedX = 0;
	private int CSpeedZ = 0;	
	
	public Player(int x, int y, int z) {
		super(Models.blockModels[0], x,y,z, 0, 0, 0);
		rotY = 90;
	}
	
	public void Move() {
		this.CSpeedZ = 0;
		this.CSpeedY = 0;
		this.CSpeedX = 0;
		this.CSpeedT = 0;
		checkInputs();
		super.increaseRotation(0, CSpeedT * DisplayManager.getFrameTimeSeconds(), 0);
		
		dx =  (float) (CSpeedZ * Math.sin(Math.toRadians(rotY)));
		dz = (float) (CSpeedZ * Math.cos(Math.toRadians(rotY)));
		dx2 = (float) (CSpeedX * Math.sin(Math.toRadians(90+rotY)));
		dz2 = (float) (CSpeedX * Math.cos(Math.toRadians(90+rotY)));

		super.increasePosition(
				(dx+dx2)* DisplayManager.getFrameTimeSeconds(),
				CSpeedY * DisplayManager.getFrameTimeSeconds(),
				(dz+dz2)* DisplayManager.getFrameTimeSeconds()
		);
		
//		CUpSpeed += Grav * DisplayManager.getFrameTimeSeconds();
//		if(super.getPosition().y < TerrainHeight) {
//			CSpeedY = 0;
//			super.position.y = TerrainHeight;
//		}
		dx = 0;
		dz = 0;
		dx2 = 0;
		dz2 = 0;
		this.rotY %= 360;
	}
	
	public int[] Chunk() {
		chunk[0] = (int)((position.x)/16);
		chunk[1] = (int)((position.z)/16);
		return chunk;
	}

	
	/**
	 * 
	 */
	private void checkInputs() {
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.CSpeedZ = -Speed;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.CSpeedZ = Speed;
		}


		if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			this.CSpeedY = -10;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
			this.CSpeedY = 10;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.CSpeedX = -Speed;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.CSpeedX = Speed;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			this.CSpeedT = TurnSpeed;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			this.CSpeedT = -TurnSpeed;
		}

//		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)&&!DisplayManager.Esc) {
//			DisplayManager.Esc = true;
//		}
		
	}
}

/*	
*/