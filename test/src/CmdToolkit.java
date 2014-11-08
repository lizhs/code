import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

import org.junit.Test;

/**
 * 控制台处理工具箱
 * 
 * @author leizhimin 2009-6-25 14:12:14
 */
public class CmdToolkit {

	private void main(String[] args) {

		// Process p = pb.start();
	}
	@Test
	public void t3() {
		Method m;
	}

	@Test
	public void t1() {
		StringTokenizer st = new StringTokenizer("a b c");
		for (int i = 0; st.hasMoreTokens(); i++)
			System.out.println(st.nextToken());
	}

	@Test
	public void t2() {
		try {
			readConsole("cmd /k cd F:\\code\\v6-ide\\trunk mvn clean install", false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取控制命令的输出结果
	 * 
	 * @param cmd
	 *            命令
	 * @param isPrettify
	 *            返回的结果是否进行美化（换行），美化意味着换行，默认不进行美化,当此参数为null时也不美化，
	 * @return 控制命令的输出结果
	 * @throws IOException
	 */
	public String readConsole(String cmd, Boolean isPrettify) throws IOException {
		System.out.println("执行命令：" + cmd);
		// pb.start();
		Process process = Runtime.getRuntime().exec("f:/test.bat"); // 执行一个系统命令
		InputStream fis = process.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(fis,"GBK"));
		String line = null;
		process.exitValue();
		System.out.println("开始....");
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}

		if (process.exitValue() != 0) {
			System.out.println("错误....");
			br = new BufferedReader(new InputStreamReader(process.getErrorStream(),"GBK"));
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		}
		System.out.println("结束....");
		return "";
	}
}
