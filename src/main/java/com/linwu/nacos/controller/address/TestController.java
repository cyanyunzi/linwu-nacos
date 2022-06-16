package com.linwu.nacos.controller.address;

import com.alibaba.nacos.api.exception.NacosException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@Api(tags = "测试")
@RestController
@RequestMapping("/test")
public class TestController {
  @Autowired private ConfigurableEnvironment configurableEnvironment;

  @ApiOperation("获取配置")
  @GetMapping("/get")
  public ResponseEntity<String> get()
          throws NacosException {
    Map<String, Object> systemProperties = configurableEnvironment.getSystemProperties();
    systemProperties.put("linwu.url", "wwwwwwwwwwwwwwww");

    return ResponseEntity.ok(null);

  }
}
