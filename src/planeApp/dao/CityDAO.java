package planeApp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import planeApp.common.City;

public class CityDAO {

	private Connection conn;

	public CityDAO(Connection conn) {
		this.conn = conn;
	}

	public List<City> getAll() throws SQLException {
		List<City> list = new ArrayList<>();

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from city");

		while (rs.next()) {
			City tempCity = convert(rs);
			list.add(tempCity);
		}

		return list;
	}
	
	public boolean addCity(City c) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO city VALUES (null, ?, ?, ?, ?)");
		
		stmt.setString(1, c.getName());
		stmt.setString(2, c.getCountry());
		stmt.setDouble(3, c.getLat());
		stmt.setDouble(4, c.getLon());
		
		int affected = stmt.executeUpdate();
		System.out.println("Affected rows: " + affected);
		return true;
	}
	
	public List<City> search(String name) throws SQLException {
		List<City> list = new ArrayList<>();

		PreparedStatement stmt = conn.prepareStatement("select * from city where name like ?");
		stmt.setString(1, "%"+name+"%");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			City tempCity = convert(rs);
			list.add(tempCity);
		}

		return list;
	}

	private City convert(ResultSet set) throws SQLException {
		String name = set.getString("name");
		String country = set.getString("country");
		double lat = set.getDouble("lat");
		double lon = set.getDouble("lon");
		
		return new City(name, country, lat, lon);
	}
	
	public City get(String name) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("select * from city where name = ?");
		stmt.setString(1, name);
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next())
			return convert(rs);
		throw new SQLException("No city with provided Name");
	}
}
