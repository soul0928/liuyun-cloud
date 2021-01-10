package com.liuyun.user.service.impl;

import com.liuyun.database.mybatisplus.service.impl.AbstractServiceImpl;
import com.liuyun.model.user.dto.AllRolePermissionDTO;
import com.liuyun.model.user.entity.SysPermissionEntity;
import com.liuyun.model.user.entity.SysRolePermissionEntity;
import com.liuyun.model.user.enums.SysPermissionTypeEnum;
import com.liuyun.redis.constants.UserRedisConstants;
import com.liuyun.redis.service.RedisService;
import com.liuyun.user.mapper.SysRolePermissionMapper;
import com.liuyun.user.service.SysRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色权限关联表 服务实现类
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
@Slf4j
@Service
public class SysRolePermissionServiceImpl
        extends AbstractServiceImpl<SysRolePermissionMapper, SysRolePermissionEntity> implements SysRolePermissionService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @PostConstruct
    public void initAllInterfacePermission() {
        log.info("初始化角色权限信息...");
        this.loadAllInterfacePermissionToCache();
    }

    /**
     * 获取路径需要角色信息
     *
     * @param url {@link String} 请求路径
     * @return java.util.Set<java.lang.String>
     * @author wangdong
     * @date 2021/1/3 7:04 下午
     **/
    @Override
    public Set<String> getRoleByUrl(String url) {
        List<AllRolePermissionDTO> allList = this.loadAllInterfacePermissionToCache();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return allList
                .stream()
                .filter(i -> antPathMatcher.match(i.getPermissionUri(), url))
                .map(AllRolePermissionDTO::getRoleCode)
                .collect(Collectors.toSet());
    }

    /**
     * 加载角色权限信息放入缓存
     *
     * @return java.util.List<com.liuyun.model.user.dto.AllRolePermissionDTO>
     * @author wangdong
     * @date 2021/1/3 7:12 下午
     **/
    private List<AllRolePermissionDTO> loadAllInterfacePermissionToCache() {
        String cacheKey = UserRedisConstants.getKey(UserRedisConstants.USER_PREFIX, UserRedisConstants.ROLE_INFO_PREFIX);
        if (redisService.exists(cacheKey)) {
            redisService.delete(cacheKey);
        }
        SysPermissionEntity sysPermissionEntity = new SysPermissionEntity();
        sysPermissionEntity.setDelFlag(SysPermissionEntity.DEL_FLAG_NORMAL);
        sysPermissionEntity.setPermissionType(SysPermissionTypeEnum.URL.getCode());
        List<AllRolePermissionDTO> allList = this.sysRolePermissionMapper.queryAllRolePermission(sysPermissionEntity);
        allList = Optional.ofNullable(allList).orElse(new ArrayList<>());
        Map<String, Set<String>> allMap = allList
                .stream()
                .collect(
                        Collectors.groupingBy(AllRolePermissionDTO::getPermissionUri,
                                Collectors.mapping(AllRolePermissionDTO::getRoleCode, Collectors.toSet()))
                );
        redisService.hSetAll(cacheKey, allMap);
        return allList;
    }
}
