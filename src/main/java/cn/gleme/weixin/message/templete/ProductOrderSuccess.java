package cn.gleme.weixin.message.templete;

public class ProductOrderSuccess {
	private Text first;
	private Text keyword1;//门店名
	private Text keyword2;//下单时间
	private Text keyword3;//菜品
	private Text remark;
	public Text getFirst() {
		return first;
	}
	public void setFirst(Text first) {
		this.first = first;
	}
	public Text getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(Text keyword1) {
		this.keyword1 = keyword1;
	}
	public Text getKeyword2() {
		return keyword2;
	}
	public void setKeyword2(Text keyword2) {
		this.keyword2 = keyword2;
	}
	public Text getKeyword3() {
		return keyword3;
	}
	public void setKeyword3(Text keyword3) {
		this.keyword3 = keyword3;
	}
	public Text getRemark() {
		return remark;
	}
	public void setRemark(Text remark) {
		this.remark = remark;
	}
}
