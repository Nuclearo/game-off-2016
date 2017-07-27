package nukey.nova.cool;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Cool extends ApplicationAdapter {
	private SpriteBatch batch;
	private Viewport view;
	private OrthographicCamera cam;
	private CameraInputProcessor camController;
	private Map world;
	private UI gui;
	private UnitManager unitManager;
	private Unit selectedUnit=null;
	
	public UnitManager getUnitManager() {
		return unitManager;
	}

	public enum Player{
		HACKER,
		AI;
		
		private int availableBandwidth = 10;
		public int getAvailableBandwidth() {
			return availableBandwidth;
		}

		public void setAvailableBandwidth(int availableBandwidth) {
			this.availableBandwidth = availableBandwidth;
		}
	}
	private Player currentPlayer = Player.HACKER;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		gui = new UI(this);
		world = new Map("testmap.dat");
		unitManager = new UnitManager("testunits.dat", world);

        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

		cam = new OrthographicCamera();
		view = new ScalingViewport(Scaling.fill,8,8,cam);
		view.update(w, h);
		Unit player=null;
		for(Unit u: unitManager.getUnits()){
			if(u instanceof Hacker){
				player = u;
				break;
			}
		}
        cam.position.set(player.getXpos()+world.getTileWidth()/2,
        		         player.getYpos()+world.getTileHeight()/2, 2);
        cam.far = 1000;
        cam.update();
        
        camController = new CameraInputProcessor(view);
        
        InputMultiplexer mux = new InputMultiplexer();
        mux.addProcessor(gui.getController());
        mux.addProcessor(camController);
        Gdx.input.setInputProcessor(mux);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camController.update();
		view.apply();
		batch.setProjectionMatrix(view.getCamera().combined);
		batch.begin();
		world.render(batch);
		unitManager.render(batch, world, this);
		batch.end();

		gui.draw();
	}
	
	public void resize(int width, int height) {
        view.update(width, height);
        cam.update();
		gui.resize(width, height);
    }
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	
	public Player getCurrentPlayer(){
		return currentPlayer;
	}

	public void nextTurn() {
		currentPlayer = Player.values()[(currentPlayer.ordinal()+1)%Player.values().length];
		unitManager.newTurn(currentPlayer);
		currentPlayer.availableBandwidth = 10;
	}

	public Unit getSelectedUnit() {
		return selectedUnit;
	}

	public void setSelectedUnit(Unit selectedUnit) {
		this.selectedUnit = selectedUnit;
	}

	public Camera getCamera() {
		return cam;
	}
	
	public Viewport getViewport(){
		return view;
	}
	
	public Map getWorld() {
		return world;
	}
}
