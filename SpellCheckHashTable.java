import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

//Name: Benjamin Lerner
/**
 * Probing table implementation of hash tables.
 * Note that all "matching" is based on the equals method.
 * @author Mark Allen Weiss
 */
public class SpellCheckHashTable
{
    /**
     * Construct the hash table.
     */
    public SpellCheckHashTable( )
    {
        this( DEFAULT_TABLE_SIZE );
    }

    /**
     * Construct the hash table.
     * @param size the approximate initial size.
     */
    public SpellCheckHashTable( int size )
    {
        allocateArray( size );
        doClear( );
    }

    /**
     * Insert into the hash table. If the item is
     * already present, do nothing.
     * @param x the item to insert.
     */
    public boolean insert( String x )
    {
        // Insert x as active
        int currentPos = findPos( x );
        if( isActive( currentPos ) )
            return false;

        array[ currentPos ] = new HashEntry( x, true );
        theSize++;

        // Rehash; see Section 5.5
        if( ++occupied > array.length / 2 )
            rehash( );

        return true;
    }

    /**
     * Expand the hash table.
     */
    private void rehash( )
    {
        HashEntry [ ] oldArray = array;

        // Create a new double-sized, empty table
        allocateArray( 2 * oldArray.length );
        occupied = 0;
        theSize = 0;

        // Copy table over
        for( HashEntry entry : oldArray )
            if( entry != null && entry.isActive )
                insert( entry.element );
    }

    /**
     * Method that performs quadratic probing resolution.
     * @param x the item to search for.
     * @return the position where the search terminates.
     */
    private int findPos( String x )
    {
        int offset = 1;
        int currentPos = myhash( x );

        while( array[ currentPos ] != null &&
                !array[ currentPos ].element.equals( x ) )
        {
            currentPos += offset;  // Compute ith probe
            offset += 2;
            if( currentPos >= array.length )
                currentPos -= array.length;
        }

        return currentPos;
    }



    /**
     * Remove from the hash table.
     * @param x the item to remove.
     * @return true if item removed
     */
    public boolean remove( String x )
    {
        int currentPos = findPos( x );
        if( isActive( currentPos ) )
        {
            array[ currentPos ].isActive = false;
            theSize--;
            return true;
        }
        else
            return false;
    }

    /**
     * Get current size.
     * @return the size.
     */
    public int size( )
    {
        return theSize;
    }

    /**
     * Get length of internal table.
     * @return the size.
     */
    public int capacity( )
    {
        return array.length;
    }

    /**
     * Find an item in the hash table.
     * @param x the item to search for.
     * @return the matching item.
     */
    public boolean contains( String x )
    {
        int currentPos = findPos( x );
        return isActive( currentPos );
    }

    /**
     * Return true if currentPos exists and is active.
     * @param currentPos the result of a call to findPos.
     * @return true if currentPos is active.
     */
    private boolean isActive( int currentPos )
    {
        return array[ currentPos ] != null && array[ currentPos ].isActive;
    }

    /**
     * Make the hash table logically empty.
     */
    public void makeEmpty( )
    {
        doClear( );
    }

    private void doClear( )
    {
        occupied = 0;
        for( int i = 0; i < array.length; i++ )
            array[ i ] = null;
    }

    private int myhash( String x )
    {
        int hashVal = hashing(x, array.length);

        hashVal %= array.length;
        if( hashVal < 0 )
            hashVal += array.length;

        return hashVal;
    }



    private static class HashEntry
    {
        public String  element;   // the element
        public boolean isActive;  // false if marked deleted

        public HashEntry( String e )
        {
            this( e, true );
        }

        public HashEntry( String e, boolean i )
        {
            element  = e;
            isActive = i;
        }
    }

    //uses a hash function featuring a prime number to generate keys and tokens
    public int hashing (String key, int tableSize) {
        int hashVal = 0;
        for (int i = 0; i < key.length(); i++) {
            hashVal = 41 * hashVal + key.charAt(i);
        }
        hashVal %= tableSize;
        if (hashVal < 0) {
            hashVal += tableSize;
        }
        return hashVal;
    }

    //the size of the initial table
    private static final int DEFAULT_TABLE_SIZE = 17;

    private HashEntry [ ] array; 		  // The array of elements
    private int occupied;                 // The number of occupied cells
    private int theSize;                  // Current size

    /**
     * Internal method to allocate array.
     * @param arraySize the size of the array.
     */
    private void allocateArray( int arraySize )
    {
        array = new HashEntry[ nextPrime( arraySize ) ];
    }

    /**
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    private static int nextPrime( int n )
    {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime( int n )
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }

    public static void main(String[] args) throws IOException
    {

        // Takes three parameters: a dictionary, a dictionary, and then text that is to be spellchecked
        SpellCheckHashTable H = new SpellCheckHashTable( );
        FileInputStream fis = null;
        BufferedReader reader = null;
        FileInputStream fisDict = null;
        BufferedReader readDict = null;
        FileInputStream fisDict2 = null;
        BufferedReader readDict2 = null;

        //http://www.avajava.com/tutorials/lessons/how-do-i-read-a-string-from-a-file-line-by-line.html
        //reads in line-by-line, tests if each line in the document is a palindrome, prints if it is or not
        try {
            //first argument is the first dictionary
            fisDict = new FileInputStream(args[0]);
            readDict = new BufferedReader(new InputStreamReader(fisDict));
            String dictLine = readDict.readLine();
            while (dictLine != null) {
                H.insert(dictLine);
                dictLine = readDict.readLine();
            }

            //second argument is the second dictionary
            fisDict2 = new FileInputStream(args[1]);
            readDict2 = new BufferedReader(new InputStreamReader(fisDict2));
            String dictLine2 = readDict2.readLine();
            while (dictLine2 != null) {
                H.insert(dictLine2);
                dictLine2 = readDict2.readLine();
            };

            //third argument is the input to be tested
            fis = new FileInputStream(args[2]);
            reader = new BufferedReader(new InputStreamReader(fis));
            String line = reader.readLine();
            int counter = 1;
            while (line != null) {
                //splits the line up
                String[] salt = line.split(" ");
                for (String skull : salt) {
                    //makes sure it's only letters and apostrophes
                    String s = skull.replaceAll(("[^(a-zA-Z||')]" ),  "");
                    if (!H.contains(s) && !s.equals(" ") && !s.equals("")) {
                        System.out.println("Misspelling in line: " + counter + ""
                                + ". Misspelled word: '" + s + "'. Possible corrections include: ");
                        //outputs possible correctly spelled words ONLY if the word is misspelled
                        H.spellCheck(s);
                    }
                }
                line = reader.readLine();
                //keeps track of the lines
                ++counter;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //readers for the files
                readDict.close();
                fisDict.close();
                readDict2.close();
                fisDict.close();
                reader.close();
                fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void spellCheck(String s) {
        ArrayList<String> gutArrList = new ArrayList<>();
        String[] alphabetPlus = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n",
            "o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F",
            "G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","'"};


        //checks if you can add a letter and make a word
        for (int i = 0; i <= s.length(); ++i) {
            for (int j = 0; j < alphabetPlus.length; ++j) {
                String addTest = s.substring(0, i) + alphabetPlus[j] + s.substring(i, s.length());
                if (this.contains(addTest) && !gutArrList.contains(addTest)) {
                    gutArrList.add(addTest);
                }
            }
        }

        //checks if you can remove a letter and make a word
        for (int i = 0; i < s.length(); ++i) {
            String removeTest = s.substring(0, i) + s.substring((i + 1), s.length());
            if (this.contains(removeTest) && !gutArrList.contains(removeTest)) {
                gutArrList.add(removeTest);
            }
        }

        //checks if you can swap adjacent letters
        for (int i = 0; i <= s.length(); ++i) {
            if (i + 2 < s.length()) {
                String swap1 = s.substring(0, i) + s.substring(i+1, i+2) + s.substring(i, i+1) + s.substring(i+2, s.length());
                if (this.contains(swap1) && !gutArrList.contains(swap1)) {
                    gutArrList.add(swap1);
                }
            }
            if (i == s.length() - 1) {
                String swap2 = s.substring(0, i - 1) + s.substring(i, i + 1) + s.substring(i - 1, i);
                if (this.contains(swap2) && !gutArrList.contains(swap2)) {
                    gutArrList.add(swap2);
                }
            }
        }

        //prints possible properly spelled words
        for (int i = 0; i < gutArrList.size(); ++i) {
            System.out.println(gutArrList.get(i));
        }
    }
}
