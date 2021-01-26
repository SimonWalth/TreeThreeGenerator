package help;

import tree.NodeInt;

import java.util.LinkedList;
import java.util.List;

public class TreeOperations {

    /**
     * Check if two given trees are equal
     *
     * @param cNode  Current Node
     * @param eqNode Equal Node that is checked against
     * @return true if trees are equal, else false
     */

    public static boolean treeEqual(NodeInt cNode, NodeInt eqNode) {
        if (cNode.getData() == eqNode.getData()) {
            // nodes equal if both children are leaves of tree
            if (cNode.getChildren().size() == eqNode.getChildren().size() && cNode.getChildren().size() == 0) {
                return true;
            }
        }
        // if data not equal or if they don't have the same number of children or they don't have the same number of nodes they are surely not equal
        if (cNode.getData() != eqNode.getData() || cNode.getChildren().size() != eqNode.getChildren().size() || cNode.calcDescendants() != eqNode.calcDescendants()) {
            return false;
        } else {
            // check all children branches
            List<NodeInt> branchesTaken = new LinkedList<>();
            for (int ti = 0; ti < cNode.getChildren().size(); ti++) {
                NodeInt cChild = cNode.getChildren().get(ti);

                boolean childBranchFound = false;

                for (int ei = 0; ei < eqNode.getChildren().size(); ei++) {
                    NodeInt eChild = eqNode.getChildren().get(ei);
                    if (cChild.getData() == eChild.getData()) {
                        //check branch
                        if (treeEqual(cChild, eChild)) {
                            // branch found
                            // branch has not been taken yet ?
                            if (!branchesTaken.contains(eChild)) {
                                childBranchFound = true;
                                branchesTaken.add(eChild);
                                break;
                            }
                        }
                    }
                }
                if (!childBranchFound) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * checks if previous Tree is contained in new Tree
     *
     * @param prevTree previous tree in sequence
     * @param newTree new tree for sequence
     * @return true if tree is contained, else false
     */

    public static boolean treeContains(NodeInt prevTree, NodeInt newTree) {
        // if data is equal and if prevTree has no children -> prevTree is contained
        if (prevTree.getData() == newTree.getData() && prevTree.getChildren().size() == 0) {
            return true;
        }
        // if newTree is smaller than prevTree -> prevTree is surely not contained
        if (newTree.calcDescendants() < prevTree.calcDescendants()) {
            return false;
        }

        // branch can only be considered once, so store taken branches
        List<NodeInt> branchesTaken = new LinkedList<>();
        if (prevTree.getData() == newTree.getData()) {
            // node match: now search for branches of prevTree
            List<NodeInt> branchesToFind = prevTree.getChildren();
            boolean containsBranches = containsBranches(branchesToFind, newTree, branchesTaken);
            if (containsBranches) {
                return true;
            } else {
                // we still have to check the hole hierarchy so try to match with next level of next tree
                for (int nt = 0; nt < newTree.getChildren().size(); nt++) {
                    NodeInt newChild = newTree.getChildren().get(nt);
                    if (treeContains(prevTree, newChild)) {
                        return true;
                    }
                }
                return false;
            }
        } else {
            // no node match: continue searching
            // search for branch in new Tree
            for (int nt = 0; nt < newTree.getChildren().size(); nt++) {
                NodeInt newChild = newTree.getChildren().get(nt);
                if (treeContains(prevTree, newChild)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * check if new tree contains given branches
     *
     * @param branchesToFind branches that need to be checked
     * @param newTree tree that is checked
     * @param branchesTaken branches that are marked as taken
     * @return true if branches are in new tree, else false
     */

    public static boolean containsBranches(List<NodeInt> branchesToFind, NodeInt newTree, List<NodeInt> branchesTaken) {
        // find branches
        for (int pt = 0; pt < branchesToFind.size(); pt++) {
            boolean containsBranch = false;
            List<NodeInt> listBranchMatch = new LinkedList<>();
            // get branches that still need to be found
            NodeInt prevChild = branchesToFind.get(pt);
            // search for branches in new Tree that contain branch
            for (int nt = 0; nt < newTree.getChildren().size(); nt++) {
                NodeInt newChild = newTree.getChildren().get(nt);
                // if branch was found

                if (treeContains(prevChild, newChild)) {
                    listBranchMatch.add(newChild);
                }
            }
            // now iterate over found matches for this branch
            for (NodeInt branchMatch : listBranchMatch) {
                // if it has not been taken yet
                if (!branchesTaken.contains(branchMatch)) {
                    // check all branch combinations -> create new list for branch taken and branches still need to be found and iterate
                    List<NodeInt> listBranchTakenCpy = new LinkedList<>(branchesTaken);
                    listBranchTakenCpy.add(branchMatch);
                    List<NodeInt> listBranchToFindCpy = new LinkedList<>(branchesToFind);
                    listBranchToFindCpy.remove(prevChild);
                    if (containsBranches(listBranchToFindCpy, newTree, listBranchTakenCpy)) {
                        containsBranch = true;
                    }
                }
            }
            if (!containsBranch) {
                return false;
            }
        }
        return true;
    }

    /**
     * verify a given tree sequence
     *
     * @param treeSeq   list of trees in sequence
     * @return          true if sequence is Tree(3) conform
     */

    public static Boolean verifyTreeSequence(List<NodeInt> treeSeq) {

        List<NodeInt> treeSeqCpy = new LinkedList<>(treeSeq);

        while (!treeSeqCpy.isEmpty()) {
            NodeInt firstElem = treeSeqCpy.get(0);
            treeSeqCpy.remove(0);

            System.out.println("Verification steps left: " + treeSeqCpy.size());

            for (int i = 0; i < treeSeqCpy.size(); i++) {
                NodeInt elem = treeSeqCpy.get(i);

                if (treeContains(firstElem, elem)) {
                    System.out.println("tree " + (treeSeq.size() - treeSeqCpy.size() + i + 1) + " contains tree " + (treeSeq.size() - treeSeqCpy.size()));
                    return false;
                }

            }
        }

        return true;
    }

}
