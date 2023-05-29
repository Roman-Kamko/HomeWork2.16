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

        if (size == storage.length) {
            storage = grow(storage);
        }

        storage[size++] = item;
        int i = storage.length;
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        checkItem(item);
        checkIndex(index);

        if (size == storage.length) {
            storage = grow(storage);
        }

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
            return item;
        }

        size--;
        return item;
    }

    @Override
    public boolean contains(Integer item) {
        checkItem(item);
        Integer[] storageCopy = toArray();
        quickSort(storageCopy, 0, size - 1);
        return binarySearch(storageCopy, item);
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

    private void insertSort(Integer[] arr) {
        for (int i = 0; i < arr.length; i++) {
            Integer value = arr[i];
            int index = i;

            while (index > 0 && arr[index - 1] >= value) {
                arr[index] = arr[index - 1];
                index--;
            }

            arr[index] = value;
        }
    }

    private void quickSort(Integer[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private int partition(Integer[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                swapElements(arr, i, j);
            }
        }

        swapElements(arr, i + 1, end);
        return i + 1;
    }
    private void swapElements(Integer[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }

    private boolean binarySearch(Integer[] arr, Integer item) {
        int minIndex = 0;
        int maxIndex = arr.length;

        while (minIndex <= maxIndex) {
            int midIndex = (minIndex + maxIndex) / 2;

            if (arr[midIndex].equals(item)) {
                return true;
            }

            if (item < arr[midIndex]) {
                maxIndex = midIndex - 1;
            } else {
                minIndex = midIndex + 1;
            }
        }
        return false;
    }

    private Integer[] grow(Integer[] arr) {
        Integer[] newArr = new Integer[(int) (size * 1.5 + 1)];
        System.arraycopy(arr, 0, newArr, 0, size);
        return newArr;
    }
}
