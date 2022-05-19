package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class TestBeanFactory {
    public static void main(String[] args) {
        //BeanFactory的一个重要实现类
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //添加bean的定义 (类型,scope,初始化,销毁)
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(config.class).setScope("singleton").getBeanDefinition();
        //bean工厂就有了bean
        beanFactory.registerBeanDefinition("config", beanDefinition);
        //1.BeanFactory的后处理器
        //BeanFactory没有解析注解@Configuration,@Bean的能力
        //调用工具类, 给BeanFactory添加了一些常用的后处理器,能够解析
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
        //加入bean工厂之后工作,拿到BeanFactory后处理器
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }


        //2.Bean 后处理器, 针对Bean 生命周期各个阶段提供扩展,例如: @Autowired, @Resource...
        //  添加到Bean工厂
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanFactory::addBeanPostProcessor);
        //准备好所有单例, 提前将单例对象实例化
        beanFactory.preInstantiateSingletons();
        System.out.println("-------------------------------------------");
        System.out.println(beanFactory.getBean(Bean1.class).getBean2());

        /**
         * BeanFactory不会做的事
         *      1.不会主动调用BeanFactory后处理器
         *      2.不会主动添加Bean后处理器
         *      3.不会主动初始化单例
         *      4.不会解析beanFactory,还不会解析${},#{}
         * bean后处理器会有排序的逻辑
         *      都实现了order的接口, 数字小优先级高
         */
    }

    @Configuration
    static class config {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }
    }

    static class Bean1 {
        private static final Logger log = LoggerFactory.getLogger(Bean1.class);

        public Bean1() {
            log.debug("构造Bean1()");
        }

        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2() {
            return bean2;
        }
    }

    static class Bean2 {
        private static final Logger log = LoggerFactory.getLogger(Bean2.class);

        public Bean2() {log.debug("构造Bean2()"); }
    }
}
