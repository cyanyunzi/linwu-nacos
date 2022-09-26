package com.linwu.nacos.controller.http3;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Api(tags = "okhttp测试")
@RestController
@RequestMapping("/okhttp")
public class OkHttpController {
    private OkHttpClient.Builder builder;

    @PostConstruct
    public void init(){
        builder = getClient();
    }

    @ApiOperation("请求OkHttp")
    @GetMapping("/send")
    public ResponseEntity<String> send() throws IOException {
        Request request = new Request.Builder()
                .url("http://localhost:8080/okhttp/req")
                .build();
        Call call = builder.build().newCall(request);
        Response response = call.execute();
        ResponseBody body = response.body();
        return ResponseEntity.ok(null);
    }


    @ApiOperation("OkHttp被请求接口")
    @GetMapping("/req")
    public ResponseEntity<String> test1(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        System.out.println("remoteAddr: "+remoteAddr);
        return ResponseEntity.ok(null);
    }



    public OkHttpClient.Builder getClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Interceptor headers = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl newUrl = original.url().newBuilder().host("192.168.3.25").build();
                Request completeRequest = original.newBuilder()
                        .url(newUrl)
                        .build();
                Request.Builder requestBuilder = completeRequest.newBuilder();
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

        OkHttpClient client = httpClient.build();

        httpClient.interceptors().add(headers);

        return httpClient;
    }


}
