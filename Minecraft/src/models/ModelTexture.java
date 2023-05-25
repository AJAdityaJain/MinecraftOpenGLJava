package models;

public class ModelTexture {
	private int textureID;
	private boolean hasTransparency = false;
	
	public boolean isHasTransparency() {
		return hasTransparency;
	}

	public void setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
	}

	public ModelTexture(int id) {
		this.textureID = id;
	}
	
	public ModelTexture(int id, boolean b) {
		this.textureID = id;
		hasTransparency = b;
	}

	public int getID() {
		return textureID;
	}
}