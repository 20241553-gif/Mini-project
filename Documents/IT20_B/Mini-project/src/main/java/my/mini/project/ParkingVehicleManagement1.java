

package my.mini.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;   

public class ParkingVehicleManagement1 extends JFrame{
    static class VehicleNode {
        String plateNumber;
        String vehicleType;
        long entryTime;
        VehicleNode next;
        
        public VehicleNode(String plateNumber, String vehicleType, long entryTime) {
            this.plateNumber = plateNumber;
            this.vehicleType = vehicleType;
            this.entryTime = entryTime;
            this.next = null;
        }
    }
    
    static class VehicleLinkedList {
        private VehicleNode head;
        private int totalCount;
        
        public VehicleLinkedList() {
            this.head = null;
            this.totalCount = 0;
        }
    
     public void addVehicle(String plateNumber, String vehicleType, long entryTime) {
         VehicleNode newNode = new VehicleNode(plateNumber, vehicleType, entryTime);
         
         if (head == null) {
             head = newNode;
         }else{
             VehicleNode temp = head;
             while (temp.next != null) {
             temp = temp.next;
         }
         temp.next = newNode;
      } 
    totalCount++;
     }
     
     public VehicleNode findVehicle(String plateNumber){
         VehicleNode current = head;
         while (current != null){
             if (matchesPlate(current.plateNumber, plateNumber)) {
                 return current;
             }
             current = current.next;    
             }
         return null;
         }
     
     private boolean removeVehicle(String plateNumber) {
         if (head == null) {
             return false;
         }
         
         if (matchesPlate(head.plateNumber, plateNumber)) {
             head= head.next;
             totalCount--;
             return true;
         }
         
         VehicleNode current = head;
         while (current.next != null){
             if (matchesPlate(current.next.plateNumber, plateNumber)) {
                 current.next =current.next.next.next;
                 totalCount--;
                 return true;
             }
             current = current.next;
         }
         return false;
     }
     
     public VehicleNode getHead(){
         return head;
     }
     
     public int getTotalCount() {
         return totalCount;
     }
     
     private boolean matchesPlate(String str1, String str2){
         if (str1 == null || str2 == null) return false;
         String Clean1 = str1.trim() .toLowerCase();
         String Clean2 = str2.trim() .toLowerCase();
         return Clean1.equals(Clean2);
     }
     
    }
    private VehicleLinkedList vehicleList = new VehicleLinkedList();
 
    private JTextField txtPlateInput;
    private JComboBox<String> cbTypeInput;
    private JTextField txtSearchInput;
    private JTextField txtExitInput;

    private JTable tblVehicles;
    private DefaultTableModel tableModel;
    private JTextArea txtReceiptArea;
    private JButton btnConfirmExit;
    private JLabel lblStatus;
    private JLabel lblCount;

    private VehicleNode currentExitVehicle = null;

    private final double HOURLY_CAR = 5.00;
    private final double HOURLY_SUV = 7.00;
    private final double HOURLY_MOTO = 3.00;

    public ParkingVehicleManagement1() {
        setTitle("Vehicle Parking Management System");
        setSize(980, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buildUserInterface();
    }
    
    private void buildUserInterface() {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Parking Gate Controls"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0;
        topPanel.add(new JLabel("Plate No:"), g);
        g.gridx = 1;
        txtPlateInput = new JTextField(10);
        topPanel.add(txtPlateInput, g);

        g.gridx = 2;
        topPanel.add(new JLabel("Type:"), g);
        g.gridx = 3;
        String[] options = {"Car (10.00/hr)", "SUV (15.00/hr)", "Motorcycle (8.00/hr)"};
        cbTypeInput = new JComboBox<>(options);
        topPanel.add(cbTypeInput, g);

        g.gridx = 4;
        JButton btnRegister = new JButton("Register Entry");
        btnRegister.setBackground(new Color(34, 139, 34));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.addActionListener(e -> registerVehicle());
        topPanel.add(btnRegister, g);

        g.gridx = 0; g.gridy = 1;
        topPanel.add(new JLabel("Search Plate:"), g);
        g.gridx = 1;
        txtSearchInput = new JTextField(10);
        topPanel.add(txtSearchInput, g);

        g.gridx = 2;
        JButton btnSearch = new JButton("Find Plate");
        btnSearch.addActionListener(e -> searchVehicle());
        topPanel.add(btnSearch, g);

        g.gridx = 3;
        txtExitInput = new JTextField(10);
        topPanel.add(txtExitInput, g);

        g.gridx = 4;
        JButton btnCalculate = new JButton("Process Exit");
        btnCalculate.setBackground(new Color(178, 34, 34));
        btnCalculate.setForeground(Color.WHITE);
        btnCalculate.addActionListener(e -> calculateExitFee());
        topPanel.add(btnCalculate, g);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        String[] cols = {"Plate Number", "Type", "Entry Time", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tblVehicles = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(tblVehicles);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Active Parked Vehicles"));
        centerPanel.add(tableScroll);

        // Right Box: Billing Terminal (Embedded, no JOptionPane)
        JPanel receiptPanel = new JPanel(new BorderLayout(5, 5));
        receiptPanel.setBorder(BorderFactory.createTitledBorder("Checkout Terminal"));

        txtReceiptArea = new JTextArea();
        txtReceiptArea.setEditable(false);
        txtReceiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtReceiptArea.setText("\n  Select a vehicle or enter a plate number\n  and press 'Process Exit' to prepare bill.");
        receiptPanel.add(new JScrollPane(txtReceiptArea), BorderLayout.CENTER);

        btnConfirmExit = new JButton("Confirm Payment & Open Gate");
        btnConfirmExit.setEnabled(false);
        btnConfirmExit.setBackground(new Color(70, 130, 180));
        btnConfirmExit.setForeground(Color.WHITE);
        btnConfirmExit.addActionListener(e -> completeExit());
        receiptPanel.add(btnConfirmExit, BorderLayout.SOUTH);

        centerPanel.add(receiptPanel);
        
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createLoweredBevelBorder());

        lblStatus = new JLabel(" System Ready.");
        lblStatus.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblCount = new JLabel("Parked Vehicles: 0   ");
        lblCount.setFont(new Font("SansSerif", Font.BOLD, 12));

        bottomPanel.add(lblStatus, BorderLayout.WEST);
        bottomPanel.add(lblCount, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void registerVehicle() {
        String plate = txtPlateInput.getText().trim();

        if (plate.isEmpty()) {
            updateStatus("Error: Plate number cannot be empty.", Color.RED);
            return;
        }

        if (vehicleList.findVehicle(plate) != null) {
            updateStatus("Error: Vehicle with plate '" + plate + "' is already registered.", Color.RED);
            return;
        }

        String rawType = (String) cbTypeInput.getSelectedItem();
        String type = "Car";
        if (rawType != null && rawType.startsWith("SUV")) type = "SUV";
        if (rawType != null && rawType.startsWith("Motorcycle")) type = "Motorcycle";

        vehicleList.addVehicle(plate.toUpperCase(), type, System.currentTimeMillis());

        txtPlateInput.setText("");
        refreshTable();
        updateStatus("Vehicle " + plate.toUpperCase() + " registered successfully.", new Color(0, 100, 0));
    }

    private void searchVehicle() {
        String query = txtSearchInput.getText().trim();

        if (query.isEmpty()) {
            refreshTable();
            updateStatus("Table view restored.", Color.BLACK);
            return;
        }

        VehicleNode match = vehicleList.findVehicle(query);

        if (match != null) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).toString().equalsIgnoreCase(match.plateNumber)) {
                    tblVehicles.setRowSelectionInterval(i, i);
                    txtExitInput.setText(match.plateNumber);
                    updateStatus("Found Vehicle: Plate " + match.plateNumber + " (" + match.vehicleType + ")", Color.BLUE);
                    return;
                }
            }
        } else {
            updateStatus("Search Failed: No vehicle found with plate '" + query + "'.", Color.RED);
        }
    }
     private void calculateExitFee() {
        String plate = txtExitInput.getText().trim();

        if (plate.isEmpty()) {
            int selectedRow = tblVehicles.getSelectedRow();
            if (selectedRow >= 0) {
                plate = tableModel.getValueAt(selectedRow, 0).toString();
            }
        }

        if (plate.isEmpty()) {
            updateStatus("Error: Select a vehicle from table or enter a plate number.", Color.RED);
            return;
        }

        VehicleNode target = vehicleList.findVehicle(plate);

        if (target == null) {
            updateStatus("Error: Plate '" + plate + "' not found in database.", Color.RED);
            txtReceiptArea.setText("\n  Vehicle record not found.");
            btnConfirmExit.setEnabled(false);
            currentExitVehicle = null;
            return;
        }

        long durationMillis = System.currentTimeMillis() - target.entryTime;
        double hoursParked = Math.ceil(durationMillis / (1000.0 * 60 * 60));
        if (hoursParked < 1.0) hoursParked = 1.0;

        double hourlyRate = HOURLY_CAR;
        if (target.vehicleType.equalsIgnoreCase("SUV")) hourlyRate = HOURLY_SUV;
        if (target.vehicleType.equalsIgnoreCase("Motorcycle")) hourlyRate = HOURLY_MOTO;

        double totalBill = hoursParked * hourlyRate;

        String receipt = String.format(
            "\n  ------------------------------------\n" +
            "            PARKING RECEIPT           \n" +
            "  ------------------------------------\n\n" +
            "   Plate Number : %s\n" +
            "   Vehicle Type : %s\n" +
            "   Entry Time   : %s\n" +
            "   Duration     : %.0f hr(s)\n" +
            "   Rate / Hour  : $%.2f\n" +
            "  ------------------------------------\n" +
            "   TOTAL DUE    : $%.2f\n" +
            "  ------------------------------------\n\n" +
            "   Click 'Confirm Payment' below to\n" +
            "   complete exit processing.",
            target.plateNumber, target.vehicleType, getTimeString(target.entryTime), hoursParked, hourlyRate, totalBill
        );

        txtReceiptArea.setText(receipt);
        currentExitVehicle = target;
        btnConfirmExit.setEnabled(true);
        updateStatus("Receipt generated for " + target.plateNumber + ". Ready for payment.", Color.BLUE);
    }

    private void completeExit() {
        if (currentExitVehicle != null) {
            String plate = currentExitVehicle.plateNumber;
            boolean removed = vehicleList.removeVehicle(plate);

            if (removed) {
                refreshTable();
                txtExitInput.setText("");
                txtReceiptArea.setText("\n  Payment Confirmed!\n\n  Plate: " + plate + "\n  Status: Exited\n  Gate: Opened");
                btnConfirmExit.setEnabled(false);
                currentExitVehicle = null;
                updateStatus("Exit process complete for plate " + plate + ".", new Color(0, 100, 0));
            } else {
                updateStatus("Error removing vehicle from list.", Color.RED);
            }
        }
    }
    private void refreshTable() {
        tableModel.setRowCount(0); 
        VehicleNode current = vehicleList.getHead();

        while (current != null) {
            tableModel.addRow(new Object[]{
                current.plateNumber,
                current.vehicleType,
                getTimeString(current.entryTime),
                "PARKED"
            });
            current = current.next;
        }

        lblCount.setText("Parked Vehicles: " + vehicleList.getTotalCount() + "   ");
    }

    private void updateStatus(String msg, Color color) {
        lblStatus.setText(" " + msg);
        lblStatus.setForeground(color);
    }

    private String getTimeString(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = (minutes / 60) % 24;
        long remainingMins = minutes % 60;
        return String.format("%02d:%02d PM/AM", hours, remainingMins);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new ParkingVehicleManagement1().setVisible(true);
        });
    }
}
    

    





    

    

     
    
     
     
    
    
  

             
       
       



  