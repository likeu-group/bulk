package cool.likeu.bulk.repo.po;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * <p>bulk_user数据库持久化对象, 携带了该用户所拥有的角色集合</p>
 *
 * @see cool.likeu.bulk.repo.po.RolePO
 * @author XiaoYu
 */
@Data
@Accessors(chain = true)
/*
 TODO 不使用Mybatis lazyLoading，懒加载会使用JavassistProxyFactory进行Bean的处理
 TODO Mybatis BUG 解决Mybatis级联查询时，使用的MethodHandler造成的Serialization异常
 */
public class UserPO implements Serializable {

	private static final long serialVersionUID = 5096488452871109136L;

	private Long userId;

	private String username;

	private String password;

	private Integer sex;

	private Integer status;

	private String email;

	private String phone;

	private String avatar;

	private String address;

	private Date createTime;

	private Date modifyTime;

	private List<RolePO> roles;

	public Status convertUserStatus() {
		return Status.of(getStatus());
	}

	/**
	 * <p>用户状态描述枚举类</p>
	 *
	 * @author XiaoYu
	 */
	@Getter
	public enum Status {
		NORMAL(0, "用户状态正常"),
		LOCKED(1, "用户已被锁定"),
		FORBIDDEN(2, "已禁止用户访问"),
		INACTIVATION(3, "用户未激活");

		private final static Map<Integer, Status> USER_STATUS_MAP;

		static {
			USER_STATUS_MAP = new HashMap<>(values().length);
			for (Status status : values()) {
				USER_STATUS_MAP.put(status.getStatus(), status);
			}
		}

		private final int status;

		private final String description;

		Status(int status, String description) {
			this.status = status;
			this.description = description;
		}

		public static Status of(int status) {
			return USER_STATUS_MAP.get(status);
		}
	}
}
