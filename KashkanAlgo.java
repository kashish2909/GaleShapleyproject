package dsproject;
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
//        do{
//            //This will help us to get outta do while
//            flag = 0;
//            for(int i = 0;i<total_students;i++){
//                if(student[i].alloted==false){
//                    flag = 1;
//                }
//            }
//            if(flag==0)
//                break;
//            
            //This means that now we still have students that have not been alotted any subject
            int count = 0;
            for(int i = 0;i<total_students;i = (i+1)%(total_students)){
                if(count==total_students)
                    break;
                System.out.println("i:"+i);
                if(student[i].subjects.isEmpty()){
                    System.out.println("student:"+(i+1)+" - Stack is empty");
                    continue;
                }
                if(student[i].alloted==true ){
                    System.out.println("student["+i+"].alloted==true");
                    continue;
                }
                    
                
                int temp = student[i].subjects.pop();
                
                //If there is a vaccancy in the subject 
                if(subjects[temp-1].length<max_students){
                   // System.out.println("length<max_students");
                    subjects[temp-1].insertSubjectList(student[i].id, student[i].cgpa);
                    count++;
                    student[i].alloted=true;
                }
                //If there are no vacant seats then we see if the new student is better than any of the student that has been alotted the same course
                else{
                    System.out.println("subjects[temp-1].head.cgpa, student[i].cgpa "+subjects[temp-1].head.cgpa+" "+student[i].cgpa);
                    if(subjects[temp-1].head.cgpa<student[i].cgpa)
                    {   
                        System.out.println("length IS MORE THAN max_students");
                        for(int j=0;j<student.length;j++)
                        {
                            if(student[j].id==subjects[temp-1].head.id){
                                student[j].alloted=false;
                                break;
                            }
                            
                        }
                        //Changing the Linked List of Subjects
//                        System.out.println("Changing the Linked List of Subjects");
//                        Node tempnode=new Node(student[i].id,student[i].cgpa);
//                        tempnode.next=subjects[temp-1].head.next;
//                        subjects[temp-1].head=tempnode;
                        subjects[temp-1].head = subjects[temp-1].head.next;
                        subjects[temp-1].length = subjects[temp-1].length-1;
                        subjects[temp-1].insertSubjectList(student[i].id,student[i].cgpa);
                        student[i].alloted=true;
                        
                    }
                    else
                        System.out.println("There's Already a better batch");
                }
               
            }
            
            
//        }while(flag==1);
        
        for(int i=0;i<total_subjects;i++)
        {
            subjects[i].printlist(subjects[i].head);
            System.out.println("Next Subject");
        }
//        for(int i=0;i<total_students;i++)
//        {
//            int tempsub=stud[i].subjects.pop();
//            subjects[tempsub-1].insertSubjectList(stud[i].id,stud[i].cgpa);
//            //visited condition and max students condition
//        }
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
    
        void insertSubjectList(int idst, float cgpa)
            {    
                Node new_node=new Node(idst,cgpa);
                Node current;
                length++;
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
                this.printlist(head); 
                System.out.println("Out of The Insert function");
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


