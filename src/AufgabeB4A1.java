import java.util.ArrayList;
import java.util.Scanner;

public class AufgabeB4A1 {

    /**
     * public static void main(String[] args) ist der Einstiegspunkt Ihres Programms.
     * Das Programm soll als erstes Argument eine Ganzzahl k erhalten und anschließend
     * aus Standard-In wie auf den vorigen Blättern eine Reihe von n Ganzzahlen einliest.
     * Nachdem Sie die eingelesenen Werte in ein Array überführt haben, soll mittels der
     * Methode heapSelect oder heapSelectFast der k-kleinste Wert gefunden und in der
     * main-Methode via Standard-Out als erste Ausgabezeile ausgegeben werden
     * @Runtime O(n) - n ist die Anzahl an Zeilen in der Eingabe
     * @param args
     */
    public static void main(String[] args) {
        //TODO: AufgabeB4A1.main(String[] args) --Drafted--
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
        System.out.println("HeapSelect returns: " + heapSelect(array_input, k));
        System.out.println("HeapSelectFast returns: " + heapSelectFast(array_input, k));
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
     * public static int heapSelect(int[] arr, int k) soll ein Array arr aus n Ganzzahlen und eine Ganzzahl k entgegennehmen und mittels eines Maxheap das k-kleinste
     * Element in O(n log(n)) berechnen und zurückgeben. Das eingegebene Array darf dabei nicht verändert werden.
     * @Runtime O(n log n) - n ist die Größe des übergebenen Arrays
     * @param arr
     * @param k
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
     * public static int heapSelectFast(int[] arr, int k) soll ein Array arr aus n Ganzzahlen und eine Ganzzahl k entgegennehmen und mittels eines Maxheap das k-kleinste Element in O(n log(k)) berechnen und zurückgeben.
     * Das eingegebene Array darf dabei nicht verändert werden.
     * Hinweis: Woher kommt der Logarithmus in der Laufzeit und welche Größe müssten Sie entsprechend auf k anpassen um diese kleinere Laufzeitschranke einzuhalten?
     * @Runtime O(n log k) - n ist die Größe des übergebenen Arrays
     * @param arr
     * @param k
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
}
