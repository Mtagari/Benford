/*
 * Name: Muhammad Tagari
 * Date: May 12, 2021
 * Teacher: Mr.Ho
 * Description: Analyzes the entire sales data provided to determine if it complies with Benford's Law
 */
// Importing All The Packages Needed
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException; 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

class SalesAnalysisSystem2 extends JPanel {
    // Variables For The Graph
    private double[] values;
    private String[] names;
    private String title;

    public static void main(String[] args){
        try{
            // Initializing Scanner
            Scanner reader = new Scanner(System.in);

            // Asks User To Put File Name (Ex: sales.csv)
            System.out.println("Enter File Name");
            String filePath = reader.nextLine();

            Scanner scan = new Scanner (new File(filePath));

            // Variables (Amount Of Numbers, Line Scanner Is On, First Digit, Array)
            int lineCounters = 0;
            String currentLine; 
            char index;
            double[] digitCounters = new double[9];

            // While Loop - While The File Is Being Read
            while (scan.hasNextLine()){
                currentLine = scan.nextLine();
                // Find The First Digit Add It To Array
                index = (firstDigit(currentLine));
                digitCounters = putArray(index, digitCounters);

                lineCounters = lineCounters + 1;
            }

             // Subtracting The Initial Line From The Beginning
             lineCounters = lineCounters - 1;
             // Percentage Of Each Digit
             double[] frequency = new double[9];
             // This Calculates The Percentage Showing
             calculateFrequency(frequency, digitCounters, lineCounters);
             // Outputting
             printResults(frequency);
            
             // Settings For New Screen + Size
             JFrame f = new JFrame();
             f.setSize(500, 400);

             // Arrays For Bar Graph
             double[] values = new double[9];
             String[] names = new String[9];

             // Populates Array
             for(int i=0; i < values.length; i++) {
                 values[i] = frequency[i];
             }
             names[0] = "1";
             names[1] = "2";
             names[2] = "3";
             names[3] = "4";
             names[4] = "5";
             names[5] = "6";
             names[6] = "7";
             names[7] = "8";
             names[8] = "9";
         
             // Creates The Graph
             f.getContentPane().add(new SalesAnalysisSystem2(values, names, "First Digit Distribution"));
         
             // Opens The Window That Is Being Worked ON
             WindowListener wndCloser = new WindowAdapter() {
                 public void windowClosing(WindowEvent e) {
                     System.exit(0);
                 }
             };
              
             f.addWindowListener(wndCloser);
             f.setVisible(true);
             
             // Results Then Get Output In A csv file
             resultsFile(frequency);
            }
         catch(IOException e) {
             e.printStackTrace();
         }
        }
     /*
      *@desc: Finds the first digit on the sales
      *@param: String currentLine
      *@return: returns the char(index)
     */
     public static char firstDigit(String currentLine) {
         // Char Index At Element 4
         char index = currentLine.charAt(4);
         return index;
     }
     /*
      *@desc: Populates Array Depending On The First Digit
      *@param: char index, double[] digitCounters
      *@return: returns array
     */
     public static double[] putArray(char index, double[] digitCounters) {
         // Decides What The Index Is And Acts Accordingly
         switch(index) {
             
             case '1':
             ++digitCounters[0];
             break;
         
             case '2':
             ++digitCounters[1];
             break;
         
             case '3':
             ++digitCounters[2];
             break;
         
             case '4':
             ++digitCounters[3];
             break;
         
             case '5':
             ++digitCounters[4];
             break;
         
             case '6':
             ++digitCounters[5];
             break;
         
             case '7':
             ++digitCounters[6];
             break;
         
             case '8':
             ++digitCounters[7];
             break;
         
             case '9':
             ++digitCounters[8];
             break;
         } 
         
         return digitCounters;
     }
     /*
      *@desc: Calculates Frequency Of Each First Digit
      *@param: double[] frequency, double[] digitCounters, int lineCoutners
      *@return: Returns Array With Frequencies
     */
     public static double[] calculateFrequency(double[] frequency, double[] digitCounters,int lineCounters) {
         for(int i = 0; i < digitCounters.length; i++) {
             // Calculates Frequency For Each First Digit
             frequency[i] = digitCounters[i]/lineCounters * 100;
             // Rounds Frequency
             frequency[i] = Math.round(frequency[i] * 10.0) / 10.0;
         }
 
         return frequency;
     }
     /*
      *@desc: Outputs csv file with Results
      *@param: double[] frequency
     */
     public static void resultsFile(double[] frequency) {
         // Initializing Scanner
         Scanner reader = new Scanner(System.in);
         // Asks Where They Want The New Result File Stored (File Name)
         System.out.println("Where Do You Want To Store The Results File:");
         String fileLocation = reader.nextLine();
         
         try{
             BufferedWriter bw = new BufferedWriter(new FileWriter(fileLocation));
             // Writes Results in csv file
             bw.write("First digit, frequency \n");
             for(int i = 0; i < frequency.length; i++) {
                 bw.write( i + 1 + "," + frequency[i] + "% \n");
             }
             bw.close();
         }
         // Catches Errors Just In Case
         catch(Exception e) {
             e.printStackTrace();
         }
     }
     /*
      *@desc: Displays Results 
      *@param: double[] frequency
     */
     public static void printResults(double[] frequency) {
         // Visual Representation
         System.out.println("\nFirst digit,frequency");
         
         for(int i = 0; i < frequency.length; i++) {
             System.out.println(i + 1 + " = " + frequency[i] + "%");
         }
          // Determines If Sales Fraud Is Likely Or Not
         if(frequency[0] > 29 && frequency[0] < 32) {
             System.out.println("\nAccording to benford's law, there is no sign of sales fraud occuring.\n");
         }
         else{
             System.out.println("\nThe data dose not pass the benford's law and therefore it is likely that fraud occured.\n");
         }
     }
     /*
      *@desc: Setup For The Graph (Initializing)
      *@param: double[] v, String[] n, String t
     */
     public SalesAnalysisSystem2(double[] v, String[] n, String t) {
         names = n;
         values = v;
         title = t;
     }
     /*
      *@desc: Bar Graph Components
      *@param: Graphics g
     */
     public void paintComponent(Graphics g) {
         super.paintComponent(g);
         // Dimensions
         if (values == null || values.length == 0)
         return;
         double minValue = 0;
         double maxValue = 0;
         for (int i = 0; i < values.length; i++) {
             if (minValue > values[i])
             minValue = values[i];
             if (maxValue < values[i])
             maxValue = values[i];
         }
 
        
         Dimension d = getSize();
         int clientWidth = d.width;
         int clientHeight = d.height;
         int barWidth = clientWidth / values.length;
 
         // Font + Titles
         Font titleFont = new Font("SansSerif", Font.BOLD, 25);
         FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
         Font labelFont = new Font("SansSerif", Font.PLAIN, 15);
         FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);
 
        
         int titleWidth = titleFontMetrics.stringWidth(title);
         int y = titleFontMetrics.getAscent();
         int x = (clientWidth - titleWidth) / 2;
         g.setFont(titleFont);
         g.drawString(title, x, y);
 
         int top = titleFontMetrics.getHeight();
         int bottom = labelFontMetrics.getHeight();
         if (maxValue == minValue)
         return;
         double scale = (clientHeight - top - bottom) / (maxValue - minValue);
         y = clientHeight - labelFontMetrics.getDescent();
         g.setFont(labelFont);
         
         // Bars
         for (int i = 0; i < values.length; i++) {
             int valueX = i * barWidth + 1;
             int valueY = top;
             int height = (int) (values[i] * scale);
             if (values[i] >= 0)
               valueY += (int) ((maxValue - values[i]) * scale);
             else {
               valueY += (int) (maxValue * scale);
               height = -height;
             }
             // Colour And Drawing Bars
             g.setColor(Color.green);
             g.fillRect(valueX, valueY, barWidth - 2, height);
             g.setColor(Color.black);
             g.drawRect(valueX, valueY, barWidth - 2, height);
             int labelWidth = labelFontMetrics.stringWidth(names[i]);
             x = i * barWidth + (barWidth - labelWidth) / 2;
             g.drawString(names[i], x, y);
            }
        }
    }
