package jvm;

/**
 * -Xss1m
 * @author lizhs
 * @date 2014-3-9
 * @desc:会死机
 */
public class JavaVMStackOOM {

	private void dontStop(){
		while(true){
			
		}
	}
	public void stackLeakByThread(){
		while(true){
			Thread thead=new Thread(new Runnable() {
				
				@Override
				public void run() {
					dontStop();
				}
			});
			thead.start();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JavaVMStackOOM oom=new JavaVMStackOOM();
		oom.stackLeakByThread();

	}

}
