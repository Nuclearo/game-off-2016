package nukey.nova.cool;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.badlogic.gdx.graphics.Texture;

public class Map {
	private Tile[][] tilesheet;
	private int height, width;
	private int tileHeight=64,tileWidth=64;
	private Texture[] textures={new Texture("tileA.jpg")};
	
	public Tile getTile(int x,int y) {
		return tilesheet[x][y];
	}
	
	Map(String datafile) {
		BufferedReader mapdata=null;
		try {
			mapdata=new BufferedReader(new FileReader(datafile));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			String size[]=new String[4];
			size[0]=mapdata.readLine();
			size=size[0].split(",");
			
			height=Integer.parseInt(size[0]);
			width=Integer.parseInt(size[1]);
			tilesheet=new Tile[width][height];
			
			//currently making tile coordinates go from topleft - will change later
			String tileCol[]=new String[width];
			int tiletype;
			for (int i=0;i<height;i++) {
				tileCol=mapdata.readLine().split(",",width);
				for (int j=0;j<width;j++) {
					tiletype=Integer.parseInt(tileCol[j]);
					tilesheet[i][j]=new Tile(tiletype);
				}
			}
		}
		catch (NumberFormatException e1) {
			e1.printStackTrace();
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			mapdata.close();
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
}