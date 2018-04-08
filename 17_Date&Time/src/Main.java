import java.time.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Time in system time zone: " + Clock.systemDefaultZone().millis());

//        System.out.println("Print available zones: "+ ZoneId.getAvailableZoneIds());

        System.out.println("Time in current time zone: " + LocalTime.now());

        System.out.println("Time in London time zone: " + LocalTime.now(Clock.system(ZoneId.of("Europe/London"))));
        
        System.out.println("New year week day: " + LocalDate.of(2018, Month.JANUARY, 1).getDayOfWeek());
    }
}
