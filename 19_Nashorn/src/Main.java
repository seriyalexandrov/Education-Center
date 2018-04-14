import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("nashorn");
        try {
            engine.eval(
                    new String(
                            Files.readAllBytes(Paths.get("src/jsexpression.js"))
                    )
            );
        } catch (ScriptException | IOException se) {
            throw new RuntimeException(se);
        }
    }
}
