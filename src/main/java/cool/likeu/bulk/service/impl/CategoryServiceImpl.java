package cool.likeu.bulk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cool.likeu.bulk.dao.Category;
import cool.likeu.bulk.mapper.CategoryMapper;
import cool.likeu.bulk.service.CategoryService;

import org.springframework.stereotype.Service;

@Service("categoryServiceImpl")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
