import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class Contact_Manager {

    //Instance variables
    private Node name_root = null;
    private Node instagram_root = null;
    private int size = 0;

    //Constructor
    private class Node {
        private Contact key;
        private int val;
        private Node left;
        private Node right;

        private Node(Contact key, int val){
            this.key = key;
            this.val = val;
        }
    }

    //Constructor
    private class Contact {
        private String name;
        private String instagram;
        private long phone;
        private int priority;

        public Contact(String name, String instagram, long phone, int priority){
            this.name = name;
            this.instagram = instagram;
            this.phone = phone;
            this.priority = priority;
        }
    }
    
    //Function that verifies if the current BST is empty
    private boolean is_Empty(Node root){
        return root == null;
    }

    //Function that adds a new contact to our 2 BST`s
    public void add(Contact contact){
        name_root = add_by_name(name_root, contact, contact.priority);
        instagram_root = add_by_instagram(instagram_root, contact, contact.priority);
        size++;
    }

    private Node add_by_name(Node x, Contact key, int val){
        if(is_Empty(x)) return new Node(key, val);
        int cmp = key.name.compareTo(x.key.name);
        if(cmp < 0) x.left = add_by_name(x.left, key, val);
        else if(cmp > 0) x.right = add_by_name(x.right, key, val);
        else x.val = val;
        return x;
    }

    private Node add_by_instagram(Node x, Contact key, int val){
        if(is_Empty(x)) return new Node(key, val);
        int cmp = key.instagram.compareTo(x.key.instagram);
        if(cmp < 0) x.left = add_by_instagram(x.left, key, val);
        else if(cmp > 0) x.right = add_by_instagram(x.right, key, val);
        else x.val = val;
        return x;
    }

    //Function that search for a contact by its name
    public Contact search_by_name(String name){
        return search_by_name(name_root, name);
    }

    private Contact search_by_name(Node x, String name){
        if(is_Empty(x)){
            System.err.println("This contact is not in your list");
            return null;
        }
        int cmp = name.compareTo(x.key.name);
        if(cmp < 0) return search_by_name(x.left, name);
        else if(cmp > 0) return search_by_name(x.right, name);
        else{
            System.out.println("These are the informations about the contact:");
            System.out.println("Name:" + " " + x.key.name);
            System.out.println("Instagram:" + " " + x.key.instagram);
            System.out.println("Phone:" + " " + x.key.phone);
            System.out.println("Priority:" + " " + x.key.priority);
        }
        return x.key;
    }

    //Function that search for a contact by its instagram
    public Contact search_by_instagram(String instagram){
        return search_by_instagram(instagram_root, instagram);
    }

    private Contact search_by_instagram(Node x, String instagram){
        if(is_Empty(x)){
            System.err.println("This contact is not in your list");
            return null;
        }
        int cmp = instagram.compareTo(x.key.instagram);
        if(cmp < 0) return search_by_instagram(x.left, instagram);
        else if(cmp > 0) return search_by_instagram(x.right, instagram);
        else{
            System.out.println("These are the informations about the contact:");
            System.out.println("Name:" + " " + x.key.name);
            System.out.println("Instagram:" + " " + x.key.instagram);
            System.out.println("Phone:" + " " + x.key.phone);
            System.out.println("Priority:" + " " + x.key.priority);
        }
        return x.key;
    }

    //This function update the informations about a contact, but cant update its name and its instagram (for performance pourpuses)
    //Input: A String, the name of the Contact that will have its informations changed, and all the new informations that will be updated
    public void update(String name, String instagram, long phone, int priority){
        Contact old_contact = search_by_name(name_root, name);
        old_contact.phone = phone;
        old_contact.priority = priority;
        System.out.println("These are the new informations about the contact:");
            System.out.println("Name:" + " " + name);
            System.out.println("Instagram:" + " " + instagram);
            System.out.println("Phone:" + " " + phone);
            System.out.println("Priority:" + " " + priority);
    }

    //This function list all the user Contats by priority 
    public void list_by_priority(){
        Contact[] array;
        array = bstToArray();
        sort(array);
        int j = 0;
        System.out.println("These are your Contacts listed by priority");
        while(j < size){
            System.out.println(array[j].name);
            System.out.println("Instagram:" + " " + array[j].instagram);
            System.out.println("Phone:" + " " + array[j].phone);
            System.out.println(" ");
            j++;
        }
    }

    //This function creates an array with all the Contacts of a BST
    private Contact[] bstToArray() {
        List<Contact> resultList = new ArrayList<>();
        inorder(name_root, resultList);  // Preencher a lista com os contatos
        return resultList.toArray(new Contact[0]);  // Converter para um array de Contact
    }

    //This function put all the Contacts of a BST in a List
    //Input: the root of the BST and a empty List 
    private void inorder(Node node, List<Contact> result) {
        if (node == null) return;
        inorder(node.left, result);
        result.add(node.key);
        inorder(node.right, result);
    }

    //This function merges two sorted halfs of an array in one sorted array
    //Input:The two arrays we wish to merge, three index "low" (index of the first element), "mid" (index of the (hi-low)/ 2 element) and "hi" (index of the last element)
    private static void merge(Contact[] a, Contact[] aux, int low, int mid, int hi){
        for(int k = low; k <= hi; k++){         
            aux[k] = a[k];
        }
        int i = low, j = mid+1;
        for(int k = low; k <= hi; k++){
            if(i > mid)                                 a[k] = aux[j++];        
            else if(j > hi)                             a[k] = aux[i++];        
            else if(aux[j].priority < aux[i].priority)  a[k] = aux[j++];        
            else                                        a[k] = aux[i++];        
        }
    }

    //This function sorts a array with the help of another array "aux" and two index "lo" (index of the first element) e "hi"(index of the last element)
    //Input:The array we wish to sort, the "aux" array, and the two index "lo" and "hi"
    private static void sort(Contact[] a, Contact[] aux, int lo, int hi){
        if(hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);              
        sort(a, aux, mid+1, hi);            
        if(!(a[mid+1].priority < a[mid].priority)) return;    
        merge(a, aux, lo, mid, hi);         
    }

    //This funcion sorts an array given by the user
    public static void sort(Contact[] a){
        Contact[] aux = new Contact[a.length];
        sort(a, aux, 0, a.length-1); 
    }

    //This function list all the user Contacts in alfabetical order 
    public void list_by_name(){
        System.out.println("These are your Contacts listed by alfabetical order");
        list_by_name(name_root);
    }

    private void list_by_name(Node node) {
        if (node == null) return;
        list_by_name(node.left);
        System.out.println(node.key.name);
        System.out.println("Instagram:" + " " + node.key.instagram);
        System.out.println("Phone:" + " " + node.key.phone);
        System.out.println(" ");
        list_by_name(node.right);
    }

    //This function deletes the oldest contact added by the user
    public void delete(){
        name_root = delete(name_root, name_root.key);
        size--;
    }

    private Node delete(Node x, Contact key){
        if(x == null) return null;                          //in case the node to be deleted doesnt exist or doesnt have children, we just set it to null
        int cmp = key.name.compareTo(x.key.name);
        if      (cmp < 0) x.left = delete(x.left, key);     //search for the node
        else if (cmp > 0) x.right = delete(x.right, key);   
        else{
            if(x.right == null) return x.left;              //if the node doesnt have a left child, we put his right child in its place
            if(x.left == null) return x.right;              //if the node doesnt have a right child, we put his left child in its place

            Node t = x;                               //if the node has both children, the smallest node of its right subtree will be put in its place 
            x = min(t.right);                         
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        return x;
    }

    //This funcion returns the smallets Node of a subtree
    private Node min(Node x){
        if(x.left == null) return x;
        x.left = min(x.left);
        return x;
    }

    private Node deleteMin(Node x){
        if(x.left == null) return x.right;          
        x.left = deleteMin(x.left);                 
        return x;
    }

    // test client
    public static void main(String[] args){
        Contact_Manager st = new Contact_Manager();
        Contact contact1 = st.new Contact("Bruno", "cachorro", 1999159448, 26);
        st.add(contact1);
        Contact contact2 = st.new Contact("Carlos", "alface", 1994463235, 2);
        st.add(contact2);
        Contact contact3 = st.new Contact("Alemao", "dado", 1994463236, 5);
        st.add(contact3);
        Contact contact4 = st.new Contact("Diego", "jota", 1994463236, 76);
        st.add(contact4);
        Contact contact5 = st.new Contact("Dirma", "dade", 1994463236, 3);
        st.add(contact5);

        System.out.println(st.name_root.key.name);
        System.out.println(st.name_root.right.key.name);
        System.out.println(st.name_root.left.key.name);
        System.out.println(st.instagram_root.key.instagram);
        System.out.println(st.instagram_root.right.key.instagram);      
        System.out.println(st.instagram_root.left.key.instagram);       

        st.update("Bruno", "cachorro", 1999159448, 79);

        System.out.println(st.name_root.key.name);
        System.out.println(st.name_root.right.key.name);
        System.out.println(st.name_root.left.key.name);
        System.out.println(st.instagram_root.key.instagram);
        System.out.println(st.instagram_root.right.key.instagram);     
        System.out.println(st.instagram_root.left.key.instagram);       

        st.search_by_instagram("cachorro");
        st.search_by_instagram("alface");
        st.search_by_instagram("cavalo");

        st.list_by_priority();
        st.list_by_name();
        st.delete();
        st.list_by_priority();
        st.list_by_name();
    }
}