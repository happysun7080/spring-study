package gwshin.typeconverter;

import gwshin.typeconverter.converter.IntegerToStringConverter;
import gwshin.typeconverter.converter.IpPortToStringConverter;
import gwshin.typeconverter.converter.StringToIntegerConverter;
import gwshin.typeconverter.converter.StringToIpPortConverter;
import gwshin.typeconverter.formatter.MyNumberFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 주석처리 우선순위
//        registry.addConverter(new StringToIntegerConverter());
//        registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new IpPortToStringConverter());
        registry.addConverter(new StringToIpPortConverter());

        // 추가
        registry.addFormatter(new MyNumberFormatter());
    }
}
