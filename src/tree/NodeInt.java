package tree;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class NodeInt implements Serializable {

    private int data;
    private NodeInt parent;
    private List<NodeInt> children;

    public NodeInt() {
    }

    public NodeInt(int data) {
        this.data = data;
        this.children = new LinkedList<>();
    }

    public NodeInt addChild(int childData) {
        NodeInt childNode = new NodeInt(childData);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

    public int getData() {
        return data;
    }

    public NodeInt getParent() {
        return parent;
    }

    public List<NodeInt> getChildren() {
        return children;
    }

    public int calcDescendants() {
        int numDes = 0;

        numDes += children.size();
        for (NodeInt child : children) {
            numDes += child.calcDescendants();
        }

        return numDes;
    }

    // call this for root node only!
    public List<NodeInt> getAllNodesOfTree(){
        List<NodeInt> allNodesOfTree = new LinkedList<>();

        allNodesOfTree.add(this);

        for (NodeInt child : children) {
            allNodesOfTree.addAll(child.getAllNodesOfTree());
        }

        return allNodesOfTree;
    }

    public NodeInt getRootNode() {
        if (parent == null) {
            return this;
        } else {
            return parent.getRootNode();
        }
    }

    public boolean isRootNode() {
        return parent == null;
    }

    public NodeInt cloneNode() {
        NodeInt cNode = new NodeInt(data);
        cNode.setChildren(children);
        cNode.setParent(parent);
        return cNode;
    }

    public String toString() {
        String retString = ""+ data;
        retString = retString + children.toString();
        return retString;
    }

    public void setParent(NodeInt parent) {
        this.parent = parent;
    }

    public void setChildren(List<NodeInt> children) {
        this.children = children;
    }

    public void setData(int data) {
        this.data = data;
    }
}
