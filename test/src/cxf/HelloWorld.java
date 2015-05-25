package cxf;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface HelloWorld {
 
	String sayHi(@WebParam(name="xx")String text);
  
}