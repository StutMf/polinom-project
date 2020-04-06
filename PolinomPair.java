package semestrovka;

public class PolinomPair {
    private int coefficient;
    private int deg;

    public PolinomPair (int coefficient, int deg) {
        if(coefficient != 0) {
            this.coefficient = coefficient;
            this.deg = deg;
        }
    }

    public int getCoefficient() {
        return coefficient;
    }

    public int getDeg() {
        return deg;
    }

    public void setCoefficient(int coefficient) {
        if (coefficient != 0) { this.coefficient = coefficient; }
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }
}
