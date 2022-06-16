package com.linwu.nacos.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel
@Data
public class CommonPublishReq implements Serializable {
    @ApiModelProperty("dataId")
    private String dataId;
    @ApiModelProperty("group")
    private String group;
    @ApiModelProperty("content")
    private String content;

}
