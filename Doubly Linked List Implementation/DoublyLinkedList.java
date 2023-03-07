package csu22011_a2;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

// -------------------------------------------------------------------------
/**
 * This class contains the methods of Doubly Linked List.
 *
 * @author MykBit
 * @version 09/11/22
 *          //------------------------------------------------------------------------
 *          /**
 *          Class DoublyLinkedList: implements a *generic* Doubly Linked List.
 * 
 * @param <T> This is a type parameter. T is used as a class name in the
 *            definition of this class.
 *
 * 
 *            When creating a new DoublyLinkedList, T should be instantiated
 *            with an
 *            actual class name that extends the class Comparable.
 *            Such classes include String and Integer.
 *
 *            For example to create a new DoublyLinkedList class containing
 *            String data:
 *            DoublyLinkedList<String> myStringList = new
 *            DoublyLinkedList<String>();
 *
 *            The class offers a toString() method which returns a
 *            comma-separated sting of
 *            all elements in the data structure.
 * 
 *            This is a bare minimum class you would need to completely
 *            implement.
 *            You can add additional methods to support your code. Each method
 *            will need
 *            to be tested by your jUnit tests -- for simplicity in jUnit
 *            testing
 *            introduce only public methods.
 */
class DoublyLinkedList<T extends Comparable<T>> {

  /**
   * private class DLLNode: implements a *generic* Doubly Linked List node.
   */
  private class DLLNode {
    public final T data; // this field should never be updated. It gets its
                         // value once from the constructor DLLNode.
    public DLLNode next;
    public DLLNode prev;

    /**
     * Constructor
     * 
     * @param theData  : data of type T, to be stored in the node
     * @param prevNode : the previous Node in the Doubly Linked List
     * @param nextNode : the next Node in the Doubly Linked List
     * @return DLLNode
     */
    public DLLNode(T theData, DLLNode prevNode, DLLNode nextNode) {
      data = theData;
      prev = prevNode;
      next = nextNode;
    }
  }

  // Fields head and tail point to the first and last nodes of the list.
  private DLLNode head, tail;

  /**
   * Constructor of an empty DLL
   * 
   * @return DoublyLinkedList
   */
  public DoublyLinkedList() {
    head = null;
    tail = null;
  }

  /**
   * Tests if the doubly linked list is empty
   * 
   * @return true if list is empty, and false otherwise
   *         Worst-case asymptotic running time cost: Theta(1)
   * 
   *         Justification:
   *         On line 93 we simply assign an existing variable with a value, which
   *         is a one-time operation.
   */
  public boolean isEmpty() {
    return this.head == null;
  }

  /**
   * Gets the size of the doubly linked list.
   * 
   * @param node : current head of the doubly linked list.
   * 
   * @return integer which represents the size of the doubly
   *         linked list
   *
   *         Worst-case asymptotic running time cost: Theta(n)
   *
   *         Justification:
   *         On line 116-117, 122 we do a simple variable assignment and return
   *         the
   *         value.
   *         On lines 118-120 we have a while-loop, which is limited
   *         by the number of nodes we have in our DLL. Thus, to calculate its
   *         size we need to
   *         run through the whole DLL, which makes the time complexity Theta(n)
   */
  public int listSize() {
    int size = 0;
    DLLNode tempHead = head;
    while (tempHead != null) {
      tempHead = tempHead.next;
      size++;
    }
    return size;
  }

  /**
   * Inserts an element in the doubly linked list
   * 
   * @param pos  : The integer location at which the new data should be
   *             inserted in the list. We assume that the first position in the
   *             list
   *             is 0 (zero). If pos is less than 0 then add to the head of the
   *             list.
   *             If pos is greater or equal to the size of the list then add the
   *             element at the end of the list.
   * @param data : The new data of class T that needs to be added to the list
   * @return none
   *         Worst-case asymptotic running time cost: Theta(n), where n is
   *         (pos - 1)
   *
   *         Justification:
   *         Lines 155-165 consist of simple variable assginments, if/else
   *         statements and object constructions
   *         which all take a constant amount of time independently from the
   *         input.
   *         Lines 166-168 have a for-loop which is limited by the input we get.
   *         In the best case we could
   *         only run through the loop once, but if the pos is pointing at the
   *         last element of the DLL, then it would take (pos - 1) times to run
   *         through the loop.
   *         Lines 169-173 also contain simple operations which do not affect the
   *         time complexity.
   */

  public void insertBefore(int pos, T data) {
    if (isEmpty()) {
      this.head = this.tail = new DLLNode(data, null, null);
    } else {
      if (pos <= 0) {
        this.head.prev = new DLLNode(data, null, head);
        this.head = this.head.prev;
      } else if (pos >= listSize()) {
        this.tail.next = new DLLNode(data, tail, null);
        this.tail = this.tail.next;
      } else {
        DLLNode targetNode = this.head;
        for (int i = 0; i < pos; i++) {
          targetNode = targetNode.next;
        }
        DLLNode headNode = targetNode.prev;
        DLLNode tailNode = targetNode;
        DLLNode newNode = new DLLNode(data, headNode, tailNode);
        headNode.next = newNode;
        tailNode.prev = newNode;
      }
    }
  }

  /**
   * Returns the data stored at a particular position
   * 
   * @param pos : the position
   * @return the data at pos, if pos is within the bounds of the list, and null
   *         otherwise.
   *
   *         Worst-case asymptotic running time cost: Theta(n)
   *
   *         Justification:
   *         Line 204: apart from single-time execution of if/else statement, we
   *         have a call
   *         of listSize function, which time complexity is Theta(n).
   *
   *         Lines 206, 208, 210-212: these are simple operations running at a
   *         constant
   *         time.
   *         Lines 207-209: there is a for-loop limited by (pos - 1). Since by
   *         default pos < size of the DLL,
   *         the time complexity will be Theta(m), where m is (pos - 1).
   * 
   *         Combining, we have Theta(n) + Theta(m) = Theta(n), because n will
   *         always be
   *         bigger than m.
   */
  public T get(int pos) {
    if (pos >= 0 && pos < listSize()) {
      DLLNode node = head;
      for (int i = 0; i < pos; i++) {
        node = node.next;
      }
      return node.data;
    } else
      return null;
  }

  /**
   * Deletes the element of the list at position pos.
   * First element in the list has position 0. If pos points outside the
   * elements of the list then no modification happens to the list.
   * 
   * @param pos : the position to delete in the list.
   * @return true : on successful deletion, false : list has not been modified.
   *
   * 
   *         Worst-case asymptotic running time cost: Theta(n)
   *
   *         Justification:
   *         Here the situation is similar to the one in get() function.
   *         Line 243: apart from single-time execution of if/else statement, we
   *         have a call
   *         of listSize function, which time complexity is Theta(n).
   * 
   *         Line 244, 248-267: these are all simple operations running at a
   *         constant time
   * 
   *         Line 245-247: there is a for-loop limited by (pos - 1). Since by
   *         default pos < size of the DLL,
   *         the time complexity will be Theta(m), where m is (pos - 1).
   * 
   *         Combining, we have Theta(n) + Theta(m) = Theta(n), because n will
   *         always be
   *         bigger than m.
   */
  public boolean deleteAt(int pos) {
    if (pos >= 0 && pos < listSize()) {
      DLLNode targetNode = head;
      for (int i = 0; i < pos; i++) {
        targetNode = targetNode.next;
      }
      DLLNode prevNode = targetNode.prev;
      DLLNode nextNode = targetNode.next;

      targetNode.prev = null;
      targetNode.next = null;

      if (prevNode != null) {
        prevNode.next = nextNode;
      } else {
        head = nextNode;
      }

      if (nextNode != null) {
        nextNode.prev = prevNode;
      } else {
        tail = prevNode;
      }
      return true;
    }
    return false;
  }

  /**
   * Reverses the list.
   * If the list contains "A", "B", "C", "D" before the method is called
   * Then it should contain "D", "C", "B", "A" after it returns.
   *
   * Worst-case asymptotic running time cost: Theta(n)
   *
   * Justification:
   * Lines 289-290: simple objects' assignments.
   * 
   * Lines 291-296: here we have while-loop which is limited by the size of the
   * DLL.
   * In any case while-loop will be executed up until the last pair of elements is
   * reversed. Thus, Theta(n).
   * 
   * Lines 297-299: simple condition statement which takes a constant amount of
   * time to execute.
   */
  public void reverse() {
    DLLNode prevNode = null;
    DLLNode tempHead = head;
    while (tempHead != null) {
      prevNode = tempHead.prev;
      tempHead.prev = tempHead.next;
      tempHead.next = prevNode;
      tempHead = tempHead.prev;
    }
    if (prevNode != null) {
      head = prevNode.prev;
    }
  }

  /**
   * Removes all duplicate elements from the list.
   * The method should remove the _least_number_ of elements to make all elements
   * uniqueue.
   * If the list contains "A", "B", "C", "B", "D", "A" before the method is called
   * Then it should contain "A", "B", "C", "D" after it returns.
   * The relative order of elements in the resulting list should be the same as
   * the starting list.
   *
   * Worst-case asymptotic running time cost: Theta(n*m)
   * 
   * m = m + (m-1) + (m-2) + ... + (m-(n-1))
   *
   * Justification:
   * Lines 335-341: if statement and variable assignments taking constant amount
   * of time
   * 
   * Lines 343-352: we have a while-loop which has a nested while-loop. While-loop
   * on line 343 has a Theta(n) time complexity as it in the worst case in runs
   * through the whole
   * DLL. On contrary, while-loop on line 344 is executed one time less on every
   * iteration of while-loop
   * on line 343. Hence, we will represent this loop with another symbol. Let m be
   * the time complexity of the second
   * while-loop, where m = m + (m-1) + (m-2) + ... + (m-(n-1)). Thus, Theta(m)
   * Inside this loop we have a deleteAt() function call at line 347, which time
   * complexity is
   * Theta(y), where y is (pos - 1). In all other lines of this function we have
   * simple operation which are not affecting the time complexity
   * Combining the significant parts we have, Theta(n) * (Theta(m) + Theta(y)) =
   * Theta(n*m)
   */
  public void makeUnique() {
    if (isEmpty()) {
      return;
    }
    DLLNode tempHead1 = head;
    DLLNode tempHead2 = head.next;
    int outerPosition = 0;
    int innerPosition = 1;

    while (tempHead1 != null) {
      while (tempHead2 != null) {
        if (tempHead1.data.equals(tempHead2.data)) {
          tempHead2 = tempHead2.next;
          deleteAt(innerPosition + outerPosition);
        } else {
          tempHead2 = tempHead2.next;
          innerPosition++;
        }
      }
      innerPosition = 1;
      outerPosition++;
      tempHead1 = tempHead1.next;
      if (tempHead1 != null) {
        tempHead2 = tempHead1.next;
      }
    }
  }

  /*----------------------- STACK API 
   * If only the push and pop methods are called the data structure should behave like a stack.
   */

  /**
   * This method adds an element to the data structure.
   * How exactly this will be represented in the Doubly Linked List is up to the
   * programmer.
   * 
   * @param item : the item to push on the stack
   *
   *             Worst-case asymptotic running time cost: Theta(1)
   *
   *             Justification:
   *             All the operations (even the isEmpty() function call) taking
   *             place in this function take a constant
   *             amount of time
   *             no matter what the input is. Thus, Theta(1)
   */
  public void push(T item) {
    DLLNode pushNode = new DLLNode(item, null, null);
    if (isEmpty()) {
      head = pushNode;
      tail = pushNode;
    } else {
      tail.next = pushNode;
      pushNode.prev = tail;
      tail = pushNode;
    }
  }

  /**
   * This method returns and removes the element that was most recently added by
   * the push method.
   * 
   * @return the last item inserted with a push; or null when the list is empty.
   *
   *         Worst-case asymptotic running time cost: Theta(1)
   *
   *         Justification:
   *         Just as in push() we only have simple operations as variable
   *         assignments and if/else statements, which
   *         are running at a constant amount of time and that gives us Theta(1).
   */
  public T pop() {
    if (head != null) {
      DLLNode popNode = tail;
      if (popNode.next == null && popNode.prev == null) {
        head = tail = null;
        return popNode.data;
      }
      tail = tail.prev;
      tail.next = null;
      return popNode.data;
    } else {
      return null;
    }
  }

  /*----------------------- QUEUE API
   * If only the enqueue and dequeue methods are called the data structure should behave like a FIFO queue.
   */

  /**
   * This method adds an element to the data structure.
   * How exactly this will be represented in the Doubly Linked List is up to the
   * programmer.
   * 
   * @param item : the item to be enqueued to the stack
   *
   *             Worst-case asymptotic running time cost: Theta(1)
   *
   *             Justification:
   *             this function only calls the push() method which was already
   *             evaluated as Theta(1).
   *             Hence, this function's time complexity is also Theta(1).
   */
  public void enqueue(T item) {
    push(item);
  }

  /**
   * This method returns and removes the element that was least recently added by
   * the enqueue method.
   * 
   * @return the earliest item inserted with an equeue; or null when the list is
   *         empty.
   *
   *         Worst-case asymptotic running time cost: Theta(1)
   *
   *         Justification:
   *         this function runs in a similar manner as pop() but the other way
   *         around. Since all the operations are
   *         constant, the time complexity will also be Theta(1).
   */
  public T dequeue() {
    DLLNode deqNode = head;
    if (!isEmpty()) {
      if (deqNode.next == null && deqNode.prev == null) {
        head = tail = null;
        return deqNode.data;
      }
      head = head.next;
      head.prev = null;
      return deqNode.data;
    } else {
      return null;
    }
  }

  /**
   * @return a string with the elements of the list as a comma-separated
   *         list, from beginning to end
   *
   *         Worst-case asymptotic running time cost: Theta(n)
   *
   *         Justification:
   *         The code consists of the lines before the for-loop, the for-loop
   *         itself and the lines after the for-loop.
   *
   *         The lines before the for loop involve
   *         - the creation of a StringBuilder object which does not depend on the
   *         length of the list. Therefore it takes Theta(1) time.
   *         - the allocation and assignment of two variables which are constant
   *         time operations.
   *         Thus the code before the for-loop will take Theta(1) time to run.
   *
   *         The lines after the for-loop involve a single return instruction and
   *         thus take Theta(1) time.
   *
   *         The for-loop itself will iterate over all elements of the DLL. Thus
   *         it will perform Theta(N) iterations.
   *         Each iteration will involve:
   *         * The if-conditional will run:
   *         - the if-then-else conditions and branching, which are constant time
   *         operations. Thus these cost Theta(1) time.
   *         - The then-branch of the conditional calls StringBuilder's append()
   *         method, which (from the Java documentation) we know it runs in
   *         Theta(1) time.
   *         - the else-branch of the conditional involves a single assignment,
   *         thus runs in Theta(1) time.
   *         Therefore the if-then-else conditions will cost Theta(1) in the worst
   *         case.
   *         * The final call to StringBuilder's append will cost again Theta(1)
   *         * the nested call to toString() will cost time proportional to the
   *         length of the strings (but not the length of the list).
   *         Assuming strings are relatively small compared to the size of the
   *         list, this cost will be Theta(1).
   *         Therefore each iteration of the loop will cost Theta(1).
   *         Thus the lines involving the for-loop will run in Theta(N)*Theta(1) =
   *         Theta(N).
   *
   *         Therefore this method will run in Theta(1) + Theta(1) + Theta(N) =
   *         Theta(N) time in the worst case.
   *
   */
  public String toString() {
    StringBuilder s = new StringBuilder();
    boolean isFirst = true;

    // iterate over the list, starting from the head
    for (DLLNode iter = head; iter != null; iter = iter.next) {
      if (!isFirst) {
        s.append(",");
      } else {
        isFirst = false;
      }
      s.append(iter.data.toString());
    }

    return s.toString();
  }

}
