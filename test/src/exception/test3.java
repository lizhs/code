package exception;

public class test3 {

	public static void main(String[] args) {
		new test3().t3();
	}

	void t1() {
		int i = 1 / 0;
	}

	void t2() {
		try {
			t1();
		} catch (Exception e) {
			throw new RuntimeException("异常信息2", e);
		}

	}

	void t3() {
		try {
			t2();
		} catch (Exception e) {
			throw new RuntimeException("异常信息3", e);
		}
	}

}