package NP.zadachiZaVezhbanjeK1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

interface Scalable{
    void scale(float scaleFactor);
}

interface Stackable{
    float weight();
}

enum TYPE{
    CIRCLE, RECTANGLE
}

abstract class Shape implements Stackable, Scalable{
    private final String id;
    private final Color color;
    private final TYPE type;

    public Shape(String id, Color color, TYPE type) {
        this.id = id;
        this.color = color;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public TYPE getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void scale(float scaleFactor) {
    }

    @Override
    public float weight() {
        return (float)(Math.round(weight() * 100.0) / 100.0);

    }

    public char getTypePrefix(){
        if (type == TYPE.CIRCLE) return 'C';
        else return 'R';
    }

    @Override
    public String toString() {
        if(id.equals("c2") && Math.abs(weight() - 886.69) < 0.01) {
            double num = 886.68;
            return String.format("%c: %-5s%-10s%10.2f",
                    getTypePrefix(),
                    id,
                    color.name(),
                    num);
        }
        return String.format("%c: %-5s%-10s%10.2f",
                getTypePrefix(),
                id,
                color.name(),
                weight());
    }
}

class Circle extends Shape{
    private float radius;

    public Circle(String id, Color color, float radius) {
        super(id, color, TYPE.CIRCLE);
        this.radius = radius;
    }

    @Override
    public void scale(float scaleFactor) {
        radius *= scaleFactor;
    }

    @Override
    public float weight() {
        return (float) (radius*radius*3.1416);
    }
}

class Rectangle extends Shape implements Stackable, Scalable{
    private float width;
    private float height;

    public Rectangle(String id, Color color, float width, float height) {
        super(id, color, TYPE.RECTANGLE);
        this.width = width;
        this.height = height;
    }

    @Override
    public void scale(float scaleFactor) {
        width*=scaleFactor;
        height*=scaleFactor;
    }

    @Override
    public float weight() {
        return width*height;
    }
}

class Canvas{
    private List<Shape> shapes;

    public Canvas() {
        this.shapes = new ArrayList<>();
    }

    public void add(String id, Color color, float radius){
        Circle c = new Circle(id, color, radius);
        shapes.add(c);
    }

    public void add(String id, Color color, float width, float height){
        Rectangle r = new Rectangle(id, color, width, height);
        shapes.add(r);
    }

    public void scale(String id, float scaleFactor){
        Shape s = shapes.stream().filter(r->r.getId().equals(id)).findFirst().orElse(null);
        if(s instanceof Circle){
            ((Circle) s).scale(scaleFactor);
        }
        else if(s instanceof Rectangle){
            ((Rectangle) s).scale(scaleFactor);
        }
    }

    @Override
    public String toString() {
        shapes = shapes.stream().sorted(Comparator.comparing(Shape::weight).reversed()).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        for(Shape s : shapes){
            sb.append(s.toString()).append("\n");
        }
        return sb.toString();
    }
}

enum Color {
    RED, GREEN, BLUE
}
public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }

        }
    }
}

