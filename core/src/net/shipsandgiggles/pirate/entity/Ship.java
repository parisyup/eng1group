package net.shipsandgiggles.pirate.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import net.shipsandgiggles.pirate.conf.Configuration;
import net.shipsandgiggles.pirate.screen.impl.GameScreen;

import java.util.UUID;

public class Ship extends MovableEntity {


	/** creation of the main player class*/
	private final Body entityBody;
	private final float turnSpeed;
	private final float driftFactor;

	private float turnDirection;
	private float driveDirection;
	private Sprite texture;
	CannonBall ball;

	public boolean rapidShot = false;
	public float timeBetweenRapidShots = 0.2f;
	public float rapidShotCoolDown = 0.2f;
	public int numberOfShotsLeft = 3;
	public int shotsInRapidShot = 3;

	public float shootingCoolDown = 0.6f;
	public float burstCoolDown = 4f;
	public float shootingTimer = 0f;
	public static float burstTimer = 0f;
	public World world;
	public boolean dead = false;
	public Camera cam;
	public Vector2 deathPosition = new Vector2(0,0);

	public Rectangle hitBox;

	public static float health;
	public static float maxHealth = 200f;
	public float timeToRegen = 0;
	private float healSpeed = 30;

	public Ship(Sprite texture, float spawnSpeed, float maxSpeed, float driftFactor, float turnSpeed, Location location, float height, float width, Camera cam) {
		super(UUID.randomUUID(), texture, location, EntityType.SHIP, 20, spawnSpeed, maxSpeed, height, width); // TODO: Implement health.
		/** constructor*/
		this.health = this.maxHealth;
		this.turnDirection = 0;
		this.driveDirection = 0;
		this.driftFactor = driftFactor;
		this.turnSpeed = turnSpeed;
		this.texture = texture;
		this.cam = cam;

		/**Creation of Body */
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(location.getX(), location.getY());
		bodyDef.fixedRotation = false;

		this.entityBody = GameScreen.world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((width / 2) / Configuration.PIXEL_PER_METER, (height / 2) / Configuration.PIXEL_PER_METER);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef. density = 1f;
		fixtureDef.filter.categoryBits = Configuration.Cat_Player;
		this.entityBody.createFixture(fixtureDef).setUserData(this);
		shape.dispose();
		this.hitBox = new Rectangle(location.getX(), location.getY(), texture.getWidth(), texture.getHeight());
		this.world = GameScreen.world;
	}

	@Override
	public void draw(Batch batch) { /** draws player on screen*/
		if(dead) {
			//GameScreen.zoomOut(0.1f);
			return;
		}
		batch.begin();
		this.getSprite().draw(batch);
		batch.end();
		this.hitBox.setPosition(this.getEntityBody().getPosition());
	}

	@Override
	public void shootPlayer(Ship player) {}

	@Override
	public void death() { /** checks if player is dead if not then sets them as dead and gets their last position*/
		if(this.dead) return;
		this.deathPosition.x = this.getEntityBody().getPosition().x;
		this.deathPosition.y = this.getEntityBody().getPosition().y;
		this.dead = true;
	}

	public void shoot(World world, Sprite cannonBallSprite, Camera cam, short categoryBits, short maskBit, short groupIndex){ /** shooting function for a singular shot towards the mouse */
		Vector3 mouse_position = new Vector3(0,0,0);
		mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0); /** gets mouse position*/
		cam.unproject(mouse_position);
		BallsManager.createBall(world, new Vector2(this.getEntityBody().getPosition().x, this.getEntityBody().getPosition().y), new Vector2(mouse_position.x, mouse_position.y), cannonBallSprite, categoryBits, maskBit, groupIndex); /** creates shot*/
	}

	public void burstShoot(World world, Sprite cannonBallSprite, Camera cam, short categoryBits, short maskBit, short groupIndex) { /** creates the burst shot*/
		float angle = this.getEntityBody().getAngle();
		System.out.println(Math.toDegrees(angle));
		BallsManager.createBallAtAngle(world, new Vector2(this.getEntityBody().getPosition().x, this.getEntityBody().getPosition().y), angle, cannonBallSprite, categoryBits, maskBit, groupIndex);
		BallsManager.createBallAtAngle(world, new Vector2(this.getEntityBody().getPosition().x, this.getEntityBody().getPosition().y), (float)Math.toRadians(Math.toDegrees(angle) -180), cannonBallSprite, categoryBits, maskBit, groupIndex); /** creation at an angle of the ball */
		this.rapidShot = true; /** sets the rapid ball to true so we can shoot the side balls*/
		this.numberOfShotsLeft = this.shotsInRapidShot;
	}

	public void rapidShot(World world, Sprite cannonBallSprite, Camera cam, short categoryBits, short maskBit, short groupIndex){ /** does the rapid side shots */
		float angle = this.getEntityBody().getAngle();
		if(this.rapidShot && this.timeBetweenRapidShots <= 0){
			BallsManager.createBallAtAngle(world, new Vector2(this.getEntityBody().getPosition().x, this.getEntityBody().getPosition().y), (float)Math.toRadians(Math.toDegrees(angle) -90), cannonBallSprite, categoryBits, maskBit, groupIndex);
			BallsManager.createBallAtAngle(world, new Vector2(this.getEntityBody().getPosition().x, this.getEntityBody().getPosition().y), (float)Math.toRadians(Math.toDegrees(angle) + 90), cannonBallSprite, categoryBits, maskBit, groupIndex);
			this.timeBetweenRapidShots = this.rapidShotCoolDown;
			this.numberOfShotsLeft--;
		}
		if(this.numberOfShotsLeft <= 0){ /** cool down management*/
			this.rapidShot = false;
		}
		if(this.timeBetweenRapidShots <= 0){
			this.timeBetweenRapidShots = 0;
		}
		if(this.timeBetweenRapidShots >= 0){
			this.timeBetweenRapidShots -= Gdx.graphics.getDeltaTime();
		}

	}
	public void updateShots(World world, Sprite cannonBallSprite, Camera cam, short categoryBits, short maskBit, short groupIndex) { /** checks for updated shots and if the player shoots or not*/
		if(this.dead) return;
		/** health management */
		if(this.timeToRegen > 0){
			this.timeToRegen -= Gdx.graphics.getDeltaTime();
		}
		else{
			if(this.health > this.maxHealth){
				this.health = this.maxHealth;
			}
			else if(this.timeToRegen <= 0 && this.health < this.maxHealth){
				this.timeToRegen = 0;
				this.health += this.healSpeed * Gdx.graphics.getDeltaTime();
			}
		}


		/** checks for rapid shots*/
		rapidShot(world, cannonBallSprite, cam, categoryBits, maskBit, groupIndex);

		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && shootingTimer <= 0){ /**if player is shooting then shoot */
			this.shoot(world, cannonBallSprite, cam, Configuration.Cat_Player, (short)(Configuration.Cat_Enemy | Configuration.Cat_College), (short) 0);
			this.shootingTimer = shootingCoolDown;
		}

		if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && burstTimer <= 0){ /** if player uses burst then shoot burst*/
			this.burstShoot(world, cannonBallSprite, cam, Configuration.Cat_Player, (short)(Configuration.Cat_Enemy | Configuration.Cat_College), (short) 0);
			this.burstTimer = burstCoolDown;
		}


		/** cool down management */
		if(burstTimer >= 0){
			burstTimer -= Gdx.graphics.getDeltaTime();
		}
		else{
			burstTimer = 0;
		}
		if(shootingTimer >= 0){
			shootingTimer -= Gdx.graphics.getDeltaTime();
		}
		else {
			shootingTimer = 0;
		}

	}



	/** gets the position of the player*/
	public Vector2 getPosition() {
		Vector2 position = new Vector2();

		position.x = super.getLocation().getX();
		position.y = super.getLocation().getY();

		return position;
	}

	public Sprite getSprite(){
		return this.texture;
	}

	public Body getEntityBody() {
		return this.entityBody;
	}

	public Vector2 getForwardVelocity() {
		Vector2 currentNormal = this.getEntityBody().getWorldVector(new Vector2(0, 1));
		float dotProduct = currentNormal.dot(this.getEntityBody().getLinearVelocity());

		return multiply(dotProduct, currentNormal);
	}

	public Vector2 multiply(float a, Vector2 v) {
		return new Vector2(a * v.x, a * v.y);
	}

	public Vector2 getLateralVelocity() {
		Vector2 currentNormal = this.getEntityBody().getWorldVector(new Vector2(1, 0));
		float dotProduct = currentNormal.dot(this.getEntityBody().getLinearVelocity());

		return multiply(dotProduct, currentNormal);
	}

	public void setTexture(Sprite texture){
		this.texture = texture;
	}

	public float getTurnDirection() {
		return this.turnDirection;
	}

	public void setTurnDirection(float turnDirection) {
		this.turnDirection = turnDirection;
	}

	public float getTurnSpeed() {
		return this.turnSpeed;
	}

	public float getDriveDirection() {
		return this.driveDirection;
	}

	public void setDriveDirection(float driveDirection) {
		this.driveDirection = driveDirection;
	}

	public float getDriftFactor() {
		return this.driftFactor;
	}

	public void takeDamage(float damage){
		timeToRegen = 5f;
		this.health -= damage * 0.8;
		if(this.health <= 0){
			this.death();
		}
	}
}

