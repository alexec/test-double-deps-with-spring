package com.alexecollins.testing;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.w3c.dom.Element;

/**
 * @author alex.collins
 */
public class TestNamespaceHandler extends NamespaceHandlerSupport {
    private static class TestDoubleBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
        private final Class clazz;

        private TestDoubleBeanDefinitionParser(Class clazz) {
            this.clazz = clazz;
        }

        @Override
        protected Class getBeanClass(Element element) {
            return clazz;
        }

        @Override
        protected void doParse(Element element, BeanDefinitionBuilder builder) {
            builder.addConstructorArgValue(element.getAttribute("class"));
        }
    }

    @Override
    public void init() {
        registerBeanDefinitionParser("dummy", new TestDoubleBeanDefinitionParser(TestDummyFactoryBean.class));
        registerBeanDefinitionParser("fake", new TestDoubleBeanDefinitionParser(PartialFakeFactoryBean.class));
    }
}
