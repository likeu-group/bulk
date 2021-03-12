package cool.likeu.bulk.repo.po;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Commodity {

	private Long id;

	private String name;

	private String description;

	private Float purchasePrice;

	private Integer stock;

	private String imageUrl;

	private Long brandId;

	private Long categoryId;

	private Date createTime;

	private Date modifyTime;

}
