package models;

import engineTester.Main;
import toolbox.JBMLLoader;

public class Models {
	
	private JBMLLoader loader = new JBMLLoader();
	
	public static TexturedModel[] blockModels = new TexturedModel[16];
	
	public Models() {
		RawModel BLOCK4 = loader.Load("Block4");
		RawModel BLOCK1 = loader.Load("Block1");
		RawModel CROSS = loader.Load("CrOSS");

		blockModels[0] = new TexturedModel(BLOCK4,new ModelTexture(Main.loader.loadTexture("grass")));
		blockModels[1] = new TexturedModel(BLOCK1,new ModelTexture(Main.loader.loadTexture("stone"),true));
//		blockModels[2] = new TexturedModel(CROSS,new ModelTexture(Main.loader.loadTexture("tallgrass"),true));
		blockModels[2] = new TexturedModel(BLOCK1,new ModelTexture(Main.loader.loadTexture("dirt"),true));
		
		BLOCK4 = null;
		BLOCK1 = null;
		CROSS = null;
	}
	
	public void Null() {
		loader = null;
	}
}