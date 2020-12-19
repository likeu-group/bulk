package cool.likeu.bulk.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cool.likeu.bulk.dao.Brand;
import cool.likeu.bulk.service.BrandService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BrandServiceImplTest {

	@Autowired
	private BrandService brandService;

	@Test
	void testRunBrandService() {
		Brand saveBrand = new Brand();
		saveBrand.setName("公牛");
		boolean save = brandService.save(saveBrand);
		assertTrue(save);

		Brand brand = brandService.getById(1);
		assertNotNull(brand);

		final String queryName = "公牛";
		brandService.list(
				Wrappers.<Brand>lambdaQuery()
						.eq(Brand::getName, queryName))
				.forEach(System.out::println);
	}

	@Test
	void testPageSelectBrandService() {
		IPage<Brand> page = new Page<>();
		IPage<Brand> selectedPage = brandService.page(page);
		assertNotNull(selectedPage);
	}
}