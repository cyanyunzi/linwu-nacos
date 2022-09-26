package com.linwu.nacos.controller.config;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.alibaba.nacos.api.exception.NacosException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "修改nacos密码")
@RestController
@RequestMapping("/update/password")
public class updatePasswordController {

    //nacos研发账户和密码 configadmin /venustech.taihe.db.CONF
    private String password = "venustech.taihe.db.CONF";
    private String encryptHexPassword = "";
    private String key = "venus";
    //aes加密key 需要128bits, 1个byte=8bit,8*16=128bits
    public final static byte[] aes_key = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

    @ApiOperation("加密")
    @GetMapping("/jiami")
    public ResponseEntity<String> jiami()
            throws NacosException {
        // 随机生成密钥
        // 构建
        AES aes = SecureUtil.aes(aes_key);
        // 加密为16进制表示
        encryptHexPassword = aes.encryptHex(password);
        System.out.println("加密:"+encryptHexPassword);
        return ResponseEntity.ok(password);
    }

    @ApiOperation("解密")
    @GetMapping("/jiemi")
    public ResponseEntity<String> jiemi()
            throws NacosException {
        AES aes = SecureUtil.aes(aes_key);
        // 解密为字符串
        String decryptStr = aes.decryptStr(encryptHexPassword, CharsetUtil.CHARSET_UTF_8);
        System.out.println("解密:"+decryptStr);
        return ResponseEntity.ok(password);
    }


}
