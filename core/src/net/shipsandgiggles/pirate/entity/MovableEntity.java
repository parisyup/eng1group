package net.shipsandgiggles.pirate.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.UUID;

/** assigns the variables to any entity in the game that moves*/

public abstract class MovableEntity extends Entity {

	private final float maximumSpeed;
	private final float originalSpeed;
	private float speed;

	public MovableEntity(UUID uuid, Sprite texture, Location location, EntityType entityType, float maximumHealth, float spawnSpeed, float maximumSpeed, float height, float width) {
		super(uuid, texture, location, entityType, maximumHealth, height, width);

		this.maximumSpeed = maximumSpeed;
		this.originalSpeed = spawnSpeed;
		this.speed = spawnSpeed;
	}

	public float getSpeed() {
		return this.speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getOriginalSpeed() {
		return this.originalSpeed;
	}

	public float getMaximumSpeed() {
		return this.maximumSpeed;
	}
}