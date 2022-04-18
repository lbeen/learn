package lbeen.sys.log.bean;

import lbeen.common.BaseBean;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.SQL;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author 李斌
 */
@Table("XTGL_LOG")
public class XtglLog extends BaseBean {
    @Column
    @Prev(@SQL("SELECT SEQ_XTGL_LOGBH.NEXTVAL FROM DUAL"))
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
