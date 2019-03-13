/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualize_bsa;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 *
 * @author book
 */
public class FXMLDocumentController implements Initializable {

    @FXML
  //   private LineChart<String, Double> linechart;
  //  XYChart.Series<String, Double> series;
    private LineChart<String, Double> linechart;
    XYChart.Series<String, Double> series;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        series = new XYChart.Series<>();
        series.setName("Fitness Evolution");

      //  series = new XYChart.Series<>();
       // series.setName("Fitness Evolution");
        
        linechart.getData().add(series);
      //  linechart.getData().add(series);
        Thread th = new Thread(new FitnessReader());
        th.start();
    }

    class FitnessReader implements Runnable {

        @Override
        public void run() {

            int FuncNum = 0;
            int x = 0;
            Algorithm_BSA s = new Algorithm_BSA();
            Algorithm_BSA s2 = new Algorithm_BSA();  
            Algorithm_BSA s3 = new Algorithm_BSA();  
            Algorithm_BSA s4 = new Algorithm_BSA();  
                s.Initialize(1);
                s2.Initialize(2);
                s3.Initialize(3);
                s4.Initialize(4);
                while (x <= s2.Maxcycle) {
                    switch (FuncNum) {
                        case 0: {
                            s.Selection_I();
                            s.Selection_II();
                            //series.getData().add(new XYChart.Data<>(x + "", s.Globalminimum));
                            s2.Selection_I();
                            s2.Selection_II();
                        // series2.getData().add(new XYChart.Data<>(x + "", s2.Globalminimum));
                            s3.Selection_I();
                            s3.Selection_II();
                        // series2.getData().add(new XYChart.Data<>(x + "", s3.Globalminimum));
                            s4.Selection_I();
                            s4.Selection_II();
                         series.getData().add(new XYChart.Data<>(x + "", s.Globalminimum));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                            break;
                        }
                    }
                    
                     x++;
                }
              
                
           System.out.println("\n Population \n");

            for (int i = 0; i < s.RowLength; i++) {
                for (int j = 0; j < s.ColumnLength; j++) {
                    System.out.print(s3.Population.get(i).get(j) + " , ");

                }
                System.out.println();
            }

            System.out.println("\n \n Old Population \n");

            for (int i = 0; i < s.RowLength; i++) {
                for (int j = 0; j < s.ColumnLength; j++) {
                    System.out.print(s.OldPopulation.get(i).get(j) + " , ");
                }
                System.out.println();
            }

            System.out.println("\n \n Mutation Matrix \n");

            for (int i = 0; i < s.RowLength; i++) {
                for (int j = 0; j < s.ColumnLength; j++) {
                    System.out.print(s.Mutant.get(i).get(j) + " , ");
                }
                System.out.println();
            }

            System.out.println("\n \n T Matrix \n");

            for (int i = 0; i < s.RowLength; i++) {
                for (int j = 0; j < s.ColumnLength; j++) {
                    System.out.print(s.T.get(i).get(j) + " , ");
                }
                System.out.println();
            }

            System.out.println("\n \n Nouvelle T Matrix \n");

            for (int i = 0; i < s.RowLength; i++) {
                for (int j = 0; j < s.ColumnLength; j++) {
                    System.out.print(s.T.get(i).get(j) + " , ");
                }
                System.out.println();
            }

            System.out.println("\n \n Map Matrix \n");

            for (int i = 0; i < s.RowLength; i++) {
                for (int j = 0; j < s.ColumnLength; j++) {
                    System.out.print(s.map.get(i).get(j) + " , ");
                }
                System.out.println();
            }

            System.out.println("\n \n Minimizeur Globale  \n");

            for (int i = 0; i < s.Globalminimizer.size(); i++) {
                for (int j = 0; j < s.Globalminimizer.get(i).size(); j++) {
                    System.out.print(s.Globalminimizer.get(i).get(j) + " , ");
                }
                System.out.println("");
            }

       
            System.out.println("\n \n FitnessP : " + s.FitnessP.toString());
            System.out.println("\n \n Le nouveau minimum Globale : " + s.Globalminimum);


            

        }
    
    }

}
