package guice;

import com.google.inject.Singleton;

@Singleton
public class UserInfoDAOImpl implements UserInfoDAO {

    public UserInfoDAOImpl(){
        System.out.println("创建对象。。。UserInfoDAOImpl");
    }
    public int update(int uid, String name) {
        System.out.println(" update name is success! ");
        return 1;
    }

}
