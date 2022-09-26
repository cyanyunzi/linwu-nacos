package com.linwu.nacos.controller.config;

import com.alibaba.nacos.api.exception.NacosException;
import com.linwu.nacos.utils.NacosUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "心跳")
@RestController
@RequestMapping("/heartbeat")
public class HeartbeatController {


  @ApiOperation("获取配置")
  @GetMapping("/get")
  public ResponseEntity<String> get(@RequestParam String dataId, @RequestParam String group)
      throws NacosException {
    //UP
    //DOWN
    String serverStatus = NacosUtil.getNamingService().getServerStatus();
    return ResponseEntity.ok(serverStatus);
  }


}
