import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

public class AufgabeB4A2 {
    public static boolean testRun = false;
    public static int test_size = (int)Math.pow(2, 18);
    public static int test_iterations = 20;

    /**
     * Erhält eine Ganzzahl k erhalten und anschließend aus Standard-In eine Reihe von n Ganzzahlen.
     * Anschließend wird jede der vier Select-Methoden mehrfach ausgeführt und deren mittlere Laufzeit in Millisekunden ausgegeben werden. Messen Sie dabei ausschließlich die
     * @Runtime O(20 * 4)(O(n log n) + O(n log k)) = O(n log n) + O(n log k) <= O(n log n), n ist die Anzahl an Zeilen in der Eingabe, k ist das Argument der Kommandozeile
     * @param args Argumente der Kommandozeile
     */
    public static void main(String[] args) {
        //TODO: AufgabeB4A2.main(String[] args)
        int[] array_input;
        int k;
        int iterations;
        if(!testRun){
            if(args.length != 1){
                return;
            }
            try{
                array_input = readStandardIn();
                k = Integer.parseInt(args[0]);
            }catch(NumberFormatException e){
                System.out.println("Error: Encountered problem parsing commandline-argument, required: number");
                return;
            }
            if(array_input.length == 0){
                return;
            }
            iterations = 20;
        }else{
            array_input = generateTest(test_size);
            k = rand(1, test_size - 1);
            iterations = test_iterations;
        }
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
            System.out.println(quickSelectFirst(array_input, k));
            end = Instant.now();
            times[2][i] = (int)Duration.between(start, end).toMillis();

            start = Instant.now();
            System.out.println(quickSelectRandom(array_input, k));
            end = Instant.now();
            times[3][i] = (int)Duration.between(start, end).toMillis();
        }
        evaluateTime(times);
    }

    /**
     * Mittelt Zeitmessungen von verschiedenen Algorithmen und gibt das Ergebnis aus.
     * @Runtime O(n * k) - n ist die Anzahl an Arrays, k ist die Größe jedes Arrays
     * @param times Ein Array mit Zeitmessungen von verschiedenen Algorithmen
     */
    public static void evaluateTime(int[][] times){
        for(int i = 0; i < times.length; i++){
            int sum = 0;
            for(int j = 0; j < times[i].length; j++){
                sum += times[i][j];
            }
            int avg = sum / times[i].length;
            System.out.println("<Algorithm " + (i + 1) + ">: " + avg + "ms");
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
     * @param left Die untere Schranke des betrachteten Bereichs: 0 ≤ l ≤ r
     * @param right Die obere Schranke des betrachteten Bereichs: r < arr.length
     * @param p Die Stelle des Pivot-Elements: l ≤ p ≤ r
     * @return Die neue Stelle des Pivot-Elements und die Grenze der Partitionen (l ≤ p_neu ≤ r)
     */
    public static int partition(int[] arr, int left, int right, int p){
        //TODO: AufgabeB4A2.partition(int[] arr, int l, int r, int p)
        swap(arr, p, right);
        int pivot_index = right;
        int bound = left;
        for(int i = left; i < right; i++) {
            //Verschiebe alle kleineren Elemente nach links
            if(arr[i] < arr[pivot_index]) {
                swap(arr, bound, i);
                bound++;
            }
        }
        swap(arr, bound, pivot_index);
        return bound;
    }

    /**
     * Bestimmt das k-kleinste Element aus arr und gibt dieses zurück. Das Pivot-Element ist immer der kleinste zulässige Index (p = l). Das eingegebene Array arr wird dabei nicht verändert.
     * @Runtime O(n log n) - n ist Größe des übergebenen Arrays
     * @param arr Das Array in dem gesucht wird
     * @param k Such-Variable
     * @return Das k-kleinste Element aus dem Array
     */
    public static int quickSelectFirst(int[] arr, int k){
        //TODO: AufgabeB4A2.quickSelectFirst(int[] arr, int k)
        int[] copy = arr.clone();
        return quickSelectFirst(copy, 0, copy.length - 1, k);
    }
    private static int quickSelectFirst(int[] array, int left, int right, int k){
        if(left == right){
            return array[left];
        }
        int pivotIndex = partition(array, left, right, left); //Pivot ist immer linkestes Element
        if(k - 1 == pivotIndex){
            //Fall: Genau k Elemente sind unter der Grenze → Gebe Grenze aus
            return array[pivotIndex];
        }else if(k - 1 < pivotIndex){
            //Fall: Mehr als k Elemente sind unter der Grenze → Suche weiter in der unteren Hälfte
            return quickSelectFirst(array, left, pivotIndex - 1, k);
        }else{
            //Fall: Weniger als k Elemente sind unter der Grenze → Suche in der oberen Hälfte
            return quickSelectFirst(array, pivotIndex + 1, right, k);
        }
    }
    /**
     * Bestimmt das k-kleinste Element aus arr und gibt dieses zurück. Das Pivot-Element ist immer ein zufälliger zulässiger Index (l ≤ p ≤ r). Das eingegebene Array arr wird dabei nicht verändert.
     * @Runtime O(n log n)
     * @param arr Das Array in dem gesucht wird
     * @param k Such-Variable
     * @return Das k-kleinste Element aus dem Array
     */
    public static int quickSelectRandom(int[] arr, int k){
        int[] copy = arr.clone();
        return quickSelectRandom(copy, 0, copy.length - 1, k);
    }
    private static int quickSelectRandom(int[] array, int left, int right, int k){
        if(left == right){
            return array[left];
        }
        int pivotIndex = partition(array, left, right, rand(left, right)); //Pivot ist zufälliges Element zwischen Links und Rechts
        if(k - 1 == pivotIndex){
            //Fall: Genau k Elemente sind unter der Grenze → Gebe Grenze aus
            return array[pivotIndex];
        }else if(k - 1 < pivotIndex){
            //Fall: Mehr als k Elemente sind unter der Grenze → Suche weiter in der unteren Hälfte
            return quickSelectRandom(array, left, pivotIndex - 1, k);
        }else{
            //Fall: Weniger als k Elemente sind unter der Grenze → Suche in der oberen Hälfte
            return quickSelectRandom(array, pivotIndex + 1, right, k);
        }
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
     * Swaps the elements at the two indices
     * @Runtime O(1)
     * @param array The array with the elements
     * @param a Index 1
     * @param b Index 2
     */
    private static void swap(int[] array, int a, int b){
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
    /**
     * Generiert ein zufälliges Array für einen Test mit der übergebenen Länge. Alle Werte haben einen Wert zwischen 0 und 100
     * @param size Länge des generierten Arrays
     * @return Ein Array der Länge size mit zufälligen Werten zwischen 0 und 100
     */
    private static int[] generateTest(int size){
        int[] out = new int[size];
        for(int i = 0; i < out.length; i++){
            out[i] = rand(0, 100);
        }
        return out;
    }
    //Scraps:

    /*
    public static int quickSelectFirst(int[] arr, int k) {
        int[] clone = arr.clone();
        if(clone.length == 1){
            return clone[0];
        }
        int bound = partition(clone, 0, clone.length, 0);
        if(bound == k){
            //Fall: Links von bound sind k Elemente
            return clone[bound];
        }
        if(k < bound){
            //Fall: Links von bound sind mehr als k Elemente / k ist Links
            int[] smaller = Arrays.copyOfRange(clone, 0, bound - 1);
            return quickSelectFirst(smaller, k);
        }
        //Fall: Links von bound sind weniger als k Elemente / k ist Rechts
        int[] bigger = Arrays.copyOfRange(clone, bound + 1, clone.length);
        return quickSelectFirst(bigger, k - bound - 1);
    }*/

    /*public static int quickSelectRandom(int[] arr, int k)
        if(arr.length == 1){
            return arr[0];
        }
        int[] clone = arr.clone();
        int bound = partition(clone, 0, clone.length, rand(0, clone.length - 1));
        if(bound == -1){
            System.out.println("Partition returned -1");
        }
        if(bound == k - 1){
            //Fall: In clone sind k-Elemente kleiner
            return clone[bound];
        }
        if(bound > k - 1){
            //Fall: Links von bound sind mehr als k Elemente
            int[] smaller = Arrays.copyOfRange(clone, 0, bound);
            return quickSelectRandom(smaller, k);
        }
        //Fall: Links von bound sind weniger als k Elemente
        int[] bigger = Arrays.copyOfRange(clone, bound + 1, clone.length);
        return quickSelectRandom(bigger, k - bound - 1);
    }

     */

    /*
    public static int partition(int[] arr, int l, int r, int p) {
        if(l == r){
            return l;
        }
        int pivot_index = p;
        int bound = l;
        for(int i = l; i <= r; i++){
            if(arr[i] > arr[pivot_index]){
                int temp = arr[i];
                arr[i] = arr[bound];
                arr[bound] = temp;
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
     */
}
