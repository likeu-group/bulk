package cool.likeu.bulk.repo.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>bulk_menu数据库持久化对象</p>
 * TODO declared menu_perms
 * @since 1.0
 * @author XiaoYu
 */
@Data
@Accessors(chain = true)
public class MenuPO implements Serializable, Comparable<MenuPO> {

    private static final long serialVersionUID = -7709066103873208060L;

    private Long menuId;

    private Long parenId;

    private String menuIcon;

    private String menuName;

    private String menuUrl;

    private Integer menuType;

    private Integer menuOrder;

    private Integer menuStatus;

    private String menuMethod;

    private Date createTime;

    private Date modifyTime;

    @Override
    public int compareTo(MenuPO o) {
        return Math.min(o.getMenuOrder(), this.getMenuOrder());
    }
}
