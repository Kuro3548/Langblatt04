public class MaxHeap {

    private int[] content;
    private int size; //Aktuelle Anzahl an Elementen
    private final int capacity; //Anzahl der Elemente, die im Heap gespeichert werden können (content.length - 1)

    /**
     * public MaxHeap(int capacity) soll die maximale Anzahl Werte, die der MaxHeap
     * speichern kann entgegennehmen und die MaxHeap-Instanz mit allen notwendigen Attributen initialisieren.
     * @Runtime O(1)
     * @param capacity
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
     * @param values
     * @param capacity
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
     * public int getSize() soll die aktuelle Anzahl der im MaxHeap gespeicherten Werte
     * zurückgeben.
     * @Runtime O(1)
     * @return
     */
    public int getSize(){
        //TODO: MaxHeap.getSize() --Drafted--
        return size;
    }

    /**
     * public int getCapacity() soll die maximal mögliche Anzahl der im MaxHeap gespeicherten Werte zurückgeben.
     * @Runtime O(1)
     * @return
     */
    public int getCapacity() {
        //TODO: MaxHeap.getCapacity() --Drafted--
        return capacity;
    }

    /**
     * public int[] getValues() soll die aktuell im MaxHeap enthaltenen Werte in einem
     * Array der Länge getSize() zurückgeben.
     * @Runtime O(n) - n ist Größe des Heaps
     * @return
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
     * public void add(int value) soll den übergebenen Wert in den MaxHeap einfügen.
     * Anschließend sollten die Heap-Eigenschaft erfüllt sein. Falls der MaxHeap bereits voll
     * ist, soll die angegebene Exception ausgelöst werden.
     * Hinweis: Zum Einfügen können Sie einen Wert an das Ende der bereits enthaltenen
     * Werte anhängen (neuer Blatt-Knoten) und solange mit den Eltern hochtauschen, bis
     * der Elternknoten größer oder gleich dem eingegebenen Wert ist oder bis Sie bei der
     * Wurzel angekommen sind.
     * @Runtime O(log n) - n ist Größe des Heaps
     * @param value
     * @throws IllegalStateException
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
            assert satisfiesHeap(i);
            i = i >> 1;
        }
        assert isHeap();
    }

    /**
     * public int extractMax() soll den größten im MaxHeap enthaltenen Wert zurückgeben und ihn aus dem MaxHeap entfernen. Falls der MaxHeap leer ist, soll die angegebene
     * Exception ausgelöst werden. Denken Sie daran, die Heap-Eigenschaft wieder herzustellen!
     * @Runtime O(1)
     * @return
     * @throws IllegalStateException
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
     * public int peekMax() soll den größten im MaxHeap enthaltenen Wert zurückgeben,
     * ihn allerdings nicht entfernen. Falls der MaxHeap leer ist, soll die angegebene Exception
     * ausgelöst werden.
     * @Runtime O(1)
     * @return
     * @throws IllegalStateException
     */
    public int peekMax() throws IllegalStateException {
        //TODO: MaxHeap.peekMax() --Drafted--
        if(size == 0){
            throw new IllegalStateException("Error: Tried accessing element of empty Heap");
        }
        return content[1];
    }

    /**
     * public void maxHeapify(int i) soll die Heap-Eigenschaft für den Teilbaum unterhalb des angegebenen Index herstellen.
     * @Runtime O(log n) - n ist die Größe des Heaps
     * @param i
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
        assert satisfiesHeap(i);
        maxHeapify(largerChild);
    }

    private boolean satisfiesHeap(int i){
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

    private boolean isHeap(){
        for(int i = 1; i < size; i++){
            if(!satisfiesHeap(i)){
                return false;
            }
        }
        return true;
    }
}
