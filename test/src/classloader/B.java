package classloader;

public class B {
    static {
        A.setF("BBBBB");
        System.out.println("ccc");
    }
}
