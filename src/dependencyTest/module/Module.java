package dependencyTest.module;

import dependencyInjectionFramework.module.AbstractModule;
import dependencyTest.classes.ConsoleReader;
import dependencyTest.classes.ConsoleWriter;
import dependencyTest.classes.EngineImpl;
import dependencyTest.interfaces.Engine;
import dependencyTest.interfaces.Reader;
import dependencyTest.interfaces.Writer;

public class Module extends AbstractModule {

    @Override
    public void configure() {
        createMapping(Writer.class, ConsoleWriter.class);
        createMapping(Reader.class, ConsoleReader.class);
        createMapping(Engine.class, EngineImpl.class);
    }
}
