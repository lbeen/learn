package pro.sys.login.bean;

import lb.dao.annotation.bean.Table;
import lb.dao.annotation.field.Column;
import pro.common.BaseBean;

/**
 * @author 李斌
 */
@Table("XTGL_USERINFO")
public class UserInfo extends BaseBean{
    @Column
    private String xm;
    @Column
    private String yhm;
    @Column
    private String mm;

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getYhm() {
        return yhm;
    }

    public void setYhm(String yhm) {
        this.yhm = yhm;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }
}
