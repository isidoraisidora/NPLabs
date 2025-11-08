package NP.zadachiZaVezhbanjeK1;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class IrregularCanvasException extends Exception{
    public IrregularCanvasException(String message) {
        super(message);
    }
}
enum TYPE{
    CIRCLE, SQUARE;
}

abstract class Shape implements Comparable<Shape> {
    protected TYPE type;
    protected int side;

    protected Shape(TYPE type, int side){
        this.side = side;
        this.type = type;
    }

    public TYPE getType() {
        return type;
    }
    public abstract double getArea();

    @Override
    public int compareTo(Shape o) {
        return Double.compare(getArea(),o.getArea());
    }
}

class Circle extends Shape{

    public Circle(int side){
        super(TYPE.CIRCLE, side);
    }

    @Override
    public double getArea() {
        return Math.PI*side*side;
    }
}

class Square extends Shape{

    public Square(int side){
        super(TYPE.SQUARE, side);
    }

    @Override
    public double getArea() {
        return side*side;
    }
}
class Canvas implements Comparable<Canvas>{
    private final String id;
    private final List<Shape> shapes;

    public Canvas(String str) {
        this.shapes = new ArrayList<>();
        String[] split = str.split("\\s+");
        this.id = split[0];
        for(int i=1; i<split.length; i+=2){
            if(split[i].equals("C")){
                shapes.add(new Circle(Integer.parseInt(split[i+1])));
            }
            else{
                shapes.add(new Square(Integer.parseInt(split[i+1])));
            }
        }
    }

    public double getMax(){
        return Collections.max(shapes).getArea();
    }

    public double getMin(){
        return Collections.min(shapes).getArea();
    }

    public double getArea(){
        return shapes.stream().mapToDouble(Shape::getArea).sum();
    }

    public double averageArea(){
        return shapes.stream().mapToDouble(Shape::getArea).average().getAsDouble();
    }

    public long totalCircles(){
        return shapes.stream().filter(r->r.getType().equals(TYPE.CIRCLE)).count();
    }

    public long totalSquares(){
        return shapes.stream().filter(r->r.getType().equals(TYPE.SQUARE)).count();
    }

    public String getId() {
        return id;
    }

    @Override
    public int compareTo(Canvas o) {
        return Double.compare(getArea(), o.getArea());
    }
    @Override
    public String toString() {
        return String.format("%s %d %d %d %.2f %.2f %.2f", id, shapes.size(), totalCircles(), totalSquares(), getMin(), getMax(), averageArea());
    }
}

class ShapesApplication{
    private double maxArea;
    private List<Canvas> canvases;

    public ShapesApplication(double maxArea) {
        this.maxArea = maxArea;
        this.canvases = new ArrayList<>();
    }

    public void addCanvas(Canvas c) throws IrregularCanvasException {
        if(c.getMax()>maxArea){
            throw new IrregularCanvasException(String.format("Canvas %s has a shape with area larger than %.2f",c.getId(),maxArea));
        }
        canvases.add(c);
    }

    public void readCanvases(InputStream inputStream) throws IOException, IrregularCanvasException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();
        while(line!=null){
            try{
                addCanvas(new Canvas(line));
            }
            catch (IrregularCanvasException e){
                System.out.println(e.getMessage());
            }
            line = reader.readLine();
        }
    }
    public void printCanvases(PrintStream os){
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));
        canvases.sort(Collections.reverseOrder());
        canvases.forEach(os::println);
    }
}

public class Shapes2Test {

    public static void main(String[] args) throws IrregularCanvasException, IOException {

        ShapesApplication shapesApplication = new ShapesApplication(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);


    }
}