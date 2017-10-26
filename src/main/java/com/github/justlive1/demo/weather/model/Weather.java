package com.github.justlive1.demo.weather.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	private String icon;

	public String getFlDecode() {
		if (this.fl != null) {
			Matcher matcher = Pattern.compile("\\<\\!\\[CDATA\\[(?<text>[^\\]]*)\\]\\]\\>").matcher(fl);
			if (matcher.matches()) {
				return matcher.group(1);
			}
		}
		return fl;
	}
}
