package net.shipsandgiggles.pirate.entity;

public class Location {

	/** creates class for locaiton instead of always using position*/

	private float xCoordinate;
	private float yCoordinate;

	public Location(float xCoordinate, float yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}

	public float getX() {
		return xCoordinate;
	}

	public void setX(float xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public float getY() {
		return yCoordinate;
	}

	public void setY(float yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
}