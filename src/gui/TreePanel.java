package gui;

import tree.NodeInt;

import javax.swing.*;
import java.awt.*;

public class TreePanel extends JPanel {

    private float zoom;

    private int width = 30;
    private int height = 50;

    private int maxAngle = 90 ;
    private int circleSize = 2;
    private int cirDistance = 7;
    private int thickness = 1;
    private int indexSize = 2;

    private int treeIndex;
    private NodeInt rootNode;

    public TreePanel(float zoom){

        width = (int)(zoom * width);
        height = (int)(zoom * height);
        circleSize = (int)(zoom * circleSize);
        cirDistance = (int)(zoom * cirDistance);
        thickness = (int) zoom/3;
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // clear the panel
        g.clearRect(0,0,width, height);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 8));
//        g.drawString(String.valueOf(treeIndex),0,8);

        drawNode(g,rootNode, (width-circleSize)/2, 5);
    }

    private void drawNode(Graphics g, NodeInt currNode, int x_pos, int y_pos){
        // draw current node then iterate over children
        // set color
        g.setColor(chooseColor(currNode.getData()));

        g.drawOval(x_pos,y_pos,circleSize,circleSize);
        g.fillOval(x_pos,y_pos,circleSize,circleSize);
        for(int c=0; c< currNode.getChildren().size();c++){
            // calc angle and distance
            int calcAngle,calcX,calcY;
            calcAngle = calcAngle(currNode.getChildren().size(),c);
            calcX = ((int)(cirDistance* Math.sin(Math.toRadians(calcAngle)))) +x_pos;
            calcY = ((int)(cirDistance* Math.cos(Math.toRadians(calcAngle)))) +y_pos;

            g.setColor(Color.BLACK);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(thickness));
            int xOutLine = ((int) (Math.sin(Math.toRadians(calcAngle)) * (circleSize/2))) + x_pos+(circleSize/2) ;
            int yOutLine = ((int) (Math.cos(Math.toRadians(calcAngle)) * (circleSize/2))) + y_pos+(circleSize/2) ;
            int xInLine = ((int) (Math.sin(Math.toRadians(calcAngle)) * (circleSize/2))) + calcX+(circleSize/2) ;
            int yInLine = ((int) (Math.cos(Math.toRadians(calcAngle)) * (-circleSize/2))) + calcY+(circleSize/2) ;
            g2.drawLine(xOutLine ,yOutLine,xInLine,yInLine);
            drawNode(g, currNode.getChildren().get(c), calcX, calcY);
        }

    }

    private int calcAngle(int numChildren, int childIndex){
        if (numChildren == 1){
            // if only one child position in middle
            return 0;
        }
        else{
            // calc max angle
            int cmAngle = numChildren*25;
            if(cmAngle> maxAngle){
                cmAngle = maxAngle;
            }

            return (childIndex *cmAngle/(numChildren-1)) - (cmAngle/2);
        }
    }

    public void setRootNode(NodeInt rootNode){
        this.rootNode = rootNode;
    }
    public void setTreeIndex(int treeIndex){
        this.treeIndex = treeIndex;
    }

    private Color chooseColor(int NodeType){
        switch (NodeType){
            case 0:
                return Color.BLUE;
            case 1:
                return Color.GREEN.darker();
            case 2:
                return Color.RED;
            case 3:
                return Color.BLACK;
            default:
        }
        return null;
    }


}
