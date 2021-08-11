package cool.likeu.bulk.service;

import lombok.Data;

/**
 * <p>Menu Service</p>
 *
 * @see cool.likeu.bulk.repo.po.MenuPO
 * @since 1.0
 * @author XiaoYu
 */
public interface MenuService {

	/**
	 * TODO 渲染好层级后返回
	 *
	 * @param roleId
	 */
	void lookupMenuByRoleId(Long roleId);

	/**
	 * <p>AntDesignPro DynamicRouter格式：</p>
	 * <pre>
	 * {@code
	 *   {
	 *     "name": "dashboard", // 路由名称，建议唯一
	 *     "parentId": 0,
	 *     "id": 1,
	 *     "meta": {
	 *        "icon": "dashboard",
	 *        "title": "仪表盘",
	 *        "show": true
	 *     },
	 *     "component": "RouteView", // 该路由对应页面的 组件
	 *     "redirect": "/dashboard/workplace"
	 *    }
	 * }
	 * </pre>
	 * <p>需要在MenuService中分好层级将数据返回给前端，避免前端进行菜单层级控制</p>
	 */
	@Data
	class MenuDTO {

		private final static Long DEFAULT_TOP_MENU = 0L;

		/**
		 * 菜单ID
		 */
		private Long menuId;

		/**
		 * 父级菜单ID，0为顶级菜单
		 */
		private Long parentId = DEFAULT_TOP_MENU;

		/**
		 * 路由名称，建议唯一
		 */
		private String menuName;

		/**
		 *
		 */
		private String menuUrl;

		/**
		 * 菜单类型
		 */
		private Integer menuType;

		/**
		 * 菜单排序
		 */
		private Integer menuOrder;

		/**
		 * 菜单状态
		 */
		private Integer menuStatus;

		/**
		 * 菜单权限
		 */
		private String menuPermissions;

		/**
		 * 元数据
		 */
		private Meta metadata;

		@Data
		static class Meta {
			private String icon;
			private String title;
			private String target;
			private boolean show;
			private boolean hideHeader;
			private boolean hideChildren;
			private boolean hiddenHeaderContent;
		}
	}

	class CascadeMenu {

	}
}
