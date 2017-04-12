/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacorrelation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class JavaCorrelation {

    public static void main(String[] args) {

        //In aceasta faza datele calendaristice nu sunt relevante, dar ne vom 
        //folosi de aceste variabile ulterior, cand va trebui sa extragem datele 
        //care se incadreaza doar in intervalul specificat de utilizator.
        Date date1 = new Date(2017,01,12);
        Date date2 = new Date(2017,02,12);
        DateNeeded d1 = new DateNeeded();
        Date startDate = d1.getDate();
        
        DateNeeded d2 = new DateNeeded();
        Date endDate = d2.getDate();
        
        //In acest moment, corelarea se face doar intre datele de proba din vectorul 
        //valSimbol1 si valSimbol2, urmand ca mai apoi prin conexiunea la baza de date 
        //si crearea de interogari necesare sa se testeze corelatia pe date reale.
        
        int[] valSimbol1={1,2,3};
        int[] valSimbol2 = {3,2,1};
   double res =  PearsonCorrelation.Correlation(valSimbol1, valSimbol2);
           System.out.println("The result is " + res);
                
            }
        
    }


    

