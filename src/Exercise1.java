/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author ArekDokowicz
 */
public class Exercise1 {
    static long lEndTime = System.nanoTime();//end of counting
    static long lStartTime = System.nanoTime();//start counting
    static long output = lEndTime - lStartTime; //time_counter
    static FileWriter fileWriter;

    static {
        try {
            fileWriter = new FileWriter("wyniki.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static PrintWriter printWriter = new PrintWriter(fileWriter);

    public Exercise1() throws IOException {
    }

    static void time_start(){
        lStartTime = System.nanoTime();//start counting
    }
    static void time_end(){
        lEndTime = System.nanoTime();//end of counting
        output = lEndTime - lStartTime; //time_counter
        // System.out.println("Elapsed time in milliseconds: " + output );
    }

    public static void givenWritingStringToFile_whenUsingPrintWriter_thenCorrect(PrintWriter printWriter, String string,int size,long output)
            throws IOException {
        String stringlong=Long.toString(output);
        //printWriter.printf("Product name is %s and its price is %d $", "iPhone", 1000);
        printWriter.printf("vertices:, %d, type:, %s, time:, %s",size,string,stringlong);
        printWriter.println();
    }
    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        for(int i=1;i<1000;i++) {
            int size = i;
            Graph gnp = new Graph(size);
            gnp.singleGnp(0.2F);
            time_start();
            gnp.singleVertexcounter();
            time_end();
             gnp.info();
            givenWritingStringToFile_whenUsingPrintWriter_thenCorrect(printWriter, "single-thread", size, output);
            time_start();
            gnp.multiVertexcounter();
            time_end();
             gnp.info();
            givenWritingStringToFile_whenUsingPrintWriter_thenCorrect(printWriter, "multi-thread", size, output);
        }
        // TODO code application logic here
        printWriter.close();
    }

}