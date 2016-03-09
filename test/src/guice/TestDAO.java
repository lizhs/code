package guice;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * http://iluoxuan.iteye.com/blog/2070622
 * 
 * @author lizhs
 * @date   2016年3月5日
 */
public class TestDAO {

    @Test
    public void testUserInfo() {
        //
        Injector injector=Guice.createInjector(new BindDAOModule());
        System.out.println("创建Injector");
        UserInfoDAO userInfo=injector.getInstance(UserInfoDAO.class);
        System.out.println("创建-userInfo");
        UserInfoDAO userInfo2=injector.getInstance(UserInfoDAO.class);
        Assert.assertEquals(userInfo.hashCode(), userInfo2.hashCode());
    }
}
