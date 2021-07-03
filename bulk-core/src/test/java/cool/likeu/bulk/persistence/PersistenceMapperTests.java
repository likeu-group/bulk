package cool.likeu.bulk.persistence;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import cool.likeu.bulk.repo.dao.RoleDao;
import cool.likeu.bulk.repo.dao.UserDao;
import cool.likeu.bulk.repo.po.RolePO;
import cool.likeu.bulk.repo.po.RolePOExample;
import cool.likeu.bulk.repo.po.UserPO;
import cool.likeu.bulk.repo.po.UserPOExample;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PersistenceMapperTests {

	private final Random globalRandom = new Random();

	private final static int MAN = 1;
	private final static int WOMAN = 0;

	private final static String[][] ROLE_TEMPLATES = {
			{"sys", "管理员角色，拥有最高权限"},
			{"user", "用户角色，拥有用户基础角色"},
			{"wechat", "以后待拓展的微信用户角色"}
	};

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Test
	void testSaveUserMapperStatement() {
		final String username = UUID.randomUUID().toString().replaceAll("-", "");
		UserPO userPO = new UserPO()
				.setUsername(username)
				.setPassword(DigestUtils.md5DigestAsHex(username.getBytes()))
				.setEmail(String.format("%s@qq.com", username))
				.setSex(globalRandom.nextInt(2));

		int insertResult = userDao.insertSelective(userPO);
		assertEquals(1, insertResult);
	}

	@Test
	void testUpdateUserMapperStatement() {
		UserPOExample whereClause = new UserPOExample();
		whereClause.createCriteria()
				.andUsernameEqualTo("hello_world");

		UserPO newUserPO = new UserPO()
				.setSex(MAN);

		int updateResult = userDao.updateByExampleSelective(newUserPO, whereClause);
		assertEquals(1, updateResult);
	}

	@Test
	void testSelectUserMapperStatement() {
		UserPOExample whereClause = new UserPOExample();
		whereClause.createCriteria()
				.andSexEqualTo(WOMAN);

		List<UserPO> users = userDao.selectByExample(whereClause);
		assertNotNull(users, "User list is null.");

		users.forEach(System.out::println);
	}

	@Test
	void testInsertRoleMapperStatement() {
		String[] roleTemplate = ROLE_TEMPLATES[globalRandom.nextInt(ROLE_TEMPLATES.length)];
		RolePO rolePO = new RolePO()
				.setRoleName(roleTemplate[0])
				.setRoleDesc(roleTemplate[1]);

		int insertResult = roleDao.insertSelective(rolePO);
		assertEquals(1, insertResult);
	}

	@Test
	void testUpdateRoleMapperStatement() {
		RolePOExample whereClause = new RolePOExample();
		whereClause.createCriteria()
				.andRoleNameEqualTo("wechat");

		RolePO newRolePO = new RolePO()
				.setRoleName("wx_user");

		int updateResult = roleDao.updateByExampleSelective(newRolePO, whereClause);
		assertEquals(1, updateResult);
	}

	@Test
	void testDeleteRoleMapperStatement() {
		RolePOExample whereClause = new RolePOExample();
		whereClause.createCriteria()
				.andRoleNameEqualTo("wx_user");

		int deleteResult = roleDao.deleteByExample(whereClause);
		assertEquals(1, deleteResult);
	}

	@Test
	void testSelectRoleMapperStatement() {
		RolePOExample whereClause = new RolePOExample();
		whereClause.createCriteria()
				.andRoleIdBetween(1L, 3L);

		List<RolePO> roles = roleDao.selectByExample(whereClause);
		assertNotNull(roles, "Role list is null.");

		roles.forEach(System.out::println);
	}

	@Test
	void testSelectUserHasRoleMapperStatement() {
		final long userId = 1;
		UserPO userPO = userDao.selectByUserId(userId);
		assertNotNull(userPO, "User is null.");

		List<RolePO> roles = roleDao.selectRolesByUserId(userId);
		userPO.setRoles(roles);

		System.out.println(userPO);
	}

	@Test
	void testInsertUserRoleMappingStatement() {
		UserPOExample userClause = new UserPOExample();
		userClause.createCriteria()
				.andUsernameEqualTo("hello_world");

		List<UserPO> users = userDao.selectByExample(userClause);
		assertNotNull(users, "User list is null.");

		RolePOExample roleClause = new RolePOExample();
		roleClause.createCriteria()
				.andRoleNameEqualTo("sys");
		List<RolePO> roles = roleDao.selectByExample(roleClause);
		assertNotNull(roles, "Role list is null.");

		UserPO userPO = users.stream().findFirst().orElseThrow(NullPointerException::new);
		RolePO rolePO = roles.stream().findFirst().orElseThrow(NullPointerException::new);

		int insertResult = roleDao.insertRoleByUserId(userPO.getUserId(), rolePO.getRoleId());
		assertEquals(1, insertResult);
	}

	@Test
	void testSelectByUsername() {
		final String username = "hello_world";
		UserPO userPO = userDao.selectByUsername(username);
		assertNotNull(userPO);
		assertNotNull(userPO.getRoles(), "Role list is null.");
		System.out.println(userPO);
	}
}
