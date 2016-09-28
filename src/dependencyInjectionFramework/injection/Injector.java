package dependencyInjectionFramework.injection;

import dependencyInjectionFramework.module.Module;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Injector {

    private Module module;

    public Injector(Module module) {
        this.module = module;
    }


    public <T> T inject(Class<T> desireClass) {

        Field[] allFields = desireClass.getDeclaredFields();

        Constructor<?>[] allConstructors = desireClass.getConstructors();

        boolean hasFieldInjection = this.checkForFieldInjection(allFields);

        boolean hasConstructorInjection = this.checkForConstructorInjection(allConstructors);

        if (hasConstructorInjection && hasFieldInjection) {
            throw new IllegalStateException("There must have only fields or constructors annotated with @Inject annotation");
        }

        if (hasConstructorInjection) {
            try {

                return desireClass.cast(this.injectConstructor(desireClass));

            } catch (InvocationTargetException |
                    NoSuchMethodException |
                    InstantiationException |
                    IllegalAccessException e) {

                e.printStackTrace();
            }
        } else if (hasFieldInjection) {
            try {

                return desireClass.cast(this.injectFields(desireClass));

            } catch (NoSuchMethodException |
                    IllegalAccessException |
                    InvocationTargetException |
                    InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalStateException("There is no present types annotated with @Inject annotation");
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private Object injectConstructor(Class desireClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        if (desireClass != null) {

            for (Constructor constructor : desireClass.getConstructors()) {
                if (constructor.isAnnotationPresent(Inject.class)) {

                    Class[] parameterTypes = constructor.getParameterTypes();
                    Object[] objArr = new Object[parameterTypes.length];

                    int i = 0;

                    for (Class currentClass : parameterTypes) {
                        Class dependency = this.module.getMapping(currentClass);
                        this.inject(dependency);
                        if (currentClass.isAssignableFrom(dependency)) {
                            Object instance = this.module.getInstance(dependency);
                            if (instance != null) {
                                objArr[i++] = instance;
                            } else {
                                instance = dependency.getConstructor().newInstance();
                                objArr[i++] = instance;
                                this.module.setInstance(currentClass, instance);
                            }
                        }
                    }

                    return desireClass.getConstructor(parameterTypes).newInstance(objArr);
                }
            }
        }
        return desireClass;
    }

    @SuppressWarnings("unchecked")
    private Object injectFields(Class desireClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object desireClassInstance = this.module.getInstance(desireClass);
        if (desireClassInstance == null) {
            desireClassInstance = desireClass.newInstance();
            this.module.setInstance(desireClass, desireClassInstance);
        }

        Field[] fieldsToSet = desireClass.getDeclaredFields();
        for (Field field : fieldsToSet) {
            if (!field.isAnnotationPresent(Inject.class)) {
                continue;
            }

            field.setAccessible(true);
            Class fieldType = field.getType();
            Class dependency = this.module.getMapping(fieldType);
            Class objectInstance = null;
            if (fieldType.isAssignableFrom(dependency)) {
                objectInstance = this.module.getMapping(fieldType);
            }
            Object dependencyClassInstance = objectInstance.getConstructor().newInstance();

            field.set(desireClassInstance, dependencyClassInstance);
        }
        return desireClassInstance;
    }

    private boolean checkForConstructorInjection(Constructor<?>[] allConstructors) {

        for (Constructor<?> allConstructor : allConstructors) {
            if (allConstructor.isAnnotationPresent(Inject.class)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkForFieldInjection(Field[] allFields) {

        for (Field allField : allFields) {
            if (allField.isAnnotationPresent(Inject.class)) {
                return true;
            }
        }
        return false;
    }
}
