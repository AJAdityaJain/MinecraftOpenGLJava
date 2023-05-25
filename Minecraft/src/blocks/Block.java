package blocks;

import engineTester.Main;

public abstract class Block {
	private int model;
	public int x,y,z;
	public boolean visible = true;
	
	public Block(int model, int x, int y, int z) {
		super();
		this.model = model;
		this.x =x;
		this.y =y;
		this.z =z;
		setBlock(x,y,z,this);
	}
	
	public int getModel() {
		return model;
	}
		
	public void setModel(int model) {
		this.model = model;
	}
		
	
	public static boolean isBlockAt(int x, int y, int z) {
		//God, forgive this mess
		if(y >= 0&& y < Main.chunkHeight && Main.world.getChunks(
				Math.floorDiv(x, Main.chunkSize),
				Math.floorDiv(z, Main.chunkSize)
			)!= null) {
			if(Main.world.getChunks(
				Math.floorDiv(x, Main.chunkSize),
				Math.floorDiv(z, Main.chunkSize)
			)
			.blocks
					[(x)%Main.chunkSize]
			        [y%Main.chunkHeight]
			        [(z)%Main.chunkSize]
			!= null) {
				return true;
			}
		}
		return false;
	}
	
	public void update() {
		if(isBlockAt(x, y+1,z) && isBlockAt(x, y-1,z)&&isBlockAt(x, y,z+1) && isBlockAt(x, y,z-1)&&isBlockAt(x+1, y,z) && isBlockAt(x-1, y,z)) {
			visible = false;
		}
	}
	
	public static void setBlock(int x, int y, int z,Block set) {
		Main.world.getChunks(Math.floorDiv(x, Main.chunkSize),Math.floorDiv(z, Main.chunkSize))
		.blocks[(x)%Main.chunkSize][y%Main.chunkHeight][(z)%Main.chunkSize] = set;
	}
	public static Block getBlock(int x, int y, int z) {
		return Main.world.getChunks(Math.floorDiv(x, Main.chunkSize),Math.floorDiv(z, Main.chunkSize))
		.blocks[(x)%Main.chunkSize][y%Main.chunkHeight][(z)%Main.chunkSize];
	}
}