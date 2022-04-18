package pro.sys.log.bean;

import lb.dao.annotation.bean.Table;
import lb.dao.annotation.field.Column;
import lb.dao.annotation.field.EntityVar;
import pro.common.BaseBean;

/**
 * @author 李斌
 */
@Table("XTGL_LOG")
public class XtglLog extends BaseBean{
    @Column
    @EntityVar("SELECT SEQ_XTGL_LOGBH.NEXTVAL FROM DUAL")
    private String bh;
    @Column
    private String content;

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
