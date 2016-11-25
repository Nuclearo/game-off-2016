package nukey.nova.cool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class UI {

	private BitmapFont font = new BitmapFont();
	private Stage stage;
	private Table table;
	private Cool game;
	private Label turnIndicator;
	private Label unitInfo;

	
	public UI(Cool game) {
		this.game = game;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        
        turnIndicator = new Label("Turn indicator",new LabelStyle(font,Color.GREEN));
        table.add(turnIndicator).top();
        
        table.add().expandX();
        
        unitInfo = new Label ("Hi I'm a wobot!\nSuch siv...", new LabelStyle(font, Color.WHITE));
        unitInfo.setAlignment(Align.right);
        table.add(unitInfo);
        unitInfo.setVisible(false);
        
        table.row();
        
        table.top().left();
        table.setDebug(true);
	}
	
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
    }
	
	public void draw(){
		turnIndicator.setText(game.getCurrentPlayer().name()+"'s turn!");
		if(game.getSelectedUnit()==null){
			unitInfo.setVisible(false);
		}else{
			unitInfo.setVisible(true);
			//TODO fill unit info somehow
		}
	    stage.act(Gdx.graphics.getDeltaTime());
	    stage.draw();
	}

	private class KeyboardShortcutController extends InputAdapter{		
		@Override
		public boolean keyUp(int keycode) {
			switch(keycode){
			case Keys.SPACE:
				game.nextTurn();
				return true;
			default:	
				return false;
			}
		}
		
		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			switch(button){
			case Buttons.LEFT:
				Vector3 worldCoord = game.getCamera().unproject(new Vector3(screenX, screenY, 0));
				Tile tile =  game.getWorld().getTileByCoords(worldCoord.x, worldCoord.y);
				if(tile!=null){
					game.setSelectedUnit(tile.getUnit());
				} else {
					game.setSelectedUnit(null);
				}
				return true;
			case Buttons.RIGHT:
				if (game.getSelectedUnit()!=null
				&& game.getSelectedUnit().getActions()>0
				&& (game.getSelectedUnit().getOwner()==game.getCurrentPlayer())) {
					Unit current=game.getSelectedUnit();
					Map world=game.getWorld();
					
					Vector3 worldCoord2 = game.getCamera().unproject(new Vector3(screenX, screenY, 0));
					Tile tile2 =  world.getTileByCoords(worldCoord2.x, worldCoord2.y);
					
					if(tile2!=null){
						int dist=world.getDistanceByCoords(current, worldCoord2.x, worldCoord2.y);
						
						if (tile2.getUnit()!=null) {
							if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && (current instanceof Hacker)) {
								if (dist<=((Hacker)current).getHackRange()) {
									//needs specific rebalancing - take more than one action, have a progress meter thing
									//(like, hack-HP and stuff)
									//add later
									tile2.getUnit().setOwner(current.getOwner());
									current.setActions(current.getActions()-1);
								}
							} else if (dist<=current.getRange()) {
								Unit target=tile2.getUnit();
								target.setHP(target.getHP()-current.getAttack());
								if (target.getHP()<0) {
									game.getUnitManager().getUnits().remove(target);
									tile2.setUnit(null);;
								}
								current.setActions(current.getActions()-1);
							}
						} else {
							if (dist<=current.getSpeed()) {
								world.getTile(current.getXpos(),current.getYpos()).setUnit(null);
								current.setXpos((int)(worldCoord2.x/world.getTileWidth()));
								current.setYpos((int)(worldCoord2.y/world.getTileHeight()));
								tile2.setUnit(current);
								current.setActions(current.getActions()-1);
							}
						}
					}
				}

				
/* TODO 1: enemy actions
2: hacking
3: repeatable commands*/
				return true;
			default:
				return false;
			}
		}
	}
	
	public InputProcessor getController() {
		return new KeyboardShortcutController();
	}
	
	
}
