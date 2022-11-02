
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
import java.util.Observable;
import javafx.collections.ObservableList;
public class Member extends JDialog {

    private JTextField tfMemberId;
    private JTextField tfFirstName;
    private JTextField tfMiddleName;
    private JTextField tfLastName;
    private JTextField tfDateOfBirth;
    private JTextField tfFlatNo;
    private JTextField tfTellPhoneNo;
    private JTextField tfContact1;
    private JTextField tfContact2;
    private JTextField tfEmailId1;
    private JTextField tfEmailId2;
  //  private JTextField tfMemberDescription;
    private JTextField tfParkingLot;
  //  private JTextField tfMasterCode;
  //  private JTextField tfMasterType;
    private JButton btnSave;
    private JButton btnCancel;
    private JPanel MemberPanel;
    private JPanel Jcal;
    private JComboBox cmb_mem_code;
    private JComboBox cmb_mem_type;
    private JComboBox cmb_mem_des;

    Calendar cld =Calendar.getInstance();
    JDateChooser datechos = new JDateChooser(cld.getTime());
    //all the content from form.

    public Member(JFrame parent) {
        super(parent);
        setTitle("Member Details");
        setContentPane(MemberPanel);
        setMinimumSize(new Dimension(830, 560));
        setModal(true);
        setLocationRelativeTo(parent);
        datechos.setDateFormatString("yyyy/MM/dd");
        Jcal.add(datechos);
       // save();
        //frames specs.
        try {
            fetch1();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();

            }
            //save button
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                //cancel button

            }
        });
        setVisible(true);


        cmb_mem_code.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

    //    fetch();

            }
        });
    }


    private void fetch1() throws SQLException {


        final String DB_URL = "jdbc:mysql://localhost:3306/sms";
        final String USERNAME = "root";
        final String PASSWORD = "123456";
        try {

            Connection conn2 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            ResultSet rs1 = conn2.createStatement().executeQuery("select * from master");
            while (rs1.next()){

                ObservableList<Object> data1 = FXCollections.observableArrayList();



                data1.add(new String(rs1.getString(1)));
               // JOptionPane.showMessageDialog(this,data1.toString());

                cmb_mem_code.addItem(data1.toString());


                data1.add(new String(rs1.getString(2)));

                cmb_mem_type.addItem(data1.toString());

                data1.add(new String(rs1.getString(3)));

                cmb_mem_des.addItem(data1.toString());

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }




    private void save() {
         String MemberId = tfMemberId.getText() ;
         String FirstName = tfFirstName.getText();
         String MiddleName = tfMiddleName.getText();
         String LastName = tfLastName.getText();
        SimpleDateFormat sdfmt=new SimpleDateFormat("yyyy/MM/dd");
        String dt=sdfmt.format(datechos.getDate());
        tfDateOfBirth.setText(dt);

         String DateOfBirth = tfDateOfBirth.getText();
         String FlatNo = tfFlatNo.getText();
         String TellPhoneNo = tfTellPhoneNo.getText();
         String Contact1 = tfContact1.getText();
         String Contact2 = tfContact2.getText();
         String EmailId1 = tfEmailId1.getText();
         String EmailId2 = tfEmailId2.getText();
         String MemberDescription = cmb_mem_des.getSelectedItem().toString();
         String ParkingLot = tfParkingLot.getText().toString();
         String MasterCode = cmb_mem_code.getSelectedItem().toString();
         String MasterType = cmb_mem_type.getSelectedItem().toString();

         //will take from user the values.

         if (MemberId.isEmpty() ||
                 FirstName.isEmpty() ||
                 MiddleName.isEmpty() ||
                 LastName.isEmpty() ||
                 FlatNo.isEmpty() ||
                 TellPhoneNo.isEmpty() ||
                 Contact1.isEmpty() ||
                 Contact2.isEmpty() ||
                 EmailId1.isEmpty() ||
                  EmailId2.isEmpty() ||
                 MemberDescription.isEmpty() ||
                 ParkingLot.isEmpty() ||
                 MasterCode.isEmpty() ||
                 MasterType.isEmpty() ) {
             //will see if everything is there or not.

             JOptionPane.showMessageDialog(this,
                     "Please enter all fields G",
                     "Manditory Fields",
                     JOptionPane.ERROR_MESSAGE);
             return;
         }
          member = addUsertoDatabase( MemberId ,
                 FirstName ,
                 MiddleName ,
                 LastName ,
                 DateOfBirth ,
                 FlatNo ,
                 TellPhoneNo ,
                 Contact1 ,
                 Contact2 ,
                 EmailId1 ,
                 EmailId2 ,
                 MemberDescription ,
                 ParkingLot ,
                 MasterCode ,
                 MasterType);
         if (member != null){
             dispose();
         }
         else
         {
             JOptionPane.showMessageDialog( this,
                     "Please enter all fields J",
                     "Data Sent to Database Failed",
                     JOptionPane.ERROR_MESSAGE);
         }
    }
    public User member;
 private User addUsertoDatabase(String MemberId,
    String FirstName,
    String MiddleName,
    String LastName,
    String DateOfBirth,
    String FlatNo,
    String TellPhoneNo,
    String Contact1,
    String Contact2,
    String EmailId1,
    String EmailId2,
    String MemberDescription,
    String ParkingLot,
    String MasterCode,
    String MasterType){
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/sms";
        final String USERNAME = "root";
        final String PASSWORD = "123456";
        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

            Statement stmt;
            stmt = conn.createStatement();
            String  sql = "INSERT INTO member_details (mem_id,mem_Fname,mem_Mname,mem_Lname,mem_DOB,flat_no,mem_tellphone,mem_contact1,mem_contact2,mem_emailId1,mem_emailId2,mem_desc,mem_parkingLot,master_code,master_type) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement prepareStatement = conn.prepareStatement(sql);
            prepareStatement.setString( 1,MemberId);
            prepareStatement.setString( 2,FirstName);
            prepareStatement.setString( 3,MiddleName);
            prepareStatement.setString( 4,LastName);
            prepareStatement.setString( 5,DateOfBirth);
            prepareStatement.setString( 6,FlatNo);
            prepareStatement.setString( 7,TellPhoneNo);
            prepareStatement.setString( 8,Contact1);
            prepareStatement.setString( 9,Contact2);
            prepareStatement.setString(10,EmailId1);
            prepareStatement.setString(11,EmailId2);
            prepareStatement.setString(12,MemberDescription);
            prepareStatement.setString(13,ParkingLot);
            prepareStatement.setString(14,MasterCode);
            prepareStatement.setString(15,MasterType);


            int addedRows = (prepareStatement).executeUpdate();
            if (addedRows>0){
                member = new User();
                member.MemberId = MemberId;
                member.FirstName = FirstName;
                member.MiddleName= MiddleName;
                member.LastName = LastName;
                member.DateOfBirth = DateOfBirth;
                member.FlatNo = FlatNo;
                member.TellPhoneNo = TellPhoneNo;
                member.Contact1 = Contact1;
                member.Contact2 = Contact2;
                member.EmailId1 = EmailId1;
                member.EmailId2 = EmailId2;
                member.MemberDescription = MemberDescription;
                member.ParkingLot = ParkingLot;
                member.MasterCode = MasterCode;
                member.MasterType = MasterType;

              //cmb_mem_code.addActionListener(new

                                                     //  ActionListener() {
                                                       //  @Override
                                                         //public void actionPerformed (ActionEvent e){

  //                                                           ResultSet rs = conn.createStatement().executeQuery("select * from member_details");

                                                           //  while (rs.next()){
                                                             //    ObservableList data = FXCollections.observableArrayList();
                                                               //  data.add((rs.getString(14)));
                                                              //
                                                            // cmb_mem_code.setSelectedItem(data);

                                                           }
                                                      //);

        //  }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return member;
    }





    public static void main(String[]args){
        Member mem = new Member(null);



 }
    }


