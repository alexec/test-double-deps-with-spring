---
title: Mocking Test Dependencies with Spring
date: 2013-12-21 17:30 UTC
tags: spring, java, testing, junit, mocking
---
I've been recently working on a large code base with a number of dependencies on other projects. Those projects provide classes used in our tests, but change often, which mean our test doubles regularly have to be updated.

I'm going to talk about some interesting solutions to this problem, such as mocking beans, custom Spring namespace, and partial fakes using CGLIB, that can make this other testing situations faster and give you less maintenance overhead.

Martin Fowler has a great post on the differences between [mocks, stub, fakes and dummies](http://martinfowler.com/articles/mocksArentStubs.html), which you might want to read first.

Test Dummys
---
A dummy is a test double that is uses to fill parameter list; returning the most basic responses. Spring allows you to create beans using a factory. The factory can create a bean, so we're going to ask the factory to create a dummy. A dummy returns default responses.

~~~xml
    <bean class="com.alexecollins.testing.TestDummyFactoryBean">
        <constructor-arg value="com.alexecollins.testing.Example"/>
    </bean>
~~~

A [basic factory bean](https://github.com/alexec/test-double-deps-with-spring/blob/master/src/main/java/com/alexecollins/testing/TestDummyFactoryBean.java) creates a JDK proxy that returns default values.

Firstly, we need a class to return default values:

~~~java
class Defaults {
    static final Map<Class<?>, Object> DEFAULTS = new HashMap<Class<?>, Object>();

    static {
        DEFAULTS.put(boolean.class, false);
        DEFAULTS.put(char.class, '\0');
        DEFAULTS.put(byte.class, (byte) 0);
        DEFAULTS.put(short.class, (short) 0);
        DEFAULTS.put(int.class, 0);
        DEFAULTS.put(long.class, 0L);
        DEFAULTS.put(float.class, 0f);
        DEFAULTS.put(double.class, 0d);
    }

    static Object get(Class<?> type) {
        return type.isPrimitive() ? Defaults.get(type) : type.isArray() ? new Object[0] : null;
    }
}
~~~

~~~java
public class TestDummyFactoryBean implements FactoryBean {
    ...
    private final Object object;

    public TestDummyFactoryBean(Class clazz) {

        object = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) {
                return Defaults.get(method.getReturnType());
            }
        });
    }

    @Override
    public Object getObject() {
        return object;
    }
    ...
~~~

Test Namespace
---
That's a nice start, but we're going to end up with a verbose XML document, three lines of boiler plate where we would have only one for a normal bean. How about this instead?

~~~xml
	<test:dummy id="example" class="com.alexecollins.testing.Example"/>
~~~
We can create a Spring [namespace handler](http://docs.spring.io/spring/docs/3.0.0.M3/reference/html/apbs03.html). We need three things, firstly a [class that creates the dummies:](https://github.com/alexec/test-double-deps-with-spring/blob/master/src/main/java/com/alexecollins/testing/TestNamespaceHandler.java)

~~~java
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
~~~

And then we also need plumbing in terms of [XSD](https://github.com/alexec/test-double-deps-with-spring/blob/master/src/main/resources/com/alexecollins/testing/test.xsd), [spring.schemas](https://github.com/alexec/test-double-deps-with-spring/blob/master/src/main/resources/META-INF/spring.schemas) and [spring.handlers](https://github.com/alexec/test-double-deps-with-spring/blob/master/src/main/resources/META-INF/spring.handlers) files. I'm not going to cover the details, the Spring documentation do a great job of this.


Mocks
---
A mock is a test double with behaviour we can control. Mocks are more versatile than dummies. Mockito is a great library for mocking, and the [implementation is actually marginally less complex](https://github.com/alexec/test-double-deps-with-spring/blob/master/src/main/java/com/alexecollins/testing/MockFactoryBean.java). This is best demonstrated in a unit test:

~~~java
    @Autowired
    Example example;

    @Test
    public void testExample() throws Exception {
        when(example.intValue()).thenReturn(1);
        assertEquals(1, example.intValue());
    }
~~~


Partial Fake
---
A fake is an implementation that takes "short-cuts", e.g. an in memory database. Some of the interfaces we rely on have a large number of methods, but we only use a minority of them. How can we create a class that implements a subset of the methods we need, and automatically return default (dummy) values for those we don't need? An abstract class will allow us to do that. Here's our interface:

~~~java
public interface Example {
    void voidValue();
    int intValue();
    long longValue();
    float floatValue();
    double doubleValue();
    byte byteValue();
    char charValue();
    short shortValue();
    boolean booleanValue();
    Object objectValue();
    Object[] arrayValue();
}
~~~ 

And here's our class:

~~~java
public abstract class PartialFakeExample implements Example {

    @Override
    public long longValue() {return 1l;}

    @Override
    public int intValue() {return 1;}
}
~~~

We have a problem here, JDK proxies can only subclass interfaces. But we can use CGLIB. We create [an enhancer](https://github.com/alexec/test-double-deps-with-spring/blob/master/src/main/java/com/alexecollins/testing/PartialFakeFactoryBean.java) that will call the method on the abstract class if it exists, or the default value if not.

~~~java
    private static Object newObject(final Class clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                try {
                    final Method clazzMethod = clazz.getMethod(method.getName(), method.getParameterTypes());
                    if (!Modifier.isAbstract(clazzMethod.getModifiers())) {
                        return proxy.invokeSuper(obj, args);
                    }
                } catch (NoSuchMethodException ignored) {
                }
                return Defaults.get(method.getReturnType());
            }
        });
        return enhancer.create();
    }
~~~

Again, we can create an XML namespace for a nice, tidy XML:

~~~xml
    <test:fake id="example1" class="com.alexecollins.testing.PartialFakeExample"/>        
~~~

The partial fake is the most versatile of the doubles here, with a fully defined set of methods, it becomes a normal fake, it can provide stubbed answers, or just defaults like a dummy. A dummy is a stub returning only default answers.


Stubs
---
To complete our set of doubles, we'll need stubs. Stubs return canned responses. This is similar to fakes, but unlike our fakes, they are not partial. One very good implementation is [a normal bean](https://github.com/alexec/test-double-deps-with-spring/blob/master/src/test/java/com/alexecollins/testing/StubExample.java), unlike any special XML, it's type-safe, easy to use and easy to understand. As a stub provides canned responses, we might expect it to have a no-args constructor, this suggests the following implementation for the factory:

~~~java
    public StubFactoryBean(Class clazz) throws Exception {
        super(clazz.newInstance());
    }
~~~


~~~xml
    <test:stub id="example" class="com.alexecollins.testing.StubExample"/>
~~~


Ignoring Missing Dependencies
---
A number of classes imported have more auto-wired fields than that methods we call depend on. We can modify the context to allow them to be non-mandatory, making our tests more robust to changes in those dependencies. This is a simple one:

~~~xml
        <bean class="org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor">
                <property name="requiredParameterValue" value="false" />
        </bean>
~~~

Test Lifecycle
---
Spring re-uses the context between tests. This means the beans become "dirty". You can use @DirtiesContext to tear-down and rebuild the context between test, but it's expensive and can lead to out-of-perm-gen problems. Instead, we want an annotation similar to JUnit's @Before annotation, which indicates that a method should be called before each test.

~~~java
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Before {
}
~~~

A class that stores those methods:

~~~java
public class DoubleManager {
    private static class Target {
        final Object o;
        final Method m;

        private Target(Object o, Method m) {
            this.o = o;
            this.m = m;
        }
    }

    private final List<Target> targets = new ArrayList<Target>();

    @Autowired
    public DoubleManager(Object[] objects) {
        for (Object o : objects) {
            for (Method m : o.getClass().getMethods()) {
                if (AnnotationUtils.findAnnotation(m, Before.class) != null) {
                    targets.add(new Target(o, m));
                }
            }
        }
    }

    public void setUp() throws Exception {
        for (Target target : targets) {
            target.m.invoke(target.o);
        }
    }
}
~~~

The the test double can use this to reset itself:

~~~java
public class ManagedExample implements Example {
    private int value;

    @Before
    public void setUp() {
        value = 1;
    }

    @Override
    public int intValue() {
        return value;
    }
    ...
~~~

This is best demonstrated by an example test:

~~~java
public class DoubleManagerTest {
    @Autowired
    DoubleManager doubleManager;
    @Autowired
    Example example;

    @org.junit.Before
    public void setUp() throws Exception {
        doubleManager.setUp();
    }
~~~

OK. So I hope you enjoyed this post, and you get some good ideas from it. The code can be [found on Github](https://github.com/alexec/test-double-deps-with-spring) as usual.

Want more like this post? Try [this post on test support for Tomcat and Spring](tutorial-junit-rule).
