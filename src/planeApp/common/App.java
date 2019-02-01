package planeApp.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class App implements Serializable {
	private static final long serialVersionUID = 194618104895658622L;
	public static App AppInstance = new App();//no need anymore, using SQL
	public IndexSet<Company> companyList;
	public IndexSet<City> cityList;
	public IndexSet<Flight> flightList;
	public IndexSet<Plane> planeList;
	public IndexSet<Passenger> passengerList;
	public App() {
		companyList = new IndexSet<Company>();
		cityList = new IndexSet<City>();
		flightList = new IndexSet<Flight>();
		planeList = new IndexSet<Plane>();
		passengerList = new IndexSet<Passenger>();
	}
	
	/*public boolean serialize(String fileName) {
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
			os.writeObject(this);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static App deserialize(String fileName) {
		try {
			ObjectInputStream oi = new ObjectInputStream(new FileInputStream(fileName));
			App a = (App)oi.readObject();
			oi.close();
			return a;
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Missing app.ser file for deserialization!", "Dialog", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Error while reading app.ser for deserialization!", "Dialog", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		String[] s = {"Companies:", "Cities:", "Planes:", "Passengers:", "Flights:"};
		List<IndexSet<?>> b = new ArrayList<IndexSet<?>>();
		Collections.addAll(b, companyList, cityList, planeList, passengerList, flightList);

		for (int i = 0; i < s.length; i++) {
			sb.append(s[i]+"\n");
			for (Object object : b.get(i)) {
				sb.append("\t"+object+"\n");
			}
		}
		
		return sb.toString();
	}
}