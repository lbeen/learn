package lb.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * 树
 *
 * @author 李斌
 */
public class Tree<T> {
    /**
     * 最上层节点
     */
    private List<Node<T>> topNodes;
    /**
     * 是否是子节点比较器，前一个是后一个子节点返回true
     */
    private BiFunction<T, T, Boolean> isChild;

    /**
     * 构造一个tree
     *
     * @param ts      数据
     * @param isChild 是否是子节点比较器，前一个是后一个子节点返回true
     */
    public Tree(List<T> ts, BiFunction<T, T, Boolean> isChild) {
        this.isChild = isChild;
        this.topNodes = new ArrayList<>();
        List<Node<T>> nodes = new ArrayList<>();
        for (T t : ts) {
            nodes.add(new Node<>(t));
        }
        thisFor:
        for (int i = 0, size = nodes.size(); i < size; i++) {
            Node<T> thisNode = nodes.get(i);
            if (thisNode.parent == null) {
                for (int j = 0; j < size; j++) {
                    if (j != i) {
                        Node<T> otherNode = nodes.get(j);
                        if (isChild.apply(thisNode.element, otherNode.element)) {
                            otherNode.addChild(thisNode);
                            thisNode.parent = thisNode;
                            continue thisFor;
                        }
                    }
                }
                topNodes.add(thisNode);
            }
        }
    }

    /**
     * 获取最上层节点
     */
    public List<Node<T>> topNodes() {
        return topNodes;
    }

    /**
     * 添加一个元素
     */
    public void add(T t) {
        Node<T> parant = getParent(t, topNodes);
        Node<T> tNode = new Node<>(t);
        if (parant == null) {
            topNodes.add(tNode);
        } else {
            parant.addChild(tNode);
            tNode.parent = parant;
        }
    }

    /**
     * 根据元素移除一个节点
     *
     * @param t           元素
     * @param removeChild 是否移除子节点
     */
    public Node<T> remove(T t, boolean removeChild) {
        Node<T> node = getNode(t);
        if (node == null) {
            return null;
        }
        Node<T> parent = node.parent;
        if (parent == null) {
            topNodes.remove(node);
        } else {
            parent.removeChild(node);
        }
        if (!removeChild) {
            List<Node<T>> childs = node.childs;
            if (childs != null) {
                topNodes.addAll(childs);
            }
        }
        return node;
    }

    /**
     * 根据元素获取节点
     *
     * @param t 元素
     */
    private Node<T> getNode(T t) {
        return getNode(t, topNodes);
    }

    /**
     * 递归获取父节点
     */
    private Node<T> getParent(T t, List<Node<T>> nodes) {
        for (Node<T> node : topNodes) {
            if (isChild.apply(node.element, t)) {
                return node;
            } else {
                List<Node<T>> childs = node.childs;
                if (childs != null) {
                    Node<T> parant = getParent(t, node.childs);
                    if (parant != null) {
                        return parant;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 递归获取节点
     */
    private Node<T> getNode(T t, List<Node<T>> nodes) {
        for (Node<T> node : topNodes) {
            if (t == node.element) {
                return node;
            } else {
                List<Node<T>> childs = node.childs;
                if (childs != null) {
                    Node<T> node1 = getNode(t, node.childs);
                    if (node1 != null) {
                        return node1;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 节点
     */
    public static class Node<T> {
        private Node<T> parent;
        private List<Node<T>> childs;
        private T element;

        private Node(T element) {
            this.element = element;
        }

        private void addChild(Node<T> node) {
            if (childs == null) {
                childs = new ArrayList<>();
            }
            childs.add(node);
        }

        private void removeChild(Node<T> node) {
            if (childs != null) {
                childs.remove(node);
                if (childs.size() == 0) {
                    childs = null;
                }
            }
        }

        public Node<T> getParent() {
            return parent;
        }

        public List<Node<T>> getChilds() {
            return childs;
        }

        public T getElement() {
            return element;
        }
    }
}
