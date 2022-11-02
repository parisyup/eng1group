package net.shipsandgiggles.pirate;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static net.shipsandgiggles.pirate.conf.Configuration.PIXEL_PER_METER;

public class CameraManager {


	/** manages the camera*/

	/** to lock on to something with no smoothing*/
	public static void lockOn(Camera camera, Vector2 target) {
		Vector3 cameraPosition = camera.position;
		cameraPosition.x = target.x * PIXEL_PER_METER;
		cameraPosition.y = target.y * PIXEL_PER_METER;
		camera.position.set(cameraPosition);
		camera.update();
	}

	/** applies smoothing to camera "lerping" */
	public static void lerpOn(Camera camera, Vector2 target, float lerpValue) {
		Vector3 cameraPosition = camera.position;
		cameraPosition.x = cameraPosition.x + (target.x - cameraPosition.x) * lerpValue * PIXEL_PER_METER;
		cameraPosition.y = cameraPosition.y + (target.y - cameraPosition.y) * lerpValue * PIXEL_PER_METER;
		camera.position.set(cameraPosition);
		camera.update();
	}
}
