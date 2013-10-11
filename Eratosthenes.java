
//package statement only needed by netbeans
//package eratosthenes;
import java.util.*;

/*
 * @author Nick Carlson
 */

public class Eratosthenes {

    //main driver that calls method to take input and compute result
    public static void main(String[] args) {
        getInputAndRunSearches();
    }
    
    //accepts input from user, then passes to sieve and brute-force search if input is acceptable
    public static void getInputAndRunSearches(){
        int limit = 0;
        int numPrimes1;
        int numPrimes2;
        boolean flag = true;
        Scanner reader = new Scanner(System.in);
        
        //get input from user
        //if input unacceptable, tell the user why not
        System.out.println("Please enter an integer greater than 1:");
        try{
        limit = reader.nextInt();
        }catch(InputMismatchException e){
            flag = false;
            System.out.println("You must enter an integer greater than 1.");
        }
        
        //if input is acceptable, run searches
        //if input unacceptable, tell the user why not
        if(flag == true){
            if(limit > 1){
                numPrimes1 = runSieve(limit);
                numPrimes2 = runBF(limit);
                //ensure that both searches yielded the same number of primes
                if(numPrimes1 == numPrimes2){
                    System.out.println("");
                    System.out.println("The same number of primes was found by both searches!");
                }else{
                    System.out.println("ERROR: The searches yielded different numbers of primes!");
                    System.exit(0);
                }
            }else{
                System.out.println(limit + " is an integer, but it is not greater than 1.");
            }
        }
    }
    
    //take accepted input and run the Sieve of Eratosthenes
    public static int runSieve(int limit){
        int numPrimes;
        
        //print header
        System.out.println("");
        System.out.println("Sieve of Eratosthenes:");
        
        //run sieve
        numPrimes = sieveOfEratosthenes(limit);
        
        //report count
        System.out.println("There are " + numPrimes + " prime numbers <= " + limit);
        
        //return number of primes for comparison
        return numPrimes;
    }
    
    //take accepted input and run the brute-force search
    public static int runBF(int limit){
        int numPrimes;
        
        //print header
        System.out.println("");
        System.out.println("Brute-Force Search:");
        
        //run brute-force search
        numPrimes = bfPrimes(limit);
        
        //report count
        System.out.println("There are " + numPrimes + " prime numbers <= " + limit);
        
        //return number of primes for comparison
        return numPrimes;
    }
    
    //accept an integer and test for primality, then return true if prime
    public static boolean primeTest(int num){
        //test with divisors between 2 and sqrt(num) to improve test speed
        int target = (int) Math.sqrt(num);
        for(int i = 2; i <= target; i++){
            if((num % i) == 0){
                return false;
            }
        }
        return true;
    }
    
    //compute the number of primes less than or equal to a user-specified limit
    //  with the Sieve of Eratosthenes
    public static int sieveOfEratosthenes(int limit){
        //start timer
        long startTime = System.nanoTime();
        
        //declarations
        boolean[] primes = new boolean[limit + 1];
        int numPrimes = 0;
        boolean primality;
        
        //start by assuming primality for integers >= 2
        //0 and 1 will stay false by default because they are not prime
        for(int i = 2; i <= limit; i++){
            primes[i] = true;
        }
        
        //mark non-prime integers within specified limit
        for(int i = 2; i <= Math.sqrt(limit); i++){
            if(primes[i] == true){
                //since i is prime, mark multiples of i as non-prime
                for(int j = i; i*j <= limit; j++){
                    primes[i*j] = false;
                }
            }
        }
        
        //all remaining non-marked numbers must be prime, so count them up
        for(int i = 2; i <= limit; i++){
            if(primes[i] == true){
                //if i is prime, increment # of primes
                numPrimes++;
            }
        }
        
        //SIEVE COMPLETE: stop timer, calculate duration, and report
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        double elapsedTime = (double)duration/1000000.0;
        System.out.println("The Sieve of Eratosthenes was completed in " + elapsedTime + " milliseconds.");
        
        //iterate through integers marked prime and test for primality
        for(int i = 2; i <= limit; i++){
            if(primes[i] == true){
                //test i for primality
                //if not prime, terminate program
                primality = primeTest(i);
                if(primality == false){
                    System.out.println("ERROR: A number has been incorrectly marked as prime by the Sieve of Eratosthenes!");
                    System.exit(0);
                }
            }
        }
        
        //return # of primes
        return numPrimes;
    }
    
    //a brute-force search to achieve the same results as the Sieve of Eratosthenes
    //compute the number of primes less than or equal to a user-specified limit
    public static int bfPrimes(int limit){
        //start timer
        long startTime = System.nanoTime();
        
        //declarations
        boolean[] primes = new boolean[limit + 1];
        int numPrimes = 0;
        boolean isPrime;
        boolean primality;
        int n;
        
        //test for divisors between 2 and sqrt(i)
        //this improves the time of brute-force search
        for(int i = 2; i <= limit; i++){
            isPrime = true;
            n = (int) Math.floor(Math.sqrt(i));
            for(int j = 2; j <= n; j++){
                if(i % j == 0){
                    isPrime = false;
                    break;
                }
            }
            if(isPrime){
                primes[i] = true;
            }
        }
        
        //all remaining non-marked integers must be prime, so count them up
        for(int i = 2; i <= limit; i++){
            if(primes[i] == true){
                //if i is prime, increment # of primes
                numPrimes++;
            }
        }
        
        //SEARCH COMPLETE: stop timer, calculate duration, and report
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        double elapsedTime = (double)duration/1000000.0;
        System.out.println("The brute-force search was completed in " + elapsedTime + " milliseconds.");
        
        //iterate through integers marked prime and test for primality
        for(int i = 2; i <= limit; i++){
            if(primes[i] == true){
                //test i for primality
                //if not prime, terminate program
                primality = primeTest(i);
                if(primality == false){
                    System.out.println("ERROR: A number has been incorrectly marked as prime by the brute-force search!");
                    System.exit(0);
                }
            }
        }
        
        //return # of primes
        return numPrimes;
    }
    
}
