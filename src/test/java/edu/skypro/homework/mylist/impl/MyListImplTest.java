package edu.skypro.homework.mylist.impl;

import edu.skypro.homework.mylist.MyList;
import edu.skypro.homework.mylist.exception.InvalidItemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class MyListImplTest {

    private MyList out;

    @BeforeEach
    void beforeEach() {
        out = new MyListImpl();
        out.add(5);
        out.add(3);
        out.add(1);
        out.add(7);
    }

    @Test
        // проверка, что добавленный элемент содержится в хранилище,
        // а количество элементов увеличилось на 1
    void addTest() {
        int sizeBefore = out.size();
        assertThat(out.add(9)).isIn(out.toArray());
        assertThat(out.size()).isEqualTo(sizeBefore + 1);
    }

    @Test
        // проверка, что добавленные элементы содержатся в хранилище;
        // проверка, что хранилище расширилось, а количество элементов увеличилось на 2
    void addWithGrowTest() {
        int sizeBefore = out.size();

        Integer[] expected = {5, 3, 1, 7, 11, 9};

        // хранилище заполнено
        out.add(11);

        // переполнение хранилища
        assertThat(out.add(9)).isIn(out.toArray());
        assertThat(out.toArray())
                .isEqualTo(expected)
                .hasSize(sizeBefore + 2);
    }

    @Test
        // проверка, что будет выброшено исключение при попытке добавить null
    void addNegativeTest() {
        assertThatExceptionOfType(InvalidItemException.class)
                .isThrownBy(() -> out.add(null));
    }

    @Test
    void addByIndexTest() {
        int sizeBefore = out.size();

        Integer[] expected = {5, 3, 100, 1, 7};

        assertThat(out.add(2, 100)).isIn(expected);
        assertThat(out.indexOf(100)).isEqualTo(2);
        assertThat(out.toArray())
                .isEqualTo(expected)
                .hasSize(sizeBefore + 1);
    }

    @Test
    void addByIndexWithGrowTest() {
        int sizeBefore = out.size();

        Integer[] expected = {5, 3, 100, 1, 7, 101};

        out.add(2, 100);

        assertThat(out.add(5, 101)).isIn(out.toArray());
        assertThat(out.indexOf(101)).isEqualTo(5);
        assertThat(out.toArray())
                .isEqualTo(expected)
                .hasSize(sizeBefore + 2);
    }

    @Test
    void setTest() {
        int sizeBefore = out.size();

        out.set(2, 100);

        Integer[] expected = {5, 3, 100, 7};

        assertThat(out.indexOf(100)).isEqualTo(2);
        assertThat(out.toArray())
                .hasSize(sizeBefore)
                .isEqualTo(expected);
    }

    @Test
    void removeByElementTest() {
        Integer[] expected = {5, 3, 1};
        assertThat(out.remove(new Integer(7))).isNotIn(out.toArray());
        assertThat(out.toArray()).isEqualTo(expected);
    }

    @Test
    void removeByElementNegativeTest() {
        assertThatExceptionOfType(InvalidItemException.class)
                .isThrownBy(() -> out.remove(new Integer(65)));
    }

    @Test
    void removeByIndexTest() {
        Integer[] expected = {5, 3, 7};
        out.remove(2);
        assertThat(out.toArray()).isEqualTo(expected);
    }

    @Test
    void containsTest() {
        assertThat(out.contains(1)).isTrue();
    }

    @Test
    void getTest() {
        assertThat(out.get(0)).isEqualTo(5);
    }

    @Test
    void equalsTest() {
        MyList otherList = new MyListImpl();
        otherList.add(5);
        otherList.add(3);
        otherList.add(1);
        otherList.add(7);

        assertThat(out.equals(otherList)).isTrue();
    }

    @Test
    void isEmptyTest() {
        assertThat(out.isEmpty()).isFalse();
    }

    @Test
    void clearTest() {
        out.clear();
        assertThat(out.isEmpty()).isTrue();
    }
}