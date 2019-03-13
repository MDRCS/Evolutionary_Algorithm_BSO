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
public class Solution {

    private Double FitnessBest;
    private Problem Pbm;

    public Solution(Double FitnessBest, Problem Pbm) {
        this.FitnessBest = FitnessBest;
        this.Pbm = Pbm;
    }

    public Double getFitnessBest() {
        return FitnessBest;
    }

    public void setFitnessBest(Double FitnessBest) {
        this.FitnessBest = FitnessBest;
    }

    public Problem getPbm() {
        return Pbm;
    }

    public void setPbm(Problem Pbm) {
        this.Pbm = Pbm;
    }

    public Double Random(Double Low, Double Up) {
        return Math.random() * (Up - Low) + Low;
    }
}
