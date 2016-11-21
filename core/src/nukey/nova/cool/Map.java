package nukey.nova.cool;

import java.io.BufferedReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Map {
	private Tile[][] tilesheet;
	private int height, width;
	private int tileHeight=64,tileWidth=64;
	
	private Sprite[] sprites={new Sprite(new Texture("tileA.jpg"))};
	
	public Tile getTile(int x,int y) {
		return tilesheet[x][y];
	}
	Map(String datafile) {
		BufferedReader mapdata=null;
		mapdata=new BufferedReader(Gdx.files.internal(datafile).reader(1024));
		try {
			String size[]=new String[4];
			size=mapdata.readLine().split(",");
			
			height=Integer.parseInt(size[0]);
			width=Integer.parseInt(size[1]);
			tilesheet=new Tile[width][height];
			
			String tileCol[]=new String[width];
			int tiletype;
			
			
			for (int i=0;i<height;i++) {
				tileCol=mapdata.readLine().split(",",width);
				for (int j=0;j<width;j++) {
					
					tiletype=Integer.parseInt(tileCol[j]);					
					tilesheet[j][height-1-i]=new Tile(tiletype);
					
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
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public int getTileHeight() {
		return tileHeight;
	}
	public int getTileWidth() {
		return tileWidth;
	}
	public void render(SpriteBatch batch) {
		for (int i=0;i<height;i++) {
			for (int j=0;j<width;j++) {
				batch.draw(sprites[tilesheet[j][i].getType()], j*tileWidth, i*tileHeight);
			}
		}
	}
}