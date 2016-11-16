package nukey.nova.cool;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

public class Cool extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private OrthographicCamera cam;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

		cam = new OrthographicCamera(w,h);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 2);
        cam.update();
        
        Gdx.input.setInputProcessor(new CameraInputProcessor(cam));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
        cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
