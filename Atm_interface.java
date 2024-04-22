// MIN,MAX,SUM,AVG
#include <iostream>
#include <vector>
#include <omp.h>
#include <time.h>
    
using namespace std;

int main() {
const int size = 1000;
vector<int> data(size);
srand(time(0));
for (int i = 0; i < size; ++i) {
data[i] = rand() % 100;
}
    
int min_value = data[0];
#pragma omp parallel for reduction(min:min_value)
for (int i = 0; i < size; ++i) {
if (data[i] < min_value) {
min_value = data[i];
}
    
}
int max_value = data[0];
#pragma omp parallel for reduction(max:max_value)
for (int i = 0; i < size; ++i) {
if (data[i] > max_value) {
max_value = data[i];
}
    
}
int sum = 0;
#pragma omp parallel for reduction(+:sum)
for (int i = 0; i < size; ++i) {
sum += data[i];
}
    
float average = 0.0;
#pragma omp parallel for reduction(+:average)
for (int i = 0; i < size; ++i) {
average += static_cast<float>(data[i]) / size;
}
    
cout << "Minimum value: " << min_value << endl;
cout << "Maximum value: " << max_value << endl;
cout << "Sum of values: " << sum << endl;
cout << "Average of values: " << average << endl;
return 0;
}
// ---------------------&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&-------------------------------------------------------------
// BFS, DFS
#include <iostream>
#include <vector>
#include <queue>
#include <omp.h>
using namespace std;
const int MAX_NODES = 100;
vector<int> graph[MAX_NODES];
void parallelBFS(int start) {
bool visited[MAX_NODES] = {false};
queue<int> q;
q.push(start);
visited[start] = true;
while (!q.empty()) {
int current = q.front();
q.pop();
#pragma omp parallel for
for (int i = 0; i < graph[current].size(); ++i) {
int neighbor = graph[current][i];
#pragma omp critical
{
if (!visited[neighbor]) {
q.push(neighbor);
visited[neighbor] = true;
}
}
}
}
cout << "BFS Visited Nodes: ";
for (int i = 0; i < MAX_NODES; ++i) {
if (visited[i]) {
cout << i << " ";
}
}
cout << endl;
}
void parallelDFS(int start, bool visited[]) {
visited[start] = true;
#pragma omp parallel for
for (int i = 0; i < graph[start].size(); ++i) {
int neighbor = graph[start][i];
if (!visited[neighbor]) {
parallelDFS(neighbor, visited);
}
}
}
int main() {
graph[0] = {1, 2};
graph[1] = {0, 3, 4};
graph[2] = {0, 5, 6};
graph[3] = {1};
graph[4] = {1};
graph[5] = {2};
graph[6] = {2};
int start_node = 0;
parallelBFS(start_node);
bool visited[MAX_NODES] = {false};
parallelDFS(start_node, visited);
cout << "DFS Visited Nodes: ";
for (int i = 0; i < MAX_NODES; ++i) {
if (visited[i]) {
cout << i << " ";
}
}
cout << endl;
return 0;
}

// -------------------------------------$$$$$$$$$$$$$$$$$$$$$$$$$$-------------------------------------
import java.util.*;

public class Atm_interface{
    Scanner sc=new Scanner(System.in);

    static String Name;
    static String Username;
    static String Password;
    static String Account_number;
    static String Transaction_history="";
    static float balance=0;
    static int Transaction;

    //Exit
    void exit(){
        System.exit(0);
    }

    //Transaction History
    
    void Transaction_history(){
        System.out.println("----------------\n");
        if(Transaction ==0){
            System.out.println(" Empty Transactions ");
        }else{
            System.out.println("\n" + Transaction_history);
            System.out.println();
        }
    }

    //balance
    void check_Balance(){
        System.out.println("----------------\n");
        System.out.println("Balance-: "+ balance);
        System.out.println("----------------\n");
    }

    //Money Transfer
    public static void transfer_Money(){
        Scanner sc=new Scanner(System.in);
        System.out.println("----------------\n");
        System.out.print(" Enter Account Holder Name-: ");
        String act_holder=sc.nextLine();
        System.out.println();
        System.out.print(" Enter Amount to Transfer-: ");
        float Amount=sc.nextFloat();
        System.out.println("----------------\n");

        if(balance>=Amount){
            if(Amount<=50000f){
                Transaction++;
                balance -=Amount;
                System.out.println(" Successfully Transferred RS "+Amount +" To "+ act_holder+" ");
                String str=Amount + " Ruppes Transferred To "+ act_holder +". \n";
                Transaction_history=Transaction_history.concat(str);

            }else{
                System.out.println(" Limit Exceeded! Your Limit is 50000/-Rs.");
            }
        }else{
            System.out.println("Insufficient Balance !");
        }
    }

    //withdraw
    void withdraw_Amount(){
        System.out.println("----------------\n");
        System.out.print("Enter the Amount to Withdraw-: ");
        float Amount=sc.nextInt();
        System.out.println("----------------\n\n");

        if(balance >= Amount){
            Transaction++;
            balance -= Amount;
            System.out.println(" Rs "+ Amount +" Withdrawal successful");
            String str=Amount+" Rupees Withdrawed. \n";
            Transaction_history=Transaction_history.concat(str);

        }else{
            System.out.println("Insuffucient Balance");
        }

    }
    
    //Deposit
    void deposit_Amount(){
        System.out.println("----------------\n");
        System.out.print("Enter the Amount to be Deposited-: ");
        float Amount=sc.nextFloat();
        System.out.println("----------------\n");

        if(Amount <=100000f){
            Transaction++;
            balance+=Amount;
            System.out.println(" Rs " + Amount + " Deposited Successful");
            String str=Amount + " Rupees Deposited. \n";
            Transaction_history=Transaction_history.concat(str);
        }else{
            System.out.println(" Limit 100000.0/- RS Only ");
        }
    }

    //operations
    void operation(){
        System.out.println("----------------\n");
        System.out.println(" Welcome Back "+ this.Name +"! \n");
        
        while(true){
            System.out.println("----------------\n");
            System.out.println(" Press 1 for Deposit");
            System.out.println(" Press 2 for Withdraw");
            System.out.println(" Press 3 for Check Balance");
            System.out.println(" Press 4 for Transfer Money");
            System.out.println(" Press 5 for Transaction History");
            System.out.println(" Press 0 to Exit");
            System.out.println("----------------\n");

            System.out.print("Enter Your choice-: ");
            int choice_2=sc.nextInt();
            System.out.println();

            switch(choice_2){
                case 1:{
                    deposit_Amount();
                    break;
                }
                case 2:{
                    withdraw_Amount();
                    break;
                }
                case 3:{
                    check_Balance();
                    break;
                }
                case 4:{
                    transfer_Money();
                    break;
                }
                case 5:{
                    Transaction_history();
                    break;
                }
                case 0:{
                    exit();
                }
                default:{
                    System.out.println("Invalid Operation !");
                }
            }
        }
    }

    void Login(){
        System.out.println("----------------\n");
       
        while(true){
            System.out.print("Enter Username-: ");
            String Username_1=sc.nextLine();
            System.out.println("----------------\n");

            if(Username_1.equals(Username)){
                while(true){
                    System.out.print("Enter your Password-: ");
                    String Password_1=sc.nextLine();
                    System.out.println("----------------\n");

                    if(Password_1.equals(Password)){
                        System.out.println("Login Successful !\n");
                        operation();
                        break;
                    }else{
                        System.out.println("Incorrect Password! Please Try Again.");
                    }
                }
            }else{
                System.out.println("Invalid username! Check your Username and Try again.");
            }
            
        }
    }

    void sighUp(){
        System.out.println("----------------\n");
        
        System.out.print("Enter your Name-: ");
        this.Name=sc.nextLine();
        System.out.println("----------------\n");
        System.out.print("Enter your Username-: ");
        this.Username=sc.nextLine();
        System.out.println("----------------\n");
        System.out.print("Enter your Password-: ");
        this.Password=sc.nextLine();
        System.out.println("----------------\n");
        System.out.print("Enter your Account Number-: ");
        this.Account_number=sc.nextLine();
        System.out.println("----------------\n");
        System.out.println("** Registration Completed.Thank You!! **\n");
    }

    public static void main(String args[]){
        Atm_interface obj=new Atm_interface();
        Scanner sc=new Scanner(System.in); 
        System.out.println("----------------\n");
        System.out.println("** WELCOME TO ATM **");
        System.out.println(" Press 1 To SIGNUP \n Press 2 TO EXIT");
        System.out.println("----------------\n");
        System.out.print("Enter Your Choice-: ");
        int choice=sc.nextInt();

        switch(choice){
            case 1:{
                obj.sighUp();
                break;
            }
            case 2: {
                System.exit(0);  //successful termination
            }
        }

        System.out.println(" Press 1 To LOGIN \n Press 2 To EXIT ");
        System.out.println("----------------\n");
        System.out.print("Enter your choice-: ");
        int choice_1=sc.nextInt();

        switch(choice_1){
            case 1:{
                obj.Login();
            }
           case 2:{
                System.exit(0);
            }
        }
        
    }
}
