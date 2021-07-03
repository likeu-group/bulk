package cool.likeu.bulk.repo.dao;

import java.util.List;

import cool.likeu.bulk.repo.po.UserPO;
import cool.likeu.bulk.repo.po.UserPOExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    /**
     * <p>使用用户ID删除用户信息</p>
     *
     * @param userId 用户ID
     * @return result
     */
    int deleteByUserId(Long userId);

    /**
     * <p>插入用户信息，不包括userId</p>
     *
     * @param record user info
     * @return result
     */
    int insert(UserPO record);

    /**
     * <p>可选择列插入用户信息</p>
     *
     * @param record waiting to insert user info
     * @return result
     */
    int insertSelective(UserPO record);

    @Deprecated
    int updateByExampleSelective(@Param("record") UserPO record, @Param("example") UserPOExample example);

    int updateByExample(@Param("record") UserPO record, @Param("example") UserPOExample example);

    int updateByPrimaryKeySelective(UserPO record);

    List<UserPO> selectByExample(UserPOExample example);

    /**
     * <p>通过userId查找对应用户</p>
     *
     * @param userId 用户Id
     * @return UserPO
     */
    UserPO selectByUserId(Long userId);

    /**
     * <p>使用用户名查询用户信息，并携带Role信息</p>
     *
     * @see cool.likeu.bulk.repo.po.RolePO
     * @param username 用户名
     * @return UserPO
     */
    UserPO selectByUsername(String username);

    /**
     * <p>使用邮箱查询用户信息，并携带Role信息</p>
     *
     * @param email 邮箱
     * @return UserPO
     */
    UserPO selectByEmail(String email);
}
