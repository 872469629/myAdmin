/*
 * Copyright 2005-2015 gleme.cn. All rights reserved.
 * Support: http://www.gleme.cn

 */
package cn.gleme.service.impl;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.gleme.Filter;
import cn.gleme.Order;
import cn.gleme.Page;
import cn.gleme.Pageable;
import cn.gleme.dao.BaseDao;
import cn.gleme.entity.BaseEntity;
import cn.gleme.service.BaseService;
import cn.gleme.Filter;
import cn.gleme.Order;
import cn.gleme.Page;
import cn.gleme.Pageable;
import cn.gleme.dao.BaseDao;
import cn.gleme.entity.BaseEntity;
import cn.gleme.service.BaseService;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Service - 基类
 * 
 * @author XJANY Team
 * @version 4.0
 */
@Transactional
public abstract class BaseServiceImpl<T extends BaseEntity<ID>, ID extends Serializable> implements BaseService<T, ID> {

	/** 更新忽略属性 */
	private static final String[] UPDATE_IGNORE_PROPERTIES = new String[] { BaseEntity.CREATE_DATE_PROPERTY_NAME, BaseEntity.MODIFY_DATE_PROPERTY_NAME, BaseEntity.VERSION_PROPERTY_NAME };

	/** BaseDao */
	private BaseDao<T, ID> baseDao;

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	protected void setBaseDao(BaseDao<T, ID> baseDao) {
		this.baseDao = baseDao;
	}

	@Transactional(readOnly = true)
	public T find(ID id) {
		return baseDao.find(id);
	}

	@Transactional(readOnly = true)
	public List<T> findAll() {
		return findList(null, null, null, null);
	}

	@Transactional(readOnly = true)
	public List<T> findList(ID... ids) {
		List<T> result = new ArrayList<T>();
		if (ids != null) {
			for (ID id : ids) {
				T entity = find(id);
				if (entity != null) {
					result.add(entity);
				}
			}
		}
		return result;
	}

	@Transactional(readOnly = true)
	public List<T> findList(Integer count, List<Filter> filters, List<Order> orders) {
		return findList(null, count, filters, orders);
	}

	@Transactional(readOnly = true)
	public List<T> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders) {
		return baseDao.findList(first, count, filters, orders);
	}

	@Transactional(readOnly = true)
	public Page<T> findPage(Pageable pageable) {
		return baseDao.findPage(pageable);
	}

	@Transactional(readOnly = true)
	public long count() {
		return count(new Filter[] {});
	}

	@Transactional(readOnly = true)
	public long count(Filter... filters) {
		return baseDao.count(filters);
	}

	@Transactional(readOnly = true)
	public boolean exists(ID id) {
		return baseDao.find(id) != null;
	}

	@Transactional(readOnly = true)
	public boolean exists(Filter... filters) {
		return baseDao.count(filters) > 0;
	}

	@Transactional
	public T save(T entity) {
		Assert.notNull(entity);
		Assert.isTrue(entity.isNew());

		baseDao.persist(entity);
		return entity;
	}

	@Transactional
	public T update(T entity) {
		Assert.notNull(entity);
		Assert.isTrue(!entity.isNew());

		if (!baseDao.isManaged(entity)) {
			T persistant = baseDao.find(baseDao.getIdentifier(entity));
			if (persistant != null) {
				copyProperties(entity, persistant, UPDATE_IGNORE_PROPERTIES);
			}
			return persistant;
		}
		return entity;
	}

	@Transactional
	public T update(T entity, String... ignoreProperties) {
		Assert.notNull(entity);
		Assert.isTrue(!entity.isNew());
		Assert.isTrue(!baseDao.isManaged(entity));

		T persistant = baseDao.find(baseDao.getIdentifier(entity));
		if (persistant != null) {
			copyProperties(entity, persistant, (String[]) ArrayUtils.addAll(ignoreProperties, UPDATE_IGNORE_PROPERTIES));
		}
		return update(persistant);
	}

	@Transactional
	public void delete(ID id) {
		delete(baseDao.find(id));
	}

	@Transactional
	public void delete(ID... ids) {
		if (ids != null) {
			for (ID id : ids) {
				delete(baseDao.find(id));
			}
		}
	}

	@Transactional
	public void delete(T entity) {
		if (entity != null) {
			baseDao.remove(baseDao.isManaged(entity) ? entity : baseDao.merge(entity));
		}
	}

	/**
	 * 拷贝对象属性
	 * 
	 * @param source
	 *            源
	 * @param target
	 *            目标
	 * @param ignoreProperties
	 *            忽略属性
	 */
	protected void copyProperties(T source, T target, String... ignoreProperties) {
		Assert.notNull(source);
		Assert.notNull(target);

		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(target);
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String propertyName = propertyDescriptor.getName();
			Method readMethod = propertyDescriptor.getReadMethod();
			Method writeMethod = propertyDescriptor.getWriteMethod();
			if (ArrayUtils.contains(ignoreProperties, propertyName) || readMethod == null || writeMethod == null || !baseDao.isLoaded(source, propertyName)) {
				continue;
			}
			try {
				Object sourceValue = readMethod.invoke(source);
				writeMethod.invoke(target, sourceValue);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	@Transactional(readOnly = true)
	public List  find(String sql, Object... values) {
		Assert.hasText(sql);
		Query query =  entityManager.createQuery(sql);
		if (null != values) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i+1, values[i]);
			}
		}
		return query.getResultList();
	}

	@Transactional(readOnly = true)
	public T findUniqueBy(String sql, Object... values) {
		Assert.hasText(sql);
		Query query =  entityManager.createQuery(sql);
		if (null != values) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i+1, values[i]);
			}
		}
		if(query.getResultList().size()==1){
			return (T) query.getResultList().get(0);
		}else{
			return null;
		}
	}

	/**
	 * 用于防止保存新对象时覆盖掉旧值
	 */
	@Transactional
	public void saveOrUpdate(T entity,String ... ignoreProperties ){
		Assert.notNull(entity);
		if (baseDao.isManaged(entity)) {
//			throw new IllegalArgumentException("Entity must not be managed");
		}
		try {
			T persistant = find(baseDao.getIdentifier(entity));
			if (persistant != null) {
				T saveEntity;
				saveEntity = (T) entity.getClass().newInstance();
				PropertyDescriptor[] propertyDescriptors = org.springframework.beans.BeanUtils.getPropertyDescriptors(entity.getClass());
				a:for (int i=0;i<propertyDescriptors.length;i++){
					String propertyName = propertyDescriptors[i].getName();
					//去除Object类的属性:class,如果model中含有的字段或者参数中有的字段都附加到entity中。
					if ("class".equals(propertyName)) continue;
					if ("new".equals(propertyName)) continue;
					for(String ignore:ignoreProperties){
						if(propertyName.equals(ignore)){
							continue a;
						}
					}
					if ( null != org.apache.commons.beanutils.BeanUtils.getProperty(entity, propertyName)){
						PropertyUtils.setProperty(persistant, propertyName, PropertyUtils.getProperty(entity, propertyName));
					}
				}
				PropertyUtils.copyProperties(saveEntity, persistant);
				update(saveEntity);
			}else{
				entityManager.persist(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}