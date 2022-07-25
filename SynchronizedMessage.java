public class SynchronizedMessage {
    public static void main(String args[]) {
        Sender sender = new Sender();
        SendUsingThreads sender1 = new SendUsingThreads("Hello from first sender.", sender);
        SendUsingThreads sender2 = new SendUsingThreads("Hi from first sender.", sender);
        // Start two threads of SendUsingThreads type
        sender1.start();
        sender2.start();
        // wait for threads to end
        try {
            sender1.join();
            sender2.join();
        } catch (Exception e) {
            System.out.println("Interrupted");
        }
    }
}

// A Class used to send a message 
class Sender {
    public void sendMessage(String message) {
        System.out.println("Sending " + message);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Thread interrupted.");
        }
        System.out.println(message + " (Sent)");
    }
}

// Class for sending a message using Threads
class SendUsingThreads extends Thread {
    private String message;
    Sender sender;

    // Receives a message object and a string message to be sent
    SendUsingThreads(String msg, Sender object) {
        message = msg;
        sender = object;
    }

    public void run() {
        // This will ensure that only one thread sends a message at a time.
        synchronized (sender) {
            // synchronizing the send object
            sender.sendMessage(message);
        }
    }
}
