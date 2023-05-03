import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

public class AufgabeB4A2 {

    /**
     * public static void main(String[] args) soll wie in Aufgabe 1 eine Ganzzahl k als
     * erstes Argument und eine Folge von n Ganzzahlen aus Standard-In entgegennehmen. Anschließend soll jede der vier Select-Methoden (heapSelect, heapSelectFast,
     * quickSelectFirst, quickSelectRandom) jeweils 20-mal ausgeführt und jeweils die mittlere Laufzeit in Millisekunden ausgegeben werden. Messen Sie dabei ausschließlich die
     * Laufzeit der Select-Methoden. Probieren Sie unterschiedliche Eingaben (sortiert, gemischt, verschiedene Größen) aus und notieren Sie Ihre Beobachtungen, sodass Sie diese
     * dem Tutor im Praktikum präsentieren können
     * @param args
     */
    public static void main(String[] args) {
        //TODO: AufgabeB4A2.main(String[] args)
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
        }
        //Average array -> System.out.println(...)
        for(int i = 0; i < times.length; i++){
            System.out.println("<Algorithm " + (i + 1) + ">: " + avg[i] + "ms");
        }
    }

    /**
     * Formatiert die Standard-In Eingabe in ein Array von ganzen Zahlen
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
     * @param arr
     * @param l
     * @param r
     * @param p
     * @return
     */
    public static int partition(int[] arr, int l, int r, int p) {
        //TODO: AufgabeB4A2.partition(int[] arr, int l, int r, int p)
        return -1;
    }

    /**
     * public static int quickSelectFirst(int[] arr, int k) soll nach dem oben beschriebenen Verfahren partition nutzen, um das k-kleinste Element aus arr zurückzugeben, wobei als Pivot-Element immer der kleinste zulässige Index (p = l) verwendet
     * werden soll. Das eingegebene Array arr darf dabei nicht verändert werden!
     * @param arr
     * @param k
     * @return
     */
    public static int quickSelectFirst(int[] arr, int k) {
        //TODO: AufgabeB4A2.quickSelectFirst(int[] arr, int k)
        return -1;
    }

    /**
     * public static int quickSelectRandom(int[] arr, int k) soll nach dem oben beschriebenen Verfahren partition nutzen, um das k-kleinste Element aus arr zurückzugeben, wobei als Pivot-Element immer ein zufälliger zulässiger Index (l ≤ p ≤ r) verwendet
     * werden soll. Das eingegebene Array arr darf dabei nicht verändert werden!
     * @param arr
     * @param k
     * @return
     */
    public static int quickSelectRandom(int[] arr, int k) {
        //TODO: AufgabeB4A2.partition(int[] arr, int k)
        return -1;
    }

}
