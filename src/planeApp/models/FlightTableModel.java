package planeApp.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import planeApp.common.App;
import planeApp.common.City;
import planeApp.common.Flight;

@SuppressWarnings("serial")
public class FlightTableModel extends AbstractTableModel {
	
	public static final String[] columnNames = { "Origin", "Destination", "Airline", "Plane Model",
			"Date", "Time", "Duration", "Price"};
	private List<Flight> flights;
	
	public FlightTableModel(List<Flight> flights) {
		this.flights = flights;
	}

	public List<Flight> getFlights() {
		return flights;
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
		return flights.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Flight f = flights.get(rowIndex);
		if (f == null)
	        return null;
		switch (columnIndex) {
	    case 0:
	        return f.getOrigin();
	    case 1:
	        return f.getDestination();
	    case 2:
	        return f.getAirline();
	    case 3:
	        return f.getPlane();
	    case 4:
	    	return f.getTakeOffDate();
	    case 5:
	    	return f.getTakeOffTime();
	    case 6:
	        return f.getDurationFormatted();
	    case 7:
	        return f.getPrice()+"";
	    default:
	        return null;
	    }
	}
}
