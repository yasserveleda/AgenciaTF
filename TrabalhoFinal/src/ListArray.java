
public class ListArray<E> implements ListTAD<E> {

    private static final int INITIAL_SIZE = 10;
    private E[] data;
    private int count;
    private int capacity;

    public ListArray(){}

    public ListArray(int c){}

    @Override
    public void clear(){}

    @Override
    public boolean isEmpty(){return false;}

    @Override
    public int size(){return 0;}

    @Override
    public void add(E element){}
    
    @Override
    public void add(int index, E element){}

    @Override
    public E remove(int index){return null;}

    @Override
    public boolean remove(E element){return false;}

    @Override
    public E get(int index){return null;}
    
    @Override
    public E set(int index, E element){return null;}

    @Override
    public boolean contains(E element){return false;}
    
    @Override
    public int indexOf(E element){return 0;}

    private void setCapacity(int newCapacity){}

    @Override
    public String toString(){return "";}
    
    public E[] subList(int fromIndex, int toIndex){return null;}
    

}
