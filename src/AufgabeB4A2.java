import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class AufgabeB4A2 {

    /**
     * public static void main(String[] args) soll wie in Aufgabe 1 eine Ganzzahl k als
     * erstes Argument und eine Folge von n Ganzzahlen aus Standard-In entgegennehmen. Anschließend soll jede der vier Select-Methoden (heapSelect, heapSelectFast,
     * quickSelectFirst, quickSelectRandom) jeweils 20-mal ausgeführt und jeweils die mittlere Laufzeit in Millisekunden ausgegeben werden. Messen Sie dabei ausschließlich die
     * Laufzeit der Select-Methoden. Probieren Sie unterschiedliche Eingaben (sortiert, gemischt, verschiedene Größen) aus und notieren Sie Ihre Beobachtungen, sodass Sie diese
     * dem Tutor im Praktikum präsentieren können
     * @Runtime O(n) + O(20 * 4)(O(n log n) + O(n log k) + O(quickSelectFirst) + O(quickSelectRandom)) =
     * @param args
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
     * Evaluates a 2D-array of runtime and prints the result
     * @Runtime O(n * k) - n ist die Anzahl an Arrays, k ist die Größe jedes Arrays
     * @param times
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
     * @Runtime O(n) - n ist die Anzahl an Zeilen in Eingabe
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
     * public static int partition(int[] arr, int l, int r, int p) entspricht der
     * partition-Methode von Langblatt 1 (Blatt 2). Allerdings soll diesmal eine zusätzliche
     * Ganzzahl p übergeben werden, die die Position des zu verwendenden Pivot-Elements
     * angibt mit l ≤ p ≤ r.
     * @Runtime O(n) - n ist Größe des übergebenen Arrays
     * @param arr
     * @param l
     * @param r
     * @param p
     * @return
     */
    public static int partition(int[] arr, int l, int r, int p) {
        //TODO: AufgabeB4A2.partition(int[] arr, int l, int r, int p) --Drafted--
        if(p < l || r < p){
            throw new IllegalArgumentException("Error: Pivot Element is outside Range");
        }
        if(l >= r){
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
     * public static int quickSelectFirst(int[] arr, int k) soll nach dem oben beschriebenen Verfahren partition nutzen, um das k-kleinste Element aus arr zurückzugeben,
     * wobei als Pivot-Element immer der kleinste zulässige Index (p = l) verwendet werden soll. Das eingegebene Array arr darf dabei nicht verändert werden!
     * @Runtime O(n log n) - n ist Größe des übergebenen Arrays
     * @param arr
     * @param k
     * @return
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
     * public static int quickSelectRandom(int[] arr, int k) soll nach dem oben beschriebenen Verfahren partition nutzen, um das k-kleinste Element aus arr zurückzugeben, wobei als Pivot-Element immer ein zufälliger zulässiger Index (l ≤ p ≤ r) verwendet
     * werden soll. Das eingegebene Array arr darf dabei nicht verändert werden!
     * @Runtime O(n log n)
     * @param arr
     * @param k
     * @return
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
