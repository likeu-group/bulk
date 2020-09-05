package cool.likeu.bulk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cool.likeu.bulk.dao.Commodity;
import org.apache.ibatis.annotations.Mapper;

import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommodityMapper extends BaseMapper<Commodity> {

}
