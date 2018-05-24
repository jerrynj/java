package cn.wizzer.modules.models.llfx;

import cn.wizzer.common.base.Model;
import cn.wizzer.modules.models.cms.Cms_channel;
import org.nutz.dao.DB;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Wizzer on 2016/7/18.
 */
@Table("t_user")
public class Llfx_user extends Model implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column
    @Id
    @Comment("ID")
    private int id;

    @Name
    @Column
    @Comment("名称")
    private String shopid;

    @Column
    @Comment("密码")
    private String passwd;

    @Column("ct")
    @Comment("创建时间")
    private Date createTime;

    @Column
    @Comment("修改时间")
    private Date updateTime;

    @Column
    @Comment("Salt")
    private String salt;

    @Column
    @Comment("排序字段")
    @Prev({
            @SQL(db= DB.MYSQL,value = "SELECT IFNULL(MAX(location),0)+1 FROM t_user"),
            @SQL(db= DB.ORACLE,value = "SELECT COALESCE(MAX(location),0)+1 FROM t_user")
    })
    private Integer location;

    @Column
    @ColDefine(type = ColType.VARCHAR, width = 32)
    private String channelId;

}
