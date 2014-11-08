package ws.client;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class WSClientMain {

	public static void main(String[] args) throws DatatypeConfigurationException {
		// 获取service
		SayHiService service = new SayHiServiceService().getSayHiServicePort();

		// sayhi
		User user = new User();
		user.setName("client name");
		System.out.println(user.hashCode());
		service.sayHiDefault(user);
		System.out.println(user.hashCode()+"  "+user.getName());
		Log log = new Log(){
			@Override
			public void setA(String value) {
				super.setA(value);
				System.out.println("客户端回调：" + value);
			}
		};
		log.setA("xxx");
		service.sayHi("Ahe", log);
		System.out.println("rizhi:" + log.getA());
		user = service.getUser();
		System.out.println("用户名：" + user.getName() + " " + user.getAge());

		// checktime
		// 这里主要说一下时间日期的xml传递，方法还略显复杂
		GregorianCalendar calender = new GregorianCalendar();
		calender.setTime(new Date(System.currentTimeMillis()));
		XMLGregorianCalendar xmldate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calender);
		System.out.println(service.checkTime(xmldate));

	}

}