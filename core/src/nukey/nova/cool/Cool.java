package nukey.nova.cool;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Cool extends ApplicationAdapter {
	private SpriteBatch batch;
	private ScalingViewport view;
	private OrthographicCamera cam;
	private CameraInputProcessor camController;
	private Map world;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		world = new Map("testmap.dat");
		

        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

		cam = new OrthographicCamera(w,h);
		view = new ScalingViewport(Scaling.fit,w,h,cam);
		view.update(w, h);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 2);
        cam.far = 1000;
        cam.update();
        
        camController = new CameraInputProcessor(cam);
        camController.translateUnits = 1;
        camController.rotateAngle = 0;
        Gdx.input.setInputProcessor(camController);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
		view.update(w, h);
		
		camController.update();
		batch.setProjectionMatrix(cam.combined);

		batch.begin();
		world.render(batch);
		batch.end();
	}
	
	public void resize(int width, int height) {
//        view.update(width, height);
    }
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
