package NP.zadachiZaVezhbanjeK1;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ShapesApplication{
    private static class Canvas{
        String id;
        List<Integer> squareSizes = new ArrayList<>();

        Canvas(String id){
            this.id = id;
        }

        int totalPerimeter(){
            int sum = 0;
            for(int size : squareSizes){
                sum += 4 * size;
            }
            return sum;
        }

        int countSquare(){
            return squareSizes.size();
        }
    }
    private List<Canvas> canvases;

    public ShapesApplication() {
        canvases = new ArrayList<>();
    }
    public int readCanvases(InputStream inputStream){
        int totalSquares = 0;
        Scanner scanner = new Scanner(inputStream);

        while(scanner.hasNextLine()){
            String line = scanner.nextLine().trim();
            if(line.isEmpty())
                continue;
            String[] parts = line.split("\\s+");
            if(parts.length<2)
                continue;

            Canvas canvas = new Canvas(parts[0]);
            for(int i=1; i<parts.length; i++){
                try{
                    int size = Integer.parseInt(parts[i]);
                    if(size>0){
                        canvas.squareSizes.add(size);
                        totalSquares++;
                    }
                }
                catch (NumberFormatException e){
                }
            }
            canvases.add(canvas);
        }
        return totalSquares;
    }

    public void printLargestCanvasTo(OutputStream outputStream){
        if(canvases.isEmpty()) return;

        Canvas largest = canvases.get(0);
        for(Canvas c : canvases){
            if(c.totalPerimeter() > largest.totalPerimeter()){
                largest = c;
            }
        }
        PrintWriter writer = new PrintWriter(outputStream, true);
        writer.printf("%s %d %d%n", largest.id, largest.countSquare(),largest.totalPerimeter());
    }

}

public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}
