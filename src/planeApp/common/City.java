package planeApp.common;

import java.io.Serializable;

public class City implements Serializable{
	private static final long serialVersionUID = 1861817355357234805L;
	private String name;
	private String country;
	private double lat;
	private double lon;
	public City(String name, String country, double lat, double lon) {
		super();
		this.name = name;
		this.country = country;
		this.lat = lat;
		this.lon = lon;
	}
	
	public boolean update(City c) {
		if(this.equals(c) && this.lat==c.lat && this.lon==c.lon)
			return false;
		this.name = c.name;
		this.country = c.country;
		this.lat = c.lat;
		this.lon = c.lon;
		return true;
	}
	
	public String longString() {
		return name + ", " + country + " (" + lat + ", " + lon + ")";
	}
	
	@Override
	public String toString() {
		return name + ", " + country;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof City))
			return false;
		City other = (City) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
