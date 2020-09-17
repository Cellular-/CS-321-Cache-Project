import java.util.LinkedList;

public class Cache<T> {
    private LinkedList<T> elems;
    private int maxCapacity, hits, numRefs;

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

    public void clearCache() {
        elems.clear();
        hits = 0;
        numRefs = 0;
    }

    public boolean contains(T elem) {
        numRefs += 1;
        return elems.contains(elem);
    }

    public void addToHits(int amount) {
        hits += amount;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getSize() {
        return elems.size();
    }

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