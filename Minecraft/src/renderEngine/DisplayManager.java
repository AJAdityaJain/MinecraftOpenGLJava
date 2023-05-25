package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {

//	private static final int WIDTH = 1920;
//	private static final int HEIGHT = 1000;
	public static final DisplayMode mode = Display.getDesktopDisplayMode();
	public static final int RenderDistance = 5;
	private static final int FPS_CAP = 40;
	private static final String TITLE = "Bootleg Minecraft";
	
	
	private static long lastFrameTime;
	private static float delta;

	public static void createDisplay() {
		ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
		try {
			Display.setDisplayMode(mode);
			Display.setResizable(false);
			Display.create(new PixelFormat(), attribs);
			Display.setTitle(TITLE);
			Display.setResizable(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		GL11.glViewport(0, 0, mode.getWidth(), mode.getHeight());
		lastFrameTime = getCurrentTime();
		
		
	}

	
	
	public static void updateDisplay() {

		
		Display.sync(FPS_CAP);
		Display.update();
		delta = (getCurrentTime() - lastFrameTime)/1000f;
		lastFrameTime = getCurrentTime();
	}
	
	public static float getFrameTimeSeconds() {
		return delta;
	}
	
	public static void closeDisplay() {
		Display.destroy();
	}

	private static long getCurrentTime() {
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
	
}