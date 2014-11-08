package thread;

import java.util.concurrent.atomic.AtomicInteger;

public class Obj {
	static AtomicInteger i = new AtomicInteger(0);
	private int obj_index = 0;

	public Obj() {
		obj_index = i.incrementAndGet();
	}

	@Override
	public String toString() {
		return "obj=" + obj_index;
	}
}
