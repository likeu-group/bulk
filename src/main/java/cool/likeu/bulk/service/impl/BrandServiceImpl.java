package cool.likeu.bulk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cool.likeu.bulk.dao.Brand;
import cool.likeu.bulk.mapper.BrandMapper;
import cool.likeu.bulk.service.BrandService;

import org.springframework.stereotype.Service;

@Service("brandServiceImpl")
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

}
