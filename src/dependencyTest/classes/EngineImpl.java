package dependencyTest.classes;

import dependencyInjectionFramework.injection.Inject;
import dependencyTest.interfaces.Engine;
import dependencyTest.interfaces.Reader;
import dependencyTest.interfaces.Writer;

public class EngineImpl implements Engine {

    @Inject
    private Writer writer;

    @Inject
    private Reader reader;

    public EngineImpl() {
    }

    public EngineImpl(ArgsEngineTest test) {
    }

    public void doSomething(){
        this.writer.write(this.reader.read());
    }
}
