package cool.likeu.bulk.repo.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>bulk_role数据库持久化对象</p>
 *
 * @see cool.likeu.bulk.repo.po.UserPO
 * @author XiaoYu
 */
@Data
@Accessors(chain = true)
public class RolePO implements Serializable {

    private static final long serialVersionUID = 878437636732956716L;

    private Long roleId;

    private String roleName;

    private String roleDesc;

    private Date createTime;

    private Date modifyTime;

}