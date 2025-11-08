package NP.zadachiZaVezhbanjeK1;

import java.util.*;

class Triple<T extends Number & Comparable<T>>{
    private List<T> lista;

    public Triple(T a, T b, T c) {
        lista = new ArrayList<>();
        lista.add(a);
        lista.add(b);
        lista.add(c);
    }

    public double avarage(){
        double sum = 0;
        for(T t : lista){
            sum+=t.doubleValue();
        }
        return sum/3;
    }

    public double max(){
        return Collections.max(lista).doubleValue();
    }

    public void sort(){
        Collections.sort(lista);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(T t : lista){
            sb.append(String.format("%.2f ", t.doubleValue()));
        }
        return sb.toString().trim();
    }
}

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.avarage());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.avarage());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.avarage());
        tDouble.sort();
        System.out.println(tDouble);
    }
}
// vasiot kod ovde
// class Triple



