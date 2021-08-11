package cool.likeu.bulk.repo.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author XiaoYu
 * @since 2021/7/24 17:39
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class PermissionPO implements Serializable {

	private static final long serialVersionUID = 8471812174034962699L;

	private Long permsId;

	private String permsName;

	private String permsEntity;

	private String permsDescribe;

	private Date createTime;

	private Date modifyTime;

}
