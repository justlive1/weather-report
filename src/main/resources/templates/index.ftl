<!DOCTYPE html>
<#assign ctx="${request.getContextPath()}">
<html>
	<head>
		<meta charset="utf-8">
		<title>天气预报</title>
		<link rel="stylesheet" href="${ctx}/webjars/css/style.css" />
	</head>
	<body>
	
		<div class="result-op c-container">

			<div class="op_weather4_twoicon_container_div">

				<div class="op_weather4_twoicon" style="height: 404px;">
				
					<a class="op_weather4_twoicon_day" style="">
						<div class="op_weather4_twoicon_hover"></div>
						<div class="op_weather4_twoicon_split"></div>
						<p class="op_weather4_twoicon_date">
							${weather.yesterday.date!}
						</p>
						<div class="op_weather4_twoicon_icon" style="background-image: url('${ctx}/webjars/img/whiteweather/${weather.yesterday.icon!1}.png');"></div>

						<p class="op_weather4_twoicon_temp">${weather.yesterday.low!}</p>
						<p class="op_weather4_twoicon_temp">${weather.yesterday.high!}</p>
						<p class="op_weather4_twoicon_weath" title="">
							${weather.yesterday.type!}
						</p>
						<p class="op_weather4_twoicon_wind">${weather.yesterday.fx!}	${weather.yesterday.flDecode!}</p>
					</a>
				
					<a class="op_weather4_twoicon_today" style="left:88px;">
						<div class="op_weather4_twoicon_split"></div>
						<p class="op_weather4_twoicon_date">
							${weather.month!}${weather.today.date!}	&nbsp;&nbsp;${weather.city!}
						</p>
						<p class="op_weather4_twoicon_date op_weather4_twoicon_pm25">
							实时空气质量:
							<em style="background:#afdb00"><b>${weather.aqi!}</b>&nbsp;${weather.aqiDesc!}</em>
						</p>
						<div class="op_weather4_twoicon_shishi">
							<div class="op_weather4_twoicon_icon" style="background-image: url('${ctx}/webjars/img/weather/${weather.today.icon!1}.png');"></div>
							<div class="op_weather4_twoicon_shishi_info">
								<span class="op_weather4_twoicon_shishi_title">${weather.wendu!}</span>
								<span class="op_weather4_twoicon_shishi_data">
		                                <i class="op_weather4_twoicon_shishi_sup">℃</i>
		                                <i class="op_weather4_twoicon_shishi_sub">(实时)</i>
		                            </span>
							</div>
						</div>
						<p class="op_weather4_twoicon_temp">${weather.today.low!}</p>
						<p class="op_weather4_twoicon_temp">${weather.today.high!}</p>
						<p class="op_weather4_twoicon_weath" title="">
							${weather.today.type!}
						</p>
						<p class="op_weather4_twoicon_wind">${weather.today.fx!}	${weather.today.flDecode!}</p>
					</a>

					<#list weather.forecast as forecast>  
					<a class="op_weather4_twoicon_day" style="left:${276 + 88 * forecast_index}px">
						<div class="op_weather4_twoicon_hover"></div>
						<div class="op_weather4_twoicon_split"></div>
						<p class="op_weather4_twoicon_date">
							${forecast.date!}
						</p>
						<div class="op_weather4_twoicon_icon" style="background-image: url('${ctx}/webjars/img/whiteweather/${forecast.icon!1}.png');"></div>

						<p class="op_weather4_twoicon_temp">${forecast.low!}</p>
						<p class="op_weather4_twoicon_temp">${forecast.high!}</p>
						<p class="op_weather4_twoicon_weath" title="">
							${forecast.type!}
						</p>
						<p class="op_weather4_twoicon_wind">${forecast.fx!}	${forecast.flDecode!}</p>
					</a>
					</#list>

					<div bg-name="daytime" class="op_weather4_twoicon_bg" style="background-image: -webkit-linear-gradient(top, rgb(13, 104, 188), rgb(114, 173, 224)); height: 404px;">
					</div>
				</div>

			</div>
		</div>
	</body>
</html>
