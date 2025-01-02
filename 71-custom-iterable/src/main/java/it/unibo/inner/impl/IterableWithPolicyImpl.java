package it.unibo.inner.impl;
import it.unibo.inner.api.*;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>  {
    private final T[] elements;
    //private final Predicate<T> filter;

    public IterableWithPolicyImpl(T[] elements){
        if(elements == null){
            throw new IllegalArgumentException("The array cannot be empty");
        }
        this.elements = elements;

    }

    private class ArrayIterator implements Iterator<T>{

        private int index = 0;

        @Override
        public boolean hasNext() {

            return index < elements.length;
            
        }

        @Override
        public T next() {
            if(!hasNext()) throw new NoSuchElementException();

            return elements[index++];

        }
        


        }


    public Iterator<T> iterator(){

        return new ArrayIterator();
    }

    @Override
    public String toString() {
    return List.of(elements).toString(); // Converts the array to a list and then to a string
   }


    public void setIterationPolicy(Predicate<T> filter){}
}

