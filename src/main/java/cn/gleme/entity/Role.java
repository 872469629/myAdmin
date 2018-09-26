/*
 * Copyright 2005-2015 gleme.cn. All rights reserved.
 * Support: http://www.gleme.cn

 */
package cn.gleme.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import cn.gleme.BaseAttributeConverter;
import cn.gleme.BaseAttributeConverter;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity - 角色
 * 
 * @author XJANY Team
 * @version 4.0
 */
@Entity
@Table(name = "xx_role")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_role")
public class Role extends BaseEntity<Long> {

	private static final long serialVersionUID = -5297794640913081563L;

	/** 名称 */
	private String name;

	/** 是否内置 */
	private Boolean isSystem;

	/** 描述 */
	private String description;

	/** 权限 */
	private List<AuthResource> authResource = new ArrayList<AuthResource>();

	/** 管理员 */
	private Set<Admin> admins = new HashSet<Admin>();

	/** 等级 0超级管理员 1超级管理员助理 2代理管理员 3代理助理 4商家管理员 5商家管理助理*/
	private Integer level;

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取是否内置
	 * 
	 * @return 是否内置
	 */
	@Column(nullable = false, updatable = false)
	public Boolean getIsSystem() {
		return isSystem;
	}

	/**
	 * 设置是否内置
	 * 
	 * @param isSystem
	 *            是否内置
	 */
	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	/**
	 * 获取描述
	 * 
	 * @return 描述
	 */
	@Length(max = 200)
	public String getDescription() {
		return description;
	}

	/**
	 * 设置描述
	 * 
	 * @param description
	 *            描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取管理员
	 * 
	 * @return 管理员
	 */
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	public Set<Admin> getAdmins() {
		return admins;
	}

	/**
	 * 设置管理员
	 * 
	 * @param admins
	 *            管理员
	 */
	public void setAdmins(Set<Admin> admins) {
		this.admins = admins;
	}

	/**
	 * 获取权限资源
	 *
	 * @return 权限资源
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_role_resource")
	public List<AuthResource> getAuthResource() {
		return authResource;
	}

	/**
	 * 设置权限资源
	 *
	 *            权限资源
	 */
	public void setAuthResource(List<AuthResource> authResource) {
		this.authResource = authResource;
	}


	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * 类型转换 - 权限
	 * 
	 * @author XJANY Team
	 * @version 4.0
	 */
	@Converter
	public static class AuthorityConverter extends BaseAttributeConverter<List<String>> implements AttributeConverter<Object, String> {
	}

}
