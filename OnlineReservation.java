import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;


public class OnlineReservation {
    private static final long min=1000000;
    private static final long max=9999999;

    public static class user{
        private String username;
        private String password;

        Scanner sc=new Scanner(System.in);
       public user(){

       }
       public String getUserName(){
        System.out.print("Enter Username: ");
        username=sc.nextLine();
        return username;
       }

       public String getPassword(){
        System.out.print("Enter Password: ");
        password=sc.nextLine();
        return password;
       }
    }

    public static class PnrRecord{
           private long pnrNumber;
           private String passengerName;
           private String trainNumber;
           private String trainName;
           private String classType;
           private String journeyDate;
           private String from;
           private String to;
           
           Scanner sc=new Scanner(System.in);

           public long getpnrNumber(){
            Random random=new Random();
            pnrNumber=random.nextLong(max)+min;
            return pnrNumber;
           }

           public String getPassengerName(){
            System.out.println("Enter the passenger name: ");
            passengerName=sc.nextLine();
            return passengerName;
           }

           public String gettrainNumber(){
            System.out.println("Enter the train number: ");
            trainNumber=sc.nextLine();
            return trainNumber;
           }
           public String gettrainName(){
            System.out.println("Enter the train name: ");
            trainName=sc.nextLine();
            return trainName;
           }

           public String getclassType(){
            System.out.println("Enter the class type: ");
            classType=sc.nextLine();
            return classType;
           }

           public String getjourneyDate(){
            System.out.println("Enter the Journey Date as 'YYYY-MM-DD' format: ");
            journeyDate=sc.nextLine();
            return journeyDate;
           }

           public String getfrom(){
            System.out.println("Enter the starting place: ");
            from=sc.nextLine();
            return from;
           }

           public String getto(){
            System.out.println("Enter the destination place: ");
            to=sc.nextLine();
            return to;
           }
    }
    
    public static void main(String[] args) throws SQLException {
        Scanner sc=new Scanner(System.in);
        user u1=new user();
        String username=u1.getUserName();
        String password=u1.getPassword();

        String url="jdbc:mysql://localhost:3306/devansh";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try(Connection connection=DriverManager.getConnection(url,username,password)){
                System.out.println("User Connection Granted.\n");
                while (true) { 
                    String InsertQuery="insert into reservations values(?,?,?,?,?,?,?,?)";
                    String DeleteQuery="delete from reservations where pnr_number=?";
                    String ShowQuery="Select * from reservations";
                    String query="Select * from reservations where pnr_number=?";

                    System.out.print("Enter the choice: \n");
                    System.out.println("1.Insert Record.");
                    System.out.println("2.Cancel Reservation.");
                    System.out.println("3. Show All Records");
                    System.out.println("4. Exit.\n");
                    int choice=sc.nextInt();
 
                    if(choice==1){
                        PnrRecord p1=new PnrRecord();
                        long pnr_number=p1.getpnrNumber();
                        String passengerName=p1.getPassengerName();
                        String trainNumber=p1.gettrainNumber();
                        String trainName=p1.gettrainName();
                        String classType=p1.getclassType();
                        String journeyDate=p1.getjourneyDate();
                        String getfrom=p1.getfrom();
                        String getto=p1.getto();

                        try(PreparedStatement preparedStatement=connection.prepareStatement(InsertQuery)){
                            preparedStatement.setLong(1, pnr_number);
                            preparedStatement.setString(2, passengerName);
                            preparedStatement.setString(3, trainNumber);
                            preparedStatement.setString(4, trainName);
                            preparedStatement.setString(5, classType);
                            preparedStatement.setString(6, journeyDate);
                            preparedStatement.setString(7, getfrom);
                            preparedStatement.setString(8, getto);

                            int rowsAffected=preparedStatement.executeUpdate();
                            if(rowsAffected>0){
                                System.out.println("Record added successfully.\n");
                            }
                            else{
                                System.out.println("No records were added!!\n");
                            }
                        }catch(SQLException e){
                            System.out.println("SQLException: "+e.getMessage());
                        }

                    }
                    else if(choice==2){
                        System.out.println("Enter the PNR number to cancel the reservation: ");
                        int pnrNumber=sc.nextInt();
                        try(PreparedStatement preparedStatement=connection.prepareStatement(query)){
                            
                            preparedStatement.setInt(1, pnrNumber);
                            ResultSet resultSet=preparedStatement.executeQuery();
                            if(resultSet.next()){
                                    String passengerName=resultSet.getString("passenger_name");
                                    String trainNumber=resultSet.getString("Train_number");
                                    String trainName=resultSet.getString("Train_name");
                                    String classType=resultSet.getString("Class_type");
                                    String journeyDate=resultSet.getString("Journey_date");
                                    String fromLocation=resultSet.getString("Boarding_station");
                                    String toLocation=resultSet.getString("Dest_station");
                                    System.out.println("The passenger details are: ");
                                    System.out.println("Passenger Name: "+passengerName);
                                    System.out.println("Train Number: "+trainNumber);
                                    System.out.println("Train Name: "+trainName);
                                    System.out.println("Class Type: "+classType);
                                    System.out.println("Journey Date: "+journeyDate);
                                    System.out.println("From Location: "+fromLocation);
                                    System.out.println("To Location: "+toLocation);

                                    try(PreparedStatement deletestatement=connection.prepareStatement(DeleteQuery)){
                                       
                                        deletestatement.setInt(1, pnrNumber);
                                        int rowsAffected=deletestatement.executeUpdate();
                                    if(rowsAffected>0){
                                    System.out.println("\nReservation cancelled successfully.\n");
                                    }else{
                                System.out.println("Failed to cancel Reservation.\n");
                            }
                        }
                    }else{
                        System.out.println("PNR Number not found.\n");
                    }
                }catch(SQLException e){
                            System.out.println("SQLException: "+e.getMessage());
                            System.out.println("An error occured while processing the reservation.");
                        }
                    }
                else if(choice==3){
                        try(PreparedStatement preparedStatement=connection.prepareStatement(ShowQuery);
                            ResultSet resultSet=preparedStatement.executeQuery()){
                                System.out.println("\nAll records printing.\n");
                                while(resultSet.next()){
                                    String pnrNumber=resultSet.getString("pnr_number");
                                    String passengerName=resultSet.getString("passenger_name");
                                    String trainNumber=resultSet.getString("Train_number");
                                    String trainName=resultSet.getString("Train_name");
                                    String classType=resultSet.getString("Class_type");
                                    String journeyDate=resultSet.getString("Journey_date");
                                    String fromLocation=resultSet.getString("Boarding_station");
                                    String toLocation=resultSet.getString("Dest_station");

                                    System.out.println("***************");
                                    System.out.println("PNR Number: "+pnrNumber);
                                    System.out.println("Passenger Name: "+passengerName);
                                    System.out.println("Train Number: "+trainNumber);
                                    System.out.println("Train Name: "+trainName);
                                    System.out.println("Class Type: "+classType);
                                    System.out.println("Journey Date: "+journeyDate);
                                    System.out.println("From Location: "+fromLocation);
                                    System.out.println("To Location: "+toLocation);
                                    System.out.println("***************");
                                }
                        }catch(SQLException e){
                            System.err.println("SQLException: "+e.getMessage());
                        }
                    }
                    else if(choice==4){
                        System.out.println("Exiting the program.\n");
                        break;
                    }
                    else{
                        System.out.println("Invalid Choice Entered.\n");
                    }
                }
            }catch(SQLException e){
                System.err.println("SQLException: "+e.getMessage());
            }
            
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading JDBC driver: "+e.getMessage());
        }

       sc.close(); 
    }
}


