package jvm;

/**
 * -Xss128k
 * @author lizhs
 * @date 2014-3-9
 * @desc:
 */
public class JavaVMStackSOF {

	private int stackLength=1;
	public void stackLeak(){
		stackLength++;
		stackLeak();
	}
	 
	public static void main(String[] args) throws Throwable {
		JavaVMStackSOF oom=new JavaVMStackSOF();
		try {
			oom.stackLeak();
		} catch (Throwable e) {
			System.out.println(oom.stackLength);
			throw e;
		}
	}

}
