package models;

public class RawModel {
	private int vaoID;
	private int VertexCount;

	public RawModel(int vaoID,int VertexCount) {
		this.vaoID = vaoID;
		this.VertexCount = VertexCount;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return VertexCount;
	}
	
	
}
