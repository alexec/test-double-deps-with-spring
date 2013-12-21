package com.alexecollins.testing;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.w3c.dom.Element;

/**
 * @author alex.collins
 */
public class TestNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("dummy", new AbstractSingleBeanDefinitionParser() {
            @Override
            protected Class getBeanClass(Element element) {
                return TestDummyFactoryBean.class;
            }

            @Override
            protected void doParse(Element element, BeanDefinitionBuilder builder) {
                builder.addConstructorArgValue(element.getAttribute("class"));
            }
        });
    }
}
