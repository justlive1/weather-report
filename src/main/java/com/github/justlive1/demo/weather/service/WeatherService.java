package com.github.justlive1.demo.weather.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.github.justlive1.demo.weather.conf.ConfigProps;
import com.github.justlive1.demo.weather.model.Response;
import com.github.justlive1.demo.weather.model.WeatherReport;
import com.google.gson.Gson;

/**
 * 天气服务
 * 
 * @author wubo
 *
 */
@Service
public class WeatherService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	RestTemplate template;
	@Autowired
	ConfigProps config;

	Gson gson = new Gson();

	/**
	 * 根据城市id获取天气信息
	 * 
	 * @param cityId
	 * @return
	 */
	public WeatherReport getWeatherByCityId(String cityId) {

		ResponseEntity<String> entity = template.getForEntity(config.getWeatherApiUrl(), String.class, cityId);

		if (entity.getStatusCode() == HttpStatus.OK) {

			Response resp = gson.fromJson(entity.getBody(), Response.class);

			if (resp.success()) {
				return resp.getData();
			}

			if (logger.isDebugEnabled()) {
				logger.debug("接口调用失败,{},{}", resp.getStatus(), resp.getDesc());
			}
		}
		return null;
	}

}
