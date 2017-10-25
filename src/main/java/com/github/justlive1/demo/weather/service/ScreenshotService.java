package com.github.justlive1.demo.weather.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.justlive1.demo.weather.conf.ConfigProps;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.net.UrlEscapers;

/**
 * 快照服务
 * 
 * @author wubo
 *
 */
@Service
public class ScreenshotService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ConfigProps props;

	private Cache<String, BufferedImage> cache;

	@PostConstruct
	void init() {

		cache = CacheBuilder.newBuilder().expireAfterWrite(props.getImageExpire(), TimeUnit.MINUTES).build();

	}

	/**
	 * 根据url进行快照
	 * 
	 * @param url
	 * @return
	 */
	public BufferedImage shot(String url) {

		WebDriver webDriver = null;
		try {
			DesiredCapabilities desiredCapabilities = DesiredCapabilities.phantomjs();
			desiredCapabilities.setJavascriptEnabled(true);
			desiredCapabilities.setBrowserName("phantomjs");
			desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
					props.getPhantomJsPath());
			webDriver = new PhantomJSDriver(desiredCapabilities);
			webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			webDriver.get(url);
			WebDriver augmentedDriver = new Augmenter().augment(webDriver);
			File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);

			return ImageIO.read(screenshot);

		} catch (IOException e) {
			logger.error("", e);
		} finally {
			if (webDriver != null) {
				webDriver.quit();
			}
		}

		return null;
	}

	/**
	 * 根据城市名称获取快照
	 * 
	 * @param cityName
	 * @return
	 */
	public BufferedImage shotWeather(String cityName) {

		String url = props.getProjectUrl() + UrlEscapers.urlPathSegmentEscaper().escape(cityName);

		BufferedImage image = cache.getIfPresent(url);
		if (image != null) {
			return image;
		}

		image = this.shot(url);
		if (image != null) {
			cache.put(url, image);
		}

		return image;
	}
}
