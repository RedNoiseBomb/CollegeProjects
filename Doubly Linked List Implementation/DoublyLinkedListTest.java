package csu22011_a2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunListener.ThreadSafe;
import org.junit.runners.JUnit4;

import junit.framework.Assert;

//-------------------------------------------------------------------------
/**
 * Test class for Doubly Linked List
 *
 * @author
 * @version 13/10/16 18:15
 */
@RunWith(JUnit4.class)
public class DoublyLinkedListTest {
        // ~ Constructor ........................................................
        @Test
        public void testConstructor() {
                new DoublyLinkedList<Integer>();
        }

        // ~ Public Methods ........................................................

        @Test
        public void testListSize() {
                DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
                testDLL = new DoublyLinkedList<Integer>();
                testDLL.insertBefore(0, 1);
                testDLL.insertBefore(1, 2);
                testDLL.insertBefore(2, 3);

                assertEquals("Checking listSize for a list containing 3 elements", 3, testDLL.listSize());
        }

        // ----------------------------------------------------------
        /**
         * Check if the insertBefore works
         */
        @Test
        public void testInsertBefore() {
                // test non-empty list
                DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
                testDLL.insertBefore(0, 1);
                testDLL.insertBefore(1, 2);
                testDLL.insertBefore(2, 3);

                testDLL.insertBefore(0, 4);
                assertEquals("Checking insertBefore to a list containing 3 elements at position 0", "4,1,2,3",
                                testDLL.toString());
                testDLL.insertBefore(1, 5);
                assertEquals("Checking insertBefore to a list containing 4 elements at position 1", "4,5,1,2,3",
                                testDLL.toString());
                testDLL.insertBefore(2, 6);
                assertEquals("Checking insertBefore to a list containing 5 elements at position 2", "4,5,6,1,2,3",
                                testDLL.toString());
                testDLL.insertBefore(-1, 7);
                assertEquals(
                                "Checking insertBefore to a list containing 6 elements at position -1 - expected the element at the head of the list",
                                "7,4,5,6,1,2,3", testDLL.toString());
                testDLL.insertBefore(7, 8);
                assertEquals(
                                "Checking insertBefore to a list containing 7 elemenets at position 8 - expected the element at the tail of the list",
                                "7,4,5,6,1,2,3,8", testDLL.toString());
                testDLL.insertBefore(700, 9);
                assertEquals(
                                "Checking insertBefore to a list containing 8 elements at position 700 - expected the element at the tail of the list",
                                "7,4,5,6,1,2,3,8,9", testDLL.toString());

                // test empty list
                testDLL = new DoublyLinkedList<Integer>();
                testDLL.insertBefore(0, 1);
                assertEquals(
                                "Checking insertBefore to an empty list at position 0 - expected the element at the head of the list",
                                "1", testDLL.toString());
                testDLL = new DoublyLinkedList<Integer>();
                testDLL.insertBefore(10, 1);
                assertEquals(
                                "Checking insertBefore to an empty list at position 10 - expected the element at the head of the list",
                                "1", testDLL.toString());
                testDLL = new DoublyLinkedList<Integer>();
                testDLL.insertBefore(-10, 1);
                assertEquals(
                                "Checking insertBefore to an empty list at position -10 - expected the element at the head of the list",
                                "1",
                                testDLL.toString());
        }

        @Test
        public void testGet() {
                DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
                testDLL = new DoublyLinkedList<Integer>();
                assertEquals("Checking get for an empty list", null, testDLL.get(4));
                assertEquals("Checking get for an empty list", null, testDLL.get(-5));
                testDLL.insertBefore(0, 1);
                testDLL.insertBefore(1, 2);
                testDLL.insertBefore(2, 3);
                Integer expectedResult = 3;
                assertEquals("Checking get to a list containing 3 elements", expectedResult, testDLL.get(2));
        }

        @Test
        public void testDeleteAt() {
                DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
                testDLL = new DoublyLinkedList<Integer>();
                assertFalse("Checking deleteAt for a list with a position out of bounds", testDLL.deleteAt(500));

                testDLL.insertBefore(0, 1);
                testDLL.insertBefore(1, 2);
                testDLL.insertBefore(2, 3);
                assertTrue("Checking deleteAt to a list containing 3 elements", testDLL.deleteAt(2));
        }

        @Test
        public void testReverse() {
                DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
                testDLL = new DoublyLinkedList<Integer>();
                testDLL.reverse();
                assertEquals("Checking reverse to an empty list", "", testDLL.toString());
                testDLL.insertBefore(0, 1);
                testDLL.insertBefore(1, 2);
                testDLL.insertBefore(2, 3);
                testDLL.reverse();
                assertEquals("Checking reverse to a list containing 3 elements", "3,2,1", testDLL.toString());
        }

        @Test
        public void testMakeUnique() {
                DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
                testDLL = new DoublyLinkedList<Integer>();

                testDLL.makeUnique();
                assertEquals("Checking makeUnique for a list containing no elemenents", "", testDLL.toString());

                testDLL.insertBefore(0, 1);
                testDLL.insertBefore(1, 3);
                testDLL.insertBefore(2, 2);
                testDLL.insertBefore(3, 1);
                testDLL.insertBefore(4, 0);
                testDLL.insertBefore(5, 1);
                testDLL.makeUnique();
                assertEquals("Checking makeUnique to a list containing 6 elements, 3 of which are duplicates",
                                "1,3,2,0", testDLL.toString());

        }

        @Test
        public void testPush() {
                DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
                testDLL = new DoublyLinkedList<Integer>();

                testDLL.push(3);
                testDLL.push(5);
                testDLL.push(2);

                assertEquals("Checking push for an empty list", "3,5,2", testDLL.toString());

        }

        @Test
        public void testPop() {
                DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
                testDLL = new DoublyLinkedList<Integer>();
                assertEquals("Checking pop for an empty list", null, testDLL.pop());

                testDLL.push(3);
                assertEquals("Checking pop for an empty list", (Integer) 3, testDLL.pop());

                testDLL.push(5);
                testDLL.push(2);
                Integer expResult = 2;

                assertEquals("Checking pop for a list of 3 items", expResult, testDLL.pop());
        }

        @Test
        public void testEnqueue() {
                DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
                testDLL = new DoublyLinkedList<Integer>();
                testDLL.enqueue(5);
                assertEquals("Checking dequeue for a list of enqueued 3 items", "5", testDLL.toString());

                testDLL.enqueue(6);
                assertEquals("Checking dequeue for a list of enqueued 3 items", "5,6", testDLL.toString());

                testDLL.enqueue(7);
                assertEquals("Checking dequeue for a list of enqueued 3 items", "5,6,7", testDLL.toString());

        }

        @Test
        public void testDequeue() {
                DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
                testDLL = new DoublyLinkedList<Integer>();
                assertEquals("Checking dequeue for an empty list", null, testDLL.dequeue());

                testDLL.enqueue(5);
                assertEquals("Checking dequeue for a list of enqueued 3 items", (Integer) 5, testDLL.dequeue());

                testDLL.enqueue(6);
                testDLL.enqueue(7);

                assertEquals("Checking dequeue for a list of enqueued 3 items", (Integer) 6, testDLL.dequeue());
                assertEquals("Checking dequeue for a list of enqueued 3 items", (Integer) 7, testDLL.dequeue());
        }

        // TODO: add more tests here. Each line of code in DoublyLinkedList.java should
        // be executed at least once from at least one test.

}
