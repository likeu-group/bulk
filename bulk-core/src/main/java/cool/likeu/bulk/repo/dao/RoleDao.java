package cool.likeu.bulk.repo.dao;

import cool.likeu.bulk.repo.po.RolePO;
import cool.likeu.bulk.repo.po.RolePOExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleDao {

    long countByExample(RolePOExample example);

    int deleteByExample(RolePOExample example);

    int deleteByPrimaryKey(Long roleId);

    int insert(RolePO record);

    int insertSelective(RolePO record);

    List<RolePO> selectByExample(RolePOExample example);

    RolePO selectByPrimaryKey(Long roleId);

    int updateByExampleSelective(@Param("record") RolePO record, @Param("example") RolePOExample example);

    int updateByExample(@Param("record") RolePO record, @Param("example") RolePOExample example);

    int updateByPrimaryKeySelective(RolePO record);

    int updateByPrimaryKey(RolePO record);

    /**
     * <p>通过{@code roleId}查找该用户所拥有的角色集合</p>
     *
     * @param userId 用户ID
     * @return RolePO Collections
     */
    List<RolePO> selectRolesByUserId(Long userId);

    /**
     * <p>为指定用户新增权限</p>
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return result
     */
    int insertRoleByUserId(@Param("userId") Long userId, @Param("roleId") Long roleId);
}
