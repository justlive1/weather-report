package com.github.justlive1.demo.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

import com.github.justlive1.demo.weather.conf.ConfigProps;

/**
 * 服务启动入口
 * 
 * @author wubo
 *
 */
@SpringBootApplication(scanBasePackages = { "com.github.justlive1.demo.weather" })
@EnableConfigurationProperties(ConfigProps.class)
@EnableAsync
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}
}
