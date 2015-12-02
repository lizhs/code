package cn.sunline.ltts.core.plugin.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
//扩展点
@XmlType(propOrder={"id","name","pointType","clazz"})
public class ExtensionPointConf {
    //扩展点ID
    private String id;
  //扩展点中文名称
    private String name;
    //扩展点类别
    private ExtensionPointType pointType;
    //扩展点接口，必须实现接口"cn.sunline.ltts.core.api.IExtension"
    private String clazz;
    

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    @XmlAttribute(name = "allowMultiExtension")
//    public boolean isAllowMultiExtension() {
//        return allowMultiExtension;
//    }
//
//    public void setAllowMultiExtension(boolean allowMultiExtension) {
//        this.allowMultiExtension = allowMultiExtension;
//    }
//
//    public Class<? extends IExtension> getClazzType(){
//        return (Class<? extends IExtension>) ClassUtils.classForName(clazz);
//    }
    @XmlAttribute(name = "pointType")
    public ExtensionPointType getPointType() {
        return pointType;
    }

    public void setPointType(ExtensionPointType pointType) {
        this.pointType = pointType;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
