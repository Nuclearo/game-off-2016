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
	private int tileHeight=1,tileWidth=1;
	
	private Sprite[] sprites={new Sprite(new Texture("tileA.jpg"))};
	
	public Tile getTile(int x,int y) {
		if(x>=0 && x<width && y>=0 && y<height){
			return tilesheet[x][y];
		} else {
			return null;
		}
	}
	
	public Tile getTileByCoords(float x, float y){
		return getTile((int)Math.floor(x/tileWidth),(int)Math.floor(y/tileHeight));
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
			
			String tileRow[]=new String[width];
			int tiletype;
			
			
			for (int i=0;i<height;i++) {
				tileRow=mapdata.readLine().split(",",width);
				for (int j=0;j<width;j++) {
					
					tiletype=Integer.parseInt(tileRow[j]);		
					tilesheet[j][height-1-i]=new Tile(tiletype,j,height-1-i);
					
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
	public int getDistanceByCoords(Unit unit, float x, float y) {
		int xdist=Math.abs(unit.getXpos()-(int)(x/tileWidth));
		int ydist=Math.abs(unit.getYpos()-(int)(y/tileHeight));
		return (xdist+ydist);
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
				batch.draw(sprites[tilesheet[j][i].getType()], j*tileWidth, i*tileHeight, tileWidth, tileHeight);
			}
		}
	}
}