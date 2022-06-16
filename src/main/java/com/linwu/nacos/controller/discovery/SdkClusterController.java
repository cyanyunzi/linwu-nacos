package com.linwu.nacos.controller.discovery;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.linwu.nacos.utils.NacosUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "SDK集群")
@RestController
@RequestMapping("/sdk/cluster")
public class SdkClusterController {

  @ApiOperation("获取服务实例列表")
  @GetMapping("/getInstances")
  public ResponseEntity<List<Instance>> get(String serviceName) throws NacosException {
    List<Instance> allInstances = NacosUtil.getNamingService().getAllInstances(serviceName);
    return ResponseEntity.ok(allInstances);
  }

  @ApiOperation("注册临时服务")
  @PostMapping("/register/ephemeral")
  public ResponseEntity<String> registerEphemeral(String serviceName) throws NacosException {
    NacosUtil.getNamingService().registerInstance(serviceName, "192.168.3.2", 8080);
    return ResponseEntity.ok("SUCCESS");
  }

  @ApiOperation("注销临时服务")
  @DeleteMapping("/logout/ephemeral")
  public ResponseEntity<String> logoutEphemeral(String serviceName) throws NacosException {
    // TODO 这个方法注销不能持久化服务
    NacosUtil.getNamingService().deregisterInstance(serviceName, "192.168.3.2", 8080);
    return ResponseEntity.ok("SUCCESS");
  }

  @ApiOperation("注册持久化服务")
  @PostMapping("/register/permanent")
  public ResponseEntity<String> registerPermanent(String serviceName) throws NacosException {
    Instance instance = new Instance();
    instance.setIp("192.168.3.2");
    instance.setPort(8080);
    instance.setWeight(1.0D);

    instance.setEphemeral(false);
    instance.setClusterName("DEFAULT");
    NacosUtil.getNamingService().registerInstance(serviceName, instance);
    return ResponseEntity.ok("SUCCESS");
  }

  @ApiOperation("注销持久化服务")
  @DeleteMapping("/logout/permanent")
  public ResponseEntity<String> logoutPermanent(String serviceName) throws NacosException {
    Instance instance = new Instance();
    instance.setIp("192.168.3.2");
    instance.setPort(8080);
    // TODO 这个不加 注销不成功,因为new 默认Instance 的Ephemeral 是true
    instance.setEphemeral(false);
    instance.setClusterName("DEFAULT");
    NacosUtil.getNamingService().deregisterInstance(serviceName, instance);
    return ResponseEntity.ok("SUCCESS");
  }




}
