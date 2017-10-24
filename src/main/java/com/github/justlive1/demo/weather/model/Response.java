package com.github.justlive1.demo.weather.model;

import lombok.Data;

/**
 * 请求返回体
 * 
 * @author wubo
 *
 * @param <T>
 */
@Data
public class Response {

	/**
	 * 状态码
	 */
	private Integer status;
	/**
	 * 描述
	 */
	private String desc;
	/**
	 * 返回体
	 */
	private WeatherReport data;

	public boolean success() {
		return status != null && status == 1000;
	}
}
