package com.liuyun.core.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.liuyun.core.mybatisplus.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/6 14:39
 **/
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public abstract class IBaseEntity<T> implements Serializable {

    private static final long serialVersionUID = -2133014277294316920L;

    /**
     * 删除标记（0：正常；1：删除；）
     */
    public static final Integer DEL_FLAG_NORMAL = 0;
    public static final Integer DEL_FLAG_DELETE = 1;


    /**
     * 主键 id (IdType.ASSIGN_ID)
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    protected Long id;
    /**
     * 创建者
     */
    @TableField(value = "create_user_id")
    protected Long createUserId;
    /**
     * 创建日期
     */
    @TableField(value = "create_time")
    protected LocalDateTime createTime;
    /**
     * 更新者
     */
    @TableField(value = "update_user_id")
    protected Long updateUserId;
    /**
     * 更新日期
     */
    @TableField(value = "update_time")
    protected LocalDateTime updateTime;
    /**
     * 备注
     */
    @TableField(value = "remark")
    protected String remark;
    /**
     * 版本
     */
    @TableField(value = "version")
    protected String version;
    /**
     * 删除标记（0：正常；1：删除;）
     */
    @TableLogic
    @TableField(value = "del_flag")
    protected DelFlagEnum delFlag;
}
