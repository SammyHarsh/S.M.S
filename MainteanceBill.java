import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainteanceBill extends JDialog
{

    private JLabel FlatNo;
    private JLabel Name;
    private JLabel BillNo;
    private JLabel Date;
    private JLabel MunTax;
    private JLabel SerTax;
    private JLabel SinFund;
    private JLabel ParCharges;
    private JLabel Rebate;
    private JLabel PrinArrears;
    private JLabel IntArrears;
    private JLabel Total;
    private JLabel Arrears;
    private JLabel Balance;
    private JLabel Rupees;
    private JPanel MainteanceBillJ;
    private JTextField tfBillMonth;
    private JButton printButton;
    private JLabel month;
    private JButton getBillButton;
    private JPanel ControlJ;


    public MainteanceBill()
    {




        printButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

tfBillMonth.setVisible(false);
                printButton.setVisible(false);
                getBillButton.setVisible(false);
                PrinterJob job = PrinterJob.getPrinterJob();
                job.setJobName("Print Data");

                job.setPrintable(new Printable(){
                    public int print(Graphics pg,PageFormat pf, int pageNum){
                        pf.setOrientation(PageFormat.LANDSCAPE);
                        if(pageNum > 0){
                            return Printable.NO_SUCH_PAGE;
                        }

                        Graphics2D g2 = (Graphics2D)pg;
                        g2.translate(pf.getImageableX(), pf.getImageableY());
                        g2.scale(0.47,0.47);

                        MainteanceBillJ.print(g2);


                        return Printable.PAGE_EXISTS;


                    }
                });
                boolean ok = job.printDialog();
                if(ok){
                    try{

                        job.print();
                    }
                    catch (PrinterException ex){
                        ex.printStackTrace();
                    }
                }



            }
        });
        getBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    fetch();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }


    private void fetch() throws SQLException {
  //      JOptionPane.showMessageDialog( null,"Hi");
        final String DB_URL = "jdbc:mysql://localhost:3306/sms";
        final String USERNAME = "root";
        final String PASSWORD = "123456";
        try {
            Connection conn1 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String Data = tfBillMonth.getText().toString();
            String sql = "select a.*,b.* FROM sms.maintenance as a, sms.member_details as b where a.member_id = b.mem_id  and a.member_id = '"+Data.toString()+"';";
            ResultSet rs = conn1.createStatement().executeQuery(sql);
      //      JOptionPane.showMessageDialog( this, "Bingo"+sql.toString(), "Data",

            double vMunTax, vSinFund , vParCharges , vRebate , vPrinArrears ,vIntArrears, vTotal ,vTotalF, vArrears , vBalance , vRupees;

            class GFG {
                // A function that prints
                // given number in words
                static String convert_to_words(char[] num)
                {
            String output = "";
                    // Get number of digits
                    // in given number

                    int len = num.length;

                    // Base cases
                    if (len == 0) {
                        System.out.println("empty string");
                        return null;
                    }
                    if (len > 4) {
                        System.out.println(
                                "Length more than 4 is not supported");
                        return null;
                    }

        /* The first string is not used, it is to make
            array indexing simple */
                    String[] single_digits = new String[] {
                            "zero", "one", "two",   "three", "four",
                            "five", "six", "seven", "eight", "nine"
                    };

        /* The first string is not used, it is to make
            array indexing simple */
                    String[] two_digits = new String[] {
                            "",          "ten",      "eleven",  "twelve",
                            "thirteen",  "fourteen", "fifteen", "sixteen",
                            "seventeen", "eighteen", "nineteen"
                    };

                    /* The first two string are not used, they are to
                     * make array indexing simple*/
                    String[] tens_multiple = new String[] {
                            "",      "",      "twenty",  "thirty", "forty",
                            "fifty", "sixty", "seventy", "eighty", "ninety"
                    };

                    String[] tens_power
                            = new String[] { "hundred", "thousand" };

                    /* Used for debugging purpose only */
         //           System.out.print(String.valueOf(num) + ": ");

                    /* For single digit number */
                    if (len == 1) {
                   //System.out.println(single_digits[num[0] - '0']);
                        output = output +  single_digits[num[0] - '0'];
                        return null;
                    }

        /* Iterate while num
            is not '\0' */
                    int x = 0;
                    while (x < num.length) {

                        /* Code path for first 2 digits */
                        if (len >= 3) {
                            if (num[x] - '0' != 0) {
                                System.out.print(single_digits[num[x] - '0'] + " ");
                                output = output + single_digits[num[x] - '0'] + " ";

                                output = output + tens_power[len - 3] + " ";
                                // here len can be 3 or 4
                            }
                            --len;
                        }

                        /* Code path for last 2 digits */
                        else {
                /* Need to explicitly handle
                10-19. Sum of the two digits
                is used as index of "two_digits"
                array of strings */
                            if (num[x] - '0' == 1) {
                                int sum
                                        = num[x] - '0' + num[x + 1] - '0';

                                output = output + two_digits[sum];
                                return null;
                            }

                            /* Need to explicitly handle 20 */
                            else if (num[x] - '0' == 2
                                    && num[x + 1] - '0' == 0) {

                                output = output + "twenty";
                                return null;
                            }

                /* Rest of the two digit
                numbers i.e., 21 to 99 */
                            else {
                                int i = (num[x] - '0');
                                if (i > 0)

                                    output = output + tens_multiple[i] + " ";
                                else
                                //    System.out.print("");
                                output = "";
                                ++x;
                                if (num[x] - '0' != 0)

                                output = output + single_digits[num[x] - '0'];
                            }
                        }
                        ++x;
                    }
             //  JOptionPane.showMessageDialog(null,output);
                    return output;
                }


            }


            while (rs.next()){
                BillNo.setText(rs.getString("Bill_Code"));
                tfBillMonth.setText(rs.getString("Bill_Month")) ;
                FlatNo.setText(rs.getString("flat_no"));
                Name.setText(rs.getString("mem_Fname")+ rs.getString("mem_Mname")+ rs.getString("mem_Lname"));
                month.setText(rs.getString("Bill_Month"));
                Date.setText(rs.getString("Bill_Date"));
                SinFund.setText(String.valueOf(92));
                MunTax.setText(String.valueOf(rs.getInt("Municipal_Tax")));
                SerTax.setText(String.valueOf(rs.getInt("Service_Tax")));
                ParCharges.setText(String.valueOf(rs.getInt("parking_charges")));
            //    Rebate.setText(rs.getString("Rebate"));
               // PrinArrears
               // IntArrears
                //Total
vTotal = Double.valueOf(rs.getDouble("Municipal_Tax")).doubleValue() + Double.valueOf(rs.getDouble("Service_Tax")).doubleValue() + Double.valueOf(rs.getDouble("parking_charges")).doubleValue() + 92.0;
vRebate = 0;

String compare = rs.getString("Bill_cycle").toString();
String compare2 = "Yearly";
System.out.println(compare);
System.out.println(compare2);
                JOptionPane.showMessageDialog(null,"hi"+compare + "hi");
if (compare.equals(compare2))
                {

                    vRebate = (vTotal * 5 )/100;

                }

else
                {
                    JOptionPane.showMessageDialog(null ,rs.getString("Bill_cycle").toString());
                    vRebate = 0;

                }

vTotalF = Double.valueOf(rs.getDouble("Municipal_Tax")).doubleValue() + Double.valueOf(rs.getDouble("Service_Tax")).doubleValue() + Double.valueOf(rs.getDouble("parking_charges")).doubleValue() + 92.0 - vRebate ;
vPrinArrears = (vTotal*12);
Total.setText(String.valueOf(Integer.valueOf((int) vTotalF).intValue()));
Rebate.setText(String.valueOf(vRebate).toString());
PrinArrears.setText(String.valueOf(Integer.valueOf((int) vTotal).intValue()*12));
IntArrears.setText("0");
vArrears = Integer.valueOf((int) vTotal).intValue()*12;
Arrears.setText(String.valueOf(Integer.valueOf((int) vTotal).intValue()*12));
Balance.setText(String.valueOf(Integer.valueOf((int) vArrears).intValue() - Integer.valueOf((int) vTotalF).intValue()));
//JOptionPane.showMessageDialog(this,Total.getText());
//JOptionPane.showMessageDialog(this,(GFG.convert_to_words(Total.getText().toCharArray())));
Rupees.setText(GFG.convert_to_words(Total.getText().toCharArray()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[]args)
    {
        JFrame frame = new JFrame("Mainteance Bill");

        frame.pack();
       // frame.setSize(2508,2480);
        frame.setSize(1000,6480);
        frame.setLocation(200,0);
        frame.setContentPane(new MainteanceBill().MainteanceBillJ);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);






    }

}


