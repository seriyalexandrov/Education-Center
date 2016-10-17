import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ser on 18/10/2016.
 */
public class CoreFunctionality implements Functionality {

    private static final Logger log = LoggerFactory.getLogger(CoreFunctionality.class);

    public void doSomething() {
        log.info("Executed.");
    }
}
