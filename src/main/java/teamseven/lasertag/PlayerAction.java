/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
*/
 package teamseven.lasertag;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Graphics;
import java.awt.Point;


/**
 *
 * @author daijonmroberts
 */
public class PlayerAction extends javax.swing.JFrame {

    /**
     * Creates new form PlayerAction
     */
    //member varibles
    //need an instance of the DB to get all the code names
    public Database db;

    //total team scores
    public int redTeamTotalScore;
    public int greenTeamTotalScore;
    public int i = 0;

    //red team arrays of ID's and codeNames
    public ArrayList<Integer> redTeamIDList = new ArrayList<>();
    public ArrayList<String> redTeamNameList = new ArrayList<>();
    public ArrayList<Integer> redTeamPlayerScores = new ArrayList<>();
    public int rx = 8;
    public int ry = 12; //240 is the max we want to draw our y
    //now green team
    public ArrayList<Integer> greenTeamIDList = new ArrayList<>();
    public ArrayList<String> greenTeamNameList = new ArrayList<>();
    public ArrayList<Integer> greenTeamPlayerScores = new ArrayList<>();
    public int gx = 8;
    public int gy = 12; //240 is the max we want to draw our y
    UDPServer mainServer;

    public PlayerAction(UDPServer server) 
    {
        initComponents();
        mainServer = server;
        db = new Database();
        db.openDatabase();
        db.readRecords();
        redTeamTotalScore = 0;
        greenTeamTotalScore = 0;
        this.RedTotalScoreTextPane.setText(String.valueOf(redTeamTotalScore));
        this.GreenTotalScoreTextPane.setText(String.valueOf(greenTeamTotalScore));
    }

    public void setArrays(ArrayList<Integer> red, ArrayList<Integer> green)
    {
        String tempCodeName = "";

        for(int i = 0; i < red.size(); i++)
        {
            redTeamIDList.add(red.get(i));
            tempCodeName = db.inTable(red.get(i));
            redTeamNameList.add(tempCodeName);
            redTeamPlayerScores.add(0);
        }
        tempCodeName = "";
        for(int i = 0; i < green.size(); i++)
        {
            greenTeamIDList.add(green.get(i));
            tempCodeName = db.inTable(green.get(i));
            greenTeamNameList.add(tempCodeName);
            greenTeamPlayerScores.add(0);
        }
        System.out.println("Red Team IDS: " + redTeamIDList);
        System.out.println("Green Team IDS: " + greenTeamIDList);
        System.out.println("Red Team codenames: " + redTeamNameList);
        System.out.println("Green Team codenames: " + greenTeamNameList);
        System.out.println("Red Team player scores: " + redTeamPlayerScores);
        System.out.println("Green Team player scores: " + greenTeamPlayerScores);

        if(green.size() > 0)
        {
            if(green.size() > 2)
            {
                this.GreenPlayerHighScoreNameTextPane1.setText(greenTeamNameList.get(0));
                this.GreenPlayerHighScoreNameTextPane2.setText(greenTeamNameList.get(1));
                this.GreenPlayerHighScoreNameTextPane3.setText(greenTeamNameList.get(2));
                this.GreenHighScoreTextPane1.setText(String.valueOf(greenTeamPlayerScores.get(0)));
                this.GreenHighScoreTextPane2.setText(String.valueOf(greenTeamPlayerScores.get(1)));
                this.GreenHighScoreTextPane3.setText(String.valueOf(greenTeamPlayerScores.get(2)));
            }
            else if(green.size() == 2)
            {
                this.GreenPlayerHighScoreNameTextPane1.setText(greenTeamNameList.get(0));
                this.GreenPlayerHighScoreNameTextPane2.setText(greenTeamNameList.get(1));
                this.GreenHighScoreTextPane1.setText(String.valueOf(greenTeamPlayerScores.get(0)));
                this.GreenHighScoreTextPane2.setText(String.valueOf(greenTeamPlayerScores.get(1)));
            }
            else if(green.size() == 1)
            {
                this.GreenPlayerHighScoreNameTextPane1.setText(greenTeamNameList.get(0));
                this.GreenHighScoreTextPane1.setText(String.valueOf(greenTeamPlayerScores.get(0)));
            }
        }

        if(red.size() > 0)
        {
            if(red.size() > 2)
            {
                this.RedPlayerHighScoreNameTextPane1.setText(redTeamNameList.get(0));
                this.RedPlayerHighScoreNameTextPane2.setText(redTeamNameList.get(1));
                this.RedPlayerHighScoreNameTextPane3.setText(redTeamNameList.get(2));
                this.RedHighScoreTextPane1.setText(String.valueOf(redTeamPlayerScores.get(0)));
                this.RedHighScoreTextPane2.setText(String.valueOf(redTeamPlayerScores.get(1)));
                this.RedHighScoreTextPane3.setText(String.valueOf(redTeamPlayerScores.get(2)));
            }
            else if(red.size() == 2)
            {
                this.RedPlayerHighScoreNameTextPane1.setText(redTeamNameList.get(0));
                this.RedPlayerHighScoreNameTextPane2.setText(redTeamNameList.get(1));
                this.RedHighScoreTextPane1.setText(String.valueOf(redTeamPlayerScores.get(0)));
                this.RedHighScoreTextPane2.setText(String.valueOf(redTeamPlayerScores.get(1)));
            }
            else if(red.size() == 1)
            {
                this.RedPlayerHighScoreNameTextPane1.setText(redTeamNameList.get(0));
                this.RedHighScoreTextPane1.setText(String.valueOf(redTeamPlayerScores.get(0)));
            }
        }
    }

    public boolean updateScreen() //we'll use this method to work with the java generator
    {
        boolean cntrlValue = true; 
        
        if(i < mainServer.playerShooter.size())
        {
            updateJpanel(mainServer.playerShooter.get(i), mainServer.playerHit.get(i));
            updateScores(mainServer.playerShooter.get(i), mainServer.playerHit.get(i));
            i++;

            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
            }
        }

        return cntrlValue;
    }
    //now need to create a method that updates the top players scores.
    public void updateScores(int idShooter, int idGotHit)
    {
        //need to check if we are going to write to the red or green panel
        int arrayIndex = 1000;

        boolean isItRed = false;
        boolean isItGreen = false;
        for(int i = 0; i < redTeamIDList.size(); i++)
        {
            if(idShooter == redTeamIDList.get(i))
            {
                isItRed = true;
                arrayIndex = i;
            }
        }

        for(int i = 0; i < greenTeamIDList.size(); i++)
        {
            if(idShooter == greenTeamIDList.get(i))
            {
                isItGreen = true;
                arrayIndex = i;
            }
        }

        if(arrayIndex != 1000)
        {
            if(isItRed)
            {
                int tmp = redTeamPlayerScores.get(arrayIndex);
                tmp += 10;
                redTeamPlayerScores.set(arrayIndex, tmp);
                redTeamTotalScore += 10;
            }
            else if(isItGreen)
            {
                int tmp = greenTeamPlayerScores.get(arrayIndex);
                tmp += 10;
                greenTeamPlayerScores.set(arrayIndex, tmp);
                greenTeamTotalScore += 10;
            }
            else
            {
                System.out.println("That is not an active user of this game.");
            }
        }
        //now need to update all the text box's with the leading scores....
        //so going to need to find the highest score of each team
        //only going to run traffic generator with two user IDS so possibly can use that to advantage
        //first need to set the team scores though.
        this.RedTotalScoreTextPane.setText(String.valueOf(redTeamTotalScore));
        this.GreenTotalScoreTextPane.setText(String.valueOf(greenTeamTotalScore));

        int indexMaxRed = 0;
        int valueMaxRed = 0;
        for(int i = 0; i < redTeamPlayerScores.size(); i++)
        {
            int tmp = redTeamPlayerScores.get(i);
            if(tmp > valueMaxRed)
            {
                valueMaxRed = tmp;
                indexMaxRed = i;
            }
        }
        //now do the same for green
        int indexMaxGreen = 0;
        int valueMaxGreen = 0;
        for(int i = 0; i < greenTeamPlayerScores.size(); i++)
        {
            int tmp = greenTeamPlayerScores.get(i);
            if(tmp > valueMaxGreen)
            {
                valueMaxGreen = tmp;
                indexMaxGreen = i;
            }
        }

        if(indexMaxRed == 0)
        {
            this.RedPlayerHighScoreNameTextPane1.setText(redTeamNameList.get(0));
            this.RedPlayerHighScoreNameTextPane2.setText(redTeamNameList.get(1));
            this.RedHighScoreTextPane1.setText(String.valueOf(redTeamPlayerScores.get(0)));
            this.RedHighScoreTextPane2.setText(String.valueOf(redTeamPlayerScores.get(1)));
        }
        else if(indexMaxRed == 1)
        {
            this.RedPlayerHighScoreNameTextPane1.setText(redTeamNameList.get(1));
            this.RedPlayerHighScoreNameTextPane2.setText(redTeamNameList.get(0));
            this.RedHighScoreTextPane1.setText(String.valueOf(redTeamPlayerScores.get(1)));
            this.RedHighScoreTextPane2.setText(String.valueOf(redTeamPlayerScores.get(0)));
        }
        else
        {
            System.out.println("There shouldn't be more than 2 active users.");
        }

        if(indexMaxGreen == 0)
        {
            this.GreenPlayerHighScoreNameTextPane1.setText(greenTeamNameList.get(0));
            this.GreenPlayerHighScoreNameTextPane2.setText(greenTeamNameList.get(1));
            this.GreenHighScoreTextPane1.setText(String.valueOf(greenTeamPlayerScores.get(0)));
            this.GreenHighScoreTextPane2.setText(String.valueOf(greenTeamPlayerScores.get(1)));
        }
        else if(indexMaxGreen == 1)
        {
            this.GreenPlayerHighScoreNameTextPane1.setText(greenTeamNameList.get(1));
            this.GreenPlayerHighScoreNameTextPane2.setText(greenTeamNameList.get(0));
            this.GreenHighScoreTextPane1.setText(String.valueOf(greenTeamPlayerScores.get(1)));
            this.GreenHighScoreTextPane2.setText(String.valueOf(greenTeamPlayerScores.get(0)));
        }
        else
        {
            System.out.println("There shouldn't be more than 2 active users.");
        }

    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    //creating a function to update the message jpanel box
    public void updateJpanel(int idShooter, int idGotHit)
    {
                //int x = 8;
                //int y = 240; //240 is the max we want to draw our y.
                //update(p.getGraphics());
                //Graphics g = p.getGraphics();
                //super.paintComponent(g);
                //p.super.paintComponent(g);
                //p.paintComponents(g);
                
                String nameShooter = db.inTable(idShooter);
                String nameGotHit = db.inTable(idGotHit);
                String fullText = nameShooter + " hit " + nameGotHit;
                System.out.println(fullText);
                //need to check if we are going to write to the red or green panel
                boolean isItRed = false;
                boolean isItGreen = false;
                for(int i = 0; i < redTeamNameList.size(); i++)
                {
                    if(nameShooter == redTeamNameList.get(i))
                    {
                        isItRed = true;
                    }
                }
                for(int i = 0; i < greenTeamNameList.size(); i++)
                {
                    if(nameShooter == greenTeamNameList.get(i))
                    {
                        isItGreen = true;
                    }
                }
                //we now know if the shooter was on red or green
                if(isItRed == true)
                {
                    if(ry < 240)
                    {
                        this.RedTeamScorePanel.getGraphics().drawString(fullText, rx, ry);
                        ry += 12;
                    }
                    else
                    {
                        //need a way to clear the text off the jpanel then start writing again 
                        //this.RedTeamScorePanel.getGraphics().clearRect(8, 12, 30, 240); //need to learn how to clear
                        this.RedTeamScorePanel.repaint();
                        ry = 12;                                                          //text from jpanel
                        this.RedTeamScorePanel.getGraphics().drawString(fullText, rx, ry);
                    }
                }
                else if(isItGreen)
                {
                    if(gy < 240)
                    {
                        this.GreenTeamScorePanel.getGraphics().drawString(fullText, gx, gy);
                        gy += 12;
                    }
                    else
                    {
                        //this.GreenTeamScorePanel.getGraphics().clearRect(8, 12, 30, 240);
                        this.GreenTeamScorePanel.repaint();
                        gy = 12;
                        this.GreenTeamScorePanel.getGraphics().drawString(fullText, gx, gy);
                    }
                }
                else
                {
                    System.out.println("User didnt exist as an active player");
                }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() 
    {

        BackgroundPanel = new javax.swing.JPanel();
        AlphaRedLabel = new javax.swing.JLabel();
        AlphaGreenLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        RedHighScoreTextPane1 = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        RedPlayerHighScoreNameTextPane1 = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        RedHighScoreTextPane2 = new javax.swing.JTextPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        RedPlayerHighScoreNameTextPane2 = new javax.swing.JTextPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        RedHighScoreTextPane3 = new javax.swing.JTextPane();
        jScrollPane8 = new javax.swing.JScrollPane();
        RedPlayerHighScoreNameTextPane3 = new javax.swing.JTextPane();
        jScrollPane9 = new javax.swing.JScrollPane();
        RedTotalScoreTextPane = new javax.swing.JTextPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        GreenPlayerHighScoreNameTextPane1 = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        GreenHighScoreTextPane1 = new javax.swing.JTextPane();
        jScrollPane10 = new javax.swing.JScrollPane();
        GreenPlayerHighScoreNameTextPane2 = new javax.swing.JTextPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        GreenHighScoreTextPane2 = new javax.swing.JTextPane();
        jScrollPane12 = new javax.swing.JScrollPane();
        GreenPlayerHighScoreNameTextPane3 = new javax.swing.JTextPane();
        jScrollPane13 = new javax.swing.JScrollPane();
        GreenHighScoreTextPane3 = new javax.swing.JTextPane();
        jScrollPane14 = new javax.swing.JScrollPane();
        GreenTotalScoreTextPane = new javax.swing.JTextPane();
        RedTeamScorePanel = new javax.swing.JPanel()
        {
            // int x = 8;
            // int y = 12;
            //public boolean isRed = true;
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                //g.drawString(allRedNames, 8, 12);
                // for(int i = 0; i < redTeamNameList.size(); i++) //going to need to use some of this logic for update screen.
                // {
                //     g.drawString(redTeamNameList.get(i), x, y + (i * 12));
                // }
            }
            
        };
        GreenTeamScorePanel = new javax.swing.JPanel()
        {
            // int x = 8;
            // int y = 12;
            //public boolean isRed = false;
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                //g.drawString(allRedNames, 8, 12);
                // for(int i = 0; i < greenTeamNameList.size(); i++)
                // {
                //     g.drawString(greenTeamNameList.get(i), x, y + (i * 12));
                // }
            }
        };

        WhiteFooterPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setResizable(false);

        BackgroundPanel.setBackground(new java.awt.Color(0, 0, 0));
        BackgroundPanel.setToolTipText("");

        AlphaRedLabel.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        AlphaRedLabel.setForeground(new java.awt.Color(255, 0, 51));
        AlphaRedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AlphaRedLabel.setText("ALPHA RED");

        AlphaGreenLabel.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        AlphaGreenLabel.setForeground(new java.awt.Color(0, 255, 0));
        AlphaGreenLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AlphaGreenLabel.setText("ALPHA GRN");

        jScrollPane3.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane3.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 0, 51), null));
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        RedHighScoreTextPane1.setEditable(false);
        RedHighScoreTextPane1.setBackground(new java.awt.Color(0, 0, 0));
        RedHighScoreTextPane1.setBorder(null);
        RedHighScoreTextPane1.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        RedHighScoreTextPane1.setForeground(new java.awt.Color(255, 0, 0));
        jScrollPane3.setViewportView(RedHighScoreTextPane1);

        jScrollPane4.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane4.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 0, 0), null));

        RedPlayerHighScoreNameTextPane1.setEditable(false);
        RedPlayerHighScoreNameTextPane1.setBackground(new java.awt.Color(0, 0, 0));
        RedPlayerHighScoreNameTextPane1.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        RedPlayerHighScoreNameTextPane1.setForeground(new java.awt.Color(255, 0, 0));
        jScrollPane4.setViewportView(RedPlayerHighScoreNameTextPane1);

        jScrollPane5.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane5.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 0, 0), null));

        RedHighScoreTextPane2.setEditable(false);
        RedHighScoreTextPane2.setBackground(new java.awt.Color(0, 0, 0));
        RedHighScoreTextPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        RedHighScoreTextPane2.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        RedHighScoreTextPane2.setForeground(new java.awt.Color(255, 0, 0));
        jScrollPane5.setViewportView(RedHighScoreTextPane2);

        jScrollPane6.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane6.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 0, 0), null));

        RedPlayerHighScoreNameTextPane2.setEditable(false);
        RedPlayerHighScoreNameTextPane2.setBackground(new java.awt.Color(0, 0, 0));
        RedPlayerHighScoreNameTextPane2.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        RedPlayerHighScoreNameTextPane2.setForeground(new java.awt.Color(255, 0, 0));
        jScrollPane6.setViewportView(RedPlayerHighScoreNameTextPane2);

        jScrollPane7.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane7.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 0, 0), null));

        RedHighScoreTextPane3.setEditable(false);
        RedHighScoreTextPane3.setBackground(new java.awt.Color(0, 0, 0));
        RedHighScoreTextPane3.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        RedHighScoreTextPane3.setForeground(new java.awt.Color(255, 0, 0));
        jScrollPane7.setViewportView(RedHighScoreTextPane3);

        jScrollPane8.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane8.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 0, 0), null));

        RedPlayerHighScoreNameTextPane3.setEditable(false);
        RedPlayerHighScoreNameTextPane3.setBackground(new java.awt.Color(0, 0, 0));
        RedPlayerHighScoreNameTextPane3.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        RedPlayerHighScoreNameTextPane3.setForeground(new java.awt.Color(255, 0, 0));
        jScrollPane8.setViewportView(RedPlayerHighScoreNameTextPane3);

        jScrollPane9.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane9.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 0, 0), null));

        RedTotalScoreTextPane.setEditable(false);
        RedTotalScoreTextPane.setBackground(new java.awt.Color(0, 0, 0));
        RedTotalScoreTextPane.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        RedTotalScoreTextPane.setForeground(new java.awt.Color(255, 0, 0));
        jScrollPane9.setViewportView(RedTotalScoreTextPane);

        jScrollPane1.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 255, 0), null));

        GreenPlayerHighScoreNameTextPane1.setEditable(false);
        GreenPlayerHighScoreNameTextPane1.setBackground(new java.awt.Color(0, 0, 0));
        GreenPlayerHighScoreNameTextPane1.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        GreenPlayerHighScoreNameTextPane1.setForeground(new java.awt.Color(0, 255, 0));
        jScrollPane1.setViewportView(GreenPlayerHighScoreNameTextPane1);

        jScrollPane2.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 255, 0), null));

        GreenHighScoreTextPane1.setEditable(false);
        GreenHighScoreTextPane1.setBackground(new java.awt.Color(0, 0, 0));
        GreenHighScoreTextPane1.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        GreenHighScoreTextPane1.setForeground(new java.awt.Color(0, 255, 0));
        jScrollPane2.setViewportView(GreenHighScoreTextPane1);

        jScrollPane10.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane10.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 255, 0), null));

        GreenPlayerHighScoreNameTextPane2.setEditable(false);
        GreenPlayerHighScoreNameTextPane2.setBackground(new java.awt.Color(0, 0, 0));
        GreenPlayerHighScoreNameTextPane2.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        GreenPlayerHighScoreNameTextPane2.setForeground(new java.awt.Color(0, 255, 0));
        jScrollPane10.setViewportView(GreenPlayerHighScoreNameTextPane2);

        jScrollPane11.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane11.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 255, 0), null));

        GreenHighScoreTextPane2.setEditable(false);
        GreenHighScoreTextPane2.setBackground(new java.awt.Color(0, 0, 0));
        GreenHighScoreTextPane2.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        GreenHighScoreTextPane2.setForeground(new java.awt.Color(0, 255, 0));
        jScrollPane11.setViewportView(GreenHighScoreTextPane2);

        jScrollPane12.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane12.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 255, 0), null));

        GreenPlayerHighScoreNameTextPane3.setEditable(false);
        GreenPlayerHighScoreNameTextPane3.setBackground(new java.awt.Color(0, 0, 0));
        GreenPlayerHighScoreNameTextPane3.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        GreenPlayerHighScoreNameTextPane3.setForeground(new java.awt.Color(0, 255, 0));
        jScrollPane12.setViewportView(GreenPlayerHighScoreNameTextPane3);

        jScrollPane13.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane13.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 255, 0), null));

        GreenHighScoreTextPane3.setEditable(false);
        GreenHighScoreTextPane3.setBackground(new java.awt.Color(0, 0, 0));
        GreenHighScoreTextPane3.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        GreenHighScoreTextPane3.setForeground(new java.awt.Color(0, 255, 0));
        jScrollPane13.setViewportView(GreenHighScoreTextPane3);

        jScrollPane14.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane14.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 255, 0), null));

        GreenTotalScoreTextPane.setEditable(false);
        GreenTotalScoreTextPane.setBackground(new java.awt.Color(0, 0, 0));
        GreenTotalScoreTextPane.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        GreenTotalScoreTextPane.setForeground(new java.awt.Color(0, 255, 0));
        jScrollPane14.setViewportView(GreenTotalScoreTextPane);

        RedTeamScorePanel.setBackground(new java.awt.Color(102, 102, 255));
        RedTeamScorePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 0, 0), null));

        javax.swing.GroupLayout RedTeamScorePanelLayout = new javax.swing.GroupLayout(RedTeamScorePanel);
        RedTeamScorePanel.setLayout(RedTeamScorePanelLayout);
        RedTeamScorePanelLayout.setHorizontalGroup(
            RedTeamScorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 329, Short.MAX_VALUE)
        );
        RedTeamScorePanelLayout.setVerticalGroup(
            RedTeamScorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );
        

        GreenTeamScorePanel.setBackground(new java.awt.Color(102, 102, 255));
        GreenTeamScorePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 255, 0), null));

        javax.swing.GroupLayout GreenTeamScorePanelLayout = new javax.swing.GroupLayout(GreenTeamScorePanel);
        GreenTeamScorePanel.setLayout(GreenTeamScorePanelLayout);
        GreenTeamScorePanelLayout.setHorizontalGroup(
            GreenTeamScorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        GreenTeamScorePanelLayout.setVerticalGroup(
            GreenTeamScorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout WhiteFooterPanelLayout = new javax.swing.GroupLayout(WhiteFooterPanel);
        WhiteFooterPanel.setLayout(WhiteFooterPanelLayout);
        WhiteFooterPanelLayout.setHorizontalGroup(
            WhiteFooterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 666, Short.MAX_VALUE)
        );
        WhiteFooterPanelLayout.setVerticalGroup(
            WhiteFooterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout BackgroundPanelLayout = new javax.swing.GroupLayout(BackgroundPanel);
        BackgroundPanel.setLayout(BackgroundPanelLayout);
        BackgroundPanelLayout.setHorizontalGroup(
            BackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(BackgroundPanelLayout.createSequentialGroup()
                        .addGroup(BackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(BackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(AlphaRedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, BackgroundPanelLayout.createSequentialGroup()
                                    .addGap(16, 16, 16)
                                    .addGroup(BackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                        .addComponent(jScrollPane6)
                                        .addComponent(jScrollPane8))
                                    .addGroup(BackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(BackgroundPanelLayout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jScrollPane3))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BackgroundPanelLayout.createSequentialGroup()
                                            .addGap(12, 12, 12)
                                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BackgroundPanelLayout.createSequentialGroup()
                                            .addGap(12, 12, 12)
                                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RedTeamScorePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(BackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AlphaGreenLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(BackgroundPanelLayout.createSequentialGroup()
                                .addGroup(BackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(GreenTeamScorePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(BackgroundPanelLayout.createSequentialGroup()
                                        .addGroup(BackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(BackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jScrollPane14)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                            .addComponent(jScrollPane11)
                                            .addComponent(jScrollPane13))))
                                .addGap(0, 10, Short.MAX_VALUE))))
                    .addGroup(BackgroundPanelLayout.createSequentialGroup()
                        .addComponent(WhiteFooterPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)))
                .addContainerGap())
        );
        BackgroundPanelLayout.setVerticalGroup(
            BackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AlphaRedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AlphaGreenLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BackgroundPanelLayout.createSequentialGroup()
                        .addGroup(BackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(BackgroundPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(BackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(RedTeamScorePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GreenTeamScorePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(WhiteFooterPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BackgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BackgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        BackgroundPanel.getAccessibleContext().setAccessibleName("");
        BackgroundPanel.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) 
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PlayerAction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PlayerAction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PlayerAction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlayerAction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new PlayerAction().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AlphaGreenLabel;
    private javax.swing.JLabel AlphaRedLabel;
    private javax.swing.JPanel BackgroundPanel;
    private javax.swing.JTextPane GreenHighScoreTextPane1;
    private javax.swing.JTextPane GreenHighScoreTextPane2;
    private javax.swing.JTextPane GreenHighScoreTextPane3;
    private javax.swing.JTextPane GreenPlayerHighScoreNameTextPane1;
    private javax.swing.JTextPane GreenPlayerHighScoreNameTextPane2;
    private javax.swing.JTextPane GreenPlayerHighScoreNameTextPane3;
    private javax.swing.JPanel GreenTeamScorePanel;
    private javax.swing.JTextPane GreenTotalScoreTextPane;
    private javax.swing.JTextPane RedHighScoreTextPane1;
    private javax.swing.JTextPane RedHighScoreTextPane2;
    private javax.swing.JTextPane RedHighScoreTextPane3;
    private javax.swing.JTextPane RedPlayerHighScoreNameTextPane1;
    private javax.swing.JTextPane RedPlayerHighScoreNameTextPane2;
    private javax.swing.JTextPane RedPlayerHighScoreNameTextPane3;
    private javax.swing.JPanel RedTeamScorePanel;
    private javax.swing.JTextPane RedTotalScoreTextPane;
    private javax.swing.JPanel WhiteFooterPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    // End of variables declaration//GEN-END:variables
}


