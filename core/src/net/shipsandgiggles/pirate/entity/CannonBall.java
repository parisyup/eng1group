package net.shipsandgiggles.pirate.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import net.shipsandgiggles.pirate.screen.impl.GameScreen;
import net.shipsandgiggles.pirate.screen.impl.LoadingScreen;

import static net.shipsandgiggles.pirate.conf.Configuration.PIXEL_PER_METER;


public class CannonBall {

    /** this is the creation and update for each individual ball*/

    public float timer = 0.8f;
    public World world;
    public Body body;
    public boolean isDestroyed = false;
    public Vector2 target;
    public boolean setAngle = false;
    public float angle;
    public Sprite cannonBall;
    public float speed = 1.1f;
    public float damageDelt = 50f;
    public boolean teleported = false;
    public float finalX = 0;
    public float finalY = 0;

    CannonBall(World world, Sprite cannonBall, int width, int height, Vector2 position, Vector2 target, short categoryBits, short maskBit, short groupIndex){ //constructor
        LoadingScreen.soundController.playCannonShot(); /**plays shound of shooting */
        this.world = world;
        Body body;
        BodyDef def = new BodyDef();
        this.target = target;
        this.cannonBall = cannonBall;

       def.bullet = true;
       def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(position.x, position.y);

        /**creation of the body */
        body = world.createBody(def);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width / 2f) / PIXEL_PER_METER, (height * 1.5f) / PIXEL_PER_METER);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef. density = 1f;
        fixtureDef.filter.categoryBits = categoryBits;/** telling it what it is*/
        fixtureDef.filter.maskBits = (short) (maskBit); /**telling it what it can hit */
        fixtureDef.filter.groupIndex = groupIndex;
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
        this.body = body;

    }
    CannonBall(World world, Sprite cannonBall, int width, int height, Vector2 position, float target, short categoryBits, short maskBit, short groupIndex){ // constructor
        LoadingScreen.soundController.playCannonShot();
        this.world = world;
        Body body;
        BodyDef def = new BodyDef();
        this.angle = target;
        this.cannonBall = cannonBall;


       def.bullet = true;
       def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(position.x, position.y);

        /**body creation */
        body = world.createBody(def);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width / 2f) / PIXEL_PER_METER, (height * 1.5f) / PIXEL_PER_METER);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef. density = 1f;
        fixtureDef.filter.categoryBits = categoryBits; /** telling it what it is*/
        fixtureDef.filter.maskBits = (short) (maskBit); /** telling it what it can hit*/
        fixtureDef.filter.groupIndex = groupIndex;
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
        this.body = body;
        this.body.setTransform(this.body.getPosition().x, this.body.getPosition().y, angle); //sets the angle
        this.setAngle = true;

    }


    public void update(Batch batch) {
            timer -= Gdx.graphics.getDeltaTime(); /**checks if the ball has reached the maximum time it can be alive */
            if(timer <= 0 && !this.isDestroyed){
                LoadingScreen.soundController.playExplosion();/** // plays explosion noise*/
                finalX = this.body.getPosition().x;
                finalY = this.body.getPosition().y;
                GameScreen.add(new Vector2(finalX, finalY)); /** adds explosion animation*/
                this.world.destroyBody(this.body); /** destroies the ball*/
                this.isDestroyed = true;
                BallsManager.removeNext();/** removes it from the array*/
            }
            if(!setAngle){ /** check if it has an angle or a target if it doesnt have an angle creates one towards the target*/
                this.body.setTransform(this.body.getPosition().x, this.body.getPosition().y, (float)Math.atan2( this.body.getPosition().x-this.target.x,this.target.y- this.body.getPosition().y  )); // setting the angle towards the target
                this.setAngle = true;
                this.angle = this.body.getAngle();//getting the angle
            }
            if(this.body.getAngle() != this.angle | this.body.getPosition().x < 0 | this.body.getPosition().x > 1920 | this.body.getPosition().y < 0 | this.body.getPosition().y > 1080){ // checks ifthe ball left the play area or has changes in its angle (if collided)
                teleportBall();
            }

            this.body.applyForceToCenter(this.body.getWorldVector(new Vector2(0, 200079f)), true); /**applies force to the ball*/
            Vector2 direction = new Vector2(this.body.getWorldPoint(new Vector2(0,this.cannonBall.getHeight()))); /**gets the direction the ball is going towards*/
            Vector2 position = this.body.getPosition();
            position.x = position.x + (direction.x - position.x) * speed * PIXEL_PER_METER; /** changes the direction and slightly teleports the ball so it can travel way faster**/
            position.y = position.y + (direction.y - position.y) * speed * PIXEL_PER_METER;
            this.body.setTransform(position, this.body.getAngle()); /**moves ball forward**/
            this.cannonBall.setPosition(this.body.getPosition().x * PIXEL_PER_METER - (this.cannonBall.getWidth() / 2f), this.body.getPosition().y * PIXEL_PER_METER - (this.cannonBall.getHeight() / 2f));
            this.cannonBall.setRotation((float) Math.toDegrees(this.body.getAngle()));



            batch.begin();
            this.cannonBall.draw(batch);
            batch.end();
        }

    public float getDamageDelt() {
        return this.damageDelt;
    }

    public void teleportBall(){
        if(teleported) return;
        LoadingScreen.soundController.playExplosion();
        finalX = this.body.getPosition().x;
        finalY = this.body.getPosition().y;
        GameScreen.add(new Vector2(finalX, finalY));
        this.body.setTransform(10000,10000,0);
        this.teleported = true;
    }
}
