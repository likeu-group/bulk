package cool.likeu.bulk.repo.po;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CategoryPO {

	private Long id;

	private String name;

	private Date createTime;

	private Date modifyTime;

}
