package data;

import org.testng.annotations.DataProvider;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestDataProvider {

    @DataProvider(name = "bookingData")
    public Object[][] provideBookingData(){
        Map<String, Object> bookingDates1 = new LinkedHashMap<>();
        bookingDates1.put("checkin", "2025-06-24");
        bookingDates1.put("checkout", "2025-07-24");

        Map<String, Object> booking1 = new LinkedHashMap<>();
        booking1.put("firstname", "Juliana");
        booking1.put("lastname", "Velasquez");
        booking1.put("totalprice", 111);
        booking1.put("depositpaid", true);
        booking1.put("bookingdates", bookingDates1);
        booking1.put("aditionalneeds", "Breakfast");

        Map<String, Object> bookingDates2 = new LinkedHashMap<>();
        bookingDates2.put("checkin", "2025-08-24");
        bookingDates2.put("checkout", "2025-09-24");

        Map<String, Object> booking2 = new LinkedHashMap<>();
        booking2.put("firstname", "Deicy");
        booking2.put("lastname", "Estrada");
        booking2.put("totalprice", 122);
        booking2.put("depositpaid", true);
        booking2.put("bookingdates", bookingDates2);
        booking2.put("aditionalneeds", "Breakfast");

        return  new Object[][] {
                {booking1},
                {booking2}
        };
    }
}
