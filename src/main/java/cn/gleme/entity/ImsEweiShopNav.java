package cn.gleme.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @description 导航图标
 * 
 * @author lhq
 * @date 2018-9-23
 *
 */
@Entity
@Table(name = "ims_ewei_shop_nav")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "ims_ewei_shop_nav")
public class ImsEweiShopNav extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long uniacid;

	private String navname;

	private String icon;

	private String url;

	private Long displayorder;

	private Integer status;

	private Integer iswxapp;

	public Long getUniacid() {
		return uniacid;
	}

	public void setUniacid(Long uniacid) {
		this.uniacid = uniacid;
	}

	public String getNavname() {
		return navname;
	}

	public void setNavname(String navname) {
		this.navname = navname;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getDisplayorder() {
		return displayorder;
	}

	public void setDisplayorder(Long displayorder) {
		this.displayorder = displayorder;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIswxapp() {
		return iswxapp;
	}

	public void setIswxapp(Integer iswxapp) {
		this.iswxapp = iswxapp;
	}

}
