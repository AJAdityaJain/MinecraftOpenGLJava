package toolbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import engineTester.Main;
import models.RawModel;

public class JBMLLoader {
	private String MainPath;
	public RawModel Load(String file) {
		MainPath = System.getProperty("user.dir");
		String Text = "";
		Text = LoadFrom(file);
		Text = RemoveComments(Text);

		RawModel model = ReadFunctions(Text);
		return model;
	}
	
	private RawModel ReadFunctions(String text) {
		String[] str = text.split("\n");
		
		String VERTEX = "";
		String UV = "";
		String INDICES = "";
		
		for(String line : str) {
			if(line.startsWith("plane")){
				line = line.substring(9);
				line = line.replace("to", ",");
				String[] split = line.split("mapTextureFrom");
				String[] vxs = split[0].split(",");
				String[] uvs = split[1].	split(",");
				int IS = (VERTEX.split(",").length/3);
				
				VERTEX += "," + vxs[3] + "," + vxs[4] + "," + vxs[5] +
						  "," + vxs[3] + "," + vxs[1] + "," + vxs[5] +
						  "," + vxs[0] + "," + vxs[1] + "," + vxs[2] +
						  "," + vxs[0] + "," + vxs[4] + "," + vxs[2] ;
				
				UV += 	"," + uvs[2] + "," + uvs[1] + 
						"," + uvs[2] + "," + uvs[3] +
						"," + uvs[0] + "," + uvs[3] +
						"," + uvs[0] + "," + uvs[1];
				
				int[] a = {0,1,2,2,3,0};
				for(int n : a) {

					INDICES += "," + (n + IS);
				}
			}
			else if(line.startsWith("cube1")) {
				line = line.substring(9);
				line = line.replace("to", ",");
				String[] vxs = line.split(",");
				
				int IS = (VERTEX.split(",").length/3);
				VERTEX += cubeV("," + vxs[0],"," + vxs[1],"," + vxs[2],"," + vxs[3],"," + vxs[4],"," + vxs[5]);
				
				UV += ",0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0";

		        int[] a = {0,3,1,1,3,2,4,5,7,5,6,7,8,11,9,9,11,10,12,13,15,13,14,15,16,19,17,17,19,18,20,21,23,21,22,23};
		        for(int n : a) {
		        	INDICES += "," + (n + IS);
		        }

				a = null;
				vxs = null;
				IS = 0;
				line = null;
				
			}
			else if(line.startsWith("cube4")) {
				//Please dont ask me what i wrote here, i wrote it at 1 in the morning... No idea what the heck this code does,BUT IT WORKS!!!
				line = line.substring(9);
				line = line.replace("to", ",");
				String[] split = line.split("mapTextureFrom");
				String[] vxs = split[0].split(",");
				String[] uvs = split[1].split(",");
				
				int IS = (VERTEX.split(",").length/3);
				VERTEX += cubeV("," + vxs[0],"," + vxs[1],"," + vxs[2],"," + vxs[3],"," + vxs[4],"," + vxs[5]);
		        UV += "," + uvs[0]+","+uvs[3]+","+uvs[0]+","+uvs[5]+","+uvs[2]+","+uvs[5]+","+uvs[2]+","+uvs[3]+","+uvs[0]+","+uvs[3]+","+uvs[0]+","+uvs[5]+","+uvs[2]+","+uvs[5]+","+uvs[2]+","+uvs[3]+","+uvs[4]+","+uvs[3]+","+uvs[4]+","+uvs[5]+","+uvs[2]+","+uvs[5]+","+uvs[2]+","+uvs[3]+","+uvs[4]+","+uvs[3]+","+uvs[4]+","+uvs[5]+","+uvs[2]+","+uvs[5]+","+uvs[2]+","+uvs[3]+","+uvs[2]+","+uvs[1]+","+uvs[2]+","+uvs[3]+","+uvs[4]+","+uvs[3]+","+uvs[4]+","+uvs[1]+","+uvs[0]+","+uvs[1]+","+uvs[0]+","+uvs[3]+","+uvs[2]+","+uvs[3]+","+uvs[2]+","+uvs[1];

		        int[] a = {0,3,1,1,3,2,4,5,7,5,6,7,8,11,9,9,11,10,12,13,15,13,14,15,16,19,17,17,19,18,20,21,23,21,22,23};
		        for(int n : a) {
		        	INDICES += "," + (n + IS);
		        }

				a = null;
				vxs = null;
				uvs = null;
				split = null;
				IS = 0;
				line = null;
				
			}
		}

		VERTEX = VERTEX.substring(1);	
		UV = UV.substring(1);
		INDICES = INDICES.substring(1);
		float[] Vertex = GetFloatArray(VERTEX.split(","));
		float[] Uv = GetFloatArray(UV.split(","));
		int[] Indices = GetIntArray(INDICES.split(","));
		VERTEX = null;
		UV = null;
		INDICES = null;
		str = null;
		
		return Main.loader.loadtoVAO(Vertex,Uv,Indices);
	}
	
	private float[] GetFloatArray(String[] break1) {
		float[] fs = new float[break1.length];
		int i =0;
		for(String v: break1) {
			fs[i] = Float.parseFloat(v);
			i++;
		}
		break1 = null;
		return fs;
	}
	
	private int[] GetIntArray(String[] break1) {
		int[] fs = new int[break1.length];
		int i =0;
		for(String v: break1) {
			fs[i] = Integer.parseInt(v);
			i++;
		}
		break1 = null;
		return fs;
	}
	
	private String RemoveComments(String Text) {
		String[] Split = Text.split("\\?");
		String[] Ref = new String[Split.length];
		
		int i = 0;
		for(String line: Split) {
			if(!line.startsWith("#")) {
				Ref[i] = line;
			}
			i++;
		}
		
		String Returned = "";
		
		for(String line: Ref) {
			if(line != null) {
				Returned+= line + "\n";	
			}
		}
		
		Split = null;
		Ref = null;
		i = 0;

		Returned = Returned.replace(" ", "");
		Returned = Returned.replace("\t", "");
		return Returned;
		
	}
	
	private String LoadFrom(String name) {
		String text = "";
		try {
			File file = new File(MainPath + "\\res\\models\\"+name + ".jbml");
		    Scanner myReader = new Scanner(file);
		    while (myReader.hasNextLine()) {
		    	String data = myReader.nextLine();
		    	text += data + "?";
		    }
		    myReader.close();
		} catch (FileNotFoundException e) {    }
		return text;
	}

	private String cubeV(String x,String y, String z,String a, String b,String c) {
		return x+b+z+x+y+z+a+y+z+a+b+z+x+b+c+x+y+c+a+y+c+a+b+c+a+b+z+a+y+z+a+y+c+a+b+c+x+b+z+x+y+z+x+y+c+x+b+c+x+b+c+x+b+z+a+b+z+a+b+c+x+y+c+x+y+z+a+y+z+a+y+c;
	}
} 