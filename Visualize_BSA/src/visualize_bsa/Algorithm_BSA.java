/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualize_bsa;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author book
 */
public class Algorithm_BSA {
    
    // Declaration de Variable
    public int RowLength;
    public int ColumnLength;
    public final int Maxcycle = 30;
    public double Mixrate;
    public ArrayList<Double> Low;
    public ArrayList<Double> Up;
    public Double Globalminimum;
    public ArrayList<ArrayList<Double>> Population; // Matrix
    public ArrayList<ArrayList<Double>> OldPopulation; // Matrix
    public ArrayList<ArrayList<Double>> Mutant; // local Matrix
    public ArrayList<ArrayList<Double>> T; // local Matrix
    public ArrayList<ArrayList<Integer>> map;
    public ArrayList<Double> fitnessT;
    public ArrayList<Double> FitnessP;
    public ArrayList<Integer> IndiceOldP;
    public ArrayList<ArrayList<Double>> Globalminimizer;
    public int numObjFun;
    public Double fitnessPbest;

    public void Initialize(int num) {
        this.Mixrate = 0.1415;
        this.RowLength = 30;
        this.ColumnLength = 30;
        this.Globalminimum = Double.POSITIVE_INFINITY;
        this.numObjFun = num;
        int randl, randu;
        int i = 0;
        // Initialiser Low & Up 
        Low = new ArrayList<Double>(10);
        Up = new ArrayList<Double>(10);
        do {

            Low.add(-3.14158265359); 
            Up.add(3.14158265359);
            i++;
        } while (i < 10);

        // Allocation & Initialiser de La Population et Ancien Population
        int j;
        Population = new ArrayList<ArrayList<Double>>(ColumnLength);
        OldPopulation = new ArrayList<ArrayList<Double>>(ColumnLength);
        this.FitnessP = new ArrayList<Double>(RowLength);
        for (i = 0; i < RowLength; i++) {
            List l1 = new ArrayList<Double>(ColumnLength);
            List l2 = new ArrayList<Double>(ColumnLength);
            for (j = 0; j < ColumnLength; j++) {
                randl = (int) Math.random() * 9;
                randu = (int) Math.random() * 9;
                l1.add((Math.random() * Up.get(randl) + Low.get(randu)));
                l2.add((Math.random() * Up.get(randl) + Low.get(randu)));
            }
            Population.add((ArrayList<Double>) l1);
            OldPopulation.add((ArrayList<Double>) l2);
            FitnessP.add(this.Fitness(numObjFun, this.Population.get(i)));
        }

    }

    public Double Fitness(int NumObjFun, ArrayList<Double> l) {
        Double sum = 0.0;
        switch (NumObjFun) {
            case 1: // Rastrigin
            {
                for (int j = 0; j < this.RowLength; j++) {
                    sum += (l.get(j) * l.get(j)) - 10 * Math.cos(2 * Math.PI * l.get(j));
                }
                return sum;
                //10.0 * ColumnLength +sum
            }
            case 2: //Weistrass
            {
                Double sum1= 0.0 , sum2=0.0  ; 
                 for (int i = 0; i < this.RowLength; i++) {
                     for (int j = 0; j < 20; j++) {
                    sum1+=0.5*Math.cos(2*Math.PI*3*(l.get(i)+0.5)) ; 
                }
                }
                 
                for (int j = 0; j < this.RowLength; j++) {
                   sum2+=0.5*Math.cos(2*Math.PI*3*0.5) ; 
                }
                
                sum = sum1-30*sum2 ; 
                 
            }
            case 3: // Sphere
            {
                for (int j = 0; j < this.RowLength; j++) {
                    sum += l.get(j) * l.get(j);
                }
                return sum;
            }
            
            case 4: // Ackley
            {
                Double Fct1 = 0.0 , Fct2 = 0.0 ; 
                for(int i=0 ; i<this.RowLength ; i++)
                {
                     Fct1 += l.get(i)*l.get(i) ; 
                     Fct2 += Math.cos(2*Math.PI*l.get(i)) ;   
                }
                sum =  -20*Math.exp(-0.2*Math.sqrt(1/30*Fct1)) - Math.exp(1/this.RowLength*Fct2)+20+Math.E; 
            }
        }
        return sum;

    }

    public void Mutation() {
        this.Mutant = new ArrayList<ArrayList<Double>>(ColumnLength);
        for (int i = 0; i < this.RowLength; i++) {
            List l1 = new ArrayList<Double>(RowLength);
            for (int j = 0; j < this.ColumnLength; j++) {
                Double s = (this.Population.get(i).get(j) + 3 * (Math.random() * (this.OldPopulation.get(i).get(j) - this.Population.get(i).get(j))));
                l1.add(s);
            }
            this.Mutant.add((ArrayList<Double>) l1);
        }
    }

    public void CrossOver() {
        map = new ArrayList<ArrayList<Integer>>(ColumnLength);

        List l1 = new ArrayList<Integer>(RowLength);
        for (int j = 0; j < this.RowLength; j++) {
            l1.add(1);
        }

        for (int i = 0; i < this.ColumnLength; i++) {
            map.add((ArrayList<Integer>) l1);
        }

        int c, d;
        c = (int) Math.random();
        d = (int) Math.random();
        if (c < d) {
            for (int i = 0; i < this.ColumnLength; i++) {
                int D = (int) (Math.random() * ColumnLength - 1);
                ArrayList<Integer> l2 = new ArrayList<Integer>(ColumnLength);
                for (int j = 0; j < ColumnLength; j++) {

                    if (j >= D) {
                        l2.add(1);
                    } else if (j < D) {
                        l2.add(0);
                    }
                }
                map.set(i, (ArrayList<Integer>) l2);
            }
        } else {
            for (int i = 0; i < this.ColumnLength; i++) {
                int D = (int) (Math.random() * ColumnLength - 1);
                //  System.out.println(D);
                map.get(i).set(D, 0);
            }
        }

        T = new ArrayList<ArrayList<Double>>(RowLength);

        for (int i = 0; i < RowLength; i++) {
            T.add(Mutant.get(i));
        }

        for (int i = 0; i < RowLength; i++) {
            for (int j = 0; j < ColumnLength; j++) {
                if (map.get(i).get(j) == 1) {
                    T.get(i).set(j, this.Population.get(i).get(j));
                }
            }

        }

    }

    public void Boundary_Control() {
        Double minlow = Low.get(0);
        Double maxUp = Up.get(0);
        for (int i = 0; i < RowLength; i++) {
            for (int j = 0; j < ColumnLength; j++) {

                if (T.get(i).get(j) < minlow || T.get(i).get(j) > maxUp) {
                    Double rnd = (Math.random() * (maxUp - minlow) + minlow);
                    //    System.out.println(rnd);
                    T.get(i).set(j, rnd);
                }
            }
        }

    }

    public Double Min(ArrayList<Double> l) {
        Double min = l.get(0);
        for (int i = 1; i < this.RowLength; i++) {
            if (min > l.get(i)) {
                min = l.get(i);
            }
        }
        return min;
    }

    public void Selection_I() {
        int a, b;
        a = (int) Math.random();
        b = (int) Math.random();
        if (a < b) {
            for (int i = 0; i < this.ColumnLength; i++) {
                OldPopulation.add(i, Population.get(i));
            }
        }

        this.Permutting();
        this.Mutation();
        this.CrossOver();
        this.Boundary_Control();
    }

    public void Permutting() {
        int indice1, indice2, cycle, permut;

        cycle = 2;

        this.IndiceOldP = new ArrayList<Integer>(ColumnLength);
        for (int i = 0; i < this.RowLength; i++) {
            this.IndiceOldP.add(i);
        }

        while (cycle != 0) {

            do {
                indice1 = (int) (Math.random() * ColumnLength - 1);
                indice2 = (int) (Math.random() * ColumnLength - 1);
            } while (indice1 == indice2);

            permut = this.IndiceOldP.get(indice1);
            this.IndiceOldP.set(indice1, this.IndiceOldP.get(indice2));
            this.IndiceOldP.set(indice2, permut);
            cycle--;
        }

    }

    public void Selection_II() {
        fitnessT = new ArrayList<Double>(this.ColumnLength);
        for (int i = 0; i < this.ColumnLength; i++) {
            fitnessT.add(this.Fitness(this.numObjFun, this.T.get(i)));
        }

        for (int i = 0; i < this.RowLength; i++) {
            if (this.FitnessP.get(i) > fitnessT.get(i)) {
                this.FitnessP.set(i, fitnessT.get(i));
                this.Population.set(i, T.get(i));
            }
        }

        fitnessPbest = Min(this.FitnessP);

        Globalminimizer = new ArrayList<ArrayList<Double>>(ColumnLength);
        if (fitnessPbest < this.Globalminimum) {
            //  System.out.println("bla bla");
            this.Globalminimum = fitnessPbest;
            //    System.out.println(" m " + fitnessPbest);
            for (int i = 0; i < this.ColumnLength; i++) {
                Globalminimizer.add(this.Population.get(i));

            }
        }

    }

    public void Evolutionnary_Algorithm() {
        
        for (int index = 0; index < this.Maxcycle ; index++) {
            this.Selection_I();
            this.Selection_II();
        }
       
    }

}
