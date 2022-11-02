package net.shipsandgiggles.pirate;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import net.shipsandgiggles.pirate.conf.Configuration;

public class TiledObjectUtil {

	public static void parseTiledObjectLayer(World world, MapObjects objects) {

		/** dynamic body creation of the map to create colliders that the player interacts with so the player doesnt drive out side of the map*/


		/** checks for map object*/
		for (MapObject object : objects) {
			if (!(object instanceof PolylineMapObject)) {
				continue;
			}

			/** creation of the body */
			Shape shape = createPolyLine((PolylineMapObject) object);
			BodyDef def = new BodyDef();
			def.type = BodyDef.BodyType.StaticBody;

			Body body = world.createBody(def);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = shape;
			fixtureDef. density = 1f;
			fixtureDef.filter.categoryBits = Configuration.Cat_walls;
			body.createFixture(fixtureDef);
			shape.dispose();
		}
	}

	private static ChainShape createPolyLine(PolylineMapObject polyline) {
		/** creates the lines that are used in the Tiled application*/
		float[] vertices = polyline.getPolyline().getTransformedVertices(); /** gets the corners of each line*/
		Vector2[] worldVertices = new Vector2[vertices.length / 2];/** gets the length of those lines*/

		for (int i = 0; i < worldVertices.length; i++) { /** adds the body to the line*/
			worldVertices[i] = new Vector2(vertices[i * 2] / Configuration.PIXEL_PER_METER, vertices[i * 2 + 1] / Configuration.PIXEL_PER_METER);
		}

		ChainShape cs = new ChainShape(); /** creates the chainshape and returns it */
		cs.createChain(worldVertices);
		return cs;
	}
}
