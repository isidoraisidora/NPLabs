package NP;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

enum TYPE {
    POINT,
    CIRCLE
}

enum DIRECTION {
    UP,
    DOWN,
    LEFT,
    RIGHT
}
interface Movable{
    void moveUp() throws ObjectCanNotBeMovedException;
    void moveLeft()throws ObjectCanNotBeMovedException;
    void moveRight()throws ObjectCanNotBeMovedException;
    void moveDown()throws ObjectCanNotBeMovedException;
    int getCurrentXPosition();
    int getCurrentYPosition();
}

class MovableObjectNotFittableException extends Exception {
    public MovableObjectNotFittableException(Movable m) {
        super(generateMessage(m));
    }

    private static String generateMessage(Movable m) {
        if (m instanceof MovableCircle circle) {
            return String.format(
                    "Movable circle with center (%d,%d) and radius %d can not be fitted into the collection",
                    circle.getCurrentXPosition(),
                    circle.getCurrentYPosition(),
                    circle.getRadius()
            );
        } else if (m instanceof MovablePoint) {
            return String.format(
                    "Movable point with coordinates (%d,%d) can not be fitted into the collection",
                    m.getCurrentXPosition(),
                    m.getCurrentYPosition()
            );
        } else {
            return "Unknown movable object cannot be fitted into the collection";
        }
    }
}

class ObjectCanNotBeMovedException extends Exception {
    private final int attemptedX;
    private final int attemptedY;

    public ObjectCanNotBeMovedException(int x, int y) {
        super(String.format("Point (%d,%d) is out of bounds", x, y));
        this.attemptedX = x;
        this.attemptedY = y;
    }

    public int getAttemptedX() {
        return attemptedX;
    }

    public int getAttemptedY() {
        return attemptedY;
    }
}


class MovablePoint implements Movable{
    private int X;
    private int Y;
    private int xSpeed;
    private int ySpeed;

    public MovablePoint(int x, int y, int xSpeed, int ySpeed) {
        X = x;
        Y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    @Override
    public String toString() {
        return String.format("Movable point with coordinates (%d, %d)",X,Y);
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        int newY = Y + ySpeed;
        if(newY>MovablesCollection.getMAX_Y()){
            throw new ObjectCanNotBeMovedException(X, newY);
        }
        Y = newY;
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException{
        int newX = X - xSpeed;
        if(newX<0){
            int z = getCurrentXPosition();
            throw new ObjectCanNotBeMovedException(newX, Y);
        }
        X = newX;
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException{
        int newX = X + xSpeed;
        if(newX>MovablesCollection.getMAX_X()){
            throw new ObjectCanNotBeMovedException(newX, Y);
        }
        X = newX;

    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException{
        int newY = Y - ySpeed;
        if(newY<0){
            throw new ObjectCanNotBeMovedException(X,newY);
        }
        Y = newY;
    }

    @Override
    public int getCurrentXPosition() {
        return X;
    }

    @Override
    public int getCurrentYPosition() {
        return Y;
    }
}

class MovableCircle implements Movable{
    private final int radius;
    private MovablePoint mp;

    public MovableCircle(int radius, MovablePoint mp) {
        this.radius = radius;
        this.mp = mp;
    }

    @Override
    public String toString() {
        return String.format("Movable circle with center cooridnates (%d,%d) and radius %d",mp.getCurrentXPosition(), mp.getCurrentYPosition(), radius);
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException{
        mp.moveUp();
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException{
        mp.moveLeft();
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException{
        mp.moveRight();
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException{
        mp.moveDown();
    }

    @Override
    public int getCurrentXPosition() {
        return mp.getCurrentXPosition();
    }

    @Override
    public int getCurrentYPosition() {
        return mp.getCurrentYPosition();
    }

    public int getRadius() {
        return radius;
    }
}

class MovablesCollection{
    private List<Movable> movables = new ArrayList<>();
    private static int MAX_X;
    private static int MAX_Y;
    private static final int MIN_X = 0;
    private static final int MIN_Y = 0;

    public MovablesCollection(int MAX_X, int MAX_Y){
        this.MAX_X = MAX_X;
        this.MAX_Y = MAX_Y;
    }

    public static void setxMax(int maxX) {
        MAX_X = maxX;
    }

    public static void setyMax(int maxY) {
        MAX_Y = maxY;
    }

    public static int getMAX_X() {
        return MAX_X;
    }

    public static int getMAX_Y() {
        return MAX_Y;
    }

    public void addMovableObject(Movable m) throws MovableObjectNotFittableException{
        int x = m.getCurrentXPosition();
        int y = m.getCurrentYPosition();

        if(m instanceof MovablePoint){
            if(x<0 || x>MAX_X || y<0 || y>MAX_Y){
                System.out.printf("Point (%d,%d) is out of bounds%n", x, y);
                return;
            }
            movables.add(m);
        }
        else{
            MovableCircle circle = (MovableCircle) m;
            int radius = circle.getRadius();
            if(x-radius<0 || x+radius>MAX_X || y-radius<0 || y+radius>MAX_Y){
                System.out.printf("Movable circle with center (%d,%d) and radius %d can not be fitted into the collection%n",
                        x, y, radius);
                return;
            }
            movables.add(circle);
        }
    }

    void moveObjectsFromTypeWithDirection(TYPE type, DIRECTION direction){
        for(Movable m : movables){
            if((type==TYPE.POINT && m instanceof MovablePoint) ||
                    (type==TYPE.CIRCLE && m instanceof MovableCircle)){
                try{
                    switch (direction) {
                        case UP:
                            m.moveUp();
                            break;
                        case DOWN:
                            m.moveDown();
                            break;
                        case LEFT:
                            m.moveLeft();
                            break;
                        case RIGHT:
                            m.moveRight();
                            break;
                    }

                } catch (ObjectCanNotBeMovedException e) {
                    System.out.printf("Point (%d,%d) is out of bounds%n",
                            e.getAttemptedX(), e.getAttemptedY());
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Collection of movable objects with size ").append(movables.size()).append("\n");
        for(Movable m : movables){
            sb.append(m.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}

public class CirclesTest {

    public static void main(String[] args) throws MovableObjectNotFittableException {

        System.out.println("===COLLECTION CONSTRUCTOR AND ADD METHOD TEST===");
        MovablesCollection collection = new MovablesCollection(100, 100);
        Scanner sc = new Scanner(System.in);
        int samples = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < samples; i++) {
            String inputLine = sc.nextLine();
            String[] parts = inputLine.split(" ");

            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int xSpeed = Integer.parseInt(parts[3]);
            int ySpeed = Integer.parseInt(parts[4]);

            try{
                if (Integer.parseInt(parts[0]) == 0) { //point
                    collection.addMovableObject(new MovablePoint(x, y, xSpeed, ySpeed));
                } else { //circle
                    int radius = Integer.parseInt(parts[5]);
                    collection.addMovableObject(new MovableCircle(radius, new MovablePoint(x, y, xSpeed, ySpeed)));
                }
            } catch (MovableObjectNotFittableException e) {
                System.out.println(e.getMessage());
            }


        }
        System.out.println(collection.toString());

        System.out.println("MOVE POINTS TO THE LEFT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.LEFT);
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES DOWN");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.DOWN);
        System.out.println(collection.toString());

        System.out.println("CHANGE X_MAX AND Y_MAX");
        MovablesCollection.setxMax(90);
        MovablesCollection.setyMax(90);

        System.out.println("MOVE POINTS TO THE RIGHT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.RIGHT);
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES UP");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.UP);
        System.out.println(collection.toString());


    }


}
