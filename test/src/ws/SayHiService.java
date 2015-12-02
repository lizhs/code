package ws;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


@B("xx")
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class SayHiService {

	public User getUser() {
		User u = new User();
		u.setAge(1);
		u.setName("bb");
		return u;
	}

	public void sayHiDefault(User user) {
		System.out.println("当前线程"+Thread.currentThread());
		user.setName(user.getName()+"xx");
		System.out.println("Hi, Johness!" + user.getName()+"  "+user.hashCode());
	}

	public void sayHi(String name, Log log) {
		log.info("xxxxxxxxxxx,我来自服务端数据");
		log.setA("服务端数据");
		System.out.println("Hi, " + name + "!");
	}

	public int checkTime(Date clientTime) {
		// 精确到秒
		String dateServer = new java.sql.Date(System.currentTimeMillis()).toString() + " "
				+ new java.sql.Time(System.currentTimeMillis());
		String dateClient = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(clientTime);
		return dateServer.equals(dateClient) ? 1 : 0;
	}

}