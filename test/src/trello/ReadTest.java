package trello;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
//19c955a1e2be016fb1e11e26f4b659df
public class ReadTest {
	public static void main(String[] args) {
		try {
			String end="?key=19c955a1e2be016fb1e11e26f4b659df&token=6adafa4082231648151e0992501d777ea564e442a72475f9f53a4ed2abf623a2";
			
			URL url=new URL("https://api.trello.com/1/organizations/26bc314564bd4fb896c91531c2d8062d/boards"+end);
			InputStream input = url.openStream();
			String string = IOUtils.toString(input);
			System.out.println(string);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
