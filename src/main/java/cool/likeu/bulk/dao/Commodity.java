package cool.likeu.bulk.dao;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("bulk_commodity")
public class Commodity implements Serializable {

	@TableId(value = "commodity_id", type = IdType.AUTO)
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
