package cool.likeu.bulk.dao;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Category implements Serializable {

	@TableId(value = "category_id")
	private Long id;

	@TableField("category_name")
	private String name;

	@TableField("create_time")
	private Date createTime;

	@TableField("modify_time")
	private Date modifyTime;

}
