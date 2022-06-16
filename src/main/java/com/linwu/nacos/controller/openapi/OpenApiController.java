package com.linwu.nacos.controller.openapi;

import com.linwu.nacos.model.resp.OpenApiInstanceResp;
import com.linwu.nacos.model.resp.OpenApiPageServivesResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.linwu.nacos.model.resp.OpenApiInstancesResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@Api(tags = "OpenApi")
@RestController
@RequestMapping("/openApi")
public class OpenApiController {
  @Autowired private RestTemplate restTemplate;

  private String nacosIp = "127.0.0.1";
  private int nacosPort = 8848;

  @ApiOperation("替换nacos")
  @GetMapping("/switchNacos")
  public String switchNacos(String nacosIp, int nacosPort) {
    this.nacosIp = nacosIp;
    this.nacosPort = nacosPort;
    return "SUCCESS";
  }

  @ApiOperation("删除实例")
  @DeleteMapping("/deleteInstance")
  public String deleteInstance(String serviceName, String serviceIp, int servicePort) {
    String url = String.format("http://%s:%s%s", nacosIp, nacosPort, "/nacos/v1/ns/instance");

    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl(url)
            .queryParam("serviceName", serviceName)
            .queryParam("ip", serviceIp)
            .queryParam("port", servicePort)
            .queryParam("ephemeral", false);

    ResponseEntity<String> exchange1 =
        restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.DELETE,
            null,
            new ParameterizedTypeReference<String>() {});

    log.error(
        "删除永久实例  serviceName:{} ip:{}  port:{} result:{}",
        serviceName,
        serviceIp,
        servicePort,
        exchange1.getBody());

    builder =
        UriComponentsBuilder.fromHttpUrl(url)
            .queryParam("serviceName", serviceName)
            .queryParam("ip", serviceIp)
            .queryParam("port", servicePort)
            .queryParam("ephemeral", true);

    ResponseEntity<String> exchange =
        restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.DELETE,
            null,
            new ParameterizedTypeReference<String>() {});

    log.error(
        "删除永久实例  serviceName:{} ip:{}  port:{} result:{}",
        serviceName,
        serviceIp,
        servicePort,
        exchange.getBody());
    return "SUCCESS";
  }

  @ApiOperation("删除服务")
  @DeleteMapping("/deleteService")
  public String deleteService(String serviceName) {
    String url = String.format("http://%s:%s%s", nacosIp, nacosPort, "/nacos/v1/ns/service");

    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl(url).queryParam("serviceName", serviceName);

    ParameterizedTypeReference<String> reference = new ParameterizedTypeReference<String>() {};

    ResponseEntity<String> response =
        restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, null, reference);
    return response.getBody();
  }

  @ApiOperation("实例列表")
  @GetMapping("/instances")
  public ResponseEntity<OpenApiInstancesResp> getInstances(
      String serviceName) {
    String url = String.format("http://%s:%s%s", nacosIp, nacosPort, "/nacos/v1/ns/instance/list");

    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl(url).queryParam("serviceName", serviceName);

    ParameterizedTypeReference<OpenApiInstancesResp> reference =
        new ParameterizedTypeReference<OpenApiInstancesResp>() {};
    ResponseEntity<OpenApiInstancesResp> exchange =
        restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, reference);
    return exchange;
  }

  @ApiOperation("服务列表")
  @GetMapping("/services")
  public ResponseEntity<OpenApiPageServivesResp> getServices() {
    String url = String.format("http://%s:%s%s", nacosIp, nacosPort, "/nacos/v1/ns/service/list");

    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl(url).queryParam("pageNo", 1).queryParam("pageSize", 1000);

    ParameterizedTypeReference<OpenApiPageServivesResp> reference =
        new ParameterizedTypeReference<OpenApiPageServivesResp>() {};
    return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, reference);
  }

  @ApiOperation("删除所有服务实例")
  @DeleteMapping("/deleteAllServicesInstance")
  public String deleteAllServicesInstance() {
    ResponseEntity<OpenApiPageServivesResp> services = getServices();
    OpenApiPageServivesResp servicesBody = services.getBody();
    List<String> serviceNames = servicesBody.getDoms();
    if (serviceNames == null || serviceNames.isEmpty()) {
      return "";
    }

    for (String serviceName : serviceNames) {
      ResponseEntity<OpenApiInstancesResp> instances =
          getInstances(serviceName);
      OpenApiInstancesResp instancesBody = instances.getBody();
      List<OpenApiInstanceResp> instanceRespList = instancesBody.getHosts();
      if (instanceRespList == null || instanceRespList.isEmpty()) {
        continue;
      }
      for (OpenApiInstanceResp instanceResp : instanceRespList) {
        deleteInstance(serviceName, instanceResp.getIp(), instanceResp.getPort());
      }
    }

    return "SUCCESS";
  }
}
