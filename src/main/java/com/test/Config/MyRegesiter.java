package com.test.Config;

import com.test.Domain.Role;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

@Configuration
public class MyRegesiter implements ImportBeanDefinitionRegistrar {

    // importingClassMetadata is the metadata of the class that imports this registrar.
    // registry give us a way to register new beans that can set args for the constructor of the bean.
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        System.out.println("MyRegesiter.registerBeanDefinitions");
        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Role.class).addConstructorArgValue("testNt").getBeanDefinition();
        System.out.printf("beanDefinition.getBeanClassName() = %s\n", beanDefinition.getBeanClassName());
        registry.registerBeanDefinition("testNt", beanDefinition);
    }
}
