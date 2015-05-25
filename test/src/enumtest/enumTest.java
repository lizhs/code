package enumtest;

import java.util.EnumSet;

public class enumTest {
    enum  A{
        a,b,d;
    }
    public static void main(String[] args) {
        EnumSet<A> ss=EnumSet.of(A.a);
        System.out.println(ss);
        for(A a:A.values())
            System.out.println(a.ordinal());
    }
}
