package itmo.lab6.basic.baseclasses;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class representing a location in three-dimensional space.
 *
 * @author dorlneylon
 * @version 1.0
 * @since ???
 */
public class Location implements Serializable {
	private long x;
	private Double y;
	private double z;

	/**
	 * Constructs a new `Location` object with the given x, y, and z coordinates.
	 *
	 * @param x The x coordinate of the location.
	 * @param y The y coordinate of the location.
	 * @param z The z coordinate of the location.
	 */
	public Location(long x, Double y, double z) {
		if (y == null) throw new IllegalArgumentException("Invalid location");
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Constructs a new `Location` object with random x, y, and z coordinates.
	 */
	public Location() {
		this.x = (long) (Math.random() * 100);
		this.y = Math.random() * 100;
		this.z = Math.random() * 100;
	}

	/**
	 * Returns the x coordinate of the location.
	 *
	 * @return The x coordinate of the location.
	 */
	public long getX() {
		return x;
	}

	/**
	 * Sets the x coordinate of the location.
	 *
	 * @param x The new x coordinate of the location.
	 */
	public void setX(long x) {
		this.x = x;
	}

	/**
	 * Returns the y coordinate of the location.
	 *
	 * @return The y coordinate of the location.
	 */
	public Double getY() {
		return y;
	}

	/**
	 * Sets the y coordinate of the location.
	 *
	 * @param y The new y coordinate of the location.
	 */
	public void setY(Double y) {
		this.y = y;
	}

	/**
	 * Returns the z coordinate of the location.
	 *
	 * @return The z coordinate of the location.
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Sets the z coordinate of the location.
	 *
	 * @param z The new z coordinate of the location.
	 */
	public void setZ(double z) {
		this.z = z;
	}

	public boolean equals(Location l) {
		return l.getX() == x && l.getY().equals(y) && l.getZ() == z;
	}

	/**
	 * Returns a string representation of the location in the form "(x, y, z)".
	 *
	 * @return A string representation of the location.
	 */

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
