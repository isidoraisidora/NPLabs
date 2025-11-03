package NP;
import java.sql.Array;
import java.util.Arrays;
import java.util.Scanner;
import java.util.LinkedList;

class ResizableArray<T>{
    private T[] array;
    private int size=0;

    @SuppressWarnings("unchecked")
    public ResizableArray() {
        array = (T[]) new Object[size];
    }

    @SuppressWarnings("unchecked")
    public void addElement(T element) {
        if (size == array.length) {
            int newCapacity = array.length * 2;
            if (newCapacity == 0) newCapacity = 2;
            array = Arrays.copyOf(array, newCapacity);
        }
        array[size++] = element;
    }


    public boolean removeElement(T element){
        for(int i=0; i<size; i++){
            if(array[i]!=null && array[i].equals(element)){
                for(int j=i; j<size-1; j++){
                    array[j] = array[j+1];
                }
                array[--size] = null;
                return true;
            }
        }
        return false;
    }

    public boolean contains(T element){
        for(int i=0; i<size; i++){
            if(array[i] != null && array[i].equals(element)){
                return true;
            }
        }
        return false;
    }

    public Object[] toArray(){
        return Arrays.stream(array).toArray();
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int count(){
        int c = 0;
        for(int i=0; i<size; i++){
            if(array[i]!=null){
                c+=1;
            }
        }
        return c;
    }

    public T elementAt(int index){
        if(index<0 || index > size){
            throw new ArrayIndexOutOfBoundsException();
        }
        return array[index];
    }

    @SuppressWarnings("unchecked")
    public static <T> void copyAll(ResizableArray<? super T> dest, ResizableArray<? extends T> src) {
        Object[] temp = src.toArray();
        for (Object obj : temp) {
            dest.addElement((T) obj);
        }
    }
}

class IntegerArray extends ResizableArray<Integer>{

    public double sum(){
        double sum = 0;
        for(int i=0; i<count(); i++){
            if(elementAt(i)!=null)
                sum+= elementAt(i);
        }
        return sum;
    }
    public double mean(){
        if (isEmpty())
            return 0;
        return sum() / count();
    }

    public int countNonZero(){
        int num = 0;
        for(int i=0; i<count(); i++){
            if(elementAt(i) != 0){
                num+=1;
            }
        }
        return num;
    }

    public IntegerArray distinct(){
        IntegerArray novArray = new IntegerArray();
        for(int i=0; i<count(); i++){
            if(!novArray.contains(elementAt(i))){
                novArray.addElement(elementAt(i));
            }
        }
        return novArray;
    }
    public IntegerArray increment(int offset){
        IntegerArray novArray = new IntegerArray();
        for(int i=0; i<count(); i++){
            novArray.addElement(elementAt(i)+offset);
        }
        return novArray;
    }
}

public class ResizableArrayTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int test = jin.nextInt();
        if ( test == 0 ) { //test ResizableArray on ints
            ResizableArray<Integer> a = new ResizableArray<Integer>();
            System.out.println(a.count());
            int first = jin.nextInt();
            a.addElement(first);
            System.out.println(a.count());
            int last = first;
            while ( jin.hasNextInt() ) {
                last = jin.nextInt();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
        }
        if ( test == 1 ) { //test ResizableArray on strings
            ResizableArray<String> a = new ResizableArray<String>();
            System.out.println(a.count());
            String first = jin.next();
            a.addElement(first);
            System.out.println(a.count());
            String last = first;
            for ( int i = 0 ; i < 4 ; ++i ) {
                last = jin.next();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
            ResizableArray<String> b = new ResizableArray<String>();
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));

            System.out.println(a.removeElement(first));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
        }
        if ( test == 2 ) { //test IntegerArray
            IntegerArray a = new IntegerArray();
            System.out.println(a.isEmpty());
            while ( jin.hasNextInt() ) {
                a.addElement(jin.nextInt());
            }
            jin.next();
            System.out.println(a.sum());
            System.out.println(a.mean());
            System.out.println(a.countNonZero());
            System.out.println(a.count());
            IntegerArray b = a.distinct();
            System.out.println(b.sum());
            IntegerArray c = a.increment(5);
            System.out.println(c.sum());
            if ( a.sum() > 100 )
                ResizableArray.copyAll(a, a);
            else
                ResizableArray.copyAll(a, b);
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.contains(jin.nextInt()));
            System.out.println(a.contains(jin.nextInt()));
        }
        if ( test == 3 ) { //test insanely large arrays
            LinkedList<ResizableArray<Integer>> resizable_arrays = new LinkedList<ResizableArray<Integer>>();
            for ( int w = 0 ; w < 500 ; ++w ) {
                ResizableArray<Integer> a = new ResizableArray<Integer>();
                int k =  2000;
                int t =  1000;
                for ( int i = 0 ; i < k ; ++i ) {
                    a.addElement(i);
                }

                a.removeElement(0);
                for ( int i = 0 ; i < t ; ++i ) {
                    a.removeElement(k-i-1);
                }
                resizable_arrays.add(a);
            }
            System.out.println("You implementation finished in less then 3 seconds, well done!");
        }
    }

}
