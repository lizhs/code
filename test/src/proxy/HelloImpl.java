package proxy;  
  
public class HelloImpl implements Hello {  
    @Override  
    public String sayHello(String name) {  
        String s = "Hello, "+name;  
        System.out.println(this.getClass().getName()+"->"+s);  
        return s;  
    }  
}  