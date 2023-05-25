package engineTester;

import blocks.Block;
import entities.Camera;
import entities.Entity;
import entities.Player;
import models.Models;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.InputManager;
import renderEngine.MasterRenderer;
import terrain.Chunk;
import terrain.World;
import toolbox.Loader;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	
	public static final int chunkSize = 16;
	public static final int chunkHeight = 32;
	public static final int WorldSize = 64;
	
	public static Loader loader = new Loader();
	public static World world = new World();
	public static Player Player;
	
	private static List<Entity> entities = new ArrayList<Entity>();

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		InputManager.create();
		Chunk.setPerlin();
		
		Models models = new Models();
		Player = new Player(512,32,512);
		
		Camera Cam = new Camera();
		MasterRenderer renderer = new MasterRenderer();

		
		world.Generate();
//		world.Generate(31, 31);
//		world.Generate(31, 32);
//		world.Generate(32, 31);
		
		models.Null();
		while(!Display.isCloseRequested()){
			Player.Move();
			Cam.move();
			
			renderer.processEntity(entities);	
			
			int[] p = GetPlayerCoords();
						
			for(int x = p[0]-DisplayManager.RenderDistance; x < p[0]+DisplayManager.RenderDistance+1; x++) {
				for(int z = p[1]-DisplayManager.RenderDistance; z < p[1]+DisplayManager.RenderDistance+1; z++) {
					Chunk c = world.getChunks(x,z);
					if(c!= null) {
						for(Block[][] b1:c.blocks) {
							for(Block[] b2:b1) {
								for(Block b:b2) {
									if(b!=null && b.visible) {
										renderer.processBlock(b);		
									}						
								}
							}
						}
					}
					else {
						world.Generate(x,z);
					}
				}
			}
			
			InputManager.mouseRot();

			renderer.render(Cam);
			DisplayManager.updateDisplay();
		}
		print();
		
		renderer.cleanup();
		loader.cleanUp();
		InputManager.destroy();
		DisplayManager.closeDisplay();

	}

	public static int[] GetPlayerCoords(){
		return Player.Chunk();
	}
	
	public static void print() {
		for(int x = 0; x < WorldSize; x++) {
			for(int z = 0; z < WorldSize; z++) {
				Chunk c = world.getChunks(x, z);
				if(c == null) {
					System.out.print("_");
				}
				else {

					System.out.print("O");
				}
			}
			System.out.println(" ");
		}	
	}
}