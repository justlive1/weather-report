package com.github.justlive1.demo.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import com.github.justlive1.demo.weather.conf.ConfigProps;
import com.github.justlive1.demo.weather.service.WeatherService;

/**
 * 服务启动入口
 * 
 * @author wubo
 *
 */
@SpringBootApplication(scanBasePackages = { "com.github.justlive1.demo.weather" })
@EnableConfigurationProperties(ConfigProps.class)
public class Application {

	public static void main(String[] args) {

		ApplicationContext apx = SpringApplication.run(Application.class, args);
		
		WeatherService weatherService = apx.getBean(WeatherService.class);
		weatherService.getWeatherByCityId("101190101");
	}
}
