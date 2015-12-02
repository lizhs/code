package cn.sunline.ltts.core.plugin.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.alibaba.fastjson.annotation.JSONType;

@XmlRootElement(name = "plugin")
@JSONType(ignores={"activatorObj"})
@XmlType(propOrder={"id","displayName","activator","order","enable","failOnInitError","extensionPoints","extensions"})
//插件配置
public class PluginConf implements IOrder {
    //插件ID
    private String id = "";
    //插件中文名称
    private String displayName = "";
    //插件Activator
    private String activator = "";
  //插件加载顺序
    private int order = 0;
    //插件是否启动
    private boolean enable = true;
    //失败是否终止
    private boolean failOnInitError = true;
   
//    private AbstractPlugin activatorObj;

    private List<ExtensionConf> extensions = new ArrayList<ExtensionConf>();
    private List<ExtensionPointConf> extensionPoints = new ArrayList<ExtensionPointConf>();
    
//    private String fileName;
//    
//    @XmlTransient
//    public String getFileName() {
//        return fileName;
//    }
//
//    public void setFileName(String fileName) {
//        this.fileName = fileName;
//    }

//    @XmlTransient
//    public AbstractPlugin getActivatorObj() {
//        return activatorObj;
//    }
//
//    public void setActivatorObj(AbstractPlugin activatorObj) {
//        this.activatorObj = activatorObj;
//    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @XmlAttribute
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @XmlAttribute
    public boolean isFailOnInitError() {
        return failOnInitError;
    }

    public void setFailOnInitError(boolean failOnInitError) {
        this.failOnInitError = failOnInitError;
    }

    @XmlAttribute
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @XmlAttribute(name = "activator")
    public String getActivator() {
        return activator;
    }

    public void setActivator(String activator) {
        this.activator = activator;
    }

    @XmlElementWrapper(name = "extension-points")
    @XmlElement(name = "point")
    public List<ExtensionPointConf> getExtensionPoints() {
        return extensionPoints;
    }

    public void setExtensionPoints(List<ExtensionPointConf> extensionPoints) {
        this.extensionPoints = extensionPoints;
    }

    @XmlElementWrapper(name = "extensions")
    @XmlElement(name = "extension")
    public List<ExtensionConf> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<ExtensionConf> extensions) {
        this.extensions = extensions;
    }

}
