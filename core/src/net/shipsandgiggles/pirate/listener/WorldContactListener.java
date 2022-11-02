package net.shipsandgiggles.pirate.listener;

import com.badlogic.gdx.physics.box2d.*;
import net.shipsandgiggles.pirate.entity.Ship;
import net.shipsandgiggles.pirate.entity.college.College;
import net.shipsandgiggles.pirate.entity.CannonBall;

public class WorldContactListener implements ContactListener {

    /** checks for any collides in the game*/
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if(fixtureA == null || fixtureB == null || fixtureB.getUserData() == null || fixtureA.getUserData() == null) return;

        if(fixtureB.getUserData() instanceof CannonBall){ /** checks if the collider is a cannon ball*/
            CannonBall ball = (CannonBall) fixtureB.getUserData();
            if(fixtureA.getUserData() instanceof College){ /** checks if its a college*/
                College college = (College) fixtureA.getUserData();
                college.damage(ball.getDamageDelt()); /** applies damage to college*/
            }
            if(fixtureA.getUserData() instanceof Ship){
                Ship ship = (Ship) fixtureA.getUserData(); /** checks if its a player */
                ship.takeDamage(ball.getDamageDelt()); /** applies damage to ship */
            }
        }

    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
