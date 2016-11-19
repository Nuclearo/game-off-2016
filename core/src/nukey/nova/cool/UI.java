package nukey.nova.cool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class UI {

	private BitmapFont font = new BitmapFont();
	private Stage stage;
	private Table table;
	private Cool game;
	private Label turnIndicator;
	
	public UI(Cool game) {
		this.game = game;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        
        LabelStyle style = new LabelStyle();
        style.fontColor = Color.WHITE;
        style.font = font;
        
        turnIndicator = new Label("Turn indicator",style);
        table.add(turnIndicator);
        table.top().left();
	}
	
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
    }
	
	public void draw(){
		turnIndicator.setText(game.getCurrentPlayer().name()+"'s turn!");
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
	}
	public InputProcessor getController() {
		return new KeyboardShortcutController();
	}
	
	
}
