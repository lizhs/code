package jetty;

import java.io.File;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * 
* @author lizhs
* @date 2014-8-7 上午12:33:27 
* @version V1.0   
* <pre>
* http://blog.sina.com.cn/s/blog_676d1a9b0100vly7.html
* 创建一个本地证书  生成服务器证书库
*  keytool -keystore keystore -alias jetty -genkey -keyalg RSA -storepass 123456
* 创建CSR  生成客户端证书库
* keytool -certreq -alias jetty -keystore keystore -file jetty.csr -storepass 123456
* 
* 
* 从客户端证书库中导出客户端证书
* keytool -export -v -alias client -keystore  E:\ssl\jetty.p12  -storetype PKCS12 -storepass 123456 -rfc -file  jetty.cer
* 
* 从p7b中导出相应的根证书
* </pre>
 */
public class JettyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Server service = new Server();
		Connector connector = new SocketConnector();
		// 设置端口
		connector.setPort(8888);
		
		SocketConnector  connector2=new SocketConnector();
		connector2.setPort(8889);
		
		SslSocketConnector  connector3=new SslSocketConnector();
//		connector3.set
		connector3.setNeedClientAuth(true);
		connector3.setKeystore("E:\\ssl\\server.keystore");
		connector3.setTruststore("E:\\ssl\\server.keystore");
//		connector3.setTrustPassword("123456");
		connector3.setPassword("123456");
		connector3.setKeyPassword("123456"); //http://localhost:8888/tt/a.jsp
		connector3.setPort(8883);
		
		// 设置host地址
//		connector.setHost("localhost");
		// 设置Session过期时间
		connector.setMaxIdleTime(10 * 1000);
		 service.addConnector(connector);
//		service.setConnectors(new Connector[] { connector ,connector2,connector3});
//		QueuedThreadPool qtp = new QueuedThreadPool();
//		qtp.setMinThreads(1);
//		qtp.setMaxThreads(1);
//		service.setThreadPool(qtp);
		// 添加反向代理

		// 设置根路径src/main/webapp
		WebAppContext context = new WebAppContext("html", "/tt");
		context.setTempDirectory(new File("tmp"));
		context.setMaxFormContentSize(Integer.MAX_VALUE);
		context.setConfigurationDiscovered(true);
		context.setParentLoaderPriority(true);
		context.setDisplayName("bbbbbbbb");
		
		// context.setParentLoaderPriority(true);
		// context.setResourceBase("E:/tools/apache-tomcat-7.0.37-windows-x86/apache-tomcat-7.0.37/webapps/docs");
		// 防止同一主机session丢失
		context.setInitParameter("org.eclipse.jetty.servlet.SessionCookie", "xxx");

		context.setWelcomeFiles(new String[] { "index.html" });
		service.setHandler(context);
		// service.setStopAtShutdown(true);
		try {
			service.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
