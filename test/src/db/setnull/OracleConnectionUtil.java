package db.setnull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class OracleConnectionUtil {
	public static final String driver = "oracle.jdbc.driver.OracleDriver";
//	public static final String url = "jdbc:db2://10.24.1.94:60000/test:retrieveMessagesFromServerOnGetMessage=true;";
	 public static final String url = "jdbc:oracle:thin:@10.24.1.64:1521:orcl";

	public static final String user = "tsp";
	public static final String pwd = "tsp";
	static Properties properties = new Properties();
	static{
		properties.setProperty("user", user);
		properties.setProperty("password", pwd);
//		properties.setProperty("currentSchema", "DB2INST1");
	}
	public static Connection getConnectionSetSchema(){
		try{
			Class.forName(driver);
			return DriverManager.getConnection(url, properties);
		}catch(Exception e){
			throw new RuntimeException("获取数据库连接对象失败",e);
		}
	}
	public static Connection getConnection(){
		Properties propertiesNoSchema = new Properties();
		propertiesNoSchema.setProperty("user", properties.getProperty("user"));
		propertiesNoSchema.setProperty("password", properties.getProperty("password"));
		try{
			Class.forName(driver);
			return DriverManager.getConnection(url, propertiesNoSchema);
		}catch(Exception e){
			throw new RuntimeException("获取数据库连接对象失败",e);
		}
	}
	public static void closeConnection(Connection conn){
		try{
			if(conn!=null&&!conn.isClosed()){
				conn.close();
			}
		}catch(Exception e){
			throw new RuntimeException("关闭数据库连接失败!",e);
		}
	}
	public static void closeResultSet(ResultSet rs){
		try{
			if(rs!=null&&!rs.isClosed()){
				rs.close();
			}
		}catch(Exception e){
			throw new RuntimeException("关闭数据库查询结果集失败!",e);
		}
	}
	public static void closeStatement(Statement stt){
		try{
			if(stt!=null&&!stt.isClosed()){
				stt.close();
			}
		}catch(Exception e){
			throw new RuntimeException("关闭sql执行器失败!",e);
		}
	}
	public static void closeResource(Connection conn,ResultSet rs,Statement st){
		closeConnection(conn);
		closeResultSet(rs);
		closeStatement(st);
	}
	
}
