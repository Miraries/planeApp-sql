package planeApp.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author Mo. Joseph
 * Modified!
 *
 *         Allows you to call get with o(1) instead of o(n) to get an instance
 *         by index
 *         WARNING Must implement update methods here because direct changes
 *         to objects will not be propagated to both the list and set.
 */
@SuppressWarnings("serial")
public class IndexSet<E> implements Set<E>, Serializable {
	private final ArrayList<E> list = new ArrayList<>();
	private final HashSet<E> set = new HashSet<>();

	public synchronized boolean add(E e) {
		if (set.add(e)) {
			return list.add(e);
		}
		return false;
	}

	public synchronized boolean remove(Object o) {
		if (set.remove(o)) {
			return list.remove(o);
		}
		return false;
	}
	
	public synchronized boolean update(int index, E o) {
		Object oInList = list.get(index);
		if(oInList != null)
			list.set(index, (E) o);
		else
			return false;
		if(set.remove(oInList)) {
			set.add((E) o);
			return true;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	public synchronized void clear() {
		set.clear();
		list.clear();
	}

	public synchronized E get(int index) {
		if(index >= list.size())
			return null;
		return list.get(index);
	}
	
	public synchronized E get(E e) {
		Iterator<E> i = set.iterator();
		while(i.hasNext()) {
			E in = i.next();
			if(e.equals(in))
				return in;
		}
		return null;
	}

	public synchronized boolean remove(int index) {
		if(index >= list.size())
			return false;
		System.out.println("Removing index: " + index + ", size: " + list.size());
		return set.remove(list.remove(index));
	}

	public synchronized boolean removeAll(Collection<?> c) {
		if (set.removeAll(c)) {
			return list.removeAll(c);
		}
		return true;
	}

	public synchronized boolean retainAll(Collection<?> c) {
		if (set.retainAll(c)) {
			return list.retainAll(c);
		}
		return false;
	}

	public synchronized boolean addAll(Collection<? extends E> c) {
		boolean modified = false;
		for (E e : c)
			if (add(e))
				modified = true;
		return modified;
	}

	@Override
	public synchronized int size() {
		return set.size();
	}

	@Override
	public synchronized boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public synchronized boolean contains(Object o) {
		return set.contains(o);
	}

	@Override
	public synchronized Iterator<E> iterator() {
		return list.iterator();
	}

	@Override
	public synchronized Object[] toArray() {
		return list.toArray();
	}

	@Override
	public synchronized <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}
}