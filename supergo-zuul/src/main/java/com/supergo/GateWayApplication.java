package com.supergo;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by on 2018/12/12.
 */
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableSwaggerBootstrapUI
public class GateWayApplication {

    public static void main(String[] args) {
        //入口
        SpringApplication.run(GateWayApplication.class,args);
    }

}
