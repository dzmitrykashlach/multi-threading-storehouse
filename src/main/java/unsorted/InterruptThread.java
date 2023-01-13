package unsorted;

public class InterruptThread {
    public static void main(String[] args) throws InterruptedException {
              class MyThread extends Thread{
                  // fixies the issue with shutdown
//                 Idea: https://www.javaspecialists.eu/archive/Issue150-The-Law-of-the-Blind-Spot.html
//                  public volatile  boolean needExit = false;
                  public boolean needExit = false;
                  @Override
                  public void run(){
                      //              works because of Thread.interrupt0();
//                      while(!this.isInterrupted()){
                      while(!needExit){

                      }
                  }
              }
              var thread =  new MyThread();
              thread.start();
              Thread.sleep(5000);
//              works because of Thread.interrupt0();
//              thread.interrupt();
//              doesn't work
              thread.needExit = true;
    }
}
