package com.github.justlive1.demo.weather.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "weather")
@Data
public class ConfigProps {

	private String weatherApiUrl;
}
