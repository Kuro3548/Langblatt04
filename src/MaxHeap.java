public class MaxHeap {
    private final int[] content;
    private int size; //Aktuelle Anzahl an Elementen; zeigt immer auf den nächsten leeren Platz
    //Zusätzliches Attribut capacity ist konstant content.length und kann daher über das Array erreicht werden

    /**
     * Erstellt eine MaxHeap-Instanz und initialisiert alle Werte
     * @Runtime O(1)
     * @param capacity Maximale Anzahl an Werten, die der MaxHeap speichern kann
     */
    public MaxHeap(int capacity){
        //TODO: MaxHeap.MaxHeap(int capacity)
        content = new int[capacity];
        this.size = 0;
    }

    /**
     * Gibt die Anzahl an aktuell im MaxHeap gespeicherten Werten aus
     * @Runtime O(1)
     * @return Derzeitiger interner Wert von Attribut size im MaxHeap
     */
    public int getSize(){
        //TODO: MaxHeap.getSize()
        return size;
    }
    /**
     * Gibt die maximal mögliche Anzahl der im MaxHeap gespeicherten Werte aus
     * @Runtime O(1)
     * @return Interner Wert von Attribut capacity im MaxHeap
     */
    public int getCapacity(){
        //TODO: MaxHeap.getCapacity()
        return content.length;
    }
    /**
     * Gibt die aktuell im MaxHeap enthaltenen Werte in einem Array der Länge getSize() zurück
     * @Runtime O(n) - n ist Größe des Heaps
     * @return Array mit dem Inhalt des Heaps
     */
    public int[] getValues(){
        //TODO: MaxHeap.getValues()
        int[] out = new int[size];
        for(int i = 0; i < out.length; i++){
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
    public void add(int value) throws IllegalStateException{
        //TODO: MaxHeap.add(int value)
        if(size == content.length){
            throw new IllegalStateException("Error: Tried adding value to full Heap");
        }
        //Füge neues Element am Ende hinzu
        content[size] = value;
        //Lass das Element nach oben an die korrekte Stelle wandern
        int i = size;
        int j = parent(i);
        while(i != j && content[i] >= content[parent(i)]){
            swap(i, j);
            assert checkNode(i);
            i = j;
            j = parent(i);
        }
        //"Registriert" das neue Element
        size++;
        assert isHeap();
    }

    /**
     * Gibt den größten im MaxHeap enthaltenen Wert zurück und entfernt ihn aus dem MaxHeap
     * @Runtime O(1)
     * @return Der größte Wert im MaxHeap (Wurzel)
     * @throws IllegalStateException - Falls der MaxHeap leer ist
     */
    public int extractMax() throws IllegalStateException{
        //TODO: MaxHeap.extractMax()
        if(size == 0){
            throw new IllegalStateException("Error: Tried removing element from empty Heap");
        }
        //Tauscht Start und Ende
        size--;
        int out = content[0];
        content[0] = content[size];
        //content[size] behält seinen Wert, er ist aber nicht mehr im Heap "registriert" (unter size). Daher wird er bei der nächsten Möglichkeit überschrieben.
        maxHeapify(0);
        assert isHeap();
        return out;
    }

    /**
     * Gibt den größten im MaxHeap enthaltenen Wert zurück
     * @Runtime O(1)
     * @return Der größte Wert im MaxHeap (Wurzel)
     * @throws IllegalStateException - Falls der MaxHeap leer ist
     */
    public int peekMax() throws IllegalStateException{
        //TODO: MaxHeap.peekMax()
        if(size == 0){
            throw new IllegalStateException("Error: Tried accessing element of empty Heap");
        }
        return content[0];
    }

    /**
     * Stellt die MaxHeap-Eigenschaft wieder her
     * @Runtime O(log n) - n ist die Größe des Heaps
     * @param i Die Wurzel des MaxHeaps, dessen Eigenschaft wiederhergestellt wird
     */
    public void maxHeapify(int i){
        if(isLeaf(i)){
            return;
        }
        int left = leftChild(i);
        int right = rightChild(i);
        //Wenn ein Kind-Element größer als die Wurzel ist
        if(content[i] < content[left] || content[i] < content[right]){
            if(content[left] > content[right]){
                //Fall: Linkes Kind ist größer
                swap(i, left);
                maxHeapify(left);
            }else{
                //Fall: Rechtes Kind ist größer(oder gleich)
                swap(i, right);
                maxHeapify(right);
            }
        }
    }


    /**
     * Prüft, ob ein Knoten und seine 2 Kinder die MaxHeap-Eigenschaft erfüllen
     * @Runtime O(1)
     * @param i Der Knoten, der überprüft wird
     * @return Wahr, wenn der Knoten keine größeren Kinder hat
     */
    private boolean checkNode(int i){
        //Ein Element außerhalb des Heaps oder ein Knoten ohne Kinder stören die Heap-Bedingung nicht
        if(i >= size || isLeaf(i)){
            return true;
        }
        boolean hasOnlyOneChild = rightChild(i) > size && leftChild(i) < size;
        if(hasOnlyOneChild){
            return content[leftChild(i)] <= content[i];
        }
        return (content[leftChild(i)] <= content[i] && content[rightChild(i)] <= content[i]);
    }
    /**
     * Prüft, ob der gesamte Heap die MaxHeap-Eigenschaft korrekt erfüllt
     * @Runtime O(n)
     * @return Wahr, wenn der gesamte Heap ein MaxHeap ist
     */
    private boolean isHeap(){
        for(int i = 0; i < size; i++){
            if(!checkNode(i)){
                return false;
            }
        }
        return true;
    }
    /**
     * Gibt die Position Elternknoten eines Knotens aus
     * @Runtime O(1)
     * @param i Der Unterknoten
     * @return Der Knoten, der den Knoten i als direkten Teilbaum hat
     */
    private int parent(int i){ return (i - 1) / 2; }
    /**
     * Gibt die Position linken Teilbaums eines Knotens aus
     * @Runtime O(1)
     * @param i Der Knoten, von dem der linke Teilbaum gesucht wird
     * @return Die Position des linken Teilbaums des Knotens der Position i
     */
    private int leftChild(int i){ return (2 * i) + 1; }
    /**
     * Gibt die Position rechten Teilbaums eines Knotens aus
     * @Runtime O(1)
     * @param i Der Knoten, von dem der rechte Teilbaum gesucht wird
     * @return Die Position des rechten Teilbaums des Knotens der Position i
     */
    private int rightChild(int i){
        return (2 * i) + 2;
    }
    /**
     * Prüft, ob ein Knoten ein Blatt (Knoten der letzten Ebene, besitzt keine Kinder) ist
     * @Runtime O(1)
     * @param i Der zu überprüfende Knoten
     * @return Wahrheitswert, ob der Knoten ein Blatt des Heaps ist
     */
    private boolean isLeaf(int i){
        if((size / 2) <= i && i <= size){
            return true;
        }
        return false;
    }
    /**
     * Tauscht die Elemente von 2 Positionen
     * @Runtime O(1)
     * @param x1 Der Index des ersten Elements
     * @param x2 Der Index des zweiten Elements
     */
    private void swap(int x1, int x2){
        int temp;
        temp = content[x1];
        content[x1] = content[x2];
        content[x2] = temp;
    }

}
