package nukey.nova.cool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import nukey.nova.cool.Unit.Action;


public class UI implements EventListener{

	private BitmapFont font = new BitmapFont();
	private Stage stage;
	private Table hudTable;
	private Table buttonBar;
	private TextButton[] buttons;
	private Cool game;
	private Label turnIndicator;
	private String[] unitInfoText = {"watashi desu", "sorera desu"};
	private Label unitInfo;
	private Label bandwidthIndicator;
	private Action selectedAction = null; 

	
	public UI(Cool game) {
		this.game = game;
        stage = new Stage(new ScreenViewport());
        hudTable = new Table();
        hudTable.setFillParent(true);
        stage.addActor(hudTable);
        
        turnIndicator = new Label("Turn indicator",new LabelStyle(font,Color.GREEN));
        hudTable.add(turnIndicator).top();
        
        bandwidthIndicator = new Label ("Bandwidth",new LabelStyle(font, Color.WHITE)); 
        hudTable.add(bandwidthIndicator).expandX();
        
        unitInfo = new Label ("error",new LabelStyle(font, Color.WHITE));
        unitInfo.setAlignment(Align.right);
        unitInfo.setVisible(false);
        //add background to label
        Pixmap pm1 = new Pixmap(1, 1, Format.RGBA8888);
        pm1.setColor(Color.rgba8888(0, 0, 0, 0.4f)); //Translucent black
        pm1.fill();
        Container<Label> infoContainer = new Container<Label>(unitInfo);
        infoContainer.background(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
    	hudTable.add(infoContainer);
        
        hudTable.row();//for now, move buttons where they'd be easier to click
        hudTable.add();
        buttonBar=new Table();
        hudTable.add(buttonBar);
        Pixmap buttonUpColor = new Pixmap(1, 1, Format.RGBA8888);
        buttonUpColor.setColor(Color.rgba8888(1, 1, 1, 1));
        buttonUpColor.fill();
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(new TextureRegion(new Texture(buttonUpColor)));
        Pixmap buttonDownColor = new Pixmap(1, 1, Format.RGBA8888);
        buttonDownColor.setColor(Color.rgba8888(0, 1, 0, 1));
        buttonDownColor.fill();
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(new TextureRegion(new Texture(buttonDownColor)));
        TextButtonStyle buttonStyle = new TextButtonStyle(buttonUp,buttonUp,buttonDown,font);
        buttonStyle.disabledFontColor = Color.GRAY;
        buttonStyle.fontColor = Color.RED;
        buttons = new TextButton[Action.values().length];
        for(Action action:Action.values()){
        	TextButton actionButton = new TextButton(" "+action.name()+" ", buttonStyle);
        	actionButton.setProgrammaticChangeEvents(false);
        	actionButton.addListener(this);
        	buttonBar.add(actionButton);
        	buttons[action.ordinal()] = actionButton;
        }
        hudTable.top().left();
        hudTable.setDebug(true);
	}
	
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
    }
	
	public void draw(){
		turnIndicator.setText(game.getCurrentPlayer().name()+"'s turn!");
		bandwidthIndicator.setText("BANDWIDTH: "+game.getCurrentPlayer().getAvailableBandwidth()+"/10");
		
		final Unit selectedUnit = game.getSelectedUnit();
		if(selectedUnit==null){
			unitInfo.setVisible(false);
			for(Action action: Action.values()) {
				buttons[action.ordinal()].setDisabled(true);
			}
		}else{
			String description = String.format("%s\nHP%12s\nATK%11d\n"
					, unitInfoText[selectedUnit.getID()]
					, String.format("%d/%d", selectedUnit.getHP(), selectedUnit.getMaxHP())
					, selectedUnit.getAttack());
			unitInfo.setText(description);
			unitInfo.setVisible(true);
			for(Action action: Action.values()) {
				if(selectedUnit.canPerform(action)) {
					buttons[action.ordinal()].setDisabled(false);
				} else {
					buttons[action.ordinal()].setDisabled(true);
				}
			}
		}
	    stage.act(Gdx.graphics.getDeltaTime());
	    stage.getViewport().apply();
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
			case Buttons.LEFT: //unit selection
				Vector2 worldCoord = game.getViewport().unproject(new Vector2(screenX, screenY));
				Tile tile =  game.getWorld().getTileByCoords(worldCoord.x, worldCoord.y);
				if(selectedAction==null) { //select unit
					if(tile!=null){
						game.setSelectedUnit(tile.getUnit());
					} else {
						game.setSelectedUnit(null);
					}
				} else {
					Unit current=game.getSelectedUnit();
					if (current!=null && current.getOwner()==game.getCurrentPlayer()) {
						current.sendCommand(new Command(selectedAction, tile), game);
					}
				}
				return true;
			case Buttons.RIGHT:
				if(selectedAction!=null) { //if we have a selected action right click clears it
					buttons[selectedAction.ordinal()].setChecked(false);
					selectedAction = null;
					return true;
				}
				Unit current=game.getSelectedUnit();
				if (current!=null &&
					current.getOwner()==game.getCurrentPlayer()) {
					Map world=game.getWorld();
					
					Vector2 worldCoord2 = game.getViewport().unproject(new Vector2(screenX, screenY));
					Tile tile2 =  world.getTileByCoords(worldCoord2.x, worldCoord2.y);
					
					if(tile2!=null){
						int dist=world.getDistanceByCoords(current, worldCoord2.x, worldCoord2.y);
						Action action = null;
						
						if (tile2.getUnit()!=null) { //Hack
							if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
								action = Action.HACK;
							} else if (dist<=current.getRange()) { //Attack
								action = Action.ATTACK;
							}
						} else {
							action = Action.MOVE;
						}
						
						if(action!=null&&current.canPerform(action)){
							current.sendCommand(new Command(action, tile2), game);
						}
					}
				}

				
/* TODO 1: enemy actions
3: repeatable commands*/
				return true;
			default:
				return false;
			}
		}
	}
	
	public InputProcessor getController() {
		InputMultiplexer mux = new InputMultiplexer();
        mux.addProcessor(stage);
        mux.addProcessor(new KeyboardShortcutController());
		return mux;
	}

	
	@Override
	public boolean handle(Event event) {
		if(event instanceof ChangeEvent){
			TextButton originOfClick = (TextButton)event.getListenerActor();
			for(TextButton button : buttons){
				//disable all other action buttons
				if(button != originOfClick){
					button.setChecked(false);
				}
			}
			selectedAction = originOfClick.isChecked()?Action.valueOf(originOfClick.getText().toString().trim()):null;
			return true;
		}
		return false;
	}
	
	
}
