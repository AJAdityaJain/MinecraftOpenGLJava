package terrain;

import engineTester.Main;

public class World {
	Chunk[][] chunks = new Chunk[Main.WorldSize][Main.WorldSize];

	public void Generate() {
		int[] chunkPos = Main.GetPlayerCoords();//new int[] {(int) (player[0]/Main.chunkSize),(int) (player[1]/Main.chunkSize) };
		if(getChunks(chunkPos[0],chunkPos[1]) == null) {
			new Chunk(chunkPos[0],chunkPos[1]).Generate();	
		}
	}
	public void Generate(int x, int y) {

		new Chunk(x,y).Generate();	
	}
	
	public Chunk getChunks(int x,int y){
		return chunks[x][y];
	}
}
