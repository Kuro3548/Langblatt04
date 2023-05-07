import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class AufgabeB4A1 {
    public static boolean testRun = true;
    public static int test_size = 1 + 2 + 4;

    /**
     * Erhält eine Ganzzahl k erhalten und anschließend aus Standard-In eine Reihe von n Ganzzahlen.
     * Dann wird auf dieser Menge der k-kleinste Wert mit heapSelect oder heapSelectFast gefunden und in der ausgegeben
     * @Runtime O(n) - n ist die Anzahl an Zeilen in der Eingabe
     * @param args Argumente der Kommandozeile
     */
    public static void main(String[] args) {
        //TODO: AufgabeB4A1.main(String[] args) --Drafted--
        int[] array_input;
        int k;
        if(!testRun) {
            if (args.length != 1) {
                return;
            }
            try {
                array_input = readStandardIn();
                k = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Error: Encountered problem parsing input, required: number");
                return;
            }
            if (array_input.length == 0) {
                return;
            }
        }else{
            array_input = generateTest(test_size);
            k = rand(0, test_size - 1);
            MaxHeap test = new MaxHeap(array_input);
            System.out.println(Arrays.toString(test.getValues()));
            return;
        }
        System.out.println(heapSelect(array_input, k));
        System.out.println(heapSelectFast(array_input, k));
    }

    /**
     * Formatiert die Standard-In Eingabe in ein Array von ganzen Zahlen
     * @Runtime O(n) - n ist die Anzahl an Zeilen in der Eingabe
     * @return Array, das die Eingabe enthält
     * @throws NumberFormatException wenn eine Eingabe nicht als Zahl interpretiert werden kann
     */
    public static int[] readStandardIn() throws NumberFormatException{
        Scanner in = new Scanner(System.in);
        ArrayList<Integer> list = new ArrayList<>();
        try {
            while (in.hasNextLine()) {
                int current = Integer.parseInt(in.nextLine());
                list.add(current);
            }
        }catch(NumberFormatException e){
            System.err.println("Error: Encountered problem parsing the input.");
            throw e;
        }
        int[] arr = new int[list.size()];
        for(int i = 0; i < arr.length; i++){
            arr[i] = list.get(i);
        }
        return arr;
    }

    /**
     * Berechnet mit einem Maxheap das k-kleinste Element in O(n log(n)) und gibt dieses zurück. Das eingegebene Array wird dabei nicht verändert.
     * @Runtime O(n log n) - n ist die Größe des übergebenen Arrays
     * @param arr Das Array in dem gesucht wird
     * @param k Suchvariable
     * @return
     */
    public static int heapSelect(int[] arr, int k) {
        //TODO: AufgabeB4A1.heapSelect(int[] arr, int k) --Drafted--
        MaxHeap heap = new MaxHeap(arr.clone());
        for(int i = heap.getSize(); i > k; i--){
            heap.extractMax();
        }
        return heap.extractMax();
    }

    /**
     * Berechnet mit einem Maxheap das k-kleinste Element in O(n log(n)) und gibt dieses zurück. Das eingegebene Array wird dabei nicht verändert.
     * @Runtime O(n log k) - n ist die Größe des übergebenen Arrays
     * @param arr Das Array in dem gesucht wird
     * @param k Suchvariable
     * @return
     */
    public static int heapSelectFast(int[] arr, int k) {
        //TODO: AufgabeB4A1.heapSelectFast(int[] arr, int k) --Drafted--
        MaxHeap heap = new MaxHeap(k);
        //Fülle MaxHeap komplett auf (k Elemente)
        for(int i = 0; i < k; i++){
            int value = arr[i];
            heap.add(value);
        }
        //Wir haben nun k Werte <= als max(MaxHeap)
        for(int i = k; i < arr.length; i++){
            int currentMax = heap.peekMax();
            int value = arr[i];
            if(value < currentMax){
                //Fall: neuer Wert ist kleiner als größter Wert des Heaps => Entfernen das größte Element, reihen neues kleinere Element ein
                heap.extractMax();
                heap.add(value);
            }
            //Alle Werte größer oder gleich den bisherigen Elementen können ignoriert werden
        }
        return heap.extractMax();
    }

    /**
     * Erzeugt eine pseudo-zufällige Zahl zwischen den beiden Zahlen
     * @Runtime O(?)
     * @param lowerBound untere Schranke (inklusive)
     * @param upperBound obere Schranke (inklusive)
     * @return Zufallszahl
     */
    private static int rand(int lowerBound, int upperBound){
        double f = Math.random()/Math.nextDown(1.0);
        return (int)(lowerBound * (1.0 - f) + upperBound * f);
    }

    /**
     * Generiert ein zufälliges Array für einen Test mit der übergebenen Länge. Alle Werte haben einen Wert zwischen 0 und 100
     * @Runtime O(n)
     * @param size Länge des generierten Arrays
     * @return Ein Array der Länge size mit zufälligen Werten zwischen 0 und 100
     */
    private static int[] generateTest(int size){
        int[] out = new int[size];
        for(int i = 0; i < out.length; i++){
            out[i] = (int)(Math.random() * 100);
        }
        return out;
    }
}
