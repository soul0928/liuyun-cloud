package com.liuyun.database.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuyun.database.mybatisplus.mapper.AbstractMapper;
import com.liuyun.database.mybatisplus.service.AbstractService;


/**
 * AbstractServiceImpl
 * 重写基础CRUD
 * @author wangdong
 * @version 1.0.0
 * @date 2020/7/21 00:00
 **/
public abstract class AbstractServiceImpl<M extends AbstractMapper<T>, T> extends ServiceImpl<BaseMapper<T>, T>
        implements AbstractService<T> {



}
