package lbeen.common;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.SQL;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 李斌
 */
public abstract class BaseBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Name
    private String id;
    @Column
    private String jlzt;
    @Column
    private String cjr;
    @Column
    @Prev(@SQL("SELECT SYSDATE FROM DUAL"))
    private Date cjsj;

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
