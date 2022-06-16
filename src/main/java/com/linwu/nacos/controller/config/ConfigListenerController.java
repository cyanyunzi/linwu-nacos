package com.linwu.nacos.controller.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;

@Api(tags = "配置监听器")
@RestController
@RequestMapping("/config/common")
public class ConfigListenerController {

  @ApiOperation("添加集群监听器")
  @PostMapping("/addListener")
  public ResponseEntity addListener(@RequestParam String dataId, @RequestParam String group)
      throws NacosException {
    node8848Listener(dataId, group);
    node8858Listener(dataId, group);
    node8868Listener(dataId, group);
    return ResponseEntity.ok(null);
  }

  private void node8848Listener(String dataId, String group) throws NacosException {
    NacosFactory.createConfigService("127.0.0.1:8848")
        .addListener(
            dataId,
            group,
            new Listener() {
              @Override
              public void receiveConfigInfo(String configInfo) {
                System.out.println("8848监听器 接收到变动:" + configInfo);
              }

              @Override
              public Executor getExecutor() {
                return null;
              }
            });
  }

  private void node8858Listener(String dataId, String group) throws NacosException {
    NacosFactory.createConfigService("127.0.0.1:8858")
        .addListener(
            dataId,
            group,
            new Listener() {
              @Override
              public void receiveConfigInfo(String configInfo) {
                System.out.println("8858监听器 接收到变动:" + configInfo);
              }

              @Override
              public Executor getExecutor() {
                return null;
              }
            });
  }

  private void node8868Listener(String dataId, String group) throws NacosException {
    NacosFactory.createConfigService("127.0.0.1:8868")
        .addListener(
            dataId,
            group,
            new Listener() {
              @Override
              public void receiveConfigInfo(String configInfo) {
                System.out.println("8868监听器 接收到变动:" + configInfo);
              }

              @Override
              public Executor getExecutor() {
                return null;
              }
            });
  }
}
