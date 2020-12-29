package cool.likeu.bulk.mapper;

import cool.likeu.bulk.dao.Category;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryMapperTest {

	@Autowired
	private CategoryMapper categoryMapper;

	@Test
	void testInsertCategory() {
		Category category = new Category();
		category.setName("电器");

		int execCount = categoryMapper.insert(category);
		assertTrue(execCount > 0);
	}

}
