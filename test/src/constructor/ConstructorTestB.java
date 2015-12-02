package constructor;

public class ConstructorTestB extends ConstructorTestA {
    public ConstructorTestB() {
        System.out.println("b");
    }

    public ConstructorTestB(String n) {
        //如果没有这个行，则执行父类的默认构造函数
        super(n);
        System.out.println("b=" + n);
    }

    public static void main(String[] args) {
        new ConstructorTestB();
        new ConstructorTestB("b");
    }
}
