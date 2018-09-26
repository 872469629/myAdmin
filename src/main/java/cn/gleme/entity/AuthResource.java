package cn.gleme.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "xx_auth_resource")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_auth_RESOURCE")
public class AuthResource extends BaseEntity<Long> {
/*	
   	ID                   NUMBER(19,0)                    'id';
   	RES_NAME             VARCHAR2(128),                  '名称';
   	RES_TYPE             NUMBER(19,0),                   '资源类型';
   	RES_CODE             VARCHAR2(100)                   '资源编码';
   	RES_STR              VARCHAR2(256),                  '资源字段';
   	RES_PAGE_ID          VARCHAR2(100),                  '资源页面代码';
   	PARENT_RES_ID        NUMBER(19,0),                   '上级资源ID';
   	PARENT_RES_CODE      VARCHAR2(100),                  '上级资源编码';
   	ACTION_STR           VARCHAR2(2000),                 '点击动作';
   	ICON_SRC             VARCHAR2(128),                  '图标';
   	COUNT_FLAG           NUMBER(1),                      '0：不统计 1：统计';
   	SEQUENCES            NUMBER(6),                      '顺序号';
   	IS_VALID             NUMBER(1),                      '0：不启用 1：启用';
   	COMMENTS             VARCHAR2(512),                  '备注';
*/
	private static final long serialVersionUID = 1L;

	private String resName;
	private String resStr;
	private String resPageId;
	private AuthResource parent;
	private String actionStr;
	private String iconSrc;
	private Integer countFlag = 0;
	private Integer sequences = 1;
	private Integer isValid = 1;
	private String comments;
	private String iconColor;
	
	private List<Role> roles;
	private List<AuthResource> children;
	

	@ManyToMany(mappedBy = "authResource",fetch = FetchType.LAZY)
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, targetEntity = AuthResource.class)
	@OrderBy("sequences")
	public List<AuthResource> getChildren() {
		return children;
	}
	public void setChildren(List<AuthResource> children) {
		this.children = children;
	}
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "PARENT_RES_ID",referencedColumnName="id")
	public AuthResource getParent() {
		return parent;
	}
	public void setParent(AuthResource parent) {
		this.parent = parent;
	}
	
	@Column(name = "RES_NAME")
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}


	@Column(name = "RES_STR")
	public String getResStr() {
		return resStr;
	}
	public void setResStr(String resStr) {
		this.resStr = resStr;
	}

	@Column(name = "RES_PAGE_ID")
	public String getResPageId() {
		return resPageId;
	}
	public void setResPageId(String resPageId) {
		this.resPageId = resPageId;
	}


	@Column(name = "ACTION_STR")
	public String getActionStr() {
		return actionStr;
	}
	public void setActionStr(String actionStr) {
		this.actionStr = actionStr;
	}

	@Column(name = "ICON_SRC")
	public String getIconSrc() {
		return iconSrc;
	}
	public void setIconSrc(String iconSrc) {
		this.iconSrc = iconSrc;
	}

	@Column(name = "COUNT_FLAG")
	public Integer getCountFlag() {
		return countFlag;
	}
	public void setCountFlag(Integer countFlag) {
		this.countFlag = countFlag;
	}

	public Integer getSequences() {
		return sequences;
	}
	public void setSequences(Integer sequences) {
		this.sequences = sequences;
	}

	@Column(name = "IS_VALID")
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		List<Role> roles = getRoles();
		if (roles != null) {
			for (Role role : roles) {
				role.getAuthResource().remove(this);
			}
		}
	}
	public String getIconColor() {
		return iconColor;
	}
	public void setIconColor(String iconColor) {
		this.iconColor = iconColor;
	}
}
