import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;



//JFileChooser to choose which file to run with


/**
 * @author 24rossilli
 * @version 12/2/22
 * Base converter that takes a number with a certain base and converts it to a number with a different base
 */
public class BaseConverter {
    private final String DIGITS = "0123456789ABCDEF";

    /**
     * Convert a String num in fromBase to base-10 int.
     *
     * @param num      the original number
     * @param fromBase the original from base
     * @return a base-10 int of num base fromBase
     */
    public int strToInt(String num, String fromBase) {
        int value = 0, exp = 0;
        for (int i = num.length() - 1; i >= 0; i--) {
            value += DIGITS.indexOf(num.charAt(i)) * Math.pow(Integer.parseInt(fromBase), exp);
            exp++;
        }
        /*
        start at the right most digit of num
        run a loop for x or i digits of num -- backwards
            pull out the character at index i
            find the indexOf that character in DIGITS
            value += indexOf character * Math.pow(fromBase, exp);
            exp ++
         */
        return value;
    }

    /**
     * @param num    the original number
     * @param toBase the base you want to convert to
     * @return returns the converted number or 0
     */
    public String intToStr(int num, int toBase) {
        if (num == 0)
            return "0";
        String toNum = new String();
        while (num > 0) {
            toNum = DIGITS.charAt(num % toBase) + toNum;
            num /= toBase;
        }
        return toNum;
    }

    /**
     * Method that take data from a file and converts it to a new base and writes it into a new file and onto the screen
     */
    public void inputConvertPrintWrite() {
        Scanner in = null;
        PrintWriter out = null;
        // help from https://docs.oracle.com/javase/7/docs/api/javax/swing/JFileChooser.html
        // https://www.geeksforgeeks.org/java-swing-jfilechooser/#:~:text=JFileChooser%20is%20a%20part%20of,%2C%20panels%2C%20dialogs%2C%20etc%20.
        // http://www.java2s.com/Tutorial/Java/0240__Swing/UsingJFileChooser.htm
        // https://mkyong.com/swing/java-swing-jfilechooser-example/
        // https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
        // ryan aided a tiny bit with this
        File HereIAm = new File("datafiles");
        JFileChooser j = new JFileChooser(HereIAm);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "dat & txt files", "dat", "txt");
        j.setFileFilter(filter);
        Component parent = null;
        int returnVal = j.showOpenDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                in = new Scanner(new File(j.getSelectedFile().toURI()));
                out = new PrintWriter(new File("datafiles/converted.dat"));
                String[] line;
                String output;
                while (in.hasNext()) {
                    line = in.nextLine().split("\t");
                    // TO WRITE TO THE FILE:
                    // write original number    -STRING
                    // tab
                    // write original base  -STRING
                    // tab
                    // write the converted number
                    // tab
                    // write the toBase     -STRING
                    if (Integer.parseInt(line[1]) < 2 || Integer.parseInt(line[1]) > 16)
                        System.out.println("Invalid input base " + line[1]);
                    else if (Integer.parseInt(line[2]) < 2 || Integer.parseInt(line[2]) > 16)
                        System.out.println("Invalid output base " + line[2]);
                    else {
                        output = intToStr(strToInt(line[0], line[1]), Integer.parseInt(line[2]));
                        out.println(line[0] + "\t" + line[1] + "\t" + output + "\t" + line[2]);
                        System.out.println(line[0] + " base " + line[1] + " = " + output + " base " + line[2]);
                        //System.out.println(line[0]);    // String num
                        //System.out.println(line[1]);    // String fromBase
                        //System.out.println(line[2]);    // String toBase
                    }
                }
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();

            } catch (Exception e) {
                System.out.println("Something bad happened. Details here: " + e.toString());
            }
        }
    }


    /**
     * Main method of the BaseConverter class
     *
     * @param args Command line arguments if needed
     */
    public static void main(String[] args) {
        BaseConverter bc = new BaseConverter();
        bc.inputConvertPrintWrite();





    }
}
