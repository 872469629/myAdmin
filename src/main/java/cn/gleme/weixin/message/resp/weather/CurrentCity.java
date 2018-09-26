package cn.gleme.weixin.message.resp.weather;


public class CurrentCity {
	private String currentCity;
	private WeatherData weather_data;
	public String getCurrentCity() {
		return currentCity;
	}
	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}
	public WeatherData getWeather_data() {
		return weather_data;
	}
	public void setWeather_data(WeatherData weather_data) {
		this.weather_data = weather_data;
	}
}
