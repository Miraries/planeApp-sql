package planeApp.common;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Flight implements Serializable {
	private static final long serialVersionUID = -2912690974393281384L;
	private City origin;
	private City destination;
	private Company company;
	private Plane plane;
	private LocalDateTime takeoffDateTime;
	private double durationMinutes;
	private double price;
	public Flight(City origin, City destination, Company company, Plane plane, LocalDateTime takeoffTime, double durationMinutes,
			double price) {
		this.origin = origin;
		this.destination = destination;
		this.company = company;
		this.plane = plane;
		this.takeoffDateTime = takeoffTime;
		this.durationMinutes = durationMinutes == 0 ? calcDuration() : durationMinutes;
		this.price = price;
	}
	public double calcDuration() {
		return Utils.round(Utils.distance(origin.getLat(), destination.getLat(), origin.getLon(), destination.getLon(), 0, 0)/1000/850,2)*60;
		// 850km/h is average commercial plane speed. Adjusted for other factors and planes, and simplicity
	}
	
	public LocalTime getTakeOffTime() {
		return takeoffDateTime.toLocalTime();
	}
	
	public LocalDate getTakeOffDate() {
		return takeoffDateTime.toLocalDate();
	}
	
	@Override
	public String toString() {
		// Add setting for timezone
		return origin + " -> " + destination + ", " + company + " (" + plane + ")"+ ", " +
				takeoffDateTime + " (" + durationMinutes + "), $" + price;
	}
	public String getDurationFormatted() {
		return String.format("%02d:%02d", (int)durationMinutes/60, (int)durationMinutes%60);
	}
	public int[] getDurationArray() {
		return new int[]{(int)durationMinutes/60, (int)durationMinutes%60};
	}
	public City getOrigin() {
		return origin;
	}
	public void setOrigin(City origin) {
		this.origin = origin;
	}
	public City getDestination() {
		return destination;
	}
	public void setDestination(City destination) {
		this.destination = destination;
	}
	public Company getAirline() {
		return company;
	}
	public void setAirline(Company airline) {
		this.company = airline;
	}
	public Plane getPlane() {
		return plane;
	}
	public void setPlane(Plane plane) {
		this.plane = plane;
	}
	public LocalDateTime getTakeoffTime() {
		return takeoffDateTime;
	}
	public void setTakeoffTime(LocalDateTime takeoffTime) {
		this.takeoffDateTime = takeoffTime;
	}
	public double getDurationMinutes() {
		return durationMinutes;
	}
	public void setDurationMinutes(int durationMinutes) {
		this.durationMinutes = durationMinutes;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + ((takeoffDateTime == null) ? 0 : takeoffDateTime.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Flight))
			return false;
		Flight other = (Flight) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (takeoffDateTime == null) {
			if (other.takeoffDateTime != null)
				return false;
		} else if (!takeoffDateTime.equals(other.takeoffDateTime))
			return false;
		return true;
	}
	public String toShortString() {
		return origin.getName() + " -> " + destination.getName() + " (" + company.getName() + ", " + takeoffDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ")";
	}
	
}
