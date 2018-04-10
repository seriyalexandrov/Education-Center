import java.util.Optional;

public class OptionalMain {

    public static void main(String[] args) {
        Optional<OptionalPerson> optionalPerson = getOptionalPerson();

        String streetName = optionalPerson
                .flatMap(OptionalPerson::getAddress)
                .flatMap(Address::getStreet)
                .flatMap(Street::getStreetName)
                .orElse("EMPTY");

        System.out.println(streetName);
    }

    public static Optional<OptionalPerson> getOptionalPerson() {
        return Optional.ofNullable(new OptionalPerson(new Address(new Street("Kozhevnicheskaya"))));
    }
}
