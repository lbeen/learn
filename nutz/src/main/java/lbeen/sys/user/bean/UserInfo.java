package lbeen.sys.user.bean;

import lbeen.common.BaseBean;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author 李斌
 */
@Table("XTGL_USERINFO")
public class UserInfo extends BaseBean {
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
