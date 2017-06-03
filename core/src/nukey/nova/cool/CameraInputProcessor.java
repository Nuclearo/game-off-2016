package nukey.nova.cool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CameraInputProcessor extends CameraInputController {
	public int edgeScrollWidth=16;
	private Viewport view;
	
	public CameraInputProcessor(Viewport view){
		super(view.getCamera());
		this.view = view;
		
	/** The button for rotating the camera. */
	rotateButton = -1;
	rotateAngle = 0f;
	translateButton = Buttons.MIDDLE;
	translateUnits = 2f; 
	forwardButton = -1;
	activateKey = 0;
	alwaysScroll = true;
	scrollFactor = -0.1f;
	pinchZoomFactor = 10f;
	forwardKey = Keys.ANY_KEY;
	backwardKey = Keys.ANY_KEY;
	}

	private final Vector3 tmpV1 = new Vector3();
	private final Vector3 tmpV2 = new Vector3();

	@Override
	public boolean touchDragged(float x, float y, int pointer) {
		return super.touchDragged(x, y, pointer);
	}

	@Override
	protected boolean process (float deltaX, float deltaY, int button) {
		if (button == rotateButton) {
			tmpV1.set(camera.direction).crs(camera.up).y = 0f;
			camera.rotateAround(target, tmpV1.nor(), deltaY * rotateAngle);
			camera.rotateAround(target, Vector3.Y, deltaX * -rotateAngle);
		} else if (button == translateButton) {
			camera.translate(tmpV1.set(camera.direction).crs(camera.up).nor().scl(-deltaX * camera.viewportWidth));
			camera.translate(tmpV2.set(camera.up).scl(-deltaY * camera.viewportHeight));
			if (translateTarget) target.add(tmpV1).add(tmpV2);
		} else if (button == forwardButton) {
			camera.translate(tmpV1.set(camera.direction).scl(deltaY * translateUnits));
			if (forwardTarget) target.add(tmpV1);
		}
		if (autoUpdate) camera.update();
		return true;
	}
	
	@Override
	public void update() {
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.input.getY();
		if (mouseX<=edgeScrollWidth){
			camera.translate(-translateUnits*Gdx.graphics.getDeltaTime(), 0, 0);
		}
		if (mouseX>=Gdx.graphics.getWidth()-edgeScrollWidth){
			camera.translate(translateUnits*Gdx.graphics.getDeltaTime(), 0, 0);
		}
		if (mouseY<=edgeScrollWidth){
			camera.translate(0, translateUnits*Gdx.graphics.getDeltaTime(), 0);
		}
		if (mouseY>=Gdx.graphics.getHeight()-edgeScrollWidth){
			camera.translate(0, -translateUnits*Gdx.graphics.getDeltaTime(), 0);
		}
		super.update();
		camera.update();
	}
	
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		view.setWorldSize(view.getWorldWidth()+amount, view.getWorldHeight()+amount);
		return true;
	}

}
