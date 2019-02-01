package planeApp.models;

import javax.swing.AbstractListModel;

import planeApp.common.App;
import planeApp.common.Plane;

@SuppressWarnings("serial")
public class PlaneListModel extends AbstractListModel<Plane> {

	@Override
	public Plane getElementAt(int index) {
		return App.AppInstance.planeList.get(index);
	}

	@Override
	public int getSize() {
		return App.AppInstance.planeList.size();
	}

	public boolean addElement(Plane p) {
		boolean added = App.AppInstance.planeList.add(p);
		int size = App.AppInstance.planeList.size();
		super.fireContentsChanged(this, size-2, size);
		return added;
	}
	
	public boolean removeElement(int index) {
		boolean removed = App.AppInstance.planeList.remove(index);
		int size = App.AppInstance.planeList.size();
		super.fireContentsChanged(this, size-2, size);
		return removed;
	}

}
