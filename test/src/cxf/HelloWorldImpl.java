package cxf;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(endpointInterface = "cxf.HelloWorld", serviceName = "HelloWorld")
public class HelloWorldImpl implements HelloWorld {

	@Override
	public String sayHi(String text) {
		System.out.println("sayHi called");
		return "Hello " + text;
	}

}