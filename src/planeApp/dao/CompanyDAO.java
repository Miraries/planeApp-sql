package planeApp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import planeApp.common.Company;

public class CompanyDAO {

	private Connection conn;

	public CompanyDAO(Connection conn) {
		this.conn = conn;
	}

	public List<Company> getAll() throws SQLException {
		List<Company> list = new ArrayList<>();

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from Company");

		while (rs.next()) {
			Company tempCompany = convert(rs);
			list.add(tempCompany);
		}

		return list;
	}
	
	public void addCompany(Company c) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO company VALUES (null, ?, ?)");
		
		stmt.setString(1, c.getName());
		stmt.setString(2, c.getOriginCountry());
		
		int affected = stmt.executeUpdate();
		System.out.println("Affected rows: " + affected);
	}
	
	public List<Company> search(String name) throws SQLException {
		List<Company> list = new ArrayList<>();

		PreparedStatement stmt = conn.prepareStatement("select * from company where name like ?");
		stmt.setString(1, "%"+name+"%");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			Company tempCompany = convert(rs);
			list.add(tempCompany);
		}

		return list;
	}

	private Company convert(ResultSet set) throws SQLException {
		String name = set.getString("name");
		String country = set.getString("country");
		
		return new Company(name, country);
	}
	
	public Company get(String name) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("select * from company where name = ?");
		stmt.setString(1, name);
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next())
			return convert(rs);
		throw new SQLException("No Company with provided Name");
	}
}
