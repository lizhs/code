package classloader;

import java.net.URL;
import java.net.URLClassLoader;

public class Test1 {
    public static void main(String[] args) {
        ClassLoader  cl=null;
        System.out.println(cl=Test1.class.getClassLoader());
        System.out.println(Thread.currentThread().getContextClassLoader());
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(this.getClass().getClassLoader());
            }
        }).start();
        URL[] urls=new URL[]{};
        URLClassLoader  newcl=   new  URLClassLoader(urls,cl) ;
        System.out.println("newcl="+newcl);
        Thread.currentThread().setContextClassLoader(newcl);
        
        
        
//        Class.forName("xxx");
        System.out.println(cl=Test1.class.getClassLoader());
        System.out.println(Thread.currentThread().getContextClassLoader());
        Thread t=  new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(this.getClass().getClassLoader());
            }
        });
        t.start();
        
    }
}
