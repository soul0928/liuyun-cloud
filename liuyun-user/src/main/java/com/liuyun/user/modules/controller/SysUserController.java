package com.liuyun.user.modules.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.liuyun.core.mybatisplus.controller.IBaseController;
import com.liuyun.core.result.Result;
import com.liuyun.model.user.entity.SysUserEntity;
import com.liuyun.model.user.vo.SysUserInfoVO;
import com.liuyun.user.modules.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 用户信息表 前端控制器
 *
 * @author WangDong
 * @since 2020年12月7日 下午2:11:57

 */
@RestController
@RequestMapping("/sysUser")
@Api(tags = "用户信息表服务")
public class SysUserController extends IBaseController<SysUserEntity> {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据用户 id 获取用户信息
     *
     * @param id {@link Long} 用户 Id
     * @return com.liuyun.core.result.Result<com.liuyun.model.user.vo.SysUserInfoVO>
     * @author wangdong
     * @date 2020/12/14 3:38 下午
     **/
    @GetMapping("/queryUserById/{id}")
    @ApiModelProperty("根据用户 id 获取用户信息")
    public Result<SysUserInfoVO> queryUserById(@PathVariable("id") Long id) {
        SysUserEntity sysUserEntity = this.sysUserService.getById(id);
        SysUserInfoVO sysUserInfoVO = BeanUtil.copyProperties(sysUserEntity, SysUserInfoVO.class);
        return Result.success(sysUserInfoVO);
    }

    /**
     * 根据用户账号获取用户信息
     *
     * @param username {@link String} 用户账号
     * @return com.liuyun.core.result.Result<com.liuyun.model.user.vo.SysUserInfoVO>
     * @author wangdong
     * @date 2020/12/14 3:38 下午
     **/
    @GetMapping("/queryUserByUsername/{username}")
    @ApiModelProperty("根据用户账号获取用户信息")
    public Result<SysUserInfoVO> queryUserByUsername(@PathVariable("username") String username) {
        SysUserEntity sysUserEntity = new LambdaQueryChainWrapper<>(this.sysUserService.getBaseMapper())
                .eq(SysUserEntity::getUsername, username)
                .one();
        if (Objects.isNull(sysUserEntity)) {
            return Result.success();
        }
        SysUserInfoVO sysUserInfoVO = BeanUtil.copyProperties(sysUserEntity, SysUserInfoVO.class);
        return Result.success(sysUserInfoVO);
    }
}
