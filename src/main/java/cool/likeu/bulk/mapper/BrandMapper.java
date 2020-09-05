package cool.likeu.bulk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cool.likeu.bulk.dao.Brand;
import org.apache.ibatis.annotations.Mapper;

import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BrandMapper extends BaseMapper<Brand> {

}
