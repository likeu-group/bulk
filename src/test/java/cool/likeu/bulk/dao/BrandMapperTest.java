package cool.likeu.bulk.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.likeu.bulk.mapper.BrandMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BrandMapperTest {

	@Autowired
	private BrandMapper brandMapper;

	@Test
	void insertSelective() {

	}

	@Test
	void selectByExample() {
		final String queryName = "公牛";
		LambdaQueryWrapper<Brand> queryWrapper =
				Wrappers.<Brand>lambdaQuery()
						.eq(Brand::getName, queryName);
		brandMapper.selectList(queryWrapper)
				.forEach(System.out::println);
	}

	@Test
	void insertByExample() {
		Brand brand = new Brand();
		brand.setName("公牛");

		int hasInsert = brandMapper.insert(brand);
		assertEquals(hasInsert, 1);
	}

	@Test
	void updateByExample() {
		final String queryName = "公牛";
		Brand newBrand = new Brand();
		newBrand.setName("飞利浦");

		LambdaQueryWrapper<Brand> queryWrapper =
				Wrappers.<Brand>lambdaQuery()
						.eq(Brand::getName, queryName);
		int hasUpdate = brandMapper.update(newBrand, queryWrapper);
		assertEquals(hasUpdate, 1);
	}

	@Test
	void deleteByExample() {

	}
}