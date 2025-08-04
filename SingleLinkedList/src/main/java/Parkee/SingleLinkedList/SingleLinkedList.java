package Parkee.SingleLinkedList;

import java.util.Optional;

public class SingleLinkedList {
    class Node {
        int data;
        Node next;

        Node (int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;

    public void display() {
        if (head == null) {
            return;
        }

        Node current = head;

        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
        
    }

    public Optional<Node> getHeadData () {
        if (head == null) {
            return Optional.empty();
        }

        return Optional.of(head);
    }

    public void insertAtBeginning(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
    }


    public void insertAtEnd(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            return;
        }
       
        Node current = head;

        while(current.next != null) {
            current = current.next;
        }

        current.next = newNode;
    }


    public void deleteByValue(int data) {
        if (head == null) {
            return;
        }

        if (head.data == data) {
            head = head.next;
            return;
        }

        Node current = head;

        while(current.next != null && current.next.data != data) {
            current = current.next;
        }
        
        if (current.next != null) {
            current.next = current.next.next;
        }
       
    }

    public int size() {
        int count = 0;
        Node current = head;
        while(current != null) {
            count += 1;
            current = current.next;
        }
        return count;
    }


    public boolean contains(int data) {
        Node current = head;

        while(current != null) {
            if (current.data == data) {
                return true;
            }

            current = current.next;
        }

        return false;
    }


    public boolean isEmpty () {
        return head == null;
    }

}