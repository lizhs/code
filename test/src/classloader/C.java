package classloader;

public class C {
    static {
        new B();
    }
    public String log = A.getF();

    public static void main(String[] args) {
        System.out.println(new C().log);
    }
}
