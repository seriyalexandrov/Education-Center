import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CartTest {

    Cart cart;

    @Before
    public void setUp() {
        cart = new Cart();
    }

    @Test
    public void testTotalPrice() {
        cart.addItem(1.0);
        cart.addItem(10);
        assertEquals(11, cart.total(), 0.1);
    }
}
