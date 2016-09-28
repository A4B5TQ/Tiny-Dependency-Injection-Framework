package dependencyInjectionFramework;

import dependencyInjectionFramework.injection.Injector;
import dependencyInjectionFramework.module.Module;

public class Dependency {
    public static Injector getInjector(Module module) {
        module.configure();
        return new Injector(module);
    }
}
