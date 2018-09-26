package cn.gleme.weixin.message.resp.weather;


public class CityWeather {
	private String status;
	private String date;
	private CurrentCity results;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public CurrentCity getResults() {
		return results;
	}
	public void setResults(CurrentCity results) {
		this.results = results;
	}
}
