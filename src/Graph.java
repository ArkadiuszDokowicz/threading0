/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author ArekDokowicz
 */
public class Graph {
    class CounterVertexThread implements Runnable {
        int row;//table position
        String name;
        int size;
        CountDownLatch latch;
        public CounterVertexThread(String name,int i,int size,CountDownLatch latch) {
            this.name=name;
            this.row=i;
            this.size=size;
            this.latch=latch;

        }



        synchronized void  vertexAdd(){
           vertices+=1;}
        @Override
        public void run(){

            // System.out.println(this.name+" Started");
                /*try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CounterVertexThread.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            //for(int i=0;i<100000;i++){System.out.print(this.name);}
            for(int j=this.row+1;j<this.size;j++){
                if(tablica[row][j]==1)
                    vertexAdd();
            }
            // System.out.println("before"+latch);
            latch.countDown();
            ///  System.out.println("after cd"+latch);
            //   System.out.println(this.name+" Finished");

        }
    }
    static int[][] tablica;
    static int size;
    int vertices;

    public static void setSize(int size) {
        Graph.size = size;
    }


    public void info(){
        //this.print();
        //this.printSize();
        this.printVertices();
    }
    public void printSize(){
        System.out.println("Size: "+getSize());
    }
    public void printVertices(){
        System.out.println("Vertices: "+getVertices());
    }
    public int getSize() {
        return size;
    }

    public int getVertices() {
        return vertices;
    }

    public void print(){
        for(int i=0; i< tablica.length; i++){
            for(int j=0; j< tablica[i].length; j++){
                System.out.print(tablica[i][j]+" ");
            }
            System.out.println();
        }

    }

    public void singleVertexcounter(){
        this.vertices=0;
        for(int i=0;i<size-1;i++){
            for(int j=i+1;j<size;j++){
                if(tablica[i][j]==1)
                    vertices+=1;}
        }
    }

    public void multiVertexcounter() throws InterruptedException{
        this.vertices=0;
        CountDownLatch latch = new CountDownLatch(this.size-1);

        for(int i=0;i<size-1;i++){

            CounterVertexThread t1= new CounterVertexThread("No."+i,i,size,latch);
            new Thread(t1).start();
        }
        try {
            latch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(CounterVertexThread.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    public void singleGnp(float probability){

        for(int i=0;i<size-1;i++){
            tablica[i][i]=0;
            for(int j=i+1;j<size;j++){
                int randomNum = ThreadLocalRandom.current().nextInt(0,101);
                if(randomNum<=probability*100){

                    tablica[i][j]=1;
                    tablica[j][i]=1;
                }
                else{
                    tablica[i][j]=0;
                }
            }
        }
    }

    public Graph(int size) {

        this.size=size;
        tablica = new int[size][size];


    }



}