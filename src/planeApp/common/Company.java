package planeApp.common;

import java.io.Serializable;

public class Company implements Serializable {
	private static final long serialVersionUID = -3165908557483293673L;
	private String name;
	private String originCountry;
	public Company(String name, String originCountry) {
		super();
		this.name = name;
		this.originCountry = originCountry;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOriginCountry() {
		return originCountry;
	}
	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
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
		if (!(obj instanceof Company))
			return false;
		Company other = (Company) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
