package cool.likeu.bulk.dao;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("bulk_brand")
public class Brand implements Serializable {

	@TableId(value = "brand_id", type = IdType.AUTO)
	private Long id;

	@TableField("brand_name")
	private String name;

	@TableField("create_time")
	private Date createTime;

	@TableField("modify_time")
	private Date modifyTime;

}
