

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

//-------------------------------------------------------------------------
/**
 *  Test class for Doubly Linked List
 *
 *  @version 3.1 09/11/15 11:32:15
 */

@RunWith(JUnit4.class)
public class BSTTest
{
  
  //TODO write more tests here.

  
  /** <p>Test {@link BST#prettyPrintKeys()}.</p> */
      
 @Test
 public void testPrettyPrint() {
     BST<Integer, Integer> bst = new BST<Integer, Integer>();
     assertEquals("Checking pretty printing of empty tree",
             "-null\n", bst.prettyPrintKeys());
      
                          //  -7
                          //   |-3
                          //   | |-1
                          //   | | |-null
     bst.put(7, 7);       //   | |  -2
     bst.put(8, 8);       //   | |   |-null
     bst.put(3, 3);       //   | |    -null
     bst.put(1, 1);       //   |  -6
     bst.put(2, 2);       //   |   |-4
     bst.put(6, 6);       //   |   | |-null
     bst.put(4, 4);       //   |   |  -5
     bst.put(5, 5);       //   |   |   |-null
                          //   |   |    -null
                          //   |    -null
                          //    -8
                          //     |-null
                          //      -null
     
     String result = 
      "-7\n" +
      " |-3\n" + 
      " | |-1\n" +
      " | | |-null\n" + 
      " | |  -2\n" +
      " | |   |-null\n" +
      " | |    -null\n" +
      " |  -6\n" +
      " |   |-4\n" +
      " |   | |-null\n" +
      " |   |  -5\n" +
      " |   |   |-null\n" +
      " |   |    -null\n" +
      " |    -null\n" +
      "  -8\n" +
      "   |-null\n" +
      "    -null\n";
     assertEquals("Checking pretty printing of non-empty tree", result, bst.prettyPrintKeys());
     }

  
     /** <p>Test {@link BST#delete(Comparable)}.</p> */
     @Test
     public void testDelete() {
        BST<Integer, Integer> bst = new BST<Integer, Integer>();
        bst.delete(1);
        assertEquals("Deleting from empty tree", "()", bst.printKeysInOrder());
        
        bst.put(7, 7);   //        _7_
        bst.put(8, 8);   //      /     \
        bst.put(3, 3);   //    _3_      8
        bst.put(1, 1);   //  /     \
        bst.put(2, 2);   // 1       6
        bst.put(6, 6);   //  \     /
        bst.put(4, 4);   //   2   4
        bst.put(5, 5);   //        \
                                  //         5
        
        assertEquals("Checking order of constructed tree",
                "(((()1(()2()))3((()4(()5()))6()))7(()8()))", bst.printKeysInOrder());
        
        bst.delete(9);
        assertEquals("Deleting non-existent key",
                "(((()1(()2()))3((()4(()5()))6()))7(()8()))", bst.printKeysInOrder());

        bst.delete(8);
        assertEquals("Deleting leaf", "(((()1(()2()))3((()4(()5()))6()))7())", bst.printKeysInOrder());

        bst.delete(6);
        assertEquals("Deleting node with single child", "(((()1(()2()))3(()4(()5())))7())", bst.printKeysInOrder());

        bst.delete(3);
        assertEquals("Deleting node with two children", "(((()1())2(()4(()5())))7())", bst.printKeysInOrder());
                
        bst.delete(1);
        assertEquals("Deleting node with two children", "((()2(()4(()5())))7())", bst.printKeysInOrder());

        bst.delete(2);
        assertEquals("Deleting node with two children", "((()4(()5()))7())", bst.printKeysInOrder());

        bst.delete(5);
        assertEquals("Deleting node with two children", "((()4())7())", bst.printKeysInOrder());

        bst.delete(4);
        assertEquals("Deleting node with two children", "(()7())", bst.printKeysInOrder());

        bst.delete(7);
        assertEquals("Deleting node with two children", "()", bst.printKeysInOrder());
    }
     
     @Test
     public void testHeight() {
             BST<Integer, Integer> bst = new BST<Integer, Integer>();
             assertEquals("Checking height in an empty tree", -1, bst.height());
             bst.put(7, 7); //        _7_
             assertEquals("Checking height in a 1-element tree", 0, bst.height());
             bst.put(8, 8); //      /     \
             bst.put(3, 3); //    _3_      8
             bst.put(1, 1); //  /     \
             bst.put(2, 2); // 1       6
             bst.put(6, 6); //  \     /
             bst.put(4, 4); //   2   4
             bst.put(5, 5); //        \
                                     //         5
             assertEquals("Checking height in an 8-element tree", 4, bst.height());
     }
    
      @Test
      public void testMedian() {
            BST<Integer, Integer> bst = new BST<Integer, Integer>();
            assertEquals("Checking median in an empty tree", null, bst.median());

            bst.put(7, 7);   //        _7_
            bst.put(8, 8);   //      /     \
            bst.put(3, 3);   //    _3_      8
            bst.put(1, 1);   //  /     \
            bst.put(2, 2);   // 1       6
            bst.put(6, 6);   //  \     /
            bst.put(4, 4);   //   2   4
            bst.put(5, 5);   //        \
                                      //         5
            assertEquals("Checking median in an 8-element tree", 4, (int)bst.median());
        }
     
        @Test
        public void testPrintKeysInOrder() {
                BST<Integer, Integer> bst = new BST<Integer, Integer>();
                assertEquals("Checking keys in order in an empty tree", "()",
                                bst.printKeysInOrder());

                bst.put(7, 7); //        _7_
                bst.put(8, 8); //      /     \
                bst.put(3, 3); //    _3_      8
                bst.put(1, 1); //  /     \
                bst.put(2, 2); // 1       6
                bst.put(6, 6); //  \     /
                bst.put(4, 4); //   2   4
                bst.put(5, 5); //        \
                                        //         5
                assertEquals("Checking keys in order", "(((()1(()2()))3((()4(()5()))6()))7(()8()))",
                                bst.printKeysInOrder());
        }
        
        @Test
        public void testPut() {
                BST<Integer, Integer> bst = new BST<Integer, Integer>();
                bst.put(7, null);
                assertEquals("Checking put for an element with value null", "()", bst.printKeysInOrder());
                bst.put(7, 7);
                assertEquals("Checking put for an element with a non-null value", "(()7())", bst.printKeysInOrder());
                bst.put(8, 8);
                assertEquals("Checking put for an element with a non-null value", "(()7(()8()))", bst.printKeysInOrder());
                bst.put(3, 3);
                assertEquals("Checking put for an element with a non-null value", "((()3())7(()8()))", bst.printKeysInOrder());
                bst.put(1, 1);
                assertEquals("Checking put for an element with a non-null value", "(((()1())3())7(()8()))", bst.printKeysInOrder());
                bst.put(1, 1);
                assertEquals("Checking put for an element with a non-null value", "(((()1())3())7(()8()))", bst.printKeysInOrder());
                bst.put(2, 2);
                assertEquals("Checking put for an element with a non-null value", "(((()1(()2()))3())7(()8()))", bst.printKeysInOrder());
                bst.put(6, 6);
                assertEquals("Checking put for an element with a non-null value", "(((()1(()2()))3(()6()))7(()8()))", bst.printKeysInOrder());
                bst.put(4, 4);
                assertEquals("Checking put for an element with a non-null value", "(((()1(()2()))3((()4())6()))7(()8()))", bst.printKeysInOrder());
                bst.put(5, 5);
                assertEquals("Checking put for an element with a non-null value", "(((()1(()2()))3((()4(()5()))6()))7(()8()))", bst.printKeysInOrder());
                bst.put(8, 10);
                assertEquals("Checking put for an element with a non-null value", "(((()1(()2()))3((()4(()5()))6()))7(()8()))", bst.printKeysInOrder());
        }

        @Test
        public void testGet() {
                BST<Integer, Integer> bst = new BST<Integer, Integer>();
                assertEquals("Checking get for an empty tree", null, bst.get(7));
                bst.put(7, 7); //        _7_
                bst.put(8, 8); //      /     \
                bst.put(3, 3); //    _3_      8
                bst.put(1, 1); //  /     \
                bst.put(2, 2); // 1       6
                bst.put(6, 6); //  \     /
                bst.put(4, 4); //   2   4
                bst.put(5, 5); //        \
                                        //         5
                assertEquals("Checking get for a tree", (Integer) 7, bst.get(7));
                assertEquals("Checking get for a tree", (Integer) 6, bst.get(6));
                assertEquals("Checking get for a tree", (Integer) 5, bst.get(5));
                assertEquals("Checking get for a tree", (Integer) 4, bst.get(4));
                assertEquals("Checking get for a tree", (Integer) 3, bst.get(3));
                assertEquals("Checking get for a tree", (Integer) 2, bst.get(2));
                assertEquals("Checking get for a tree", (Integer) 1, bst.get(1));
                assertEquals("Checking get for a tree", (Integer) 8, bst.get(8));
        }
        
        @Test
        public void testContains() {
                BST<Integer, Integer> bst = new BST<Integer, Integer>();
                assertEquals("Checking get for an empty tree", false, bst.contains(7));
                bst.put(7, 7);
                assertEquals("Checking get for an empty tree", true, bst.contains(7));
        }

        // @Test
        // public void testSelect() {
        //         BST<Integer, Integer> bst = new BST<Integer, Integer>();
        //         assertEquals("Checking select for an empty tree", null, bst.select(0));
        //         assertEquals("Checking select with key of rank bigger than size", null, bst.select(3));

        // }
}

