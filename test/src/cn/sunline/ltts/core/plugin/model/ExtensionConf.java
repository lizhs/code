package cn.sunline.ltts.core.plugin.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(ignores = { "extensionObj" })
//扩展定义
@XmlType(propOrder = { "id", "name", "pointId", "clazzImpl","enable","singleton" })
public class ExtensionConf {
    private String id = "";
    //中文描述
    private String name = "";
    //关联扩展点ID
    private String pointId = "";
    //扩展的实现类
    private String clazzImpl = "";
    //是否启动
    private boolean enable = true;
    //是否单例
    private boolean singleton = false;
    //加载执行的顺序  值越小 越靠前  可以是负数
    //    private int order = 0;
  

    @XmlAttribute
    public boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //    @XmlTransient
    //    public Class<?> getClazzImplType(){
    //       return ClassUtils.classForName(getClazzImpl());
    //    }

    public String getClazzImpl() {
        return clazzImpl;
    }

    public void setClazzImpl(String clazzImpl) {
        this.clazzImpl = clazzImpl;
    }

    public boolean isEnable() {
        return enable;
    }

    @XmlAttribute
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    //    @XmlAttribute
    //    public int getOrder() {
    //        return order;
    //    }
    //
    //    public void setOrder(int order) {
    //        this.order = order;
    //    }

    @XmlAttribute(name = "point")
    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

}
