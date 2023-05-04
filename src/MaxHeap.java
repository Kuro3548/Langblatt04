public class MaxHeap {

    private final int[] content;
    private int size; //Aktuelle Anzahl an Elementen
    private final int capacity; //Anzahl der Elemente, die im Heap gespeichert werden können (content.length - 1)

    /**
     * Erstellt eine MaxHeap-Instanz und initialisiert alle Werte
     * @Runtime O(1)
     * @param capacity Maximale Anzahl an Werten, die der MaxHeap speichern kann
     */
    public MaxHeap(int capacity){
        //TODO: MaxHeap.MaxHeap(int capacity) --Drafted--
        content = new int[capacity + 1]; //We dont use the first index
        this.capacity = capacity;
        this.size = 0;
    }

    /**
     * Erstellt aus dem übergebenen Array einen MaxHeap
     * @Runtime O(n log n) - n ist die Größe des übergebenen Arrays
     * @param values Die Anfangsdaten des MaxHeaps
     * @param capacity Maximale Anzahl an Werten, die der MaxHeap speichern kann
     */
    public MaxHeap(int[] values, int capacity){
        content = new int[capacity + 1]; //We dont use the first index
        this.capacity = capacity;
        this.size = 0;
        for(int i = 0; i < values.length; i++){
            add(values[i]);
        }
    }

    /**
     * Erstellt aus dem übergebenen Array einen MaxHeap
     * @Runtime O(n log n) - n ist die Größe des übergebenen Arrays
     * @param values
     */
    public MaxHeap(int[] values){
        content = new int[values.length + 1]; //We dont use the first index
        this.capacity = values.length;
        this.size = 0;
        for(int i = 0; i < values.length; i++){
            add(values[i]);
        }
    }

    /**
     * Gibt die Anzahl an aktuell im MaxHeap gespeicherten Werten aus
     * @Runtime O(1)
     * @return Derzeitiger interner Wert von Attribut size im MaxHeap
     */
    public int getSize(){
        //TODO: MaxHeap.getSize() --Drafted--
        return size;
    }

    /**
     * Gibt die maximal mögliche Anzahl der im MaxHeap gespeicherten Werte aus
     * @Runtime O(1)
     * @return Interner Wert von Attribut capacity im MaxHeap
     */
    public int getCapacity() {
        //TODO: MaxHeap.getCapacity() --Drafted--
        return capacity;
    }

    /**
     * Gibt die aktuell im MaxHeap enthaltenen Werte in einem Array der Länge getSize() zurück
     * @Runtime O(n) - n ist Größe des Heaps
     * @return Array mit dem Inhalt des Heaps
     */
    public int[] getValues() {
        //TODO: MaxHeap.getValues() --Drafted--
        int[] out = new int[size];
        for(int i = 1; i < out.length; i++){
            out[i] = content[i];
        }
        return out;
    }

    /**
     * Fügt den übergebenen Wert in den MaxHeap ein
     * @Runtime O(log n) - n ist Größe des Heaps
     * @param value Der Wert, der in den Heap eingefügt werden soll
     * @throws IllegalStateException - Falls der MaxHeap bereits voll ist
     */
    public void add(int value) throws IllegalStateException {
        //TODO: MaxHeap.add(int value) --Drafted--
        if(size == capacity){
            throw new IllegalStateException("Error: Tried adding value to full Heap");
        }
        size++;
        content[size] = value;
        int i = size;
        while(i != 1 && content[i] >= content[i / 2]){
            int temp = content[i];
            content[i] = content[i / 2];
            content[i / 2] = temp;
            assert testNode(i);
            i = i >> 1;
        }
        assert isHeap();
    }

    /**
     * Gibt den größten im MaxHeap enthaltenen Wert zurück und entfernt ihn aus dem MaxHeap
     * @Runtime O(1)
     * @return Der größte Wert im MaxHeap (Wurzel)
     * @throws IllegalStateException - Falls der MaxHeap leer ist
     */
    public int extractMax() throws IllegalStateException {
        //TODO: MaxHeap.extractMax() --Drafted--
        if(size == 0){
            throw new IllegalStateException("Error: Tried removing element from empty Heap");
        }
        int out = content[1];
        content[1] = content[size];
        size--; //Object content[former_size] persists, but size is decremented. It will be ignored and overridden
        assert isHeap();
        return out;
    }

    /**
     * Gibt den größten im MaxHeap enthaltenen Wert zurück
     * @Runtime O(1)
     * @return Der größte Wert im MaxHeap (Wurzel)
     * @throws IllegalStateException - Falls der MaxHeap leer ist
     */
    public int peekMax() throws IllegalStateException {
        //TODO: MaxHeap.peekMax() --Drafted--
        if(size == 0){
            throw new IllegalStateException("Error: Tried accessing element of empty Heap");
        }
        return content[1];
    }

    /**
     * Stellt die MaxHeap-Eigenschaft wieder her
     * @Runtime O(log n) - n ist die Größe des Heaps
     * @param i Die Wurzel des MaxHeaps, dessen Eigenschaft wiederhergestellt wird
     */
    public void maxHeapify(int i) {
        //TODO: MaxHeap.maxHeapify(i) --Drafted--
        int leftChild = 2 * i;
        int rightChild = 2 * i + 1;
        if(leftChild > size){
            //Case: Leaf
            return;
        }
        int largerChild;
        if(leftChild < size && rightChild > size){
            //Case: Only 1 Child
            largerChild = leftChild;
        }else{
            //Default: 2 Children
            largerChild = (content[leftChild] > content[rightChild])? leftChild : rightChild;
        }
        int temp = content[largerChild];
        content[largerChild] = content[i];
        content[i] = temp;
        assert testNode(i);
        maxHeapify(largerChild);
    }

    /**
     * Prüft, ob ein Knoten und seine 2 Kinder die MaxHeap-Eigenschaft erfüllen
     * @param i Der Knoten, der überprüft wird
     * @return Wahr, wenn der Knoten keine größeren Kinder hat
     */
    private boolean testNode(int i){
        boolean hasNoChildren = 2 * i > size;
        boolean hasOneChild = 2 * i + 1 > size && !hasNoChildren;
        if(hasNoChildren){
            return true;
        }
        if(hasOneChild){
            return content[2 * i] > content[i];
        }
        return (content[2 * i] > content[i] && content[2 * i + 1] > content[i]);
    }

    /**
     * Prüft, ob ein gesamter Teilbaum die MaxHeap-Eigenschaft erfüllt
     * @param i Die Wurzel des zu prüfenden Teilbaumes
     * @return Wahr, wenn alle Knoten im Teilbaum die MaxHeap-Eigenschaft erfüllen
     */
    private boolean testSubtree(int i){
        boolean node = testNode(i);
        if(!node){
            return false;
        }
        boolean left = testSubtree(2 * i);
        boolean right = testSubtree(2 * i + 1);
        return (left && right);
    }

    /**
     * Prüft, ob der gesamte Heap die MaxHeap-Eigenschaft korrekt erfüllt
     * @return Wahr, wenn der gesamte Heap ein MaxHeap ist
     */
    private boolean isHeap(){
        for(int i = 1; i < size; i++){
            if(!testNode(i)){
                return false;
            }
        }
        return true;
    }
}
