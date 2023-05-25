package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;

public class Maths {
	private static Matrix4f matrix = new Matrix4f();
	private static Vector3f X = new Vector3f(1,0,0);
	private static Vector3f Y = new Vector3f(0,1,0);
	private static Vector3f Z = new Vector3f(0,0,1);
	
	private static Vector3f scale = new Vector3f(1,1,1);
	private static Vector3f negativeCameraPos = new Vector3f(0,0,0);
	
	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry,float rz) {
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), X, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), Y, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), Z, matrix, matrix);
		Matrix4f.scale(scale, matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), X, viewMatrix,
				viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), Y, viewMatrix, viewMatrix);
		Vector3f cameraPos = camera.getPosition();
		negativeCameraPos.x = -cameraPos.x;
		negativeCameraPos.y = -cameraPos.y;
		negativeCameraPos.z = -cameraPos.z;
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		return viewMatrix;
	}

}