package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApplicationContextTest {
    public static final Logger log = LoggerFactory.getLogger(ApplicationContextTest.class);
    public static void main(String[] args) {
//        ClassPathXmlApplicationContextTest();
//        FileSystemXmlApplicationContextTest();
//        AnnotationConfigApplicationContextTest();
        AnnotationConfigServletWebServerApplicationContextTest();

       /* DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        System.out.println("读取之前......");
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println("读取之后......");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        //1. ClassPathXmlApplicationContext
        reader.loadBeanDefinitions(new ClassPathResource("cpxapplicationcontext.xml"));
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        //2. FileSystemXmlApplicationContext
        reader.loadBeanDefinitions(new FileSystemResource("E:\\JavaCode\\springboot\\springboot_demo01\\springboot_demo01\\src\\main\\resources\\cpxapplicationcontext.xml"));
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }*/
        //3. AnnotationConfigApplicationContext
    }

    private static void FileSystemXmlApplicationContextTest() {
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("E:\\JavaCode\\springboot\\springboot_demo01\\springboot_demo01\\src\\main\\resources\\cpxapplicationcontext.xml");
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    private static void ClassPathXmlApplicationContextTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("cpxapplicationcontext.xml");
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    public static void AnnotationConfigApplicationContextTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    public static void AnnotationConfigServletWebServerApplicationContextTest() {
        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
    }

    @Configuration
    static class WebConfig {
        //创建Servlet容器
        @Bean
        public ServletWebServerFactory servletWebServerFactory() {
            return new TomcatServletWebServerFactory();
        }

        @Bean
        public DispatcherServlet dispatcherServlet() {
            return new DispatcherServlet();
        }
        //注册DispatcherServlet到Tomcat服务器
        @Bean
        public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet) {
            return new DispatcherServletRegistrationBean(dispatcherServlet,"/");
        }

        @Bean("/hello")
        public Controller controller() {
            return (request, response) -> {
                response.getWriter().write("Hello");
                return null;
            };
        }
    }
    @Configuration
    static class Config {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2(Bean1 bean1) {
            Bean2 bean2 = new Bean2();
            bean2.setBean1(bean1);
            return bean2;
        }
    }

    static class Bean1 {

    }

    static class Bean2 {
        private Bean1 bean1;

        public Bean1 getBean1() {
            return bean1;
        }

        public void setBean1(Bean1 bean1) {
            this.bean1 = bean1;
        }

    }
}
