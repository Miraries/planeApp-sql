package planeApp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import planeApp.common.City;
import planeApp.common.Company;
import planeApp.common.Flight;
import planeApp.common.Plane;

public class FlightDAO {

	private Connection conn;

	public FlightDAO(Connection conn) {
		this.conn = conn;
	}

	public List<Flight> getAll() throws Exception {
		List<Flight> list = new ArrayList<>();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT c1.name as origin, c1.country as origin_country, c1.lat as origin_lat, c1.lon as origin_lon,\r\n" + 
						"c2.name as dest, c2.country as dest_country, c2.lat as dest_lat, c2.lon as dest_lon,\r\n" + 
						"p.seats as seats, co.country as company_country, co.name as company, p.name as plane, f.takeoff, f.duration, f.price\r\n" + 
						"FROM `flight` f, `city` c1, `city` c2, `company` co, `plane` p\r\n" + 
						"WHERE f.origin = c1.id AND f.destination = c2.id AND f.company = co.id AND f.plane = p.id");
		ResultSet rs = stmt.executeQuery();
		System.out.println(stmt);

		while (rs.next()) {
			Flight tempFlight = convert(rs);
			list.add(tempFlight);
		}

		return list;
	}
	
	public void addFlight(Flight c) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO flight VALUES (null, \r\n" + 
				"(SELECT id from city WHERE name=?),\r\n" + 
				"(SELECT id from city WHERE name=?),\r\n" + 
				"(SELECT id from company WHERE name=?),\r\n" + 
				"(SELECT id from plane WHERE name=?),\r\n" + 
				"(?),\r\n" + 
				"(?),\r\n" + 
				"(?));");
		
		stmt.setString(1, c.getOrigin().getName());
		stmt.setString(2, c.getDestination().getName());
		stmt.setString(3, c.getAirline().getName());
		stmt.setString(4, c.getPlane().getName());
		stmt.setString(5, c.getTakeoffTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		stmt.setDouble(6, c.getDurationMinutes());
		stmt.setDouble(7, c.getPrice());
		int affected = stmt.executeUpdate();
		System.out.println("Affected rows: " + affected);
	}
	
	public List<Flight> filter(String origin, String dest, int[] date) throws Exception {
		List<Flight> list = new ArrayList<>();
		
		String query = "SELECT c1.name as origin, c1.country as origin_country, c1.lat as origin_lat, c1.lon as origin_lon,\r\n" + 
				"c2.name as dest, c2.country as dest_country, c2.lat as dest_lat, c2.lon as dest_lon,\r\n" + 
				"p.seats as seats, co.country as company_country, co.name as company, p.name as plane, f.takeoff, f.duration, f.price\r\n" + 
				"FROM `flight` f, `city` c1, `city` c2, `company` co, `plane` p\r\n" + 
				"WHERE f.origin = c1.id AND f.destination = c2.id AND f.company = co.id AND f.plane = p.id";
		String where = "";
		
		if(origin.length() != 0)
			where += "\r\nAND c1.name LIKE \"" + origin + "\"";
		if(dest.length() != 0)
			where += "\r\nAND c2.name LIKE \"" + dest + "\"";
		if(date[0] > 0)
			where += "\r\nAND YEAR(f.takeoff) = " + date[0];
		if(date[1] > 0)
			where += "\r\nAND MONTH(f.takeoff) = " + date[1];
		if(date[2] > 0)
			where += "\r\nAND DAY(f.takeoff) = " + date[2];
		
		PreparedStatement stmt = conn.prepareStatement(query + where);
		System.out.println(stmt);
		
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			Flight tempFlight = convert(rs);
			list.add(tempFlight);
		}

		return list;
	}

	private Flight convert(ResultSet set) throws SQLException {

		City origin = new City(set.getString("origin"), set.getString("origin_country"), set.getDouble("origin_lat"), set.getDouble("origin_lon"));
		City dest = new City(set.getString("dest"), set.getString("dest_country"), set.getDouble("dest_lat"), set.getDouble("dest_lon"));
		Company company = new Company(set.getString("company"), set.getString("company_country"));
		Plane plane = new Plane(set.getString("plane"), set.getInt("seats"));
		LocalDateTime takeoff = LocalDateTime.parse(set.getString("takeoff"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
		int duration = set.getInt("duration");
		int price = set.getInt("price");

		return new Flight(origin, dest, company, plane, takeoff, duration, price);
	}
	
	public Date convertDate(LocalDateTime dateToConvert) {
		return java.util.Date
			.from(dateToConvert.atZone(ZoneId.systemDefault())
			.toInstant());
	}
}
