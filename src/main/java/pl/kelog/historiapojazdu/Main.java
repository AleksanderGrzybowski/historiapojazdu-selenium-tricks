package pl.kelog.historiapojazdu;

import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.*;

class Main {
    
    // Configure here:
    public static final String PLATE_NUMBER = "SCI12345";
    public static final String VIN_NUMBER = "VF1BR000H00000000";
    static LocalDate START_DATE = LocalDate.of(2012, Month.JANUARY, 1);
    static LocalDate END_DATE = LocalDate.of(2013, Month.JANUARY, 1);
    
    static final String BASE_URL = "https://historiapojazdu.gov.pl/";
    static final String SUCCESS_TEXT = "Dane techniczne";
    static final String NOT_FOUND_TEXT = "Niestety";
    static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy");
    static Logger log = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        for (LocalDate date = START_DATE; date.isBefore(END_DATE); date = date.plusDays(1)) {
            log.info("Checking " + date + "...");
            
            if (checkDate(date)) {
                log.info("Found match! " + date);
                System.exit(0);
            } else {
                log.info("No match.");
            }
        }
    }
    
    static boolean checkDate(LocalDate date) {
        String formattedDate = FORMATTER.format(date);
        
        // reset the current page, just in case
        open("about:blank");
        sleep(5000);
        
        // open main page
        open(BASE_URL);
        sleep(1000);
        
        // fill plate number
        $("body").sendKeys(Keys.chord(Keys.TAB));
        sleep(100);
        switchTo().activeElement().sendKeys(PLATE_NUMBER);
        sleep(100);
        
        // fill VIN number
        $("body").sendKeys(Keys.chord(Keys.TAB));
        sleep(100);
        switchTo().activeElement().sendKeys(VIN_NUMBER);
        sleep(100);
        
        // fill registration date
        $("body").sendKeys(Keys.chord(Keys.TAB));
        sleep(100);
        switchTo().activeElement().sendKeys(formattedDate);
        
        // send the request
        $("input[type='submit']").click();
        sleep(1000);
    
        String bodyText = $("body").text();
        if (bodyText.contains(SUCCESS_TEXT)) {
            return true;
        }
        if (bodyText.contains(NOT_FOUND_TEXT)) {
            return false;
        }
        
        throw new AssertionError("Something went wrong, unexpected page content");
    }
}
