package gwshin.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "gwshin.core",   // default: 현재 ComponentScan이 붙은 클래스의 위치
        basePackageClasses = AutoAppConfig.class,

        // AppConfig 유지하기 위함
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {


}
