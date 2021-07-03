package cool.likeu.bulk.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cool.likeu.bulk.repo.po.RolePO;
import cool.likeu.bulk.repo.po.UserPO;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserInformation {

	private Long userId;

	private String username;

	private Integer sex;

	private String email;

	private String phone;

	private String avatar;

	private String address;

	private List<String> roles;

	private Date createTime;

	public static UserInformation convert(UserPO userPO) {
		return new UserInformation()
				.setUserId(userPO.getUserId())
				.setUsername(userPO.getUsername())
				.setSex(userPO.getSex())
				.setEmail(userPO.getEmail())
				.setPhone(userPO.getPhone())
				.setAvatar(userPO.getAvatar())
				.setAddress(userPO.getAddress())
				.setRoles(userPO.getRoles().stream().map(RolePO::getRoleName).collect(Collectors.toList()))
				.setCreateTime(userPO.getCreateTime());
	}

}
