import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;



public class ConvexHull extends Application {

    private static ArrayList<Double> xPoints = new ArrayList<>();
    private static ArrayList<Double> yPoints = new ArrayList<>();
    static int maxPoints = 70;
    static double[] xVal = new double[maxPoints];
    static double[] yVal = new double[maxPoints];

    public static void main(String[] args){

        int pointCount = fillPoints(xVal, yVal);
        for(int i = 0; i < pointCount; i++){
            if(pointCount <= 70) {
                if (checkDuplicates(pointCount, xVal, yVal)) {
                    return;
                } else {
                    System.out.println("(xVal[" + xVal[i] + "], yVal[" + yVal[i] + "])");
                }
            } else {
                System.out.println("Maximum Capacity has been reached.");
            }
        }

        computeConvexHull(pointCount,xVal,yVal);

        System.out.println(xPoints);
        System.out.println(yPoints);

        System.out.println(yPoints.size());


        launch(args);

    }

    //loadPoints should read values from the input line alternately into xVal
    //and yVal until a negat
    // ive value is read or until greater than 2∗maxPoints
    //are read. In the latter case, print out a message warning the user that
    //the maximum capacity of the array has been reached.

   /* public static int loadPoints(double[] xVal, double[] yVal, int maxPoints){
        Scanner scan = new Scanner(System.in);
        int i;
        int noOfInputs = 0;
        boolean isValid = false;
        for(i = 0; i < maxPoints; i++){
            System.out.print("Value of x: ");
            xVal[i] = scan.nextDouble();
            if(xVal[i] < 0){
                xVal[i] = 0;
                isValid = false;
                break;
            } else {
                isValid = true;
            }

            System.out.print("Value of y: ");
            yVal[i] = scan.nextDouble();
            if(yVal[i] < 0){
                isValid = false;
                break;
            } else {
                isValid = true;
            }

            if(isValid){
                noOfInputs++;
            }

            if(noOfInputs > maxPoints){
                System.out.println("Array capacity has been reached");
            }

        }

        int numPoints = noOfInputs;
        System.out.println(numPoints);
        return numPoints;



    }*/
   private static int fillPoints(double[] xVal, double[] yVal) {
       int total = 0;
       xVal[total] = 1;
       yVal[total] = 1;
       total++;//1
       xVal[total] = 1;
       yVal[total] = 2;
       total++;//2
       xVal[total] = 1;
       yVal[total] = 3;
       total++;//3
       xVal[total] = 2;
       yVal[total] = 2;
       total++;//4
       xVal[total] = 2;
       yVal[total] = 3;
       total++;//5
       xVal[total] = 2;
       yVal[total] = 5;
       total++;//6
       xVal[total] = 3;
       yVal[total] = 1;
       total++;//7
       xVal[total] = 3;
       yVal[total] = 3;
       total++;//8
       return total;
   }

    public static boolean checkDuplicates(int pointCount, double[] xVal, double[] yVal){
        for(int i = pointCount -1 ; i >=1; i--){
            for(int j = 0; j < pointCount; j++){
                if(i != j){
                    if(xVal[i] == xVal[j] && yVal[i] == yVal[j]){
                        System.out.println("Duplicate Found!");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void computeConvexHull(int pointCount, double[] xVal, double[] yVal){


        double m,c;

        for(int i = 0; i < pointCount; i++) {
            for (int j = i + 1; j < pointCount; j++) {
                int above = 0, below = 0;
                m = (yVal[i] - yVal[j]) / (xVal[i] - xVal[j]);
                c = yVal[i] - (m * xVal[i]);
                allowBreak:
                for(int k = 0; k < pointCount; k++){
                    if (Double.isInfinite(m)/*<-- does the same(m == Double.POSITIVE_INFINITY | m == Double.NEGATIVE_INFINITY)*/) {
                        if (xVal[k] > xVal[i]) {
                            above++;
                        } else if (xVal[k] < xVal[i]) {
                            below++;
                        }

                    } else if(m == 0){
                        if (yVal[k] > yVal[i]) {
                            above++;
                        } else if (yVal[k] < yVal[i]) {
                            below++;
                        }
                    } else {
                        if (yVal[k] > (m * xVal[k] + c)) {
                            above++;
                        } else if (yVal[k] < (m * xVal[k] + c)) {
                            below++;
                        }
                    }
                }
                if ((above > 1 && below == 0)||(below > 1 && above == 0)) {
                    System.out.println("xVal[" + xVal[i] + "], yVal[" + yVal[i] + "] is on the Convex Hull");
                    System.out.println("xVal[" + xVal[j] + "], yVal[" + yVal[j] + "] is on the Convex Hull");
                    xPoints.add(xVal[i]);
                    yPoints.add(yVal[i]);
                    xPoints.add(xVal[i]);
                    yPoints.add(yVal[i]);
                }


            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Convex Hull");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<>(xAxis,yAxis);
        lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Convex Hull");

        for(int i=0; i < yPoints.size(); i++){
            series.getData().add(new XYChart.Data(xPoints.toArray()[i],yPoints.toArray()[i]));
        }

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);


        stage.setScene(scene);
        stage.show();

    }
}
