import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Curr_converter implements ActionListener{
    JFrame frame;
    JPanel pan;
    JLabel l1,l2,l3,aro,result;
    ImageIcon imgage;
    JComboBox com1,com2;
    JTextField t1,t2;
    JButton convert,reset,close;
    String[] list1={"","INR","USD","EUR","GBP","AUD","RUB"};
    Font newfont=new Font("Serif", Font.BOLD, 30);
    String currency1,currency2;
    Double Amount;

    
    Curr_converter(){
        Border border=BorderFactory.createEmptyBorder();
        Border border1=BorderFactory.createEmptyBorder(10, 020, 010, 020);
        frame=new JFrame("currencyconverter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(000033));      // frame BACKGROUND COLOR 
        frame.setLocation(350, 20);
        frame.setLayout(null);

        l1=new JLabel("Currency Converter");
        l1.setBounds(50, 10,500, 50);
        l1.setHorizontalAlignment(JLabel.CENTER);
        l1.setForeground(new Color(50, 252, 219));
        l1.setFont(newfont);
        l2=new JLabel("Amt");
        l2.setForeground(new Color( 2, 172, 219));
        l2.setBounds(120, 120,100, 50);
        l2.setHorizontalAlignment(JLabel.CENTER);
        l2.setFont(new Font("Serif", Font.BOLD, 25));

        t1=new JTextField("0.0");
        t1.setBorder(border);
        t1.setBounds(200, 120, 200, 50);
        t1.setFont(new Font("MV Boli", Font.BOLD, 25));
        t1.setBackground(new Color(26,26,26));
        t1.setForeground(new Color(90, 215, 250));
        t1.setHorizontalAlignment(JTextField.CENTER);


        com1=new JComboBox(list1);
        aro=new JLabel();
        imgage=new ImageIcon("arrow.png");
        aro.setIcon(imgage);
        com2=new JComboBox(list1);
        com1.setBounds(50, 200, 190, 40);
        aro.setBounds(280, 200, 100, 40);
        com2.setBounds(360, 200, 190, 40);
        com1.setBackground(Color.BLACK);
        com1.setForeground(new Color(90, 215, 250));
        com1.setFont(new Font("Serif", Font.BOLD, 25));
        com2.setBackground(Color.BLACK);
        com2.setForeground(new Color(90, 215, 250));
        com2.setFont(new Font("Serif", Font.BOLD, 25));

        convert=new JButton("Convert");
        convert.setFont(newfont);
        convert.setFocusable(false);
        convert.setBounds(200, 270, 200, 50);
        convert.setBorder(border1);
        convert.setBackground(new Color( 2, 172, 219));
        convert.setForeground(Color.black);
        convert.setBorder(BorderFactory.createEtchedBorder());

        result=new JLabel("RESULT");
        result.setBounds(50, 350,500, 50);
        result.setHorizontalAlignment(JLabel.CENTER);
        result.setFont(newfont);
        result.setForeground(Color.DARK_GRAY);

        reset=new JButton("RESET");
        close=new JButton("CLOSE");
        reset.setFont(newfont);
        close.setFont(newfont);
        reset.setFocusable(false);
        close.setFocusable(false);
        reset.setBounds(50,450,200,60);
        close.setBounds(350,450,200,60);
        reset.setBorder(border1);
        close.setBorder(border1);
        reset.setBackground(new Color( 2, 172, 219));
        reset.setForeground(Color.black);
        close.setBackground(Color.RED);
        close.setForeground(Color.BLACK);

        frame.add(l1);
        frame.add(l2);
        frame.add(t1);
        frame.add(com1);
        frame.add(aro);
        frame.add(com2);
        frame.add(convert);
        frame.add(result);
        frame.add(reset);
        frame.add(close);
        frame.setVisible(true);

        convert.addActionListener(this); 
        reset.addActionListener(this);
        close.addActionListener(this);


        
    
    }
    @Override
    public void actionPerformed (ActionEvent e){
        Amount=Double.parseDouble(t1.getText());
        currency1=String.valueOf(com1.getSelectedItem());
        currency2=String.valueOf(com2.getSelectedItem());
        String webpage="https://www.xe.com/currencyconverter/convert/?Amount=1&From="+currency1+"&To="+currency2;
        if (e.getSource()==convert){
            if ( com1.getSelectedItem()=="" || com2.getSelectedItem()==""){
            JOptionPane.showMessageDialog(frame,"SELECT CURRENCIES OR \nEnter Amount!!");
            
        }
            else if(currency1==currency2){
                result.setForeground(Color.WHITE);
                result.setText(Amount+" "+currency1+"="+Amount+" "+currency1);
                
            }
            else{
                String currencyString=OutputString(webpage);
                if (currencyString==null){
                    System.out.println("need internet connection!!!");
                }
                else{
                Double Currency=Double.parseDouble(CurrencyValue(currencyString));
                String currency=String.format("%.2f",(Currency)*(Amount));
                result.setForeground(Color.WHITE);
                result.setText(Amount+" "+currency1+"="+currency+" "+currency2);
                }
            }
            
        }

        if(e.getSource()==close){
            System.exit(0);
        }

        if (e.getSource()==reset){
            com1.setSelectedIndex(0);
            com2.setSelectedIndex(0);
            t1.setText("0.0");
            result.setText("RESULT");
            result.setForeground(Color.DARK_GRAY);//new Color(6, 1, 84)
            
        }


    }
    //to calculate currencyString(having CurrencyAmount) which will obtained from webpage
    public String OutputString(String webString){
        try {
            // Create a URL object
            URL website = new URL(webString);

            // Establish a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
            connection.setRequestMethod("GET");

            // Read the response from the URL
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            
            String startTag = "<p class=\"result__BigRate-sc-1bsijpp-1 iGrAod\">"; 
            String endTag = "</p>";
            int startIndex = response.indexOf(startTag) + startTag.length();
            int endIndex = response.indexOf(endTag, startIndex);
            String currencyString = response.substring(startIndex, endIndex);

            // Close the connection and reader
            reader.close();
            connection.disconnect();
            return currencyString;
        } catch (IOException e) {

            JOptionPane.showMessageDialog(frame, "REQUIRE INTERNET CONNECTON!!!");
            return null;
        }
        
        
    }

    // to calculate substring as a currency value from currency String
    public String CurrencyValue(String currrencyString){
        String subString="";
        String pattern = "\\b\\d+(\\.\\d+)?\\b";
        Pattern decimalPattern = Pattern.compile(pattern);
        Matcher matcher = decimalPattern.matcher(currrencyString);

        // Find and extract decimal numbers
        while (matcher.find()) {
            String decimalString = matcher.group();
            subString=subString+decimalString;
        }

        return subString;

        
    }


    public static void main(String[] args){
        new Curr_converter(); 
    
    
    }
}