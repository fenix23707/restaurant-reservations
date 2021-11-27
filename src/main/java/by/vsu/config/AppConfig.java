package by.vsu.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@MapperScan("by.vsu.dao")
@ImportResource("classpath:beans.xml")
public class AppConfig {
   /* private ApplicationContext context;

    @Autowired
    public AppConfig(ApplicationContext context) {
        this.context = context;
    }*/
}
