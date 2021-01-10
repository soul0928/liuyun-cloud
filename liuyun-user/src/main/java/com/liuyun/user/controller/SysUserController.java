package com.liuyun.user.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.liuyun.database.mybatisplus.controller.AbstractController;
import com.liuyun.database.mybatisplus.enums.DelFlagEnum;
import com.liuyun.model.user.entity.SysUserEntity;
import com.liuyun.model.user.vo.SysUserInfoVO;
import com.liuyun.user.service.SysUserService;
import com.liuyun.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
public class SysUserController extends AbstractController<SysUserEntity> {

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
    @ApiOperation(value = "根据用户 id 获取用户信息")
    public Result<SysUserInfoVO> queryUserById(@ApiParam(value = "用户ID", required = true) @PathVariable("id") Long id) {
        SysUserEntity sysUserEntity = this.sysUserService.getById(id);
        if (Objects.isNull(sysUserEntity)) {
            return Result.fail("用户信息不存在!!!");
        }
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
    @ApiOperation(value = "根据用户账号获取用户信息")
    public Result<SysUserInfoVO> queryUserByUsername(@ApiParam(value = "用户账号", required = true) @PathVariable("username") String username) {
        SysUserEntity sysUserEntity = new LambdaQueryChainWrapper<>(this.sysUserService.getBaseMapper())
                .eq(SysUserEntity::getUsername, username)
                .eq(SysUserEntity::getDelFlag, DelFlagEnum.NOT_DELETED)
                .one();
        if (Objects.isNull(sysUserEntity)) {
            return Result.fail("用户信息不存在!!!");
        }
        SysUserInfoVO sysUserInfoVO = BeanUtil.copyProperties(sysUserEntity, SysUserInfoVO.class);
        return Result.success(sysUserInfoVO);
    }

    /**
     * 根据用户手机号码获取用户信息
     *
     * @param phone {@link String} 用户手机号码
     * @return com.liuyun.core.result.Result<com.liuyun.model.user.vo.SysUserInfoVO>
     * @author wangdong
     * @date 2020/12/14 3:38 下午
     **/
    @GetMapping("/queryUserByPhone/{phone}")
    @ApiOperation(value = "根据用户 id 获取用户信息")
    public Result<SysUserInfoVO> queryUserByPhone(@ApiParam(value = "手机号码", required = true) @PathVariable("phone") String phone) {
        SysUserEntity sysUserEntity = new LambdaQueryChainWrapper<>(this.sysUserService.getBaseMapper())
                .eq(SysUserEntity::getDelFlag, DelFlagEnum.NOT_DELETED)
                .eq(SysUserEntity::getPhone, phone)
                .one();
        if (Objects.isNull(sysUserEntity)) {
            return Result.fail("用户信息不存在!!!");
        }
        SysUserInfoVO sysUserInfoVO = BeanUtil.copyProperties(sysUserEntity, SysUserInfoVO.class);
        return Result.success(sysUserInfoVO);
    }
}
