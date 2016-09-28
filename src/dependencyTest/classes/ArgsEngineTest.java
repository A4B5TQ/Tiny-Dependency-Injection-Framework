package dependencyTest.classes;

import dependencyInjectionFramework.injection.Inject;
import dependencyTest.interfaces.Engine;

public class ArgsEngineTest {

    private Engine engine;

    @Inject
    public ArgsEngineTest(Engine engine) {
        this.engine = engine;
    }

    public Engine getEngine() {
        return engine;
    }
}
