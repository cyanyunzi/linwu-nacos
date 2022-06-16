package com.linwu.nacos.controller.config;

import com.alibaba.nacos.api.exception.NacosException;
import com.linwu.nacos.utils.NacosUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.linwu.nacos.model.req.TestConfigBean;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "启明星辰ConfigKT融合测试")
@RestController
@RequestMapping("/config/venus")
public class VenusConfigKTController {

  @ApiOperation("发布配置")
  @PostMapping("/publish")
  public ResponseEntity publish(
      @RequestParam String dataId, @RequestParam String group, @RequestBody TestConfigBean req)
      throws NacosException {
    Field[] fields = req.getClass().getDeclaredFields();

    StringBuilder builder = new StringBuilder();

    for (Field field : fields) {
      // 打开private权限
      ReflectionUtils.makeAccessible(field);
      // 字段值
      Object value = ReflectionUtils.getField(field, req);
      System.out.println("field:" + field.getName() + ",value:" + value);

      builder.append(field.getName()).append("=").append(value).append("\n");
    }

    NacosUtil.getConfigService().publishConfig(dataId, group, builder.toString());
    return ResponseEntity.ok(null);
  }

  @ApiOperation("获取配置")
  @GetMapping("/get")
  public ResponseEntity<TestConfigBean> get(@RequestParam String dataId, @RequestParam String group)
      throws NacosException {
    String config = NacosUtil.getConfigService().getConfig(dataId, group, 3000);
    String[] arr = config.split("\n");

    Map<String, String> map = new HashMap<>();
    for (String rowConfig : arr) {
      String[] split = rowConfig.split("=");
      String fieldName = split[0];
      String value = split[1];
      map.put(fieldName, value);
    }

    TestConfigBean testConfigBean = new TestConfigBean();
    Field[] fields = testConfigBean.getClass().getDeclaredFields();
    for (Field field : fields) {
      if (!map.containsKey(field.getName())) {
        continue;
      }
      ReflectionUtils.makeAccessible(field);
      ReflectionUtils.setField(
          field, testConfigBean, parseValue(field.getType(), map.get(field.getName())));
    }

    return ResponseEntity.ok(testConfigBean);
  }

  private Object parseValue(Class<?> clazz, String value) {
    Object result;
    switch (clazz.getName()) {
      case "int":
      case "java.lang.Integer":
        result = Integer.valueOf(value);
        break;
      case "long":
      case "java.lang.Long":
        result = Long.valueOf(value);
        break;
      case "float":
      case "java.lang.Float":
        result = Float.valueOf(value);
        break;
      case "double":
      case "java.lang.Double":
        result = Double.valueOf(value);
        break;
      case "java.lang.String":
        result = value;
        break;
      case "boolean":
      case "java.lang.Boolean":
        result = Boolean.valueOf(value);
        break;
      default:
        result = null;
    }
    if (Enum.class.isAssignableFrom(clazz)) {
      result = Enum.valueOf((Class<Enum>) clazz, value);
    }
    return result;
  }
}
