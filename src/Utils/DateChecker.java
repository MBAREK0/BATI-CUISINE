package Utils;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DateChecker {



    public static boolean isValidPeriod(LocalDate startDate, LocalDate endDate) {
        // Implement your logic to check if the period is valid
        return startDate != null && endDate != null && !startDate.isAfter(endDate);
    }

}
