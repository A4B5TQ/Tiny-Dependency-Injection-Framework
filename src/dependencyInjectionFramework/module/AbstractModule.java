package dependencyInjectionFramework.module;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractModule implements Module {

    private Map<Class<?>, Class<?>> classMap = new HashMap<>();
    private Map<Class<?>, Object> instancesMap = new HashMap<>();

    @Override
    public abstract void configure();

    @Override
    public   <T> Class<? extends T> getMapping(Class<T> type) {
        Class<?> implementation = this.classMap.get(type);

        if (implementation == null) {

            throw new IllegalArgumentException("Couldn't find the mapping for : " + type);
        }

        return implementation.asSubclass(type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object getInstance(Class<?> classAbstraction) {
       return this.instancesMap.get(classAbstraction);
    }

    @Override
    public void setInstance(Class<?> classAbstraction, Object instance) {
        this.instancesMap.put(classAbstraction,instance);
    }

    protected <T> void createMapping(Class<T> baseClass, Class<? extends T> subClass) {
        this.classMap.put(baseClass, subClass.asSubclass(baseClass));
    }
}
