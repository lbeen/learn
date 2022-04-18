package pro.common;

import lb.dao.annotation.field.Column;
import lb.dao.annotation.field.EntityVar;
import lb.dao.annotation.field.Id;
import lb.mvc.Mvcs;
import lb.util.Lang;
import lb.util.SessionUser;

import java.util.Date;

/**
 * @author 李斌
 */
public abstract class BaseBean {
    @Id
    private String id;
    @Column
    private String jlzt;
    @Column
    private String cjr;
    @Column
    @EntityVar("SELECT SYSDATE FROM DUAL")
    private Date cjsj;

    public void setBaseInfo() {
        this.id = Lang.UUID();
        this.jlzt = "1";
        SessionUser userInfo = Mvcs.getUserInfo();
        if (userInfo != null) {
            this.cjr = userInfo.getXm();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJlzt() {
        return jlzt;
    }

    public void setJlzt(String jlzt) {
        this.jlzt = jlzt;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }
}
