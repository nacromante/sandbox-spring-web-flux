package com.sandbox.reactive.queue;


import java.util.ArrayDeque;
import java.util.Iterator;

public class CustomQueue<T>{

    private ArrayDeque<T> collection = new ArrayDeque<T>();

    public Iterator<T> iterator(){
        return collection.iterator();
    }

    synchronized public boolean add(T t) {
        return collection.add(t);
    }

    synchronized public T remove(){
        return collection.remove();
    }

    synchronized public T peek(){
        return collection.peek();
    }

    synchronized public void push(T t) {
        collection.push(t);
    }

    synchronized public T pop(){
        return collection.pop();
    }

    synchronized public void addFirst(T t) {
        collection.addFirst(t);
    }

    synchronized public void addLast(T t) {
        collection.addLast(t);
    }


    synchronized public boolean offerFirst(T t) {
        return collection.offerFirst(t);
    }

    synchronized public boolean offerLast(T t) {
        return collection.offerLast(t);
    }

    synchronized public T removeFirst() {
        return collection.removeFirst();
    }

    synchronized public T removeLast() {
        return collection.removeLast();
    }
    synchronized public void remove(T t) {
        collection.remove(t);
    }

    synchronized public T pollFirst() {
        return collection.pollFirst();
    }
    synchronized public T pollLast() {
        return collection.pollLast();
    }

    synchronized public T getFirst() {
        return collection.getFirst();
    }

    synchronized public T getLast() {
        return collection.getLast();
    }

    synchronized public T peekFirst(){
        return collection.peekFirst();
    }

    synchronized public T peekLast() {
        return collection.peekLast();
    }

    synchronized public boolean contains(T t) {
        return collection.contains(t);
    }


    public Integer size() {
        return collection.size();
    }
}
