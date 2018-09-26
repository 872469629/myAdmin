package cn.gleme.weixin.message.templete;

public class NewTeacherOrder {
	private Text first;
	private Text Day;
	private Text orderId;
	private Text orderType;
	private Text customerName;
	private Text customerPhone;
	private Text remark;
	public Text getFirst() {
		return first;
	}
	public void setFirst(Text first) {
		this.first = first;
	}
	public Text getDay() {
		return Day;
	}
	public void setDay(Text day) {
		Day = day;
	}
	public Text getOrderId() {
		return orderId;
	}
	public void setOrderId(Text orderId) {
		this.orderId = orderId;
	}
	public Text getOrderType() {
		return orderType;
	}
	public void setOrderType(Text orderType) {
		this.orderType = orderType;
	}
	public Text getCustomerName() {
		return customerName;
	}
	public void setCustomerName(Text customerName) {
		this.customerName = customerName;
	}
	public Text getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(Text customerPhone) {
		this.customerPhone = customerPhone;
	}
	public Text getRemark() {
		return remark;
	}
	public void setRemark(Text remark) {
		this.remark = remark;
	}
}
