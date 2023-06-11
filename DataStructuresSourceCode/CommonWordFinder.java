
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.WeakHashMap;

/**
 * @DATE: 12/19/2022
 * @author adrianharischand
 * @uni: ah3927
 *
 */

public class CommonWordFinder {

    /**
     * Method : Sorts the all the values in  the Entry array in decreasing order.
     * Resources: Dr. B's class notes on Advance sorting
     * @param array Entry array whose keys are already sorted
     * @param scratch empty array of the same length of array
     * @param low     int lower bound (aka the left index)
     * @param high   integer of the upper bound (aka right index)
     * @return sorted array of Entry type where the values of the entry is sorted in  decreasing order
     */
    private static Entry[] mergesortHelper(Entry[] array, Entry[] scratch, int low, int
            high) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            mergesortHelper(array, scratch, low, mid);
            mergesortHelper(array, scratch, mid + 1, high);
            int i = low, j = mid + 1;
            for (int k = low; k <= high; k++) {
                // Comparing the left value to the right value using hashCode which allows us to compare the values
                // Because I want to sort it in decreasing order I had to check if left value is >= to the right value
                if (i <= mid && (j > high || array[i].value.hashCode() >= array[j].value.hashCode())) {
                      scratch[k] = array[i++];
                } else {
                    scratch[k] = array[j++];
                }
            }
            for (int k = low; k <= high; k++) {
                array[k]= scratch[k];
            }
        }
        return array;
    }

    /**
     * @bst Method: It reads through the input file character by character. It parses the input file by following the
     * simple rules given by Dr. B and everytime it parses a word it will insert the word in the BST Map with the
     * word's frequency. If the word isn't in the map, I will add it to the BST Map with a frequency of 1  but if it
     * is already in the BST Map then I will add it to the BST Map with its current frequency +1. After every word and its
     * frequency has been added then I will iterate through the BST Map using the Iterator and add each key-value pair to
     * my Entry array. I know that my Entry array's key are sorted because BST Map is a sorted Binary tree or BT Map based on key.
     * Finally, I sorted the values in my  Entry array  using my mergesortHelper() method which returns a sorted Entry array
     * in terms of most common word to least common word where words are in lexicographical order.
     * @param r BufferedReader object which allows for reading through the file character by character
     * @return  A sorted Entry array in terms of most common word to least common word where words are in lexicographical order
     * @throws IOException
     *  How to use a buffer reader : I used https://www.geeksforgeeks.org/bufferedreader-read-method-in-java-with-examples/
     *  Source: https://www.geeksforgeeks.org/bufferedreader-read-method-in-java-with-examples/
     *
     * Getting the ascii values :
     * Source : https://www.cs.cmu.edu/~pattis/15-1XX/common/handouts/ascii.html
     */
    public static Entry [] bst (BufferedReader r) throws IOException {
        MyMap<String, Integer> bst_Map = new BSTMap<>();
        String str = "";

        while (r.ready()){
            int ascii_value = r.read();
            char getChar = (char) ascii_value;
            // check if it is a legal character
            if ((ascii_value >= 65 && ascii_value <=90) || ascii_value == 39
                    || (ascii_value >= 97 && ascii_value <= 122) || ascii_value == 45){
                // if it is a hyphen at the beginning of a word don't add it
                if (str.length() == 0 && ascii_value == 45 ){
                    str = str +"";
                }
                else{
                    str += getChar;
                }
            }
            // adding a word when we encounter a whitespace, line break, punctuation( . ! ?)
            if ( ascii_value == 32 || ascii_value == 10 ||
                    ascii_value == 13 || ascii_value ==46 || ascii_value ==33 || ascii_value == 63)
            {
                str = str.toLowerCase();
                // cannot put an empty string as a word
                // Check if word doesn't exist in the BST Map
                if (bst_Map.get(str) == null && str.length() !=0){
                    bst_Map.put(str, 1);
                    str ="";
                }
                // cannot put an empty string as a word
                // Check if word exist in the BST MaP
                else if (bst_Map.get(str) != null && str.length() !=0) {
                    bst_Map.put(str,bst_Map.get(str)+1);
                    str ="";
                }
            }
        }

        Iterator<Entry<String,Integer>> bstItr = bst_Map.iterator();
        Entry [] output  =  new Entry[bst_Map.size()];
        int index = 0;
        // Iterating through BST Map and populating my Entry array with elements of the BST Map
        while (bstItr.hasNext()){
            Entry my = bstItr.next();
            output[index] = my;
            index++;
        }

        //  making a copy of my Entry array
        Entry[] arrayCopy = new Entry[output.length],
                scratch =  new Entry[output.length];
        System.arraycopy(output, 0, arrayCopy, 0, output.length);

        // Sorting my Entry array
        Entry [] fin = mergesortHelper(arrayCopy,scratch,0, output.length-1) ;

        return fin;

    }



    /**
     * @avl Method: It reads through the input file character by character. It parses the input file by following the
     * simple rules given by Dr. B and everytime it parses a word it will insert the word in the AVLTree Map with the
     * word's frequency. If the word isn't in the map, I will add it to the AVLTree Map with a frequency of 1  but if it
     * is already in the AVLTree Map then I will add it to the AVLTree Map with its current frequency +1. After every word and its
     * frequency has been added then I will iterate through the AVLTree Map using the Iterator and add each key-value pair to
     * my Entry array. I know that my Entry array's key are sorted because AVLTree Map is a sorted Binary tree or BT Map based on key.
     * Finally, I sorted the values in my  Entry array  using my mergesortHelper() method which returns a sorted Entry array
     * in terms of most common word to least common word where words are in lexicographical order.
     * @param r BufferedReader object which allows for reading through the file character by character
     * @return  A sorted Entry array in terms of most common word to least common word where words are in lexicographical order
     * @throws IOException
     *    How to use a buffer reader : I used https://www.geeksforgeeks.org/bufferedreader-read-method-in-java-with-examples/
     *    Source: https://www.geeksforgeeks.org/bufferedreader-read-method-in-java-with-examples/
     * Getting the ascii values :
     * Source : https://www.cs.cmu.edu/~pattis/15-1XX/common/handouts/ascii.html
     */
    public static Entry [] avl (BufferedReader r) throws IOException {
        MyMap<String, Integer> avl_Map = new AVLTreeMap<>();
        String str = "";
        while (r.ready()){
            int ascii_value = r.read();
            char getChar = (char) ascii_value;
            // check if it is a legal character
            if ((ascii_value >= 65 && ascii_value <=90) || ascii_value == 39
                    || (ascii_value >= 97 && ascii_value <= 122) || ascii_value == 45 ){
                // if it is a hyphen at the beginning of a word don't add it
                if (str.length() == 0 && ascii_value == 45 ){
                    str = str +"";
                }
                else{
                    str += getChar;
                }
            }

            // adding a word when we encounter a whitespace, line break, punctuation( . ! ?)
            if ( ascii_value == 32 || ascii_value == 10 ||
                    ascii_value == 13 || ascii_value ==46 || ascii_value ==33 || ascii_value == 63)

            {
                str = str.toLowerCase();
                // cannot put an empty string as a word
                // Check if word doesn't exist in the AVLTree Map
                if (avl_Map.get(str) == null && str.length() !=0){

                    avl_Map.put(str, 1);
                    str ="";
                }
                // cannot put an empty string as a word
                // Check if word exist in the AVLTree Map
                else if (avl_Map.get(str) != null && str.length() !=0) {
                    avl_Map.put(str,avl_Map.get(str)+1);
                    str ="";
                }
            }


        }

        Iterator<Entry<String,Integer>> avlItr = avl_Map.iterator();
        Entry [] output_avl  =  new Entry[avl_Map.size()];
        int index = 0;
        // Iterating through AVLTree Map and populating my Entry array with elements of the AVLTree Map
        while (avlItr.hasNext()){
            Entry my_avl = avlItr.next();
            output_avl[index] = my_avl;
            index++;
        }

        //  making a copy of my Entry array
        Entry[] arrayCopy = new Entry[output_avl.length],
                scratch =  new Entry[output_avl.length];
        System.arraycopy(output_avl, 0, arrayCopy, 0, output_avl.length);
        // Sorting my Entry array
        Entry [] fin_avl = mergesortHelper(arrayCopy,scratch,0, output_avl.length-1) ;

        return fin_avl;
    }





    /**
     * @hash Method: It reads through the input file character by character. It parses the input file by following the
     * simple rules given by Dr. B and everytime it parses a word it will insert the word in the Hash Map with the
     * word's frequency. If the word isn't in the map, I will add it to the Hash Map with a frequency of 1  but if it
     * is already in the Hash Map then I will add it to the Hash Map with its current frequency +1. After every word and its
     * frequency has been added then I will iterate through the Hash Map using the Iterator and add each key-value pair to
     * my Entry array. I also iterate through HashMap and extract all of its keys and put it in an array of type String.
     * This array of Key (String) I sorted by using the Array.sort() method. I then created an empty Entry array of size (HashMap.size())
     * and populate it with the sorted Keys and their corresponding values.
     * Finally, I sorted the values in my new Entry array with sorted keys, using my mergesortHelper() method which returns a sorted Entry array
     * in terms of most common word to least common word where words are in lexicographical order.
     * @param r BufferedReader object which allows for reading through the file character by character
     * @return  A sorted Entry array in terms of most common word to least common word where words are in lexicographical order
     * @throws IOException
     *  How to use a buffer reader : I used https://www.geeksforgeeks.org/bufferedreader-read-method-in-java-with-examples/
     *  Source: https://www.geeksforgeeks.org/bufferedreader-read-method-in-java-with-examples/
     * Getting the ascii values : 
     * Source : https://www.cs.cmu.edu/~pattis/15-1XX/common/handouts/ascii.html
     */

    public static Entry [] hash (BufferedReader r) throws IOException {

        MyMap <String, Integer> hash_Map = new MyHashMap<>();
        String str = "";
        while (r.ready()){
            int ascii_value = r.read();
            char getChar = (char) ascii_value;
            // check if it is a legal character
            if ( (ascii_value >= 65 && ascii_value <=90) || ascii_value == 39
                    || (ascii_value >= 97 && ascii_value <= 122) || ascii_value == 45 ){
                // if it is a hyphen at the beginning of a word don't add it
                if (str.length() == 0 && ascii_value == 45 ){
                    str = str + "";
                }
                else{
                    str += getChar;
                }
            }

            // adding a word when we encounter a whitespace, line break, punctuation( . ! ?)
            if ( ascii_value == 32 || ascii_value == 10 || ascii_value == 13
                    || ascii_value ==46 || ascii_value ==33 || ascii_value == 63) {
                str = str.toLowerCase();
                // cannot put an empty string as a word
                // Check if word doesn't exist in the Hash Map
                if (hash_Map.get(str) == null && str.length() !=0){
                    hash_Map.put(str, 1);
                    str ="";
                }
                // cannot put an empty string as a word
                // Check if word exist in the Hash Map
                else if (hash_Map.get(str) != null && str.length() !=0) {
                    hash_Map.put(str,hash_Map.get(str)+1);
                    str ="";
                }
            }


        }
        Iterator<Entry<String,Integer>> hashItr = hash_Map.iterator();
        // array of keys
        String[] sorted_key  =  new String[hash_Map.size()];
        Entry [] output_hash =  new Entry[hash_Map.size()];
        int index = 0;
        // Iterating through Hash Map and populating my Entry array with elements of the Hash Map
        while (hashItr.hasNext()){
            Entry my_hash = hashItr.next();
            output_hash[index] = my_hash;
            sorted_key[index] = (String) my_hash.key;
            index++;
        }

        // sorting the Array of Keys
        Arrays.sort(sorted_key);

        Entry[] combine = new Entry[output_hash.length];
        // populate my Entry array with the sorted keys and their corresponding values
        for (int c = 0; c < hash_Map.size(); c++){
             combine[c] = new Entry<>(sorted_key[c], hash_Map.get(sorted_key[c]));
        }

        //  making a copy of my Entry array
        Entry[] arrayCopy_hash = new Entry[output_hash.length],
                scratch =  new Entry[output_hash.length];
        System.arraycopy(combine, 0, arrayCopy_hash, 0, output_hash.length);

        // Sorting Entry array
        Entry [] fin_hash = mergesortHelper(arrayCopy_hash, scratch,0, output_hash.length-1);

        return fin_hash;

    }


    /**
     * How to read A buffer reader :
     * Source I used : https://www.geeksforgeeks.org/bufferedreader-read-method-in-java-with-examples/
     * @param args
     */

    public static void main (String [] args){
        int limit = 10;
        File myFile = new File (args[0]);
        FileReader read_my_file ;
        BufferedReader buff_read;

        int len = args.length;
        // check my arguments length
        if (len!= 2 && len != 3){
            System.err.println("Usage: java CommonWordFinder <filename> <bst|avl|hash> [limit]");
            System.exit(1);
        }

        // check if I can open a file
        if (!myFile.exists()){
            System.err.println("Error: Cannot open file '" + args[0] + "' for input.");
            System.exit(1);
        }

        // checks if I can read my file
        try{
            read_my_file = new FileReader(args[0]);
            buff_read = new BufferedReader(read_my_file);
            while (buff_read.readLine() != null) {
                buff_read.readLine();
            }
        }
        catch(IOException e){
            System.err.println("Error: An I/O error occurred reading '" + args[0] + "'.");
        }
        // checks if user enters valid data structure
        if ( !args[1].equals("bst") && !args[1].equals("avl") && !args[1].equals("hash")){
            System.err.println("Error: Invalid data structure '" + args[1] + "' received.");
            System.exit(1);
        }

        // checks if there is a 3rd argument and if that third argument is valid
        if (args.length == 3) {
            try {
                Integer.parseInt(args[2]);

            }
            catch (NumberFormatException e) {
                System.err.println("Error: Invalid limit '" + args[2] + "' received.");
                System.exit(1);
            }
            if (Integer.parseInt(args[2]) > 0){
            limit = Integer.parseInt((args[2]));
            }
            else {
                System.err.println("Error: Invalid limit '" + args[2] + "' received.");
                System.exit(1);
            }
        }

        /** checks if user chose "bst" data structure  and if yes it call the
         * bst() method which takes in the bufferedReader object and outputs a sorted Entry array.
         * If the limit entered by the user is greater than the size of the sorted Entry array then it will
         * print every entry in the array but before printing an entry , first I need to print
         * the right number of white spaces (if it is single digit then it will have floor of log base10( total number of unique words)
         * white space, then if it is double digit it will have floor of log base10( total number of unique words)-1 white space, this
         * idea or logic flows according to the number of unique words we have and how many spaces is needed) ,
         * before the number of the word  and the period then
         * print the words and then print the (length of max word - current word length +1) number of
         * whitespace (according to the length of each word )and then print the frequency of the word.
         * As for when the limit is less than the array the size of the sorted Entry array then it will
         * print the limit amount of entry in the array but before printing an entry , first I need to print
         * the right number of white spaces (if it is single digit then it will have floor of log base10( limit)
         * white space, then if it is double digit it will have (floor of log base10(limit))-1 white space, this
         * idea or logic flows according to the number of unique words we have and how many spaces is needed) ,
         * before the number of the word  and the period then
         * print the words and then print the (length of max word  for that range of words - current word length +1) number of
         * whitespace (according to the length of each word )and then print the frequency of the word.
         */
        if (args[1].equals("bst")){
            try{
                read_my_file = new FileReader(args[0]);
                buff_read = new BufferedReader(read_my_file);
                Entry [] bst_output = CommonWordFinder.bst(buff_read);
                System.out.print("Total unique words: "+bst_output.length +System.lineSeparator());
                int max_word = 0, max_limit = 0;
                int focus = (int) Math.log10(bst_output.length),  focus_Limit = (int) Math.log10(limit);
                int hold = focus, hold_limit= focus_Limit;
                int start = 0;
                String focus_space ="";
                // finding the longest word and save it's length
                for (int i =0; i < bst_output.length; i++){
                    String word = (String)(bst_output[i].key);
                    if (word.length() >= max_word){
                        max_word = word.length();

                    }
                }

                // check if limit is greater than the number of unique words
                if (limit > bst_output.length){
                    for (int i = 0; i < bst_output.length; i++){
                        String space = "";
                        String curr = (String)(bst_output[i].key);
                        // getting the string with right amount of whitespaces between word and its frequency
                        for (int j = 0; j < (max_word-curr.length()+ 1); j++){
                            space += " ";
                        }
                        // checking when we need to change the number of whitespaces which is based on the digits change of place
                        if (i+1 == Math.pow(10, start)){
                            focus_space = "";
                            for (int f = 0; f < hold ; f++){
                                focus_space += " ";
                            }
                            hold--;
                            start++;
                        }
                        // adding the right amount of spaces according to the number of the word.
                       if ( i+1 < Math.pow(10,start)  ){
                               System.out.print( focus_space + (i+1) + ". " + bst_output[i].key + space + bst_output[i].value + System.lineSeparator());
                        }
                    }
                }
                else {
                    // finding the longest word and save its length
                    for (int i =0; i < limit; i++){
                        String word = (String)(bst_output[i].key);
                        if (word.length() >= max_limit){
                            max_limit = word.length();

                        }
                    }
                    for (int i = 0; i < limit; i++){
                        String space = "";
                        String curr = (String)(bst_output[i].key);
                        // getting the string with right amount of whitespaces between word and its frequency
                        for (int j = 0; j < (max_limit-curr.length()+ 1); j++){
                            space += " ";
                        }
                        // checking when we need to change the number of whitespaces which is based on the digits change of place
                        if (i+1 == Math.pow(10, start)){
                            focus_space = "";
                            for (int f = 0; f < hold_limit ; f++){
                                focus_space += " ";
                            }
                            hold_limit--;
                            start++;
                        }
                        // adding the right amount of spaces according to the number of the word.
                        if ( i+1 < Math.pow(10,start)  ){
                                System.out.print( focus_space + (i+1) + ". " + bst_output[i].key + space + bst_output[i].value + System.lineSeparator());
                        }
                    }
                }

            }
            catch(IOException e){
                System.err.println("Error: An I/O error occurred reading '" + args[0] + "'.");
            }

        }

        /** checks if user chose "avl" data structure  and if yes it call the
         * avl() method which takes in the bufferedReader object and outputs a sorted Entry array.
         * If the limit entered by the user is greater than the size of the sorted Entry array then it will
         * print every entry in the array but before printing an entry , first I need to print
         * the right number of white spaces (if it is single digit then it will have floor of log base10( total number of unique words)
         * white space, then if it is double digit it will have floor of log base10( total number of unique words)-1 white space, this
         * idea or logic flows according to the number of unique words we have and how many spaces is needed) ,
         * before the number of the word  and the period then
         * print the words and then print the (length of max word - current word length +1) number of
         * whitespace (according to the length of each word )and then print the frequency of the word.
         * As for when the limit is less than the array the size of the sorted Entry array then it will
         * print the limit amount of entry in the array but before printing an entry , first I need to print
         * the right number of white spaces (if it is single digit then it will have floor of log base10( limit)
         * white space, then if it is double digit it will have (floor of log base10(limit))-1 white space, this
         * idea or logic flows according to the number of unique words we have and how many spaces is needed) ,
         * before the number of the word  and the period then
         * print the words and then print the (length of max word  for that range of words - current word length +1) number of
         * whitespace (according to the length of each word )and then print the frequency of the word.
         */

        else if (args[1].equals("avl")){
            try{
                read_my_file = new FileReader(args[0]);
                buff_read = new BufferedReader(read_my_file);
                Entry [] avl_output = CommonWordFinder.avl(buff_read);
                System.out.print("Total unique words: "+avl_output.length +System.lineSeparator());
                int max_word = 0, max_limit = 0;
                int focus_avl = (int) Math.log10(avl_output.length),  focus_Limit_avl = (int) Math.log10(limit);
                int hold_avl = focus_avl, hold_limit_avl= focus_Limit_avl;
                int start = 0;
                String focus_space = "";
                // finding the longest word and save it's length
                for (int i =0; i < avl_output.length; i++){
                    String word = (String)(avl_output[i].key);
                    if (word.length() >= max_word){
                        max_word = word.length();

                    }
                }

                // check if limit is greater than the number of unique words
                if (limit > avl_output.length){
                    for (int i = 0; i < avl_output.length; i++){
                        String space = "";
                        String curr = (String)(avl_output[i].key);
                        // getting the string with right amount  of whitespaces between word and its frequency
                        for (int j = 0; j < (max_word-curr.length()+ 1); j++){
                            space += " ";
                        }
                        // checking when we need to change the number of whitespaces which is based on the digits change of place
                        if (i+1 == Math.pow(10, start)){
                            focus_space = "";
                            for (int f = 0; f < hold_avl ; f++){
                                focus_space += " ";
                            }
                            hold_avl--;
                            start++;
                        }
                        // adding the right amount of spaces according to the number of the word.
                        if ( i+1 < Math.pow(10,start)  ){
                                System.out.print( focus_space + (i+1) + ". " + avl_output[i].key + space + avl_output[i].value +System.lineSeparator());
                        }

                    }
                }
                else {
                    // finding the longest word and save it's length
                    for (int i =0; i < limit; i++){
                        String word = (String)(avl_output[i].key);
                        if (word.length() >= max_limit){
                            max_limit = word.length();

                        }
                    }

                    for (int i = 0; i < limit; i++){
                        String space = "";
                        String curr = (String)(avl_output[i].key);
                        // getting the string with right amount  of whitespaces between word and its frequency
                        for (int j = 0; j < (max_limit-curr.length()+ 1); j++){
                            space += " ";
                        }
                        // checking when we need to change the number of whitespaces which is based on the digits change of place
                        if (i+1 == Math.pow(10, start)){
                            focus_space = "";
                            for (int f = 0; f < hold_limit_avl ; f++){
                                focus_space += " ";
                            }
                            hold_limit_avl--;
                            start++;
                        }
                        // adding the right amount of spaces according to the number of the word.
                        if ( i+1 < Math.pow(10,start)  ){
                            System.out.print( focus_space + (i+1) + ". " + avl_output[i].key + space + avl_output[i].value +System.lineSeparator());
                        }
                    }
                }


            }
            catch(IOException e){
                System.err.println("Error: An I/O error occurred reading '" + args[0] + "'.");
            }

        }

        /** checks if user chose "hash" data structure  and if yes it call the
         * hash() method which takes in the bufferedReader object and outputs a sorted Entry array.
         * If the limit entered by the user is greater than the size of the sorted Entry array then it will
         * print every entry in the array but before printing an entry , first I need to print
         * the right number of white spaces (if it is single digit then it will have floor of log base10( total number of unique words)
         * white space, then if it is double digit it will have floor of log base10( total number of unique words)-1 white space, this
         * idea or logic flows according to the number of unique words we have and how many spaces is needed) ,
         * before the number of the word  and the period then
         * print the words and then print the (length of max word - current word length +1) number of
         * whitespace (according to the length of each word )and then print the frequency of the word.
         * As for when the limit is less than the array the size of the sorted Entry array then it will
         * print the limit amount of entry in the array but before printing an entry , first I need to print
         * the right number of white spaces (if it is single digit then it will have floor of log base10( limit)
         * white space, then if it is double digit it will have (floor of log base10(limit))-1 white space, this
         * idea or logic flows according to the number of unique words we have and how many spaces is needed) ,
         * before the number of the word  and the period then
         * print the words and then print the (length of max word  for that range of words - current word length +1) number of
         * whitespace (according to the length of each word )and then print the frequency of the word.
         */
        else if (args[1].equals("hash")){
            try{
                read_my_file = new FileReader(args[0]);
                buff_read = new BufferedReader(read_my_file);
                Entry [] hash_output = CommonWordFinder.hash(buff_read);;
                System.out.print("Total unique words: "+hash_output.length + System.lineSeparator());
                int max_word = 0, max_limit = 0;
                int focus_hash = (int) Math.log10(hash_output.length),  focus_Limit_hash = (int) Math.log10(limit);
                int hold_hash = focus_hash, hold_limit_hash= focus_Limit_hash;
                int start = 0;
                String focus_space = "";
                // finding the longest word and save it's length
                for (int i =0; i < hash_output.length; i++){
                    String word = (String)(hash_output[i].key);
                    if (word.length() >= max_word){
                        max_word = word.length();
                    }
                }
                // check if limit is greater than the number of unique words
                if (limit > hash_output.length){
                    for (int i = 0; i < hash_output.length; i++){
                        String space = "";
                        String curr = (String)(hash_output[i].key);
                        // getting the string with right amount  of whitespaces between word and its frequency
                        for (int j = 0; j < (max_word-curr.length()+ 1); j++){
                            space += " ";
                        }
                        // checking when we need to change the number of whitespaces which is based on the digits change of place
                        if (i+1 == Math.pow(10, start)){
                            focus_space = "";
                            for (int f = 0; f < hold_hash ; f++){
                                focus_space += " ";
                            }
                            hold_hash--;
                            start++;
                        }
                        // adding the right amount of spaces according to the number of the word.
                        if ( i+1 < Math.pow(10,start)  ){
                            System.out.print(focus_space + (i + 1) + ". " + hash_output[i].key + space + hash_output[i].value + System.lineSeparator());
                        }
                    }
                }
                else {
                    // finding the longest word and save it's length
                    for (int i =0; i < limit; i++){
                        String word = (String)(hash_output[i].key);
                        if (word.length() >= max_limit){
                            max_limit = word.length();
                        }
                    }
                    for (int i = 0; i < limit; i++){
                        String space = "";
                        String curr = (String)(hash_output[i].key);
                        // getting the string with right amount  of whitespaces between word and its frequency
                        for (int j = 0; j < (max_limit-curr.length()+ 1); j++){
                            space += " ";
                        }
                        // checking when we need to change the number of whitespaces which is based on the digits change of place
                        if (i+1 == Math.pow(10, start)){
                            focus_space = "";
                            for (int f = 0; f < hold_limit_hash ; f++){
                                focus_space += " ";
                            }
                            hold_limit_hash--;
                            start++;
                        }
                        // adding the right amount of spaces according to the number of the word.
                        if ( i+1 < Math.pow(10,start)  ) {
                            System.out.print(focus_space + (i + 1) + ". " + hash_output[i].key + space + hash_output[i].value + System.lineSeparator());
                        }
                    }
                }
            }
            catch(IOException e){
                System.err.println("Error: An I/O error occurred reading '" + args[0] + "'.");
            }

        }

    }
}
