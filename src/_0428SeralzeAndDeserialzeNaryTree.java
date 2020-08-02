import java.util.*;

class Node1 {
    public int val;
    public List<Node1> children;

    public Node1() {}

    public Node1(int _val) {
        val = _val;
    }

    public Node1(int _val, List<Node1> _children) {
        val = _val;
        children = _children;
    }
};

public class _0428SeralzeAndDeserialzeNaryTree {
    /**
     * 1. Pre-order Traversal Recursion
     *
     * ? Why stateless: Do not use class member/global/static variables to store states
     *
     * ? StringBuilder.append vs Strng.join
     * if you already have an array of strings to concatenate together (with a delimiter),
     * String.Join is the fastest way of doing it.
     *
     * ? Queue interface or Deque interface
     *
     * ? Integer <-> String
     * String.valueOf
     * Integer.parseInt=
     *
     *
     *              1
     *           3  2  4
     *          5 6
     *
     * (1), 3, (3), 2, (5), 0, (6), 0, (2), 0, (4), 0
     *
     */
    class Codec {

        // Encodes a tree to a single string.
        public String serialize(Node1 root) {
            List<String> list=new ArrayList<>();
            serializeHelper(root,list);
            return String.join(",",list);
        }

        private void serializeHelper(Node1 root, List<String> list){
            if(root==null){
                return;
            }else{
                list.add(String.valueOf(root.val));
                list.add(String.valueOf(root.children.size()));
                for (Node1 child:root.children){
                    serializeHelper(child,list);
                }
            }
        }

        // Decodes your encoded data to tree.
        public Node1 deserialize(String data) {
            if(data == null || data.isEmpty()) {
                return null;
            }

            String[] ss=data.split(",");
            Queue<String> q = new ArrayDeque<>(Arrays.asList(ss));
            return deserializeHelper(q);
        }

        private Node1 deserializeHelper(Queue<String> q){
            Node1 root=new Node1();
            root.val=Integer.parseInt(q.poll());
            int size=Integer.parseInt(q.poll());
            root.children = new ArrayList<Node1>(size);
            for(int i=0;i<size;i++){
                root.children.add(deserializeHelper(q));
            }
            return root;
        }
    }

}
