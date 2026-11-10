

package my.mini.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;   

public class ParkingVehicleManagement extends JFrame{
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
        
    }
    
    
    

  
    
}
