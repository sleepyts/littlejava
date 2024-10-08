package com.test.Config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata metadata) {

        System.out.printf("MyImportSelector.selectImports() called with metadata: %s\n",
                metadata.getClassName());
        System.out.printf("MyImportSelector.selectImports() called with annotationAttributes: %s\n",
                metadata.getAnnotationAttributes(EnableAutoConfiguration.class.getName(), true));
        return new String[] { "com.Domain.Role", "com.Component.Test1" };
    }

}
