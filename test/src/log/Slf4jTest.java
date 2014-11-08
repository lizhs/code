package log;  
      
    import org.slf4j.Logger;  
    import org.slf4j.LoggerFactory;  
     
    public class Slf4jTest {  
        // 统一按照slf4j的API进行开   
      
        Logger logger = LoggerFactory.getLogger(Slf4jTest.class);     
         
      public void testLog(){     
           logger.info("this is a test log");     
       }      
       public static void main(String[] args) {     
           Slf4jTest slf = new Slf4jTest();     
               slf.testLog();     
       }     
   }  