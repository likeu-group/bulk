package cool.likeu.bulk.repo.po;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>The entity object used to describe the menu data.</p>
 *
 * @since 2021/08/18 20:15
 * @author XiaoYu
 * @version 1.0.0
 */
@Data
@Accessors(chain = true)
public class MenuPO implements Serializable {

    private static final long serialVersionUID = -7709066103873208060L;

    /**
     * menu id
     */
    private Long menuId;

    /**
     * menu parent id
     */
    private Long parentId;

    /**
     * menu icon
     */
    private String menuIcon;

    /**
     * menu name
     */
    private String menuName;

    /**
     * menu url
     */
    private String menuUrl;

    /**
     * 需要与Vue映射的Component
     */
    private String menuComponent;

    /**
     * menu type. 0: menu, 1: button
     */
    private Integer menuType;

    /**
     * menu order
     */
    private Integer menuOrder;

    /**
     * menu state, 0: enable, 1: disable
     */
    private Integer menuStatus;

    /**
     * menu http method
     */
    private String menuMethod;

    /**
     * create menu time
     */
    private Date createTime;

    /**
     * update menu time
     */
    private Date modifyTime;

    /**
     * 建立菜单与权限的关系映射
     */
    private Collection<PermissionPO> permissions;

}
