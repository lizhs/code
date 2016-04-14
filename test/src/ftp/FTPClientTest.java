package ftp;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;

public class FTPClientTest {

    public static void main(String[] args) throws SocketException, IOException {
        FTPClient client = new FTPClient();
        client.connect("172.16.32.60", 21);
        client.login("nexus", "sunline");
        System.out.println("OK");
    }

}
