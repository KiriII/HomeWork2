import Tree.BinaryTree;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Pattern;


import static  Tree.BinaryTree.*;
import static org.testng.AssertJUnit.assertEquals;


public class test {
    //       3
    //      / \
    //     2   4
    //    /   / \
    //   1    5  6
@Test
    public void testMinValue(){
    BinaryTree<Integer> tree = new BinaryTree();
    //метод add работает криво
    tree.root = new Node<Integer>(3);
    tree.root.left = new Node<Integer>(2);
    tree.root.left.left = new Node<Integer>(1);
    tree.root.right = new Node<Integer>(5);
    tree.root.right.left = new Node<Integer>(4);
    tree.root.right.right = new Node<Integer>(6);
    assertEquals(1 , (int)tree.root.minValue());
    assertEquals(4 , (int)tree.root.right.minValue());

}
@Test
    public void testFindFather(){
    BinaryTree<Integer> tree = new BinaryTree();
    tree.root = new Node<Integer>(3);
    tree.root.left = new Node<Integer>(2);
    tree.root.left.left = new Node<Integer>(1);
    tree.root.right = new Node<Integer>(5);
    tree.root.right.left = new Node<Integer>(4);
    tree.root.right.right = new Node<Integer>(6);
    assertEquals(3 , (int)tree.findFather(tree.root , 2).value);
    assertEquals(5 , (int)tree.findFather(tree.root , 4).value);
    assertEquals(2 , (int)tree.findFather(tree.root , 1).value);
}

    @Test
    public void testRemove(){
        BinaryTree<Integer> tree = new BinaryTree();
        tree.root = new Node<Integer>(3);
        tree.root.left = new Node<Integer>(2);
        tree.root.left.left = new Node<Integer>(1);
        tree.root.right = new Node<Integer>(5);
        tree.root.right.left = new Node<Integer>(4);
        tree.root.right.right = new Node<Integer>(6);
        tree.remove(tree.root.right.right.value);
        assertEquals(null , tree.root.right.right);
        tree.root.right.right = new Node<Integer>(6);
        tree.remove(tree.root.right.value);
        assertEquals(6 , (int)tree.root.right.value);
        tree.remove(tree.root.left.value);
        assertEquals(1 , (int)tree.root.left.value);
    }

    @Test
    public void iteratorHasNext(){
        BinaryTree<Integer> tree = new BinaryTree();
        tree.root = new Node<Integer>(3);
        tree.root.left = new Node<Integer>(2);
        tree.root.left.left = new Node<Integer>(1);
        tree.root.right = new Node<Integer>(5);
        tree.root.right.left = new Node<Integer>(4);
        tree.root.right.right = new Node<Integer>(6);
    }
}
