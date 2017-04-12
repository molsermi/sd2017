package javacorrelation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class DateNeeded {
     
//Aceasta metoda este necesara pentru a prelua intervalul de timp in care se doreste a se analiza
//legatura dintre simboluri. Eventual il putem inlocui cu un DatePicker.
    public Date getDate(){

    Scanner scanner = new Scanner(System.in);
    String str[] = {"year", "month", "day" };
    String date = "";

    for(int i=0; i<3; i++) {
        System.out.println("Enter " + str[i] + ": ");
        date = date + scanner.next() + "/";
    }
    date = date.substring(0, date.length()-1);
    System.out.println("Date: "+ date); 

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    Date parsedDate = null;

    try {
        parsedDate = dateFormat.parse(date);
    } catch (ParseException e) {
        e.printStackTrace();
    }
    return parsedDate;    
}
}
