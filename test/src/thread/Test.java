package thread;
public class Test extends Thread {   
  public static void main(String[] args) {   
    for (int i = 1; i <= 20; i++) {   
      new Test().start();   
    }   
  }   
  
  public void run() {   
    System.out.print("1");   
//    yield(); ??  
    System.out.print("2");   
  }   
}  