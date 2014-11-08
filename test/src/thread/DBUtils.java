package thread;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {
	static {
		try {
			// 加载数据库驱动
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 获取数据库连接对象
	public static Connection getConn() {
		Connection conn = null;
		try {
			// "jdbc:oracle:thin:@localhost:1521:你的数据库名字", "用户名","密码"
			conn = DriverManager.getConnection("jdbc:oracle:thin:@10.24.1.53:1521:hxdb", "cbmain", "sunline");
//			DriverManager.setLoginTimeout(seconds)
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 获取语句执行对象
	public static Statement getStatement(Connection conn) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
	}

	// 获取预处理语句执行对象
	public static PreparedStatement getPreparedStatement(Connection conn, String sql) {
		PreparedStatement pstmt = null;
		try { 
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pstmt;
	}

	// 获取结果集对象
	public static ResultSet getResultSet(PreparedStatement pstmt) {
		ResultSet res = null;
		try {
			res = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	// 获取结果集对象
	public static ResultSet getResultSet(Statement stmt, String sql) {
		ResultSet res = null;
		try {
			res = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	// 关闭资源方法
	public static void close(Connection conn, Statement stmt, ResultSet res) {
		close(res);
		close(stmt);
		close(conn);
	}

	// 封装方法关闭语句对象
	private static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stmt = null;
		}
	}

	// 封装方法关闭结果集对象
	private static void close(ResultSet res) {
		if (res != null) {
			try {
				res.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			res = null;
		}
	}

	// 封装方法关闭数据库连接对象
	private static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn = null;
	}
}
