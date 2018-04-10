import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        Person person = getPerson();

        String streetName = null;

        if (person != null) {
            Person.Address personAddress = person.getAddress();
            if (personAddress != null) {
                Person.Address.Street street = personAddress.getStreet();
                if(street != null) {
                    streetName = street.getStreetName();
                } else {
                    streetName = "EMPTY";
                }
            }
        }

        System.out.println(streetName);
    }

    public static Person getPerson() {
        return new Person(new Person.Address(new Person.Address.Street("Kozhevnicheskaya")));
    }

    private static class Person {
        private Address address;

        public Person(Address address) {
            this.address = address;
        }

        public Address getAddress() {
            return address;
        }

        private static class Address {

            private Street street;

            public Address(Street street) {
                this.street = street;
            }

            public Street getStreet() {
                return street;
            }

            private static class Street {

                private String streetName;

                public Street(String streetName) {
                    this.streetName = streetName;
                }

                public String getStreetName() {
                    return streetName;
                }
            }
        }
    }
}
