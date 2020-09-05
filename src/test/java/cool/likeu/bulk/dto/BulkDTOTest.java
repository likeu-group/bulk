package cool.likeu.bulk.dto;

import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cool.likeu.bulk.dao.Brand;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
class BulkDTOTest {

	private Brand brand = new Brand();

	@Autowired
	private ObjectMapper serializer = new ObjectMapper();

	{
		brand.setId(15L);
		brand.setName("测试Bean");
		brand.setCreateTime(new Date());
		brand.setModifyTime(new Date());
	}

	@Test
	void testCreateSuccess() throws JsonProcessingException {
		brand.setName("测试Bean_1");
		BulkDTO<Brand> testBrand1 = BulkDTO.success(brand);
		System.out.println(serializer.writeValueAsString(testBrand1));

		brand.setName("测试Bean_2");
		BulkDTO<Brand> testBrand2 = BulkDTO.success(0, brand);
		System.out.println(serializer.writeValueAsString(testBrand2));

		brand.setName("测试Bean_3");
		BulkDTO<Brand> testBrand3 = BulkDTO.success(HttpStatus.OK, brand);
		System.out.println(serializer.writeValueAsString(testBrand3));
	}

	@Test
	void testCreateFailure() throws JsonProcessingException {
		BulkDTO<Brand> testBrand1 = BulkDTO.failure("failure message 1");
		System.out.println(serializer.writeValueAsString(testBrand1));

		BulkDTO<Brand> testBrand2 = BulkDTO.failure(500, "failure message 2");
		System.out.println(serializer.writeValueAsString(testBrand2));

		BulkDTO<Brand> testBrand3 = BulkDTO.failure(HttpStatus.BAD_GATEWAY, "failure message 3");
		System.out.println(serializer.writeValueAsString(testBrand3));
	}

}