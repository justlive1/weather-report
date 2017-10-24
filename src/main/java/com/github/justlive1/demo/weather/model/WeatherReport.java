package com.github.justlive1.demo.weather.model;

import java.util.List;

import lombok.Data;

/**
 * 天气报告
 * 
 * @author wubo
 *
 */
@Data
public class WeatherReport {

	/**
	 * 昨日天气
	 */
	private Weather yesterday;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 空气指数
	 */
	private String api;
	/**
	 * 感冒
	 */
	private String ganmao;
	/**
	 * 温度
	 */
	private String wendu;
	/**
	 * 七日预报
	 */
	private List<Weather> forecast;
}
