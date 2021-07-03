package cool.likeu.bulk.repo.dao;

import java.util.List;

import cool.likeu.bulk.repo.po.MenuPO;
import cool.likeu.bulk.repo.po.MenuPOExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @since 1.0
 * @author XiaoYu
 */
@Mapper
public interface MenuDao {

    int deleteByMenuId(Long menuId);

    int insert(MenuPO record);

    int insertSelective(MenuPO record);

    List<MenuPO> selectByExample(MenuPOExample example);

    List<MenuPO> selectByRoleId(Long roleId);

    MenuPO selectByPrimaryKey(Long menuId);

    int updateByExampleSelective(@Param("record") MenuPO record, @Param("example") MenuPOExample example);

    int updateByExample(@Param("record") MenuPO record, @Param("example") MenuPOExample example);

    int updateByPrimaryKeySelective(MenuPO record);

}