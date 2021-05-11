package com.spiwer.androidrosilla.proxy;

import java.lang.reflect.Proxy;

@SuppressWarnings("unchecked")
public class ProxyRosilla {
    public static <T extends Object> T create(Object instance, Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{interfaceClass},
                new ProxyHandlerUtil(instance));
    }
}
