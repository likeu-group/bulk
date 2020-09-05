package cool.likeu.bulk.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.likeu.bulk.mapper.BrandMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}