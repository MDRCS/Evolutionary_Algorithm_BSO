/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_backtracking_search_optimization;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author book
 */
public class Algorithm {

    public ArrayList<ArrayList<Double>> Population; // Matrix
    public ArrayList<ArrayList<Double>> OldPopulation; // Matrix
    public ArrayList<ArrayList<Double>> Mutant; // local Matrix
    public ArrayList<ArrayList<Double>> T; // local Matrix
    public ArrayList<ArrayList<Integer>> map;
    public ArrayList<ArrayList<Double>> Globalminimizer;
    public ArrayList<Double> fitnessT;
    public ArrayList<Double> FitnessP;
    public ArrayList<Integer> IndiceOldP;
    public Solution sol;
    public SetUpParams setup;
    public int Randmut; //Nombre de Mutation des indices a faire
    public double GlobalMin;

    public Algorithm(ArrayList<ArrayList<Double>> Population, ArrayList<ArrayList<Double>> OldPopulation, Solution sol, SetUpParams setup, double GlobalMin, ArrayList<Double> FitnessP) {
        this.Population = Population;
        this.OldPopulation = OldPopulation;
        this.sol = sol;
        this.setup = setup;
        this.GlobalMin = GlobalMin;
        this.FitnessP = FitnessP;
    }

    public Algorithm(ArrayList<ArrayList<Double>> Mutant, ArrayList<ArrayList<Double>> T, ArrayList<ArrayList<Integer>> map, ArrayList<Integer> IndiceOldP) {
        this.Mutant = Mutant;
        this.T = T;
        this.map = map;
        this.IndiceOldP = IndiceOldP;
    }

    public Algorithm(ArrayList<ArrayList<Double>> Globalminimizer, ArrayList<Double> fitnessT, int Randmut) {
        this.Globalminimizer = Globalminimizer;
        this.fitnessT = fitnessT;
        this.Randmut = Randmut;
    }

    public void Initialize() {

        for (int i = 0; i < setup.getPopsize(); i++) {
            List l1 = new ArrayList<Double>(setup.getDim());
            List l2 = new ArrayList<Double>(setup.getDim());
            for (int j = 0; j < setup.getDim(); j++) {
                l1.add(this.sol.Random(this.sol.getPbm().getLow(), this.sol.getPbm().getUp()));
                l2.add(this.sol.Random(this.sol.getPbm().getLow(), this.sol.getPbm().getUp()));
            }
            Population.add((ArrayList<Double>) l1);
            OldPopulation.add((ArrayList<Double>) l2);
            FitnessP.add(this.Fitness(this.sol.getPbm().getNumFunc(), Population.get(i)));
           
        }
     //    System.out.println(FitnessP.toString());

    }

    public Double Min(ArrayList<Double> l) {
        Double min = l.get(0);
        for (int i = 1; i < setup.getDim(); i++) {
            if (min > l.get(i)) {
                min = l.get(i);
            }
        }
        return min;
    }
    
     public Double Fitness(int NumObjFun, ArrayList<Double> l) {
        Double sum = 0.0;
        switch (NumObjFun) {
            case 1: // Rastrigin
            {
                for (int j = 0; j < setup.getDim(); j++) {
                    sum += (l.get(j) * l.get(j)) - 10 * Math.cos(2 * Math.PI * l.get(j));
                }
                return sum;
                //10.0 * ColumnLength +sum
            }
            case 2: //Weistrass
            {
                Double sum1= 0.0 , sum2=0.0  ; 
                 for (int i = 0; i < setup.getDim(); i++) {
                     for (int j = 0; j < 20; j++) {
                    sum1+=0.5*Math.cos(2*Math.PI*3*(l.get(i)+0.5)) ; 
                }
                }
                 
                for (int j = 0; j < setup.getDim(); j++) {
                   sum2+=0.5*Math.cos(2*Math.PI*3*0.5) ; 
                }
                
                sum = sum1-30*sum2 ; 
                 
            }
            case 3: // Sphere
            {
                for (int j = 0; j < setup.getDim(); j++) {
                    sum += l.get(j) * l.get(j);
                }
                return sum;
            }
            
            case 4: // Ackley
            {
                Double Fct1 = 0.0 , Fct2 = 0.0 ; 
                for(int i=0 ; i<setup.getDim() ; i++)
                {
                     Fct1 += l.get(i)*l.get(i) ; 
                     Fct2 += Math.cos(2*Math.PI*l.get(i)) ;   
                }
                sum =  -20*Math.exp(-0.2*Math.sqrt(1/30*Fct1)) - Math.exp(1/setup.getDim()*Fct2)+20+Math.E; 
            }
        }
        return sum;

    }
    
    

    public void Mutation(Algorithm a) {
        for (int i = 0; i < a.setup.getDim(); i++) {
            List l1 = new ArrayList<Double>(a.setup.getDim());
            for (int j = 0; j < a.setup.getDim(); j++) {
                Double s = (a.Population.get(i).get(j) + 3 * (Math.random() * (a.OldPopulation.get(i).get(j) - a.Population.get(i).get(j))));
                l1.add(s);
            }
            this.Mutant.add((ArrayList<Double>) l1);
        }
        
     //   System.out.println(Mutant.toString());
    }

    public void CrossOver(Algorithm a) {

        List l1 = new ArrayList<Integer>(a.setup.getDim());
        for (int j = 0; j < a.setup.getDim(); j++) {
            l1.add(1);
        }

        for (int i = 0; i < a.setup.getDim(); i++) {
            this.map.add((ArrayList<Integer>) l1);
        }

        int c, d;
        c = (int) Math.random();
        d = (int) Math.random();
        if (c < d) {
            for (int i = 0; i < a.setup.getDim(); i++) {
                int D = (int) (Math.random() * a.setup.getDim() - 1);
                ArrayList<Integer> l2 = new ArrayList<Integer>(a.setup.getDim());
                for (int j = 0; j < a.setup.getDim(); j++) {

                    if (j >= D) {
                        l2.add(1);
                    } else if (j < D) {
                        l2.add(0);
                    }
                }
                map.set(i, (ArrayList<Integer>) l2);
            }
        } else {
            for (int i = 0; i < a.setup.getDim(); i++) {
                int D = (int) (Math.random() * a.setup.getDim() - 1);
                this.map.get(i).set(D, 0);
            }
        }


        for (int i = 0; i < a.setup.getDim(); i++) {
            T.add(Mutant.get(i));
        }
      //    System.out.println(T.toString());
        for (int i = 0; i < a.setup.getDim(); i++) {
            for (int j = 0; j < a.setup.getDim(); j++) {
                if (map.get(i).get(j) == 1) {
                    T.get(i).set(j, a.Population.get(i).get(j));
                }
            }

        }
       // System.out.println(T.toString());

    }

    public void Permutting(Algorithm a) {
        int indice1, indice2, cycle, permut;

        cycle = 2;

        for (int i = 0; i < this.setup.getDim(); i++) {
            a.IndiceOldP.add(i);
        }

        while (cycle != 0) {

            do {
                indice1 = (int) (Math.random() * this.setup.getDim() - 1);
                indice2 = (int) (Math.random() * this.setup.getDim() - 1);
            } while (indice1 == indice2);

            permut = a.IndiceOldP.get(indice1);
            a.IndiceOldP.set(indice1, a.IndiceOldP.get(indice2));
            a.IndiceOldP.set(indice2, permut);
            cycle--;
        }

    }

    public void Boundary_Control(Algorithm a) {

        for (int i = 0; i < this.setup.getDim(); i++) {
            for (int j = 0; j < this.setup.getDim(); j++) {
                if (a.T.get(i).get(j) < this.sol.getPbm().getLow() || a.T.get(i).get(j) > this.sol.getPbm().getUp()) {
                    Double rnd = (Math.random() * (this.sol.getPbm().getUp() - this.sol.getPbm().getLow()) + this.sol.getPbm().getLow());
                    a.T.get(i).set(j, rnd);
                }
            }
        }

    }

    public void Selection_I(Algorithm y) {
        int a, b;
        a = (int) Math.random();
        b = (int) Math.random();
        if (a < b) {
            for (int i = 0; i < this.setup.getDim(); i++) {
                this.OldPopulation.add(i, this.Population.get(i));
            }
        }
        
        this.Permutting(y);
        y.Mutation(this);
        y.CrossOver(this);
        this.Boundary_Control(y);
   //     System.out.println("FitnessP"+FitnessP.toString());
    }

    public void Selection_II(Algorithm a, Algorithm b) {
        
        for (int i = 0; i < this.setup.getDim(); i++) {
            b.fitnessT.add(this.Fitness(this.sol.getPbm().getNumFunc(), a.T.get(i)));
        }
       
        for (int i = 0; i < this.setup.getDim(); i++) {
            if (this.FitnessP.get(i) > b.fitnessT.get(i)) {
              //  System.out.println("?????"+this.FitnessP.get(i)+" "+b.fitnessT.get(i));
               // System.out.println(this.FitnessP.toString());
                this.FitnessP.set(i, b.fitnessT.get(i));
              //   System.out.println("New "+this.FitnessP.toString());
               //  System.out.println("New T"+a.T.toString());
                this.Population.set(i, a.T.get(i));
            }
        }

        this.sol.setFitnessBest(Min(this.FitnessP));
        

        if (this.sol.getFitnessBest() < GlobalMin) {
            this.GlobalMin = this.sol.getFitnessBest();
            for (int i = 0; i < this.setup.getDim(); i++) {
                b.Globalminimizer.add(this.Population.get(i));

            }
        }

    }
    
    //  if(x==0 || x==100 || x==200 || x==300 || x==400 || x==500 || x==600 || x==700 || x==800 || x==900 || x==1000)
                     //   {
    /*
   SetUpParams setup = new SetUpParams(10, 1.0, 30, 30);
        ArrayList<ArrayList<Double>> Population = new ArrayList<ArrayList<Double>>(setup.getDim());
        ArrayList<ArrayList<Double>> OldPopulation = new ArrayList<ArrayList<Double>>(setup.getDim());
        Problem pbmm = new Problem(2);
        Solution sol = new Solution(Double.POSITIVE_INFINITY, pbmm);
        ArrayList<Double> FitnessP = new ArrayList<Double>(setup.getDim());
        Algorithm algo = new Algorithm(Population, OldPopulation, sol, setup, Double.POSITIVE_INFINITY, FitnessP);
        algo.Initialize();
        ArrayList<ArrayList<Double>> mutant = new ArrayList<ArrayList<Double>>(setup.getDim());
        ArrayList<ArrayList<Double>> T = new ArrayList<ArrayList<Double>>(setup.getDim());
        ArrayList<ArrayList<Integer>> map = new ArrayList<ArrayList<Integer>>(setup.getDim());
        ArrayList<Integer> IndiceOldP = new ArrayList<Integer>(setup.getDim());
        Algorithm algo_c1 = new Algorithm(mutant, T, map, IndiceOldP);

        algo.Selection_I(algo_c1);
        ArrayList<ArrayList<Double>> globalminimizer = new ArrayList<ArrayList<Double>>(setup.getDim());
        ArrayList<Double> FitnessT = new ArrayList<Double>(setup.getDim());
        Algorithm algo_c2 = new Algorithm(globalminimizer,FitnessT,100) ;
        algo.Selection_II(algo_c1,algo_c2);
        System.out.println(algo_c2.Globalminimizer.toString());
        System.out.println(algo.GlobalMin);
     */

}
