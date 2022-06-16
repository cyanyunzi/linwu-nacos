package com.linwu.nacos.controller.server;

import com.alibaba.nacos.api.exception.NacosException;
import com.linwu.nacos.utils.NacosUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "NacosServer服务端")
@RestController
@RequestMapping("/server")
public class ServerController {

  @ApiOperation("获取服务节点状态")
  @GetMapping("/status")
  public ResponseEntity<String> get() throws NacosException {
    return ResponseEntity.ok(NacosUtil.getNamingService().getServerStatus());
  }


}
