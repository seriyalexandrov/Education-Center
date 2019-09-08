public class Executor {

    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.addItem(1);
        System.out.println(cart.total());
    }
}
