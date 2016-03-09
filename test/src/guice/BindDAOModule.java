package guice;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * guice ioc 注入
 * @author ljq
 */
public class BindDAOModule implements Module {

    public void configure(Binder binder) {
        // 默认绑定是每次都new的，不是单例
        // 单例绑定 binder.bind(UserInfoDAO.class).to(UserInfoDAOImpl.class).in(Scopes.SINGLETON)
        // 一个接口不能绑定多个实现类
        // 可以binder.bind(UserInfoDAO.class).to(new UserInfoDAOImpl()); 绑定自己的实现
        // 可以在接口上使用@ImplementedBy(UserInfoDAO.class) 如果同时存在model,优先： 手动大于注解
        binder.bind(UserInfoDAO.class).to(UserInfoDAOImpl.class);
    }

}
