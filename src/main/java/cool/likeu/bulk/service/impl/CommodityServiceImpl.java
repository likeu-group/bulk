package cool.likeu.bulk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cool.likeu.bulk.dao.Commodity;
import cool.likeu.bulk.mapper.CommodityMapper;
import cool.likeu.bulk.service.CommodityService;

import org.springframework.stereotype.Service;

@Service("commodityServiceImpl")
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {

}
