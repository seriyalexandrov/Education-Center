import java.util.Optional;

class Street {

    private String streetName;

    public Street(String streetName) {
        this.streetName = streetName;
    }

    public Optional<String> getStreetName() {
        return Optional.ofNullable(streetName);
    }
}