public class MaxHeap {

    private int[] content;
    private int size;

    /**
     * public MaxHeap(int capacity) soll die maximale Anzahl Werte, die der MaxHeap
     * speichern kann entgegennehmen und die MaxHeap-Instanz mit allen notwendigen Attributen initialisieren.
     * @param capacity
     */
    public MaxHeap(int capacity){
        content = new int[capacity];
        this.size = 0;
    }

    /**
     * public int getSize() soll die aktuelle Anzahl der im MaxHeap gespeicherten Werte
     * zurückgeben.
     * @return
     */
    public int getSize(){
        return size;
    }

    /**
     * public int getCapacity() soll die maximal mögliche Anzahl der im MaxHeap gespeicherten Werte zurückgeben.
     * @return
     */
    public int getCapacity() {
        return content.length;
    }

    /**
     * public int[] getValues() soll die aktuell im MaxHeap enthaltenen Werte in einem
     * Array der Länge getSize() zurückgeben.
     * @return
     */
    public int[] getValues() {
        int[] out = new int[size];
        for(int i = 0; i < out.length; i++){
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
     * @param value
     * @throws IllegalStateException
     */
    public void add(int value) throws IllegalStateException {
        //TODO: MaxHeap.add(int value)
        if(size == content.length){
            throw new IllegalStateException("Error: Tried adding value to full Heap");
        }
        size++;
        content[size] = value;
        maxHeapify(0);
    }

    /**
     * public int extractMax() soll den größten im MaxHeap enthaltenen Wert zurückgeben und ihn aus dem MaxHeap entfernen. Falls der MaxHeap leer ist, soll die angegebene
     * Exception ausgelöst werden. Denken Sie daran, die Heap-Eigenschaft wieder herzustellen!
     * @return
     * @throws IllegalStateException
     */
    public int extractMax() throws IllegalStateException {
        //TODO: MaxHeap.extractMax()
        if(size == 0){
            throw new IllegalStateException("Error: Tried removing element from empty Heap");
        }
        size--;
        int out = content[0];
        maxHeapify(0);
        return out;
    }

    /**
     * public int peekMax() soll den größten im MaxHeap enthaltenen Wert zurückgeben,
     * ihn allerdings nicht entfernen. Falls der MaxHeap leer ist, soll die angegebene Exception
     * ausgelöst werden.
     * @return
     * @throws IllegalStateException
     */
    public int peekMax() throws IllegalStateException {
        if(size == 0){
            throw new IllegalStateException("Error: Tried accessing element of empty Heap");
        }
        return content[0];
    }

    /**
     * public void maxHeapify(int i) soll die Heap-Eigenschaft für den Teilbaum unterhalb des angegebenen Index herstellen.
     * @param i
     */
    public void maxHeapify(int i) {
        //TODO: MaxHeap.maxHeapify(i)
    }
}
