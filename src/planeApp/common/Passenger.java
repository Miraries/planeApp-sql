package planeApp.common;

import java.io.Serializable;

public class Passenger implements Serializable {
	private static final long serialVersionUID = 668650439867578654L;
	private String jmbg;
	private String firstName;
	private String lastName;
	public Passenger(String jmbg, String firstName, String lastName) {
		super();
		this.jmbg = jmbg;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName + " (" + jmbg + ")";
	}

	public String getJmbg() {
		return jmbg;
	}
	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public int hashCode() {
		return ((jmbg == null) ? 0 : jmbg.hashCode());
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Passenger))
			return false;
		Passenger other = (Passenger) obj;
		if (jmbg == null) {
			if (other.jmbg != null)
				return false;
		} else if (!jmbg.equals(other.jmbg))
			return false;
		return true;
	}
}
