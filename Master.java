//all imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.io.*;
import java.text.SimpleDateFormat;

//below given all the fields
public class Master extends JDialog
{
    private JTextField tfmastercode;
    private JTextField tfmastertype;
    private JTextField tfmasterdescription;
    private JButton saveButton;
    private JButton cancelButton;
    private JPanel MasterJ;
    private JButton searchButton;


    public Master() {
        final String[] flag = {""};
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             //   JOptionPane.showMessageDialog(null,"hello world");
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            if (flag[0].equals("Update"))
            {
               // JOptionPane.showMessageDialog(null,"Update");
                update();
            }
            else
            {
                save();
            }

            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    fetch();
                    flag[0] ="Update";
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }
        });
    }

    private void update()
    {
        String MasterCode = tfmastercode.getText();
        String MasterType = tfmastertype.getText();
        String MasterDesc = tfmasterdescription.getText();

        final String DB_URL = "jdbc:mysql://localhost:3306/sms";
        final String USERNAME = "root";
        final String PASSWORD = "123456";

        try {

            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            //  JOptionPane.showMessageDialog(null,"Near Connection");
            Statement stmt;
            stmt = conn.createStatement();
            String  sql = "Update master " + "set master_type ='" + tfmastertype.getText().toString()+ "',master_desc = '" +tfmasterdescription.getText().toString()+
                    "' where master_code = '"+tfmastercode.getText().toString()+"'";

            PreparedStatement prepareStatement = conn.prepareStatement(sql);


            //  JOptionPane.showMessageDialog(this,prepareStatement);
            int addedRows = (prepareStatement).executeUpdate();
            JOptionPane.showMessageDialog(this,"Record Updated Sucessfully");
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //  JOptionPane.showMessageDialog(null,"Error");
        }
    }

    private void fetch() throws SQLException {

      //  if (MasterCode.isEmpty())
        {
         //   JOptionPane.showMessageDialog( this,
           //         "Please enter all fields, Can not be empty",
             //       "Try again",
               //     JOptionPane.ERROR_MESSAGE);
          //  return;

        }

        final String DB_URL = "jdbc:mysql://localhost:3306/sms";
        final String USERNAME = "root";
        final String PASSWORD = "123456";
        try {

            Connection conn1 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String Data = tfmastercode.getText().toString();
            String sql = "select * FROM sms.master where master_code = '"+tfmastercode.getText().toString()+"';";
            ResultSet rs = conn1.createStatement().executeQuery(sql);

            while (rs.next()){
                tfmastercode.setText(rs.getString("master_code").toString());
                tfmastertype.setText(rs.getString("master_type").toString());
                tfmasterdescription.setText(rs.getString("master_desc").toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        JOptionPane.showMessageDialog(null,e.getMessage().toString());
        }

    }




    private void save() {

        String MasterCode = tfmastercode.getText();
        String MasterType = tfmastertype.getText();
        String MasterDesc = tfmasterdescription.getText();


        if (MasterCode.isEmpty() || MasterType.isEmpty() || MasterDesc.isEmpty()  )
        {
            JOptionPane.showMessageDialog( this,
                    "Please enter all fields, Can not be empty",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;

        }


        Master = addDataToDatabase(MasterCode, MasterType,MasterDesc);

        if (Master != null){

            dispose();
        }
        else
        {
            JOptionPane.showMessageDialog( this,
                    "Please enter all fields"+MasterCode+MasterType+MasterDesc,
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public Master_User Master;

    private Master_User addDataToDatabase(String mastercode, String mastertype,String masterdescription)

    {

        Master_User User = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/sms";
        final String USERNAME = "root";
        final String PASSWORD = "123456";

        try {

            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
          //  JOptionPane.showMessageDialog(null,"Near Connection");
            Statement stmt;
            stmt = conn.createStatement();
            String  sql = "INSERT INTO master (master_code,master_type,master_desc)" +
                    " VALUES (?,?,?)";

            PreparedStatement prepareStatement = conn.prepareStatement(sql);
            prepareStatement.setString( 1,mastercode);
            prepareStatement.setString( 2,mastertype);
            prepareStatement.setString( 3,masterdescription);


            //    JOptionPane.showMessageDialog(this,prepareStatement);

            //  JOptionPane.showMessageDialog(this,prepareStatement);
            int addedRows = (prepareStatement).executeUpdate();
            if (addedRows>0){
                Master = new Master_User();
             Master.mastercode = mastercode;
             Master.mastertype = mastertype;
             Master.masterdescription = masterdescription;
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
          //  JOptionPane.showMessageDialog(null,"Error");
        }

        return Master;

    }


    public static void main(String[] args) {

    JFrame frame = new JFrame("Master");
    frame.setContentPane(new Master().MasterJ);
    frame.pack();
    frame.setSize(500,500);
    frame.setLocation(500,100);

    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

}


