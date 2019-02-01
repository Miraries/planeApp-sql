package planeApp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import planeApp.common.Plane;

public class PlaneDAO {

	private Connection conn;

	public PlaneDAO(Connection conn) {
		this.conn = conn;
	}

	public List<Plane> getAll() throws SQLException {
		List<Plane> list = new ArrayList<>();

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from Plane");

		while (rs.next()) {
			Plane tempPlane = convert(rs);
			list.add(tempPlane);
		}

		return list;
	}
	
	public void addPlane(Plane c) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO plane VALUES (null, ?, ?)");
		
		stmt.setString(1, c.getName());
		stmt.setInt(2, c.getSeats());
		
		int affected = stmt.executeUpdate();
		System.out.println("Affected rows: " + affected);
	}
	
	public List<Plane> search(String name) throws SQLException {
		List<Plane> list = new ArrayList<>();

		PreparedStatement stmt = conn.prepareStatement("select * from plane where name like ?");
		stmt.setString(1, "%"+name+"%");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			Plane tempPlane = convert(rs);
			list.add(tempPlane);
		}

		return list;
	}

	private Plane convert(ResultSet set) throws SQLException {
		String name = set.getString("name");
		int seats = set.getInt("seats");
		
		return new Plane(name, seats);
	}
	
	public Plane get(String name) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("select * from plane where name = ?");
		stmt.setString(1, name);
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next())
			return convert(rs);
		throw new SQLException("No Plane with provided Name");
	}
}
