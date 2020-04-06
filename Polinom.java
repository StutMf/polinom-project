package semestrovka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Polinom {
    private List<PolinomPair> polinom = new ArrayList<>();
    private Map<Integer, Integer> degrees = new TreeMap<>();
    private TreeMap<Integer, Integer> sorted = new TreeMap<>(Collections.reverseOrder());


    public Polinom() {}

    public Polinom(String filename) {
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();
            StringBuilder number = new StringBuilder();
            while (line != null) {
                number.setLength(0);
                int coefficient = 0;
                for(int i = 0; i < line.length(); i++) {
                    if(line.charAt(i) == '-') {
                        coefficient = Integer.parseInt(number.toString());
                        number.setLength(0);
                    } else {
                        number.append(line.charAt(i));
                    }
                }
                int deg = Integer.parseInt(number.toString());
                insert(coefficient, deg);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        String stringPolinom = "";
        for(PolinomPair polinomPair : polinom) {
            stringPolinom += (polinomPair.getCoefficient() > 0 ? "+" : "") + polinomPair.getCoefficient() + "x^" + polinomPair.getDeg();
        }
        return stringPolinom;
    }

    public void insert(int coefficient, int deg) {
        polinom.add(new PolinomPair(coefficient, deg));
    }

    public void combine() {
        mapping(this);
        polinom.clear();
        for(Map.Entry<Integer, Integer> entry : degrees.entrySet()) {
            insert(entry.getValue(), entry.getKey());
        }
        degrees.clear();
    }

    public void delete(int deg) {
        polinom.removeIf(polinomPair -> polinomPair.getDeg() == deg);
    }

    public void sum(Polinom p) {
        mapping(this);
        mapping(p);
        polinom.clear();
        for(Map.Entry<Integer, Integer> entry : degrees.entrySet()) {
            insert(entry.getValue(), entry.getKey());
        }
        degrees.clear();
    }

    public void differentiate() {
        List<PolinomPair> p = new ArrayList<>();
        for(PolinomPair polinomPair : polinom){
            if (polinomPair.getDeg() != 0) {
                PolinomPair pP = new PolinomPair(polinomPair.getCoefficient() * polinomPair.getDeg(), polinomPair.getDeg() - 1);
                p.add(pP);
            }
        }
        polinom = p;
    }

    public int value(int x) {
        sort();
        boolean isFirst = true;
        int result = sorted.get(sorted.firstKey());

        for(Map.Entry<Integer, Integer> entry : sorted.entrySet()) {
            if (isFirst) { isFirst = false; }
            else {
                result = result * x + entry.getValue();
            }
        }
        return result;
    }

    public void deleteOdd(){
        polinom.removeIf(polinomPair -> polinomPair.getCoefficient() % 2 != 0);
    }


    private int mapping(Polinom p) {
        int max = 0;
        for(PolinomPair polinomPair : p.polinom) {
            if (polinomPair.getDeg() > max) max = polinomPair.getDeg();
            if(degrees.containsKey(polinomPair.getDeg())) {
                degrees.replace(polinomPair.getDeg(), degrees.get(polinomPair.getDeg()) + polinomPair.getCoefficient());
            } else {
                degrees.put(polinomPair.getDeg(), polinomPair.getCoefficient());
            }
        }
        return max;
    }

    private void mappingWithZeroCoefficent(Polinom p) {
        int t = mapping(p);
        for (int i = 0; i < t; i++){
            if (!degrees.containsKey(i))
                degrees.put(i, 0);
        }
    }

    private void sort() {
        mappingWithZeroCoefficent(this);
        sorted.clear();
        sorted.putAll(degrees);
        degrees.clear();
    }
}