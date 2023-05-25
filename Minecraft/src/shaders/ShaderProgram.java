package shaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public abstract class ShaderProgram {

	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private static FloatBuffer matrix = BufferUtils.createFloatBuffer(16);
	
	public ShaderProgram(String vertexFile, String fragmentFile) {
		vertexShaderID = loadShader(vertexFile,GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile,GL20.GL_FRAGMENT_SHADER);
		
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttribs();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}

	protected abstract void getAllUniformLocations(); 	
	
	protected int getUniformLocation(String Name) {
		return GL20.glGetUniformLocation(programID, Name);
	}
	
	protected void loadFloat(int location, float val) {
		GL20.glUniform1f(location, val);
	}
	
	protected void loadVector(int location, Vector3f val) {
		GL20.glUniform3f(location, val.x,val.y,val.z);
	}
	
	protected void loadBool(int location, boolean val) {
		float toLoad = 0;
		if(val){
			toLoad = 1;
		}
		GL20.glUniform1f(location, toLoad);
	}
	
	protected void loadMatrix(int location, Matrix4f val) {
		val.store(matrix);
		matrix.flip();
		GL20.glUniformMatrix4(location, false, matrix);
	}
	
	public void start() {
		GL20.glUseProgram(programID);
	}
	
	public void stop() {
		GL20.glUseProgram(0);
	}
	
	public void cleanUp() {
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	
	protected abstract void bindAttribs();
	
	protected void bindAttrib(int attrib,String varName) {
		GL20.glBindAttribLocation(programID, attrib, varName);
	}

	@SuppressWarnings("deprecation")
	private static int loadShader(String file, int type){
		StringBuilder shaderSource = new StringBuilder();
		try{
			InputStream in = StaticShader.class.getResourceAsStream(file);
			BufferedReader reader = new BufferedReader(new 	InputStreamReader(in));
			String line;
			while((line = reader.readLine())!=null){
				shaderSource.append(line).append("//\n");
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if(GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader!");
			System.exit(-1);
		}
		return shaderID;
	}
}