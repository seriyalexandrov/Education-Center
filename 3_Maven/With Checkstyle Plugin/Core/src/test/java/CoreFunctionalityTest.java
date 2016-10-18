import org.junit.Before;
import org.junit.Test;

/**
 * Created by Ser on 18/10/2016.
 */
public class CoreFunctionalityTest {

    Functionality functionality;

    @Before
    public void setUp() {
        functionality = new CoreFunctionality();
    }

    @Test
    public void testDoSomething() {
        functionality.doSomething();
    }
}
