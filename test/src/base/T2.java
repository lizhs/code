package base;

public class T2 extends T1 {
    public String s;
    {
        System.out.println("111");
    }

    public T2() {
        System.out.println("aaaaaa");
    }

    @Override
    void t1() {
        s = "xx";
        System.out.println("222222222");
    }

    public static void main(String[] args) {
        System.out.println(new T2().s);
    }

    {
        System.out.println("33333333");
    }
}
