import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class AufgabeB4A2 {

    /**
     * Erhält eine Ganzzahl k erhalten und anschließend aus Standard-In eine Reihe von n Ganzzahlen.
     * Anschließend wird jede der vier Select-Methoden mehrfach ausgeführt und deren mittlere Laufzeit in Millisekunden ausgegeben werden. Messen Sie dabei ausschließlich die
     * @Runtime O(20 * 4)(O(n log n) + O(n log k)) = O(n log n) + O(n log k) <= O(n log n), n ist die Anzahl an Zeilen in der Eingabe, k ist das Argument der Kommandozeile
     * @param args Argumente der Kommandozeile
     */
    public static void main(String[] args) {
        //TODO: AufgabeB4A2.main(String[] args) --Drafted--
        int[] array_input = readStandardIn();
        int k;
        if(args.length != 1){
            throw new IllegalArgumentException("Error: No commandline-argument received, expected: 1 number");
        }
        try{
            k = Integer.parseInt(args[0]);
        }catch(NumberFormatException e){
            System.err.println("Error: Encountered problem parsing commandline-argument, required: number");
            throw e;
        }
        int iterations = 20;
        int[][] times = new int[4][iterations];
        for(int i = 0; i < iterations; i++){
            Instant start = Instant.now();
            AufgabeB4A1.heapSelect(array_input, k);
            Instant end = Instant.now();
            times[0][i] = (int)Duration.between(start, end).toMillis();

            start = Instant.now();
            AufgabeB4A1.heapSelectFast(array_input, k);
            end = Instant.now();
            times[1][i] = (int)Duration.between(start, end).toMillis();

            start = Instant.now();
            quickSelectFirst(array_input, k);
            end = Instant.now();
            times[2][i] = (int)Duration.between(start, end).toMillis();

            start = Instant.now();
            quickSelectRandom(array_input, k);
            end = Instant.now();
            times[4][i] = (int)Duration.between(start, end).toMillis();
        }
        evaluateTime(times);
    }

    /**
     * Mittelt Zeitmessungen von verschiedenen Algorithmen und gibt das Ergebnis aus.
     * @Runtime O(n * k) - n ist die Anzahl an Arrays, k ist die Größe jedes Arrays
     * @param times Ein Array mit Zeitmessungen von verschiedenen Algorithmen
     */
    public static void evaluateTime(int[][] times){
        int[] avg = new int[times.length];
        for(int i = 0; i < times.length; i++){
            int sum = 0;
            for(int j = 0; j < times[i].length; j++){
                sum += times[i][j];
            }
            avg[i] = sum / times[i].length;
            System.out.println("<Algorithm " + (i + 1) + ">: " + avg[i] + "ms");
        }
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
     * Partitioniert das Array nach einem Pivot-Element, sodass alle Elemente an tieferen Stellen kleiner oder gleich dem Pivot-Element sind und diese an höheren Stellen größer als das Pivot-Element.
     * @Runtime O(n) - n ist Größe des übergebenen Arrays
     * @param arr Das zu partitionierende Array
     * @param l Die untere Schranke des betrachteten Bereichs: 0 ≤ l ≤ r
     * @param r Die obere Schranke des betrachteten Bereichs: r ≤ arr.length
     * @param p Die Stelle des Pivot-Elements: l ≤ p ≤ r
     * @return Die neue Stelle des Pivot-Elements und die Grenze der Partitionen
     */
    public static int partition(int[] arr, int l, int r, int p) {
        //TODO: AufgabeB4A2.partition(int[] arr, int l, int r, int p) --Drafted--
        if(p < l || r < p){
            throw new IllegalArgumentException("Error: Pivot Element is outside Range");
        }
        if(l > r){
            throw new IllegalArgumentException("Error: Partition of Negative Range");
        }
        if(l == r){
            return l;
        }
        int pivot_index = p;
        int bound = l; //Bound where all Elements left of it are bigger than pivot
        for(int i = l; i <= r; i++){
            //If found Element is smaller than pivot
            if(arr[i] > arr[pivot_index]){
                //Swapping i, bound
                int temp = arr[i];
                arr[i] = arr[bound];
                arr[bound] = temp;
                //Case: Swapped Element was Pivot => Readjust Pivot_Index
                if(bound == pivot_index){
                    pivot_index = i;
                }
                bound++;
            }
        }
        //Putting Pivot to the correct place (to bound)
        int temp = arr[pivot_index];
        arr[pivot_index] = arr[bound];
        arr[bound] = temp;
        return bound;
    }

    /**
     * Bestimmt das k-kleinste Element aus arr und gibt dieses zurück. Das Pivot-Element ist immer der kleinste zulässige Index (p = l). Das eingegebene Array arr wird dabei nicht verändert.
     * @Runtime O(n log n) - n ist Größe des übergebenen Arrays
     * @param arr Das Array in dem gesucht wird
     * @param k Suchvariable
     * @return Das k-kleinste Element aus dem Array
     */
    public static int quickSelectFirst(int[] arr, int k) {
        //TODO: AufgabeB4A2.quickSelectFirst(int[] arr, int k) --Drafted--
        if(arr.length == 1){
            return arr[0];
        }
        int[] clone = arr.clone();
        int bound = partition(clone, 0, clone.length - 1, 0);
        if(bound == k){
            //Fall: In clone sind k-Elemente kleiner
            return clone[bound];
        }
        if(bound > k){
            //Fall: Links von bound sind mehr als k Elemente
            int[] smaller = Arrays.copyOfRange(clone, 0, bound - 1);
            return quickSelectFirst(smaller, k);
        }
        //Fall: Links von bound sind weniger als k Elemente
        int[] bigger = Arrays.copyOfRange(clone, bound + 1, arr.length);
        return quickSelectFirst(bigger, k - bound);
    }

    /**
     * Bestimmt das k-kleinste Element aus arr und gibt dieses zurück. Das Pivot-Element ist immer ein zufälliger zulässiger Index (l ≤ p ≤ r). Das eingegebene Array arr wird dabei nicht verändert.
     * @Runtime O(n log n)
     * @param arr Das Array in dem gesucht wird
     * @param k Suchvariable
     * @return Das k-kleinste Element aus dem Array
     */
    public static int quickSelectRandom(int[] arr, int k) {
        //TODO: AufgabeB4A2.partition(int[] arr, int k) --Drafted--
        if(arr.length == 1){
            return arr[0];
        }
        int[] clone = arr.clone();
        int bound = partition(clone, 0, clone.length - 1, rand(0, clone.length));
        if(bound == k){
            //Fall: In clone sind k-Elemente kleiner
            return clone[bound];
        }
        if(bound > k){
            //Fall: Links von bound sind mehr als k Elemente
            int[] smaller = Arrays.copyOfRange(clone, 0, bound - 1);
            return quickSelectFirst(smaller, k);
        }
        //Fall: Links von bound sind weniger als k Elemente
        int[] bigger = Arrays.copyOfRange(clone, bound + 1, arr.length);
        return quickSelectFirst(bigger, k - bound);
    }

    /**
     * Erzeugt eine pseudo-zufällige Zahl zwischen den beiden Zahlen
     * @Runtime O(?)
     * @param lowerBound untere Schranke (inklusive)
     * @param upperBound obere Schranke (exklusive)
     * @return Zufallszahl
     */
    private static int rand(int lowerBound, int upperBound){
        int range = upperBound - lowerBound;
        return (int)(Math.random() * range + lowerBound);
    }
}
