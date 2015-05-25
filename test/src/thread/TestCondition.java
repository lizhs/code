package thread;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import junit.framework.TestCase;

public class TestCondition extends TestCase {
	static final CountDownLatch cdl = new CountDownLatch(31);

	public void testCondition() {
		Monitor monitor = new Monitor();
		ExecutorService executorService = Executors.newFixedThreadPool(31);

		for (int i = 0; i < 30; i++) {
			Student student = new Student(monitor);
			monitor.addClassMate(student);
			executorService.execute(student);
		}
		executorService.execute(monitor);

		try {
			Thread.sleep(10*1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(Thread.currentThread() + "老师还没来");
		monitor.setTeacherIsComing(true);
		System.out.println(Thread.currentThread() + "老师来了");

		try {
			cdl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executorService.shutdown();
	}

	static class Student implements Runnable {
		private Monitor monitor;

		Student() {

		}

		Student(Monitor monitor) {
			this.monitor = monitor;
		}

		@Override
		public void run() {

			try {
				monitor.getLock().lock();
				while (!monitor.isTeacherIsComing()) {
					monitor.getCondition().await();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				monitor.getLock().unlock();
				cdl.countDown();
			}
			System.out.println(Thread.currentThread() + "回到自己座位，坐下");
		}
	}

	static class Monitor extends Student {
		private boolean teacherIsComing = false;

		private Set<Student> classMates = new HashSet<Student>();

		private Lock lock = new ReentrantLock();

		private Condition condition = lock.newCondition();

		@Override
		public void run() {
			lock.lock();
			while (!teacherIsComing) {
				System.out.println(Thread.currentThread() + "看老师是否来了");
			}
			condition.signalAll();
			lock.unlock();
			cdl.countDown();
		}

		public boolean isTeacherIsComing() {
			return teacherIsComing;
		}

		public void setTeacherIsComing(boolean teacherIsComing) {
			this.teacherIsComing = teacherIsComing;
		}

		public void addClassMate(Student student) {
			classMates.add(student);
		}

		public Condition getCondition() {
			return condition;
		}

		public Lock getLock() {
			return lock;
		}
	}
}
