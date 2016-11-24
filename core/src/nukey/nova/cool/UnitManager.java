package nukey.nova.cool;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import nukey.nova.cool.Cool.Player;

public class UnitManager {
	private ArrayList<Unit> units=new ArrayList<Unit>();
	private Sprite[] sprites={
			new Sprite(new Texture("player.png")),
			new Sprite(new Texture("unitA.png"))};
	private Sprite acted=new Sprite(new Texture("acted.png"));
	private Sprite select=new Sprite(new Texture("select.png"));
		
	UnitManager(String datafile, Map map) {
		BufferedReader unitdata=null;
		unitdata=new BufferedReader(Gdx.files.internal(datafile).reader(1024));
		String unit[]=new String[3];
		Unit newUnit;
		try {
			while ((unit[0]=unitdata.readLine()) != null) {
				unit=unit[0].split(",",3);
				switch (Integer.parseInt(unit[0])) {
					case 0:
						newUnit=new Hacker(
								Integer.parseInt(unit[1])-1,
								Integer.parseInt(unit[2])-1,
								Player.HACKER);
						units.add(newUnit);
						map.getTile(Integer.parseInt(unit[1])-1, Integer.parseInt(unit[2])-1).setUnit(newUnit);;
						break;
					case 1:
						newUnit=new Drone(
								Integer.parseInt(unit[1])-1,
								Integer.parseInt(unit[2])-1,
								Player.AI);
						units.add(newUnit);
						map.getTile(Integer.parseInt(unit[1])-1,Integer.parseInt(unit[2])-1).setUnit(newUnit);;
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
	public ArrayList<Unit> getUnits() {
		return units;
	}
	public void render(SpriteBatch batch, Map map, Cool cool) {
		for (Unit unit: units) {
			batch.draw(sprites[unit.getSprite()], unit.getXpos()*map.getTileWidth(), unit.getYpos()*map.getTileHeight());
			if (unit.getOwner()==cool.getCurrentPlayer() && unit.getActions()==0) {
				batch.draw(acted,unit.getXpos()*map.getTileWidth(),unit.getYpos()*map.getTileHeight());
			}
			if (unit==cool.getSelectedUnit()) {
				batch.draw(select,unit.getXpos()*map.getTileWidth(),unit.getYpos()*map.getTileHeight());
			}
		}
	}
	public void newTurn(Player startturn) {
		for (Unit unit:units) {
			if (unit.getOwner()==startturn) {
				unit.setActions(unit.getMaxActions());
			}
			else {
				unit.setActions(0);
			}
		}
	}
}
