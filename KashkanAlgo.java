import java.util.*;

public class KashkanAlgo {
    static int total_subjects;
    static int max_students;
    static Scanner s = new Scanner(System.in);
    
    
    public static void main(String[] args) {
        System.out.println("Enter number of students");
        
        int total_students=s.nextInt();
        
        System.out.println("Enter number of subjects");
        
        total_subjects=s.nextInt();
        subjectList subjects[]=new subjectList[total_subjects];
        max_students=(int)Math.ceil(total_students/total_subjects);
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
        
        int flag = 0;

        do{
            flag = 0;
            for(int i = 0;i<total_students;i++)
            {
                if(student[i].alloted==false){
                    flag = 1;
                }
            }
            if(flag==0)
                break;
            
            
            for(int i = 0;i<total_students && !student[i].alloted;i++){
                int temp = student[i].subjects.pop();
                if(subjects[temp-1].length<max_students){
                    subjects[temp-1].insertSubjectList(student[i].id, student[i].cgpa);  
                    student[i].alloted=true;
                }
                else{
                    System.out.println("i and temp are "+ i + temp);    //DEBUGGs
                    if(student[i] == null){
                        System.out.println("student[temp-1] is null");
                    }
                    if(subjects[temp-1].head.cgpa<student[i].cgpa)
                    {   
                        
                        for(int j=0;j<student.length;j++)
                        {
                            if(student[j].id==subjects[temp-1].head.id)
                                student[j].alloted=false;
                            break;
                        }
                        Node tempnode=new Node(student[i].id,student[i].cgpa);
                        tempnode.next=subjects[temp-1].head.next;
                        subjects[temp-1].head=tempnode;
                        student[i].alloted=true;
                        
                    }
                }
               
            }
            
            
        }while(flag==1);
        for(int i=0;i<total_subjects;i++)
        {
            subjects[i].printlist(subjects[i].head);
        }
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
            length++;
        }
        void printlist(Node head)
        {
            Node tempno=head;
            while(tempno.next!=null)
            {
                System.out.print(tempno.id+"  ");
            }
            System.out.println();
        }
            void insertSubjectList(int idst, float cgpa)
            {    
                Node new_node=new Node(idst,cgpa);
                Node current;
 
         /* Special case for head node */
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
            temp.push(s.pop());
        }
        return temp;
    }
}