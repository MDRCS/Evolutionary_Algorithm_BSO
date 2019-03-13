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
public class SetUpParams {

    private int Maxcycle;
    private Double Mixrate;
    private int Dim;
    private int Popsize;

    public SetUpParams(int Maxcycle, Double Mixrate, int Dim, int Popsize) {
        this.Maxcycle = Maxcycle;
        this.Mixrate = Mixrate;
        this.Dim = Dim;
        this.Popsize = Popsize;
    }

    public int getMaxcycle() {
        return Maxcycle;
    }

    public void setMaxcycle(int Maxcycle) {
        this.Maxcycle = Maxcycle;
    }

    public Double getMixrate() {
        return Mixrate;
    }

    public void setMixrate(Double Mixrate) {
        this.Mixrate = Mixrate;
    }

    public int getDim() {
        return Dim;
    }

    public void setDim(int Dim) {
        this.Dim = Dim;
    }

    public int getPopsize() {
        return Popsize;
    }

    public void setPopsize(int Popsize) {
        this.Popsize = Popsize;
    }

}
