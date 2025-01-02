package it.unibo.inner.impl;

import it.unibo.inner.api.Predicate;
import it.unibo.inner.api.IterableWithPolicy;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.List;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {
    private final T[] elements;
    private Predicate<T> filter;

    @SuppressWarnings("unused")
    public IterableWithPolicyImpl(final T[] elements) {
        this(elements, t -> true); // Default filter: always true
    }

    public IterableWithPolicyImpl(final T[] elements, Predicate<T> filter) {
        if (elements == null || filter == null) {
            throw new IllegalArgumentException("Neither filter nor array can be null");
        }
        this.elements = elements;
        this.filter = filter;
    }

    private class ArrayIterator implements Iterator<T> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            while (index < elements.length) {
                final T elem = elements[index];
                if (filter.test(elem)) {
                    return true;
                }
                index++;
            }
            return false;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return elements[index++];
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        if (filter == null) {
            throw new IllegalArgumentException("The filter must not be null");
        }
        this.filter = filter;
    }

    @Override
    public String toString() {
        return List.of(elements).toString();
    }
}
