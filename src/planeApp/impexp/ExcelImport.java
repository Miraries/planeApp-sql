package planeApp.impexp;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import planeApp.common.City;
import planeApp.common.Company;
import planeApp.common.Flight;
import planeApp.common.Plane;
import planeApp.dao.CityDAO;
import planeApp.dao.CompanyDAO;
import planeApp.dao.ConnectionManager;
import planeApp.dao.FlightDAO;
import planeApp.dao.PlaneDAO;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JTextPane;

public class ExcelImport {
	
	private JTextPane txtLog;

    public ExcelImport(JTextPane txtLog) {
		super();
		this.txtLog = txtLog;
	}

	public List<Flight> importData(File file, JTextPane txtLog) throws IOException, InvalidFormatException {

        Workbook workbook = WorkbookFactory.create(file);

        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        System.out.println("Retrieving Sheets using lambdas");
        workbook.forEach(sheet -> {
            switch(sheet.getSheetName()) {
            case "Cities": importCities(sheet); break;
            case "Companies": importCompanies(sheet); break;
            case "Planes": importPlanes(sheet); break;
            case "Flights": importFlights(sheet); break;
            default: break;
            }
        });

        workbook.close();
        
        return null;
    }
    
    
    private void importCities(Sheet sheet) {
    	System.out.println("Importing cities");
    	List<City> cities = new ArrayList<>();
    	sheet.forEach(row -> {
    		if(row.getRowNum() < 4 || row.getCell(1) == null) return;
    		String name = row.getCell(1).getStringCellValue();
    		String country = row.getCell(2).getStringCellValue();
    		double lat = row.getCell(3).getNumericCellValue();
    		double lon = row.getCell(4).getNumericCellValue();
    		if(name.isEmpty() || country.isEmpty() || lat == 0 || lon == 0) {
    			log("Invalid city skipped");
    			return;
    		}
            cities.add(new City(name, country, lat, lon));
        });
    	System.out.println(cities);
		try {
			Connection conn = ConnectionManager.getConnection();
			CityDAO dao = new CityDAO(conn);
	    	cities.forEach(city -> {
				try {
					dao.addCity(city);
				} catch (SQLException e) {
					log("City " + city + " already exists");
					return;
				}
				log("City " + city + " has been added");
			});
		} catch (Exception e1) {
			txtLog.setText(txtLog.getText() + "Unable to connect to database" + e1.getMessage());
		}
    }
    
	private void importCompanies(Sheet sheet) {
		System.out.println("Importing companies");
    	List<Company> companies = new ArrayList<>();
    	sheet.forEach(row -> {
    		if(row.getRowNum() < 4 || row.getCell(1) == null) return;
    		String name = row.getCell(1).getStringCellValue();
    		String country = row.getCell(2).getStringCellValue();
    		if(name.isEmpty() || country.isEmpty()){
    			log("Invalid company skipped");
    			return;
    		}
            companies.add(new Company(name, country));
        });
    	System.out.println(companies);
		try {
			Connection conn = ConnectionManager.getConnection();
			CompanyDAO dao = new CompanyDAO(conn);
	    	companies.forEach(company -> {
				try {
					dao.addCompany(company);
				} catch (SQLException e) {
					log("Company " + company + " already exists");
					return;
				}
				log("Company " + company + " has been added");
			});
		} catch (Exception e1) {
			txtLog.setText(txtLog.getText() + "Unable to connect to database" + e1.getMessage());
		}
	}
	
	private void importPlanes(Sheet sheet) {
		System.out.println("Importing planes");
    	List<Plane> planes = new ArrayList<>();
    	sheet.forEach(row -> {
    		if(row.getRowNum() < 4 || row.getCell(1) == null) return;
    		String name = row.getCell(1).getStringCellValue();
    		int seats = (int)row.getCell(2).getNumericCellValue();
    		if(name.isEmpty() || seats == 0){
    			log("Invalid plane skipped");
    			return;
    		}
            planes.add(new Plane(row.getCell(1).getStringCellValue(), (int)row.getCell(2).getNumericCellValue()));
        });
    	System.out.println(planes);
		try {
			Connection conn = ConnectionManager.getConnection();
			PlaneDAO dao = new PlaneDAO(conn);
	    	planes.forEach(plane -> {
				try {
					dao.addPlane(plane);
				} catch (SQLException e) {
					log("Plane " + plane + " already exists");
					return;
				}
				log("Plane " + plane + " has been added");
			});
		} catch (Exception e1) {
			txtLog.setText(txtLog.getText() + "Unable to connect to database" + e1.getMessage());
		}
	}
	
	private void importFlights(Sheet sheet) {
		System.out.println("Importing flights");
    	List<Flight> flights = new ArrayList<>();
    	sheet.forEach(row -> {
    		if(row.getRowNum() < 4 || row.getCell(1) == null) return;
    		try {
    			City origin = new City(row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue(), 0, 0);
        		City dest = new City(row.getCell(3).getStringCellValue(), row.getCell(4).getStringCellValue(), 0, 0);
        		Company company = new Company(row.getCell(5).getStringCellValue(), "");
        		Plane plane = new Plane(row.getCell(6).getStringCellValue(), 0);
        		LocalDate date = convertDate(row.getCell(7).getDateCellValue());
        		LocalTime time = convertTime(row.getCell(8).getDateCellValue());
        		int duration = (int)row.getCell(8).getNumericCellValue();
        		int price = (int)row.getCell(9).getNumericCellValue();
        		LocalDateTime dateTime = LocalDateTime.of(date, time);
                flights.add(new Flight(origin, dest, company, plane, dateTime, duration, price));
    		} catch (Exception e) {
    			log("Invalid flight skipped");
    		}
        });
    	System.out.println(flights);
		try {
			Connection conn = ConnectionManager.getConnection();
			FlightDAO dao = new FlightDAO(conn);
	    	flights.forEach(flight -> {
				try {
					dao.addFlight(flight);
				} catch (SQLException e) {
					if(e.getMessage().toLowerCase().contains("duplicate"))
						log("Flight " + flight.toShortString() + " already exists");
					else if(e.getMessage().toLowerCase().contains("cannot be null"))
						log("Invalid " + e.getMessage().split("'")[1] + " for flight " + flight.toShortString());
					else
						e.printStackTrace();
					return;
				}
				log("Flight " + flight.toShortString() + " has been added");
			});
		} catch (Exception e1) {
			txtLog.setText(txtLog.getText() + "Unable to connect to database" + e1.getMessage());
		}
	}
	
	public void log(String message) {
		txtLog.setText(txtLog.getText()+"\n"+message);
	}
	
	public LocalDate convertDate(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	
	public LocalTime convertTime(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalTime();
	}
    
    /*public static void main(String[] args) {
    	try {
			importData(new File("C:\\Users\\encor\\Desktop\\planeApp.xlsx"));
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
	}*/
}