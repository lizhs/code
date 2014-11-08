package ws;

import javax.xml.ws.Endpoint;

import ws.SayHiService;

public class WSServiceMain {

    /**
     * 发布WebService
     * 简单
     */
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/testjws/service/sayHi", new SayHiService());
    }

}