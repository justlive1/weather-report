package com.github.justlive1.demo.weather.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * 天气
 * 
 * @author wubo
 *
 */
@Data
public class Weather {

	/**
	 * 日期
	 */
	private String date;
	/**
	 * 最高温度
	 */
	private String high;
	/**
	 * 最低温度
	 */
	private String low;
	/**
	 * 风力
	 */
	@SerializedName(value = "fl", alternate = { "fengli" })
	private String fl;
	/**
	 * 风向
	 */
	@SerializedName(value = "fx", alternate = { "fengxiang" })
	private String fx;
	/**
	 * 天气类型
	 */
	private String type;

}
