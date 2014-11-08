package exception;

import java.io.*;
import java.net.*;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileReader fr = null;
		try {
			int i = 1 / 0;
			// FileNotFoundException
			fr = new FileReader("d:\\aa.txt");
			// UnknownHostException,IOException
			Socket s = new Socket("192.168.1.22", 7899);
		} catch (UnknownHostException e) {
			// e.printStackTrace();
			System.out.println("exception handled at \"UnknownHostException\"");
		} catch (FileNotFoundException e2) {
			// e2.printStackTrace();
			System.out.println("exception handledat\"FileNotFoundException\"");
		} catch (IOException e3) {
			// e3.printStackTrace();
			System.out.println("exception handled at \"other IOException\"");

		} finally {
			System.out.println("finally starts");
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("finally ends");
		}

		System.out.println("print after \"try{}catch{}finally{}\"");
	}

}