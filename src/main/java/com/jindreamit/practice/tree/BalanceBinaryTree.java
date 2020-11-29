package com.jindreamit.practice.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BalanceBinaryTree {

    //根节点
    private Node root;

    public BalanceBinaryTree() {
    }

    public static void main(String[] args) {
        BalanceBinaryTree tree = new BalanceBinaryTree();
        //int[] values = {48, 58, 78, 80, 90, 51, 99, 88, 82, 25};
        int[] values = {10, 7, 15, 4, 8, 5};
//        int []values={1,2,3,4,5,6,
//                7,8,9
//                ,10,-1,-3,-5,-2,-11
//        };
        //生成树
        tree.add(values);
        //平衡树
        tree.levelTraverse();

        tree.preTraverse();

    }

    public void preTraverse(){
        Stack<Node> temStack=new Stack<>();
        temStack.add(root);
        while (true){
            if (temStack.empty()) {
                break;
            }
            Node node=temStack.pop();
            System.out.println(node.getValue());
            temStack.push(node.getLeftChild());
            temStack.push(node.getRightChild());
        }
    }

    //生成树
    public void add(Node node) {
        //根节点是否为空
        if (this.root == null) {
            //只有一个节点,将其设置根节点,高度设为0
            this.root = node;
            this.root.setHigh(0);
        } else {
            //插入节点,确定左右子树
            insert(this.root, node);
            //确定新插入节点是否导致不平衡树,返回问题节点
            Node minRoot = getMinNotBalanceTreeRoot(node);
            //当前插入节点导致二叉树不平衡
            if (minRoot != null) {
                //判断当前不平衡二叉树属于那种类型
                //插入节点  问题节点  插入节点的路径
                findNodeAndGetPath(node, minRoot, "");
                System.out.println("Path: " + this.path);
                //LL型
                if (this.path.equals("LLR") || this.path.equals("LLL") || this.path.equals("LL")) {
                    System.out.println("LL型");
                    this.llRotate(minRoot);
                }
                //LR型
                else if (this.path.equals("LRL") || this.path.equals("LRR") || this.path.equals("LR")) {
                    System.out.println("LR型");
                    this.lrRotate(minRoot);
                }
                //RL型
                else if (this.path.equals("RLL") || this.path.equals("RLR") || this.path.equals("RL")) {
                    System.out.println("RL型");
                    this.rlRotate(minRoot);
                }
                //RR型
                else if (this.path.equals("RRR") || this.path.equals("RRL") || this.path.equals("RR")) {
                    System.out.println("RR型");
                    this.rrRotate(minRoot);
                }
                System.out.println("=============旋转结束============");
                this.updateHigh(this.root);
            }
        }


    }

    //插入节点,判断是左孩子还是右孩子
    private void insert(Node root, Node node) {
        //右孩子
        if (node.getValue() > root.getValue())
            //是否存在右孩子
            if (root.getRightChild() != null)
                //存在右孩子,将右孩子作为根节点,递归调用
                insert(root.getRightChild(), node);
                //不存在右孩子,添加节点为当前根节点的右孩子
            else {
                System.out.println("-----------------------");
                System.out.println("值为 " + node.getValue() + " 插入到 父节点为 " + root);
                System.out.println("作为父节点的右孩子");
                System.out.println("-----------------------");
                //高度+1,设为父节点
                node.setHigh(root.getHigh() + 1);
                node.setParent(root);
                //根节点的右孩子设置为当前节点,是相互绑定,不能忘
                root.setRightChild(node);
            }
        //左孩子
        if (node.getValue() < root.getValue())
            //注解同上
            if (root.getLeftChild() != null)
                insert(root.getLeftChild(), node);
            else {
                System.out.println("值为 " + node.getValue() + " 插入到 父节点为 " + root);
                System.out.println("作为父节点的左孩子");
                System.out.println("-----------------------");
                System.out.println("-----------------------");
                node.setHigh(root.getHigh() + 1);
                node.setParent(root);
                root.setLeftChild(node);
            }
    }

    private void levelTraverse() {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(this.root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node != null) {
                System.out.println(node);
                if (node.getLeftChild() != null)
                    queue.offer(node.getLeftChild());
                if (node.getRightChild() != null)
                    queue.offer(node.getRightChild());
            }

        }
    }

    //获取树高
    private int getHigh(Node node) {
        if (node == null)
            return 0;
        //找到最底层的叶子节点
        if (node.getLeftChild() == null && node.getRightChild() == null) {
            //返回树高
            return node.getHigh();
        }
        //当前节点不为最底层叶子节点,递归,返回当前节点存在的左/右孩子
        else
            /*
            Math.max     (a >= b) ? a : b;
             */
            return Math.max(getHigh(node.getLeftChild()), getHigh(node.getRightChild()));
    }


    /**
     * 从新插入的节点开始,由下向上搜索,找到最小不平衡子树
     *
     * @param node 新插入的节点
     * @return 最小不平衡子树的根节点
     */
    private Node getMinNotBalanceTreeRoot(Node node) {
        //每插入一个节点就判断一次是否平衡
        int leftHigh = node.getHigh();
        int rightHigh = node.getHigh();
        //当前节点的左子树最大高度
        if (node.getLeftChild() != null)
            leftHigh = getHigh(node.getLeftChild());
        //当前节点的右子树最大高度
        if (node.getRightChild() != null)
            rightHigh = getHigh(node.getRightChild());
        //不平衡,返回当前节点
        if (Math.abs(leftHigh - rightHigh) > 1)
            return node;
            //如果是根节点且左右子树高度差的绝对值<1,则没有不平衡的子树
        else if (node == this.root)
            return null;
            //并无上述两种情况,则继续递归,直到出现返回值
        else
            return getMinNotBalanceTreeRoot(node.getParent());

    }

    /**
     * 判断子树的不平衡情况 有四种:LL,LR,RL,RR
     *
     * @param node
     * @param root
     * @return
     */
    private Type judgeType(Node node, Node root) {
        return null;
    }

    //保存插入节点的路径
    private String path = "";

    public String getPath() {
        return path;
    }

    //利用递归找到插入节点的路径

    private void findNodeAndGetPath(Node node, Node root, String temPath) {
        //节点为根节点
        if (node == root) {
            this.path = temPath;
            return;
        }
        //插入的节点值是问题节点的右子树下的节点
        if (node.getValue() > root.getValue() && root.getRightChild() != null) {
            //递归
            findNodeAndGetPath(node, root.getRightChild(), temPath + "R");
        }
        //插入的节点值是问题节点的左子树下的节点
        else if (node.getValue() < root.getValue() && root.getLeftChild() != null) {
            //递归
            findNodeAndGetPath(node, root.getLeftChild(), temPath + "L");
        }
    }


    private enum Type {
        LL,
        LR,
        RR,
        RL
    }

    /**
     * 左旋
     *
     * @param root
     */
    private void leftRotate(Node root) {
        System.out.println("左旋: " + root.getValue());
        Node rootParent = root.getParent();
        Node rootRightChild = root.getRightChild();
        //如果左旋的节点不是根节点,则替代父节点
        if (rootParent != null) {
            if (root == rootParent.getLeftChild())
                rootParent.setLeftChild(rootRightChild);
            else if (root == rootParent.getRightChild())
                rootParent.setRightChild(rootRightChild);
        } else
            //如果左旋的节点是根节点,则更新根节点为右孩子
            this.root = rootRightChild;
        //右孩子的左子树成为根节点的右子树
        root.setRightChild(rootRightChild.getLeftChild());
        if (rootRightChild.getLeftChild() != null) {
            rootRightChild.getLeftChild().setParent(root);
        }
        //根节点的父节点更新为右孩子
        root.setParent(rootRightChild);
        //右孩子的父节点从 root 更新为 rootParent
        rootRightChild.setParent(rootParent);
        //根节点成为右孩子的左孩子
        rootRightChild.setLeftChild(root);

    }


    /**
     * 右旋
     *
     * @param root
     */
    private void rightRotate(Node root) {
        System.out.println("右旋: " + root.getValue());
        //获取问题节点的父节点
        Node rootParent = root.getParent();
        //获取问题节点的左孩子
        Node rootLeftChild = root.getLeftChild();
        //问题节点非根节点
        if (rootParent != null) {
            //问题节点为左孩子
            if (root == rootParent.getLeftChild())
                //问题节点的左孩子设为父节点的左孩子
                rootParent.setLeftChild(rootLeftChild);
                //问题节点为右孩子
            else if (root == rootParent.getRightChild())
                //问题节点的右孩子设为父节点的右孩子
                rootParent.setRightChild(rootLeftChild);
        }
        //问题节点为根节点
        else
            //如果右旋的节点是根节点,则更新根节点为右孩子
            this.root = rootLeftChild;
        //更新左孩子的父节点为 rootParent
        rootLeftChild.setParent(rootParent);
        //更新 root 的父节点为 rootLeftChild
        root.setParent(rootLeftChild);
        //左孩子的右子树成为根节点的左子树
        root.setLeftChild(rootLeftChild.getRightChild());
        //左孩子的右孩子的父节点更新为root
        if (rootLeftChild.getRightChild() != null)
            rootLeftChild.getRightChild().setParent(root);
        //左孩子的右子树更新为 root
        rootLeftChild.setRightChild(root);


    }


    private void rrRotate(Node root) {
        this.leftRotate(root);
    }

    private void rlRotate(Node root) {
        this.rightRotate(root.getRightChild());
        this.leftRotate(root);
    }

    private void llRotate(Node root) {
        this.rightRotate(root);
    }

    private void lrRotate(Node root) {
        this.leftRotate(root.getLeftChild());
        this.rightRotate(root);

    }

    private void updateHigh(Node root) {
        if (root.getParent() == null)
            root.setHigh(0);
        else
            root.setHigh(root.getParent().getHigh() + 1);
        if (root.getLeftChild() != null)
            updateHigh(root.getLeftChild());
        if (root.getRightChild() != null)
            updateHigh(root.getRightChild());
    }

    private void midTraverse(Node root) {
        if (root.getLeftChild() != null)
            midTraverse(root.getLeftChild());
        System.out.println(root.getValue());
        if (root.getRightChild() != null)
            midTraverse(root.getRightChild());
    }

    public void leftRotateTest() {
        BalanceBinaryTree tree = new BalanceBinaryTree();
        Node _48 = new Node(48);
        Node _58 = new Node(58);
        Node _76 = new Node(76);
        tree.add(_48);
        tree.add(_58);
        tree.add(_76);
        tree.levelTraverse();
        System.out.println("=================");
        Node node = tree.getMinNotBalanceTreeRoot(_76);
        tree.leftRotate(node);
        tree.levelTraverse();
        System.out.println("=================");
        Node _80 = new Node(80);
        Node _90 = new Node(90);
        tree.add(_80);
        tree.add(_90);
        node = tree.getMinNotBalanceTreeRoot(_90);
        System.out.println(node);
        tree.leftRotate(node);
        System.out.println("=================");
        tree.levelTraverse();
    }

    public void rightRotateTest() {
        BalanceBinaryTree tree = new BalanceBinaryTree();
        Node _48 = new Node(48);
        Node _58 = new Node(58);
        Node _50 = new Node(50);
        tree.add(_48);
        tree.add(_58);
        tree.add(_50);
        tree.levelTraverse();
        System.out.println("==================");
        Node node = tree.getMinNotBalanceTreeRoot(_50);
        tree.findNodeAndGetPath(_50, node, "");

        System.out.println("getPath() Begin");
        System.out.println(tree.getPath());
        System.out.println("getPath() Over");
//
//
//        System.out.println(node);
//        System.out.println("==================");
//        tree.rightRotate(node.getRightChild());
//        tree.levelTraverse();
//        tree.leftRotate(node);
//        System.out.println("==================");
//        tree.levelTraverse();
    }

    public void getPathTest() {
        BalanceBinaryTree tree = new BalanceBinaryTree();
        Node _48 = new Node(48);
        Node _58 = new Node(58);
        Node _76 = new Node(76);
        tree.add(_48);
        tree.add(_58);
        tree.add(_76);
        tree.levelTraverse();
        System.out.println("=================");
        Node node = tree.getMinNotBalanceTreeRoot(_76);
        tree.leftRotate(node);
        tree.levelTraverse();
        System.out.println("=================");
        Node _80 = new Node(80);
        Node _90 = new Node(90);
        tree.add(_80);
        tree.add(_90);
        node = tree.getMinNotBalanceTreeRoot(_90);
        tree.findNodeAndGetPath(_90, node, "");
        System.out.println("getPath() Begin");
        System.out.println(tree.getPath());
        System.out.println("getPath() Over");
        System.out.println(node);
        tree.leftRotate(node);
        System.out.println("=================");
        tree.levelTraverse();

    }

    public void add(int[] values) {
        for (int value : values) {
            this.add(new Node(value));
        }
    }

}

class Node {
    private int value;  //值
    private int high;   //高度
    private Node leftChild;   //左孩子
    private Node rightChild;  //右孩子
    private Node parent;      //父节点


    public Node(int value) {
        this.value = value;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", high=" + high +
                ", leftChild=" + leftChild +
                ", rightChild=" + rightChild +
                '}';
    }
}