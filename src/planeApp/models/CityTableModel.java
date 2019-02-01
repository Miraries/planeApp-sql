package planeApp.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import planeApp.common.App;
import planeApp.common.City;
import planeApp.dao.CityDAO;
import planeApp.dao.ConnectionManager;

@SuppressWarnings("serial")
public class CityTableModel extends AbstractTableModel {
	public static final String[] columnNames = { "City Name", "Country", "Latitude", "Longitude" };
	private List<City> cities;

	public CityTableModel(List<City> cities) {
		this.cities = cities;
	}

	public List<City> getCities() {
		return cities;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getRowCount() {
		return cities.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		City city = cities.get(rowIndex);
		if (city == null)
	        return null;
		switch (columnIndex) {
	    case 0:
	        return city.getName();
	    case 1:
	        return city.getCountry();
	    case 2:
	        return city.getLat();
	    case 3:
	        return city.getLon();
	    default:
	        return null;
	    }
	}
}

