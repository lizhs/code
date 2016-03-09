package db.setnull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DB2SQLTest {
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

    @Test
    public void testQuery0() {
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
    public void testQueryNoSetSchema() {
        String querySql = "SELECT * FROM DB2INST1.TESTTABLE";
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
    public void testInsert() {
        String sql_insert = "insert into testtable values('10000002','张三','123456','123456@163.com')";
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
    public void testOracleSetNull() {
        String sql_insert = "insert into testtable values('10000004',?,'6','lisi@163.com')";
        try {

            PreparedStatement prepareStatement = conn.prepareStatement(sql_insert);
            prepareStatement.setNull(1, 0);
            //          prepareStatement.setString(1, null);
            prepareStatement.setObject(1, null);
            prepareStatement.execute();
            System.out.println("OK");
           
        } catch (Exception e) {
            e.printStackTrace();
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
                + "\"PWD\" VARCHAR(100) , "
                + "\"EMAIL\" VARCHAR(100) )";
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
}
