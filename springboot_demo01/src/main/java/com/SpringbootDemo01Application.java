package com;

import org.apache.naming.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;

import java.util.Locale;

@SpringBootApplication
public class SpringbootDemo01Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootDemo01Application.class, args);
        /*
            1.什么是BeanFactory?
                - 它是ApplicationContext的父接口
                - 它是Spring的核心容器, 主要的ApplicationContext实现都组合了他的功能
         */

        /*
            2.BeanFactory能干啥
                - 表面上只有getBean
                - 实际上控制反转丶基本的依赖注入丶甚至bean生命周期的各种功能,都是由它的实现类提供
         */

        /*
            3.ApplicationContext功能
                1.MessageSource
         */
        //ApplicationContext
        //[1].国际化
        //  String message = context.getMessage("hi", null, Locale.CHINA);
        //[2].获取资源
        //System.out.println(context.getResource("classpath*:META-INFO/spring.factories"));
        //[3].获取配置信息
        //System.out.println(context.getEnvironment().getProperty("java_home"));
        //[4].发送事件,实现组件之间解耦


    }

}
