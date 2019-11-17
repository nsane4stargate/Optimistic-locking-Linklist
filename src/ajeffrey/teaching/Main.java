package ajeffrey.teaching;

import ajeffrey.teaching.util.list.ImmutableList;
import ajeffrey.teaching.util.list.OptimisticMutableList;
import java.util.concurrent.atomic.AtomicReference;
/* Author: Lea Middleton
   Program was developed in IntelliJ, with built in command line arguments
   "barney betty wilma fred harry william serena". You can add more elements
   if you would like.
 */

public class Main {
    static AtomicReference<ImmutableList> currentList;
    static AtomicReference<OptimisticMutableList> mutableList;

    public static void main(String[] args) {
        mutableList = new AtomicReference<OptimisticMutableList>(new OptimisticMutableList());
        currentList = new AtomicReference<ImmutableList>(getMutableList().get().getListContents());
        for (int i = 0; i < args.length; i++) {
            mutableList.get().add(args[i]);
        }

        /* Print out list */
        mutableList.get().print();
        System.out.println("Current list elements: " + currentList);
    }

    public static AtomicReference<ImmutableList> getTopList(){
        return currentList;
    }

    public static AtomicReference<OptimisticMutableList> getMutableList(){
        return mutableList;
    }
}

