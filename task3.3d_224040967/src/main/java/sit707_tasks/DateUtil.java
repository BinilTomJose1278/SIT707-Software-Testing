package sit707_tasks;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DateUtil {

    private int day, month, year;
    private LocalDateTime dateTime;
    private ZoneId timeZone;
    private BusinessCalendar businessCalendar;
    private DateTimeFormatter formatter;
    private WorkingHours workingHours;

    private static final HolidayProvider holidayProvider = new HolidayProvider();
    private static final Map<String, ZonedDateTime> CONVERSION_CACHE = new ConcurrentHashMap<>();
    private static final int MAX_CACHE_SIZE = 1000;

    private static final String[] MONTHS = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };

    private void initDefaults() {
        this.businessCalendar = new BusinessCalendar();
        this.workingHours = new WorkingHours();
        this.formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    }

    public DateUtil(int day, int month, int year) {
        validateLegacyDate(day, month, year);
        this.day = day;
        this.month = month;
        this.year = year;
        this.dateTime = LocalDateTime.of(year, month, day, 0, 0, 0);
        this.timeZone = ZoneId.systemDefault();
        initDefaults();
    }

    public DateUtil(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.day = dateTime.getDayOfMonth();
        this.month = dateTime.getMonthValue();
        this.year = dateTime.getYear();
        this.timeZone = ZoneId.systemDefault();
        initDefaults();
    }

    public DateUtil(LocalDateTime dateTime, ZoneId timeZone) {
        this(dateTime);
        this.timeZone = timeZone;
    }

    public DateUtil(String isoString) {
        try {
            this.dateTime = LocalDateTime.parse(isoString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            this.day = dateTime.getDayOfMonth();
            this.month = dateTime.getMonthValue();
            this.year = dateTime.getYear();
            this.timeZone = ZoneId.systemDefault();
            initDefaults();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid ISO date-time string: " + isoString, e);
        }
    }

    public DateUtil(String isoString, String timeZoneId) {
        this.timeZone = ZoneId.of(timeZoneId);
        this.dateTime = LocalDateTime.parse(isoString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        ZonedDateTime zoned = this.dateTime.atZone(this.timeZone);
        this.dateTime = zoned.withZoneSameInstant(this.timeZone).toLocalDateTime();
        this.day = dateTime.getDayOfMonth();
        this.month = dateTime.getMonthValue();
        this.year = dateTime.getYear();
        initDefaults();
    }

    public int getDay() { return day; }
    public int getMonth() { return month; }
    public int getYear() { return year; }

    public void increment() {
        this.dateTime = this.dateTime.plusDays(1);
        updateLegacyFields();
    }

    public void decrement() {
        this.dateTime = this.dateTime.minusDays(1);
        updateLegacyFields();
    }

    public DateUtil add(Duration duration) {
        return new DateUtil(this.dateTime.plus(duration), this.timeZone);
    }

    public DateUtil subtract(Duration duration) {
        return new DateUtil(this.dateTime.minus(duration), this.timeZone);
    }

    public DateUtil addMilliseconds(long milliseconds) {
        return new DateUtil(this.dateTime.plus(milliseconds, ChronoUnit.MILLIS), this.timeZone);
    }

    public DateUtil addSeconds(long seconds) {
        return new DateUtil(this.dateTime.plusSeconds(seconds), this.timeZone);
    }

    public DateUtil addMinutes(long minutes) {
        return new DateUtil(this.dateTime.plusMinutes(minutes), this.timeZone);
    }

    public DateUtil addHours(long hours) {
        return new DateUtil(this.dateTime.plusHours(hours), this.timeZone);
    }

    public DateUtil addDays(long days) {
        return new DateUtil(this.dateTime.plusDays(days), this.timeZone);
    }

    public DateUtil addBusinessDays(int days) {
        LocalDateTime current = this.dateTime;
        int remaining = Math.abs(days);
        int direction = days > 0 ? 1 : -1;
        while (remaining > 0) {
            current = current.plusDays(direction);
            if (isBusinessDay(current)) {
                remaining--;
            }
        }
        return new DateUtil(current, this.timeZone);
    }

    public DateUtil addBusinessHours(double hours) {
        LocalDateTime current = this.dateTime;
        double remaining = Math.abs(hours);
        int direction = hours > 0 ? 1 : -1;
        while (remaining > 0) {
            if (isBusinessHour(current)) {
                double hoursToAdd = Math.min(remaining, 1.0);
                current = current.plusMinutes((long)(hoursToAdd * 60 * direction));
                remaining -= hoursToAdd;
            } else {
                current = current.plusHours(direction);
            }
        }
        return new DateUtil(current, this.timeZone);
    }

    public DateUtil nextBusinessDay() {
        LocalDateTime next = this.dateTime.plusDays(1);
        while (!isBusinessDay(next)) {
            next = next.plusDays(1);
        }
        return new DateUtil(next, this.timeZone);
    }

    public DateUtil previousBusinessDay() {
        LocalDateTime prev = this.dateTime.minusDays(1);
        while (!isBusinessDay(prev)) {
            prev = prev.minusDays(1);
        }
        return new DateUtil(prev, this.timeZone);
    }

    public List<DateUtil> getBusinessDaysInRange(DateUtil end) {
        List<DateUtil> businessDays = new ArrayList<>();
        LocalDateTime current = this.dateTime;
        LocalDateTime endDateTime = end.dateTime;
        while (!current.isAfter(endDateTime)) {
            if (isBusinessDay(current)) {
                businessDays.add(new DateUtil(current, this.timeZone));
            }
            current = current.plusDays(1);
        }
        return businessDays;
    }

    public String format(DateTimePattern pattern) {
        return this.dateTime.format(pattern.getFormatter());
    }

    public String format(String customPattern) {
        return this.dateTime.format(DateTimeFormatter.ofPattern(customPattern));
    }

    public DateUtil convertToTimeZone(ZoneId targetZone) {
        ZonedDateTime zonedDateTime = this.dateTime.atZone(this.timeZone);
        ZonedDateTime converted = zonedDateTime.withZoneSameInstant(targetZone);
        return new DateUtil(converted.toLocalDateTime(), targetZone);
    }

    public long toEpochMilli() {
        return this.dateTime.atZone(this.timeZone).toInstant().toEpochMilli();
    }

    public Instant toInstant() {
        return this.dateTime.atZone(this.timeZone).toInstant();
    }

    public String toISOString() {
        return this.dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public boolean isValid() {
        try {
            if (this.year < 1700 || this.year > 2100) return false;
            if (this.month < 1 || this.month > 12) return false;
            if (this.day < 1 || this.day > monthDuration(this.month, this.year)) return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ValidationResult validateForContext(BusinessContext context) {
        ValidationResult result = new ValidationResult();
        if (!isValid()) {
            result.addError("Invalid date-time format");
            return result;
        }
        switch (context) {
            case APPOINTMENT_SCHEDULING:
                if (!isBusinessHour()) {
                    result.addWarning("Appointment scheduled outside business hours");
                }
                if (isHoliday()) {
                    result.addError("Cannot schedule appointment on holiday");
                }
                break;
            case PAYMENT_PROCESSING:
                if (!isWorkingDay()) {
                    result.addWarning("Payment processing may be delayed on non-business day");
                }
                break;
            case DEADLINE_MANAGEMENT:
                if (this.dateTime.isBefore(LocalDateTime.now())) {
                    result.addError("Deadline cannot be in the past");
                }
                break;
        }
        return result;
    }

    public int compareTo(DateUtil other) {
        return this.dateTime.compareTo(other.dateTime);
    }

    public Duration between(DateUtil other) {
        return Duration.between(this.dateTime, other.dateTime);
    }

    public boolean isBusinessHour() {
        return isBusinessHour(this.dateTime);
    }

    public boolean isWorkingDay() {
        return isBusinessDay(this.dateTime);
    }

    public boolean isHoliday() {
        return holidayProvider.isHoliday(this.dateTime.toLocalDate());
    }

    public int getHour() { return this.dateTime.getHour(); }
    public int getMinute() { return this.dateTime.getMinute(); }
    public int getSecond() { return this.dateTime.getSecond(); }
    public DayOfWeek getDayOfWeek() { return this.dateTime.getDayOfWeek(); }
    public LocalDateTime getDateTime() { return this.dateTime; }
    public ZoneId getTimeZone() { return this.timeZone; }

    private void validateLegacyDate(int day, int month, int year) {
        if (day < 1 || day > 31) throw new RuntimeException("Invalid day: " + day);
        if (month < 1 || month > 12) throw new RuntimeException("Invalid month: " + month);
        if (year < 1700 || year > 2024) throw new RuntimeException("Invalid year: " + year);
        if (day > monthDuration(month, year)) throw new RuntimeException("Invalid day for month");
    }

    private void updateLegacyFields() {
        this.day = this.dateTime.getDayOfMonth();
        this.month = this.dateTime.getMonthValue();
        this.year = this.dateTime.getYear();
    }

    private boolean isBusinessDay(LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY && !holidayProvider.isHoliday(dateTime.toLocalDate());
    }

    private boolean isBusinessHour(LocalDateTime dateTime) {
        int hour = dateTime.getHour();
        return workingHours.isBusinessHour(hour) && isBusinessDay(dateTime);
    }

    public static int monthDuration(int month, int year) {
        if (month == 2 && isLeapYear(year)) return 29;
        if (month == 2) return 28;
        if (Arrays.asList(4, 6, 9, 11).contains(month)) return 30;
        return 31;
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    @Override
    public String toString() {
        return day + " " + MONTHS[month - 1] + " " + year + " " + 
               String.format("%02d:%02d:%02d", dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DateUtil dateUtil = (DateUtil) obj;
        return Objects.equals(dateTime, dateUtil.dateTime) && Objects.equals(timeZone, dateUtil.timeZone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, timeZone);
    }

    public static class BusinessCalendar {
        private Set<LocalDate> holidays = new HashSet<>();
        public BusinessCalendar() {
            initializeStandardHolidays();
        }
        private void initializeStandardHolidays() {
            int currentYear = LocalDate.now().getYear();
            holidays.add(LocalDate.of(currentYear, 1, 1));
            holidays.add(LocalDate.of(currentYear, 12, 25));
        }
        public void addHoliday(LocalDate date) {
            holidays.add(date);
        }
        public boolean isHoliday(LocalDate date) {
            return holidays.contains(date);
        }
    }

    public static class WorkingHours {
        private int startHour = 9;
        private int endHour = 17;
        private int lunchStart = 12;
        private int lunchEnd = 13;
        public boolean isBusinessHour(int hour) {
            return hour >= startHour && hour < endHour && !(hour >= lunchStart && hour < lunchEnd);
        }
        public void setWorkingHours(int start, int end) {
            this.startHour = start;
            this.endHour = end;
        }
    }

    public static class HolidayProvider {
        private Map<LocalDate, String> holidays = new HashMap<>();
        public HolidayProvider() {
            initializeHolidays();
        }
        private void initializeHolidays() {
            for (int year = 2024; year <= 2025; year++) {
                holidays.put(LocalDate.of(year, 1, 1), "New Year's Day");
                holidays.put(LocalDate.of(year, 12, 25), "Christmas Day");
                holidays.put(LocalDate.of(year, 7, 4), "Independence Day");
            }
        }

        public boolean isHoliday(LocalDate date) {
            return holidays.containsKey(date);
        }
        public String getHolidayName(LocalDate date) {
            return holidays.get(date);
        }
    }

    public enum DateTimePattern {
        ISO_8601(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        US_DATETIME(DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a")),
        EUROPEAN_DATETIME(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
        ISO_DATE_ONLY(DateTimeFormatter.ISO_LOCAL_DATE),
        TIME_ONLY(DateTimeFormatter.ofPattern("HH:mm:ss"));

        private final DateTimeFormatter formatter;
        DateTimePattern(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }
        public DateTimeFormatter getFormatter() {
            return formatter;
        }
    }

    public enum BusinessContext {
        APPOINTMENT_SCHEDULING,
        PAYMENT_PROCESSING,
        DEADLINE_MANAGEMENT,
        GENERAL
    }

    public static class ValidationResult {
        private List<String> errors = new ArrayList<>();
        private List<String> warnings = new ArrayList<>();
        public void addError(String error) {
            errors.add(error);
        }
        public void addWarning(String warning) {
            warnings.add(warning);
        }
        public boolean isValid() {
            return errors.isEmpty();
        }
        public List<String> getErrors() {
            return new ArrayList<>(errors);
        }
        public List<String> getWarnings() {
            return new ArrayList<>(warnings);
        }
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (!errors.isEmpty()) {
                sb.append("Errors: ").append(String.join(", ", errors));
            }
            if (!warnings.isEmpty()) {
                if (sb.length() > 0) sb.append("; ");
                sb.append("Warnings: ").append(String.join(", ", warnings));
            }
            return sb.toString();
        }
    }
}
