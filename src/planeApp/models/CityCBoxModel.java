package planeApp.models;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import planeApp.common.App;
import planeApp.common.City;

@SuppressWarnings("serial")
public class CityCBoxModel implements ComboBoxModel<City> {

	City selection = null;
	List<City> cities;
	
	public CityCBoxModel(List<City> cities) {
		super();
		this.cities = cities;
	}

	public City getElementAt(int index) {
		return cities.get(index);
	}

	public int getSize() {
		return cities.size();
	}

	@Override
	public Object getSelectedItem() {
		return selection;
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selection = (City) anItem;
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

}
