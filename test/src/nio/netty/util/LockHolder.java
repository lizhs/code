package nio.netty.util;

import java.util.concurrent.ConcurrentHashMap;

public class LockHolder {	
	private static final ConcurrentHashMap<String, Lock> holder = new ConcurrentHashMap<String, Lock>();
	
	public static void addLock(final String key,final Lock locker)
	{
		holder.put(key, locker);
	}
	
	public static void removeLock(final String key)
	{
		holder.remove(key);
	}
	
	public static Lock getLock(final String key)
	{
		Lock locker = holder.get(key);
		if (locker == null) {
			return null;
		} else {
			return locker;
		}
	}
}
