package planeApp.common;

import java.io.Serializable;

public class Plane implements Serializable {
	private static final long serialVersionUID = 6677764664417820535L;
	private String name;
	private int seats;
	public Plane(String name, int seats) {
		super();
		this.name = name;
		this.seats = seats;
	}
	
	@Override
	public String toString() {
		return name + " (" + seats + ")";
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	@Override
	public int hashCode() {
		return ((name == null) ? 0 : name.hashCode());
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Plane))
			return false;
		Plane other = (Plane) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
