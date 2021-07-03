package cool.likeu.bulk.config;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"cool.likeu.bulk.repo.dao"})
public class MybatisConfig {

	@Bean
	public Interceptor pageInterceptor() {
		return null;
	}

}
