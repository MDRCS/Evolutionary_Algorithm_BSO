/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_backtracking_search_optimization;

import java.net.URL;
import java.util.ArrayList;
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
    private LineChart<String, Double> linechart;
    XYChart.Series<String, Double> series;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        series = new XYChart.Series<>();
        series.setName("Fitness Evolution");

        linechart.getData().add(series);
        Thread th = new Thread(new FitnessReader());
        th.start();
    }

    class FitnessReader implements Runnable {

        @Override
        public void run() {
            SetUpParams setup = new SetUpParams(25, 0.25, 30, 30);
            ArrayList<ArrayList<Double>> Population = new ArrayList<ArrayList<Double>>(setup.getDim());
            ArrayList<ArrayList<Double>> OldPopulation = new ArrayList<ArrayList<Double>>(setup.getDim());
            Problem pbmm = new Problem(4);
            Solution sol = new Solution(Double.POSITIVE_INFINITY, pbmm);
            ArrayList<Double> FitnessP = new ArrayList<Double>(setup.getDim());
            Algorithm algo = new Algorithm(Population, OldPopulation, sol, setup, Double.POSITIVE_INFINITY, FitnessP);
            algo.Initialize();
            ArrayList<ArrayList<Double>> mutant = new ArrayList<ArrayList<Double>>(setup.getDim());
            ArrayList<ArrayList<Double>> T = new ArrayList<ArrayList<Double>>(setup.getDim());
            ArrayList<ArrayList<Integer>> map = new ArrayList<ArrayList<Integer>>(setup.getDim());
            ArrayList<Integer> IndiceOldP = new ArrayList<Integer>(setup.getDim());
            Algorithm algo_c1 = new Algorithm(mutant, T, map, IndiceOldP);
            ArrayList<ArrayList<Double>> globalminimizer = new ArrayList<ArrayList<Double>>(setup.getDim());
            ArrayList<Double> FitnessT = new ArrayList<Double>(setup.getDim());
            Algorithm algo_c2 = new Algorithm(globalminimizer, FitnessT, 100);

            ////////////////////////////////////////////////////////////////////////////////////////////
            int FuncNum = 0;
            int x = 0;

            while (x <= algo.setup.getMaxcycle()) {
                
                algo.Selection_I(algo_c1);
                algo.Selection_II(algo_c1, algo_c2);
                x++;

                switch (FuncNum) {
                    case 0: {                      
                         series.getData().add(new XYChart.Data<>(x + "", algo.sol.getFitnessBest()));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        //   System.out.println("Best fitness"+algo.sol.getFitnessBest());
                        //   System.out.println("fitnessP"+algo.FitnessP.toString());
                        //   System.out.println("GlobalMin"+algo.GlobalMin);       
                        break;
                    }
                }
            }

            System.out.println("\n Population \n");

            for (int i = 0; i < algo.Population.size(); i++) {
                for (int j = 0; j < algo.Population.get(i).size(); j++) {
                    System.out.print(algo.Population.get(i).get(j) + " , ");

                }
                System.out.println();
            }

            System.out.println("\n \n Old Population \n");

            for (int i = 0; i < algo.OldPopulation.size(); i++) {
                for (int j = 0; j < algo.OldPopulation.get(i).size(); j++) {
                    System.out.print(algo.OldPopulation.get(i).get(j) + " , ");
                }
                System.out.println();
            }

            System.out.println("\n \n Mutation Matrix \n");

            for (int i = 0; i < algo_c1.Mutant.size(); i++) {
                for (int j = 0; j < algo_c1.Mutant.get(i).size(); j++) {
                    System.out.print(algo_c1.Mutant.get(i).get(j) + " , ");
                }
                System.out.println();
            }

            
            System.out.println("\n \n T Matrix \n");

            for (int i = 0; i < algo_c1.T.size(); i++) {
                for (int j = 0; j < algo_c1.T.get(i).size(); j++) {
                    System.out.print(algo_c1.T.get(i).get(j) + " , ");
                }
                System.out.println();
            }
            

            System.out.println("\n \n Map Matrix \n");

            for (int i = 0; i < algo_c1.map.size(); i++) {
                for (int j = 0; j < algo_c1.map.get(i).size(); j++) {
                    System.out.print(algo_c1.map.get(i).get(j) + " , ");
                }
                System.out.println();
            }

            System.out.println("\n \n Minimizeur Globale  \n");

            for (int i = 0; i < algo_c2.Globalminimizer.size(); i++) {
                for (int j = 0; j < algo_c2.Globalminimizer.get(i).size(); j++) {
                    System.out.print(algo_c2.Globalminimizer.get(i).get(j) + " , ");
                }
                System.out.println("");
            }

           System.out.println("FitnessP"+algo.FitnessP.toString());
              System.out.println(algo.GlobalMin);
        }
    }

}
