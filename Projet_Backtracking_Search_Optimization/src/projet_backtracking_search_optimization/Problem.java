/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_backtracking_search_optimization;

/**
 *
 * @author book
 */
public class Problem {

    private Double Low;
    private Double Up;
    private int NumFunc;

    public Problem(int NumFunc) {
        switch (NumFunc) {
            case 1: // Rastrigin
            {
                this.Low = -100.0;
                this.Up = 100.0;
            }
            case 2: // Weierstrass 
            {
                 this.Low = -0.5;
                this.Up = 0.5;

            }
            case 3: // Sphere
            {
                this.Low = -100.0;
                this.Up = 100.0;
                
            }
            case 4: // Ackley
            {
                this.Low = -32.0;
                this.Up = 32.0;
            }
        }
        this.NumFunc = NumFunc;
    }

    public Double getLow() {
        return Low;
    }

    public void setLow(Double Low) {
        this.Low = Low;
    }

    public Double getUp() {
        return Up;
    }

    public void setUp(Double Up) {
        this.Up = Up;
    }

    public int getNumFunc() {
        return NumFunc;
    }

    public void setNumFunc(int NumFunc) {
        this.NumFunc = NumFunc;
    }

}
