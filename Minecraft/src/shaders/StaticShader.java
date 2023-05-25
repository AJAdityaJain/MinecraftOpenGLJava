package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import toolbox.Maths;

public class StaticShader extends ShaderProgram{

	private static final String VERTEX_FILE = "/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "/shaders/fragmentShader.txt";
	
	private int locationTransMatrix;
	private int	locationProjMatrix;
	private int	locationViewMatrix;
	private int locationSkyColour;
	
	private Matrix4f viewMatrix;
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttribs() {	
		super.bindAttrib(0, "position");
		super.bindAttrib(1, "textureCoords");
	}

	@Override
	protected void getAllUniformLocations() {
		locationTransMatrix = super.getUniformLocation("transMatrix");
		locationProjMatrix = super.getUniformLocation("projMatrix");
		locationViewMatrix = super.getUniformLocation("viewMatrix");
		locationSkyColour = super.getUniformLocation("skyColour");
		
	}
	
	public void loadTransMatrix(Matrix4f matrix) {
		super.loadMatrix(locationTransMatrix, matrix);
	}
	
	public void loadSkyColour(Vector3f rgb) {
		super.loadVector(locationSkyColour, rgb);
	}
	
	public void loadProjMatrix(Matrix4f matrix) {
		super.loadMatrix(locationProjMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera cam) {
		viewMatrix = Maths.createViewMatrix(cam);
		super.loadMatrix(locationViewMatrix, viewMatrix);	
	}
	
}
