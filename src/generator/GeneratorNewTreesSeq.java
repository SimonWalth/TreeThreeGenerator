package generator;

import help.ObjectCloner;
import help.TreeOperations;
import tree.NodeInt;

import java.util.LinkedList;
import java.util.List;

public class GeneratorNewTreesSeq {

    private static final int TREE = 3;

    /**
     * generates tree roots
     *
     * @return tree list
     */

    private static List<NodeInt> generateTreeRoots() {
        // generate new Tree and check if rules are satisfied
        List<NodeInt> treeList = new LinkedList<>();

        // create roots
        for (int n = 0; n < TREE; n++) {
            NodeInt root = new NodeInt(n);
            treeList.add(root);
        }
        return treeList;
    }

    /**
     * Generate Tree Sequence with max number of nodes per tree
     *
     * @param   maxTreeIndex maximal nodes in a tree
     * @return  tree list sequence
     * @throws Exception if Error in ObjectCloner
     */
    public static List<NodeInt> generateTreeSequence(int maxTreeIndex) throws Exception{
        List<NodeInt> treeListSeq = new LinkedList<>();
        int treeIndex = 0;

        List<NodeInt> listPosNextTrees = new LinkedList<>(generateTreeRoots());

        treeListSeq.addAll(treeIndex,listPosNextTrees);
        treeIndex++;

        while(treeIndex<maxTreeIndex){
            List<NodeInt> newPosNextTrees = new LinkedList<>();
            List<NodeInt> treeListSeqCmp = treeListSeq.subList(0,treeIndex);
            for(NodeInt posNextTree : listPosNextTrees){
                // expand possible Next Trees of previous index
                expandTree(posNextTree, treeIndex, newPosNextTrees, treeListSeqCmp);
            }
            listPosNextTrees = new LinkedList<>(newPosNextTrees);
            // TODO debug: delete later
//            listPosNextTrees.set(0, newPosNextTrees.get(1));
//            listPosNextTrees.set(1, newPosNextTrees.get(0));
            // -----------------------

            treeListSeq.addAll(treeIndex,listPosNextTrees);
            treeIndex++;
        }

        return treeListSeq;
    }

    /**
     * recursively expands a given tree until max number of nodes is reached
     *
     * @param treeNode          Tree to expand
     * @param TreeIndex         Number of Nodes in tree (just for error catching)
     * @param treeList          Already created trees
     * @param treeListSeqCmp    Already defined sequence
     * @throws Exception if Error in ObjectCloner
     */

    private static void expandTree(NodeInt treeNode, int TreeIndex, List<NodeInt> treeList, List<NodeInt> treeListSeqCmp) throws Exception {
        // total number of nodes is root node + all descendants
        int numberNodes = treeNode.getRootNode().calcDescendants() + 1;
        // continue until desired number of nodes is reached
        if (numberNodes == TreeIndex) {
            //expand tree:
            // get all nodes of tree
            NodeInt treeNodeRoot = treeNode.getRootNode();
            List<NodeInt> treeNodes = treeNodeRoot.getAllNodesOfTree();

            for(NodeInt tNode : treeNodes){
                // add all possible nodes on this hierarchy or the next but not if it is the root node
                if (!tNode.isRootNode()) {
                    // add to this hierarchy
                    NodeInt parentNode = tNode.getParent();
                    for (int n = 1; n < TREE; n++) {
                        // clone parent node
                        NodeInt cloneNode = (NodeInt) ObjectCloner.deepCopy(parentNode);
                        // create a new tree by adding a node to it
                        NodeInt newNode = cloneNode.addChild(n);
                        // add the new tree to the tree list if it does not already exist
                        addTree(newNode, treeList, treeListSeqCmp);
                    }
                }
                //add to next hierarchy
                for (int n = 1; n < TREE; n++) {
                    // clone curr node
                    NodeInt cloneNode = (NodeInt) ObjectCloner.deepCopy(tNode);
                    // create a new tree by adding a node to it
                    NodeInt newNode = cloneNode.addChild(n);
                    // add the new tree to the tree list if it does not already exist
                    addTree(newNode, treeList, treeListSeqCmp);
                }
            }

        }
    }

    /**
     * Check if tree already exists, fits in sequence then add to treeList
     *
     * @param newNode           node of tree that was expanded
     * @param treeList          Already created trees
     * @param treeListSeqCmp    Already defined sequence
     */

    private static void addTree(NodeInt newNode, List<NodeInt> treeList, List<NodeInt> treeListSeqCmp) {
        // get the root of the new tree
        NodeInt newNodeRoot = newNode.getRootNode();

        // check if tree already exists
        boolean treeAlreadyExists = false;
        for (NodeInt sTree : treeList) {
            if (TreeOperations.treeEqual(sTree, newNodeRoot)) {
                treeAlreadyExists = true;
                break;
            }
        }
        if (!treeAlreadyExists) {
            // check if tree is not contained in sequence
            boolean contained = false;
            for (NodeInt prevTree : treeListSeqCmp) {
                if (TreeOperations.treeContains(prevTree, newNodeRoot)) {
                    contained = true;
                }
            }
            if(!contained) {
                // if tree does not already exist add it to treeList
                treeList.add(newNode.getRootNode());
//                System.out.println("Tree List Size: " + treeList.size() + " _ " + newNodeRoot.getData());
            }
        }
    }
}
