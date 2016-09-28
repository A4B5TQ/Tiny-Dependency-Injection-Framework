import dependencyInjectionFramework.Dependency;
import dependencyInjectionFramework.injection.Injector;
import dependencyTest.classes.ArgsEngineTest;
import dependencyTest.classes.EngineImpl;
import dependencyTest.interfaces.Engine;
import dependencyTest.module.Module;

public class Main {
    public static void main(String[] args) {

        Injector injector = Dependency.getInjector(new Module());

        Engine engine = injector.inject(EngineImpl.class);
        engine.doSomething();

        ArgsEngineTest engineTest = injector.inject(ArgsEngineTest.class);
        engineTest.getEngine().doSomething();

        System.out.println(engine.toString().equals(engineTest.getEngine().toString()));
    }
}
