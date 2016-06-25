import java.math.BigDecimal;

import org.junit.Test;

public class BigDecimalTest {
    @Test
    public void testBigDecimal() throws Exception {
        //ROUND_HALF_UP 四舍五入
        BigDecimal b1 = new BigDecimal(0.15000005);
        System.out.println(b1);
        BigDecimal b2 = b1.setScale(1,BigDecimal.ROUND_CEILING);
        System.out.println(b2);
        BigDecimal b3 =b1.setScale(1,BigDecimal.ROUND_DOWN);
        System.out.println(b3);
        BigDecimal b4 =b1.setScale(1,BigDecimal.ROUND_FLOOR);
        System.out.println(b4);
        BigDecimal b5 =b1.setScale(1,BigDecimal.ROUND_HALF_DOWN);
        System.out.println(b5);
        BigDecimal b6 =b1.setScale(1,BigDecimal.ROUND_HALF_EVEN);
        System.out.println(b6);
        BigDecimal b7 = b1.setScale(1,BigDecimal.ROUND_HALF_UP);
        System.out.println(b7);
        
        b1 = new BigDecimal(-0.15000005);
        BigDecimal b8 = b1.setScale(1,BigDecimal.ROUND_HALF_UP);
        System.out.println(b8);
    }
}
