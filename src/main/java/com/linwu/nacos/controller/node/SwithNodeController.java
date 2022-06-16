package com.linwu.nacos.controller.node;

import com.alibaba.nacos.api.exception.NacosException;
import com.linwu.nacos.utils.NacosUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "切换nacos节点")
@RestController
@RequestMapping("/switch")
public class SwithNodeController {

  @ApiOperation("切换定制")
  @GetMapping("/custom")
  public ResponseEntity<String> custom(@RequestParam String serviceIp, @RequestParam int port)
      throws NacosException {
    NacosUtil.setConfigService(serviceIp,port);
    NacosUtil.setNamingService(serviceIp,port);
    return ResponseEntity.ok("SUCCESS");
  }

  @ApiOperation("切换nacos-8848")
  @GetMapping("/nacos8848")
  public ResponseEntity<String> switchNacos1() throws NacosException {
    NacosUtil.setConfigService("127.0.0.1",8848);
    NacosUtil.setNamingService("127.0.0.1",8848);
    return ResponseEntity.ok("SUCCESS");
  }

  @ApiOperation("切换nacos-8858")
  @GetMapping("/nacos8858")
  public ResponseEntity<String> switchNacos2() throws NacosException {
    NacosUtil.setConfigService("127.0.0.1",8858);
    NacosUtil.setNamingService("127.0.0.1",8858);
    return ResponseEntity.ok("SUCCESS");
  }

  @ApiOperation("切换nacos-8868")
  @GetMapping("/nacos8868")
  public ResponseEntity switchNacos3() throws NacosException {
    NacosUtil.setConfigService("127.0.0.1",8868);
    NacosUtil.setNamingService("127.0.0.1",8868);
    return ResponseEntity.ok("SUCCESS");
  }
}
