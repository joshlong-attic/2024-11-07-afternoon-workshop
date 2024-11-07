package smart;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.util.concurrent.atomic.AtomicBoolean;

class MyBeanPostProcessor implements BeanPostProcessor {


    boolean isLogged(Object o) {
        var aClass = o.getClass();
        var found = new AtomicBoolean(aClass.isAnnotationPresent(Logged.class));
        ReflectionUtils.doWithMethods(aClass, m -> {
            if (m.isAnnotationPresent(Logged.class)) {
                found.set(true);
            }
        });
        return found.get();
    }

    private Object proxyFor(Object o) {
        var pfb = new ProxyFactoryBean();
        pfb.setTarget(o);
        pfb.setProxyTargetClass(true);
        pfb.addAdvice((MethodInterceptor) invocation -> {
            System.out.println("before!! [" + invocation.getMethod().getName() + "]");
            try {
                return invocation.proceed();
            } finally {
                System.out.println("after!! [" + invocation.getMethod().getName() + "]");
            }

        });
        return pfb.getObject();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (isLogged(bean)) {
            return proxyFor(bean);
        }

        return bean;
    }
}
