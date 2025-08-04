package Parkee.SingleLinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("SingleLinkedList Test")
public class SingleLinkedListTest {

    private SingleLinkedList list;

    @BeforeEach
    void setUp() {
        list = new SingleLinkedList();
    }

    @Test
    @DisplayName("New list should be empty")
    void testNewListIsEmpty() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }


    @Test
    @DisplayName("Insert at Beginning - Empty List")
    void testInsertBeginningEmptyList() {
        list.insertAtBeginning(99);

        assertEquals(1, list.size());
        assertFalse(list.isEmpty());
        assertEquals(99, list.getHeadData().get().data);
    }


    @Test
    @DisplayName("Insert at Beginning - Multiple value")
    void testInsertBeginningMultipleValue() {
        list.insertAtBeginning(22);
        list.insertAtBeginning(23);
        list.insertAtBeginning(24);

        assertEquals(3, list.size());
        assertFalse(list.isEmpty());
        assertEquals(24, list.getHeadData().get().data);
    }


    @Test
    @DisplayName("Insert at End - Empty List")
    void testInsertEndEmptyList() {
        list.insertAtEnd(22);

        assertEquals(1, list.size());
        assertFalse(list.isEmpty());
        assertEquals(22, list.getHeadData().get().data);
    }


    @Test
    @DisplayName("Insert at End - Multiple value")
    void testInsertEndMultipleValue() {
        list.insertAtEnd(22);
        list.insertAtEnd(23);
        list.insertAtEnd(24);

        assertEquals(3, list.size());
        assertFalse(list.isEmpty());
     
        assertEquals(22, list.getHeadData().get().data);
    }



    @Test
    @DisplayName("Delete Head")
    void testDeleteHead() {
        list.insertAtBeginning(22);
        list.insertAtBeginning(23);
        list.insertAtBeginning(24);

        list.deleteByValue(24);
        assertEquals(2, list.size());
        assertEquals(23, list.getHeadData().get().data);
    }



    @Test
    @DisplayName("Delete Middle")
    void testDeleteMiddle() {
        list.insertAtBeginning(22);
        list.insertAtBeginning(23);
        list.insertAtBeginning(24);

        list.deleteByValue(23);
        assertEquals(2, list.size());
        assertEquals(24, list.getHeadData().get().data);
        assertEquals(22, list.getHeadData().get().next.data);
    }



}
