package db.setnull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.junit.Test;

public class DBTest {
    // com.ibm.db2.jdbc.net.DB2Driver
    @Test
    public void testOracle() {
        String sql_insert = "insert into testtable values('10000006',?,'6','lisi@163.com')";
        try {
            Connection connection = OracleConnectionUtil.getConnectionSetSchema();

            PreparedStatement prepareStatement = connection.prepareStatement(sql_insert);
            prepareStatement.setNull(1, Types.OTHER);
            //          prepareStatement.setString(1, null);
            prepareStatement.setObject(1, null);
            prepareStatement.execute();
            System.out.println("OK");
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDB2() {
        //		String sql_select = "SELECT * FROM TESTTABLE";
        String sql_insert = "insert into testtable values('10000009',?,'6','lisi@163.com')";
        try {
            Connection connection = DBConnectionUtil.getConnectionSetSchema();

            PreparedStatement prepareStatement = connection.prepareStatement(sql_insert);
//            prepareStatement.setNull(1, 70);
            prepareStatement.setNull(1, Types.BOOLEAN);
//            			prepareStatement.setString(1, null);
            prepareStatement.setObject(1, "new");
            prepareStatement.execute();
            System.out.println("OK");
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printRs(ResultSet rs) throws SQLException {
        int i = 1;
        while (rs.next()) {
            System.out.println(rs.getString(i++));
            System.out.println(rs.getString(i++) + "(encode:" + getEncoding(rs.getString(i - 1)) + ")");
            System.out.println(rs.getString(i++));
            System.out.println(rs.getString(i++));
            System.out.println("=======================");
            i = 1;
        }
    }

    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }

}
