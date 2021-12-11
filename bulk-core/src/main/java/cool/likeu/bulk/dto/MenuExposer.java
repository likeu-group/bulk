package cool.likeu.bulk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

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
 *
 * @author XiaoYu
 * @since 2021/8/17 10:40
 * @version 1.0.0
 */
@Data
public class MenuExposer {

	@JsonIgnoreProperties
	private final static long DEFAULT_TOP_MENU = 0L;

	/**
	 * 菜单ID
	 */
	private long menuId;

	/**
	 * 父级菜单ID，0为顶级菜单
	 */
	private long parentId;

	/**
	 * 路由名称，建议唯一
	 */
	private String menuName;

	/**
	 * 菜单url
	 */
	private String menuUrl;

	/**
	 * 菜单类型
	 */
	private int menuType;

	/**
	 * 菜单排序号
	 */
	private int menuOrder;

	/**
	 * 菜单状态
	 */
	private int menuStatus;

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
