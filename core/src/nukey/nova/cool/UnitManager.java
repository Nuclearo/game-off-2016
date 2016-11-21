package nukey.nova.cool;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UnitManager {
	private ArrayList<Unit> units=new ArrayList<Unit>();
	private Sprite[] sprites={new Sprite(new Texture("unitA.png"))};
		
	UnitManager(String datafile) {
		BufferedReader unitdata=null;
		unitdata=new BufferedReader(Gdx.files.internal(datafile).reader(1024));
		String unit[]=new String[3];
		try {
			while ((unit[0]=unitdata.readLine()) != null) {
				unit=unit[0].split(",",3);
				switch (Integer.parseInt(unit[0])) {
					case 1:
						units.add(new Drone(
								Integer.parseInt(unit[1]),
								Integer.parseInt(unit[2]),
								1));
						break;
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
			unitdata.close();
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
	public void render(SpriteBatch batch, Map map) {
		for (Unit unit: units) {
			batch.draw(sprites[unit.getSprite()], unit.getXpos()*map.getTileWidth(), unit.getYpos()*map.getTileHeight());
		}
	}
}
