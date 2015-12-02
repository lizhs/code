package dbutils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.After;
//import com.exam.db.DbManager;  
//import com.exam.util.BasicRowProcessorEx;  
import org.junit.Before;
import org.junit.Test;

public class dbutilsTest {

    @Before
    public void beforetest() throws Exception {
        mysql.connect();
        System.out.println("conect ok");
    }

    @After
    public void aftertest() throws SQLException {
        DbUtils.close(mysql.con);
        System.out.println("close ok");
    }

    @Test
    public void testMapHandler() throws Exception {
        Connection conn = mysql.con;
        QueryRunner queryRunner = new QueryRunner();

        // 返回单行记录，使用Map  
        System.out.println("使用Map处理单行记录！");
        Map<String, Object> map = queryRunner.query(conn,
                "select * from tb1", new MapHandler(),
                (Object[]) null);

        System.out.println(map);
    }

    @Test
    public void testArrayListHandler() throws Exception {
        Connection conn = mysql.con;
        QueryRunner queryRunner = new QueryRunner();
        ArrayListHandler h = new ArrayListHandler();
        // 返回单行记录，使用Map  
        System.out.println("使用Map处理单行记录！");
        List<Object[]> ret = queryRunner.query(conn,
                "select * from tb1", h);

        System.out.println(ret);
    }

    @Test
    public void testMapListHandler() throws Exception {
        Connection conn = mysql.con;
        QueryRunner queryRunner = new QueryRunner();
        MapListHandler h = new MapListHandler();
        // 返回单行记录，使用Map  
        System.out.println("使用Map处理单行记录！");
        List<Map<String, Object>> ret = queryRunner.query(conn,
                "select * from tb1", h);

        System.out.println(ret);
    }

    @Test
    public void testBeanHandler() throws Exception {
        Connection conn = mysql.con;
        QueryRunner queryRunner = new QueryRunner();
        BeanHandler<Model1> h = new BeanHandler<Model1>(Model1.class);
        // 返回单行记录，使用Map  
        System.out.println("使用Map处理单行记录！");
        Model1 ret = queryRunner.query(conn,
                "select * from tb1", h);

        System.out.println(ret);
    }
    @Test
    public void testBeanListHandler() throws Exception {
        Connection conn = mysql.con;
        QueryRunner queryRunner = new QueryRunner();
        BeanListHandler<Model1> h = new BeanListHandler<Model1>(Model1.class);
        // 返回单行记录，使用Map  
        System.out.println("使用Map处理单行记录！");
        List<Model1> ret = queryRunner.query(conn,
                "select * from tb1", h);

        System.out.println(ret);
    }

    //    public static void testDBUtilSelect() {
    //        Connection conn = mysql.con;
    //        QueryRunner queryRunner = new QueryRunner();
    //
    //        try {
    //            // 返回单行记录，使用Map  
    //            System.out.println("使用Map处理单行记录！");
    //            List<Map<String, Object>> map = queryRunner.query(conn,
    //                    "select * from test_tb", new MapListHandler(),
    //                    (Object[]) null);
    //
    //            for (Iterator<Entry<String, Object>> i = map.entrySet().iterator(); i
    //                    .hasNext();) {
    //                Entry<String, Object> e = i.next();
    //                System.out.println(e.getKey() + "=" + e.getValue());
    //            }
    //            if (true)
    //                return;
    //            System.out.println("处理多行记录！");
    //            List<Map<String, Object>> list = queryRunner.query(conn,
    //                    "select * from tab where rownum<=3", new MapListHandler(),
    //                    (Object[]) null);
    //
    //            for (Iterator<Map<String, Object>> li = list.iterator(); li
    //                    .hasNext();) {
    //                System.out.println("--------------");
    //                Map<String, Object> m = li.next();
    //                for (Iterator<Entry<String, Object>> mi = m.entrySet()
    //                        .iterator(); mi.hasNext();) {
    //                    Entry<String, Object> e = mi.next();
    //                    System.out.println(e.getKey() + "=" + e.getValue());
    //                }
    //            }
    //
    //            System.out.println("使用Bean处理单行记录！");
    //
    //            // com.exam.test.TestSomething.Tab  
    //            //            Tab tab = queryRunner.query(conn,
    //            //                    "select tname from tab where rownum=1",
    //            //                    new BeanHandler<Tab>(Tab.class));
    //            //            System.out.println("tname=" + tab.getTname());
    //            //            System.out.println("tabtype=" + tab.getTabtype());
    //
    //            System.out.println("使用Array处理单行记录！");
    //            Object[] array = queryRunner.query(conn,
    //                    "select * from tab where rownum=1", new ArrayHandler());
    //
    //            for (int i = 0; i < array.length; i++) {
    //                System.out.println(array[i]);
    //            }
    //
    //            System.out.println("使用Array处理多行记录！");
    //            List<Object[]> arraylist = queryRunner
    //                    .query(conn, "select * from tab where rownum<=3",
    //                            new ArrayListHandler());
    //
    //            for (Iterator<Object[]> itr = arraylist.iterator(); itr.hasNext();) {
    //                Object[] a = itr.next();
    //                System.out.println("--------------");
    //                for (int i = 0; i < a.length; i++) {
    //                    System.out.println(a[i]);
    //
    //                }
    //            }
    //
    //            System.out.println("使用ColumnListHandler处理单行记录，返回其中指定的一列！");
    //            List<Object> colList = queryRunner.query(conn,
    //                    "select * from tab where rownum=1", new ColumnListHandler(
    //                            "tname"));
    //            for (Iterator<Object> itr = colList.iterator(); itr.hasNext();) {
    //                System.out.println(itr.next());
    //            }
    //
    //            System.out
    //                    .println("使用ScalarHandler处理单行记录，只返回结果集第一行中的指定字段，如未指定字段，则返回第一个字段！");
    //            Object scalar1 = queryRunner.query(conn, "select * from tab",
    //                    new ScalarHandler("tname"));
    //            System.out.println(scalar1);
    //            Object scalar2 = queryRunner.query(conn,
    //                    "select tname,tabtype from tab", new ScalarHandler());
    //            System.out.println(scalar2);
    //
    //            // 使用自定义的行处理器  
    //            // Map中的KEY可按输入顺序输出  
    //            System.out.println("使用Map处理单行记录(使用自定义行处理器)！");
    //            Map<String, Object> linkedmap = queryRunner
    //                    .query(
    //                            conn,
    //                            "select tabtype,tname,'wallimn' as programmer from tab where rownum=1",
    //                            new MapHandler(new BasicRowProcessorEx()),
    //                            (Object[]) null);
    //
    //            for (Iterator<Entry<String, Object>> i = linkedmap.entrySet()
    //                    .iterator(); i.hasNext();) {
    //                Entry<String, Object> e = i.next();
    //                System.out.println(e.getKey() + "=" + e.getValue());
    //            }
    //
    //            // 使用自定义的行处理器  
    //            // Map中的KEY可按输入顺序输出  
    //            System.out.println("处理多行记录(使用自定义行处理器)！");
    //            List<Map<String, Object>> listLinedMap = queryRunner
    //                    .query(
    //                            conn,
    //                            "select tabtype,tname,'wallimn' as programmer from tab where rownum<=3",
    //                            new MapListHandler(new BasicRowProcessorEx()),
    //                            (Object[]) null);
    //
    //            for (Iterator<Map<String, Object>> li = listLinedMap.iterator(); li
    //                    .hasNext();) {
    //                System.out.println("--------------");
    //                Map<String, Object> m = li.next();
    //                for (Iterator<Entry<String, Object>> mi = m.entrySet()
    //                        .iterator(); mi.hasNext();) {
    //                    Entry<String, Object> e = mi.next();
    //                    System.out.println(e.getKey() + "=" + e.getValue());
    //                }
    //            }
    //        } catch (SQLException e) {
    //            // TODO Auto-generated catch block  
    //            e.printStackTrace();
    //        }
    //        DbUtils.closeQuietly(conn);
    //    }

}
