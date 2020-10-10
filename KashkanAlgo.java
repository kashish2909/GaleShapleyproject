import java.util.*;
import java.io.*;

public class KashkanAlgo {
    static int total_subjects = 0;
    static int total_students = 0;
    static int max_students = 0;
    static Scanner s = new Scanner(System.in);
    //good
    //static PrintStream ou = new PrintStream(new FileOutputStream("C:/Users/kashishban/Documents/dsa project/project/output.txt"));
    public static void main(String[] args) throws IOException
     {
        FileWriter outp=new FileWriter("output.txt");
        System.out.println("Enter number of students");

        total_students=s.nextInt();

        System.out.println("Enter number of subjects");

        total_subjects=s.nextInt();

        subjectList subjects[]=new subjectList[total_subjects];
        if(total_students%total_subjects==0)
           max_students=(total_students/total_subjects);
        else if(total_students%total_subjects!=0)
            max_students=(total_students/total_subjects)+1;
        Student student[]=new Student[total_students];

        for(int i=0;i<total_students;i++)
        {
            System.out.print("ID:"+(i+1)+"\t"+"Enter your cgpa\t");
            float cgpa=s.nextFloat();
            System.out.println();
            student[i]=new Student((i+1),cgpa);
        }

        for(int i=0;i<total_subjects;i++)
        {
            subjects[i]=new subjectList((i+1));
        }
            //This means that now we still have students that have not been alotted any subject
            int count = 0;
            System.out.println("");
            System.out.println("OUTPUT");
            outp.write("OUTPUT");
            outp.write("\r\n");
        for(int i = 0;i<total_students;i = (i+1)%(total_students)){
                if(count==total_students)
                    break;  
                if(student[i].subjects.isEmpty()){
                    continue;
                }
                if(student[i].alloted==true ){
                   continue;
                }
                int temp = student[i].subjects.pop();

                if(subjects[temp-1].length<max_students){                       
                    subjects[temp-1].insertSubjectList(student[i].id, student[i].cgpa);
                    count++;
                    student[i].alloted=true;
                }
                else{                        
                    if(subjects[temp-1].head.cgpa<student[i].cgpa)
                    {                               
                        for(int j=0;j<student.length;j++)
                        {
                            if(student[j].id==subjects[temp-1].head.id){
                                student[j].alloted=false;
                                break;
                            }

                        }
                        subjects[temp-1].head = subjects[temp-1].head.next;
                        subjects[temp-1].length = subjects[temp-1].length-1;
                        subjects[temp-1].insertSubjectList(student[i].id,student[i].cgpa);
                        student[i].alloted=true;

                    }
                }

            }
        
        for(int i=0;i<total_subjects;i++)
        {
            if(subjects[i].head==null){
                System.out.print("Subject"+(i+1)+":"+" Empty");
                outp.write("Subject"+(i+1)+":"+" Empty");
                continue;
            }                    
            System.out.print("Subject"+(i+1)+":");
            subjects[i].printlist(subjects[i].head);                
            System.out.println();outp.write("\r\n");
        }
        System.out.println();
        for(int i=0;i<total_subjects;i++)
        {
            if(subjects[i].head==null){
                outp.write("Subject"+(i+1)+":"+" Empty");
                continue;
            }                    
            outp.write("Subject"+(i+1)+":");

            subjects[i].printlistout(subjects[i].head,outp);                
            outp.write("\r\n");
        }
        outp.close();
    }
    static class Student{
        int id;
        float cgpa;
        boolean alloted;
        String backup = "";
        Stack subjects = new Stack();
        Student(int id,float cgpa){
            this.id = id;
            this.cgpa = cgpa;
            alloted = false;
            System.out.println("Enter the choice of subjects(Separated by enter)");
            for(int i = 0;i<total_subjects;i++){
                int sub = s.nextInt();
                subjects.push(sub);
            }
            subjects = reverseStack(subjects,backup);

        }
    }

    static class subjectList{
        int id;
        Node head=null;
        int length = 0;

        subjectList(int k){
            id = k;

        }
        void printlist(Node head)
        {
            Node tempno=head;
            while(tempno!=null)
            {
                System.out.print(tempno.id+"  ");
                tempno = tempno.next;
            }
            System.out.println();
        }
        void printlistout(Node head,FileWriter outp) throws IOException
        {
            Node tempno=head;
            while(tempno!=null)
            {
                outp.write(tempno.id+"  ");
                tempno = tempno.next;
            }
            //outp.println();
        }

        void insertSubjectList(int idst, float cgpa)
            {    
                Node new_node=new Node(idst,cgpa);
                Node current;
                length++;
                // Special case for head node
                if (head == null || head.cgpa >= new_node.cgpa)
                {
                   new_node.next = head;
                   head = new_node;

                }
                else {

                   /* Locate the node before point of insertion. */
                   current = head;
                   while (current.next != null && current.next.cgpa < new_node.cgpa)
                        current = current.next;

                   new_node.next = current.next;
                   current.next = new_node;
                }                
                return;
            }
    }


    static class Stack{    //THIS CONTAINS THE LIST OF CHOICES OF SUBJECTS FILLED BY STUDENTS
        int subject_choice[] = new int[total_subjects];
        int top = -1;

        void push(int sub){
            if(top >= total_subjects-1){
                System.out.println("Overflow! Can't enter more than "+total_subjects+" subjects");
            }
            subject_choice[++top] = sub;
        }
        int pop(){
            if(top==-1){
                System.out.println("Underflow!");
            }
            return subject_choice[top--];
        }

        boolean isEmpty(){
            if(top==-1)
                return true;
            else return false;
        }

    }

    static Stack reverseStack(Stack s,String back){
        Stack temp = new Stack();

        while(!s.isEmpty()){
            int k = s.pop();
            back = back+k;
            temp.push(k);
        }
        return temp;
    }

    static class Node
    {
        int id;
        float cgpa;
        Node next; //make constructor
        Node(int id,float cgpa)
        {
            this.id=id;
            this.cgpa=cgpa;
        }
    }
}


