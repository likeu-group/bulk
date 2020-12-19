package cool.likeu.bulk.dao;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Commodity implements Serializable {

	@TableId(value = "commodity_id")
	private Long id;

	@TableField("commodity_name")
	private String name;

	@TableField("commodity_desc")
	private String description;

	@TableField("purchase_price")
	private Float purchasePrice;

	@TableField("stock")
	private Integer stock;

	@TableField("image_url")
	private String imageUrl;

	@TableField("branch_id")
	private Long brandId;

	@TableField("category_id")
	private Long categoryId;

	@TableField("create_time")
	private Date createTime;

	@TableField("modify_time")
	private Date modifyTime;

}
