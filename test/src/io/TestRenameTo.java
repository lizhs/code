package io;

//可能我人品比较好，一试就成功
import java.io.*;

public class TestRenameTo {
	public static void main(String[] args) {
		File filea = new File("c:/a.dat");
		if (filea.renameTo(new File("c:/a_backup.dat")))
			System.out.println("rename successful");
		else
			System.out.println("rename fail");
	}
}
