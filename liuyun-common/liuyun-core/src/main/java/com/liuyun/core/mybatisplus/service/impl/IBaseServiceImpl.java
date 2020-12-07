package com.liuyun.core.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuyun.core.mybatisplus.mapper.IBaseMapper;
import com.liuyun.core.mybatisplus.service.IBaseService;


/**
 * IBaseServiceImpl
 * 重写基础CRUD
 * @author wangdong
 * @version 1.0.0
 * @date 2020/7/21 00:00
 **/
public abstract class IBaseServiceImpl<M extends IBaseMapper<T>, T> extends ServiceImpl<BaseMapper<T>, T>
        implements IBaseService<T> {



}
