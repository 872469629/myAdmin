package cn.gleme.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @description 幻灯片 
 * 
 * @author lhq
 * @date 2018-9-23 
 *
 */
@Entity
@Table(name = "ims_ewei_shop_adv")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "ims_ewei_shop_adv")
public class ImsEweiShopAdv extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long uniacid;
	
	private String advname;
	
	private String link;
	
	private String thumb;
	
	private Integer displayorder;
	
	private Integer enabled;
	
	private Integer shopid;
	
	private Integer iswxapp;

	public Long getUniacid() {
		return uniacid;
	}

	public void setUniacid(Long uniacid) {
		this.uniacid = uniacid;
	}

	public String getAdvname() {
		return advname;
	}

	public void setAdvname(String advname) {
		this.advname = advname;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public Integer getDisplayorder() {
		return displayorder;
	}

	public void setDisplayorder(Integer displayorder) {
		this.displayorder = displayorder;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getShopid() {
		return shopid;
	}

	public void setShopid(Integer shopid) {
		this.shopid = shopid;
	}

	public Integer getIswxapp() {
		return iswxapp;
	}

	public void setIswxapp(Integer iswxapp) {
		this.iswxapp = iswxapp;
	}

}
