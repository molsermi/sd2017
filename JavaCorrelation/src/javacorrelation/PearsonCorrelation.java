package javacorrelation;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

//Coeficientul Pearson este folosit in statistica pentru a determina tipul legatura 
//intre diverse variabile(in cazul analizat, stabileste tipul legaturii intre doua titluri de pe aceeasi
//piata si, de asemenea, tipul legaturii dintre un titlu pe diverse piete).
public class PearsonCorrelation {

    
    public static double Correlation(int[] xValues, int[] yValues) {
    
    //Vectorii xValues si yValues sunt utilizati in scopul stocarii valorilor titlurilor pe piata. 
    // Variabila xSum face referire la suma tuturor valorilor din vectorul xValues si, respectiv, yValues;
    // ySquareSum si YSquareSum sunt variabli care retin valorile sumelor xSum si YSum la patrat, care 
    //ulterior vor fi folosite in cadrul formulei de calcul al coeficientului Pearson. In acelasi scop a 
    //fost declarata si variabila xySum (rezultatul produsului sumei xSum si ySum).
    
    double xSum = 0.0;
    double ySum = 0.0;
    double xSquareSum = 0.0;
    double ySquareSum = 0.0;
    double xySum = 0.0;

    
    int n = xValues.length;

    for(int i = 0; i < n; ++i) {
      double x = xValues[i];
      double y = yValues[i];

      xSum += x;
      ySum += y;
      xSquareSum += x * x;
      ySquareSum += y * y;
      xySum += x * y;
    }

    // Pentru a determina coeficientul Pearson, prima data vom calcula covarianta.
    double cov = xySum / n - xSum * ySum / n / n;
    
    // Pasul doi presupune determinarea abaterii standard (sigma) pentru valorile x si y.
    double sigmax = Math.sqrt(xSquareSum / n -  xSum * xSum / n / n);
    double sigmay = Math.sqrt(ySquareSum / n -  ySum * ySum / n / n);

    // Iar valoarea returnata nu este altceva decat coeficientul Pearson.
    return cov / sigmax / sigmay;
  } 
}
