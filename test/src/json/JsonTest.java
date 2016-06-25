package json;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import exception.test;

public class JsonTest {
    @Test
    public void testJson() throws Exception {
        M m = new M();
        BigDecimal dd = new BigDecimal(0.007);
//        dd=dd.setScale(3, )
        m.setDd(dd);
        m.setId("01");
        String jsonString = JSON.toJSONString(m);
        System.out.println(jsonString);
        
        M parseObject = JSON.parseObject(jsonString, M.class);
        System.out.println(parseObject.getDd());
    }

    public static class M {
        private String id;
        private BigDecimal dd;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public BigDecimal getDd() {
            return dd;
        }

        public void setDd(BigDecimal dd) {
            this.dd = dd;
        }

    }
}
