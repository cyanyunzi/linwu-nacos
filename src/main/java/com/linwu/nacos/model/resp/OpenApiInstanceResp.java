package com.linwu.nacos.model.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class OpenApiInstanceResp implements Serializable {
  private String instanceId;
  private String ip;
  private int port;
  private Double weight;
  private Boolean healthy;
  private Boolean enabled;
  private Boolean ephemeral;
  private String clusterName;
  private String serviceName;
  private Map<Object,Object> metadata;
  private Integer instanceHeartBeatInterval;
  private Integer ipDeleteTimeout;
  private String instanceIdGenerator;
  private Integer instanceHeartBeatTimeOut;
}
