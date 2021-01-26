package Main;

import generator.GeneratorNewTreesSeq;
import gui.TreePanel;
import help.TreeOperations;
import help.XMLHandler;
import tree.NodeInt;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // init trees list used for graphic output
        List<NodeInt> trees = new LinkedList<>();

        // generate tree sequence
        try {
            trees = GeneratorNewTreesSeq.generateTreeSequence(5);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(trees.size());

        // verify tree sequence (takes a lot longer than the generation of the sequence)
//        System.out.println("Verify sequence");
//        if(TreeOperations.verifyTreeSequence(trees)){
//            java.lang.System.out.println("Tree sequence valid");
//        }
//        else{
//            System.out.println("Tree sequence not valid");
//        }

        //Store generated trees in folder "xmlFile"
//        String filePath = new File("").getAbsolutePath();
//        filePath+="\\xmlFile\\";
//        java.lang.System.out.println(filePath);
//        int t_iterator = 0;
//        for (NodeInt tree : trees) {
//            String filename =filePath + t_iterator + ".xml";
//            t_iterator++;
//
//            XMLHandler.serialize(tree, filename);
//        }

        // load stored xmlSequence
//        String[] pathNames;
//        String filePath = new File("").getAbsolutePath();
//        filePath+="\\xmlFile\\";
//        pathNames = new File(filePath).list();
//        List<String> pathNameList = Arrays.asList(pathNames);
//
//        for(int pl=1; pl<pathNameList.size();pl++){
//            System.out.println(pathNameList.get(pl));
//            String lPath = pathNameList.get(pl);
//            NodeInt lTree = (NodeInt)XMLHandler.deserialize(filePath+lPath);
//            trees.add(lTree);
//        }

        //Where instance variables are declared:
        JTabbedPane cards;
        final String CARDPANEL1 = "1";
        final String CARDPANEL2 = "2";
        final String CARDPANEL3 = "3";
        final String CARDPANEL4 = "4";
        final String CARDPANEL5 = "5";
        final String CARDPANEL6 = "6";
        final String CARDPANEL7 = "7";
        final String CARDPANEL8 = "8";
        final String CARDPANEL9 = "9";
        final String CARDPANEL10 = "10";
        final String CARDPANEL11 = "Ende";

        cards = new JTabbedPane();

        // create frame and display forrest stored in trees
        JFrame frame = new JFrame("TreeThree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setPreferredSize(new Dimension(1900, 1100));

        JPanel card1 = getNewPanel(18.0f,1,0,3, trees);
        JPanel card2 = getNewPanel(9.0f,2,3,15, trees);
        JPanel card3 = getNewPanel(6.0f,3,15,42, trees);
        JPanel card4 = getNewPanel(4.5f,4,42,90, trees);
        JPanel card5 = getNewPanel(3.6f,5,90,165, trees);
        JPanel card6 = getNewPanel(3.0f,6,165,273, trees);
        JPanel card7 = getNewPanel(2.572f,7,273,420, trees);
        JPanel card8 = getNewPanel(2.25f,8,420,612, trees);
        JPanel card9 = getNewPanel(2.0f,9,612,855, trees);
        JPanel card10 = getNewPanel(1.8f,10,855,1155, trees);
//        JPanel card11 = getNewPanel(1.8f,10,342895,343195, trees);

        //Create the panel that contains the "cards".

        cards.add(card1, CARDPANEL1);
        cards.add(card2, CARDPANEL2);
        cards.add(card3, CARDPANEL3);
        cards.add(card4, CARDPANEL4);
        cards.add(card5, CARDPANEL5);
        cards.add(card6, CARDPANEL6);
        cards.add(card7, CARDPANEL7);
        cards.add(card8, CARDPANEL8);
        cards.add(card9, CARDPANEL9);
        cards.add(card10, CARDPANEL10);
//        cards.add(card11, CARDPANEL11);

        frame.add(cards);
        frame.setVisible(true);
        frame.pack();

    }

    public static JPanel getNewPanel(float zoom, int rows, int startIndex, int stopIndex, List<NodeInt> trees){

        List<NodeInt> treesShow = new LinkedList<>();
        JPanel card = new JPanel();

        for(int pl=startIndex; pl<stopIndex;pl++){
//            NodeInt lTree = trees.get(pl);
            if(trees.size() > pl) {
                treesShow.add(trees.get(pl));
            }
        }

        card.setLayout(new GridLayout(rows, 70, 0, 0));

        int ti=startIndex+1;
        for (NodeInt tree : treesShow) {
            TreePanel treetmp = new TreePanel(zoom);
            //treetmp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            treetmp.setTreeIndex(ti++);
            treetmp.setRootNode(tree);
            card.add(treetmp);
        }

        return card;
    }
}
