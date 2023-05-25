package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

import engineTester.Main;

public class InputManager {
	public static boolean Esc = false;
	private static final int[] MouseBox = new int[] {DisplayManager.mode.getWidth()/2,DisplayManager.mode.getHeight()/2};
	
	public static void create() {
		try {
			Mouse.create();
		} catch (LWJGLException e) {}
		Mouse.setGrabbed(true);
	}
		
	public static void mouseRot() {
		
		if(!Esc) {
			Main.Player.rotX += (MouseBox[1]-Mouse.getY())/10;
			Main.Player.rotY += (MouseBox[0]-Mouse.getX())/10;
			Mouse.setCursorPosition(MouseBox[0],MouseBox[1]);	
		}
		else {

			Mouse.setGrabbed(false);
		}
	}
	
	public static void destroy() {
		Mouse.destroy();
	}
}
