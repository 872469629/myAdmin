/*
 * Copyright 2005-2015 gleme.cn. All rights reserved.
 * Support: http://www.gleme.cn

 */
package cn.gleme.service.impl;

import cn.gleme.entity.${model};
import cn.gleme.service.${model}Service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - ${name}
 *
 * @author XJANY Team
 * @version 4.0
 */
@Service("${repository}ServiceImpl")
public class ${model}ServiceImpl extends BaseServiceImpl<${model}, Long> implements ${model}Service {

}