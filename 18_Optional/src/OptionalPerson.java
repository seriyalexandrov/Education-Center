import java.util.Optional;

class OptionalPerson {
    private Address address;

    public OptionalPerson(Address address) {
        this.address = address;
    }

    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }
}