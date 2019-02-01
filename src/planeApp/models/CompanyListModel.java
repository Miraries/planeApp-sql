package planeApp.models;

import javax.swing.AbstractListModel;

import planeApp.common.App;
import planeApp.common.Company;

@SuppressWarnings("serial")
public class CompanyListModel extends AbstractListModel<Company> {

	@Override
	public Company getElementAt(int index) {
		return App.AppInstance.companyList.get(index);
	}

	@Override
	public int getSize() {
		return App.AppInstance.companyList.size();
	}

	public boolean addElement(Company c) {
		boolean added = App.AppInstance.companyList.add(c);
		int size = App.AppInstance.companyList.size();
		super.fireContentsChanged(this, size-2, size);
		return added;
	}
	
	public boolean removeElement(int index) {
		boolean removed = App.AppInstance.companyList.remove(index);
		int size = App.AppInstance.companyList.size();
		super.fireContentsChanged(this, size-2, size);
		return removed;
	}

}
