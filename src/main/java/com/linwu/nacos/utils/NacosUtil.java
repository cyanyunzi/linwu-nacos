package com.linwu.nacos.utils;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;

public class NacosUtil {
  private static NamingService namingService;
  private static ConfigService configService;

  public static void setNamingService(String serviceIp, int port) throws NacosException {
    namingService = NacosFactory.createNamingService(serviceIp + ":" + port);
  }

  public static void setConfigService(String serviceIp, int port) throws NacosException {
    configService = NacosFactory.createConfigService(serviceIp + ":" + port);
  }

  public static NamingService getNamingService() throws NacosException {
    return namingService;
  }

  public static ConfigService getConfigService() throws NacosException {
    return configService;
  }
}
