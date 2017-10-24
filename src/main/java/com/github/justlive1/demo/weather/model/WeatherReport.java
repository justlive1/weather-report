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
	private String aqi;
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

	/* 解析字段 */
	/**
	 * 今日天气
	 */
	private Weather today;
	/**
	 * 当前月份
	 */
	private String month;
	/**
	 * 天气图标
	 */
	private String icon;
	/**
	 * 空气指数描述
	 */
	private String aqiDesc;
}
