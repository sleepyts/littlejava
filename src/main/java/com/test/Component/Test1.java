package com.test.Component;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.concurrent.ThreadPoolExecutor;


public class Test1 implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean, DisposableBean {

    private String name;

    public Test1() {
    }

    public void fun1(){
        System.out.println("fun1() of Test1 called");
        System.out.println("Bean Name: " + name);
    }
    @Override
    public void setBeanName(String name) {
        name="BeanNameAware";
        this.name = name;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("Bean Factory: " + beanFactory.getClass().getName());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("Application Context: " + applicationContext.getClass().getName());
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Initializing Bean: " + this.getClass().getName());

        System.out.println("Bean Name: " + name);
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Disposing Bean: " + this.getClass().getName());
    }
}
