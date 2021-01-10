package com.liuyun.model.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  AbstractPageDTO
 *  分页实体
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/6 15:30
 */
@Data
@ApiModel(value = "分页实体")
public abstract class AbstractPageDTO<T> {

    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页")
    private Integer currPage;

    /**
     * 每页展示条数
     */
    @ApiModelProperty(value = "每页展示条数")
    private Integer pageSize;

}
