package cool.likeu.bulk.service;

import java.util.Collection;

import cool.likeu.bulk.dto.MenuExposer;
import cool.likeu.bulk.repo.po.MenuPO;

/**
 * <p>Menu Service</p>
 *
 * @see cool.likeu.bulk.repo.po.MenuPO
 * @since 1.0.0
 * @author XiaoYu
 */
public interface MenuService {

	/**
	 * TODO 渲染好层级后返回
	 *
	 * @param roleId role id
	 * @return menu exposer
	 */
	Collection<MenuExposer> exposeMenuByCorrespondRole(Long roleId);

	/**
	 * <p>List the relationship between all menus and permissions</p>
	 *
	 * @return the relationship between menus and permissions
	 */
	Collection<MenuPO> listMenus();

}
