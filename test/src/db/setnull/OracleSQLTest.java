package db.setnull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OracleSQLTest {

    @Test
    public void testQuery() {
        String querySql = "SELECT * FROM TESTTABLE";
        Statement state = null;
        ResultSet rs = null;
        try {
            state = conn.createStatement();
            rs = state.executeQuery(querySql);
            printRs(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //			ConnectionUtil.closeResource(conn, rs, state);
        }
    }

    @Test
    public void testSucessInsert() {
        String sql_insert = "insert into testtable values('10000002',?,?,?)";
        try {
            PreparedStatement prepareStatement = conn.prepareStatement(sql_insert);
            prepareStatement.setString(1, "aaa");
            prepareStatement.setBoolean(2, true);
            prepareStatement.setDate(3, new java.sql.Date(1, 1, 1));
            prepareStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //			ConnectionUtil.closeResource(conn, null, state);
        }
    }

    @Test
    public void testFailInsert() {
        String sql_insert = "insert into testtable values('10000003',?,?,?)";
        try {
            PreparedStatement prepareStatement = conn.prepareStatement(sql_insert);
            prepareStatement.setNull(1, 0);
            prepareStatement.setNull(2, 0);
            prepareStatement.setNull(3, 0); 
            prepareStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //          ConnectionUtil.closeResource(conn, null, state);
        }
    }

    @Test
    public void testDropTable() {
        String sql_insert = "DROP TABLE TESTTABLE";
        Statement state = null;
        try {
            state = conn.createStatement();
            state.executeUpdate(sql_insert);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //			ConnectionUtil.closeResource(conn, null, state);
        }
    }

    @Test
    public void testCreateTable() {
        String sql_createTable = "CREATE TABLE TESTTABLE "
                + "( \"ID\" VARCHAR(40) NOT NULL PRIMARY KEY , "
                + "\"NAME\" VARCHAR(100) , "
                + "\"F1\" char(1) , "//布尔类型
                + "\"F2\" DATE )";
        Statement state = null;
        try {
            state = conn.createStatement();
            state.executeUpdate(sql_createTable);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //			ConnectionUtil.closeResource(conn, null, state);
        }
    }

    Connection conn = null;

    @Before
    public void mySetUP() {
        conn = OracleConnectionUtil.getConnection();
    }

    @After
    public void myDown() {
        try {
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void printRs(ResultSet rs) throws SQLException {
        int i = 1;
        while (rs.next()) {
            System.out.println(rs.getString(i++));
            System.out.println(rs.getString(i++));
            System.out.println(rs.getString(i++));
            System.out.println(rs.getString(i++));
            System.out.println("=======================");
            i = 1;
        }
    }
}
