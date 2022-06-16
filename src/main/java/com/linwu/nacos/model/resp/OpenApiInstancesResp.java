package com.linwu.nacos.model.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class OpenApiInstancesResp implements Serializable {
    private String name;
    private String groupName;
    private String clusters;
    private int cacheMillis;
    private List<OpenApiInstanceResp> hosts;
    private long lastRefTime;
    private String checksum;
    private Boolean allIPs;
    private Boolean reachProtectionThreshold;
    private Boolean valid;
}
