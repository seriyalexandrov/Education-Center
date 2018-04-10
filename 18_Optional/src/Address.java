import java.util.Optional;

class Address {

    private Street street;

    public Address(Street street) {
        this.street = street;
    }

    public Optional<Street> getStreet() {
        return Optional.ofNullable(street);
    }
}