package com.tos.pojo;

/**
 * app实例表
 * Created by hasaa on 2016/3/14.
 */
public class App_instance {

    private String id;
    private String install_time; //	创建时间
    private String source;//来源
    private String pad; //安装在哪个pad上

    public App_instance(){};

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstall_time() {
        return install_time;
    }

    public void setInstall_time(String install_time) {
        this.install_time = install_time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPad() {
        return pad;
    }

    public void setPad(String pad) {
        this.pad = pad;
    }

    @Override
    public String toString() {
        return "App_instance{" +
                "id='" + id + '\'' +
                ", install_time='" + install_time + '\'' +
                ", source='" + source + '\'' +
                ", pad='" + pad + '\'' +
                '}';
    }
}
