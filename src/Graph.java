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
class CounterVertexThread implements Runnable {
    int row;//table position
    String name;
    Graph obj;
    int[][]tablica;
    int size;
    int vertices;
    CountDownLatch latch;
    public CounterVertexThread(String name,int i,int size,int vertices,int[][]tablica,CountDownLatch latch) {
        this.name=name;
        this.row=i;
        this.size=size;
        //this.vertices=vertices;
        //this.obj=obj;
        this.vertices=vertices;
        this.latch=latch;
        this.tablica=tablica;
    }

    public CounterVertexThread(String name, int i, Graph obj, CountDownLatch latch) {
        this.obj=obj;
        this.latch=latch;
        this.name=name;
        this.row=i;
    }

    synchronized void  vertexAdd(){
        this.obj.vertices+=1;}
    @Override
    public void run(){

        // System.out.println(this.name+" Started");
                /*try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CounterVertexThread.class.getName()).log(Level.SEVERE, null, ex);
                }*/
        //for(int i=0;i<100000;i++){System.out.print(this.name);}
        for(int j=this.row+1;j<this.obj.size;j++){
            if(this.obj.tablica[row][j]==1)
                vertexAdd();
        }
        // System.out.println("before"+latch);
        latch.countDown();
        ///  System.out.println("after cd"+latch);
        //   System.out.println(this.name+" Finished");

    }
}
/**
 *
 * @author ArekDokowicz
 */
public class Graph {
    int[][] tablica;
    int size;
    int vertices;

    public void info(){
        this.print();
        this.printSize();
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

        //Lock lock = new ReentrantLock();
        //lock.lock();
        //try {
        //CounterVertexThread[] th_tab=new CounterVertexThread[size-1];
        for(int i=0;i<size-1;i++){

            CounterVertexThread t1= new CounterVertexThread("No."+i,i,this,latch);
            new Thread(t1).start();

            //latch.countDown();

            //  }   } finally {
            //lock.unlock();
        }
        //for(int i=0;i<size-1;i++){
        //  th_tab[i].run();

        // System.out.println(latch);
        //}
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