package terrain;

import blocks.Block;
import blocks.Dirt;
import blocks.Grass;
import engineTester.Main;
import toolbox.Perlin_Noise;

public class Chunk {
	public Block[][][] blocks = new Block[Main.chunkSize][Main.chunkHeight][Main.chunkSize];
	public int x,z;
	private float freq = 2f;
	private int amp = 4; 
	
	private static Perlin_Noise perlin = new Perlin_Noise();
	
	public Chunk(int x,int z) {
		this.x = x;
		this.z = z;
		Main.world.chunks[x][z] = this;
		
	}
	
	public static void setPerlin() {
		perlin.SetNoiseType(Perlin_Noise.NoiseType.Perlin);
	}
	
	private void GetBlock(int x, int y, int z) {
		float Continentalness = perlin.GetNoise(x*freq, z*freq)*amp;
		float Erosion = perlin.GetNoise(x*freq*2, z*freq*2)*amp/2;
		
		int surface = 20 +(int) (Continentalness);
		if(surface < 12) {
			surface = 12;
		}
		System.out.println(surface);
		
		if( y == surface) {
			new Grass(x,y,z);	
		}
		else if(surface-y <3 && surface-y > 0) {
			new Dirt(x,y,z);	
		}
		else if(y < surface) {
//			new Stone(x,y,z);	
		}
		
		Continentalness = 0;
		Erosion = 0;
		surface = 0;
	}
	
	public void Generate() {
		int X = this.x * Main.chunkSize;
		int Z = this.z * Main.chunkSize;
		
		for(int x1 = 0; x1 < Main.chunkSize;x1++) {
			for(int y1 = 0; y1 < Main.chunkHeight;y1++) {
				for(int z1 = 0; z1 < Main.chunkSize;z1++) {
					GetBlock(x1+X,y1,z1+Z);
				}
			}
		}
		
		for(int x = 0; x < blocks.length;x++ ) {
			for(int y = 0;y < blocks[x].length;y++ ) {
				for(int z = 0; z < blocks[x][y].length;z++ ) {
					if(blocks[x][y][z] != null) {
						blocks[x][y][z].update();
					}
				}	
			}	
		}
	}
}
