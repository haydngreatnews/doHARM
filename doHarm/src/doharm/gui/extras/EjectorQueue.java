package doharm.gui.extras;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This is a special case of Queue where once the queue has reached capacity it
 * will eject the back of the queue when a new item is added
 * 
 * @author newporhayd
 * 
 * @param <T>
 */
public class EjectorQueue<T> implements Queue<T> {
	private int capacity;
	private Queue<T> backer;

	public EjectorQueue(int capacity) {
		backer = new LinkedList<T>();
		this.capacity = capacity;
	}

	@Override
	public int size() {
		return backer.size();
	}

	@Override
	public boolean isEmpty() {
		return backer.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return backer.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return backer.iterator();
	}

	@Override
	public Object[] toArray() {
		return backer.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return backer.toArray(a);
	}

	@Override
	public boolean remove(Object o) {
		return backer.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return backer.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		for (T a : c) {
			add(a);
		}
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return backer.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return backer.retainAll(c);
	}

	@Override
	public void clear() {
		backer.clear();
	}

	@Override
	public boolean add(T e) {
		return backer.offer(e);
	}

	@Override
	public boolean offer(T e) {
		if (size() >= capacity) {
			poll();
		}
		return backer.offer(e);
	}

	@Override
	public T remove() {
		return backer.poll();
	}

	@Override
	public T poll() {
		return backer.poll();
	}

	@Override
	public T element() {
		return backer.element();
	}

	@Override
	public T peek() {
		return backer.peek();
	}
}
