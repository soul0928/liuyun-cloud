package com.liuyun.user.modules.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyun.core.mybatisplus.controller.IBaseController;
import com.liuyun.core.result.Result;
import com.liuyun.model.user.dto.SysUserCreateDTO;
import com.liuyun.model.user.dto.SysUserPageDTO;
import com.liuyun.model.user.entity.SysUserEntity;
import com.liuyun.model.user.vo.SysUserInfoVO;
import com.liuyun.model.user.vo.SysUserPageVO;
import com.liuyun.user.modules.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/page")
    @ApiModelProperty("分页列表")
    public Result<Page<SysUserPageVO>> page(@RequestBody SysUserPageDTO sysUserPageDTO) {
        Page<SysUserPageVO> page = sysUserService.queryPage(sysUserPageDTO);
        return Result.success(page);
    }

    @PostMapping("/save")
    @ApiModelProperty("新增")
    public Result<Object> save(@RequestBody SysUserCreateDTO sysUserCreateDTO) {
        SysUserEntity sysUserEntity = BeanUtil.copyProperties(sysUserCreateDTO, SysUserEntity.class);
        sysUserService.save(sysUserEntity);
        return Result.success();
    }

    @GetMapping("/info/{id}")
    @ApiModelProperty("详情")
    public Result<SysUserInfoVO> info(@PathVariable("id") Long id) {
        SysUserEntity sysUserEntity = sysUserService.getById(id);
        SysUserInfoVO sysUserInfoVO = BeanUtil.copyProperties(sysUserEntity, SysUserInfoVO.class);
        return Result.success(sysUserInfoVO);
    }

    @DeleteMapping(value = "/remove/{id}")
    @ApiModelProperty("删除")
    public Result<Object> remove(@PathVariable("id") Long id) {
        sysUserService.removeById(id);
        return Result.success();
    }
}
