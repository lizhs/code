package proxy;  
  
  
public class TestJavaProxy {  
    public static void main(String[] args) {  
        JavaDynProxy proxy = new JavaDynProxy();  
        Hello hello = (Hello)proxy.getProxyInstance(new HelloImpl());  
        String s = hello.sayHello("Leon");  
        System.out.println(s);  
          
        UserDao userDao = (UserDao) proxy.getProxyInstance(new UserDaoImpl());  
        userDao.login("Leon", "1234");  
        System.out.println(userDao.getClass().getName());  
    }  
}  