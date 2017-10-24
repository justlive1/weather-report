package com.github.justlive1.demo.weather.service;

import java.time.LocalDate;

import javax.annotation.PostConstruct;

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
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
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

	RangeMap<Integer, String> ranges = TreeRangeMap.create();

	@PostConstruct
	void init() {
		ranges.put(Range.closed(0, 50), "优");
		ranges.put(Range.closed(51, 100), "良");
		ranges.put(Range.closed(101, 150), "轻度污染");
		ranges.put(Range.closed(151, 200), "中度污染");
		ranges.put(Range.closed(201, 300), "重度污染");
		ranges.put(Range.atLeast(301), "严重污染");
	}

	/**
	 * 根据城市id获取天气信息
	 * 
	 * @param cityId
	 * @return
	 */
	public WeatherReport getWeatherByCityId(String cityId) {

		return this.getWeather(config.getWeatherCityIdUrl(), cityId);
	}

	/**
	 * 根据城市名称获取天气信息
	 * 
	 * @param cityName
	 * @return
	 */
	public WeatherReport getWeatherByCityName(String cityName) {

		return this.getWeather(config.getWeatherCityNameUrl(), cityName);
	}

	private WeatherReport getWeather(String url, String key) {

		ResponseEntity<String> entity = template.getForEntity(url, String.class, key);

		if (entity.getStatusCode() == HttpStatus.OK) {

			Response resp = gson.fromJson(entity.getBody(), Response.class);

			if (resp.success()) {

				WeatherReport report = resp.getData();
				if (report != null && report.getForecast() != null && !report.getForecast().isEmpty()) {
					this.transfer(report);
				}

				return report;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("接口调用失败,{},{}", resp.getStatus(), resp.getDesc());
			}
		}
		return null;
	}

	private void transfer(WeatherReport report) {
		report.setToday(report.getForecast().get(0));
		report.getForecast().remove(0);
		report.setMonth(LocalDate.now().getMonth().getValue() + "月");

		try {
			int aqi = Integer.parseInt(report.getAqi());
			String aqiDesc = ranges.get(aqi);
			report.setAqiDesc(aqiDesc);
		} catch (Exception e) {
		}

		// TODO 天气图标

	}
}
