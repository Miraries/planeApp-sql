package planeApp.common;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

public class Test {

	public static void main(String[] args) {
		City c1 = new City("San Francisco", "USA", 37.76, -122.43);
		City c2 = new City("Moscow", "Russia", 55.75, 37.61);
		City c3 = new City("Podgorica", "Montenegro", 42.44, 19.26);
		City c4 = new City("Tivat", "Montenegro", 42.43, 18.69);
		City c5 = new City("Cape Town", "South Africa", -33.94, 18.55);
		City c6 = new City("Melbourne", "Australia", -37.85, 145.06);
		City c7 = new City("Rio de Jeneiro", "Brasil", -22.90, -43.17);
		City c8 = new City("Vardo", "Norway", 70.37, 31.10);
		Company a1 = new Company("Montenegro Airlines", "Montenegro");
		Company a2 = new Company("United Airlines", "USA");
		Company a3 = new Company("Aeroflot", "Russia");
		Passenger p1 = new Passenger(Utils.randomJMBG(0), "Marko", "Markovic");
		Passenger p2 = new Passenger(Utils.randomJMBG(0), "Danilo", "Petrovic");
		Passenger p3 = new Passenger(Utils.randomJMBG(0), "Petar", "Bajceta");
		Plane m1 = new Plane("Boeing 737", 148);
		Plane m2 = new Plane("Airbus A350", 112);
		Plane m3 = new Plane("Airbus A319", 84);
		LocalDateTime d1 = LocalDateTime.of(2018, Month.JANUARY, 14, 9, 30);
		LocalDateTime d2 = LocalDateTime.of(2018, Month.FEBRUARY, 19, 18, 00);
		LocalDateTime d3 = LocalDateTime.of(2018, Month.MARCH, 7, 6, 10);
		LocalDateTime d4 = LocalDateTime.of(2018, Month.FEBRUARY, 5, 20, 30);
		Flight f1 = new Flight(c1, c2, a1, m1, d1, 120, 400);
		Flight f2 = new Flight(c2, c3, a2, m2, d2, 180, 600);
		Flight f3 = new Flight(c1, c3, a3, m3, d3, 340, 200);
		Flight f4 = new Flight(c4, c2, a2, m2, d4, 420, 600);
		Flight f5 = new Flight(c7, c1, a1, m1, d1, 120, 400);
		Flight f6 = new Flight(c4, c5, a2, m2, d2, 180, 600);
		Flight f7 = new Flight(c6, c1, a3, m3, d3, 340, 200);
		Flight f8 = new Flight(c8, c2, a2, m2, d4, 420, 600);
		App app = new App();
		Collections.addAll(app.cityList, c1, c2, c3, c4, c5, c6, c7, c8);
		Collections.addAll(app.companyList, a1, a2, a3);
		Collections.addAll(app.passengerList, p1, p2, p3);
		Collections.addAll(app.planeList, m1, m2, m3);
		Collections.addAll(app.flightList, f1, f2, f3, f4, f5, f6, f7, f8);
		System.out.println(app.toString());
		//app.serialize("app.ser");
	}
}
