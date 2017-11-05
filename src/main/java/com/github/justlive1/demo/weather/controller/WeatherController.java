package com.github.justlive1.demo.weather.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.justlive1.demo.weather.conf.ConfigProps;
import com.github.justlive1.demo.weather.model.WeatherReport;
import com.github.justlive1.demo.weather.service.ScreenshotService;
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
	private ScreenshotService screenshotService;

	@Autowired
	private IpSearch ipSearch;

	@RequestMapping
	public String index(String cityId, String cityName, Model model, HttpServletRequest request) {

		WeatherReport weather = null;
		if (StringUtils.hasText(cityId)) {
			weather = weatherService.getWeatherByCityId(cityId);
		} else if (StringUtils.hasText(cityName)) {
			weather = weatherService.getWeatherByCityName(cityName);
		} else {
			String ip = WebUtils.getIpAddr(request);
			if (StringUtils.hasText(ip)) {
				cityName = ipSearch.findCity(ip);
				if (StringUtils.hasText(cityName)) {
					weather = weatherService.getWeatherByCityName(cityName);
					// ip解析时进行后台快照
					screenshotService.asyncShot(cityName);
				}
			}
		}

		if (weather == null) {
			weather = weatherService.getWeatherByCityId(props.getCityId());
		}

		model.addAttribute("weather", weather);

		return "index.ftl";
	}

	@RequestMapping("image")
	public void image(String cityName, HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (!StringUtils.hasText(cityName)) {

			String ip = WebUtils.getIpAddr(request);
			if (StringUtils.hasText(ip)) {
				cityName = ipSearch.findCity(ip);
			}
		}

		BufferedImage image = screenshotService.shotWeather(cityName);
		if (image != null) {
			response.setContentType("image/png");
			OutputStream os = response.getOutputStream();
			ImageIO.write(image, "png", os);
			os.close();
		}
	}
}
