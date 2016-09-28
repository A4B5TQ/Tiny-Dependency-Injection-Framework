package dependencyInjectionFramework.module;

public interface Module {

    void configure();

    <T> Class<? extends T> getMapping(Class<T> type);

    Object getInstance(Class<?> classAbstraction);

    void setInstance(Class<?> classAbstraction, Object instance);
}
