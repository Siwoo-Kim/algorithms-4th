package p1;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * P.1.3.19
 *
 * Delete the last node of the linked list
 * when first node is "first".
 *
 */
public class DeleteLastNode {

    private static class Node {
        private Node next;
        private int val;

        public Node(int val) {
            this.val = val;
        }
    }

    private final Node first;

    public DeleteLastNode(Node first) {
        checkNotNull(first);
        this.first = first;
    }

    public Node deleteLastNode() {
        if (first.next == null)
            return first;
        return del(first, null);
    }

    private Node del(Node first, Node prev) {
        if (first.next == null)  {
            prev.next = null;
            return null;
        }
        first.next = del(first.next, first);
        return first;
    }

    public static void main(String[] args) {
        Node node = new Node(1);
        node.next = new Node(2);
        node.next.next = new Node(3);
        node.next.next.next = new Node(4);
        node.next.next.next.next = new Node(5);
        DeleteLastNode DLN = new DeleteLastNode(node);
        node = DLN.deleteLastNode();
        assertThat(node.next.next.next.next).isNull();
        node = DLN.deleteLastNode();
        assertThat(node.next.next.next).isNull();
        node = DLN.deleteLastNode();
        assertThat(node.next.next).isNull();
        node = DLN.deleteLastNode();
        assertThat(node.next).isNull();
        node = DLN.deleteLastNode();
        assertThat(node.next).isNull();
    }
}
