package cn.keeponline.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 认证授权中心
 * 
 * @author capital
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GeneratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
        System.out.println("capital-auth认证授权中心启动成功");
    }
}
