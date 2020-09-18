import java.util.LinkedList;

public class Cache<T> {
    private LinkedList<T> elems;
    private int maxCapacity, hits, refs;

    public Cache() {
        init();
    }

    public Cache(int maxCapacity) {
        init();
        this.maxCapacity = maxCapacity;
    }

    private void init() {
        this.elems = new LinkedList<T>();
    }

    public T getObject() {
        return elems.getLast();
    }

    public void addObject(T elem, Position position) {
        if (elems.size() == maxCapacity) {
            elems.removeLast();
        }

        if(position == Position.TOP) {
            elems.addFirst(elem);
        } else if(position == Position.BOTTOM) {
            elems.addLast(elem);
        }
    }

    public void removeObject(T elem) {
        elems.remove(elem);
    }

    /**
     * If an elements exists in the cache,
     * it must be moved to the top of the
     * cache.
     * 
     * @param elem; The element to move.
     */
    public void moveToTop(T elem) {
        removeObject(elem);
        addObject(elem, Position.TOP);
    }

    /**
     * Reset cache settings and elements.
     */
    public void clearCache() {
        elems.clear();
        hits = 0;
        refs = 0;
        maxCapacity = 0;
    }

    /**
     * Searches the cache for the given elem.
     * Increments number of references and hits
     * which are used to caculate hit ratio.
     * 
     * @param elem
     * @return true if elem in cache; otherwise, false.
     */
    public boolean search(T elem) {
        boolean elemExists = false;
        refs += 1;

        if (elems.contains(elem)) {
            hits += 1;
            elemExists = true;
        } else {
            
        }

        return elemExists;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getSize() {
        return elems.size();
    }

    public int getHits() {
        return hits;
    }

    public int getRefs() {
        return refs;
    }

    private boolean isEmpty() {
        return elems.size() == 0;
    }

    /**
     * Get clone of cache elements.
     * 
     * @return clone of elements in list.
     */
    public LinkedList<T> getElems() {
        LinkedList<T> clone = new LinkedList<T>();

        for (T t : elems) {
            clone.addLast(t);
        }

        return clone;
    }

    /**
     * Prints the list elements toString() value one by one on a new line.
     */
    public String toString() {
        StringBuilder string = new StringBuilder();

        for(T elem : elems) {
            string.append(elem + "\n");
        }

        return string.toString();
    }
}