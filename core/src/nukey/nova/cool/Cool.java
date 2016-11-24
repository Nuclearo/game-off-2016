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

public class Cool extends ApplicationAdapter {
	private SpriteBatch batch;
	private ScalingViewport view;
	private OrthographicCamera cam;
	private CameraInputProcessor camController;
	private Map world;
	private UI gui;
	private UnitManager unitManager;
	private Unit selectedUnit=null;
	
	public enum Player{
		HACKER,
		AI
	}
	private Player currentPlayer = Player.HACKER;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		gui = new UI(this);
		world = new Map("testmap.dat");
		unitManager = new UnitManager("testunits.dat");

        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

		cam = new OrthographicCamera(16,9);
		view = new ScalingViewport(Scaling.fill,w,h,cam);
		view.update(w, h);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 2);
        cam.far = 1000;
        cam.update();
        
        camController = new CameraInputProcessor(cam);
        camController.translateUnits = 1;
        camController.rotateAngle = 0;
        
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
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		world.render(batch);
		unitManager.render(batch, world, this);
		batch.end();

		gui.draw();
	}
	
	public void resize(int width, int height) {
        view.update(width, height);
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
		if(currentPlayer==Player.HACKER){
			currentPlayer = Player.AI;
			unitManager.newTurn(currentPlayer);
		}else{
			currentPlayer = Player.HACKER;
			unitManager.newTurn(currentPlayer);
		}
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
	
	public Map getWorld() {
		return world;
	}
}
