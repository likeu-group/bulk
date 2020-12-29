package cool.likeu.bulk.mapper;

import cool.likeu.bulk.dao.Commodity;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommodityMapperTest {

	@Autowired
	private CommodityMapper commodityMapper;

	@Test
	void testInsertCommodity() {
		Commodity commodity = new Commodity();
		commodity.setName("公牛开关插座 五孔插座 GN123");
		commodity.setDescription("公牛开关插座，五孔插座，纯铜片接触片");
		commodity.setPurchasePrice(13.0F);
		commodity.setStock(100);
		commodity.setImageUrl("http://localhost:8080/bulk/images/gn-123.png");
		commodity.setBrandId(1L);
		commodity.setCategoryId(1L);

		int execCount = commodityMapper.insert(commodity);
		assertTrue(execCount > 0);
	}

}
