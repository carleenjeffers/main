package edu.jhu.apl.patterns_class.iterator;

// Aggregate Interface
public interface Aggregate<T> {
    Iterator<T> createIterator();
}
