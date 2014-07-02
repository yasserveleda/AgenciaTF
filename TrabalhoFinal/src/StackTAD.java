

/**
 * Interface Stack que possui o conjunto de metodos
 * utilizado para a implementaçao de pilhas.
 * 
 * @author Isabel H. Manssour 
 */

import java.util.EmptyStackException; 

public interface StackTAD<E> {
	public int size();
	public boolean isEmpty();
	public E top() throws EmptyStackException;
	public void push(E element);
	public E pop() throws EmptyStackException;
    public void clear();
}