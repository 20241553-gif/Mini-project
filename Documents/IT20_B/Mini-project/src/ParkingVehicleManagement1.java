

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
         
     }
     
    
     
     
    
    
  

             
       
       



  