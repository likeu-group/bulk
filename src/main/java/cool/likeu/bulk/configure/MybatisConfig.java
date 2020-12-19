package cool.likeu.bulk.configure;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"cool.likeu.bulk.mapper"})
public class MybatisConfig {

}
