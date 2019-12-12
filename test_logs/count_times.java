import java.io.*;
import java.util.Scanner;

public class count_times {
    public static void main(String args[]) {
        try {
 	    File file = new File("/home/ubuntu/tomcat/bin/TJ_timings.txt");	     
            Scanner sc = new Scanner(file);
	    String input = "";
            long sum = 0;
            long i = 0;
            int d = 0;

            while (sc.hasNextLine()) {
                input = sc.nextLine();
                i = Integer.parseInt(input);
                sum += i;
                ++d;
                //System.out.println("i: " + i + ", d: " + d + ", sum: " + sum);
            }
            System.out.println("i: " + i + ", d: " + d + ", sum: " + sum);
            System.out.println("TJ AVG: " + sum / d);

            file = new File("/home/ubuntu/tomcat/bin/TS_timings.txt");
            sc = new Scanner(file);
            sum = 0;
            i = 0;
            d = 0;
             
            while (sc.hasNextLine()) {
                input = sc.nextLine();
                i = Integer.parseInt(input);
                sum += i;
                ++d;
                //System.out.println("i: " + i + ", d: " + d + ", sum: " + sum);
            }
            System.out.println("i: " + i + ", d: " + d + ", sum: " + sum);
            System.out.println("TS AVG: " + sum / d);
        } catch (IOException e) {
	    e.printStackTrace();
        }
    }
}
