package com.github.justlive1.demo.weather.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.justlive1.demo.weather.conf.ConfigProps;
import com.github.justlive1.demo.weather.model.WeatherReport;
import com.github.justlive1.demo.weather.service.WeatherService;
import com.github.justlive1.demo.weather.util.IpSearch;
import com.github.justlive1.demo.weather.util.WebUtils;

/**
 * 天气
 * 
 * @author wubo
 *
 */
@Controller
@RequestMapping("/")
public class WeatherController {

	@Autowired
	private ConfigProps props;

	@Autowired
	private WeatherService weatherService;

	@Autowired
	private IpSearch ipSearch;

	@RequestMapping
	public String index(String cityId, Model model, HttpServletRequest request) {

		WeatherReport weather = null;
		if (StringUtils.hasText(cityId)) {
			weather = weatherService.getWeatherByCityId(cityId);
		} else {
			String ip = WebUtils.getIpAddr(request);
			if (StringUtils.hasText(ip)) {
				String cityName = ipSearch.findCity(ip);
				if (StringUtils.hasText(cityName)) {
					weather = weatherService.getWeatherByCityName(cityName);
				}
			}
		}

		if (weather == null) {
			weather = weatherService.getWeatherByCityId(props.getCityId());
		}

		model.addAttribute("weather", weather);

		return "index.ftl";
	}
}
