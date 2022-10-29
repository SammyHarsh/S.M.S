import com.toedter.calendar.JDateChooser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MaintenanceForm extends JDialog
{
    private JTextField tfBillCode;
    private JTextField tfMunicipalTax;
    private JTextField tfServiceTax;
  //  private JTextField tfMasterCode;
   // private JTextField tfMasterType;
    private JTextField tfMemberId;
    private JTextField tfparkingcharges;
    private JButton btnSave;
    private JButton btnCancel;
    private JPanel Maintenance;
    private JComboBox cmb_Mas_Code;
    private JComboBox cmb_Mas_Type;
    private JComboBox cmb_Bill_Cycle;
    private JComboBox cmb_Pay_Mode;
    private JComboBox cmb_Bill_Month;
    private JPanel Jcal;
    private JTextField tfGetDate;

    Calendar cld=Calendar.getInstance();
   JDateChooser datechos= new JDateChooser(cld.getTime());



    public MaintenanceForm(JFrame parent){
        super(parent);
        setTitle("Maintenance Form");
        setContentPane(Maintenance);
        setMaximumSize(new Dimension(500, 600));
        setSize(500,600);
        setModal(true);
        datechos.setDateFormatString("yyyy/MM/dd");
        Jcal.add(datechos);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       // save();
        try {
            fetch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // add values to Combobox Payment Mode
        cmb_Pay_Mode.addItem("Select");
        cmb_Pay_Mode.addItem("Cash");
        cmb_Pay_Mode.addItem("Cheque");

        // add values to Combobox Bill Cycle
        cmb_Bill_Cycle.addItem("Select");
        cmb_Bill_Cycle.addItem("Monthly");
        cmb_Bill_Cycle.addItem("Yearly");


        // add value to Combobox Bill Month
        cmb_Bill_Month.addItem("Select");
        cmb_Bill_Month.addItem("January");
        cmb_Bill_Month.addItem("February");
        cmb_Bill_Month.addItem("March");
        cmb_Bill_Month.addItem("April");
        cmb_Bill_Month.addItem("May");
        cmb_Bill_Month.addItem("June");
        cmb_Bill_Month.addItem("July");
        cmb_Bill_Month.addItem("August");
        cmb_Bill_Month.addItem("September");
        cmb_Bill_Month.addItem("October");
        cmb_Bill_Month.addItem("November");
        cmb_Bill_Month.addItem("December");

        setLocationRelativeTo(parent);
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
        cmb_Mas_Code.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        cmb_Pay_Mode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        cmb_Mas_Type.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
//public JComboBox cmb_mas_code;
    private void fetch() throws SQLException {

        final String DB_URL = "jdbc:mysql://localhost:3306/sms";
        final String USERNAME = "root";
        final String PASSWORD = "123456";
        try {
            Connection conn1 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
ResultSet rs = conn1.createStatement().executeQuery("select * from master");
while (rs.next()){

    ObservableList<Object> data = FXCollections.observableArrayList();



    data.add(new String(rs.getString(1)));

    cmb_Mas_Code.addItem(data.toString());


    data.add(new String(rs.getString(2)));

    cmb_Mas_Type.addItem(data.toString());

}

    } catch (SQLException e) {
        e.printStackTrace();
    }

    }


    private void save() {
        String GetDate = tfGetDate.getText();
        String BillCode = tfBillCode.getText();
        String BillMonth = cmb_Bill_Month.getSelectedItem().toString();
        String MunicipalTax = tfMunicipalTax.getText();
        String ServiceTax = tfServiceTax.getText();
        String PaymentMode = cmb_Pay_Mode.getSelectedItem().toString();
        String BillCycle = cmb_Bill_Cycle.getSelectedItem().toString();
        String MasterCode = cmb_Mas_Code.getSelectedItem().toString();
        String MasterType = cmb_Mas_Type.getSelectedItem().toString();
        String MemberId = tfMemberId.getText();
        String parkingcharges = tfparkingcharges.getText();
        SimpleDateFormat sdfmt=new SimpleDateFormat("yyyy/MM/dd");
        String dt=sdfmt.format(datechos.getDate());
        tfGetDate.setText(dt);


        if (BillCode.isEmpty() || BillMonth.isEmpty() || MunicipalTax.isEmpty() || ServiceTax.isEmpty() || PaymentMode.isEmpty() || BillCycle.isEmpty() || MasterCode.isEmpty() || MasterType.isEmpty() || parkingcharges.isEmpty())
        {
            JOptionPane.showMessageDialog( this,
                 "Please enter all fields bingo",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;

        }


        maintenance = addDataToDatabase(BillCode,dt, BillMonth, MunicipalTax, ServiceTax, PaymentMode, BillCycle ,MasterCode, MasterType,MemberId,parkingcharges);

            if (maintenance != null){

                dispose();
            }
            else
            {
                JOptionPane.showMessageDialog( this,
                        "Please enter all fields"+BillCode+dt+BillMonth+MunicipalTax+ServiceTax+PaymentMode+MasterCode+MasterType+MemberId+parkingcharges ,
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
            }

        }

    public User maintenance;

    private User addDataToDatabase(String BillCode, String GetDate, String BillMonth, String MunicipalTax, String ServiceTax, String PaymentMode, String BillCycle, String MasterCode, String MasterType, String MemberId, String parkingcharges) {
        User User = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/sms";
        final String USERNAME = "root";
        final String PASSWORD = "123456";
        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

        Statement stmt;
            stmt = conn.createStatement();
            String  sql = "INSERT INTO maintenance ( Bill_code,Bill_Date,Bill_Month,Municipal_Tax,Service_Tax,Payment_Mode,Bill_Cycle,master_code,master_type,member_id,parking_charges)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement prepareStatement = conn.prepareStatement(sql);
        prepareStatement.setString( 1,BillCode);
        prepareStatement.setString( 2,GetDate);
        prepareStatement.setString( 3,BillMonth);
        prepareStatement.setString( 4,MunicipalTax);
        prepareStatement.setString( 5,ServiceTax);
        prepareStatement.setString( 6,PaymentMode);
        prepareStatement.setString( 7,BillCycle);
        prepareStatement.setString( 8,MasterCode);
        prepareStatement.setString( 9,MasterType);
        prepareStatement.setString(10,MemberId);
            prepareStatement.setString(11,parkingcharges);

          //  JOptionPane.showMessageDialog(this,prepareStatement);
        int addedRows = (prepareStatement).executeUpdate();
        if (addedRows>0){
            maintenance = new User();
            maintenance.BillCode = BillCode;
            maintenance.GetDate = GetDate;
            maintenance.BillMonth = BillMonth;
            maintenance.MunicipalTax = MunicipalTax;
            maintenance.ServiceTax = ServiceTax;
            maintenance.PaymentMode = PaymentMode;
            maintenance.BillCycle = BillCycle;
            maintenance.MasterCode = MasterCode;
            maintenance.MasterType = MasterType;
            maintenance.MemberId = MemberId;
            maintenance.parkingcharges = parkingcharges;
        }
        stmt.close();
        conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maintenance;

        }

    public static void main(String[] args) {


        MaintenanceForm myform = new MaintenanceForm(  null);

        User maintenance = myform.maintenance;
        if(maintenance!=null){
            System.out.println("Record Added Successfully For Bill Number:" + maintenance.BillCode);
        }
        else{
            System.out.println("Transaction Failed");
        }

        }

    }

