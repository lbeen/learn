package pro.sys.module.bean;

import lb.dao.annotation.bean.Table;
import lb.dao.annotation.field.Column;
import pro.common.BaseBean;

/**
 * @author 李斌
 */
@Table("SYS_MODULE")
public class Module extends BaseBean{
    @Column
    private String name;
    @Column
    private String url;
    @Column
    private String sjbh;
    @Column
    private String xh;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSjbh() {
        return sjbh;
    }

    public void setSjbh(String sjbh) {
        this.sjbh = sjbh;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

}
