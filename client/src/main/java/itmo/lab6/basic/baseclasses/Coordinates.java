package itmo.lab6.basic.baseclasses;

import java.io.Serial;
import java.io.Serializable;

/**
 * The Coordinates class represents a pair of coordinates (x, y).
 * It implements the Serializable interface, allowing instances to be written to and read from streams.
 *
 * @author dorlneylon
 * @version 1.0?
 * @since ??.??.????
 */
public class Coordinates implements Serializable {
	private float x;
	private Integer y;

	/**
	 * Constructs a new Coordinates object with the given x and y values.
	 *
	 * @param x the x value of the coordinate
	 * @param y the y value of the coordinate
	 */
	public Coordinates(float x, Integer y) {
		if (x < -520 || y > 256) throw new IllegalArgumentException("Invalid coordinates");
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructs a new Coordinates object with random x and y values.
	 */
	public Coordinates() {
		this.x = (float)(Math.random() * 1000 - 521);
		this.y = (int)(Math.random() * 256);
	}

	/**
	 * Returns the x value of the coordinate.
	 *
	 * @return the x value of the coordinate
	 */
	public float getX() {
		return x;
	}

	/**
	 * Returns the y value of the coordinate.
	 *
	 * @return the y value of the coordinate
	 */
	public Integer getY() {
		return y;
	}

	/**
	 * Sets the x value of the coordinate.
	 *
	 * @param x the new x value of the coordinate
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Sets the y value of the coordinate.
	 *
	 * @param y the new y value of the coordinate
	 */
	public void setY(Integer y) {
		this.y = y;
	}

	public boolean equals(Coordinates c) {
		return c.getX() == this.x && c.getY().equals(this.y);
	}

	/**
	 * Returns a string representation of the coordinate in the format (x, y).
	 *
	 * @return a string representation of the coordinate
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
