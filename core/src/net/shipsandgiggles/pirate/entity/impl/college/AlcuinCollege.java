package net.shipsandgiggles.pirate.entity.impl.college;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import net.shipsandgiggles.pirate.conf.Configuration;
import net.shipsandgiggles.pirate.currency.Currency;
import net.shipsandgiggles.pirate.entity.Location;
import net.shipsandgiggles.pirate.entity.Ship;
import net.shipsandgiggles.pirate.entity.BallsManager;
import net.shipsandgiggles.pirate.entity.college.College;

import java.util.UUID;

import static net.shipsandgiggles.pirate.conf.Configuration.PIXEL_PER_METER;

public class AlcuinCollege extends College {

    public World world;

    /** this is the class to control the Alcuin college it is practically the same for each other college*/



    /** construction of college*/

    public AlcuinCollege(Sprite texture, Location location, float maximumHealth, World world) {
        super(UUID.randomUUID(), Type.LANGWITH, texture, location, maximumHealth, texture.getHeight(), texture.getWidth());

        Body body;
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.StaticBody;

        def.position.set(location.getX(), location.getY());

        /** creation of the body*/

        def.fixedRotation = true;
        body = world.createBody(def);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((texture.getWidth() / 2f) / PIXEL_PER_METER, (texture.getHeight() / 2f) / PIXEL_PER_METER);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef. density = 1f;
        fixtureDef.filter.categoryBits = Configuration.Cat_College; /**telling it what category it is */
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
        this.body = body;
        this.cannonBallSprite = new Sprite(new Texture(Gdx.files.internal("models/cannonBall.png")));
        this.world = world;

        this.hitBox = new com.badlogic.gdx.math.Rectangle((int)location.getX()-500,(int)location.getY(), 700,400);


    }

    @Override
    public boolean perform() {
        return false;
    }

    @Override
    public void draw(Batch batch) {
        if(dead){
            return;
        }
        this.getSkin().setPosition(this.getBody().getPosition().x * PIXEL_PER_METER - (this.getSkin().getWidth() / 2f), this.getBody().getPosition().y * PIXEL_PER_METER - (this.getSkin().getHeight() / 2f)); /**sets position of the college */
        this.getSkin().setRotation((float) Math.toDegrees(this.getBody().getAngle()));
        batch.begin();

        this.getSkin().draw(batch);//draws college

        /**changes the college health colour based on the amount it has */
        if(this.getHealth() > (this.getMaximumHealth() * 0.51)){
            batch.setColor(Color.GREEN);
        }
        else if(this.getHealth() > (this.getMaximumHealth() * 0.25)){
            batch.setColor(Color.ORANGE);
        }
        else{
            batch.setColor(Color.RED);
        }

        batch.draw(healthBar, this.body.getPosition().x - this.getSkin().getHeight()/2 + 80 ,this.body.getPosition().y + this.getSkin().getWidth()/2 + 20, (float) (this.getSkin().getWidth()/2 * (this.getHealth() /this.getMaximumHealth())), 10);
        batch.setColor(Color.WHITE);
        batch.end();

    }

    @Override /** shooting the player method if the player is close enough*/
    public void shootPlayer(Ship player) {
        if(this.health == 1 && !this.dead){ /** checks if the college is dead or not*/
            this.counter += Gdx.graphics.getDeltaTime();
            if(this.counter >= 1){
                Currency.get().give(Currency.Type.POINTS, 3);
                Currency.get().give(Currency.Type.GOLD, 5);
                this.counter = 0;
            }
        }
        if(this.hitBox.overlaps(player.hitBox) && timer <= 0 && !this.dead && this.health != 1) {/** creates shot and shoots*/
            BallsManager.createBall(this.world, new Vector2(this.body.getPosition().x, this.body.getPosition().y), new Vector2(player.getEntityBody().getPosition().x, player.getEntityBody().getPosition().y), cannonBallSprite, (short)(Configuration.Cat_Enemy | Configuration.Cat_College), Configuration.Cat_Player, (short) 0);
            this.timer = this.cooldownTimer;
        }
        else if(timer <= 0) this.timer = 0; /** ensures that there is a cool down between every shot*/
        else this.timer -= Gdx.graphics.getDeltaTime();
    }


}
