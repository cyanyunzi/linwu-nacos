package com.linwu.nacos.controller.address;

import com.alibaba.nacos.api.exception.NacosException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Api(tags = "地址服务器")
@RestController
@RequestMapping("/address")
public class AddressController {
  private List<String> list = new ArrayList<>();

  @PostConstruct
  public void init() {
    list.add("192.168.3.2:8848");
//    list.add("192.168.3.2:8858");
//    list.add("192.168.3.2:8868");
  }

  @ApiOperation("获取集群配置")
  @GetMapping("/serverlist")
  public ResponseEntity<String> getServerlist(String nodeName) throws NacosException {
    StringBuilder builder = new StringBuilder();
    for (String server : list) {
      builder.append(server).append('\n');
    }
    log.info("{} 获取集群列表:{}", nodeName, LocalDateTime.now());
    return ResponseEntity.ok(builder.toString());
  }

  @ApiOperation("添加集群配置")
  @PostMapping("/serverlist")
  public ResponseEntity<String> addServerlist(String serviceIp, int port) throws NacosException {
    list.add(serviceIp + ":" + port);
    return ResponseEntity.ok("SUCCESS");
  }

  @ApiOperation("删除集群配置")
  @DeleteMapping("/serverlist")
  public ResponseEntity<String> deleteServerlist(String serviceIp, int port) throws NacosException {
    list.remove(serviceIp + ":" + port);
    return ResponseEntity.ok("SUCCESS");
  }
}
