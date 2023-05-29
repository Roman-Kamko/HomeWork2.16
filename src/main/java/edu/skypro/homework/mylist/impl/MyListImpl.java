package edu.skypro.homework.mylist.impl;

import edu.skypro.homework.mylist.MyList;
import edu.skypro.homework.mylist.exception.BadIndexException;
import edu.skypro.homework.mylist.exception.InvalidItemException;
import edu.skypro.homework.mylist.exception.StorageIsFullException;

import java.util.Arrays;

public class MyListImpl implements MyList {
    Integer[] storage;
    int size;

    public MyListImpl() {
        storage = new Integer[5];
    }

    public MyListImpl(int len) {
        storage = new Integer[len];
    }


    @Override
    public Integer add(Integer item) {
        checkItem(item);
        checkSize();

        storage[size++] = item;

        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        checkItem(item);
        checkIndex(index);
        checkSize();

        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = item;
        size++;

        return item;
    }

    @Override
    public Integer set(int index, Integer item) {
        checkItem(item);
        checkIndex(index);

        storage[index] = item;

        return item;
    }

    @Override
    public Integer remove(Integer item) {
        checkItem(item);
        int index = indexOf(item);

        if (index == -1) {
            throw new InvalidItemException();
        }

        if (index != size - 1) {
            System.arraycopy(storage, index + 1, storage, index, size - index);
            size--;
        }

        size--;
        return item;
    }

    @Override
    public Integer remove(int index) {
        checkIndex(index);
        Integer item = storage[index];

        if (index != size - 1) {
            System.arraycopy(storage, index + 1, storage, index, size - index);
            size--;
        }

        size--;
        return item;
    }

    @Override
    public boolean contains(Integer item) {
        checkItem(item);
        insertSort();
        return binarySearch(item);
    }

    @Override
    public int indexOf(Integer item) {
        checkItem(item);
        for (int i = 0; i < size; i++) {
            if (item.equals(storage[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        checkItem(item);
        for (int i = size - 1; i >= 0; i--) {
            if (item.equals(storage[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        checkIndex(index);
        return storage[index];
    }

    @Override
    public boolean equals(MyList otherList) {
        return Arrays.equals(this.toArray(), otherList.toArray());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public Integer[] toArray() {
        return Arrays.copyOf(storage, size);
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new BadIndexException();
        }
    }

    private void checkItem(Integer item) {
        if (item == null) {
            throw new InvalidItemException();
        }
    }

    private void checkSize() {
        if (size == storage.length) {
            throw new StorageIsFullException();
        }
    }

    private void insertSort() {
        for (int i = 0; i < size; i++) {
            Integer value = storage[i];
            int index = i;

            while (index > 0 && storage[index - 1] >= value) {
                storage[index] = storage[index - 1];
                index--;
            }

            storage[index] = value;
        }
    }

    private boolean binarySearch(Integer item) {
        int minIndex = 0;
        int maxIndex = size;

        while (minIndex <= maxIndex) {
            int midIndex = (minIndex + maxIndex) / 2;

            if (storage[midIndex].equals(item)) {
                return true;
            }

            if (item < storage[midIndex]) {
                maxIndex = midIndex - 1;
            } else {
                minIndex = midIndex + 1;
            }
        }
        return false;
    }
}
