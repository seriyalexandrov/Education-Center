import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Double> prices = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(Cart.class);

    public void addItem(double price) {
        prices.add(price);
        log.info("Add item: {}", price);
    }

    public Double total() {
        Double total = 0.0;
        for (Double d : prices) {
            total += d;
        }
        return total;
    }
}
