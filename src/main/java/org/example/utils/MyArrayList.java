package org.example.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MyArrayList<E>  implements Iterable<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elements;

    private int size;

    public MyArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public MyArrayList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("емкость не может быть меньше 0");
        }
        this.elements = new Object[capacity];
        this.size = 0;
    }

    /**
     * Добавит элемент в конец коллекции
     */
    public void add(E element) {
        if (size == elements.length) {
            grow();
        }
        elements[size++] = element;
    }

    /**
     * Вернет элемент по индексу
     *
     * @throws IndexOutOfBoundsException если индекс вне диапазона [0, size-1]
     */
    public E get(int index) {
        checkIndex(index);
        return (E) elements[index];
    }

    /**
     * Удалит элемент по индексу, сдвигая последующие элементы влево
     *
     * @return удалённый элемент
     * @throws IndexOutOfBoundsException если индекс вне диапазона [0, size-1]
     */
    public E remove(int index) {
        checkIndex(index);
        E removed = (E) elements[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null; // помогаем сборщику мусора

        return removed;
    }

    public boolean remove(E element) {
        if (size == 0) {
            return false;
        }
        int index = indexOf(element);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;

    }

    /**
     * Вернет первый индекс указанного элемента или -1 при отсутствии
     */
    public int indexOf(E element) {
        for (int i = 0; i < size; i++) {
            if (element == null && elements[i] == null
                    || element != null && element.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Вернет true если элемент существует в коллекции
     */
    public boolean contains(E element) {
        return indexOf(element) >= 0;
    }

    /**
     * Вернет количество элементов в коллекции
     */
    public int size() {
        return size;
    }

    /**
     * Увеличит емкость массива в 1,5 раза
     */
    private void grow() {
        int newCapacity = elements.length + Math.max(elements.length / 2, 1);
        elements = Arrays.copyOf(elements, newCapacity);
    }

    /**
     * Проверяет корректность индекса
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Индекс: " + index + ", размер: " + size);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(", ").append("\n");
            }
            sb.append(elements[i]);
        }
        sb.append("]");
        return sb.toString();
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public E next() {
                if (cursor >= size) {
                    throw new NoSuchElementException();
                }
                return (E) elements[cursor++];
            }
        };
    }

    public Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
}
