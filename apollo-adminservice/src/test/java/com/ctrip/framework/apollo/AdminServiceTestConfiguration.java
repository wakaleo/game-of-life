package com.ctrip.framework.apollo;

import com.ctrip.framework.apollo.adminservice.AdminServiceApplication;
import com.ctrip.framework.apollo.common.controller.HttpMessageConverterConfiguration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(excludeFilters = {@Filter(type = FilterType.ASSIGNABLE_TYPE, value = {
        LocalAdminServiceApplication.class, AdminServiceApplication.class,
        HttpMessageConverterConfiguration.class})})
@EnableAutoConfiguration
public class AdminServiceTestConfiguration {

}
