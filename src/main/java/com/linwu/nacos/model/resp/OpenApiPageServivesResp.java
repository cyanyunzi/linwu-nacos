package com.linwu.nacos.model.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class OpenApiPageServivesResp implements Serializable {
    private int count;
    private List<String> doms;

}
