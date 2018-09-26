package cn.gleme.weixin.message.templete;

public class UpdateState {
	private Text first;
	private Text orderSn;//门店名
	private Text orderStatus;//下单时间
	private Text remark;
	public Text getFirst() {
		return first;
	}
	public void setFirst(Text first) {
		this.first = first;
	}
	public Text getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(Text orderSn) {
		this.orderSn = orderSn;
	}
	public Text getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Text orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Text getRemark() {
		return remark;
	}
	public void setRemark(Text remark) {
		this.remark = remark;
	}
}
