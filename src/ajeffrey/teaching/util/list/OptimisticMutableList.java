/* Author: Lea Middleton
 * 
 * Program take an Immutablist class and implements
 * a Threadsafe Mutable list data stucture
 */
package ajeffrey.teaching.util.list;
import ajeffrey.teaching.Main;
import java.util.concurrent.atomic.AtomicReference;

import static ajeffrey.teaching.Main.*;

public class OptimisticMutableList extends MutableListImpl {
    private static AtomicReference<MutableList> mutableList = new AtomicReference<MutableList>(MutableList.factory.build());
    private AtomicReference<MutableListImpl> newList;
    private static AtomicReference<ImmutableList> mutable;
    private AtomicReference<Iterator> it;

    public OptimisticMutableList(){
        super();
        this.newList = new AtomicReference<MutableListImpl>((MutableListImpl) mutableList.get());
        this.mutable = new AtomicReference<ImmutableList>(mutableList.get().getListContents());
        this.it = new AtomicReference<Iterator>(mutableList.get().iterator());
    }

    public void add(Object element){
        ImmutableList oldList;
        ImmutableList newList;
        do{
            oldList = getTopList().get();
            MutableList nL = this.newList.get();
            nL.add(element);
            newList= nL.getListContents();
        }while(!getTopList().compareAndSet(oldList,newList));
    }

    public void removeObject (Object element){
        ImmutableList oldList;
        ImmutableList newList;
        do{
            oldList = getTopList().get();
            MutableList nL = this.newList.get();
            oldList.remove(element);
            nL.remove(element);
            newList= nL.getListContents();
        }while(!getTopList().compareAndSet(oldList,newList));
    }

    public void print(){
       Object oldElement;
       Object newElement;
       do{
           oldElement = getIteratorObject().get();
           removeObject(oldElement);
           System.out.println("Removed: " + oldElement.toString());
           if(getIterator().get().hasNext()) {
               newElement = getIterator().get().next();
           }else{
               break;
           }
       }while(!getIteratorObject().compareAndSet(oldElement, newElement));
    }

    public AtomicReference<Iterator> getIterator(){
        AtomicReference<Iterator> it = new AtomicReference<Iterator>(Main.getTopList().get().iterator());
        return it;
    }

    public AtomicReference<Object> getIteratorObject(){
        AtomicReference<Object> object = new AtomicReference<Object>(getIterator().get().next());
        return object;
    }
}
