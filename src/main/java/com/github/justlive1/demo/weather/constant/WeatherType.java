package com.github.justlive1.demo.weather.constant;

public enum WeatherType {

	SUN("1", "晴"), CLOUDY("3", "多云");

	private String type;
	private String desc;

	private WeatherType(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public static String findTypeByDesc(String desc) {
		for (WeatherType weather : values()) {
			if (weather.desc.equals(desc)) {
				return weather.type;
			}
		}
		return null;
	}
}
