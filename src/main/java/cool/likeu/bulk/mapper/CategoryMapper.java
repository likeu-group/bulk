package cool.likeu.bulk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cool.likeu.bulk.dao.Category;
import org.apache.ibatis.annotations.Mapper;

import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

}
