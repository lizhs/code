import static org.junit.Assert.*;

import java.io.PrintWriter;

import org.junit.Test;


public class CommTest {

	@Test
	public void testString() {
		String s="abcd";
		int index=s.indexOf("b");
		System.out.println(index);
		System.out.println(s.substring(index));
	}
	
	@Test
	public void testout() {
		PrintWriter pw = new PrintWriter(System.out);
		pw.append("xxx");
		System.out.println("-------------");
		pw.append("bb");
		pw.flush();
	}

}
