package com.linwu.nacos.controller.config;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.exception.NacosException;
import com.linwu.nacos.model.req.CommonPublishReq;
import com.linwu.nacos.utils.NacosUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "配置中心")
@RestController
@RequestMapping("/config/common")
public class ConfigController {

  @ApiOperation("发布配置")
  @PostMapping("/publish")
  public ResponseEntity publish(@RequestBody CommonPublishReq req) throws NacosException {
    ConfigService nacosConfigService = NacosUtil.getConfigService();
    boolean flag =
        nacosConfigService.publishConfig(
            req.getDataId(), req.getGroup(), req.getContent(), ConfigType.PROPERTIES.getType());
    return ResponseEntity.ok(flag);
  }

  @ApiOperation("获取配置")
  @GetMapping("/get")
  public ResponseEntity<String> get(@RequestParam String dataId, @RequestParam String group)
      throws NacosException {
    String config = NacosUtil.getConfigService().getConfig(dataId, group, 3000);
    return ResponseEntity.ok(config);
  }


}
