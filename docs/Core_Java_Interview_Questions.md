# Core Java Interview Questions — Multithreading & Concurrency

## 1. What Is the Difference Between a Process and a Thread?

In Java programming, a process and a thread are both units of execution, but they operate at different levels of abstraction and have distinct characteristics.

A process can be thought of as an independent entity that runs in its own memory space and has its own resources, including memory, file handles, and system resources. Processes are heavyweight entities, and each process operates independently of other processes. Examples of processes include running multiple instances of a web browser or a text editor on your computer. Each instance operates as a separate process, with its own memory space and resources.

On the other hand, a thread is a lightweight unit of execution that exists within a process. Threads share the same memory space and resources as the process to which they belong. Multiple threads can exist within a single process and share resources such as memory and file handles. Threads are used to achieve concurrency within a program, allowing multiple tasks to be performed simultaneously. For example, in a web server application, multiple threads can handle incoming client requests concurrently, improving the server's responsiveness and efficiency.

**Real-World Use Case Examples:**

- **Web Browser:** When you open multiple tabs in a web browser, each tab typically runs as a separate process. This isolation ensures that if one tab crashes or experiences issues, it does not affect the other tabs or the browser itself. Within each tab, multiple threads handle tasks such as rendering web pages, processing user input, and downloading resources concurrently.
- **Word Processing Application:** In a word processing application like Microsoft Word, the application itself runs as a process. Within the application, various threads handle tasks such as user interface interactions, spell-checking, auto-saving, and printing. These threads operate concurrently, providing a smooth and responsive user experience.

In summary, while processes and threads both represent units of execution in Java, they differ in their level of isolation, resource allocation, and concurrency.

## 2. How can we create a Thread in Java?

You can create a thread instance by either extending the `Thread` class or implementing the `Runnable` interface. Once you have created a thread instance, you can start it by calling the `start()` method.

```java
// Define a class that extends Thread
class MyThread extends Thread {
    public void run() {
        // Code to be executed by the thread
        System.out.println("Thread running: " + Thread.currentThread().getName());
    }
}

public class Main {
    public static void main(String[] args) {
        // Create an instance of MyThread
        MyThread myThread = new MyThread();
        // Start the thread
        myThread.start();
    }
}
```

```java
// Define a class that implements the Runnable interface
class MyRunnable implements Runnable {
    public void run() {
        // Code to be executed by the thread
        System.out.println("Thread running: " + Thread.currentThread().getName());
    }
}

public class Main {
    public static void main(String[] args) {
        // Create an instance of MyRunnable
        MyRunnable myRunnable = new MyRunnable();
        // Create a Thread instance and pass MyRunnable as a parameter
        Thread thread = new Thread(myRunnable);
        // Start the thread
        thread.start();
    }
}
```

## 3. Describe the Different States of a Thread?

During its lifecycle, a thread in Java can exist in several states, each representing a different stage of its execution:

- **New** — When a thread is created but not yet started, it is in the "New" state. The `start()` method has not been called.
- **Runnable** — After calling `start()`, the thread moves to the "Runnable" state. It is eligible to run, but the scheduler has not yet selected it.
- **Running** — When the thread scheduler selects a thread for execution, it enters the "Running" state. The thread's code is actively being executed by the CPU.
- **Blocked (or Waiting)** — A thread enters this state when it is temporarily inactive, typically waiting for a resource or condition, such as I/O completion or acquiring a lock.
- **Timed Waiting** — Threads pause execution for a specified period, e.g., via `sleep()` or `join()` with a timeout.
- **Terminated** — The final state, indicating the thread has completed execution, either successfully or by being explicitly stopped.

**Real-World Use Case:**

Imagine you're building a chat application. Each user's interaction with the chat interface is handled by a separate thread. Here's how the different thread states might come into play:

- **New:** When a new user opens the chat application, a thread is created to handle their interactions.
- **Runnable:** Once the user logs in, their thread transitions to the "Runnable" state, awaiting execution by the thread scheduler.
- **Running:** When the scheduler selects the user's thread for execution, it enters the "Running" state, actively processing messages and user inputs.
- **Blocked:** If the user sends a message and it needs to be sent over the network, the thread might enter the "Blocked" state while waiting for the network I/O operation to complete.
- **Terminated:** Once the user logs out or closes the application, their thread enters the "Terminated" state, indicating the end of its lifecycle.

## 4. What Is a Daemon Thread and how to create a Daemon Thread? What are the real use cases of Daemon thread?

A daemon thread in Java is a special type of thread that runs in the background, providing services to other threads or performing tasks that are not critical to the application's lifecycle. Unlike user threads, daemon threads do not prevent the JVM from exiting when all user threads have finished executing. They are automatically terminated when all non-daemon threads have exited or when the JVM shuts down.

**Real-World Use Cases:**

- **Garbage Collection:** Daemon threads are commonly used by the JVM for tasks such as garbage collection, reclaiming unused memory while the application continues to execute.
- **Monitoring Services:** Daemon threads can monitor system resources or perform periodic maintenance tasks in the background, such as disk usage, network traffic, or performance metrics.
- **Background Tasks:** In server-side applications, daemon threads can handle background tasks such as logging, sending periodic heartbeats to a monitoring system, or cleaning up temporary files.

By using daemon threads, you can offload non-critical tasks to background threads, improving the overall responsiveness and efficiency of your Java applications.

To create a daemon thread in Java, you simply need to set the daemon flag to `true` before starting the thread.

```java
public class DaemonThreadExample {
    public static void main(String[] args) {
        Thread daemonThread = new Thread(() -> {
            while (true) {
                System.out.println("Daemon thread is running...");
                try {
                    Thread.sleep(1000); // Simulate some task
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        daemonThread.setDaemon(true); // Set the thread as daemon
        daemonThread.start(); // Start the daemon thread
    }
}
```

## 5. What Is the Difference Between the Runnable and Callable Interfaces?

The `Runnable` and `Callable` interfaces in Java both represent tasks that can be executed by threads, but they differ in several key aspects:

**Return Value**
- `Runnable` does not return a result when its task completes. Its `run()` method has a `void` return type.
- `Callable` can return a result when its task completes, via its `call()` method.

**Exception Handling**
- In `Runnable`, any checked exceptions thrown by `run()` must be caught and handled within the method itself.
- `Callable` allows `call()` to throw checked exceptions, which can be caught and handled by the calling code.

**Usage with Executors**
- `Runnable` is primarily used with the `Executor` framework for executing tasks asynchronously in a thread pool.
- `Callable` is used with `ExecutorService`, which provides additional features such as submitting tasks that return values and handling exceptions.

```java
package java.util.concurrent;

/**
 * A task that returns a result and may throw an exception.
 * Implementors define a single method with no arguments called
 * {@code call}.
 *
 * <p>The {@code Callable} interface is similar to {@link
 * java.lang.Runnable}, in that both are designed for classes whose
 * instances are potentially executed by another thread.  A
 * {@code Runnable}, however, does not return a result and cannot
 * throw a checked exception.
 *
 * <p>The {@link Executors} class contains utility methods to
 * convert from other common forms to {@code Callable} classes.
 *
 * @see Executor
 * @since 1.5
 * @author Doug Lea
 * @param <V> the result type of method {@code call}
 */
@FunctionalInterface
public interface Callable<V> {
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    V call() throws Exception;
}
```

## 6. What Is Java Memory Model (JMM) and its purpose?

The Java Memory Model (JMM) defines how Java programs interact with memory, including how threads interact through memory when executing concurrently. Its purpose is to ensure that concurrent Java programs behave predictably and consistently across different platforms and JVM implementations.

The Java memory model specifies how the Java virtual machine works with the computer's memory (RAM).

Each Java thread operates with its own thread stack, which maintains the call stack — a record of invoked methods leading to the current execution point. Local variables for each method reside in the thread stack and are exclusive to that thread.

In contrast, the heap stores all objects created within the Java application, irrespective of the creating thread. This includes object versions of primitive types. Whether an object is assigned to a local or a member variable, it's stored in the heap.

## 7. What is Data Race Condition and Visibility problem?

**Data Race Condition:**

A data race condition occurs when two or more threads access shared data concurrently, and at least one of the threads modifies the data. This can lead to unpredictable behavior and incorrect results due to the non-deterministic interleaving of thread operations. Data races can occur when threads access shared variables without proper synchronization, leading to inconsistent or corrupted data.

**Visibility Problem:**

The visibility problem refers to the issue where changes made by one thread to shared variables may not be immediately visible to other threads. In Java, each thread has its own cache of variables, and changes made by one thread may not be immediately propagated to other threads' caches. This can result in one thread not seeing the most recent value of a shared variable, leading to incorrect behavior.

**Real-World Use Case:**

Consider a scenario where multiple threads are updating a shared counter variable. Without proper synchronization, such as using locks or volatile variables, data races can occur. For instance, one thread may read the counter's value while another thread is in the process of updating it, leading to inconsistent or incorrect results. Additionally, if one thread updates the counter, other threads may not immediately see the updated value due to visibility issues.

```java
public class Counter {
    private int count;

    public void increment() {
        count++; // Not thread-safe
    }

    public int getCount() {
        return count; // Not thread-safe
    }
}
```

## 8. What is difference between Volatile, Atomic and Synchronized?

- **Volatile:** Indicates that a variable's value may be modified by multiple threads, and changes should be immediately visible to other threads. It does not provide atomicity or mutual exclusion, but ensures reads/writes are not reordered by the compiler or processor, addressing the visibility problem.
- **Atomic:** The `java.util.concurrent.atomic` package provides classes like `AtomicInteger`, `AtomicLong`, and `AtomicReference`, offering atomic operations without explicit synchronization, using compare-and-set (CAS) to ensure updates are performed atomically and without data races.
- **Synchronized:** Creates a mutually exclusive block of code that can only be executed by one thread at a time. It provides both atomicity and mutual exclusion, ensuring thread safety by preventing data races and ensuring consistent access to shared resources.

**Real-World Use Cases:**

- **Volatile:** Use when you have a variable shared among multiple threads and want changes to be immediately visible without the need for locking, e.g., a flag indicating whether a thread should continue running.
- **Atomic:** Use for atomic operations like incrementing, updating, or comparing variables in a thread-safe manner, e.g., a global counter or thread-safe caching.
- **Synchronized:** Use when only one thread should access a block of code or modify shared resources at a time, e.g., updating shared data structures or critical operations requiring exclusive access.

```java
// Volatile
public class VolatileExample {
    private volatile boolean flag;

    public void setFlag(boolean value) {
        flag = value;
    }

    public boolean getFlag() {
        return flag;
    }
}

// Atomic
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicExample {
    private AtomicInteger counter = new AtomicInteger(0);

    public void increment() {
        counter.incrementAndGet();
    }

    public int getCount() {
        return counter.get();
    }
}

// Synchronized
public class SynchronizedExample {
    private int count;

    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}
```

## 9. Explain about Deadlock, Livelock, and Starvation conditions in Java Multithreading?

**Deadlock**

Deadlock occurs when two or more threads are permanently blocked, waiting for each other to release resources, leading to a circular dependency.

Example: Thread A locks Resource 1 and waits for Resource 2. Thread B locks Resource 2 and waits for Resource 1. Both threads wait indefinitely → Deadlock occurs.

Prevention Strategies:
- **Consistent Lock Ordering:** Always acquire locks in a fixed order.
- **Timeouts:** Use `tryLock()` with a timeout to avoid indefinite waiting.
- **Deadlock Detection:** Implement a resource hierarchy or use a tool like `ThreadMXBean` to detect deadlocks.

**Livelock**

Livelock occurs when threads keep changing states in response to each other without making any actual progress.

Example: Two people trying to pass through a narrow hallway. Both step aside at the same time, then move back, repeating indefinitely. The system is not blocked but also not progressing.

Prevention Strategies:
- **Random Delays:** Introduce a random wait time before retrying.
- **Priority Inversion Handling:** Ensure a fallback mechanism to break the cycle.
- **Use Message Passing:** Use a queue-based or event-driven approach instead of retries.

**Starvation**

Starvation happens when a thread is perpetually denied access to resources because other high-priority threads keep executing.

Example: A low-priority thread is never scheduled because higher-priority threads keep running. This happens in priority-based scheduling or biased locking scenarios.

Prevention Strategies:
- **Fair Scheduling Policies:** Use `ReentrantLock(true)` for fair access to locks.
- **Priority Adjustments:** Dynamically adjust thread priorities if a thread is waiting too long.
- **Avoid Starvation-Prone Algorithms:** Implement fair resource allocation mechanisms like FIFO-based locks.

**Real-World Use Cases**

- **Deadlock:** In database transactions, two queries trying to lock rows in different order can cause deadlocks.
- **Livelock:** In distributed systems, multiple nodes retrying conflict resolution can lead to infinite state changes without progress.
- **Starvation:** In web servers, high-priority API requests may dominate CPU cycles, starving low-priority background tasks.

## 10. What is Java Executor Service and type of threadpools which can be created with Java Executor service?

The Java Executor Service is a higher-level concurrency utility that provides a simplified interface for managing threads and executing tasks asynchronously. It abstracts away the complexity of thread management.

**Types of Thread Pools:**

- **FixedThreadPool:** Maintains a fixed number of threads. If a task is submitted when all threads are busy, it is queued until a thread becomes available. Suitable for a predictable number of tasks with a limit on concurrent threads.
- **CachedThreadPool:** Dynamically adjusts the number of threads based on workload. Threads are created as needed and reused if available; unused threads are terminated after a specified idle timeout. Ideal for a large number of short-lived tasks or variable workloads.
- **SingleThreadPool:** Maintains a single thread, ensuring tasks execute sequentially in submission order. If the thread terminates due to an exception, a new one replaces it. Useful when tasks must execute sequentially.
- **ScheduledThreadPool:** Used for executing tasks at a specific time or with a fixed delay between executions, via `schedule()`, `scheduleAtFixedRate()`, and `scheduleWithFixedDelay()`. Ideal for periodic tasks like background jobs.

**Real-World Use Case Example:**

Suppose you're developing a web server application where multiple client requests need to be processed concurrently. You can use a `FixedThreadPool` to limit the maximum number of concurrent connections and ensure efficient resource utilization. Additionally, you can use a `ScheduledThreadPool` to schedule periodic tasks like log rotation or cache cleanup.

```java
// Fixed Thread Pool
ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

System.out.println("\nFixed Thread Pool:");
for (int i = 1; i <= 5; i++) {
    final int taskId = i;
    fixedThreadPool.execute(() -> {
        System.out.println("Fixed Thread Task " + taskId +
                " executed by Thread: " +
                Thread.currentThread().getName());
    });
}

// Cached Thread Pool
ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

System.out.println("\nCached Thread Pool:");
for (int i = 1; i <= 5; i++) {
    final int taskId = i;
    cachedThreadPool.execute(() -> {
        System.out.println("Cached Thread Task " + taskId +
                " executed by Thread: " +
                Thread.currentThread().getName());
    });
}

// Single Thread Executor
ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

System.out.println("\nSingle Thread Executor:");
for (int i = 1; i <= 5; i++) {
    final int taskId = i;
    singleThreadExecutor.execute(() -> {
        System.out.println("Single Thread Task " + taskId +
                " executed by Thread: " +
                Thread.currentThread().getName());
    });
}

// Scheduled Thread Pool
ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);

scheduledThreadPool.schedule(() ->
        System.out.println("Scheduled Task"),
        5, TimeUnit.SECONDS);

scheduledThreadPool.scheduleAtFixedRate(() ->
        System.out.println("Scheduled Task"),
        0, 1, TimeUnit.SECONDS);
```

## 11. What Are Executor and ExecutorService and Differences Between them?

- **Executor:** A simple interface that decouples task submission from task execution. It defines a single method, `execute(Runnable command)`, which executes the given command in a thread pool.
- **ExecutorService:** A subinterface of `Executor` that represents a more complete version, providing methods to manage the lifecycle of the thread pool, submit tasks for execution, and obtain `Future`s representing task results.

**Differences:**

- **Lifecycle Management:** `Executor` provides no lifecycle management. `ExecutorService` adds `shutdown()`, `shutdownNow()`, and `awaitTermination()`.
- **Task Submission and Execution:** Both provide ways to submit tasks (`execute(Runnable)` in `Executor`; `submit(Runnable)` and `submit(Callable<T>)` in `ExecutorService`).
- **Task Result Handling:** `Executor` has no way to obtain a task's result directly. `ExecutorService` supports `submit(Callable<T>)`, returning a `Future` for result retrieval.
- **Termination:** `Executor` provides no termination methods. `ExecutorService` provides `shutdown()`, `shutdownNow()`, and `awaitTermination()`.

**Use Case Scenarios:**

- Use `Executor` when you only need to execute tasks asynchronously without managing the thread pool's lifecycle.
- Use `ExecutorService` when you need more control, such as managing lifecycle, submitting tasks with results, and awaiting termination.

## 12. What is Producer-Consumer problem and how to resolve it?

The Producer-Consumer problem is a classic synchronization problem in concurrent programming, where there are two types of threads: producers and consumers. Producers generate data or items, and consumers consume or process these items.

The challenge is to ensure that producers do not produce items when the buffer is full, and consumers do not consume items when the buffer is empty. It requires efficient coordination and synchronization between producers and consumers to avoid race conditions and ensure proper resource management.

One effective solution is using the `BlockingQueue` interface in Java, which provides a thread-safe queue implementation with blocking operations.

**Real-World Use Case Example:**

Consider a scenario where a web server application receives requests from clients and processes them concurrently. Producers enqueue incoming requests into a `BlockingQueue`, and multiple worker threads (consumers) dequeue requests from the queue and process them. This ensures efficient request handling without the risk of data corruption or race conditions.

```java
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class ProducerConsumerExample {
    private static final int BUFFER_SIZE = 10;
    private static BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(BUFFER_SIZE);

    public static void main(String[] args) {
        Thread producerThread = new Thread(new Producer());
        Thread consumerThread = new Thread(new Consumer());

        producerThread.start();
        consumerThread.start();
    }

    static class Producer implements Runnable {
        public void run() {
            try {
                int item = 1;
                while (true) {
                    buffer.put(item++);
                    System.out.println("Produced item: " + (item - 1));
                    Thread.sleep(1000); // Simulate production time
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Consumer implements Runnable {
        public void run() {
            try {
                while (true) {
                    int item = buffer.take();
                    System.out.println("Consumed item: " + item);
                    Thread.sleep(2000); // Simulate processing time
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
```

## 13. What is ThreadLocal and what are the advantages of using ThreadLocal?

`ThreadLocal` is a class in Java that provides thread-local variables. Each thread accessing a `ThreadLocal` variable has its own, independently initialized copy of the variable. `ThreadLocal` variables are typically used to store data that is specific to a particular thread and should not be shared among multiple threads.

**Advantages of Using ThreadLocal:**

- **Thread Isolation:** Maintains separate copies of variables for each thread, preventing concurrent threads from accessing or modifying each other's data, enhancing thread safety.
- **Thread Confinement:** Supports confining data to a specific thread's execution context, encapsulating thread-specific data without needing synchronization or locks.
- **Reduced Synchronization Overhead:** Since each thread has its own copy, there is no need for explicit synchronization when accessing these variables, improving performance and scalability.
- **Avoids Memory Leaks:** `ThreadLocal` variables are garbage collected along with the thread that owns them, preventing memory leaks from shared resources across threads.
- **Contextual Data Storage:** Often used to store contextual data related to the current thread's execution context, such as user sessions, transaction contexts, or request-specific data in web applications.

**Real-World Use Case Example:**

In a web application framework, `ThreadLocal` variables are commonly used to store user authentication information or request context data. For example, in a servlet-based web application, a `ThreadLocal` variable can be used to store the current `HttpServletRequest` object, allowing components downstream to access request-specific information without passing it explicitly.

By leveraging `ThreadLocal`, developers can ensure that each thread has access to its own instance of the data, eliminating the need for complex parameter passing or synchronization mechanisms. This improves code readability, simplifies concurrency management, and enhances overall application performance.

```java
public class ThreadLocalExample {
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        // Set thread-local value for main thread
        threadLocal.set(100);

        // Create and start a new thread
        Thread thread = new Thread(() -> {
            // Access thread-local value in the new thread
            System.out.println("ThreadLocal value in new thread: " + threadLocal.get());
        });
        thread.start();
    }
}
```

## 14. What are Virtual Threads in Java 21?

Virtual Threads are lightweight, user-mode threads managed by the Java Virtual Machine (JVM). Unlike traditional OS threads (kernel threads), virtual threads are managed entirely by the JVM and can be created and scheduled more efficiently.

Virtual threads are lightweight compared to traditional OS threads, requiring fewer system resources for creation and management. This makes them suitable for applications with a large number of concurrent tasks.

Use virtual threads in high-throughput concurrent applications, especially those that consist of a great number of concurrent tasks that spend much of their time waiting. Server applications are examples of high-throughput applications because they typically handle many client requests that perform blocking I/O operations such as fetching resources.

Virtual threads are not faster threads; they do not run code any faster than platform threads. They exist to provide scale (higher throughput), not speed (lower latency).

```java
public static void main(String[] args) {
    Thread virtualThread = Thread.startVirtualThread(() -> {
        System.out.println("Running task with virtual thread: "
                + Thread.currentThread().getName());
    });

    // Waiting for virtual threads to complete
    try {
        virtualThread.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```

## 15. What is the Fork/Join Framework in Java?

The Fork/Join Framework is designed to efficiently parallelize divide-and-conquer algorithms, where a problem is recursively split into smaller independent subproblems, solved in parallel, and then combined to form the final result.

**Key Components of the Fork/Join Framework**

1. **ForkJoinPool** — A specialized implementation of `ExecutorService` optimized for recursive parallelism. It manages a pool of worker threads and efficiently distributes tasks using work stealing. Unlike fixed-thread pools, it dynamically adjusts workload distribution for optimal CPU utilization.

2. **ForkJoinTask** — A lightweight task that can be forked (split) into subtasks and joined (merged) once completed. `ForkJoinTask` is an abstract class, and its two main implementations are:
   - `RecursiveTask<T>` → Returns a result.
   - `RecursiveAction` → Performs an operation but does not return a result.

3. **Work Stealing Algorithm** — Instead of waiting for tasks, idle worker threads dynamically steal tasks from the tail of another thread's deque (double-ended queue). This improves load balancing and ensures efficient parallel execution.

**How It Works (Workflow)**

1. A main task is submitted to `ForkJoinPool`.
2. The task divides itself into smaller subtasks (forking).
3. Each subtask is executed independently in parallel.
4. Once all subtasks finish, their results are merged (joining) to get the final output.
5. If a worker thread runs out of tasks, it steals work from other busy threads.

**Advantages of Fork/Join Framework**

- **Parallelism** — Automates parallel execution of recursive tasks, reducing manual thread management.
- **Load Balancing** — Uses work-stealing to evenly distribute tasks across worker threads.
- **Scalability** — Leverages multiple processor cores for efficient computation of large datasets.

**Real-World Use Cases**

- **Recursive Algorithms** — Parallelizing Merge Sort, Quick Sort, Matrix Multiplication, Tree Traversal.
- **Data Processing** — Image processing, Matrix operations, Parallel reductions.
- **Concurrent Computations** — Tasks that can be broken into independent parallel sub-tasks.

The code below demonstrates how to leverage the Fork/Join Framework to parallelize the computation of the sum of array elements, improving performance by utilizing multiple threads efficiently.

```java
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinSumExample {
    // Define a task to compute the sum of elements in a given range of an array
    static class SumTask extends RecursiveTask<Integer> {
        private static final int THRESHOLD = 10; // Threshold for splitting tasks
        private int[] array;
        private int start, end;

        public SumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        protected Integer compute() {
            if (end - start <= THRESHOLD) {
                int sum = 0;
                for (int i = start; i < end; i++)
                    sum += array[i];
                return sum;
            } else {
                int mid = (start + end) / 2;
                SumTask leftTask = new SumTask(array, start, mid);
                SumTask rightTask = new SumTask(array, mid, end);

                leftTask.fork();
                rightTask.fork();

                return leftTask.join() + rightTask.join();
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        ForkJoinPool pool = ForkJoinPool.commonPool();

        int result = pool.invoke(new SumTask(array, 0, array.length));

        System.out.println("Sum: " + result);
    }
}
```

## 16. What Is the Purpose of the Wait, Notify and NotifyAll Methods?

The `wait()`, `notify()`, and `notifyAll()` methods in Java are used for inter-thread communication. They allow threads to wait for a certain condition to be met and then be notified when it is.

- **wait():** Makes a thread wait until another thread notifies it. Typically called inside a synchronized block or method. Threads call `wait()` when they need to wait for a condition to be satisfied before proceeding with their execution — for example, a consumer thread may wait for a producer thread to produce data.
- **notify():** Wakes up a single waiting thread that is waiting on the same object's monitor. Threads call `notify()` to signal other threads that a particular condition has been met or a resource is available for consumption — for example, a producer thread may notify a consumer thread when new data is available.
- **notifyAll():** Wakes up all waiting threads that are waiting on the same object's monitor. Used when multiple threads are waiting for the same condition or resource, ensuring all waiting threads can compete for the shared resource or condition; generally used to prevent potential deadlock situations.

**Important Points:**

- `wait()`, `notify()`, and `notifyAll()` must be called from within a synchronized block or method to ensure proper thread coordination and avoid race conditions.
- These methods are used to implement the classic producer-consumer pattern, where producers produce data and notify consumers when data is available for consumption.
- It's important to use these methods carefully to prevent potential issues such as deadlock or livelock.

```java
public class WaitNotifyExample {
    private static final Object lock = new Object(); // Object used as a monitor for synchronization
    private static boolean condition = false; // Shared condition variable

    public static void main(String[] args) {
        // Consumer thread
        Thread consumerThread = new Thread(() -> {
            synchronized (lock) {
                while (!condition) { // Wait until condition is true
                    try {
                        lock.wait(); // Wait for notification
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // Condition is true, consume the resource
                System.out.println("Consumer: Resource consumed");
            }
        });

        // Producer thread
        Thread producerThread = new Thread(() -> {
            // Produce the resource
            System.out.println("Producer: Resource produced");
            synchronized (lock) {
                condition = true; // Set condition to true
                lock.notify(); // Notify waiting threads
            }
        });

        // Start consumer and producer threads
        consumerThread.start();
        producerThread.start();
    }
}
```

## 17. What Is the Purpose of Thread.yield()?

The purpose of the `Thread.yield()` method in Java is to hint to the thread scheduler that the current thread is willing to relinquish its current use of the CPU. When a thread calls `yield()`, it suggests that the scheduler should move it from the running state back to the runnable state, allowing other threads of the same or higher priority to execute.

However, the exact behavior of `Thread.yield()` is platform-dependent and not guaranteed. It's up to the thread scheduler to decide whether to act on this hint or not, so it may not have any effect at all. Generally, `Thread.yield()` is used in scenarios where a thread needs to wait for some condition but doesn't want to block completely (like with `sleep()` or `wait()`), allowing other threads a chance to run.

The output of the code below can be different every time you run it because the behavior of `yield()` is non-deterministic and platform dependent as well.

```java
Runnable r = () -> {
    int counter = 0;
    while (counter < 2) {
        System.out.println(Thread.currentThread()
                .getName());
        counter++;
        Thread.yield();
    }
};
new Thread(r).start();
new Thread(r).start();
```

## 18. What is the purpose of Semaphore?

The purpose of `Semaphore` is to control access to a shared resource or a pool of resources by multiple threads concurrently. It provides a way to limit the number of threads that can access the resource simultaneously, thus preventing resource contention and managing concurrent access in a controlled manner.

It is a synchronization construct that controls access to a shared resource by using a counter. If the counter is greater than zero, access is allowed. If it is zero, access is denied. The counter keeps track of permits that allow access to the shared resource. To access the resource, a thread must acquire a permit from the semaphore. When the thread no longer needs access to the shared resource, it releases the permit, allowing another thread to acquire it.

In Java, the `Semaphore` class in the `java.util.concurrent` package implements this mechanism, so you don't have to implement your own semaphores.

In the example below, each thread acquires a permit using `semaphore.acquire()` before accessing the shared resource and releases the permit using `semaphore.release()` after completing its work.

As a result, only 2 threads are allowed to access the resource concurrently, while other threads wait for a permit to become available.

```java
import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    private static final int NUM_THREADS = 5;
    private static final Semaphore semaphore = new Semaphore(2); // Allow only 2 threads to access the resource simultaneously

    public static void main(String[] args) {
        // Create and start multiple threads
        for (int i = 0; i < NUM_THREADS; i++) {
            Thread thread = new Thread(() -> {
                try {
                    semaphore.acquire(); // Acquire permit
                    System.out.println(Thread.currentThread().getName() + " is accessing the resource");
                    Thread.sleep(1000); // Simulate resource usage
                    System.out.println(Thread.currentThread().getName() + " released the resource");
                    semaphore.release(); // Release permit
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
    }
}
```

## 19. Approaches to write efficient and scalable code in Java concurrency?

Here are some key principles for writing efficient concurrent code:

**Use High-Level Concurrency APIs**
Example: Consider a web server handling incoming HTTP requests concurrently. Instead of managing threads manually, you can use Java's `Executors` to create a thread pool, where each thread processes a request independently.
Explanation: Executors abstract thread creation and management, making it easier to handle concurrent tasks without worrying about low-level details.

**Choose the Right Concurrency Mechanisms**
Example: In a financial trading system, multiple threads need to process trades concurrently. Here, you might use a `ConcurrentHashMap` to store and access trade data concurrently, ensuring thread safety.
Explanation: `ConcurrentHashMap` provides efficient concurrent access to shared data, preventing data corruption and ensuring consistency.

**Avoid Unnecessary Synchronization**
Example: In a multi-threaded logging system, instead of synchronizing access to the log file using locks, you can use a thread-safe logger implementation provided by a logging framework like Log4j.
Explanation: By avoiding unnecessary synchronization, you reduce contention and improve the system's scalability and performance.

**Fine-Grained Locking**
Example: In a banking application, multiple threads may concurrently access different accounts for transactions. Instead of locking the entire account database, you can use fine-grained locking to lock only the specific accounts being accessed.
Explanation: Fine-grained locking minimizes contention and allows multiple threads to access different parts of the system concurrently.

**Reduce Lock Duration**
Example: In a multi-threaded game, instead of holding a lock while rendering a frame, you can acquire the lock just before updating shared game state and release it immediately afterward.
Explanation: By reducing lock duration, you minimize the time threads spend waiting for locks, improving concurrency and responsiveness.

**Concurrency Design Patterns**
Example: In a messaging application, you can use the Producer-Consumer pattern to decouple message producers from consumers, allowing multiple threads to send and receive messages concurrently.
Explanation: Concurrency design patterns provide reusable solutions to common concurrency problems, promoting code maintainability and scalability.

**Asynchronous and Non-Blocking IO**
Example: In a web server, you can use non-blocking IO to handle multiple client connections concurrently without blocking threads, improving server scalability.
Explanation: Asynchronous and non-blocking IO enables efficient resource utilization and responsiveness in IO-bound applications.

**Optimize Data Access**
Example: In a high-frequency trading system, you can optimize access to order book data using a lock-free data structure like a `ConcurrentSkipListMap`, allowing multiple threads to access the order book concurrently with minimal contention.
Explanation: Optimizing data access patterns enhances concurrency and scalability in multi-threaded applications.

---

# Race Conditions & Synchronization

*Module 03 - Multithreading*

Understanding data corruption in concurrent access and how to prevent it.

## What is a Race Condition?

A **race condition** occurs when multiple threads access shared data simultaneously, and the outcome depends on the unpredictable timing of thread execution. This leads to data corruption and bugs that are hard to reproduce.

**The Problem**

Consider two threads incrementing a counter. Each increment seems atomic but is actually three CPU operations:

1. **READ:** Load current value from memory into CPU register
2. **MODIFY:** Add 1 to the register value
3. **WRITE:** Store the new value back to memory

If threads interleave these operations, updates get lost. For example, both threads READ `counter` as `0`, each computes `register = 0 + 1 = 1`, and both WRITE `1` back — so `counter` ends up `1` instead of the expected `2`.

```java
public class Counter {
    private int count = 0;
    public void increment() {
        count++; // NOT ATOMIC!
    }
}
// Two threads call increment()
```

## The synchronized Keyword

Java's `synchronized` keyword provides mutual exclusion — only one thread can execute a synchronized block at a time. This prevents race conditions by ensuring atomic access to shared data.

**Synchronized Method**

```java
public class Counter {
    private int count = 0;

    // Only one thread can execute this at a time
    public synchronized void increment() {
        count++;  // Now atomic!
    }

    public synchronized int getCount() {
        return count;
    }
}
```

**Synchronized Block**

```java
public class Counter {
    private int count = 0;
    private final Object lock = new Object();

    public void increment() {
        synchronized (lock) {  // Acquire lock
            count++;
        }  // Release lock
    }
}
```

**How It Works**

- Every object in Java has an intrinsic lock (monitor).
- `synchronized(obj)` acquires `obj`'s lock before executing.
- Only one thread can hold a lock at a time.
- Other threads trying to acquire the same lock will BLOCK.
- The lock is automatically released when the block exits (even on exception).

## What to Synchronize On

**Bad: Wrong Lock Object**

```java
// WRONG: Different lock objects!
public void increment() {
    synchronized (new Object()) {
        count++;
    }
}
// Each call creates a new lock
```

**Good: Same Lock Object**

```java
private final Object lock = new Object();

public void increment() {
    synchronized (lock) {
        count++;
    }
}
```

**Lock Object Options**

- **this** — The instance itself (used by synchronized methods).
- **ClassName.class** — For static synchronized methods.
- **Private final object** — Best practice for finer control.

**Never Synchronize On**

- String literals (interned and shared)
- Boxed primitives like `Integer` (cached instances)
- Objects that might be reassigned
- `null` (throws `NullPointerException`)

## Atomic Variables

For simple operations like increment/decrement, `java.util.concurrent.atomic` classes provide lock-free thread safety using CPU-level atomic instructions (CAS — Compare And Swap).

```java
import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet();  // Atomic, no lock needed
    }

    public int getCount() {
        return count.get();
    }
}
```

**Common Atomic Classes**

- **AtomicInteger** — Thread-safe int operations
- **AtomicLong** — Thread-safe long operations
- **AtomicBoolean** — Thread-safe boolean operations
- **AtomicReference\<T\>** — Thread-safe object reference

**When to Use Atomic vs Synchronized**

- **Use Atomic when:** Single variable, simple operations (increment, compare-and-set).
- **Use Synchronized when:** Multiple variables, compound operations, complex logic.

## The volatile Keyword

`volatile` ensures visibility of changes across threads but does NOT provide atomicity.

```java
public class Flag {
    private volatile boolean stopped = false;

    public void stop() {
        stopped = true;  // Visible to all threads immediately
    }

    public void run() {
        while (!stopped) {  // Reads fresh value each time
            doWork();
        }
    }
}
```

**volatile Does NOT Fix This**

```java
private volatile int count = 0;

public void increment() {
    count++;  // STILL NOT THREAD-SAFE!
}
// count++ is still READ-MODIFY-WRITE
```

**When to Use volatile**

- Simple flags (boolean start/stop signals)
- Variables written by one thread, read by many
- Reference to immutable objects

## Common Interview Questions

**Q: What is a race condition?**

A race condition occurs when the correctness of a program depends on the relative timing of multiple threads. It happens when threads access shared mutable state without proper synchronization.

**Q: Difference between synchronized and volatile?**

- **synchronized:** Provides atomicity AND visibility. Only one thread executes at a time.
- **volatile:** Provides visibility only. Changes are immediately visible, but compound operations are still non-atomic.

**Q: Is i++ thread-safe?**

No. `i++` is a compound operation (read-increment-write). Even on volatile variables, it is not atomic. Use `AtomicInteger.incrementAndGet()` or a synchronized block.

**Q: What happens if two threads synchronize on different objects?**

No mutual exclusion occurs. Both threads can execute simultaneously because they hold different locks. Always synchronize on the SAME object to protect shared data.

## Key Takeaways

- Race conditions occur when threads access shared data without synchronization.
- Even simple operations like `i++` are not atomic (read-modify-write).
- `synchronized` provides mutual exclusion — only one thread at a time.
- Always synchronize on the SAME lock object to protect shared data.
- `AtomicInteger` and friends provide lock-free thread safety for simple operations.
- `volatile` provides visibility but NOT atomicity.
- Keep synchronized blocks as small as possible for performance.

---

# Deadlock Detection & Prevention

*Module 03 - Multithreading*

Understanding why threads get stuck waiting forever and how to prevent it.

## What is a Deadlock?

A deadlock is a situation where two or more threads are blocked forever, each waiting for a resource held by the other. The threads are stuck in a circular wait, and the program hangs indefinitely.

**Classic Deadlock Scenario**

- Thread 1 holds Lock A and waits for Lock B.
- Thread 2 holds Lock B and waits for Lock A.
- Neither can proceed — they will wait forever!

## Four Conditions for Deadlock

All four of these conditions must be present simultaneously for a deadlock to occur. Breaking any one of them prevents deadlock.

1. **Mutual Exclusion** — Resources cannot be shared; only one thread can hold a lock at a time.
2. **Hold and Wait** — Thread holds one resource while waiting for another.
3. **No Preemption** — Resources cannot be forcibly taken from a thread.
4. **Circular Wait** — Chain of threads, each waiting for a resource held by the next thread in the chain.

**Prevention Strategy**

The most practical approach is to break the Circular Wait condition by always acquiring locks in a consistent, global order.

```java
// Thread 1 runs method1()
synchronized (lockA) {
    // Thread 1 holds A
    synchronized (lockB) { // Waits for B
        doWork();
    }
}

// Thread 2 runs method2()
synchronized (lockB) {
    // Thread 2 holds B
    synchronized (lockA) { // Waits for A
        doWork();
    }
}
```

## Deadlock Code Example

**The Problem: Inconsistent Lock Order**

```java
public class DeadlockExample {
    private final Object lockA = new Object();
    private final Object lockB = new Object();

    public void method1() {
        synchronized (lockA) {           // Thread 1: Acquires A first
            System.out.println("Holding A, waiting for B");
            synchronized (lockB) {       // Thread 1: Waits for B
                doWork();
            }
        }
    }

    public void method2() {
        synchronized (lockB) {           // Thread 2: Acquires B first
            System.out.println("Holding B, waiting for A");
            synchronized (lockA) {       // Thread 2: Waits for A
                doWork();                // DEADLOCK if both run!
            }
        }
    }
}
```

**The Solution: Consistent Lock Order**

```java
public class NoDeadlock {
    private final Object lockA = new Object();
    private final Object lockB = new Object();

    public void method1() {
        synchronized (lockA) {           // Always acquire A first
            synchronized (lockB) {
                doWork();
            }
        }
    }

    public void method2() {
        synchronized (lockA) {           // Also A first - same order!
            synchronized (lockB) {
                doWork();
            }
        }
    }
}
```

**Key Insight**

When both threads acquire locks in the same order (A then B), circular wait is impossible. Thread 2 cannot hold B while Thread 1 holds A, because Thread 2 must first acquire A.

## Prevention Strategies

**1. Lock Ordering**

Define a global order for all locks and always acquire them in that order.

```java
// Use object hash codes or IDs to determine order
public void transferMoney(Account from, Account to, int amount) {
    Account first = from.getId() < to.getId() ? from : to;
    Account second = from.getId() < to.getId() ? to : from;

    synchronized (first) {
        synchronized (second) {
            from.withdraw(amount);
            to.deposit(amount);
        }
    }
}
```

**2. Lock Timeout with tryLock()**

Use `ReentrantLock.tryLock()` with a timeout to avoid waiting forever.

```java
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class TimeoutExample {
    private final ReentrantLock lockA = new ReentrantLock();
    private final ReentrantLock lockB = new ReentrantLock();

    public boolean doWork() {
        try {
            if (lockA.tryLock(1, TimeUnit.SECONDS)) {
                try {
                    if (lockB.tryLock(1, TimeUnit.SECONDS)) {
                        try {
                            // Do work with both locks
                            return true;
                        } finally {
                            lockB.unlock();
                        }
                    }
                } finally {
                    lockA.unlock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return false;  // Could not acquire both locks
    }
}
```

**3. Avoid Nested Locks**

Restructure code to avoid holding multiple locks simultaneously.

Nested Locks (Risk):

```java
synchronized (lockA) {
    synchronized (lockB) {
        doWork();
    }
}
```

Sequential Locks (Safer):

```java
Data snapshot;
synchronized (lockA) {
    snapshot = copyData();
}
synchronized (lockB) {
    processData(snapshot);
}
```

## Detecting Deadlocks

**Using jstack (Command Line)**

```bash
# Find the Java process ID
jps

# Get thread dump with deadlock detection
jstack <pid>

# Output shows:
# Found one Java-level deadlock:
# =============================
# "Thread-1":
#   waiting to lock monitor 0x00007f8b4c0062b8
#   which is held by "Thread-0"
```

**Programmatic Detection**

```java
import java.lang.management.*;

public class DeadlockDetector {
    public static void detectDeadlock() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] deadlockedThreads = bean.findDeadlockedThreads();

        if (deadlockedThreads != null) {
            System.out.println("Deadlock detected!");
            for (long id : deadlockedThreads) {
                ThreadInfo info = bean.getThreadInfo(id);
                System.out.println(info.getThreadName() +
                    " blocked on " + info.getLockName());
            }
        }
    }
}
```

## Common Interview Questions

**Q: What is a deadlock?**

A deadlock is a situation where two or more threads are blocked forever, each waiting for a resource held by the other. The threads form a circular dependency and cannot make progress.

**Q: What are the four conditions for deadlock?**

1. Mutual Exclusion: Resources cannot be shared.
2. Hold and Wait: Thread holds resources while waiting for others.
3. No Preemption: Resources cannot be forcibly taken.
4. Circular Wait: Circular chain of threads waiting for each other.

**Q: How do you prevent deadlocks?**

- Lock Ordering: Always acquire locks in the same global order.
- Lock Timeout: Use `tryLock()` with timeout.
- Avoid Nested Locks: Minimize holding multiple locks.

## Key Takeaways

- Deadlock occurs when threads form a circular wait for locks.
- Four conditions must all be present for deadlock.
- Breaking any one condition prevents deadlock.
- Lock ordering is the most practical prevention strategy.
- `tryLock()` with timeout provides a fallback mechanism.
- `jstack` and `ThreadMXBean` can detect deadlocks.

---

# Java Locks: Complete Guide

*Core Java - Concurrency*

From `synchronized` to `StampedLock` — understanding every lock type in Java 21+.

## Lock Types Overview

Java provides multiple locking mechanisms, each designed for specific use cases. Choosing the right lock can make the difference between a system that scales and one that bottlenecks.

| Lock Type | Package | Reentrancy | Fairness | Best For |
|---|---|---|---|---|
| `synchronized` | Built-in | Yes | No | Simple mutual exclusion |
| `ReentrantLock` | `java.util.concurrent` | Yes | Optional | Advanced locking, tryLock, interruptible |
| `ReentrantReadWriteLock` | `java.util.concurrent` | Yes | Optional | Read-heavy workloads |
| `StampedLock` | `java.util.concurrent` | No | No | Maximum read throughput |
| `Semaphore` | `java.util.concurrent` | N/A | Optional | Resource pooling, rate limiting |

**Java 21+ Virtual Threads Note**

With virtual threads, `synchronized` blocks can pin the carrier thread. For virtual thread compatibility:

- Prefer `ReentrantLock` over `synchronized` for long-held locks.
- Keep `synchronized` blocks extremely short.
- Avoid `synchronized` around blocking I/O.

## 1. synchronized (Intrinsic Lock)

The `synchronized` keyword uses the object's intrinsic monitor lock. It's simple but has limitations.

```java
// Method-level synchronization
public synchronized void increment() {
    count++;
}

// Block-level synchronization (preferred)
public void increment() {
    synchronized (this) {
        count++;
    }
}

// Synchronizing on a dedicated lock object (best)
private final Object lock = new Object();
public void increment() {
    synchronized (lock) {
        count++;
    }
}
```

**Advantages**

- Simple syntax
- Automatic release on exit
- Reentrant by default
- Works with wait/notify

**Limitations**

- No `tryLock()` capability
- No fairness option
- Cannot interrupt a waiting thread
- Pins virtual threads (Java 21+)

**Virtual Threads Warning (Java 21+)**

When a virtual thread holds a synchronized lock and blocks, it pins the carrier thread. This defeats the scalability benefit of virtual threads. Use `ReentrantLock` instead for long-held locks.

## 2. ReentrantLock

`ReentrantLock` provides the same mutual exclusion as `synchronized` but with additional features like `tryLock`, timed waiting, and fairness.

```java
private final ReentrantLock lock = new ReentrantLock();

public void doWork() {
    lock.lock();  // Blocks until acquired
    try {
        // Critical section
    } finally {
        lock.unlock();  // MUST unlock in finally!
    }
}

// Non-blocking attempt
public boolean tryDoWork() {
    if (lock.tryLock()) {
        try {
            // Critical section
            return true;
        } finally {
            lock.unlock();
        }
    }
    return false;  // Lock not available
}

// Timed attempt
public boolean timedDoWork() throws InterruptedException {
    if (lock.tryLock(1, TimeUnit.SECONDS)) {
        try {
            // Got lock within 1 second
        } finally {
            lock.unlock();
        }
    }
}
```

**Fair vs Non-Fair**

```java
// Non-fair (default) - better throughput
ReentrantLock lock = new ReentrantLock();

// Fair - FIFO ordering, prevents starvation
ReentrantLock fairLock = new ReentrantLock(true);
```

**When to Use ReentrantLock**

- Need `tryLock()` for non-blocking attempts
- Need interruptible lock acquisition
- Need fairness guarantees
- Using with virtual threads (Java 21+)
- Need multiple `Condition` objects

## 3. ReentrantReadWriteLock

Optimizes for read-heavy workloads. Multiple readers can hold the lock simultaneously, but writers get exclusive access.

```java
private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
private final Lock readLock = rwLock.readLock();
private final Lock writeLock = rwLock.writeLock();

private Map<String, String> cache = new HashMap<>();

public String get(String key) {
    readLock.lock();
    try {
        return cache.get(key);  // Multiple readers OK
    } finally {
        readLock.unlock();
    }
}

public void put(String key, String value) {
    writeLock.lock();
    try {
        cache.put(key, value);  // Exclusive access
    } finally {
        writeLock.unlock();
    }
}
```

**Lock Compatibility**

| Current Holders | Read Request | Write Request |
|---|---|---|
| None | Granted | Granted |
| Readers (1+) | Granted | Blocked (waits for all readers) |
| Writer (1) | Blocked | Blocked |

**Use Case: Configuration Cache**

Perfect for caches where reads vastly outnumber writes. 100 readers can access simultaneously, but configuration updates get exclusive access.

## 4. StampedLock (Java 8+)

The highest-performance lock for read-heavy workloads. Supports optimistic reads that don't block writers.

```java
private final StampedLock sl = new StampedLock();
private double x, y;

// Optimistic read - doesn't acquire lock!
public double distanceFromOrigin() {
    long stamp = sl.tryOptimisticRead();  // Non-blocking
    double currentX = x, currentY = y;

    if (!sl.validate(stamp)) {  // Check if write occurred
        stamp = sl.readLock();  // Fallback to read lock
        try {
            currentX = x;
            currentY = y;
        } finally {
            sl.unlockRead(stamp);
        }
    }
    return Math.sqrt(currentX * currentX + currentY * currentY);
}

// Write lock - exclusive
public void move(double deltaX, double deltaY) {
    long stamp = sl.writeLock();
    try {
        x += deltaX;
        y += deltaY;
    } finally {
        sl.unlockWrite(stamp);
    }
}
```

**StampedLock Caveats**

- Not reentrant — will deadlock if the same thread tries to acquire twice.
- Stamps must match — unlock with the same stamp returned by lock.
- More complex — optimistic reads need the validation pattern.
- Best for short read operations with rare writes.

**Lock Mode Conversion**

```java
// Upgrade: optimistic -> read -> write
long stamp = sl.tryOptimisticRead();
// ... read data ...
if (!sl.validate(stamp)) {
    stamp = sl.readLock();  // Upgrade to read
}
// If need to write:
long writeStamp = sl.tryConvertToWriteLock(stamp);
if (writeStamp == 0L) {
    sl.unlockRead(stamp);
    writeStamp = sl.writeLock();  // Acquire write lock
}
// ... write data ...
sl.unlockWrite(writeStamp);
```

## 5. Semaphore

Controls access to a resource with a limited number of permits. Unlike locks, permits are not tied to threads — any thread can release.

```java
// Connection pool with max 10 connections
private final Semaphore semaphore = new Semaphore(10);
private final Queue<Connection> pool = new ConcurrentLinkedQueue<>();

public Connection getConnection() throws InterruptedException {
    semaphore.acquire();  // Blocks if no permits
    return pool.poll();
}

public void releaseConnection(Connection conn) {
    pool.offer(conn);
    semaphore.release();  // Return permit
}

// Rate limiting example
private final Semaphore rateLimiter = new Semaphore(100);  // 100 concurrent requests

public Response handleRequest(Request req) {
    if (rateLimiter.tryAcquire()) {
        try {
            return processRequest(req);
        } finally {
            rateLimiter.release();
        }
    }
    return Response.tooManyRequests();
}
```

**Common Use Cases**

- **Connection Pooling:** Limit concurrent database connections.
- **Rate Limiting:** Control request throughput.
- **Resource Bounding:** Limit memory usage by capping concurrent operations.
- **Producer-Consumer:** Coordinate between producers and consumers.

## Which Lock Should You Use?

- **synchronized** — Simple mutual exclusion with automatic release. Use when: simple cases, short critical sections, not using virtual threads.
- **ReentrantLock** — Advanced locking with tryLock and fairness. Use when: need tryLock, interruptibility, fairness, or virtual thread compatibility.
- **ReadWriteLock** — Shared reads, exclusive writes. Use when: read-heavy workloads (90%+ reads), cache implementations.
- **StampedLock** — Optimistic reads for maximum throughput. Use when: extreme read performance needed, short operations, no reentrancy needed.
- **Semaphore** — Permit-based resource control. Use when: limiting concurrent access count, connection pools, rate limiting.

**Decision Flowchart**

- Need to limit concurrent access count? → `Semaphore`
- Read-heavy with rare writes? → `ReadWriteLock` or `StampedLock`
- Need tryLock or fairness? → `ReentrantLock`
- Using virtual threads with blocking I/O? → `ReentrantLock`
- Simple case, short lock hold? → `synchronized`

## Key Takeaways

- `synchronized` is simple but limited — no tryLock, no fairness, pins virtual threads.
- `ReentrantLock` offers tryLock, interruptibility, and fairness options.
- `ReadWriteLock` allows concurrent reads, exclusive writes — ideal for caches.
- `StampedLock` provides optimistic reads for maximum throughput but is not reentrant.
- `Semaphore` controls permit count — perfect for pools and rate limiting.
- Always unlock in a `finally` block with explicit locks.
- For Java 21+ virtual threads, prefer `ReentrantLock` over `synchronized`.

---

# Virtual Threads

## 1. The Problem Statement

Traditional Java applications use platform threads (OS threads) for concurrency. Each platform thread consumes ~1MB of stack memory and is managed by the operating system. When your application handles thousands of concurrent requests — each waiting on database calls, HTTP responses, or file I/O — those threads sit idle, blocked, doing nothing. You quickly hit limits: memory exhaustion, context-switching overhead, and poor throughput. This is the "thread-per-request" bottleneck that has plagued Java servers for decades.

## 2. What are Virtual Threads

Virtual Threads (introduced in Java 21 via Project Loom) are lightweight threads managed by the JVM, not the OS. They use only ~1KB of memory and can number in the millions. When a virtual thread encounters a blocking operation (like a database call), it yields instead of blocking — the underlying carrier thread immediately picks up another virtual thread. This means your application can handle massive concurrency without the resource overhead of traditional threads.

## 3. Platform Threads vs Virtual Threads

**Platform Threads:**

- Memory: ~1MB per thread
- Limited to thousands (constrained by OS)
- High blocking cost — thread sits completely idle during I/O
- Managed by the Operating System
- Best suited for CPU-intensive tasks

**Virtual Threads:**

- Memory: ~1KB per thread (1000x lighter)
- Can scale to millions (managed by JVM)
- Near-zero blocking cost — yields to carrier thread during I/O
- Managed by the JVM runtime
- Best suited for I/O-bound operations

## 4. When to Use Virtual Threads

- High-concurrency I/O operations (HTTP clients, database queries)
- REST API servers handling thousands of simultaneous requests
- Microservices with frequent network calls
- Applications where threads spend most time waiting (not computing)
- Replacing reactive/async code with simpler synchronous style

**When NOT to use:**

CPU-bound tasks (parallel computation, data processing) — platform threads are still better here since virtual threads don't add parallelism, only concurrency.

## 5. Key Differences

| Aspect | Platform | Virtual |
|---|---|---|
| Memory | ~1MB | ~1KB |
| Max Count | Thousands | Millions |
| Managed | OS | JVM |
| Blocking | High Cost | Near Zero |
| Best For | CPU Tasks | I/O Tasks |

---

# Collections Under the Hood

*Java Collections*

Visualize how `ArrayList`, `LinkedList`, `HashMap`, and `TreeMap` work internally.

## Why Understanding Internals Matters

Knowing how collections work internally helps you make better decisions about which collection to use. In interviews, you'll often be asked about time complexity, resizing behavior, and collision handling.

**What You'll Learn**

- **ArrayList:** Dynamic array resizing and element shifting
- **LinkedList:** Node linking and pointer manipulation
- **HashMap:** Hashing, bucket arrays, and collision resolution
- **TreeMap:** Red-Black tree structure and balancing

## ArrayList: Dynamic Array

`ArrayList` uses a resizable array internally. When you add elements beyond capacity, it creates a new array with 1.5x capacity and copies all elements.

**Java 8+ Implementation Detail**

`ArrayList` uses lazy initialization. The internal array starts as an empty shared instance and grows to capacity 10 on the first `add()` call.

**Key Operations**

| Operation | Time | Why? |
|---|---|---|
| `get(index)` | O(1) | Direct array access: `elementData[index]` |
| `add(element)` | O(1)* | Append to end. *Amortized - resize is O(n) but rare |
| `add(index, element)` | O(n) | Must shift all elements after index to the right |
| `remove(index)` | O(n) | Must shift all elements after index to the left |
| `contains(element)` | O(n) | Linear search through array |

**Resizing Formula**

```java
int newCapacity = oldCapacity + (oldCapacity >> 1);  // 1.5x growth
```

- Default initial capacity: 10 (on first add)
- Growth: 10 -> 15 -> 22 -> 33 -> 49 -> ...
- Resize copies all elements via `Arrays.copyOf()`

```java
// Actual ArrayList source (simplified)
public class ArrayList<E> {
    transient Object[] elementData;  // The backing array
    private int size;                // Number of elements

    private static final int DEFAULT_CAPACITY = 10;

    public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // Grow if needed
        elementData[size++] = e;           // O(1)
        return true;
    }

    public void add(int index, E element) {
        rangeCheckForAdd(index);
        ensureCapacityInternal(size + 1);
        System.arraycopy(elementData, index,    // Shift right - O(n)
                         elementData, index + 1,
                         size - index);
        elementData[index] = element;
        size++;
    }
}
```

## LinkedList: Doubly Linked Nodes

`LinkedList` uses doubly-linked nodes. Each node holds data plus pointers to previous and next nodes. This makes insertion/deletion at ends O(1), but random access O(n).

**LinkedList Implements Deque**

Since Java 1.6, `LinkedList` implements `Deque`, making it suitable for queue (FIFO) or stack (LIFO) with O(1) operations at both ends.

**Node Structure**

```java
private static class Node<E> {
    E item;           // The data
    Node<E> next;     // Reference to next node
    Node<E> prev;     // Reference to previous node
}

transient Node<E> first;  // Head of list
transient Node<E> last;   // Tail of list
transient int size;
```

**Key Operations**

| Operation | Time | Why? |
|---|---|---|
| `addFirst()` / `addLast()` | O(1) | Just update first/last pointers |
| `removeFirst()` / `removeLast()` | O(1) | Just update first/last pointers |
| `get(index)` | O(n) | Must traverse from first or last |
| `add(index, element)` | O(n) | O(n) traverse + O(1) insert |

**When to Use LinkedList**

- Frequent insertions/deletions at both ends (Queue/Deque)
- You have an Iterator and insert/remove during traversal
- You don't need random access by index

## HashMap: Hash Table with Chaining

`HashMap` uses an array of buckets (`table[]`). Keys are hashed to determine bucket index. Collisions are handled by chaining (linked list, or tree for 8+ entries).

**How put(key, value) Works**

1. **Hash:** `(h = key.hashCode()) ^ (h >>> 16)` — spreads bits
2. **Index:** `hash & (table.length - 1)` — bucket position
3. **Insert:** Add to bucket (chain if collision)
4. **Resize:** If size > threshold, double table and rehash

```java
// HashMap constants (Java 8+)
static final int DEFAULT_INITIAL_CAPACITY = 16;   // Must be power of 2
static final float DEFAULT_LOAD_FACTOR = 0.75f;
static final int TREEIFY_THRESHOLD = 8;           // List -> Tree
static final int UNTREEIFY_THRESHOLD = 6;         // Tree -> List
static final int MIN_TREEIFY_CAPACITY = 64;       // Min table size for tree

// Hash function - spreads bits to reduce collisions
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}

// Bucket index (fast modulo for power of 2)
int index = hash & (table.length - 1);
```

**Key Operations**

| Operation | Average | Worst (pre-Java 8) | Worst (Java 8+) |
|---|---|---|---|
| `get(key)` | O(1) | O(n) | O(log n) |
| `put(key, value)` | O(1) | O(n) | O(log n) |
| `remove(key)` | O(1) | O(n) | O(log n) |

**Treeification (Java 8+)**

When a bucket exceeds 8 entries AND table size >= 64:

- Linked list converts to Red-Black tree.
- Worst case improves from O(n) to O(log n).
- Converts back to list when entries drop to 6.

## TreeMap: Red-Black Tree

`TreeMap` uses a Red-Black tree — a self-balancing binary search tree. Keys are always sorted, and all operations are guaranteed O(log n).

**Red-Black Tree Properties**

- Every node is RED or BLACK.
- Root is always BLACK.
- All leaves (null) are BLACK.
- RED nodes have only BLACK children.
- Every path from root to leaf has the same BLACK count.

These rules ensure height is at most `2 * log(n+1)`.

**Key Operations**

| Operation | Time | Why? |
|---|---|---|
| `get(key)` | O(log n) | Binary search down tree |
| `put(key, value)` | O(log n) | Find position + rebalance |
| `firstKey()` / `lastKey()` | O(log n) | Traverse to leftmost/rightmost |
| `subMap(from, to)` | O(log n) | Returns view backed by tree |

**When to Use TreeMap**

- Need keys in sorted order
- Need range queries: `subMap()`, `headMap()`, `tailMap()`
- Need navigation: `floorKey()`, `ceilingKey()`, `higherKey()`

## Quick Comparison

| Collection | Structure | Order | Null Keys | Best For |
|---|---|---|---|---|
| ArrayList | Dynamic Array | Insertion | Yes | Random access |
| LinkedList | Doubly Linked | Insertion | Yes | Queue/Deque |
| HashMap | Hash Table | None | Yes (1) | Fast lookup |
| TreeMap | Red-Black Tree | Sorted | No | Sorted map |

## Key Takeaways

- **ArrayList:** Best for random access. Avoid inserts in middle.
- **LinkedList:** Best for queue/stack. Avoid `get(index)`.
- **HashMap:** Best for fast lookup. Override `hashCode()` properly.
- **TreeMap:** Best for sorted keys. Keys must be Comparable.

---

# Choosing the Right Collection

*Java Collections*

## The Collection Selection Problem

Java provides 15+ collection implementations. Choosing the wrong one can cause performance issues, bugs, or unnecessary complexity. This chapter helps you make the right choice quickly.

**Key Questions to Ask**

1. Do you need key-value pairs or just values?
2. Do you need elements in sorted order?
3. Are duplicates allowed?
4. Do you need thread safety?
5. What operations are most frequent? (add, get, search, iterate)

## Time Complexity Comparison

| Collection | Add | Remove | Get/Contains | Iterate |
|---|---|---|---|---|
| ArrayList | O(1)* | O(n) | O(1)/O(n) | O(n) |
| LinkedList | O(1) | O(1)** | O(n) | O(n) |
| HashSet | O(1) | O(1) | O(1) | O(n) |
| LinkedHashSet | O(1) | O(1) | O(1) | O(n) |
| TreeSet | O(log n) | O(log n) | O(log n) | O(n) |
| HashMap | O(1) | O(1) | O(1) | O(n) |
| LinkedHashMap | O(1) | O(1) | O(1) | O(n) |
| TreeMap | O(log n) | O(log n) | O(log n) | O(n) |
| ArrayDeque | O(1) | O(1) | O(n) | O(n) |
| PriorityQueue | O(log n) | O(log n) | O(n) | O(n) |
| ConcurrentHashMap | O(1) | O(1) | O(1) | O(n) |
| CopyOnWriteArrayList | O(n) | O(n) | O(1) | O(n) |

\* Amortized, O(n) when resizing. \*\* O(1) at ends, O(n) by index.

## Quick Reference Cards

**ArrayList** — implements `List`
Default choice for lists. Fast random access, slow inserts in middle.
`get(i)` O(1) · `add()` O(1)* · `contains()` O(n)

**LinkedList** — implements `List`, `Deque`
Best for queue/deque operations. Avoid for random access.
`addFirst()` O(1) · `addLast()` O(1) · `get(i)` O(n)

**HashSet** — implements `Set`
Fast uniqueness check. No order, no duplicates.
`add()` O(1) · `contains()` O(1) · `remove()` O(1)

**TreeSet** — implements `SortedSet`, `NavigableSet`
Sorted unique elements. Range queries.
`add()` O(log n) · `first()` O(log n) · `subSet()` O(log n)

**HashMap** — implements `Map`
Default choice for key-value. Fastest lookup.
`put()` O(1) · `get()` O(1) · `containsKey()` O(1)

**TreeMap** — implements `SortedMap`, `NavigableMap`
Sorted keys. Range queries on keys.
`put()` O(log n) · `firstKey()` O(log n) · `subMap()` O(log n)

**ArrayDeque** — implements `Deque`
Best stack/queue. Faster than LinkedList.
`push()` O(1) · `pop()` O(1) · `offer()` O(1)

**ConcurrentHashMap** — implements `ConcurrentMap`
Thread-safe map. Better than synchronized HashMap.
`putIfAbsent()` O(1) · `computeIfAbsent()` O(1)

## Decision Flowchart

Follow this flowchart to quickly narrow down your choice:

```
Need key-value pairs?
├── YES -> Need sorted keys?
│         ├── YES -> TreeMap
│         └── NO -> Need insertion order?
│                  ├── YES -> LinkedHashMap
│                  └── NO -> Thread safe?
│                           ├── YES -> ConcurrentHashMap
│                           └── NO -> HashMap (default)
│
└── NO -> Allow duplicates?
         ├── YES -> Need FIFO/LIFO?
         │         ├── YES -> ArrayDeque (stack/queue)
         │         └── NO -> Need random access?
         │                  ├── YES -> ArrayList (default)
         │                  └── NO -> LinkedList
         │
         └── NO (Set) -> Need sorted?
                       ├── YES -> TreeSet
                       └── NO -> Need insertion order?
                                ├── YES -> LinkedHashSet
                                └── NO -> HashSet (default)
```

## Common Mistakes to Avoid

**1. Using LinkedList for Random Access**

`linkedList.get(5000)` is O(n) — must traverse 5000 nodes!

Fix: Use `ArrayList` for index-based access.

**2. Using ArrayList as a Queue**

`arrayList.remove(0)` shifts all elements — O(n)!

Fix: Use `ArrayDeque` for FIFO operations.

**3. Using TreeMap When Order Doesn't Matter**

`TreeMap` is O(log n) for everything. `HashMap` is O(1).

Fix: Use `HashMap` unless you need sorted keys.

**4. Using Vector or Hashtable**

These are legacy classes with unnecessary synchronization overhead.

Fix: Use `ArrayList`/`HashMap` or their concurrent alternatives.

**5. Not Overriding hashCode() with equals()**

`HashSet`/`HashMap` won't work correctly if `equals()` is overridden but `hashCode()` isn't.

Fix: Always override both together.

## Key Takeaways

**Default Choices**

- List: `ArrayList` (unless you need queue operations)
- Set: `HashSet` (unless you need order/sorting)
- Map: `HashMap` (unless you need order/sorting)
- Queue/Stack: `ArrayDeque` (faster than LinkedList)
- Thread-safe Map: `ConcurrentHashMap` (not Hashtable)

**When to Use Alternatives**

- `LinkedHashMap`/`Set`: When insertion order matters (LRU cache)
- `TreeMap`/`Set`: When you need sorted order or range queries
- `LinkedList`: Only for frequent add/remove at both ends
- `PriorityQueue`: When you need elements in priority order
- `CopyOnWriteArrayList`: Read-heavy, write-rare concurrent scenarios

---

# Collections in Action: Real-World Patterns

*Java Collections*

7 essential patterns every Java developer should know, visualized step by step.

## From Theory to Practice

Knowing how collections work internally is only half the battle. The real skill is recognizing which pattern to apply when you see a problem. This chapter covers 7 patterns that appear constantly in interviews and production code.

**Patterns Covered**

1. **Frequency Counting** — HashMap for counting occurrences
2. **Deduplication** — HashSet for removing duplicates
3. **LRU Cache** — LinkedHashMap with eviction policy
4. **Top-K Elements** — PriorityQueue (min-heap) for finding top items
5. **Grouping** — Map of Lists for categorizing data
6. **Stack Problems** — ArrayDeque for bracket matching and undo
7. **Range Queries** — TreeMap for sorted lookups and ranges

## Pattern 1: Frequency Counting

Use a `HashMap<K, Integer>` to count how often each element appears. This is one of the most common interview patterns.

```java
// Classic frequency count
Map<String, Integer> freq = new HashMap<>();
for (String word : words) {
    freq.merge(word, 1, Integer::sum);  // Java 8+
}

// Alternative using getOrDefault
for (String word : words) {
    freq.put(word, freq.getOrDefault(word, 0) + 1);
}

// With Streams (Collectors.groupingBy + counting)
Map<String, Long> freq = words.stream()
    .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
```

**When to Use**

- Word frequency / character frequency in strings
- Vote counting, histogram building
- Finding most/least common elements
- Anagram detection (compare character frequency maps)

## Pattern 2: Deduplication

Use a `HashSet` to track seen elements and filter out duplicates in O(1) per check.

```java
// Remove duplicates from a list
List<String> unique = new ArrayList<>(new LinkedHashSet<>(list));
// LinkedHashSet preserves insertion order

// Deduplication during stream processing
Set<String> seen = new HashSet<>();
for (Event event : eventStream) {
    if (seen.add(event.getId())) {  // returns false if already present
        process(event);             // only process first occurrence
    }
}

// With Streams
List<String> unique = list.stream().distinct().toList();
```

**Important: hashCode() and equals()**

For custom objects, `HashSet.add()` uses `hashCode()` to find the bucket and `equals()` to check for duplicates. If you override `equals()`, you must also override `hashCode()`.

## Pattern 3: LRU Cache

A Least Recently Used cache evicts the entry that hasn't been accessed for the longest time. `LinkedHashMap` with `accessOrder=true` does the heavy lifting.

```java
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);  // accessOrder = true
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;  // evict when over capacity
    }
}

// Usage
LRUCache<String, String> cache = new LRUCache<>(100);
cache.put("user:1", "Alice");
cache.get("user:1");  // moves to most-recently-used
```

**How accessOrder Works**

When `accessOrder=true`, every `get()` or `put()` moves that entry to the tail of the internal linked list. The head is always the least recently used entry. When `removeEldestEntry()` returns `true`, the head entry is evicted.

## Pattern 4: Top-K Elements

Use a `PriorityQueue` (min-heap) of size K. For each element: if heap has room, add it. If the new element is larger than the min, remove min and add new.

```java
// Find top K largest elements from a stream
public static List<Integer> topK(int[] nums, int k) {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();  // min at top

    for (int num : nums) {
        if (minHeap.size() < k) {
            minHeap.offer(num);
        } else if (num > minHeap.peek()) {
            minHeap.poll();   // remove smallest
            minHeap.offer(num); // add new larger element
        }
    }

    return new ArrayList<>(minHeap);  // contains top K elements
}

// Time: O(n log k) - much better than sorting O(n log n) for small k
// Space: O(k)
```

**Why Min-Heap for Top-K?**

A min-heap of size K keeps the K largest elements seen so far. The root (min) acts as a gatekeeper: anything smaller is rejected. This is O(n log k) vs. sorting which is O(n log n).

## Pattern 5: Grouping

Use `Map<K, List<V>>` to group elements by a key. Java 8+ `Collectors.groupingBy()` makes this clean.

```java
// Manual grouping
Map<String, List<Employee>> byDept = new HashMap<>();
for (Employee emp : employees) {
    byDept.computeIfAbsent(emp.getDept(), k -> new ArrayList<>())
          .add(emp);
}

// Stream grouping (same result, cleaner code)
Map<String, List<Employee>> byDept = employees.stream()
    .collect(Collectors.groupingBy(Employee::getDept));

// Grouping with downstream collector
Map<String, Long> countByDept = employees.stream()
    .collect(Collectors.groupingBy(Employee::getDept, Collectors.counting()));

// Partitioning (special case: boolean grouping)
Map<Boolean, List<Employee>> seniorVsJunior = employees.stream()
    .collect(Collectors.partitioningBy(e -> e.getYears() > 5));
```

**computeIfAbsent() is Key**

`map.computeIfAbsent(key, k -> new ArrayList<>())` creates the list only if the key is absent, then returns it. This avoids the boilerplate null-check-then-create pattern.

## Pattern 6: Stack Problems

Use `ArrayDeque` as a stack for bracket matching, expression evaluation, undo/redo, and depth-first traversal.

```java
// Bracket matching
public static boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();
    Map<Character, Character> pairs = Map.of(')', '(', ']', '[', '}', '{');

    for (char c : s.toCharArray()) {
        if ("([{".indexOf(c) >= 0) {
            stack.push(c);                // opening: push
        } else if (pairs.containsKey(c)) {
            if (stack.isEmpty() || stack.pop() != pairs.get(c)) {
                return false;             // no match
            }
        }
    }
    return stack.isEmpty();  // all brackets matched
}
```

**ArrayDeque, not Stack**

The `java.util.Stack` class is legacy — it extends `Vector`, adding unnecessary synchronization. Always use `ArrayDeque` for stack operations. It's faster and has a cleaner API.

## Pattern 7: Range Queries

Use `TreeMap` when you need sorted keys and operations like "find all entries between A and B" or "find the closest key."

```java
TreeMap<Integer, String> map = new TreeMap<>();

// Navigation methods
map.floorKey(50);      // largest key <= 50
map.ceilingKey(50);    // smallest key >= 50
map.lowerKey(50);      // largest key < 50 (strictly less)
map.higherKey(50);     // smallest key > 50 (strictly greater)

// Range queries
map.subMap(20, true, 60, true);  // all entries with key 20..60 inclusive
map.headMap(50, true);           // all entries with key <= 50
map.tailMap(50, true);           // all entries with key >= 50

// Practical: Find events in a time window
TreeMap<LocalDateTime, Event> events = new TreeMap<>();
var inRange = events.subMap(startTime, true, endTime, true);
```

**Real-World TreeMap Use Cases**

- Scheduling systems — find next available time slot
- Rate limiting — count requests in a sliding time window
- Financial systems — find closest price point
- Leaderboards — find rank range queries

## Pattern Quick Reference

| Pattern | Collection | Key Method | Time |
|---|---|---|---|
| Frequency Count | HashMap | `merge()` / `getOrDefault()` | O(n) |
| Deduplication | HashSet | `add()` returns boolean | O(n) |
| LRU Cache | LinkedHashMap | `removeEldestEntry()` | O(1) per op |
| Top-K | PriorityQueue | `offer()` / `poll()` | O(n log k) |
| Grouping | HashMap + List | `computeIfAbsent()` | O(n) |
| Stack Problems | ArrayDeque | `push()` / `pop()` | O(n) |
| Range Queries | TreeMap | `subMap()` / `floorKey()` | O(log n) |

## Key Takeaways

- Most interview collection problems boil down to one of these 7 patterns.
- `merge()`, `computeIfAbsent()`, and `getOrDefault()` eliminate boilerplate.
- `PriorityQueue` (min-heap) of size K is the standard Top-K approach.
- `LinkedHashMap` with `accessOrder=true` is the simplest LRU cache.
- Always use `ArrayDeque` over `Stack` for stack operations.
- `TreeMap` shines when you need sorted keys or range lookups.

---

# Java Collections Interview Revision Guide

This comprehensive guide is designed to help you refresh and strengthen your understanding of Java Collections concepts in preparation for technical interviews.

**In this chapter, you'll learn:**

- Fail-Fast vs Fail-Safe Iterators
- ArrayList vs LinkedList
- HashMap vs Hashtable
- HashSet vs TreeSet
- How HashMap works internally
- HashMap Initial Capacity and Load Factor
- EnumSet and EnumMap
- Sorting using Comparable and Comparator
- Key Interfaces in Java Collections
- Collection vs Collections
- Custom Collections
- HashMap vs LinkedHashMap
- HashMap vs ConcurrentHashMap
- How TreeMap Works
- PriorityQueue vs Regular Queue
- Deep Copy vs Shallow Copy
- Handling Duplicates in Collections
- Synchronized Collections vs Concurrent Collections

## What Is the Difference Between Fail-Safe and Fail-Fast Iterators?

Fail-Safe and Fail-Fast iterators are concepts used to describe the behavior of iterators in the face of concurrent modifications.

**Fail-Fast Iterators:**

- **Definition:** Fail-Fast systems abort operation as-fast-as-possible, exposing failures immediately and stopping the whole operation. They immediately throw a `ConcurrentModificationException` if they detect any structural modifications to the collection while iterating.
- **Behavior:** These iterators directly access the collection's data structure, making them highly sensitive to modifications.
- **Example:** Iterators of most Java Collections Framework classes such as `ArrayList`, `HashSet`, `HashMap` are Fail-Fast.

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FailFastTest {
    public void failFast() {
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> iterator = numbers.iterator();
        while (iterator.hasNext()) {
            Integer number = iterator.next();
            numbers.add(50); // This line will throw error
        }
    }
}
```

**Fail-Safe Iterators:**

- **Definition:** Fail-Safe iterators do not throw exceptions when the underlying collection is modified. Instead, they operate on a clone or a snapshot of the collection's data.
- **Behavior:** These iterators maintain a copy of the collection's state at the time the iterator was created.
- **Example:** Iterators of concurrent collections such as `CopyOnWriteArrayList`, `ConcurrentHashMap` in Java are Fail-Safe.

```java
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class FailSafeTest {
    public void failSafe() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(Arrays.asList("a", "b", "c"));
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals("b")) {
                list.remove("b"); // This will not cause an exception
            }
        }
        System.out.println(list); // ["a", "c"]
    }
}
```

## What is the Difference Between LinkedList and ArrayList?

LinkedList and ArrayList both are data structures in Java used to store data.

**ArrayList:**

- **Underlying Data Structure:** Uses a dynamic array to store elements. Provides fast random access to elements using an index.
- **Time Complexity:** Uses O(1) for `get` and `set` operations and appending element to last. But it takes O(n) to add or remove an element in between.
- **Use Case:** Best suited for applications where frequent access to elements using an index is required. Ideal when the size of the list is relatively stable, and insertion or deletion operations are infrequent.

**LinkedList:**

- **Underlying Data Structure:** Uses a doubly linked list to store elements. Does not support efficient random access. Elements are accessed sequentially.
- **Time Complexity:** Uses O(n) for `get` and `set` operations because it requires traversal from the beginning or end to the desired index. O(1) for adding or removing elements at the beginning or end, but O(n) for accessing elements at arbitrary positions.
- **Use Case:** Best suited for applications where frequent insertions and deletions are required, especially at the beginning or middle of the list.

```java
import java.util.ArrayList;
import java.util.List;

public class ArrayListExample {
    public static void main(String[] args) {
        // Create an ArrayList
        List<String> arrayList = new ArrayList<>();

        // Add elements to the ArrayList
        arrayList.add("Apple");
        arrayList.add("Banana");
        arrayList.add("Cherry");

        // Access elements by index
        System.out.println("Element at index 1: " + arrayList.get(1)); // Element at index 1: Banana

        // Iterate through the ArrayList
        System.out.println("ArrayList elements:");
        for (String fruit : arrayList) {
            System.out.println(fruit);
        }
        // Output:
        // ArrayList elements:
        // Apple
        // Banana
        // Cherry

        // Remove an element
        arrayList.remove(1); // Removes "Banana"
        System.out.println("After removal:");
        for (String fruit : arrayList) {
            System.out.println(fruit);
        }
        // After removal:
        // Apple
        // Cherry
    }
}
```

```java
import java.util.LinkedList;

public class LinkedListExample {
    public static void main(String[] args) {
        // Create a LinkedList
        LinkedList<String> linkedList = new LinkedList<>();

        // Add elements to the LinkedList
        linkedList.add("Apple");
        linkedList.add("Banana");
        linkedList.add("Cherry");

        // Access elements by index (not efficient)
        System.out.println("Element at index 1: " + linkedList.get(1)); // Banana

        // Iterate through the LinkedList
        System.out.println("LinkedList elements:");
        for (String fruit : linkedList) {
            System.out.println(fruit);
        }
        // LinkedList elements:
        // Apple
        // Banana
        // Cherry

        // Remove an element
        linkedList.remove(1); // Removes "Banana"
        System.out.println("After removal:");
        for (String fruit : linkedList) {
            System.out.println(fruit);
        }
        // After removal:
        // Apple
        // Cherry

        // Add element at the beginning and the end
        linkedList.addFirst("Mango");
        linkedList.addLast("Orange");
        System.out.println("After adding at the beginning and end:");
        for (String fruit : linkedList) {
            System.out.println(fruit);
        }
        // After adding at the beginning and end:
        // Mango
        // Apple
        // Cherry
        // Orange
    }
}
```

## What is the Difference Between HashMap and HashTable?

**1. Thread Safety**

- HashMap: Not thread-safe. Concurrent modification can lead to inconsistent state.
- Hashtable: Thread-safe. All methods are synchronized.

**2. Synchronization**

- HashMap: No internal synchronization. Needs external synchronization for thread safety.
- Hashtable: Methods are internally synchronized.

**3. Performance**

- HashMap: Faster because it does not have synchronization overhead.
- Hashtable: Slower due to synchronization overhead.

**4. Null Keys and Values**

- HashMap: Allows one null key and multiple null values.
- Hashtable: Does not allow null keys or null values.

**5. Iterator Type**

- HashMap: Fail-fast iterator, which throws `ConcurrentModificationException` if the map is modified during iteration.
- Hashtable: Fail-safe enumeration, which does not throw `ConcurrentModificationException`.

**6. Use Case**

- HashMap: Suitable for non-threaded applications where thread safety is not a concern.
- Hashtable: Suitable for threaded applications where thread safety is required.

**7. Legacy**

- HashMap: Considered modern and more versatile.
- Hashtable: Considered legacy; use `ConcurrentHashMap` for thread-safe operations.

```java
// HashMap allows one null key and any number of null values.
Map<String, Integer> hashMap = new HashMap<>();
hashMap.put(null, null);

// Hashtable does not allow null keys or values
Map<String, Integer> hashtable = new Hashtable<>();
hashtable.put(null, null); // exception
```

## What Is the Difference Between HashSet and TreeSet?

HashSet and TreeSet are both implementations of the `Set` interface in Java, but they have different underlying mechanisms, performance characteristics, and usage scenarios.

**HashSet:**

- **Underlying Data Structure:** Uses a hash table to store elements. Relies on hashing for its operations, meaning it uses the `hashCode` method to determine the storage location of elements.
- **Time Complexity:** O(1) average time complexity for `add`, `remove`, and `contains` operations.
- **Ordering:** Does not maintain any order of elements. The order of elements when iterating over the set is unpredictable and can change over time.
- **Use Case:** Suitable when you need fast lookups and do not care about the order of elements.

**TreeSet:**

- **Underlying Data Structure:** Uses a Red-Black tree (a self-balancing binary search tree) to store elements.
- **Time Complexity:** O(log n) for `add`, `remove`, and `contains` operations.
- **Ordering:** Maintains elements in a sorted order. The elements are sorted according to their natural ordering (using `Comparable`) or by a specified `Comparator`.
- **Use Case:** Suitable when you need to maintain a sorted order of elements.

```java
// HashSet Example
import java.util.HashSet;
import java.util.Set;

public class HashSetExample {
    public static void main(String[] args) {
        Set<String> hashSet = new HashSet<>();

        // Add elements to the HashSet
        hashSet.add("Apple");
        hashSet.add("Banana");
        hashSet.add("Cherry");

        // Check if an element exists
        System.out.println("Contains 'Apple': " + hashSet.contains("Apple")); // true

        // Remove an element
        hashSet.remove("Banana");

        // Iterate over the HashSet
        System.out.println("HashSet elements:");
        for (String fruit : hashSet) {
            System.out.println(fruit);
        }
        // Output:
        // HashSet elements:
        // Apple
        // Cherry
    }
}
```

```java
// TreeSet example
import java.util.Set;
import java.util.TreeSet;

public class TreeSetExample {
    public static void main(String[] args) {
        Set<String> treeSet = new TreeSet<>();

        // Add elements to the TreeSet
        treeSet.add("Apple");
        treeSet.add("Banana");
        treeSet.add("Cherry");

        // Check if an element exists
        System.out.println("Contains 'Apple': " + treeSet.contains("Apple")); // true

        // Remove an element
        treeSet.remove("Banana");

        // Iterate over the TreeSet
        System.out.println("TreeSet elements:");
        for (String fruit : treeSet) {
            System.out.println(fruit);
        }
        // Output:
        // TreeSet elements:
        // Apple
        // Cherry
    }
}
```

## How Is HashMap Implemented in Java (explain in detail)?

A HashMap is a data structure that allows you to store and retrieve key-value pairs efficiently. Think of it like a digital dictionary where you can look up values (like definitions) using unique keys (like words).

The `HashMap` class implements a hash map data structure using a resizable array of power-of-two size. When adding an element, the key's `hashCode` is calculated and its lower bits determine the array index (bucket). Accessing elements by array index is very fast (O(1)).

However, hash collisions occur when different keys produce the same index. In Java's `HashMap`, each bucket points to a red-black tree (a linked list before Java 8) of colliding elements. The HashMap traverses this tree to insert or find key-value pairs, replacing existing pairs with matching keys.

Retrieving an element involves calculating the key's `hashCode`, finding the bucket, and traversing the tree to match the key. HashMap operations generally have O(1) complexity but can degrade to O(log n) with many collisions. Using a good hash function helps maintain performance.

When the internal array fills up, the HashMap doubles its size, triggering a costly rehashing process. Thus, it's advisable to estimate the initial size to avoid frequent resizing.

Here is an implementation of HashMap in Java:

```java
// Entry.java
class Entry<K, V> {
    K key;
    V value;
    Entry<K, V> next;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}

// MyHashMap.java
public class MyHashMap<K, V> {
    private Entry<K, V>[] buckets;
    private static final int INITIAL_CAPACITY = 16;
    private int size = 0;

    public MyHashMap() {
        this.buckets = new Entry[INITIAL_CAPACITY];
    }

    // Hash function to calculate the index for a given key
    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    // Add or update a key-value pair
    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        Entry<K, V> newEntry = new Entry<>(key, value);

        // If bucket is empty, add the new entry
        if (buckets[bucketIndex] == null) {
            buckets[bucketIndex] = newEntry;
            size++;
        } else {
            // Traverse the bucket and update the value if key is found, otherwise append the new entry
            Entry<K, V> current = buckets[bucketIndex];
            Entry<K, V> previous = null;
            while (current != null) {
                if (current.key.equals(key)) {
                    current.value = value; // Update value
                    return;
                }
                previous = current;
                current = current.next;
            }
            previous.next = newEntry; // Append the new entry at the end
            size++;
        }
    }

    // Retrieve a value by key
    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        Entry<K, V> current = buckets[bucketIndex];

        // Traverse the bucket to find the key
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null; // Key not found
    }

    // Remove a key-value pair by key
    public V remove(K key) {
        int bucketIndex = getBucketIndex(key);
        Entry<K, V> current = buckets[bucketIndex];
        Entry<K, V> previous = null;

        // Traverse the bucket to find the key and remove the entry
        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    buckets[bucketIndex] = current.next; // Remove first entry in the bucket
                } else {
                    previous.next = current.next; // Bypass the entry to be removed
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }
        return null; // Key not found
    }

    // Return the number of key-value pairs in the map
    public int size() {
        return size;
    }

    // Check if the map is empty
    public boolean isEmpty() {
        return size == 0;
    }
}

// MyHashMapTest.java
public class MyHashMapTest {
    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();

        // Add some key-value pairs
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        // Retrieve values
        System.out.println("Value for 'one': " + map.get("one")); // 1
        System.out.println("Value for 'two': " + map.get("two")); // 2

        // Remove a key-value pair
        System.out.println("Removed value for 'two': " + map.remove("two")); // 2

        // Check size and content
        System.out.println("Size of map: " + map.size()); // 2
        System.out.println("Value for 'two': " + map.get("two")); // null

        // Check if map is empty
        System.out.println("Is map empty? " + map.isEmpty()); // false

        // Remove all elements
        map.remove("one");
        map.remove("three");

        // Check if map is empty
        System.out.println("Is map empty? " + map.isEmpty()); // true
    }
}
```

## What Is the Use of the Initial Capacity and Load Factor Parameters of a HashMap and Their Default Values?

**Initial Capacity:**

- **Purpose:** Sets the initial size of the internal array that stores the key-value pairs.
- **Default Value:** 16.
- **Effect:** If the specified initial capacity is not a power of two, it is adjusted to the next power of two. For example, an initial capacity of 10 will be adjusted to 16.
- **Usage:** Helps to minimize the number of resizings if the number of elements to be stored is known in advance, improving performance.

**Load Factor:**

- **Purpose:** Determines when to increase the size of the internal array to maintain performance.
- **Default Value:** 0.75.
- **Effect:** When the number of elements exceeds the product of the load factor and the current capacity, the array size is doubled, and the elements are rehashed.
- **Usage:** Balances time complexity of operations and space usage. A lower load factor reduces the likelihood of collisions but increases memory usage, while a higher load factor saves memory but may increase collisions and degrade performance.

Choosing the right `initialCapacity` can be challenging. Guava's `Maps.newHashMapWithExpectedSize()` and `Sets.newHashSetWithExpectedSize()` methods help create maps and sets sized to minimize resizing.

**Initial Capacity:** Determines the starting size of the internal array. **Load Factor:** Controls the threshold for resizing the array. **Default Settings:** 16 for initial capacity and 0.75 for load factor, providing a good balance of performance and memory usage for general use cases.

```java
// Creating a HashMap with initial capacity of 32 and load factor of 0.5
HashMap<Integer, String> map = new HashMap<>(32, 0.5f);
```

## Explain EnumSet and EnumMap

`EnumSet` and `EnumMap` are specialized data structures in Java that are specifically designed to work with enumerations (enums). Both provide efficient and type-safe ways to handle collections and mappings involving enums.

**EnumSet:**

- **Efficiency:** `EnumSet` is implemented internally as a bit vector, which makes it very fast and efficient in terms of both time and space compared to other `Set` implementations like `HashSet` or `TreeSet`.
- **Type-Safety:** It can only contain elements of the specified enum type, ensuring type safety.
- **Iteration Order:** Elements are maintained in their natural order (the order in which the enum constants are declared).
- **Null Handling:** `EnumSet` does not allow null elements.

```java
import java.util.EnumSet;

enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

public class EnumSetExample {
    public static void main(String[] args) {
        EnumSet<Day> weekend = EnumSet.of(Day.SATURDAY, Day.SUNDAY);
        System.out.println("Weekend days: " + weekend); // [SATURDAY, SUNDAY]

        EnumSet<Day> workdays = EnumSet.range(Day.MONDAY, Day.FRIDAY);
        System.out.println("Workdays: " + workdays); // [MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY]

        EnumSet<Day> allDays = EnumSet.allOf(Day.class);
        System.out.println("All days: " + allDays); // [MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY]
    }
}
```

**EnumMap:**

- **Efficiency:** `EnumMap` is implemented using arrays, which makes it very fast and memory-efficient compared to other `Map` implementations like `HashMap` or `TreeMap`.
- **Type-Safety:** It ensures type safety by requiring the keys to be of the specified enum type.
- **Iteration Order:** Entries are maintained in the natural order of their keys (the order in which the enum constants are declared).
- **Null Handling:** `EnumMap` does not allow null keys, but it allows null values.

```java
import java.util.EnumMap;

enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

public class EnumMapExample {
    public static void main(String[] args) {
        EnumMap<Day, String> activityMap = new EnumMap<>(Day.class);
        activityMap.put(Day.MONDAY, "Gym");
        activityMap.put(Day.TUESDAY, "Swimming");
        activityMap.put(Day.WEDNESDAY, "Running");

        for (Day day : Day.values()) {
            System.out.println(day + ": " + activityMap.getOrDefault(day, "No activity"));
        }
        // MONDAY: Gym
        // TUESDAY: Swimming
        // WEDNESDAY: Running
        // THURSDAY: No activity
        // FRIDAY: No activity
        // SATURDAY: No activity
        // SUNDAY: No activity
    }
}
```

## How to Use Comparable and Comparator Interfaces to Sort Collections?

Sorting collections in Java boils down to two heavyweight contenders: `Comparable` and `Comparator`. Both serve the same end goal but take completely different approaches.

**Comparable Interface: The "Natural Order" Champion**

`Comparable` is all about defining the natural ordering of your objects. Think of it as teaching your class how to compare itself with other instances of the same type.

**Key Characteristics:**

- Single sorting logic: One way to sort, baked right into the class.
- Method signature: `int compareTo(T other)`.
- Return values: Negative integer → current object is "less than" the parameter. Zero → objects are equal. Positive integer → current object is "greater than" the parameter.

**When to Use Comparable:**

- When there's an obvious, universally accepted way to sort your objects.
- You want sorting to be the class's default behavior.
- Examples: sorting students by ID, products by price, dates chronologically.

```java
import java.util.*;

class Student implements Comparable<Student> {
    private String name;
    private int grade;
    private int studentId;

    public Student(String name, int grade, int studentId) {
        this.name = name;
        this.grade = grade;
        this.studentId = studentId;
    }

    // Getters
    public String getName() { return name; }
    public int getGrade() { return grade; }
    public int getStudentId() { return studentId; }

    @Override
    public int compareTo(Student other) {
        // Natural order: sort by student ID (ascending)
        return Integer.compare(this.studentId, other.studentId);
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %d, Grade: %d)", name, studentId, grade);
    }
}

public class ComparableExample {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
            new Student("Alice", 85, 103),
            new Student("Bob", 90, 101),
            new Student("Charlie", 80, 102)
        );

        Collections.sort(students); // Uses natural order (by student ID)

        students.forEach(System.out::println);
        // Output:
        // Bob (ID: 101, Grade: 90)
        // Charlie (ID: 102, Grade: 80)
        // Alice (ID: 103, Grade: 85)
    }
}
```

**Comparator Interface: The "Custom Logic" Powerhouse**

`Comparator` is your go-to when you need multiple ways to sort the same object or when you can't modify the original class. It's like having a bunch of different sorting algorithms in your toolkit.

**Key Characteristics:**

- Multiple sorting strategies: Define as many as you need.
- Method signature: `int compare(T o1, T o2)`.
- External to the class: Keep your sorting logic separate.
- Flexibility: Can be implemented as separate classes, anonymous classes, or lambda expressions.

**When to Use Comparator:**

- Multiple sorting criteria for the same class.
- Can't modify the original class (third-party libraries).
- Want to keep sorting logic separate from business logic.
- Need different sorting behavior in different contexts.

```java
import java.util.*;

class Student {
    private String name;
    private int grade;
    private int studentId;

    public Student(String name, int grade, int studentId) {
        this.name = name;
        this.grade = grade;
        this.studentId = studentId;
    }

    // Getters
    public String getName() { return name; }
    public int getGrade() { return grade; }
    public int getStudentId() { return studentId; }

    @Override
    public String toString() {
        return String.format("%s (ID: %d, Grade: %d)", name, studentId, grade);
    }
}

// Approach 1: Separate Comparator class
class NameComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.getName().compareTo(s2.getName());
    }
}

class GradeComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return Integer.compare(s2.getGrade(), s1.getGrade()); // Descending order
    }
}

public class ComparatorExample {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
            new Student("Alice", 85, 103),
            new Student("Bob", 90, 101),
            new Student("Charlie", 80, 102),
            new Student("Diana", 95, 104)
        );

        System.out.println("=== Original List ===");
        students.forEach(System.out::println);

        // Approach 1: Using separate Comparator class
        System.out.println("\n=== Sorted by Name (Ascending) ===");
        Collections.sort(students, new NameComparator());
        students.forEach(System.out::println);

        // Approach 2: Lambda expression (most common in modern Java)
        System.out.println("\n=== Sorted by Grade (Descending) ===");
        students.sort((s1, s2) -> Integer.compare(s2.getGrade(), s1.getGrade()));
        students.forEach(System.out::println);

        // Approach 3: Method reference with Comparator utility methods
        System.out.println("\n=== Sorted by Student ID (Ascending) ===");
        students.sort(Comparator.comparing(Student::getStudentId));
        students.forEach(System.out::println);

        // Approach 4: Chaining comparators for complex sorting
        System.out.println("\n=== Sorted by Grade (Desc), then by Name (Asc) ===");
        students.sort(Comparator.comparing(Student::getGrade).reversed()
                                .thenComparing(Student::getName));
        students.forEach(System.out::println);

        // Approach 5: Anonymous class (less common but still valid)
        System.out.println("\n=== Sorted by Name Length ===");
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Integer.compare(s1.getName().length(), s2.getName().length());
            }
        });
        students.forEach(System.out::println);
    }
}
```

```java
// Modern Java Comparator Utilities (Java 8+)

// Basic comparisons
Comparator.comparing(Student::getName)                    // Sort by name
Comparator.comparing(Student::getGrade).reversed()        // Sort by grade (descending)

// Chaining comparators
Comparator.comparing(Student::getGrade).reversed()
          .thenComparing(Student::getName)                // Grade desc, then name asc

// Null-safe comparisons
Comparator.comparing(Student::getName,
          Comparator.nullsFirst(String::compareTo))       // Handle null names

// Natural order
Comparator.naturalOrder()                                 // For Comparable objects
Comparator.reverseOrder()                                 // Reverse natural order
```

**Performance Notes:**

- `Comparable`: Slightly faster since it's built into the object.
- `Comparator`: Minimal overhead, especially with method references.
- Lambda expressions: JVM optimizes these heavily, so performance is excellent.
- Anonymous classes: Slight memory overhead due to class file generation.

## What Are the Main Interfaces in Java Collections?

Java Collections Framework provides several interfaces that define various types of collections:

**Collection Interface:**

- The root interface of the collection hierarchy. It defines the basic operations for all types of collections, such as adding, removing, and querying elements.
- Key methods: `add()`, `remove()`, `size()`, `iterator()`, `clear()`, `isEmpty()`, `contains()`.

**List Interface:**

- An ordered collection (also known as a sequence). Lists can contain duplicate elements and allow positional access and insertion of elements.
- Extends `Collection`.
- Key methods: `get(int index)`, `set(int index, E element)`, `add(int index, E element)`, `remove(int index)`, `indexOf(Object o)`, `subList(int fromIndex, int toIndex)`.

**Set Interface:**

- A collection that cannot contain duplicate elements. It models the mathematical set abstraction.
- Extends `Collection`.
- Key methods: Same as `Collection`, but with the constraint that no duplicates are allowed.

**SortedSet Interface:**

- A `Set` that maintains its elements in ascending order. Implements the `NavigableSet` interface.
- Extends `Set`.
- Key methods: `first()`, `last()`, `headSet(E toElement)`, `tailSet(E fromElement)`, `subSet(E fromElement, E toElement)`.

**NavigableSet Interface:**

- A `SortedSet` with navigation methods reporting closest matches for given search targets.
- Extends `SortedSet`.
- Key methods: `lower(E e)`, `floor(E e)`, `ceiling(E e)`, `higher(E e)`, `pollFirst()`, `pollLast()`.

## What Are the Differences Between Collection and Collections in Java?

In Java, `Collection` and `Collections` are often confused due to their similar names, but they serve different purposes and have distinct functionalities:

**Collection:**

- **Type:** Interface
- **Package:** `java.util`
- **Purpose:** `Collection` is the root interface in the Java Collections Framework. It represents a group of objects, known as elements. Some of its subinterfaces include `List`, `Set`, and `Queue`.
- **Usage:** `Collection` defines the basic operations that can be performed on a collection of elements, such as adding, removing, and querying elements.
- **Key Methods:** `add(E e)`, `remove(Object o)`, `size()`, `iterator()`, `isEmpty()`, `clear()`, `contains(Object o)`.

```java
Collection<String> collection = new ArrayList<>();
collection.add("Element1");
collection.add("Element2");
```

**Collections:**

- **Type:** Utility Class
- **Package:** `java.util`
- **Purpose:** `Collections` is a utility class that consists of static methods that operate on or return collections. It provides methods for manipulating and creating collections.
- **Usage:** `Collections` provides static methods for various collection operations, such as sorting, searching, reversing, and synchronizing collections. It also provides methods to create empty or singleton collections.
- **Key Methods:** `sort()`, `reverse()`, `shuffle()`, `unmodifiableCollection()`, `synchronizedCollection()`, `binarySearch()`.

```java
List<String> list = new ArrayList<>();
list.add("Element3");
list.add("Element1");
list.add("Element2");

Collections.sort(list);
System.out.println(list); // Output: [Element1, Element2, Element3]
```

## How Do You Implement a Custom Collection in Java?

Implementing a custom collection in Java involves defining a new class that encapsulates the data structure you want to create and implementing relevant interfaces from the Java Collections Framework (JCF). Here's a step-by-step guide on how to create a simple custom collection, such as a custom list, by implementing the `List` interface.

**1. Define the Class and Implement the Interface:**

```java
import java.util.*;

public class CustomList<E> implements List<E> {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    public CustomList() {
        elements = new Object[DEFAULT_CAPACITY];
    }
}
```

**2. Implement Required Methods:**

The `List` interface contains many methods that need to be implemented. Here, we'll implement the core methods: `size()`, `add()`, `get()`, and `remove()`.

```java
@Override
public int size() {
    return size;
}

@Override
public boolean isEmpty() {
    return size == 0;
}

@Override
public boolean add(E e) {
    ensureCapacity();
    elements[size++] = e;
    return true;
}

@Override
public E get(int index) {
    checkIndex(index);
    return (E) elements[index];
}

@Override
public E remove(int index) {
    checkIndex(index);
    E oldValue = (E) elements[index];

    int numMoved = size - index - 1;
    if (numMoved > 0) {
        System.arraycopy(elements, index + 1, elements, index, numMoved);
    }
    elements[--size] = null; // Clear to let GC do its work
    return oldValue;
}

private void ensureCapacity() {
    if (size == elements.length) {
        int newCapacity = elements.length * 2;
        elements = Arrays.copyOf(elements, newCapacity);
    }
}

private void checkIndex(int index) {
    if (index < 0 || index >= size) {
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
}
```

**3. Implement Remaining Methods:**

The `List` interface has many other methods. Implementing them all can be tedious, but it ensures your collection behaves correctly as a list. Here are some more essential methods you might want to implement:

```java
@Override
public void add(int index, E element) {
    checkIndexForAdd(index);
    ensureCapacity();

    System.arraycopy(elements, index, elements, index + 1, size - index);
    elements[index] = element;
    size++;
}

@Override
public boolean contains(Object o) {
    return indexOf(o) >= 0;
}

@Override
public int indexOf(Object o) {
    if (o == null) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == null) {
                return i;
            }
        }
    } else {
        for (int i = 0; i < size; i++) {
            if (o.equals(elements[i])) {
                return i;
            }
        }
    }
    return -1;
}

@Override
public Object[] toArray() {
    return Arrays.copyOf(elements, size);
}

@Override
public <T> T[] toArray(T[] a) {
    if (a.length < size) {
        return (T[]) Arrays.copyOf(elements, size, a.getClass());
    }
    System.arraycopy(elements, 0, a, 0, size);
    if (a.length > size) {
        a[size] = null;
    }
    return a;
}
```

## What is the Difference Between HashMap and LinkedHashMap?

**HashMap:**

- **Order of Entries:** `HashMap` does not maintain any order of its entries. The order of elements can change over time, especially when rehashing occurs after resizing the map.
- **Performance:** `HashMap` generally provides constant-time performance for the basic operations (`get` and `put`), assuming the hash function disperses the elements properly among the buckets.
- **Internal Structure:** `HashMap` uses a hash table to store the map entries. Each entry is stored in a bucket which is determined by the hash code of the key.
- **Use Cases:** Suitable for general-purpose use where the order of the entries is not important.

```java
import java.util.HashMap;
import java.util.Map;

public class HashMapExample {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        System.out.println(map); // Output can be in any order
    }
}
```

**LinkedHashMap:**

- **Order of Entries:** `LinkedHashMap` maintains a doubly-linked list running through all of its entries. This allows it to maintain the order in which keys were inserted (insertion-order).
- **Performance:** `LinkedHashMap` has a slightly lower performance compared to `HashMap` due to the overhead of maintaining the linked list. However, it still provides constant-time performance for basic operations, assuming the hash function disperses the elements properly.
- **Internal Structure:** Like `HashMap`, `LinkedHashMap` uses a hash table to store the map entries. Additionally, each entry maintains pointers to the next and previous entries in the doubly-linked list.
- **Use Cases:** Suitable when you need to maintain the insertion order of the elements.

```java
import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapExample {
    public static void main(String[] args) {
        // Creating a LinkedHashMap
        Map<String, Integer> linkedMap = new LinkedHashMap<>();

        // Adding key-value pairs
        linkedMap.put("one", 1);
        linkedMap.put("two", 2);
        linkedMap.put("three", 3);
        linkedMap.put("four", 4);

        // Printing the map
        System.out.println(linkedMap); // {one=1, two=2, three=3, four=4}

        // Accessing an entry to check if the order is maintained
        System.out.println("Value for key 'two': " + linkedMap.get("two"));

        // Iterating through the LinkedHashMap
        System.out.println("Iterating over LinkedHashMap:");
        for (Map.Entry<String, Integer> entry : linkedMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        // Using LinkedHashMap with access-order enabled (for LRU caching)
        Map<String, Integer> accessOrderMap = new LinkedHashMap<>(16, 0.75f, true);
        accessOrderMap.put("one", 1);
        accessOrderMap.put("two", 2);
        accessOrderMap.put("three", 3);
        accessOrderMap.put("four", 4);

        // Accessing some elements
        accessOrderMap.get("one");
        accessOrderMap.get("three");

        // Printing after access to show LRU order (least recently used goes to end)
        System.out.println("LinkedHashMap with Access Order: " + accessOrderMap);
        // Output: {two=2, four=4, one=1, three=3}
    }
}
```

## How Does ConcurrentHashMap Work and How Is It Different From HashMap?

`ConcurrentHashMap` and `HashMap` are both implementations of the `Map` interface in Java, but they are designed for different use cases, especially when it comes to handling concurrent access.

**ConcurrentHashMap:**

- **Concurrency Level:** `ConcurrentHashMap` divides the map into segments or buckets. Each segment is a smaller, independently synchronized map, allowing multiple threads to access different segments concurrently without contention.
- **Locking Mechanism:** Instead of locking the entire map, `ConcurrentHashMap` locks only the specific segment that a key belongs to. This fine-grained locking mechanism significantly improves performance in multi-threaded environments.
- **Performance:** `ConcurrentHashMap` provides better performance in a multi-threaded environment compared to `HashMap` when synchronized externally, due to its advanced locking mechanism.

```java
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class ConcurrentHashMapExample {
    public static void main(String[] args) {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        map.forEach((key, value) -> System.out.println(key + ": " + value));

        // Concurrent modification example
        map.computeIfAbsent("four", k -> 4);
        System.out.println("four: " + map.get("four"));
        // Output:
        // one: 1
        // two: 2
        // three: 3
        // four: 4
    }
}
```

**HashMap:**

- **Simple Structure:** `HashMap` uses an array of buckets. Each bucket is a linked list (or a tree, in case of many hash collisions), where each entry contains a key-value pair.
- **No Internal Synchronization:** `HashMap` does not handle synchronization internally. When accessed by multiple threads concurrently, external synchronization is needed to avoid data corruption or inconsistent results.
- **Performance:** In a single-threaded environment, `HashMap` is faster than `ConcurrentHashMap` due to the lack of synchronization overhead.
- **Iteration:** Iterators on a `HashMap` are fail-fast, meaning that if the map is modified after the iterator is created, a `ConcurrentModificationException` is thrown.

```java
import java.util.HashMap;
import java.util.Map;

public class HashMapExample {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        map.forEach((key, value) -> System.out.println(key + ": " + value));
        // Output:
        // one: 1
        // two: 2
        // three: 3
    }
}
```

## Explain the Internal Structure and Working of a TreeMap

**Internal Structure:**

**1. Red-Black Tree:**

- Internally, `TreeMap` is implemented using a Red-Black tree, which is a type of self-balancing binary search tree.
- Red-Black tree properties ensure that the tree remains approximately balanced, providing O(log n) time complexity for basic operations like `get`, `put`, `remove`, etc.

**2. Nodes:**

- Each node in the Red-Black tree contains a key-value pair.
- Every node also contains references to its parent, left child, and right child nodes.
- Additionally, each node has a color attribute, which can either be red or black.

**Working of TreeMap:**

**1. Insertion (put method):**

- When a new key-value pair is added, the `put` method traverses the tree to find the correct location for the new node, maintaining the sorted order of keys.
- After inserting the new node, the tree may need to be rebalanced to maintain the Red-Black tree properties. This involves recoloring and possibly rotating nodes.

**2. Deletion (remove method):**

- When a key is removed, the `remove` method locates the node and removes it from the tree.
- After deletion, the tree is rebalanced to ensure it remains a valid Red-Black tree. This may also involve recoloring and rotating nodes.

**3. Searching (get method):**

- The `get` method searches for a specific key by traversing the tree from the root, moving left or right based on the comparison of the key with the current node's key.
- The search operation has a time complexity of O(log n) due to the balanced nature of the Red-Black tree.

**4. Iteration:**

- `TreeMap` supports in-order traversal, which means iterating over the entries will return them in the sorted order of keys.
- The iterators are fail-fast, meaning they throw `ConcurrentModificationException` if the map is modified after the iterator is created.

## How Does a PriorityQueue Work and How Is It Different From a Regular Queue?

`PriorityQueue` uses a binary heap. Elements are ordered based on their priority (natural order or a provided comparator). In a min-heap, the smallest element is always at the head.

**Differences from a Regular Queue**

**Order of Elements:**

- PriorityQueue: Elements are ordered by priority.
- Regular Queue (e.g., LinkedList): Elements follow FIFO order.

**Head Element:**

- PriorityQueue: Head is the element with the highest priority.
- Regular Queue: Head is the oldest element (first inserted).

**Use Cases:**

- PriorityQueue: Suitable for scheduling tasks, simulations, or any scenario where order is determined by priority.
- Regular Queue: Suitable for simple task queues, buffers, or any scenario requiring FIFO order.

```java
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Queue;

PriorityQueue<Integer> pq = new PriorityQueue<>();
pq.offer(3);
pq.offer(1);
pq.offer(2);

System.out.println(pq.poll()); // Outputs: 1 (smallest element)

Queue<Integer> queue = new LinkedList<>();
queue.offer(3);
queue.offer(1);
queue.offer(2);

System.out.println(queue.poll()); // Outputs: 3 (first inserted element)
```

## What Is the Difference Between Deep Copy and Shallow Copy in Collections?

**Shallow Copy:**

A shallow copy of an object is a new object which is a copy of the original object, but the internal objects referenced by the original object are not copied. Instead, the references to the original objects are copied. This means that both the original object and its shallow copy share references to the same objects.

**Characteristics of Shallow Copy:**

**1. Copies Object Structure Only:**

- The outer object is copied, but the inner objects (objects referred by the fields) are not copied.

**2. Shared References:**

- Any changes made to the mutable objects within the copied object will reflect in the original object, and vice versa.

```java
package com.example.hungrycoder;

class ABC {
    // instance variable of the class ABC
    int x = 30;
}

public class ShallowCopyTest {
    // main method
    public static void main(String argvs[]) {
        // creating an object of the class ABC
        ABC obj1 = new ABC();

        // it will copy the reference, not value
        ABC obj2 = obj1;

        // updating the value to 6
        // using the reference variable obj2
        obj2.x = 6;

        // printing the value of x using reference variable obj1
        System.out.println("The value of x is: " + obj1.x); // The value of x is: 6
    }
}
```

**Deep Copy:**

A deep copy of an object is a new object which is a copy of the original object along with all the objects referenced by the original object, recursively. This means that the deep copy has its own copies of the objects, and changes to the inner objects in the copied structure do not affect the original object and vice versa.

**Characteristics of Deep Copy:**

**1. Copies Entire Structure:**

- Both the outer object and all objects it references (directly or indirectly) are recursively copied.

**2. Independent Copies:**

- Changes to the copied objects do not affect the original objects, and vice versa.

```java
class ABC {
    // instance variable of the class ABC
    int x = 30;
}

public class DeepCopyExample {
    // main method
    public static void main(String argvs[]) {
        // creating an object of the class ABC
        ABC obj1 = new ABC();

        // it will copy the reference, not value
        ABC obj2 = new ABC();

        // updating the value to 6
        // using the reference variable obj2
        obj2.x = 6;

        // printing the value of x using reference variable obj1
        System.out.println("The value of x is: " + obj1.x); // The value of x is: 30
    }
}
```

## How Do You Handle Duplicates in Collections Such as List, Set, and Map?

Handling duplicates in collections in Java varies depending on the type of collection you are using (`List`, `Set`, or `Map`).

**List**

A `List` in Java allows duplicate elements. However, if you need to handle or remove duplicates, you can do so manually using various approaches.

**Removing Duplicates from a List**

**1. Using a Set to Filter Duplicates:**

```java
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class RemoveDuplicatesFromList {
    public static void main(String[] args) {
        List<String> listWithDuplicates = new ArrayList<>();
        listWithDuplicates.add("apple");
        listWithDuplicates.add("banana");
        listWithDuplicates.add("apple");
        listWithDuplicates.add("orange");

        // Using LinkedHashSet to maintain insertion order
        Set<String> set = new LinkedHashSet<>(listWithDuplicates);
        List<String> listWithoutDuplicates = new ArrayList<>(set);

        System.out.println("List without duplicates: " + listWithoutDuplicates);
        // List without duplicates: [apple, banana, orange]
    }
}
// This approach maintains the insertion order by using a LinkedHashSet.
```

**2. Using Streams (Java 8+):**

```java
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveDuplicatesWithStreams {
    public static void main(String[] args) {
        List<String> listWithDuplicates = new ArrayList<>();
        listWithDuplicates.add("apple");
        listWithDuplicates.add("banana");
        listWithDuplicates.add("apple");
        listWithDuplicates.add("orange");

        List<String> listWithoutDuplicates = listWithDuplicates.stream()
                .distinct()
                .collect(Collectors.toList());

        System.out.println("List without duplicates: " + listWithoutDuplicates);
        // List without duplicates: [apple, banana, orange]
    }
}
```

**Set**

A `Set` in Java inherently does not allow duplicate elements. If you add a duplicate element to a `Set`, it will not be added. Different implementations of `Set` (e.g., `HashSet`, `TreeSet`, `LinkedHashSet`) have different behaviors regarding order and performance.

```java
import java.util.HashSet;
import java.util.Set;

public class SetExample {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("apple");
        set.add("banana");
        set.add("apple"); // Duplicate element

        System.out.println("Set: " + set); // Set: [banana, apple]
    }
}
```

**Map**

A `Map` in Java does not allow duplicate keys, but it allows duplicate values. If you insert an entry with a key that already exists, the old value is replaced with the new value.

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapWithListValues {
    public static void main(String[] args) {
        Map<String, List<String>> map = new HashMap<>();

        addValueToMap(map, "fruit", "apple");
        addValueToMap(map, "fruit", "banana");
        addValueToMap(map, "fruit", "apple"); // Duplicate value for the same key

        System.out.println("Map: " + map); // Map: {fruit=[apple, banana, apple]}
    }

    public static void addValueToMap(Map<String, List<String>> map, String key, String value) {
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }
}
```

## What Is the Difference Between Synchronized Collections and Concurrent Collections in Java?

In Java, both synchronized collections and concurrent collections are used to handle multithreaded access to data structures.

**Synchronized Collections**

Synchronized collections are part of the legacy collections framework and are designed to provide basic thread safety by synchronizing the entire collection. This means that all methods that access or modify the collection are synchronized, which can lead to contention and reduced performance under high concurrency.

**Key Points:**

**1. Implementation:** Synchronized collections are usually created using the `Collections.synchronized*` methods.

**2. Synchronization Mechanism:** Methods are synchronized using intrinsic locks (the `synchronized` keyword). This ensures that only one thread can access the method at a time.

**3. Performance:** Synchronizing on the entire collection can cause significant performance bottlenecks, especially in highly concurrent environments.

**4. Iteration:** Iterators must be used within a synchronized block to avoid `ConcurrentModificationException`.

**5. Use Cases:** Suitable for low to moderate concurrency scenarios where thread safety is needed, but high performance is not critical.

```java
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class SynchronizedCollectionsExample {
    public static void main(String[] args) {
        List<String> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        synchronizedList.add("one");
        synchronizedList.add("two");

        synchronized (synchronizedList) {
            for (String item : synchronizedList) {
                System.out.println(item);
            }
        }
    }
    // Output:
    // one
    // two
}
```

**Concurrent Collections**

Concurrent collections are part of the `java.util.concurrent` package and are designed for high concurrency. They use advanced concurrency control mechanisms to allow multiple threads to access and modify the collection concurrently without the need for explicit synchronization on the part of the user.

**Key Points:**

**1. Implementation:** Some common concurrent collections include: `ConcurrentHashMap`, `ConcurrentLinkedQueue`, `CopyOnWriteArrayList`, `ConcurrentSkipListMap`, `ConcurrentSkipListSet`.

**2. Synchronization Mechanism:** These collections use sophisticated algorithms and non-blocking techniques to achieve thread safety. For example, `ConcurrentHashMap` uses a segmented locking technique, and `CopyOnWriteArrayList` creates a new copy of the underlying array on each write operation.

**3. Performance:** Designed for high concurrency and scalability. They minimize lock contention and are more efficient under heavy multi-threaded access.

**4. Iteration:** Iterators are weakly consistent, meaning they can tolerate concurrent modifications without throwing `ConcurrentModificationException` and may reflect some, all, or none of the changes made to the collection after the iterator was created.

**5. Use Cases:** Suitable for high concurrency scenarios where thread safety and performance are critical.

```java
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentCollectionsExample {
    public static void main(String[] args) {
        ConcurrentMap<String, String> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("one", "1");
        concurrentMap.put("two", "2");

        for (String key : concurrentMap.keySet()) {
            System.out.println(key + ": " + concurrentMap.get(key));
        }
    }
    // Output:
    // one: 1
    // two: 2
}
```

---

# SQL Query Execution Flow

*SQL Fundamentals*

Understanding how databases process your queries from parsing to results.

## The Big Picture

When you write a SQL query and hit execute, the database engine performs a series of sophisticated operations to return your results. Understanding this process is essential for writing efficient queries, debugging performance issues, and acing technical interviews.

**Why This Matters in Interviews**

- "Explain the order of SQL query execution"
- "How does the query optimizer work?"
- "What is the difference between a query plan and execution plan?"
- "How would you analyze and optimize a slow query?"

## The Five Phases of Query Execution

1. **Parser** — Validates syntax and creates AST
2. **Analyzer** — Validates schema and resolves references
3. **Optimizer** — Generates and selects best plan
4. **Executor** — Runs the plan and retrieves data
5. **Formatter** — Formats and returns results

## Phase 1: Query Parsing

The parser is the first component that processes your SQL query. It converts the text string into a structured format that the database can work with.

**What Happens During Parsing**

1. **Lexical Analysis:** Breaks SQL into tokens (keywords, identifiers, operators, literals)
2. **Syntax Validation:** Checks that tokens follow SQL grammar rules
3. **AST Generation:** Creates an Abstract Syntax Tree representing the query structure

```
SELECT name FROM users WHERE id = 1

Lexer -> Tokens: SELECT, name, FROM, users, WHERE, id, =, 1
Parser -> Abstract Syntax Tree (AST)
```

**Parser Catches These Errors**

- Syntax errors: `SELEC * FROM users` (misspelled keyword)
- Missing clauses: `SELECT FROM users` (no columns specified)
- Unbalanced quotes: `WHERE name = 'John`
- Invalid operators: `WHERE age === 25`

**Note:** The parser does NOT check if tables or columns exist — that's the analyzer's job.

## Phase 2: Semantic Analysis

The semantic analyzer (also called the binder) validates that your query makes logical sense by checking it against the database schema.

**Analyzer Responsibilities**

- **Schema Validation:** Verifies tables and columns exist
- **Type Checking:** Ensures data types are compatible
- **Reference Resolution:** Resolves aliases and qualified names
- **Permission Checking:** Validates user has access to referenced objects

```sql
-- This query will fail at semantic analysis, not parsing
SELECT employee_name    -- Column doesn't exist (should be 'name')
FROM employees
WHERE salary = 'high'  -- Type mismatch: salary is numeric

-- Error: Unknown column 'employee_name' in field list
```

**Query Rewriting Happens Here**

The analyzer may transform your query into an equivalent but more efficient form:

- **View expansion:** Replaces view references with underlying query
- **Subquery flattening:** Converts some subqueries to joins
- **Predicate pushdown:** Moves WHERE conditions closer to data source

## Phase 3: Query Optimization

The query optimizer is the "brain" of the database engine. It generates multiple execution strategies and selects the one with the lowest estimated cost.

**How the Optimizer Works**

```
Parsed Query
  -> Generate Candidate Plans
       Plan 1: Index Scan
       Plan 2: Full Table Scan
       Plan 3: Hash Join
  -> Cost Estimation
  -> Select Lowest Cost
  -> Chosen Execution Plan
```

**Cost Estimation Factors**

| Factor | What It Measures | Impact |
|---|---|---|
| I/O Cost | Disk reads and writes required | Usually the dominant factor |
| CPU Cost | Processing time for operations | Important for complex calculations |
| Cardinality | Estimated number of rows | Affects join strategy selection |
| Selectivity | Percentage of rows matching condition | Determines index usefulness |

**Common Optimizer Decisions**

- **Index Selection** — Which index to use, or whether to do a full table scan
- **Join Strategy** — Nested loop, hash join, or merge join
- **Join Order** — Which table to process first in multi-table joins

```sql
-- View the execution plan in PostgreSQL
EXPLAIN ANALYZE
SELECT * FROM orders o
JOIN customers c ON o.customer_id = c.id
WHERE c.country = 'USA';

-- Output shows:
-- Hash Join (cost=245.00..1890.00 rows=5000)
--   -> Seq Scan on orders (cost=0.00..1520.00 rows=50000)
--   -> Hash (cost=180.00..180.00 rows=2000)
--        -> Index Scan on customers (cost=0.00..180.00 rows=2000)
```

**Why Statistics Matter**

The optimizer relies heavily on table statistics to make good decisions. Stale statistics can lead to poor plans. Always run `ANALYZE` (PostgreSQL) or `UPDATE STATISTICS` (SQL Server) after major data changes.

## Phase 4: Execution Engine

The execution engine takes the optimized plan and actually retrieves the data. It coordinates with the storage engine and manages resources like memory and locks.

**Execution Process**

1. **Plan Initialization:** Allocates resources, opens cursors
2. **Data Retrieval:** Fetches data from storage (disk or buffer cache)
3. **Operation Execution:** Applies filters, joins, aggregations
4. **Result Buffering:** Collects results for return to client

**Key Execution Components**

- **Buffer Pool** — In-memory cache for frequently accessed data pages. Reduces disk I/O dramatically.
- **Lock Manager** — Handles concurrency control to ensure transaction isolation.
- **Transaction Log** — Records all changes for durability and recovery purposes.

**Iterator Model (Volcano Model)**

Most databases use the iterator model where each operator in the plan implements three methods:

- **Open():** Initialize the operator
- **Next():** Return the next row (called repeatedly)
- **Close():** Clean up resources

Data flows from bottom operators (scans) up through the tree to the top (final result).

## SQL Logical Execution Order

SQL queries are NOT executed in the order you write them. Understanding the logical execution order helps you write correct and efficient queries.

1. FROM / JOIN
2. WHERE
3. GROUP BY
4. HAVING
5. SELECT
6. DISTINCT
7. ORDER BY
8. LIMIT / OFFSET

| Order | Clause | What Happens |
|---|---|---|
| 1 | FROM | Tables are identified and joined (Cartesian product created) |
| 2 | WHERE | Rows are filtered based on conditions |
| 3 | GROUP BY | Remaining rows are grouped |
| 4 | HAVING | Groups are filtered |
| 5 | SELECT | Columns are selected/expressions evaluated |
| 6 | DISTINCT | Duplicate rows removed |
| 7 | ORDER BY | Results are sorted |
| 8 | LIMIT | Result set is truncated |

**Common Gotcha: Column Aliases**

Because SELECT is processed after WHERE and GROUP BY, you can't use column aliases in those clauses:

```sql
-- This FAILS
SELECT salary * 12 AS annual_salary
FROM employees
WHERE annual_salary > 100000;  -- Error: unknown column

-- This WORKS (ORDER BY is processed after SELECT)
SELECT salary * 12 AS annual_salary
FROM employees
ORDER BY annual_salary;  -- OK!
```

## Top 10 Interview Questions

Master these frequently asked questions about SQL query execution:

**1. What is the order of SQL query execution?**

Logically: `FROM`/`JOIN` → `WHERE` → `GROUP BY` → `HAVING` → `SELECT` → `DISTINCT` → `ORDER BY` → `LIMIT`/`OFFSET`. This is why you can't reference a `SELECT` alias in `WHERE` (it doesn't exist yet) but you can in `ORDER BY` (it runs after `SELECT`). Note this is the *logical* order the engine reasons about — the optimizer may physically execute operations in a different sequence as long as the result is equivalent.

**2. What is the difference between a query plan and an execution plan?**

A **query plan** (or "plan") is the strategy the optimizer *chooses* before running the query — a tree of operations (scans, joins, sorts) with estimated costs, produced by tools like `EXPLAIN`. An **execution plan** is what you get from `EXPLAIN ANALYZE` — the plan annotated with *actual* runtime numbers (real rows returned, real time taken, real I/O) after the query has actually run. In short: estimated vs. actual.

**3. How does the query optimizer choose between execution plans?**

It enumerates candidate plans (different index choices, join orders, join algorithms), estimates the cost of each using table statistics (row counts, data distribution, selectivity), and picks the plan with the lowest estimated cost — usually dominated by I/O cost, with CPU cost as a secondary factor. This is a cost-based optimizer (CBO); it depends heavily on statistics being up to date.

**4. What is a full table scan and when is it actually good?**

A full table scan reads every row in a table sequentially, ignoring indexes. It's actually the *better* choice when: the table is small, the query needs a large percentage of the rows (low selectivity — e.g., >15-20%), or there's no useful index. Sequential I/O is often faster than the random I/O of jumping between an index and the table (index lookups), so for low-selectivity queries a scan can beat an index seek.

**5. What is the difference between index seek and index scan?**

An **index seek** uses the index's B-tree structure to jump directly to the matching rows (like a binary search) — fast, used for highly selective queries (e.g., `WHERE id = 5`). An **index scan** reads the entire index sequentially, in order, checking each entry — used when a large portion of rows match, or when the index is scanned for ordering rather than filtering. A seek is O(log n) to find the start point; a scan is proportional to the number of entries scanned.

**6. Explain the three join algorithms databases use.**

- **Nested Loop Join:** For each row in the outer table, scan the inner table (or probe its index) for matches. Good for small tables or when one side has a very selective index; O(n × m) worst case without an index.
- **Hash Join:** Build an in-memory hash table from the smaller (build) input keyed on the join column, then probe it with rows from the larger (probe) input. Efficient for large, unsorted datasets with equality joins; roughly O(n + m).
- **Merge Join (Sort-Merge):** Both inputs are sorted (or already sorted via an index) on the join key, then merged like the merge step of merge-sort. Efficient when both inputs are already sorted; O(n + m) given sorted inputs, O(n log n + m log m) if sorting is required first.

**7. What does the parser check vs what does the analyzer check?**

The **parser** checks pure syntax — is the SQL grammatically valid? (missing keywords, unbalanced quotes, bad operators) — producing an Abstract Syntax Tree without knowing anything about the actual schema. The **analyzer/binder** checks semantics against the schema — do the referenced tables and columns actually exist, are the data types compatible, does the user have permission, and it resolves aliases/qualified names. Parsing happens first; semantic errors (like a typo'd column name) only surface at the analysis phase.

**8. What is the buffer pool and why is it critical for performance?**

The buffer pool (or buffer cache) is an in-memory area that caches frequently/recently accessed data pages from disk. Since disk I/O is orders of magnitude slower than memory access, serving reads and writes from the buffer pool instead of hitting disk every time is the single biggest performance lever a database has. A "cold" buffer pool (after a restart) means early queries are slow until relevant pages are cached ("warmed up").

**9. How do you analyze and fix a slow query?**

1. Run `EXPLAIN ANALYZE` (or the engine's equivalent) to get the actual execution plan with real row counts and timings.
2. Look for mismatches between estimated and actual row counts (stale statistics), unexpected full table scans, or expensive operations (nested loops over large tables, sorts, hash joins spilling to disk).
3. Check whether appropriate indexes exist on filter/join columns, and whether the optimizer is actually using them.
4. Update statistics (`ANALYZE` / `UPDATE STATISTICS`) if they're stale.
5. Consider rewriting the query (avoiding functions on indexed columns, reducing subqueries, pushing filters earlier) or adding/adjusting indexes.
6. Re-run and compare the new plan and timing.

**10. What is query rewriting and give examples?**

Query rewriting is when the analyzer/optimizer transforms your query into a logically equivalent but more efficient form before execution. Examples:

- **View expansion:** A query against a view is rewritten to reference the view's underlying base-table query directly.
- **Subquery flattening/unnesting:** A correlated subquery is converted into an equivalent `JOIN`, which is often cheaper to execute.
- **Predicate pushdown:** A `WHERE` condition on a joined or aggregated result is pushed down closer to the base table scan, so filtering happens before more expensive operations like joins or grouping, reducing the number of rows processed downstream.

---

# Database Indexing Visualized

*SQL Fundamentals*

Understanding how indexes work and when to use them for optimal query performance.

## The Big Picture

Imagine you have a 1000-page book and need to find every mention of "database". Without an index, you'd have to read every single page. With an index at the back, you simply look up "database" and get the exact page numbers. Database indexes work the same way — they're the single most important tool for query optimization.

**Why This Matters in Interviews**

- "Explain how database indexes work"
- "What is the difference between clustered and non-clustered index?"
- "When should you NOT create an index?"
- "Does column order matter in a composite index?"

**The Book Index Analogy**

- **Without Index (Full Table Scan):** Reading every page of a 1000-page book to find mentions of "database". Slow but guaranteed to find everything.
- **With Index (Index Seek):** Looking up "database" in the back index, getting page numbers 42, 156, 891. Jump directly to those pages.

## Index Scan vs Full Table Scan

Compare performance: Finding employees in the Engineering department.

| | Index Scan | Full Table Scan |
|---|---|---|
| Method | Using `idx_department` index | No index available |
| Rows Scanned | 3 | 10,000 |
| Execution Time | 0.2ms | 450ms |
| Page Reads | 12 | 2,500 |
| Complexity | O(log n) | O(n) |

## How B-Tree Index Works

Most database indexes use a B-Tree (Balanced Tree) data structure. It keeps data sorted and allows searches, insertions, and deletions in logarithmic time O(log n).

**B-Tree Properties**

- **Self-Balancing:** All leaf nodes are at the same depth.
- **Sorted Order:** Keys within each node are sorted.
- **Multiple Keys per Node:** Each node can hold multiple keys (reduces tree height).
- **Leaf Nodes Linked:** Leaves are linked for efficient range scans.

```
Root Node:      [30, 60]
               /    |    \
Branch:   [10, 20] [40, 50] [70, 80]
          /    \    /    \    /    \
Leaf:   5,8  15,18 35,38 55,58 65,68 75,85  (row pointers)
```

**Search Algorithm**

1. **Start at Root:** Compare search key with root node keys.
2. **Navigate Down:** Follow the appropriate child pointer based on comparison.
3. **Repeat:** Continue until reaching a leaf node.
4. **Scan Leaf:** Find the key in the leaf node or determine it doesn't exist.

**Why B-Tree is Efficient**

- **Height is Low:** A B-Tree with 1 million keys typically has height 3-4.
- **Each Level = 1 Disk Read:** Only 3-4 disk reads to find any record.
- **Range Queries:** Linked leaves allow efficient range scans.

## Types of Database Indexes

Different index types serve different purposes. Understanding when to use each type is crucial for optimal performance.

**Clustered Index**

Determines physical order of data in table. Only one per table. Usually on primary key.

```sql
-- Usually created automatically on PK
CREATE TABLE users (
    id INT PRIMARY KEY -- Clustered
);
```

**Non-Clustered Index**

Separate structure pointing to data rows. Multiple allowed per table. Like a book's back index.

```sql
CREATE INDEX idx_email ON users(email);
```

**Composite Index**

Index on multiple columns. Column order matters! Follows leftmost prefix rule.

```sql
CREATE INDEX idx_name_dept ON employees(last_name, department);
```

**Covering Index**

Contains all columns needed by query. Avoids table lookup entirely (index-only scan).

```sql
CREATE INDEX idx_covering ON orders(customer_id) INCLUDE (order_date, total);
```

**Unique Index**

Enforces uniqueness constraint. Automatically created for `PRIMARY KEY` and `UNIQUE` constraints.

```sql
CREATE UNIQUE INDEX idx_email ON users(email);
```

**Partial Index**

Indexes only rows matching a condition. Smaller and faster for specific queries.

```sql
CREATE INDEX idx_active ON orders(created_at) WHERE status = 'active';
```

## Clustered vs Non-Clustered: Key Differences

| Aspect | Clustered Index | Non-Clustered Index |
|---|---|---|
| Data Storage | Data rows stored in index order | Separate structure with pointers to data |
| Per Table | Only ONE allowed | Multiple allowed (typically up to 999) |
| Leaf Nodes | Contain actual data rows | Contain pointers to data rows |
| Speed | Fastest for range queries | Requires extra lookup (unless covering) |
| Size | Doesn't add storage (IS the table) | Additional storage required |

## Composite Index: Column Order Matters!

One of the most misunderstood aspects of indexing. The order of columns in a composite index determines which queries can use it efficiently.

**The Leftmost Prefix Rule**

An index on `(A, B, C)` can efficiently support queries on:

- `A` — Yes
- `A, B` — Yes
- `A, B, C` — Yes
- `B` — No (doesn't use index efficiently)
- `B, C` — No
- `C` — No

```sql
-- Index on (last_name, first_name, department)
CREATE INDEX idx_employee_name
ON employees(last_name, first_name, department);

-- Uses index efficiently
SELECT * FROM employees WHERE last_name = 'Smith';
SELECT * FROM employees WHERE last_name = 'Smith' AND first_name = 'John';

-- Cannot use index efficiently (skips last_name)
SELECT * FROM employees WHERE first_name = 'John';
SELECT * FROM employees WHERE department = 'Engineering';
```

**Column Order Strategy**

- **Equality columns first:** Columns used with `=` should come before range columns.
- **High selectivity first:** Columns that filter out more rows should come first.
- **Consider query patterns:** Order based on your most common queries.

## When to Use Indexes (and When NOT To)

Indexes aren't free — they consume storage and slow down writes. Understanding when to use them is crucial.

**DO Create Indexes When**

- Column is frequently used in `WHERE` clauses.
- Column is used in `JOIN` conditions.
- Column is used in `ORDER BY` / `GROUP BY`.
- Column has high selectivity (many unique values).
- Table is large and queries return small result sets.
- Read operations far outnumber writes.

**DON'T Create Indexes When**

- Table is very small (full scan may be faster).
- Column has low selectivity (few unique values).
- Table has heavy `INSERT`/`UPDATE`/`DELETE` load.
- Queries return a large percentage of table rows.
- Column is rarely used in queries.
- Column values change frequently.

## Index Selectivity

Selectivity = Number of Distinct Values / Total Number of Rows

| Column | Distinct Values | Total Rows | Selectivity | Index Useful? |
|---|---|---|---|---|
| email | 100,000 | 100,000 | 1.0 (100%) | Excellent |
| last_name | 5,000 | 100,000 | 0.05 (5%) | Good |
| country | 50 | 100,000 | 0.0005 | Maybe |
| gender | 2 | 100,000 | 0.00002 | Poor |
| is_active (boolean) | 2 | 100,000 | 0.00002 | Poor (use partial index) |

## The Write Penalty

Every index must be updated when data changes. More indexes = slower writes.

```
INSERT Statement
  -> Write to Table
  -> Update Index 1
  -> Update Index 2
  -> Update Index 3
  -> Update Index N...
  -> Transaction Complete
```

**Index Maintenance Operations**

- **INSERT:** New entry added to every index on the table.
- **UPDATE:** If indexed column changes, old entry removed, new entry added.
- **DELETE:** Entry removed from every index.
- **Page Splits:** When a B-Tree node fills up, it splits into two (expensive).

**Finding the Right Balance**

Rule of thumb for OLTP systems: 5-10 indexes per table maximum. For OLAP/analytics: more indexes may be acceptable since writes are batched.

## Analyzing Index Usage

Learn to identify missing indexes and unused indexes using database tools.

**Check If Index Is Being Used**

```sql
-- PostgreSQL: Check index usage stats
SELECT
    indexrelname AS index_name,
    idx_scan AS times_used,
    idx_tup_read AS tuples_read,
    idx_tup_fetch AS tuples_fetched
FROM pg_stat_user_indexes
WHERE schemaname = 'public'
ORDER BY idx_scan DESC;

-- Find unused indexes (idx_scan = 0)
SELECT indexrelname, idx_scan
FROM pg_stat_user_indexes
WHERE idx_scan = 0;
```

**Identify Missing Indexes**

```sql
-- SQL Server: Missing index recommendations
SELECT
    d.statement AS table_name,
    d.equality_columns,
    d.inequality_columns,
    d.included_columns,
    s.user_seeks,
    s.avg_user_impact
FROM sys.dm_db_missing_index_details d
JOIN sys.dm_db_missing_index_groups g ON d.index_handle = g.index_handle
JOIN sys.dm_db_missing_index_group_stats s ON g.index_group_handle = s.group_handle
ORDER BY s.avg_user_impact DESC;
```

**Use EXPLAIN to Verify Index Usage**

```sql
EXPLAIN ANALYZE
SELECT * FROM employees
WHERE department = 'Engineering';

-- Look for: "Index Scan" or "Index Only Scan" = Good
-- Watch out for: "Seq Scan" on large tables = May need index
```

## Top 10 Interview Questions

Master these frequently asked questions about database indexing:

**1. What is a database index and how does it work?**

An index is a separate data structure (typically a B-Tree) that stores a sorted copy of one or more columns' values along with pointers back to the full row. It lets the database find matching rows via a fast O(log n) tree search instead of scanning every row (O(n)) — the same principle as a book's back index letting you jump to page numbers instead of reading cover to cover.

**2. Explain the difference between clustered and non-clustered index.**

A clustered index determines the physical storage order of the table's rows — the table data itself IS the index's leaf level, so there can only be one per table (usually on the primary key). A non-clustered index is a separate structure whose leaf nodes hold pointers (row locators) back to the actual data rows, which live elsewhere in their own order; a table can have many non-clustered indexes. Looking up via a non-clustered index that isn't "covering" requires an extra lookup step back to the table.

**3. What is a composite index and does column order matter?**

A composite (multi-column) index is built on two or more columns together. Column order matters enormously because of the leftmost prefix rule: an index on `(A, B, C)` can be used efficiently for lookups on `A`, `(A, B)`, or `(A, B, C)`, but not for a lookup on `B` or `C` alone, since the tree is sorted primarily by `A` first. Put equality-filtered, high-selectivity columns first.

**4. What is a covering index?**

A covering index includes every column a query needs — either as part of the key or as included/non-key columns — so the database can satisfy the entire query from the index alone without a separate lookup into the base table (an "index-only scan"). This avoids the extra I/O of jumping from index to table.

**5. When should you NOT create an index?**

Skip indexing when: the table is very small (a full scan is cheap and may beat the overhead of using an index); the column has low selectivity (few distinct values, like a boolean or a 2-3 value status column); the table sees heavy write traffic relative to reads (every index slows down INSERT/UPDATE/DELETE); the column is rarely queried; or queries typically return a large fraction of the table's rows anyway (a scan beats many random index lookups).

**6. How does index selectivity affect performance?**

Selectivity = distinct values / total rows. High selectivity (close to 1.0, e.g. an email column) means an index lookup narrows results to very few rows — ideal for indexing. Low selectivity (e.g. a boolean flag) means the index barely narrows anything down, so the optimizer may reasonably choose a full table scan instead, and the index adds write overhead for little read benefit (a partial index on the minority value can still help in that case).

**7. What is index fragmentation and how do you fix it?**

Fragmentation happens as INSERTs, UPDATEs, DELETEs, and especially B-Tree page splits leave index pages partially empty or out of physical/logical order over time, which increases the number of page reads needed for the same logical search and hurts range-scan performance. It's fixed by rebuilding the index (recreating it from scratch, fully compacted) or reorganizing it (a lighter-weight defragmentation of existing pages), typically run as periodic maintenance.

**8. Explain the difference between unique and non-unique indexes.**

A unique index enforces that no two rows can have the same value in the indexed column(s) (nulls are often treated as distinct, depending on the engine) — it's both a performance structure and a data-integrity constraint, and is created automatically for `PRIMARY KEY` and `UNIQUE` column constraints. A non-unique (regular) index allows duplicate values and exists purely to speed up lookups, with no constraint behavior.

**9. What happens to indexes during INSERT/UPDATE/DELETE?**

INSERT adds a new entry to every index defined on the table, not just the primary structure. UPDATE removes and re-inserts the entry in any index whose indexed column(s) changed value (if unrelated columns change, non-covering indexes on those columns are untouched). DELETE removes the corresponding entry from every index. This is the "write penalty" — more indexes mean more work per write, and B-Tree node page splits during inserts are a particularly expensive case.

**10. How do you identify missing or unused indexes?**

For unused indexes, query the engine's usage statistics view (e.g. PostgreSQL's `pg_stat_user_indexes`, filtering for `idx_scan = 0`) to find indexes that are never used by the query planner — candidates for removal since they only add write overhead. For missing indexes, use the engine's recommendation tooling (e.g. SQL Server's `sys.dm_db_missing_index_*` views) or manually inspect `EXPLAIN ANALYZE` output on slow queries, looking for full/sequential scans on large tables where a `WHERE`, `JOIN`, or `ORDER BY` column has no supporting index.

---

# SQL JOINs Explained

*SQL Fundamentals*

Master all JOIN types with visual explanations and practical examples.

## The Big Picture

JOINs are the foundation of relational databases — they allow you to combine data from multiple tables based on related columns. Understanding JOINs is essential for writing efficient queries and is one of the most frequently tested topics in SQL interviews.

**Why This Matters in Interviews**

- "What is the difference between INNER JOIN and LEFT JOIN?"
- "When would you use a FULL OUTER JOIN?"
- "Explain SELF JOIN with an example"
- "How do you optimize queries with multiple JOINs?"

## The Six Types of JOINs

- **INNER JOIN** — Returns only matching rows from both tables
- **LEFT JOIN** — All rows from left table, matching from right
- **RIGHT JOIN** — All rows from right table, matching from left
- **FULL OUTER JOIN** — All rows from both tables, NULL where no match
- **CROSS JOIN** — Cartesian product - every row combined
- **SELF JOIN** — Table joined to itself

## Example Dataset

**Employees (Left Table)**

| id | name | dept_id |
|---|---|---|
| 1 | Alice | 1 |
| 2 | Bob | 2 |
| 3 | Charlie | 1 |
| 4 | Diana | NULL |

**Departments (Right Table)**

| id | dept_name |
|---|---|
| 1 | Engineering |
| 2 | Marketing |
| 3 | Finance |

```sql
SELECT e.name, d.dept_name FROM employees e INNER JOIN departments d ON e.dept_id = d.id;
```

| name | dept_name |
|---|---|
| Alice | Engineering |
| Bob | Marketing |
| Charlie | Engineering |

Result: 3 rows.

## INNER JOIN

INNER JOIN returns only the rows where there is a match in both tables. If a row in the left table has no matching row in the right table (or vice versa), it is excluded from the result.

```sql
-- INNER JOIN: Only matching rows
SELECT e.name, e.salary, d.dept_name
FROM employees e
INNER JOIN departments d ON e.dept_id = d.id;

-- Equivalent using WHERE (older syntax)
SELECT e.name, e.salary, d.dept_name
FROM employees e, departments d
WHERE e.dept_id = d.id;
```

**When to Use INNER JOIN**

- You only want rows that have related data in both tables.
- Foreign key relationships are mandatory (NOT NULL).
- Reporting where incomplete data should be excluded.

## LEFT JOIN (LEFT OUTER JOIN)

LEFT JOIN returns all rows from the left table, and the matching rows from the right table. If there is no match, NULL values are returned for the right table columns.

```sql
-- LEFT JOIN: All from left, matching from right
SELECT e.name, e.salary, d.dept_name
FROM employees e
LEFT JOIN departments d ON e.dept_id = d.id;

-- Find employees without a department
SELECT e.name
FROM employees e
LEFT JOIN departments d ON e.dept_id = d.id
WHERE d.id IS NULL;
```

**Common Use Case: Finding Missing Relationships**

`LEFT JOIN` with `WHERE right_table.column IS NULL` is a common pattern to find rows in the left table that have no corresponding rows in the right table:

- Customers who have never placed an order
- Products that have never been sold
- Employees not assigned to any project

## RIGHT JOIN (RIGHT OUTER JOIN)

RIGHT JOIN is the opposite of LEFT JOIN. It returns all rows from the right table, and the matching rows from the left table. If there is no match, NULL values are returned for the left table columns.

```sql
-- RIGHT JOIN: All from right, matching from left
SELECT e.name, d.dept_name
FROM employees e
RIGHT JOIN departments d ON e.dept_id = d.id;

-- Find departments with no employees
SELECT d.dept_name
FROM employees e
RIGHT JOIN departments d ON e.dept_id = d.id
WHERE e.id IS NULL;
```

**RIGHT JOIN vs LEFT JOIN**

In practice, RIGHT JOIN is rarely used because you can always rewrite it as a LEFT JOIN by swapping the table order. Most developers prefer LEFT JOIN for consistency and readability.

```sql
-- These two queries are equivalent:
SELECT * FROM A RIGHT JOIN B ON A.id = B.a_id;
SELECT * FROM B LEFT JOIN A ON A.id = B.a_id;
```

## FULL OUTER JOIN

FULL OUTER JOIN returns all rows from both tables. Where there is a match, the row contains data from both tables. Where there is no match, the row contains NULL for the missing side.

```sql
-- FULL OUTER JOIN: All rows from both tables
SELECT e.name, d.dept_name
FROM employees e
FULL OUTER JOIN departments d ON e.dept_id = d.id;

-- MySQL doesn't support FULL OUTER JOIN directly
-- Use UNION of LEFT and RIGHT JOIN instead:
SELECT e.name, d.dept_name
FROM employees e
LEFT JOIN departments d ON e.dept_id = d.id
UNION
SELECT e.name, d.dept_name
FROM employees e
RIGHT JOIN departments d ON e.dept_id = d.id;
```

**When to Use FULL OUTER JOIN**

- Data reconciliation between two systems.
- Finding all mismatches in both directions.
- Audit reports showing complete picture.
- Comparing two datasets for differences.

## CROSS JOIN (Cartesian Product)

CROSS JOIN returns the Cartesian product of both tables — every row from the first table is combined with every row from the second table. If Table A has 10 rows and Table B has 5 rows, the result has 50 rows.

```sql
-- CROSS JOIN: Every combination
SELECT colors.name, sizes.name
FROM colors
CROSS JOIN sizes;

-- Equivalent syntax (implicit cross join)
SELECT colors.name, sizes.name
FROM colors, sizes;

-- Practical example: Generate all date-product combinations
SELECT d.date, p.product_name,
       COALESCE(s.quantity, 0) AS quantity
FROM dates d
CROSS JOIN products p
LEFT JOIN sales s ON d.date = s.date AND p.id = s.product_id;
```

**Caution: CROSS JOIN Can Be Dangerous**

CROSS JOIN can produce massive result sets. A cross join between two tables with 1000 rows each produces 1,000,000 rows. Use with caution and only when you specifically need all combinations.

- Always verify table sizes before using CROSS JOIN.
- Consider adding a WHERE clause to limit results.
- Common legitimate uses: generating test data, creating schedules, matrix reports.

## SELF JOIN

A SELF JOIN is when a table is joined to itself. This is useful for hierarchical data (like employee-manager relationships) or comparing rows within the same table.

```sql
-- SELF JOIN: Find employees and their managers
SELECT
    e.name AS employee_name,
    m.name AS manager_name
FROM employees e
LEFT JOIN employees m ON e.manager_id = m.id;

-- Find employees who earn more than their manager
SELECT e.name, e.salary, m.name AS manager, m.salary AS manager_salary
FROM employees e
JOIN employees m ON e.manager_id = m.id
WHERE e.salary > m.salary;

-- Find pairs of employees in the same department
SELECT e1.name, e2.name, e1.dept_id
FROM employees e1
JOIN employees e2 ON e1.dept_id = e2.dept_id
WHERE e1.id < e2.id;  -- Avoid duplicates and self-pairs
```

**Common SELF JOIN Use Cases**

- Organizational hierarchies (employee-manager)
- Finding duplicates or similar records
- Comparing sequential records (current vs previous)
- Graph traversal (friends of friends)
- Bill of materials (parts containing other parts)

## Joining Multiple Tables

Real-world queries often require joining three or more tables. The key is to chain JOINs logically, connecting each table through their relationships.

```sql
-- Join 3 tables: orders, customers, and products
SELECT
    c.name AS customer,
    o.order_date,
    p.product_name,
    oi.quantity,
    oi.price
FROM orders o
JOIN customers c ON o.customer_id = c.id
JOIN order_items oi ON o.id = oi.order_id
JOIN products p ON oi.product_id = p.id
WHERE o.order_date >= '2024-01-01'
ORDER BY o.order_date DESC;
```

**JOIN Order and Performance**

The order in which you write JOINs can affect readability, but most modern query optimizers will reorder JOINs for optimal performance. However, understanding join order helps with:

- **Readability:** Start from the main entity and join related tables.
- **Debugging:** Build queries incrementally, adding one JOIN at a time.
- **Hints:** Some databases allow forcing join order with hints.

**Best Practice: Qualify All Column Names**

When joining multiple tables, always use table aliases and qualify column names to avoid ambiguity and improve readability:

```sql
-- Bad: Ambiguous column names
SELECT name, date, amount FROM orders JOIN customers...

-- Good: Qualified column names with aliases
SELECT c.name, o.order_date, o.amount
FROM orders o
JOIN customers c...
```

## JOIN vs Subquery

Many queries can be written using either JOINs or subqueries. Understanding when to use each approach is important for writing efficient and readable SQL.

**Using JOIN** — Generally preferred when you need columns from multiple tables in the result.

```sql
SELECT e.name, d.dept_name
FROM employees e
JOIN departments d
  ON e.dept_id = d.id
WHERE d.location = 'NYC';
```

**Using Subquery** — Useful for existence checks or when you only need data from one table.

```sql
SELECT e.name
FROM employees e
WHERE e.dept_id IN (
  SELECT id
  FROM departments
  WHERE location = 'NYC'
);
```

| Aspect | JOIN | Subquery |
|---|---|---|
| Performance | Usually optimized well by database | Correlated subqueries can be slow |
| Readability | Better for combining multiple tables | Better for simple existence checks |
| Result Columns | Can select from all joined tables | Limited to outer query tables |
| Duplicates | May produce duplicates (use DISTINCT) | IN/EXISTS naturally handles duplicates |

## Top 10 Interview Questions

Master these frequently asked questions about SQL JOINs:

**1. What is the difference between INNER JOIN and LEFT JOIN?**

INNER JOIN returns only rows where a match exists in both tables — unmatched rows from either side are excluded entirely. LEFT JOIN returns every row from the left table regardless of whether a match exists in the right table; when there's no match, the right table's columns come back as NULL. So LEFT JOIN is a superset: it includes everything INNER JOIN would return, plus the left table's unmatched rows.

**2. When would you use a FULL OUTER JOIN?**

When you need every row from both tables regardless of whether a match exists on either side — useful for data reconciliation between two systems, finding mismatches in both directions, audit reports needing the complete picture, or comparing two datasets to see what's in A but not B, in B but not A, and what matches. Note MySQL has no native FULL OUTER JOIN; it's emulated with `UNION` of a LEFT JOIN and a RIGHT JOIN.

**3. Explain SELF JOIN with a practical example.**

A SELF JOIN joins a table to itself, typically using two aliases to treat it as if it were two tables. The classic example is an employee-manager hierarchy stored in one `employees` table with a `manager_id` column referencing `employees.id`: `SELECT e.name, m.name AS manager FROM employees e LEFT JOIN employees m ON e.manager_id = m.id;`. It's also used to find duplicate/similar records or compare each row against other rows in the same table (e.g., employees earning more than their manager).

**4. How do you find rows in one table that have no match in another?**

Use a LEFT JOIN from the table you want "all rows" from, then filter with `WHERE <right_table>.<column> IS NULL` — this isolates the rows where the LEFT JOIN couldn't find a match and had to fill in NULLs. Example: `SELECT e.name FROM employees e LEFT JOIN departments d ON e.dept_id = d.id WHERE d.id IS NULL;` finds employees with no department. This same pattern finds customers with no orders, or products never sold.

**5. What is a CROSS JOIN and when is it useful?**

A CROSS JOIN produces the full Cartesian product of two tables — every row of the first paired with every row of the second, with no join condition. It's useful for intentionally generating all combinations: test data, scheduling/calendar grids, matrix reports, or pairing a date range with a product list to ensure every date-product combination appears (even with zero sales) before left-joining in actual data. It's dangerous on large tables since row counts multiply (1000 x 1000 = 1,000,000 rows).

**6. How does JOIN order affect query performance?**

In most modern databases, the query optimizer is free to reorder joins internally based on cost estimates (table sizes, available indexes, selectivity) regardless of the order you wrote them in — so the SQL-level order mainly affects readability, not execution order. That said, join order can matter for: debugging (build incrementally, one JOIN at a time), using optimizer hints to force a specific order when you know better than the optimizer, and in some engines/configurations where the optimizer's search space is limited and it takes the written order more literally.

**7. What happens when you JOIN on columns with NULL values?**

NULL never equals NULL in standard SQL comparison semantics, so a row with a NULL in the join column will never match another row via a standard `ON a.col = b.col` condition — even if both sides are NULL. This means rows with NULL foreign keys are naturally excluded from INNER JOIN matches and show up as the "no match" side in a LEFT/RIGHT JOIN. If you need NULL-aware matching, you'd need something like `a.col IS NOT DISTINCT FROM b.col` (PostgreSQL) or explicit `IS NULL` handling.

**8. What is the difference between JOIN and subquery? When to use which?**

A JOIN combines columns from multiple tables directly in the result set and is generally well-optimized by the query planner; it's the right choice when you need actual columns from more than one table in your output. A subquery (especially with `IN`/`EXISTS`) is often clearer and sometimes faster for pure existence/filtering checks where you don't need columns from the other table — and it naturally avoids the duplicate-row problem a JOIN can introduce on one-to-many relationships, since IN/EXISTS just test membership rather than multiplying rows.

**9. How do you join three or more tables?**

Chain JOIN clauses sequentially, connecting each new table through a shared key with the tables already joined: `FROM orders o JOIN customers c ON o.customer_id = c.id JOIN order_items oi ON o.id = oi.order_id JOIN products p ON oi.product_id = p.id`. Always alias every table and qualify column names (`o.order_date`, not just `order_date`) to avoid ambiguity, since multiple joined tables commonly share column names like `id` or `name`.

**10. Why is RIGHT JOIN rarely used in practice?**

Because any RIGHT JOIN can be rewritten as an equivalent LEFT JOIN simply by swapping the order of the two tables (`A RIGHT JOIN B` ≡ `B LEFT JOIN A`), and most teams standardize on LEFT JOIN for consistency and readability — it keeps the "which side has all rows" convention always on the left, so you don't have to mentally flip direction depending on which keyword was used.

## Key Takeaways

- INNER JOIN returns only matching rows from both tables.
- LEFT JOIN returns all rows from left table, with NULLs for non-matches.
- RIGHT JOIN is the opposite of LEFT JOIN (rarely used in practice).
- FULL OUTER JOIN returns all rows from both tables.
- CROSS JOIN produces Cartesian product — use with caution.
- SELF JOIN connects a table to itself for hierarchical data.
- LEFT JOIN with `WHERE ... IS NULL` finds rows without matches.
- Always use table aliases when joining multiple tables.
- JOINs are generally preferred over subqueries for combining data.
- The optimizer usually determines the best join order automatically.

---

# Transactions and ACID Properties

*SQL Fundamentals*

Understanding how databases ensure data integrity and consistency.

## The Big Picture

A transaction is a sequence of database operations that are treated as a single unit of work. Either all operations succeed (commit), or none of them take effect (rollback). This is crucial for maintaining data integrity, especially in concurrent environments where multiple users access the database simultaneously.

**Why This Matters in Interviews**

- "What are ACID properties? Explain each one."
- "What happens if a transaction fails midway?"
- "Explain the difference between COMMIT and ROLLBACK"
- "How does the database ensure durability?"

**The Classic Example: Bank Transfer**

Consider transferring $100 from Alice's account to Bob's account. This requires two operations: debit Alice and credit Bob. What happens if the system crashes after debiting Alice but before crediting Bob? Without transactions, $100 would simply disappear. Transactions ensure this never happens.

## ACID Properties

ACID is an acronym describing the four key properties that guarantee reliable transaction processing:

**Atomicity — All or Nothing**

A transaction is an indivisible unit. Either all operations complete successfully, or none of them take effect. There is no partial completion.

Example: Transfer $100: Debit Alice AND Credit Bob must both succeed, or neither happens.

**Consistency — Valid State to Valid State**

A transaction brings the database from one valid state to another. All constraints, rules, and triggers must be satisfied after the transaction.

Example: Total money before = Total money after. Account balances cannot go negative if constrained.

**Isolation — Concurrent Independence**

Concurrent transactions execute as if they were running sequentially. One transaction cannot see the intermediate state of another.

Example: Two simultaneous transfers don't interfere with each other's balance calculations.

**Durability — Permanent Once Committed**

Once a transaction is committed, its changes are permanent and survive system failures, crashes, or power outages.

Example: After COMMIT, even if server crashes immediately, the transfer is preserved.

## Transaction Lifecycle

A transaction goes through several states from start to finish:

```
BEGIN -> Active
Active -> (last operation) -> Partially Committed
Active -> (error/abort) -> Failed
Partially Committed -> (COMMIT success) -> Committed
Partially Committed -> (COMMIT failure) -> Failed
Failed -> (ROLLBACK) -> Aborted
```

| State | Description | Can Transition To |
|---|---|---|
| Active | Transaction is executing operations | Partially Committed, Failed |
| Partially Committed | All operations completed, waiting for commit | Committed, Failed |
| Committed | Changes are permanently saved | End (terminal state) |
| Failed | Error occurred, cannot proceed | Aborted |
| Aborted | Transaction rolled back, changes undone | End (terminal state) |

## Transaction Syntax

Here's how to work with transactions in SQL:

```sql
-- Basic transaction structure
BEGIN TRANSACTION;  -- or just BEGIN, or START TRANSACTION

-- Your SQL operations here
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;

COMMIT;  -- Make changes permanent
-- or
ROLLBACK;  -- Undo all changes
```

**Transaction with Error Handling**

```sql
-- PostgreSQL example with error handling
BEGIN;

UPDATE accounts SET balance = balance - 100 WHERE id = 1;

-- Check if debit was successful
IF (SELECT balance FROM accounts WHERE id = 1) < 0 THEN
    ROLLBACK;
    RAISE EXCEPTION 'Insufficient funds';
END IF;

UPDATE accounts SET balance = balance + 100 WHERE id = 2;

COMMIT;
```

## Savepoints

Savepoints allow partial rollbacks within a transaction:

```sql
BEGIN;

INSERT INTO orders (customer_id, total) VALUES (1, 500);
SAVEPOINT order_created;

INSERT INTO order_items (order_id, product_id) VALUES (1, 101);
INSERT INTO order_items (order_id, product_id) VALUES (1, 999);  -- This fails!

-- Roll back to savepoint (keeps order, removes items)
ROLLBACK TO SAVEPOINT order_created;

-- Try again with valid data
INSERT INTO order_items (order_id, product_id) VALUES (1, 102);

COMMIT;
```

## How Databases Ensure Durability

Durability guarantees that committed transactions survive system failures. Databases achieve this through several mechanisms:

**Write-Ahead Logging (WAL)**

```
Transaction -> Write to WAL Log -> Acknowledge Commit -> Write to Data Files -> Checkpoint
Crash -> Recovery: Replay WAL
```

- **WAL (Write-Ahead Log):** Before any data modification, the change is first written to a log file.
- **Sequential Writes:** Log writes are sequential (fast), data file writes can be random (slower).
- **Checkpoints:** Periodically, all dirty pages are flushed to disk and a checkpoint record is written.
- **Crash Recovery:** After a crash, the database replays the WAL from the last checkpoint.

**Why WAL Works**

The key insight is that as long as the log is durable, data can always be reconstructed. Writing to the log is fast (sequential), and the actual data files can be updated lazily in the background.

## Common Transaction Scenarios

**Successful Transfer**

Alice sends $100 to Bob. Both UPDATE statements succeed. COMMIT is issued. Changes are permanent.

Result: Alice has $900, Bob has $600.

**Insufficient Funds**

Alice (balance $50) tries to send $100. Constraint violation detected. ROLLBACK is issued.

Result: Both accounts unchanged.

**System Crash Mid-Transaction**

Alice debited, but system crashes before crediting Bob. On restart, uncommitted transaction is rolled back.

Result: Both accounts unchanged.

**System Crash After Commit**

Transfer completes, COMMIT written to WAL, then crash. On restart, committed changes are recovered.

Result: Transfer preserved (Durability).

## Auto-Commit Mode

By default, many databases run in auto-commit mode where each statement is automatically wrapped in its own transaction:

```sql
-- With auto-commit ON (default in most databases)
UPDATE accounts SET balance = balance - 100 WHERE id = 1;  -- Immediately committed
UPDATE accounts SET balance = balance + 100 WHERE id = 2;  -- Immediately committed
-- DANGER: If second statement fails, first is already committed!

-- With explicit transaction (auto-commit effectively OFF)
BEGIN;
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;
COMMIT;  -- Both committed together, or neither
```

**Best Practice**

For operations that must succeed or fail together, always use explicit transactions. Never rely on auto-commit for multi-statement operations that should be atomic.

## Top 10 Interview Questions

Master these frequently asked questions about transactions:

**1. What are ACID properties? Explain each one.**

- **Atomicity:** A transaction is indivisible — either all its operations complete, or none do. No partial completion is ever left in place.
- **Consistency:** A transaction moves the database from one valid state to another, never violating constraints, rules, or triggers (e.g., total money in the system before and after a transfer must match).
- **Isolation:** Concurrent transactions behave as if executed sequentially — one transaction never observes another's intermediate, uncommitted state.
- **Durability:** Once committed, a transaction's changes are permanent and survive crashes or power loss, typically guaranteed via write-ahead logging.

**2. What happens if a transaction fails midway through execution?**

The transaction enters a "Failed" state and the database rolls it back (either automatically or via an explicit `ROLLBACK`), undoing every operation performed since `BEGIN` so the database returns to the state it was in before the transaction started. No partial changes are left behind — this is Atomicity in action. If a savepoint was set, the rollback can instead target just that savepoint, undoing only the operations after it while preserving earlier work in the same transaction.

**3. Explain the difference between COMMIT and ROLLBACK.**

`COMMIT` makes all changes made during the transaction permanent and visible to other transactions — once committed, Durability guarantees the changes survive even a crash. `ROLLBACK` discards all changes made since the transaction began (or since a named savepoint), returning the data to its prior state as if the transaction never happened. Every transaction ends in exactly one of these two outcomes.

**4. What is a savepoint and when would you use it?**

A savepoint is a named marker set within a transaction that you can later roll back to without aborting the entire transaction. It's useful when a transaction has multiple logical steps and you want the ability to undo just a later step while keeping earlier work — e.g., creating an order succeeds and is kept via `SAVEPOINT order_created`, but if inserting a specific order item fails, you `ROLLBACK TO SAVEPOINT order_created` to discard just the failed items and retry, instead of discarding the whole order.

**5. How does the database ensure durability?**

Primarily through Write-Ahead Logging (WAL): before any data file is modified, the change is first written to a sequential log file, and only after that log write succeeds is the transaction acknowledged as committed. Because sequential log writes are fast and durable, the database can commit quickly while updating the actual (random-access) data files lazily in the background. If a crash happens, recovery replays the WAL from the last checkpoint to reconstruct any committed changes that hadn't yet been flushed to the data files.

**6. What is the difference between optimistic and pessimistic locking?**

Pessimistic locking acquires a lock on data as soon as it's read, blocking other transactions from modifying (or sometimes even reading) it until the lock is released — it assumes conflicts are likely, trading concurrency for safety. Optimistic locking assumes conflicts are rare: transactions read data without locking it, and at commit time the database checks (often via a version number or timestamp column) whether the data changed since it was read; if it did, the transaction is rejected/retried, otherwise it commits. Optimistic locking scales better under low contention; pessimistic locking is safer under high contention.

**7. What is a distributed transaction and what is 2PC?**

A distributed transaction spans multiple separate databases or services (e.g., updating data in two different database servers) that must all commit or all roll back together, even though no single database can coordinate across the boundary on its own. Two-Phase Commit (2PC) is the classic protocol for this: in the **prepare** phase, a coordinator asks every participant to get ready to commit and lock in that they *can* commit; only if all participants vote yes does the coordinator send the **commit** phase instruction to all of them (if any votes no, it tells all to abort instead). This guarantees atomicity across systems, at the cost of blocking and coordinator-failure risk.

**8. What is auto-commit mode and why should you be careful with it?**

Auto-commit is the default mode in most databases where every individual SQL statement is implicitly wrapped in and immediately committed as its own transaction. The danger is for multi-statement operations that need to be atomic together (like debit-then-credit in a transfer): if the first statement auto-commits successfully and the second one then fails, you're left with a corrupted, half-done state — the debit happened but the credit never did. The fix is to wrap related statements in an explicit `BEGIN ... COMMIT`/`ROLLBACK` block so they succeed or fail as one unit.

**9. How do long-running transactions affect the database?**

They hold locks (or prevent old row versions from being cleaned up, in MVCC systems) for extended periods, which increases contention and can block other transactions waiting on the same rows/tables, hurting overall throughput. They also keep the transaction log / undo space from being reclaimed (since the data might still be rolled back), which can bloat storage and, in some systems, degrade performance for all other queries (e.g., PostgreSQL vacuum being held up by a long-running transaction). Best practice is to keep transactions short and focused, doing any slow, non-transactional work (like external API calls) outside the transaction boundary.

**10. What is a deadlock and how do databases handle it?**

A deadlock in the database sense is the same core problem as in multithreading: Transaction A holds a lock on row/table X and waits for a lock on Y, while Transaction B holds a lock on Y and waits for X — neither can proceed. Databases handle this with automatic deadlock detection (periodically checking the lock wait-for graph for cycles): when a cycle is found, the database picks one transaction as the "victim," aborts it with an error, and rolls it back, releasing its locks so the other transaction(s) can proceed. Applications are expected to catch that error and retry the aborted transaction.

## Key Takeaways

- A transaction is a unit of work that is atomic — all or nothing.
- ACID: Atomicity, Consistency, Isolation, Durability.
- Atomicity ensures partial failures don't corrupt data.
- Consistency maintains database constraints and rules.
- Isolation prevents concurrent transactions from interfering.
- Durability guarantees committed data survives crashes.
- Use `BEGIN`/`COMMIT`/`ROLLBACK` to control transactions explicitly.
- Savepoints allow partial rollbacks within a transaction.
- Write-Ahead Logging (WAL) is how databases ensure durability.
- Never rely on auto-commit for multi-statement atomic operations.

---

# Transaction Isolation Levels

*SQL Fundamentals*

Understanding concurrency control and the trade-offs between consistency and performance.

## The Big Picture

When multiple transactions run concurrently, they can interfere with each other in unexpected ways. Isolation levels define how much one transaction is isolated from the effects of other concurrent transactions. Higher isolation provides more consistency but reduces concurrency and performance.

**Why This Matters in Interviews**

- "What are the four transaction isolation levels?"
- "Explain dirty read, non-repeatable read, and phantom read"
- "Which isolation level prevents phantom reads?"
- "What is the default isolation level in PostgreSQL/MySQL?"

## Read Phenomena (Concurrency Problems)

There are three main problems that can occur when transactions run concurrently:

**Dirty Read**

Transaction reads data written by another transaction that has not yet committed. If that transaction rolls back, you have read data that never officially existed.

**Non-Repeatable Read**

Transaction reads the same row twice and gets different values because another transaction modified and committed the row between the two reads.

**Phantom Read**

Transaction executes the same query twice and gets different sets of rows because another transaction inserted or deleted rows that match the query condition.

## Visual Timeline Examples

**Dirty Read Example**

```sql
-- Transaction 1                    -- Transaction 2
BEGIN;
UPDATE accounts
  SET balance = 900
  WHERE id = 1;
                                    BEGIN;
                                    SELECT balance FROM accounts
                                      WHERE id = 1;
                                    -- Returns 900 (uncommitted!)
ROLLBACK;
                                    -- T2 has read data that never existed!
```

**Non-Repeatable Read Example**

```sql
-- Transaction 1                    -- Transaction 2
BEGIN;
SELECT balance FROM accounts
  WHERE id = 1;
-- Returns 1000
                                    BEGIN;
                                    UPDATE accounts
                                      SET balance = 500
                                      WHERE id = 1;
                                    COMMIT;
SELECT balance FROM accounts
  WHERE id = 1;
-- Returns 500 (different!)
```

**Phantom Read Example**

```sql
-- Transaction 1                    -- Transaction 2
BEGIN;
SELECT COUNT(*) FROM employees
  WHERE dept = 'Sales';
-- Returns 5
                                    BEGIN;
                                    INSERT INTO employees
                                      (name, dept)
                                      VALUES ('New', 'Sales');
                                    COMMIT;
SELECT COUNT(*) FROM employees
  WHERE dept = 'Sales';
-- Returns 6 (phantom row appeared!)
```

## The Four Isolation Levels

SQL standard defines four isolation levels, from lowest to highest isolation:

**READ UNCOMMITTED** (Lowest)

Transactions can see uncommitted changes from other transactions. Maximum concurrency but minimum consistency. Rarely used in practice. Allows Dirty Read, Non-Repeatable Read, and Phantom Read.

**READ COMMITTED** (Default for many DBs)

Transactions only see committed changes. Each query sees a fresh snapshot. Good balance of consistency and performance. Prevents Dirty Read; allows Non-Repeatable Read and Phantom Read.

**REPEATABLE READ** (Default for MySQL)

Transaction sees a consistent snapshot from start. Same query returns same rows. MySQL InnoDB also prevents phantoms at this level. Prevents Dirty Read and Non-Repeatable Read; allows Phantom Read (except in MySQL InnoDB).

**SERIALIZABLE** (Highest)

Transactions execute as if they were running one at a time. Complete isolation but significantly reduced concurrency. Prevents Dirty Read, Non-Repeatable Read, and Phantom Read.

## Summary Table

| Isolation Level | Dirty Read | Non-Repeatable Read | Phantom Read |
|---|---|---|---|
| READ UNCOMMITTED | Possible | Possible | Possible |
| READ COMMITTED | Prevented | Possible | Possible |
| REPEATABLE READ | Prevented | Prevented | Possible* |
| SERIALIZABLE | Prevented | Prevented | Prevented |

\* MySQL InnoDB prevents phantom reads at REPEATABLE READ using next-key locking.

## Setting Isolation Level

You can set the isolation level for a session or a specific transaction:

```sql
-- Set for session (all subsequent transactions)
SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED;

-- Set for next transaction only
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

-- PostgreSQL: Set within transaction
BEGIN;
SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
-- ... your queries ...
COMMIT;

-- MySQL: Check current isolation level
SELECT @@transaction_isolation;

-- PostgreSQL: Check current isolation level
SHOW transaction_isolation;
```

## Database Default Isolation Levels

| Database | Default Isolation Level |
|---|---|
| PostgreSQL | READ COMMITTED |
| MySQL (InnoDB) | REPEATABLE READ |
| SQL Server | READ COMMITTED |
| Oracle | READ COMMITTED |
| SQLite | SERIALIZABLE |
| H2 Database | READ COMMITTED |

## How Isolation Is Implemented: MVCC

Most modern databases use Multi-Version Concurrency Control (MVCC) to implement isolation without heavy locking:

**How MVCC Works**

- **Multiple Versions:** Each row can have multiple versions, each tagged with transaction IDs.
- **Snapshot:** When a transaction starts, it gets a snapshot of which transactions are committed.
- **Visibility Rules:** A row version is visible if it was created by a committed transaction before the snapshot.
- **No Read Locks:** Readers don't block writers, writers don't block readers.

**MVCC Benefits**

- Readers never block writers.
- Writers never block readers.
- High concurrency for read-heavy workloads.
- Consistent snapshots without locking.

**MVCC Trade-offs**

- Old versions accumulate (requires `VACUUM` in PostgreSQL).
- More storage space needed for multiple versions.
- Write-write conflicts still require locking or conflict detection.

## Choosing the Right Isolation Level

| Use Case | Recommended Level | Reason |
|---|---|---|
| Simple reads, high throughput | READ COMMITTED | Good balance of consistency and performance |
| Reports that need consistent data | REPEATABLE READ | Entire report sees same snapshot |
| Financial transactions | SERIALIZABLE | Maximum consistency, prevent all anomalies |
| Analytics on stale data OK | READ UNCOMMITTED | Maximum throughput, approximate results |
| Inventory/booking systems | SERIALIZABLE | Prevent double-booking via phantoms |

**Rule of Thumb**

Start with the database default (usually READ COMMITTED). Only increase isolation level when you have a specific consistency requirement that demands it, as higher isolation reduces concurrency and can cause more conflicts/retries.

## Top 10 Interview Questions

Master these frequently asked questions about isolation levels:

**1. What are the four transaction isolation levels?**

From lowest to highest isolation: `READ UNCOMMITTED`, `READ COMMITTED`, `REPEATABLE READ`, and `SERIALIZABLE`. Each level progressively prevents more of the three read phenomena (dirty read, non-repeatable read, phantom read) at the cost of reduced concurrency.

**2. Explain dirty read, non-repeatable read, and phantom read.**

A dirty read happens when a transaction reads another transaction's uncommitted changes, which may vanish if that transaction rolls back. A non-repeatable read happens when a transaction reads the same row twice within itself and gets different values because another transaction updated and committed that row in between. A phantom read happens when a transaction re-runs the same query and gets a different set of rows because another transaction inserted or deleted rows matching the query's condition in between.

**3. Which isolation level prevents phantom reads?**

`SERIALIZABLE` is the only standard level that fully prevents phantom reads. `REPEATABLE READ` prevents them in some implementations (notably MySQL InnoDB, via next-key locking) but the SQL standard only guarantees phantom-read prevention at `SERIALIZABLE`.

**4. What is the default isolation level in PostgreSQL and MySQL?**

PostgreSQL defaults to `READ COMMITTED`. MySQL (InnoDB) defaults to `REPEATABLE READ`, and uniquely among mainstream databases also prevents phantom reads at that level via next-key locking.

**5. What is MVCC and how does it relate to isolation levels?**

MVCC (Multi-Version Concurrency Control) is the mechanism most modern databases use to implement isolation without heavy locking: instead of blocking readers behind writers, each row can have multiple versions tagged by the transaction that created them, and each transaction sees a consistent snapshot based on which versions were committed before it started. This lets readers avoid blocking writers and vice versa, while still delivering the consistency guarantees that each isolation level promises.

**6. When would you use READ UNCOMMITTED isolation level?**

Rarely in practice — only when you need maximum throughput and can tolerate seeing uncommitted, potentially-rolled-back data, such as rough real-time dashboards/analytics or monitoring queries where approximate numbers are acceptable and you don't want your query to wait on or be blocked by other transactions' locks.

**7. How does SERIALIZABLE isolation level affect performance?**

It significantly reduces concurrency because it must guarantee transactions behave as if executed strictly one at a time — implementations achieve this either through heavy locking (blocking concurrent access to the same data) or, more commonly in MVCC databases, through conflict detection that aborts one of the conflicting transactions at commit time. This means SERIALIZABLE transactions are more likely to be blocked or forced to retry due to serialization failures under contention, trading throughput for correctness.

**8. What is the difference between locking and MVCC for isolation?**

Lock-based isolation makes readers and writers block each other directly — a reader may need a shared lock that blocks a writer's exclusive lock, and vice versa — which guarantees consistency but limits concurrency. MVCC avoids this by keeping multiple versions of each row: readers are given a consistent snapshot of already-committed data and never need to wait on writers, and writers create new versions without blocking readers, only needing locks against other concurrent writers to the same row.

**9. How do you choose the right isolation level for an application?**

Start from the database's default (usually `READ COMMITTED`) and only raise it when you have a specific, identified consistency requirement — e.g. `REPEATABLE READ` for a report that must see one consistent snapshot throughout, or `SERIALIZABLE` for financial transactions or booking/inventory systems where double-spending or double-booking via phantom reads must be impossible. Weigh this against the performance and retry-rate cost: higher isolation means more blocking or more aborted/retried transactions under contention.

**10. What happens when two transactions conflict under SERIALIZABLE?**

The database detects that allowing both transactions to commit would produce a result inconsistent with some serial (one-at-a-time) execution order, and it aborts one of them with a serialization failure error rather than letting the conflicting commit through. The application is expected to catch this error and retry the aborted transaction from the beginning.

## Key Takeaways

- Three read phenomena: dirty read, non-repeatable read, phantom read.
- Four isolation levels: READ UNCOMMITTED, READ COMMITTED, REPEATABLE READ, SERIALIZABLE.
- Higher isolation = more consistency but less concurrency.
- READ COMMITTED is the default for most databases (PostgreSQL, Oracle, SQL Server).
- MySQL InnoDB defaults to REPEATABLE READ.
- MVCC enables high concurrency by maintaining multiple row versions.
- SERIALIZABLE prevents all anomalies but may cause more conflicts.
- Choose isolation level based on specific consistency requirements.
- Higher isolation levels may require retry logic for conflicts.
- Know your database's default and adjust only when necessary.

---

# Subqueries and CTEs

*SQL Fundamentals*

Master nested queries, common table expressions, and recursive patterns.

## The Big Picture

Subqueries and CTEs (Common Table Expressions) allow you to break complex queries into manageable pieces. A subquery is a query nested inside another query, while a CTE is a named temporary result set that you can reference within a statement. Both are essential for writing readable, maintainable SQL.

**Why This Matters in Interviews**

- "What is the difference between a subquery and a CTE?"
- "When would you use a correlated subquery?"
- "Explain how recursive CTEs work"
- "Rewrite this subquery as a JOIN"

## Types of Subqueries

**Scalar Subquery** — Returns a single value (one row, one column). Can be used anywhere a single value is expected.

```sql
SELECT name, (SELECT MAX(salary) FROM employees) AS max_sal FROM employees;
```

**Row Subquery** — Returns a single row with multiple columns. Used with row constructors for comparisons.

```sql
WHERE (dept_id, salary) = (SELECT dept_id, MAX(salary) FROM ...)
```

**Table Subquery** — Returns multiple rows and columns. Used in `FROM` clause (derived table) or with `IN`/`EXISTS`.

```sql
SELECT * FROM (SELECT ... ) AS derived_table WHERE ...
```

**Correlated Subquery** — References columns from outer query. Executes once per row of outer query — can be slow!

```sql
WHERE salary > (SELECT AVG(salary) FROM employees e2 WHERE e2.dept = e1.dept)
```

## Subquery Examples

**Subquery in WHERE Clause**

```sql
-- Find employees who earn more than average
SELECT name, salary
FROM employees
WHERE salary > (SELECT AVG(salary) FROM employees);

-- Find employees in departments located in NYC
SELECT name, dept_id
FROM employees
WHERE dept_id IN (
    SELECT id FROM departments WHERE location = 'NYC'
);
```

**Subquery in FROM Clause (Derived Table)**

```sql
-- Get department statistics and filter
SELECT dept_name, avg_salary
FROM (
    SELECT
        d.name AS dept_name,
        AVG(e.salary) AS avg_salary
    FROM departments d
    JOIN employees e ON d.id = e.dept_id
    GROUP BY d.name
) AS dept_stats
WHERE avg_salary > 75000;
```

**Subquery in SELECT Clause (Scalar)**

```sql
-- Show each employee with their department's average
SELECT
    name,
    salary,
    (SELECT AVG(salary)
     FROM employees e2
     WHERE e2.dept_id = e1.dept_id) AS dept_avg
FROM employees e1;
```

## EXISTS vs IN

```sql
-- Using IN - good for small result sets
SELECT name FROM employees
WHERE dept_id IN (SELECT id FROM departments WHERE budget > 1000000);

-- Using EXISTS - often better for large tables
SELECT name FROM employees e
WHERE EXISTS (
    SELECT 1 FROM departments d
    WHERE d.id = e.dept_id AND d.budget > 1000000
);

-- NOT EXISTS - find employees without orders
SELECT name FROM customers c
WHERE NOT EXISTS (
    SELECT 1 FROM orders o WHERE o.customer_id = c.id
);
```

**IN vs EXISTS Performance**

- **IN:** Subquery executes once, result cached. Better when subquery result is small.
- **EXISTS:** Stops at first match (short-circuit). Better when outer table is small or subquery result is large.
- **IN with NULLs:** Be careful — `NULL IN (...)` returns NULL, not FALSE!

## Common Table Expressions (CTEs)

A CTE is a named temporary result set defined using the `WITH` clause. It exists only for the duration of the query and makes complex queries more readable.

```sql
-- Basic CTE syntax
WITH cte_name AS (
    SELECT ... FROM ...
)
SELECT * FROM cte_name;

-- Multiple CTEs
WITH
    high_earners AS (
        SELECT * FROM employees WHERE salary > 100000
    ),
    dept_counts AS (
        SELECT dept_id, COUNT(*) AS cnt
        FROM high_earners
        GROUP BY dept_id
    )
SELECT d.name, dc.cnt
FROM departments d
JOIN dept_counts dc ON d.id = dc.dept_id;
```

**CTE vs Subquery**

Subquery (Derived Table) — Defined inline where used. Can be harder to read when nested.

```sql
SELECT *
FROM (
    SELECT dept_id,
           AVG(salary) AS avg_sal
    FROM employees
    GROUP BY dept_id
) AS dept_avg
WHERE avg_sal > 50000;
```

CTE — Defined at the top, named, can be referenced multiple times.

```sql
WITH dept_avg AS (
    SELECT dept_id,
           AVG(salary) AS avg_sal
    FROM employees
    GROUP BY dept_id
)
SELECT * FROM dept_avg
WHERE avg_sal > 50000;
```

**When to Use CTEs**

- Breaking complex queries into logical steps.
- When you need to reference the same subquery multiple times.
- Recursive queries (hierarchies, graphs).
- Improving readability of deeply nested queries.

## Recursive CTEs

Recursive CTEs can reference themselves, making them perfect for hierarchical or graph data like org charts, bill of materials, or folder structures.

```sql
-- Recursive CTE structure
WITH RECURSIVE cte_name AS (
    -- Base case (anchor member)
    SELECT ... FROM table WHERE condition

    UNION ALL

    -- Recursive case (recursive member)
    SELECT ... FROM table
    JOIN cte_name ON ...
)
SELECT * FROM cte_name;
```

**Org Chart Example**

```sql
-- Find all employees under a manager (org hierarchy)
WITH RECURSIVE org_tree AS (
    -- Base case: start with the CEO (no manager)
    SELECT
        id,
        name,
        manager_id,
        0 AS level
    FROM employees
    WHERE manager_id IS NULL

    UNION ALL

    -- Recursive case: find employees of current level
    SELECT
        e.id,
        e.name,
        e.manager_id,
        ot.level + 1
    FROM employees e
    JOIN org_tree ot ON e.manager_id = ot.id
)
SELECT * FROM org_tree ORDER BY level, name;
```

The recursion proceeds level by level: iteration 0 finds the root (`WHERE manager_id IS NULL`), iteration 1 finds the root's direct reports, iteration 2 finds their direct reports, and so on until an iteration produces no new rows, at which point the recursion stops.

## Subquery vs JOIN

Many queries can be written using either subqueries or JOINs. Understanding when to use each is important:

Using Subquery:

```sql
SELECT name
FROM employees
WHERE dept_id IN (
    SELECT id
    FROM departments
    WHERE location = 'NYC'
);
```

Using JOIN:

```sql
SELECT e.name
FROM employees e
JOIN departments d
  ON e.dept_id = d.id
WHERE d.location = 'NYC';
```

| Aspect | Subquery | JOIN |
|---|---|---|
| Readability | Can be clearer for existence checks | Clearer when combining data from multiple tables |
| Performance | Modern optimizers often convert to JOIN | Generally efficient, especially with indexes |
| Duplicates | IN/EXISTS naturally handles duplicates | May produce duplicates (need DISTINCT) |
| Columns | Cannot easily select from subquery table | Can select from all joined tables |

**Rule of Thumb**

- Use JOIN when you need columns from multiple tables.
- Use IN/EXISTS subquery for existence checks.
- Use CTE to improve readability of complex queries.
- Modern query optimizers often produce the same execution plan either way.

## Common Patterns

**Find Records with No Match**

```sql
-- Customers with no orders (3 equivalent approaches)

-- 1. LEFT JOIN + IS NULL
SELECT c.* FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
WHERE o.id IS NULL;

-- 2. NOT EXISTS
SELECT * FROM customers c
WHERE NOT EXISTS (
    SELECT 1 FROM orders o WHERE o.customer_id = c.id
);

-- 3. NOT IN (careful with NULLs!)
SELECT * FROM customers
WHERE id NOT IN (
    SELECT customer_id FROM orders WHERE customer_id IS NOT NULL
);
```

**Find Maximum Per Group**

```sql
-- Highest paid employee per department

-- Using correlated subquery
SELECT * FROM employees e1
WHERE salary = (
    SELECT MAX(salary) FROM employees e2
    WHERE e2.dept_id = e1.dept_id
);

-- Using CTE with window function (preferred)
WITH ranked AS (
    SELECT *,
           RANK() OVER (PARTITION BY dept_id ORDER BY salary DESC) AS rn
    FROM employees
)
SELECT * FROM ranked WHERE rn = 1;
```

**Running Difference**

```sql
-- Calculate change from previous row using CTE
WITH with_prev AS (
    SELECT
        date,
        value,
        LAG(value) OVER (ORDER BY date) AS prev_value
    FROM metrics
)
SELECT
    date,
    value,
    value - prev_value AS change
FROM with_prev;
```

## Top 10 Interview Questions

Master these frequently asked questions about subqueries and CTEs:

**1. What is the difference between a subquery and a CTE?**

A subquery is a query nested inline inside another query (in `WHERE`, `SELECT`, or `FROM`), defined at the point of use and not named. A CTE (`WITH name AS (...)`) is defined up front, given a name, and can be referenced one or more times later in the same statement, which makes complex queries more readable and avoids repeating the same subquery logic. Functionally, a non-recursive CTE is often treated by the optimizer much like a subquery/derived table — the main practical difference is readability and reusability, plus CTEs support recursion, which subqueries don't.

**2. What is a correlated subquery and why can it be slow?**

A correlated subquery references a column from the outer query (e.g. `WHERE e2.dept_id = e1.dept_id`), which means, conceptually, it must be re-evaluated once for every row processed by the outer query rather than being computed a single time. For large outer tables this can turn into effectively an O(n × m) operation, which is why they're often rewritten as JOINs or window functions for better performance, though modern optimizers can sometimes rewrite/decorrelate simple cases automatically.

**3. What is the difference between IN and EXISTS?**

`IN` evaluates the subquery once, materializes its full result set, and checks whether the outer value appears in it — efficient when the subquery's result set is small. `EXISTS` is a boolean check that stops as soon as it finds one matching row (short-circuits), making it efficient when the outer table is small or when the subquery itself would produce a huge result set. A key gotcha: `IN` behaves unexpectedly with NULLs (`x IN (1, NULL)` can yield NULL instead of a clean true/false), whereas `EXISTS`/`NOT EXISTS` doesn't have this problem.

**4. How do recursive CTEs work?**

A recursive CTE has two parts combined with `UNION ALL`: an anchor/base case (a plain query that produces the starting rows, e.g. the root of a hierarchy with no parent) and a recursive case that joins the CTE back to itself to find the "next level" based on the previous iteration's rows. The database repeatedly executes the recursive case, feeding each iteration's output back in as input to the next, until an iteration produces zero new rows, at which point the recursion terminates and all accumulated rows are returned.

**5. When would you use a subquery vs a JOIN?**

Use a JOIN when you need actual columns from more than one table in your result set — it's the natural tool for combining data. Use a subquery (particularly with `IN`/`EXISTS`/`NOT EXISTS`) when you only need to filter the outer table based on a condition in another table and don't need to display any of that other table's columns — it avoids the risk of duplicate rows that a JOIN can introduce on a one-to-many relationship.

**6. Why should you be careful with NOT IN and NULL values?**

If the subquery feeding a `NOT IN` can return even a single NULL value, the entire `NOT IN` expression evaluates to NULL (not true or false) for every comparison, because SQL's NULL comparison semantics propagate — this silently causes the query to return zero rows instead of the expected result. The safe fix is to either filter out NULLs explicitly in the subquery (`WHERE column IS NOT NULL`) or use `NOT EXISTS`, which doesn't have this problem since it's a row-existence check rather than a value-membership check.

**7. Can a CTE improve query performance?**

Generally, a plain (non-recursive, non-materialized) CTE does not inherently improve performance on its own — most optimizers treat it like an inlined subquery/derived table and may even re-evaluate it multiple times if referenced more than once, unless the engine materializes it. Its main benefit is readability and maintainability, letting you name and reuse a complex intermediate result. Some databases (e.g. PostgreSQL with `MATERIALIZED`, or when a CTE is referenced multiple times) can use CTEs to avoid recomputing the same subquery, which can help performance in those specific cases — but it's not a performance feature by default.

**8. How do you find the top N records per group using a subquery?**

The classic correlated-subquery approach for top-1 per group compares each row's value to the max within its group: `SELECT * FROM employees e1 WHERE salary = (SELECT MAX(salary) FROM employees e2 WHERE e2.dept_id = e1.dept_id)`. For top-N (N > 1) or ties handled cleanly, the preferred modern approach uses a window function inside a CTE: rank rows with `ROW_NUMBER()`/`RANK()` `OVER (PARTITION BY dept_id ORDER BY salary DESC)`, then filter the CTE's result with `WHERE rn <= N`.

**9. What is a derived table?**

A derived table is a subquery used in the `FROM` clause and given an alias, effectively creating an unnamed, temporary, in-memory "table" that the rest of the query can select from and filter further — e.g. `SELECT * FROM (SELECT dept_id, AVG(salary) AS avg_sal FROM employees GROUP BY dept_id) AS dept_avg WHERE avg_sal > 50000`. It's functionally very similar to a CTE, but defined inline at the point of use rather than named up front.

**10. How do you prevent infinite loops in recursive CTEs?**

Most databases impose a default recursion depth limit (e.g. PostgreSQL defaults don't hard-limit by default but SQL Server defaults to 100 and allows `OPTION (MAXRECURSION n)`) that will error out or stop a runaway recursive CTE caused by cyclic data (e.g. a corrupted hierarchy where an employee is indirectly their own manager). Beyond relying on that safety net, well-designed recursive CTEs should include an explicit termination condition — such as tracking visited IDs in an array/path column and adding a `WHERE NOT (id = ANY(path))` guard in the recursive member — to detect and stop cycles in the actual data rather than depending solely on an engine-imposed depth cap.

## Key Takeaways

- Subqueries can return scalar values, single rows, or tables.
- Correlated subqueries reference outer query — execute once per row.
- CTEs (`WITH` clause) improve readability and allow reuse.
- Recursive CTEs have base case + recursive case for hierarchies.
- `EXISTS` stops at first match (efficient); `IN` loads full result.
- `NOT IN` with NULLs can give unexpected results — use `NOT EXISTS`.
- Many subqueries can be rewritten as JOINs and vice versa.
- Use JOIN when you need columns from multiple tables.
- Use CTE + window functions for "top N per group" problems.
- Modern optimizers often produce same plan for equivalent queries.

---

# Window Functions Deep Dive

*SQL Fundamentals*

Master powerful analytics with `OVER`, `PARTITION BY`, and frame specifications.

## The Big Picture

Window functions perform calculations across a set of rows that are related to the current row. Unlike `GROUP BY` which collapses rows, window functions keep all rows while adding computed values. They are essential for analytics, rankings, running totals, and comparing values across rows.

**Why This Matters in Interviews**

- "What is a window function and how is it different from GROUP BY?"
- "Explain ROW_NUMBER vs RANK vs DENSE_RANK"
- "How do you get the top N records per group?"
- "What is the difference between ROWS and RANGE?"

## Window Function Syntax

```
function_name(arguments) OVER (
    [PARTITION BY partition_expression]
    [ORDER BY sort_expression]
    [frame_clause]
)
```

- **OVER:** Required keyword that defines this as a window function.
- **PARTITION BY:** Divides rows into groups (like GROUP BY but keeps rows).
- **ORDER BY:** Defines the order within each partition.
- **Frame Clause:** Defines which rows to include in the calculation.

## Types of Window Functions

- **Ranking Functions** — `ROW_NUMBER`, `RANK`, `DENSE_RANK`, `NTILE`: assign positions to rows based on order.
- **Value Functions** — `LEAD`, `LAG`, `FIRST_VALUE`, `LAST_VALUE`, `NTH_VALUE`: access values from other rows.
- **Aggregate Functions** — `SUM`, `AVG`, `COUNT`, `MIN`, `MAX`: used with `OVER` for running totals and moving averages.

```sql
SELECT name, department, salary,
       ROW_NUMBER() OVER (
           PARTITION BY department
           ORDER BY salary DESC
       ) AS row_num
FROM employees;
```

## ROW_NUMBER vs RANK vs DENSE_RANK

These three ranking functions handle ties differently. Understanding the difference is crucial:

| Employee | Salary | ROW_NUMBER | RANK | DENSE_RANK |
|---|---|---|---|---|
| Alice | $90,000 | 1 | 1 | 1 |
| Bob | $80,000 | 2 | 2 | 2 |
| Charlie | $80,000 | 3 | 2 | 2 |
| Diana | $70,000 | 4 | 4 | 3 |

**Key Differences**

- **ROW_NUMBER:** Always unique, arbitrary for ties (1, 2, 3, 4).
- **RANK:** Same rank for ties, skips numbers (1, 2, 2, 4).
- **DENSE_RANK:** Same rank for ties, no gaps (1, 2, 2, 3).

```sql
-- All three ranking functions in one query
SELECT
    name,
    salary,
    ROW_NUMBER() OVER (ORDER BY salary DESC) AS row_num,
    RANK() OVER (ORDER BY salary DESC) AS rank,
    DENSE_RANK() OVER (ORDER BY salary DESC) AS dense_rank
FROM employees;
```

## Top N Per Group Pattern

One of the most common interview questions: "Get the top 3 highest paid employees per department."

```sql
-- Top 3 employees per department by salary
WITH ranked AS (
    SELECT
        name,
        department,
        salary,
        ROW_NUMBER() OVER (
            PARTITION BY department
            ORDER BY salary DESC
        ) AS rn
    FROM employees
)
SELECT name, department, salary
FROM ranked
WHERE rn <= 3;
```

**ROW_NUMBER vs RANK for Top N**

Use `ROW_NUMBER` when you want exactly N rows per group, even with ties. Use `RANK` or `DENSE_RANK` when you want to include all tied values (may return more than N rows).

## LAG and LEAD Functions

Access data from previous (`LAG`) or subsequent (`LEAD`) rows without self-joins:

```sql
-- Compare each sale to previous sale
SELECT
    sale_date,
    amount,
    LAG(amount) OVER (ORDER BY sale_date) AS prev_amount,
    amount - LAG(amount) OVER (ORDER BY sale_date) AS change
FROM sales;

-- With default value for first row
SELECT
    sale_date,
    amount,
    LAG(amount, 1, 0) OVER (ORDER BY sale_date) AS prev_amount
FROM sales;

-- Look ahead with LEAD
SELECT
    employee_name,
    hire_date,
    LEAD(employee_name) OVER (ORDER BY hire_date) AS next_hire
FROM employees;
```

**LAG/LEAD Syntax**

```
LAG(column, offset, default) OVER (ORDER BY ...)
LEAD(column, offset, default) OVER (ORDER BY ...)
```

- **offset:** Number of rows back (`LAG`) or forward (`LEAD`). Default is 1.
- **default:** Value to return when there is no row. Default is NULL.

## Running Totals and Moving Averages

Aggregate functions with `OVER` can calculate cumulative and moving computations:

```sql
-- Running total of sales
SELECT
    sale_date,
    amount,
    SUM(amount) OVER (
        ORDER BY sale_date
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS running_total
FROM sales;

-- 3-day moving average
SELECT
    sale_date,
    amount,
    AVG(amount) OVER (
        ORDER BY sale_date
        ROWS BETWEEN 2 PRECEDING AND CURRENT ROW
    ) AS moving_avg_3day
FROM sales;

-- Running total per department
SELECT
    department,
    sale_date,
    amount,
    SUM(amount) OVER (
        PARTITION BY department
        ORDER BY sale_date
    ) AS dept_running_total
FROM sales;
```

## Frame Specification: ROWS vs RANGE

The frame clause defines which rows are included in the window calculation:

```
ROWS | RANGE BETWEEN start AND end

Where start/end can be:
  UNBOUNDED PRECEDING  -- From the first row of partition
  n PRECEDING          -- n rows before current
  CURRENT ROW          -- Current row
  n FOLLOWING          -- n rows after current
  UNBOUNDED FOLLOWING  -- To the last row of partition
```

| Aspect | ROWS | RANGE |
|---|---|---|
| Definition | Physical row positions | Logical value ranges |
| With Ties | Processes rows individually | Includes all rows with same ORDER BY value |
| Performance | Generally faster | May be slower |
| Use Case | "Last 3 rows" | "All rows with same date" |

```sql
-- ROWS: Exactly 3 physical rows
SUM(amount) OVER (
    ORDER BY sale_date
    ROWS BETWEEN 2 PRECEDING AND CURRENT ROW
)

-- RANGE: All rows with same date value as current
SUM(amount) OVER (
    ORDER BY sale_date
    RANGE BETWEEN CURRENT ROW AND CURRENT ROW
)
```

**Default Frame**

When you specify `ORDER BY` without a frame clause, the default is:

```
RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
```

This is why `SUM` with just `ORDER BY` gives a running total by default.

## Window Function vs GROUP BY

**GROUP BY (Aggregation)** — Collapses rows into groups. You lose individual row details.

```sql
SELECT
    department,
    AVG(salary) AS avg_salary
FROM employees
GROUP BY department;

-- Result: One row per department
-- Engineering | 85000
-- Sales      | 75000
```

**Window Function** — Keeps all rows while adding computed columns.

```sql
SELECT
    name,
    department,
    salary,
    AVG(salary) OVER (
        PARTITION BY department
    ) AS dept_avg
FROM employees;

-- Result: All rows preserved
-- Alice | Engineering | 90000 | 85000
-- Bob   | Engineering | 80000 | 85000
```

**When to Use Each**

- **GROUP BY:** When you want summary statistics only (report totals, averages per group).
- **Window Function:** When you need individual rows WITH aggregate context (employee salary vs department average).

## Practical Examples

**Calculate Percentage of Total**

```sql
SELECT
    product_name,
    sales,
    ROUND(
        100.0 * sales / SUM(sales) OVER (),
        2
    ) AS pct_of_total
FROM products;
```

**Find Gaps in Sequences**

```sql
SELECT
    id,
    LEAD(id) OVER (ORDER BY id) AS next_id,
    LEAD(id) OVER (ORDER BY id) - id AS gap
FROM orders
WHERE LEAD(id) OVER (ORDER BY id) - id > 1;
```

**Year-over-Year Comparison**

```sql
SELECT
    year,
    revenue,
    LAG(revenue) OVER (ORDER BY year) AS prev_year,
    ROUND(
        100.0 * (revenue - LAG(revenue) OVER (ORDER BY year))
        / LAG(revenue) OVER (ORDER BY year),
        2
    ) AS yoy_growth_pct
FROM annual_revenue;
```

**Assign Row to Buckets (Quartiles)**

```sql
SELECT
    name,
    salary,
    NTILE(4) OVER (ORDER BY salary) AS salary_quartile
FROM employees;
```

## Top 10 Interview Questions

Master these frequently asked questions about window functions:

**1. What is a window function and how is it different from GROUP BY?**

A window function performs a calculation across a set of related rows (the "window," defined by `OVER`, optionally with `PARTITION BY`/`ORDER BY`) but returns a value for every individual row rather than collapsing rows into groups. `GROUP BY` reduces N rows into one row per group with aggregated values, losing per-row detail; a window function keeps all N rows and simply adds a computed column (e.g. each employee's row alongside their department's average salary).

**2. Explain the difference between ROW_NUMBER, RANK, and DENSE_RANK.**

All three assign a position to each row based on an `ORDER BY`, but they differ on ties. `ROW_NUMBER` always gives a unique, sequential number even for tied values (1, 2, 3, 4 — arbitrary which tied row gets which number). `RANK` gives tied rows the same rank but then skips subsequent numbers accordingly (1, 2, 2, 4). `DENSE_RANK` gives tied rows the same rank but doesn't skip numbers afterward (1, 2, 2, 3).

**3. How do you get the top N records per group?**

Wrap the table in a CTE that computes `ROW_NUMBER() OVER (PARTITION BY group_column ORDER BY sort_column DESC)`, then filter the CTE's result with `WHERE rn <= N` in the outer query. Use `ROW_NUMBER` for exactly N rows per group even with ties, or `RANK`/`DENSE_RANK` if you want all tied rows included (which may return more than N rows for a group).

**4. What are LEAD and LAG functions used for?**

They let you access a value from another row in the result set — relative to the current row, based on the `ORDER BY` — without writing a self-join. `LAG` looks backward (the previous row's value, useful for period-over-period comparisons like "change from yesterday"), and `LEAD` looks forward (the next row's value, useful for finding gaps or "what happens next"). Both accept an optional offset (how many rows back/forward, default 1) and a default value to use when there's no such row (e.g. the very first row has no `LAG`).

**5. How do you calculate a running total?**

Use an aggregate function like `SUM` with `OVER (ORDER BY sort_column)` — when `ORDER BY` is present without an explicit frame, most databases default the frame to `RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW`, which naturally produces a running/cumulative total up through the current row. You can make this explicit and ROWS-based with `ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW`, and combine with `PARTITION BY` to get a running total that resets per group (e.g. per department).

**6. What is the difference between ROWS and RANGE frame specifications?**

`ROWS` defines the frame by physical row positions — e.g. "the 2 rows immediately before this one, plus this one" — regardless of whether their ORDER BY values are equal. `RANGE` defines the frame by logical value ranges relative to the current row's ORDER BY value — e.g. "all rows with the same date as the current row" get included together, even if that's more than a fixed number of physical rows. `ROWS` is generally faster and more predictable; `RANGE` is useful when ties on the ORDER BY column should be treated as a single logical group.

**7. Can you use window functions in a WHERE clause?**

No — window functions are evaluated after the `WHERE` clause (and after `GROUP BY`/`HAVING`) in SQL's logical processing order, so their results aren't yet available when `WHERE` is applied. To filter on a window function's result, you must compute it in a subquery or CTE first, then filter in an outer query's `WHERE` clause against that computed column (this is exactly the pattern used for "top N per group").

**8. What is NTILE and when would you use it?**

`NTILE(n)` divides the ordered rows in a partition into `n` roughly equal-sized buckets and returns each row's bucket number (1 through n). It's commonly used for percentile/quantile analysis — e.g. `NTILE(4)` to split employees into salary quartiles, or `NTILE(100)` for percentile ranking — letting you bucket continuous data into discrete groups for reporting or further analysis.

**9. How do window functions affect query performance?**

Window functions typically require the database to sort (and/or partition) the data according to the `PARTITION BY`/`ORDER BY` clause, which has a cost (often O(n log n) for the sort) — and using multiple different `PARTITION BY`/`ORDER BY` combinations in one query may force multiple separate sort passes. That said, they're usually far more efficient than the equivalent hand-written correlated subqueries or self-joins they replace, since the database can compute the window calculation in a single pass per sort rather than re-scanning the table once per outer row.

**10. How do you calculate percentage of total using window functions?**

Divide each row's value by the sum of that value across the whole result set (or partition), computed with an empty or partitioned `OVER`: `100.0 * sales / SUM(sales) OVER ()` for percentage of grand total, or `100.0 * sales / SUM(sales) OVER (PARTITION BY category)` for percentage within each category. The window function recomputes the denominator (the total) for every row without collapsing the result, so each row shows both its own value and its share of the relevant total side by side.

## Key Takeaways

- Window functions calculate across rows without collapsing them (unlike GROUP BY).
- `OVER` clause is required — defines the window for calculation.
- `PARTITION BY` divides data into groups (like GROUP BY but keeps rows).
- `ROW_NUMBER`: unique sequential; `RANK`: gaps for ties; `DENSE_RANK`: no gaps.
- `LAG` looks back, `LEAD` looks forward in the ordered result.
- Running totals use `SUM` with `ORDER BY` and appropriate frame.
- `ROWS` counts physical rows, `RANGE` uses logical value ranges.
- Default frame with `ORDER BY` is `RANGE UNBOUNDED PRECEDING` to `CURRENT ROW`.
- Top N per group pattern: `ROW_NUMBER` + `PARTITION BY` in a CTE, then filter.
- Window functions cannot be used in `WHERE` clause — use CTE or subquery.

---

# Query Optimization Techniques

*SQL Fundamentals*

Learn to identify and fix performance bottlenecks in SQL queries.

## The Big Picture

Query optimization is the art of making database queries run faster while returning the same results. A poorly written query can take seconds or minutes; an optimized version might take milliseconds. Understanding optimization is crucial for building scalable applications and is heavily tested in interviews.

**Why This Matters in Interviews**

- "How do you identify slow queries?"
- "What is the N+1 problem and how do you solve it?"
- "How do you read an EXPLAIN plan?"
- "What makes a query slow and how would you optimize it?"

## Key Optimization Areas

- **Indexing** — Create indexes on columns used in WHERE, JOIN, and ORDER BY clauses.
- **Query Rewriting** — Restructure queries to use indexes effectively and reduce work.
- **Schema Design** — Normalize or denormalize based on read/write patterns.
- **Batching** — Combine multiple queries into fewer round trips.
- **Caching** — Cache query results to avoid repeated database hits.
- **EXPLAIN Analysis** — Use execution plans to understand query behavior.

## The N+1 Query Problem

The N+1 problem is one of the most common performance issues. It occurs when code executes 1 query to fetch N records, then N additional queries to fetch related data for each record. This is extremely common with ORMs like Hibernate, ActiveRecord, and Entity Framework.

**N+1 Problem (Slow)** — 1 query for orders + 100 queries for customers = 101 queries

```sql
-- Query 1: Get all orders
SELECT * FROM orders;

-- Then for EACH order (N times):
SELECT * FROM customers
WHERE id = 1; -- order 1
SELECT * FROM customers
WHERE id = 2; -- order 2
-- ... repeated 100 times!
```

**JOIN Solution (Fast)** — 1 single query fetches everything

```sql
-- Single query with JOIN
SELECT
    o.*,
    c.name AS customer_name,
    c.email AS customer_email
FROM orders o
JOIN customers c
    ON o.customer_id = c.id;

-- All data in ONE round trip!
```

**Alternative Solutions**

```sql
-- Solution 2: Batch with IN clause (when JOIN not ideal)
SELECT * FROM orders;
-- Collect all customer_ids: [1, 2, 3, 4, 5...]
SELECT * FROM customers
WHERE id IN (1, 2, 3, 4, 5, ...);
-- Only 2 queries total!

-- ORM Solutions:
-- JPA/Hibernate: @BatchSize, JOIN FETCH, EntityGraph
-- ActiveRecord: includes(:customer)
-- Entity Framework: Include(o => o.Customer)
```

## Reading EXPLAIN Plans

`EXPLAIN` shows how the database will execute a query. Understanding it is essential for optimization. Key things to look for: table scans vs index usage, join methods, and estimated costs.

```sql
-- Basic EXPLAIN
EXPLAIN SELECT * FROM employees WHERE dept_id = 5;

-- EXPLAIN with more details (PostgreSQL)
EXPLAIN ANALYZE SELECT * FROM employees WHERE dept_id = 5;

-- MySQL format
EXPLAIN FORMAT=JSON SELECT * FROM employees WHERE dept_id = 5;
```

**What to Look For**

| Indicator | Good | Bad |
|---|---|---|
| Access Type | const, eq_ref, ref, range | ALL (full table scan) |
| Key | Index name shown | NULL (no index used) |
| Rows | Small number | Large number (scanning many rows) |
| Extra | Using index | Using filesort, Using temporary |

**Example EXPLAIN Output**

Optimized query: `Index Scan using idx_employees_dept on employees` — cost 0.15..8.20, filter `dept_id = 5`, rows: 25.

Unoptimized query: `Seq Scan on employees` (full table scan) — cost 0.00..1520.00, filter `dept_id = 5`, rows: 50000 scanned, 25 returned.

## Common Query Anti-patterns

1. **SELECT \* (Selecting All Columns)** — Fetches unnecessary data, prevents covering index usage, increases network traffic.
2. **Functions on Indexed Columns** — `WHERE YEAR(created_at) = 2024` prevents index usage. Use range instead.
3. **Implicit Type Conversion** — `WHERE varchar_col = 123` forces conversion on every row, preventing index use.
4. **Leading Wildcard in LIKE** — `WHERE name LIKE '%smith'` cannot use index. Only trailing wildcard can.
5. **OR Conditions on Different Columns** — `WHERE a = 1 OR b = 2` often prevents index usage. Consider UNION instead.

**Anti-pattern Fixes**

```sql
-- BAD: Function on indexed column
SELECT * FROM orders
WHERE YEAR(created_at) = 2024;

-- GOOD: Use range comparison
SELECT * FROM orders
WHERE created_at >= '2024-01-01'
  AND created_at < '2025-01-01';

-- BAD: Leading wildcard
SELECT * FROM users WHERE email LIKE '%@gmail.com';

-- GOOD: Consider full-text search or reversed index
-- Or store email_domain separately

-- BAD: OR on different columns
SELECT * FROM products
WHERE category_id = 5 OR brand_id = 10;

-- GOOD: Use UNION
SELECT * FROM products WHERE category_id = 5
UNION
SELECT * FROM products WHERE brand_id = 10;
```

## Index Optimization Tips

**When to Create Indexes**

- Columns frequently used in WHERE clauses.
- Columns used in JOIN conditions.
- Columns used in ORDER BY (to avoid sorting).
- Columns with high selectivity (many unique values).

**When NOT to Create Indexes**

- Small tables (full scan is fast enough).
- Columns with low selectivity (e.g., boolean, status with few values).
- Heavily updated tables (indexes slow down writes).
- Columns rarely used in queries.

**Composite Index Column Order**

```sql
-- Index on (a, b, c) can be used for:
WHERE a = 1                      -- Yes (leftmost)
WHERE a = 1 AND b = 2            -- Yes
WHERE a = 1 AND b = 2 AND c = 3  -- Yes (all columns)
WHERE b = 2                      -- No (skipped a)
WHERE a = 1 AND c = 3            -- Partial (only a used)
WHERE c = 3                      -- No

-- Rule: Index is used left-to-right until a gap
```

**Covering Index**

A covering index includes all columns needed by a query, so the database can answer entirely from the index without reading the table. This is the fastest possible access.

```sql
-- Create covering index
CREATE INDEX idx_covering
ON orders (customer_id, status)
INCLUDE (total, created_at);

-- This query uses only the index
SELECT status, total, created_at
FROM orders
WHERE customer_id = 123;
```

## Efficient Pagination

OFFSET-based pagination becomes slow for large offsets because the database must scan and discard rows:

**OFFSET Pagination (Slow)**

```sql
-- Page 1000 (slow!)
SELECT * FROM products
ORDER BY id
LIMIT 20 OFFSET 19980;
-- Must scan 20,000 rows!
```

**Keyset Pagination (Fast)**

```sql
-- Use last seen ID
SELECT * FROM products
WHERE id > 19980  -- last_id
ORDER BY id
LIMIT 20;
-- Index seek, very fast!
```

**Keyset Pagination Limitations**

- Cannot jump to arbitrary page (no "go to page 500").
- Requires stable, unique sort key.
- More complex with multi-column sorts.
- Best for infinite scroll or "load more" patterns.

## Top 10 Interview Questions

Master these frequently asked questions about query optimization:

**1. What is the N+1 query problem and how do you solve it?**

It's when code runs 1 query to fetch a list of N records, then issues N additional queries to fetch related data for each record individually (common with ORM lazy loading), resulting in N+1 total round trips instead of 1 or 2. It's solved by fetching the related data in bulk: either a single JOIN that pulls everything at once, or a batched `WHERE id IN (...)` query collecting all the related IDs first — ORMs expose this via eager-loading features (`JOIN FETCH` in JPA/Hibernate, `includes` in ActiveRecord, `Include()` in Entity Framework).

**2. How do you identify slow queries?**

Enable and review the database's slow query log (e.g. MySQL's slow query log, PostgreSQL's `log_min_duration_statement`), which records queries exceeding a configured duration threshold. Beyond logs, use `EXPLAIN ANALYZE` on suspect queries to see actual execution time and row counts, check database-level monitoring/APM tooling for query latency percentiles, and look at usage statistics views (e.g. `pg_stat_statements` in PostgreSQL) that rank queries by total or average execution time.

**3. How do you read an EXPLAIN plan?**

Read it looking for: the access method per table (an index seek/scan is good; a full "Seq Scan"/"ALL" on a large table is a red flag), whether a `key`/index is actually being used, the estimated vs. actual row counts (`EXPLAIN ANALYZE` shows both — a big mismatch suggests stale statistics), the join algorithm chosen, and warning signs in the "Extra" column like "Using filesort" or "Using temporary" (MySQL) that indicate expensive sort/temp-table operations. Start from the most expensive/outermost operation and work inward to find the bottleneck.

**4. Why does putting a function on an indexed column prevent index usage?**

An index stores the raw column values in sorted order; wrapping the column in a function (`WHERE YEAR(created_at) = 2024`) means the database would need to apply that function to every stored value before it could compare — which defeats the purpose of a sorted structure the optimizer could otherwise binary-search. Since the index wasn't built on the function's output, the optimizer typically falls back to a full table scan. The fix is to rewrite the condition as a plain range comparison on the raw column (`created_at >= '2024-01-01' AND created_at < '2025-01-01'`), or build a functional/expression index on that exact function if the database supports it.

**5. What is a covering index?**

An index that includes every column a particular query needs — either as indexed key columns or as additional included columns — so the query can be fully satisfied by reading the index alone, without a separate lookup back into the base table ("index-only scan"). This avoids the extra I/O cost of jumping from the index to the table to fetch other columns, making it the fastest possible way to answer a specific, known query pattern.

**6. Why is OFFSET pagination slow for large offsets?**

`OFFSET n` tells the database to still compute and then discard the first `n` matching rows before returning the next page — so page 1000 with `OFFSET 19980` requires scanning and throwing away nearly 20,000 rows every single time, even though you only want 20 of them. The cost grows linearly with the offset. Keyset pagination avoids this by filtering directly on the last-seen sort key (`WHERE id > last_id ORDER BY id LIMIT 20`), letting an index seek jump straight to the right starting point regardless of how deep into the result set you are.

**7. How does composite index column order affect query performance?**

Per the leftmost prefix rule, a composite index on `(a, b, c)` can be used efficiently for a query filtering on `a`, or `a` and `b`, or all three — but not for a query that filters on `b` or `c` alone, since the index is physically sorted by `a` first. A query like `WHERE a = 1 AND c = 3` can only use the index to narrow down by `a` (then must scan/filter the rest for `c`), getting partial benefit. Choosing the right order (equality columns before range columns, higher-selectivity columns first) determines which of your actual query patterns the index can actually accelerate.

**8. When should you NOT create an index?**

Skip indexing when the table is small enough that a full scan is already fast, the column has low selectivity (few distinct values, like a boolean), the table has heavy write (INSERT/UPDATE/DELETE) traffic relative to reads (since every index adds write overhead and must be kept in sync), or the column is rarely referenced in actual queries — in all these cases the index's maintenance cost outweighs any read benefit.

**9. What is the difference between EXISTS and IN for performance?**

`IN` evaluates its subquery fully and caches the complete result set before checking membership, which works well when that result set is small. `EXISTS` is a short-circuiting existence check — it stops scanning as soon as it finds one matching row — which tends to be more efficient when the outer table driving the check is relatively small or when the subquery's result set would otherwise be very large. In practice, most modern optimizers rewrite one into the other (semi-join) when it's advantageous, so the difference matters most on older/simpler optimizers or unusual data distributions.

**10. How do you optimize a query that uses OR conditions?**

An `OR` across two different, separately-indexed columns (`WHERE category_id = 5 OR brand_id = 10`) often prevents the optimizer from efficiently using either index, since it would need to combine two index scans and de-duplicate — many optimizers instead fall back to a full scan. Rewriting it as a `UNION` of two separate single-condition queries (each of which can use its own index cleanly) is often faster, with `UNION` deduplicating any rows matching both conditions, or `UNION ALL` if duplicates are acceptable/impossible and you want to skip the dedup cost.

## Key Takeaways

- N+1 problem: 1 + N queries instead of 1 JOIN — extremely common with ORMs.
- Use `EXPLAIN` to understand query execution plans.
- Look for full table scans (type: ALL) and missing indexes.
- Avoid functions on indexed columns in WHERE clause.
- Composite index column order matters — leftmost prefix rule.
- `SELECT` only needed columns, not `SELECT *`.
- Leading wildcards (`LIKE '%text'`) prevent index usage.
- Use keyset pagination instead of OFFSET for large datasets.
- Covering indexes are fastest — include all needed columns.
- Profile before optimizing — measure, don't guess.

---

# Aggregations and GROUP BY

*SQL Fundamentals*

Master data summarization with aggregate functions, grouping, and filtering.

## The Big Picture

Aggregate functions compute a single result from a set of input values. Combined with `GROUP BY`, they allow you to summarize data by categories. Understanding aggregation is essential for reporting, analytics, and answering business questions with SQL.

**Why This Matters in Interviews**

- "What is the difference between WHERE and HAVING?"
- "How does NULL affect aggregate functions?"
- "Why can you not use non-aggregated columns without GROUP BY?"
- "What is the execution order of SELECT, WHERE, GROUP BY, HAVING?"

## Aggregate Functions

Aggregate functions operate on a set of rows and return a single value: `COUNT`, `SUM`, `AVG`, `MIN`, `MAX`, and others like `STRING_AGG`, `ARRAY_AGG`.

```sql
-- Basic aggregate functions
SELECT
    COUNT(*) AS total_rows,
    COUNT(email) AS has_email,
    COUNT(DISTINCT department) AS unique_depts,
    SUM(salary) AS total_payroll,
    AVG(salary) AS avg_salary,
    MIN(hire_date) AS earliest_hire,
    MAX(salary) AS highest_salary
FROM employees;
```

**COUNT(\*) vs COUNT(column)**

- `COUNT(*)` counts all rows, including those with NULL values.
- `COUNT(column)` counts only rows where that column is NOT NULL.
- `COUNT(DISTINCT column)` counts unique non-NULL values.

## GROUP BY Clause

`GROUP BY` divides rows into groups based on column values. Aggregate functions then operate on each group separately:

```sql
-- Sales by department
SELECT
    department,
    COUNT(*) AS employee_count,
    SUM(salary) AS dept_payroll,
    AVG(salary) AS avg_salary
FROM employees
GROUP BY department;

-- Multiple grouping columns
SELECT
    department,
    job_title,
    COUNT(*) AS count,
    AVG(salary) AS avg_salary
FROM employees
GROUP BY department, job_title
ORDER BY department, avg_salary DESC;
```

**Common Error: Non-Aggregated Columns**

Every column in `SELECT` must either be in `GROUP BY` or wrapped in an aggregate function:

```sql
-- ERROR: name is not aggregated or grouped
SELECT department, name, AVG(salary)
FROM employees
GROUP BY department;

-- CORRECT: Include name in GROUP BY
SELECT department, name, AVG(salary)
FROM employees
GROUP BY department, name;
```

## WHERE vs HAVING

Both filter rows, but they operate at different stages:

**WHERE** — Filters individual rows BEFORE grouping. Cannot use aggregate functions.

```sql
-- Filter rows before grouping
SELECT department, AVG(salary)
FROM employees
WHERE status = 'active'
GROUP BY department;
```

**HAVING** — Filters groups AFTER aggregation. Can use aggregate functions.

```sql
-- Filter groups after aggregation
SELECT department, AVG(salary)
FROM employees
GROUP BY department
HAVING AVG(salary) > 50000;
```

**Using Both Together**

```sql
-- Find departments with average salary > 60K among active employees
SELECT
    department,
    COUNT(*) AS active_count,
    AVG(salary) AS avg_salary
FROM employees
WHERE status = 'active'        -- Filter rows first
GROUP BY department
HAVING AVG(salary) > 60000    -- Then filter groups
   AND COUNT(*) >= 5           -- Multiple HAVING conditions
ORDER BY avg_salary DESC;
```

| Aspect | WHERE | HAVING |
|---|---|---|
| When Applied | Before GROUP BY | After GROUP BY |
| Operates On | Individual rows | Grouped rows |
| Aggregate Functions | Cannot use | Can use |
| Performance | Filters early (faster) | Filters late (after aggregation) |

## Query Execution Order

Understanding execution order is crucial for writing correct queries:

`FROM` → `WHERE` → `GROUP BY` → `HAVING` → `SELECT` → `ORDER BY` → `LIMIT`

**Key Implications**

- `WHERE` cannot reference `SELECT` aliases (`WHERE` runs before `SELECT`).
- `HAVING` can use aggregates (`GROUP BY` creates them first).
- `ORDER BY` can reference `SELECT` aliases (`ORDER BY` runs after `SELECT`).
- Column aliases from `SELECT` work in `ORDER BY` but not `WHERE` or `HAVING`.

## NULL Handling in Aggregates

Understanding how NULL values affect aggregates is critical:

```
-- Sample data: [100, 200, NULL, 300]

COUNT(*)        -- 4 (counts all rows)
COUNT(value)    -- 3 (ignores NULL)
SUM(value)      -- 600 (ignores NULL)
AVG(value)      -- 200 (600/3, ignores NULL)
MIN(value)      -- 100 (ignores NULL)
MAX(value)      -- 300 (ignores NULL)
```

**NULL Gotchas**

- All aggregates except `COUNT(*)` ignore NULL values.
- `AVG` divides by count of non-NULL values, not total rows.
- If all values are NULL, most aggregates return NULL (not 0).
- `GROUP BY` treats all NULLs as equal (groups them together).

**Handling NULLs Explicitly**

```sql
-- Replace NULL with 0 for calculation
SELECT AVG(COALESCE(bonus, 0)) AS avg_bonus
FROM employees;

-- Return 0 instead of NULL when no rows
SELECT COALESCE(SUM(amount), 0) AS total
FROM orders
WHERE customer_id = 999;  -- no matching rows

-- Count NULLs specifically
SELECT
    COUNT(*) - COUNT(email) AS null_email_count
FROM customers;
```

## Advanced Grouping

**ROLLUP — Hierarchical Subtotals**

```sql
-- Subtotals by region, then grand total
SELECT
    region,
    department,
    SUM(sales) AS total_sales
FROM sales_data
GROUP BY ROLLUP(region, department);

-- Result includes:
-- East    | Sales      | 10000  (detail)
-- East    | Marketing  | 8000   (detail)
-- East    | NULL       | 18000  (region subtotal)
-- West    | Sales      | 12000  (detail)
-- West    | NULL       | 12000  (region subtotal)
-- NULL    | NULL       | 30000  (grand total)
```

**CUBE — All Combinations**

```sql
-- All possible grouping combinations
SELECT
    region,
    department,
    SUM(sales) AS total_sales
FROM sales_data
GROUP BY CUBE(region, department);

-- Includes: by region+dept, by region, by dept, grand total
```

**GROUPING SETS — Custom Combinations**

```sql
-- Specific grouping combinations only
SELECT
    region,
    department,
    SUM(sales) AS total_sales
FROM sales_data
GROUP BY GROUPING SETS(
    (region, department),  -- detail level
    (region),              -- region subtotals
    ()                     -- grand total
);
```

## Common Patterns

**Top N Per Group**

```sql
-- Top 3 products by sales in each category
WITH ranked AS (
    SELECT
        category,
        product_name,
        SUM(sales) AS total_sales,
        ROW_NUMBER() OVER (
            PARTITION BY category
            ORDER BY SUM(sales) DESC
        ) AS rn
    FROM sales
    GROUP BY category, product_name
)
SELECT * FROM ranked WHERE rn <= 3;
```

**Conditional Aggregation**

```sql
-- Count by status in single query (pivot-style)
SELECT
    department,
    COUNT(*) AS total,
    COUNT(CASE WHEN status = 'active' THEN 1 END) AS active,
    COUNT(CASE WHEN status = 'inactive' THEN 1 END) AS inactive,
    SUM(CASE WHEN status = 'active' THEN salary ELSE 0 END) AS active_payroll
FROM employees
GROUP BY department;

-- PostgreSQL FILTER syntax (cleaner)
SELECT
    department,
    COUNT(*) AS total,
    COUNT(*) FILTER (WHERE status = 'active') AS active
FROM employees
GROUP BY department;
```

**Running Totals with GROUP BY**

```sql
-- Daily sales with running total
SELECT
    sale_date,
    SUM(amount) AS daily_total,
    SUM(SUM(amount)) OVER (ORDER BY sale_date) AS running_total
FROM sales
GROUP BY sale_date
ORDER BY sale_date;
```

## Top 10 Interview Questions

Master these frequently asked questions about aggregations:

**1. What is the difference between WHERE and HAVING?**

`WHERE` filters individual rows before any grouping happens, and cannot reference aggregate functions since they haven't been computed yet. `HAVING` filters groups after `GROUP BY` has aggregated the rows, and is specifically designed to use aggregate functions (e.g. `HAVING AVG(salary) > 50000`) to decide which groups survive. You can use both together: `WHERE` to cheaply exclude rows up front, `HAVING` to filter on the aggregated result.

**2. How does NULL affect aggregate functions?**

Every aggregate function except `COUNT(*)` silently ignores NULL values in its input — `SUM`, `AVG`, `MIN`, `MAX`, and `COUNT(column)` all skip NULLs. This means `AVG` divides by the count of non-NULL values, not the total row count, and if every value in the group is NULL, most aggregates return NULL rather than 0. `GROUP BY` treats all NULLs as a single equal group rather than excluding them.

**3. Why can you not use non-aggregated columns in SELECT without GROUP BY?**

Because once you aggregate, each output row represents a whole group of input rows collapsed into one — any column not wrapped in an aggregate function and not part of the `GROUP BY` list would have multiple different values within that group, and the database has no defined rule for which one to show. SQL requires every selected column to be either part of the grouping key (one value per group, unambiguous) or explicitly aggregated (reduced to one value per group) to guarantee a deterministic result.

**4. What is the execution order of a SQL query with GROUP BY?**

`FROM` (identify/join tables) → `WHERE` (filter rows) → `GROUP BY` (form groups) → `HAVING` (filter groups) → `SELECT` (compute output columns/aggregates) → `ORDER BY` (sort) → `LIMIT` (truncate). This is why `WHERE` can't use aggregates or `SELECT` aliases (they don't exist yet) while `HAVING` can use aggregates and `ORDER BY` can use `SELECT` aliases.

**5. What is the difference between COUNT(\*), COUNT(column), and COUNT(DISTINCT column)?**

`COUNT(*)` counts every row in the group regardless of NULLs. `COUNT(column)` counts only the rows where that specific column is NOT NULL. `COUNT(DISTINCT column)` counts the number of unique non-NULL values in that column, collapsing duplicates.

**6. How do you calculate percentage of total using GROUP BY?**

Compute each group's aggregate normally, then divide by the grand total — the grand total can come from a subquery, a CTE, or (more elegantly) a window function with an empty `OVER ()` that computes the overall sum alongside the grouped rows: `SELECT category, SUM(sales), 100.0 * SUM(sales) / SUM(SUM(sales)) OVER () AS pct FROM sales GROUP BY category;` — the outer `SUM(...) OVER ()` sums the already-grouped per-category totals into one grand total available on every output row.

**7. What is conditional aggregation?**

It's using a `CASE WHEN` expression (or the cleaner PostgreSQL `FILTER (WHERE ...)` syntax) inside an aggregate function to count or sum only rows matching a specific condition, letting you compute multiple conditional metrics in a single pass/single query instead of running separate queries per condition — e.g. `COUNT(CASE WHEN status = 'active' THEN 1 END) AS active_count` alongside `COUNT(CASE WHEN status = 'inactive' THEN 1 END) AS inactive_count` in the same `SELECT`, producing a pivot-style summary row per group.

**8. What are ROLLUP, CUBE, and GROUPING SETS?**

All three extend `GROUP BY` to produce multiple levels of subtotals in a single query instead of running several separate aggregate queries and `UNION`-ing them. `ROLLUP(a, b)` produces a hierarchy of subtotals: detail rows by `(a, b)`, subtotals by `a` alone, and a grand total. `CUBE(a, b)` produces every possible combination of groupings (by `a,b`, by `a`, by `b`, and overall). `GROUPING SETS` lets you specify exactly which grouping combinations you want, when you don't need the full hierarchy or full cross-product.

**9. How do you find duplicates using GROUP BY?**

Group by the column(s) that should be unique, then filter with `HAVING COUNT(*) > 1`: `SELECT email, COUNT(*) FROM users GROUP BY email HAVING COUNT(*) > 1;` — this returns every value that appears more than once, along with how many times, which is the standard pattern for duplicate detection.

**10. Can you use aggregate functions without GROUP BY?**

Yes — without a `GROUP BY`, the entire result set is treated as a single implicit group, so an aggregate function like `SELECT COUNT(*), AVG(salary) FROM employees;` returns one row summarizing the whole table. The restriction on mixing non-aggregated columns only kicks in if you try to select a plain column alongside the aggregate in that case (e.g. `SELECT name, COUNT(*) FROM employees;` would error, since `name` has no single value across all rows).

## Key Takeaways

- Aggregate functions (`COUNT`, `SUM`, `AVG`, `MIN`, `MAX`) summarize data.
- `COUNT(*)` counts all rows; `COUNT(column)` ignores NULLs.
- `GROUP BY` creates groups; aggregates compute per group.
- Every `SELECT` column must be grouped or aggregated.
- `WHERE` filters rows before grouping; `HAVING` filters groups after.
- `HAVING` can use aggregates; `WHERE` cannot.
- Execution order: `FROM`, `WHERE`, `GROUP BY`, `HAVING`, `SELECT`, `ORDER BY`.
- All aggregates except `COUNT(*)` ignore NULL values.
- `ROLLUP`, `CUBE`, `GROUPING SETS` for advanced subtotals.
- Conditional aggregation with `CASE WHEN` or `FILTER`.

---

# Database Normalization

*SQL Fundamentals*

Organize data to eliminate redundancy and ensure integrity.

## The Big Picture

Normalization is the process of organizing database tables to minimize redundancy and dependency. It involves dividing large tables into smaller, related tables and defining relationships between them. The goal is to isolate data so that additions, deletions, and modifications can be made in one table and propagated through the rest via relationships.

**Why This Matters in Interviews**

- "What are the normal forms and what problem does each solve?"
- "Explain the difference between 2NF and 3NF"
- "When would you denormalize a database?"
- "What are update, insert, and delete anomalies?"

## Why Normalize?

Unnormalized data leads to three types of anomalies:

**Update Anomaly** — Same data exists in multiple rows. Updating one occurrence but not others leads to inconsistency.

**Insert Anomaly** — Cannot insert data without having other unrelated data. Example: cannot add department without an employee.

**Delete Anomaly** — Deleting data unintentionally removes other important data. Example: deleting last employee deletes department info.

**Example: Unnormalized Data**

| order_id | customer_name | customer_email | product | price |
|---|---|---|---|---|
| 1001 | John Smith | john@email.com | Laptop | 999 |
| 1002 | John Smith | john@email.com | Mouse | 29 |
| 1003 | Jane Doe | jane@email.com | Keyboard | 79 |

John's info is stored redundantly across multiple rows.

## The Normal Forms

**1NF — First Normal Form (Atomic Values)**

Must have atomic (indivisible) values in each column, no repeating groups or arrays.

Rule: Eliminate repeating groups; each cell has a single value.

**2NF — Second Normal Form (Full Dependency)**

Must be in 1NF. All non-key columns depend on the entire primary key, not just part of it.

Rule: Remove partial dependencies on composite keys.

**3NF — Third Normal Form (No Transitive Dependency)**

Must be in 2NF. Non-key columns depend only on the primary key, not on other non-key columns.

Rule: Remove transitive dependencies (A → B → C).

**BCNF — Boyce-Codd Normal Form (Strict 3NF)**

Must be in 3NF. Every determinant must be a candidate key. Handles edge cases 3NF misses.

Rule: For X → Y, X must be a superkey.

## First Normal Form (1NF)

A table is in 1NF if:

- Each column contains atomic (indivisible) values.
- Each column contains values of the same type.
- Each row is unique (has a primary key).
- No repeating groups or arrays.

**Violation Example**

```
-- NOT in 1NF: phone_numbers contains multiple values
| student_id | name  | phone_numbers           |
|------------|-------|-------------------------|
| 1          | Alice | 555-1234, 555-5678      |
| 2          | Bob   | 555-9999                |

-- IN 1NF: Split into separate rows or table
| student_id | name  | phone_number |
|------------|-------|--------------|
| 1          | Alice | 555-1234     |
| 1          | Alice | 555-5678     |
| 2          | Bob   | 555-9999     |
```

```sql
-- OR better: Separate phone_numbers table
CREATE TABLE students (student_id INT PRIMARY KEY, name VARCHAR);
CREATE TABLE phone_numbers (
    student_id INT REFERENCES students,
    phone_number VARCHAR
);
```

## Second Normal Form (2NF)

A table is in 2NF if:

- It is in 1NF.
- All non-key columns are fully dependent on the entire primary key.
- No partial dependencies (relevant only for composite primary keys).

**Key Insight**

2NF only matters when you have a composite primary key. If your primary key is a single column, and you are in 1NF, you are automatically in 2NF.

**Violation Example**

```
-- NOT in 2NF: course_name depends only on course_id, not student_id
-- Primary Key: (student_id, course_id)
| student_id | course_id | course_name | grade |
|------------|-----------|-------------|-------|
| 1          | CS101     | Intro CS    | A     |
| 1          | CS102     | Data Struct | B     |
| 2          | CS101     | Intro CS    | B     |  <- course_name repeated
```

```sql
-- IN 2NF: Split into two tables
CREATE TABLE enrollments (
    student_id INT,
    course_id VARCHAR,
    grade CHAR(1),
    PRIMARY KEY (student_id, course_id)
);

CREATE TABLE courses (
    course_id VARCHAR PRIMARY KEY,
    course_name VARCHAR
);
```

## Third Normal Form (3NF)

A table is in 3NF if:

- It is in 2NF.
- No transitive dependencies — non-key columns depend only on the primary key.
- No non-key column depends on another non-key column.

**Violation Example**

```
-- NOT in 3NF: dept_name depends on dept_id, not employee_id
-- employee_id -> dept_id -> dept_name (transitive!)
| employee_id | name  | dept_id | dept_name   |
|-------------|-------|---------|-------------|
| 1           | Alice | D1      | Engineering |
| 2           | Bob   | D1      | Engineering |  <- dept_name repeated
| 3           | Carol | D2      | Sales       |
```

```sql
-- IN 3NF: Split into two tables
CREATE TABLE employees (
    employee_id INT PRIMARY KEY,
    name VARCHAR,
    dept_id VARCHAR REFERENCES departments
);

CREATE TABLE departments (
    dept_id VARCHAR PRIMARY KEY,
    dept_name VARCHAR
);
```

**3NF Summary (Bill Kent)**

"Every non-key attribute must provide a fact about the key, the whole key, and nothing but the key."

- The key: 1NF — attributes depend on a key.
- The whole key: 2NF — no partial dependencies.
- Nothing but the key: 3NF — no transitive dependencies.

## Denormalization

Sometimes we intentionally violate normal forms for performance. This is called denormalization:

| Aspect | Normalized | Denormalized |
|---|---|---|
| Data Redundancy | Minimal | Increased |
| Write Performance | Faster (less data) | Slower (update multiple places) |
| Read Performance | Slower (many JOINs) | Faster (fewer JOINs) |
| Data Integrity | Easier to maintain | Risk of inconsistency |
| Storage | Less space | More space |

**When to Denormalize**

- Read-heavy workloads: When queries vastly outnumber writes.
- Reporting/Analytics: Pre-computed aggregates, materialized views.
- Caching: Store computed values to avoid repeated calculations.
- Performance critical paths: When JOINs are too slow.

```sql
-- Denormalized: Store customer_name in orders for faster reads
CREATE TABLE orders (
    order_id INT PRIMARY KEY,
    customer_id INT REFERENCES customers,
    customer_name VARCHAR,  -- Denormalized!
    total DECIMAL
);

-- Now this query doesn't need a JOIN:
SELECT order_id, customer_name, total
FROM orders
WHERE order_id = 1001;
```

**Denormalization Trade-offs**

When you denormalize, you take on the responsibility of keeping redundant data in sync. Use database triggers, application logic, or eventual consistency patterns to maintain data integrity.

## Top 10 Interview Questions

Master these frequently asked questions about normalization:

**1. What is database normalization and why is it important?**

Normalization is the process of structuring a database's tables and relationships to minimize data redundancy and avoid the update/insert/delete anomalies that redundancy causes. It's important because it keeps data consistent (a fact stored once can only be true in one place), reduces storage waste, and makes the database easier to maintain correctly as it grows — at the cost of needing more JOINs to reassemble related data at query time.

**2. Explain the first three normal forms.**

1NF requires atomic values — no repeating groups or multi-valued columns, each cell holds one value. 2NF requires 1NF plus no partial dependencies — every non-key column must depend on the *entire* primary key, not just part of a composite key. 3NF requires 2NF plus no transitive dependencies — a non-key column can't depend on another non-key column; it must depend directly and only on the primary key.

**3. What is a partial dependency?**

A partial dependency occurs when a non-key column depends on only part of a composite primary key rather than the whole key — e.g. in a table keyed on `(student_id, course_id)`, if `course_name` depends only on `course_id` (and not on `student_id` at all), that's a partial dependency, and it violates 2NF. It's resolved by splitting the table so the partially-dependent column moves to a table keyed on just the column it actually depends on.

**4. What is a transitive dependency?**

A transitive dependency is when a non-key column depends on another non-key column, which in turn depends on the primary key — forming a chain like `employee_id -> dept_id -> dept_name`, where `dept_name` depends on the key only indirectly, through `dept_id`. This violates 3NF and is fixed by moving `dept_name` into its own `departments` table keyed on `dept_id`.

**5. What are update, insert, and delete anomalies?**

An update anomaly happens when the same fact is duplicated across multiple rows and updating it in one place but not others leaves the data inconsistent. An insert anomaly happens when you can't add a piece of data without also having unrelated data available (e.g. can't record a new department until at least one employee is hired into it, if department data only lives inside the employee table). A delete anomaly happens when deleting one row unintentionally destroys other information that happened to be stored alongside it (e.g. deleting the last employee in a department wipes out the department's name/info too). Normalization eliminates all three by storing each fact in exactly one place.

**6. What is the difference between 3NF and BCNF?**

3NF allows an edge case: a non-key attribute can still be a "determinant" (something that functionally determines another attribute) as long as it isn't itself transitively dependent on the key in a prohibited way, if the table has overlapping candidate keys. BCNF is the stricter rule: for every functional dependency `X -> Y` in the table, `X` must be a candidate key (a superkey) — no exceptions. In other words, BCNF requires that every determinant be a key, catching some redundancy cases that 3NF's definition technically permits.

**7. When would you denormalize a database?**

When read performance matters more than write performance or storage efficiency and JOINs across normalized tables have become a measurable bottleneck — common in read-heavy reporting/analytics systems, materialized views/pre-computed aggregates, or caching frequently-joined data (like storing a customer's name directly on an order row to avoid a JOIN on every read). The trade-off is accepting responsibility for keeping the duplicated data in sync, typically via triggers, application logic, or an eventual-consistency process.

**8. How do you identify if a table is not in 3NF?**

Look for any non-key column whose value can be determined entirely by another non-key column rather than directly by the primary key — a telltale sign is a column's value repeating in lockstep with another non-key column across multiple rows (e.g. `dept_name` always matching whatever `dept_id` is, independent of which employee row you're looking at). If updating that one non-key column in one row would require also updating it in other rows to stay consistent, there's a transitive dependency and the table isn't in 3NF.

**9. What is a candidate key?**

A candidate key is any minimal set of one or more columns that can uniquely identify a row in a table — "minimal" meaning you can't drop any column from the set and still have uniqueness. A table can have multiple candidate keys (e.g. both an auto-increment `id` and a `email` column could each uniquely identify a user); one of them is chosen as the primary key, and the rest remain candidate keys (sometimes enforced with `UNIQUE` constraints).

**10. Is it always necessary to normalize to 3NF?**

No — 3NF is a widely used practical balance point for most transactional (OLTP) systems, but it's not a universal requirement. Analytics/reporting (OLAP) systems, data warehouses, and read-heavy caching layers often intentionally use denormalized or star-schema designs for performance, accepting redundancy because reads vastly outnumber writes and JOIN cost matters more than storage or write-consistency overhead. The right level of normalization depends on the actual read/write pattern of the system.

## Key Takeaways

- Normalization eliminates redundancy and prevents anomalies.
- Three anomalies: Update, Insert, Delete — caused by redundant data.
- 1NF: Atomic values, no repeating groups.
- 2NF: No partial dependencies (on part of composite key).
- 3NF: No transitive dependencies (non-key depending on non-key).
- BCNF: Every determinant must be a candidate key.
- "The key, the whole key, nothing but the key" summarizes 1NF-3NF.
- Most production databases aim for 3NF as a balance.
- Denormalization trades integrity for read performance.
- Denormalize only with clear performance requirements.

---

# Constraints and Data Integrity

*SQL Fundamentals*

Enforce business rules and maintain data quality at the database level.

## The Big Picture

Constraints are rules enforced by the database to maintain data integrity. They prevent invalid data from being inserted, updated, or deleted. By defining constraints at the database level, you ensure data quality regardless of which application or user accesses the database.

**Why This Matters in Interviews**

- "What are the different types of constraints in SQL?"
- "Explain the difference between PRIMARY KEY and UNIQUE"
- "What are foreign key actions (CASCADE, SET NULL, etc.)?"
- "When would you use a CHECK constraint vs application validation?"

## Types of Constraints

**PRIMARY KEY** — Uniquely identifies each row. Combines NOT NULL and UNIQUE. Only one per table.

```sql
id INT PRIMARY KEY
```

**FOREIGN KEY** — References primary key in another table. Enforces referential integrity between tables.

```sql
FOREIGN KEY (dept_id) REFERENCES departments(id)
```

**UNIQUE** — Ensures all values in column are different. Allows one NULL (varies by database).

```sql
email VARCHAR(255) UNIQUE
```

**NOT NULL** — Prevents NULL values. Column must always have a value on insert/update.

```sql
name VARCHAR(100) NOT NULL
```

**CHECK** — Validates data against a boolean expression. Enforces domain rules.

```sql
CHECK (age >= 0 AND age <= 150)
```

**DEFAULT** — Provides automatic value when none specified. Often used with timestamps.

```sql
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
```

## PRIMARY KEY

A primary key uniquely identifies each row in a table. It combines two constraints: NOT NULL and UNIQUE.

```sql
-- Single column primary key
CREATE TABLE users (
    id INT PRIMARY KEY,
    name VARCHAR(100)
);

-- Auto-increment primary key (common pattern)
CREATE TABLE users (
    id SERIAL PRIMARY KEY,  -- PostgreSQL
    name VARCHAR(100)
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,  -- MySQL
    name VARCHAR(100)
);

-- Composite primary key
CREATE TABLE order_items (
    order_id INT,
    product_id INT,
    quantity INT,
    PRIMARY KEY (order_id, product_id)
);
```

**PRIMARY KEY vs UNIQUE**

- PRIMARY KEY: Only one per table, cannot be NULL, defines row identity.
- UNIQUE: Multiple per table, allows NULL (usually one), enforces uniqueness.

## FOREIGN KEY

Foreign keys enforce referential integrity by ensuring values in one table correspond to values in another.

```sql
-- Basic foreign key
CREATE TABLE orders (
    id INT PRIMARY KEY,
    customer_id INT,
    total DECIMAL(10,2),
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- With referential actions
CREATE TABLE orders (
    id INT PRIMARY KEY,
    customer_id INT,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
```

**Referential Actions**

| Action | ON DELETE | ON UPDATE |
|---|---|---|
| CASCADE | Delete child rows when parent deleted | Update child FKs when parent PK changes |
| SET NULL | Set FK to NULL when parent deleted | Set FK to NULL when parent PK changes |
| SET DEFAULT | Set FK to default value | Set FK to default value |
| RESTRICT | Prevent delete if children exist | Prevent update if children exist |
| NO ACTION | Same as RESTRICT (checked at end of statement) | Same as RESTRICT |

**Choosing Referential Actions**

- **CASCADE:** When child data is meaningless without parent (order items when order deleted).
- **SET NULL:** When child can exist independently (employee's manager_id when manager leaves).
- **RESTRICT:** When deletion should be explicit (prevent accidental data loss).

## CHECK Constraint

CHECK constraints validate data against a boolean expression before allowing insert or update:

```sql
-- Simple CHECK constraint
CREATE TABLE employees (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    age INT CHECK (age >= 18 AND age <= 120),
    salary DECIMAL CHECK (salary > 0)
);

-- Named CHECK constraint
CREATE TABLE products (
    id INT PRIMARY KEY,
    price DECIMAL(10,2),
    discount DECIMAL(5,2),
    CONSTRAINT chk_price_positive CHECK (price > 0),
    CONSTRAINT chk_discount_range CHECK (discount >= 0 AND discount <= 100)
);

-- CHECK with multiple columns
CREATE TABLE events (
    id INT PRIMARY KEY,
    start_date DATE,
    end_date DATE,
    CHECK (end_date >= start_date)
);

-- CHECK with IN list
CREATE TABLE orders (
    id INT PRIMARY KEY,
    status VARCHAR(20) CHECK (status IN ('pending', 'shipped', 'delivered'))
);
```

**CHECK vs Application Validation**

- CHECK constraints: Enforced at database level, cannot be bypassed, simpler expressions.
- Application validation: More complex logic, better error messages, can be bypassed.
- Best practice: Use both! Database for critical rules, application for UX.

## Managing Constraints

**Adding Constraints to Existing Tables**

```sql
-- Add PRIMARY KEY
ALTER TABLE users ADD PRIMARY KEY (id);

-- Add FOREIGN KEY
ALTER TABLE orders
ADD CONSTRAINT fk_customer
FOREIGN KEY (customer_id) REFERENCES customers(id);

-- Add UNIQUE constraint
ALTER TABLE users ADD CONSTRAINT uq_email UNIQUE (email);

-- Add CHECK constraint
ALTER TABLE products ADD CONSTRAINT chk_price CHECK (price > 0);

-- Add NOT NULL (varies by database)
ALTER TABLE users ALTER COLUMN name SET NOT NULL;  -- PostgreSQL
ALTER TABLE users MODIFY name VARCHAR(100) NOT NULL;  -- MySQL
```

**Removing Constraints**

```sql
-- Drop constraint by name
ALTER TABLE orders DROP CONSTRAINT fk_customer;
ALTER TABLE users DROP CONSTRAINT uq_email;

-- Drop PRIMARY KEY
ALTER TABLE users DROP PRIMARY KEY;  -- MySQL
ALTER TABLE users DROP CONSTRAINT users_pkey;  -- PostgreSQL

-- Temporarily disable constraints (for bulk loads)
ALTER TABLE orders DISABLE TRIGGER ALL;  -- PostgreSQL
SET FOREIGN_KEY_CHECKS = 0;  -- MySQL
```

## Top 10 Interview Questions

Master these frequently asked questions about constraints:

**1. What is the difference between PRIMARY KEY and UNIQUE?**

A PRIMARY KEY uniquely identifies every row and implicitly combines NOT NULL + UNIQUE — a table can have only one. A UNIQUE constraint also enforces distinct values, but a table can have multiple UNIQUE constraints, and most databases allow a UNIQUE column to contain NULL (typically just one NULL, since NULL isn't considered equal to another NULL). In short: PRIMARY KEY defines the row's core identity; UNIQUE is for additional uniqueness rules (like email addresses) that aren't the table's primary identifier.

**2. What are the different referential actions for foreign keys?**

`CASCADE` propagates the delete/update to child rows automatically. `SET NULL` sets the child's foreign key to NULL when the parent is deleted/changed. `SET DEFAULT` sets it to a predefined default value. `RESTRICT` blocks the delete/update entirely if dependent child rows exist. `NO ACTION` behaves like `RESTRICT` in most databases, differing mainly in exactly when the check is enforced (end of statement vs immediately).

**3. When would you use a CHECK constraint vs application validation?**

Use a CHECK constraint for simple, critical data-integrity rules that must hold true no matter which application, script, or user touches the data directly (e.g. `price > 0`), since it can't be bypassed even by direct database access. Use application-level validation for more complex business logic, cross-system checks, and to give users friendlier, more specific error messages — application code can be bypassed by direct DB access, so critical invariants belong in both places, with the database as the non-negotiable safety net.

**4. Can a foreign key reference a non-primary key column?**

Yes — a foreign key must reference a column (or column set) that has a UNIQUE constraint (or is the PRIMARY KEY) in the parent table, since the referenced value must be guaranteed unique for the relationship to be well-defined, but it doesn't strictly have to be that table's primary key as long as uniqueness is enforced on it.

**5. What happens if you try to insert NULL into a PRIMARY KEY column?**

It fails with a constraint violation error — PRIMARY KEY implicitly includes a NOT NULL constraint, so NULL is never a valid value for any column that's part of a primary key, whether it's a single-column or composite key.

**6. How do you add a NOT NULL constraint to an existing column with data?**

First ensure every existing row already has a non-NULL value in that column (update any NULLs to a valid default first), then run the database-specific `ALTER TABLE` syntax — e.g. `ALTER TABLE users ALTER COLUMN name SET NOT NULL;` in PostgreSQL, or `ALTER TABLE users MODIFY name VARCHAR(100) NOT NULL;` in MySQL. If any row still has NULL in that column, the `ALTER TABLE` will fail.

**7. What is a composite primary key and when would you use one?**

A composite primary key is a primary key made up of two or more columns together, where the combination (not any single column alone) uniquely identifies a row. It's commonly used for junction/association tables representing a many-to-many relationship, like `order_items` keyed on `(order_id, product_id)`, where neither column alone is unique but the pair always is.

**8. What is the difference between RESTRICT and NO ACTION for foreign keys?**

They're functionally very similar — both prevent a parent delete/update if dependent child rows exist — but differ in timing semantics per the SQL standard: `RESTRICT` checks and blocks immediately, while `NO ACTION` defers the check until the end of the statement (which matters if other triggers or cascades within the same statement might resolve the conflict before the check happens). In many databases (e.g. MySQL), the two are treated as effectively identical in practice.

**9. Can you have a foreign key that references the same table?**

Yes — this is a self-referencing foreign key, commonly used for hierarchical data like an employee-manager relationship: `manager_id INT REFERENCES employees(id)` within the `employees` table itself, where each row's `manager_id` points to another row's `id` in that same table.

**10. How do you temporarily disable constraints for bulk data loading?**

Database-specific commands can suspend constraint/trigger checking during a bulk load for performance, then re-enable and optionally re-validate afterward — e.g. `SET FOREIGN_KEY_CHECKS = 0;` in MySQL, or `ALTER TABLE orders DISABLE TRIGGER ALL;` in PostgreSQL (which disables the triggers enforcing some constraint checks). This should be done carefully and constraints re-enabled (with data re-validated) immediately after the load, since it temporarily removes the database's data-integrity safety net.

## Key Takeaways

- Constraints enforce data integrity at the database level.
- PRIMARY KEY = UNIQUE + NOT NULL, only one per table.
- FOREIGN KEY enforces referential integrity between tables.
- Referential actions: CASCADE, SET NULL, RESTRICT, NO ACTION.
- UNIQUE allows NULL (usually one), can have multiple per table.
- CHECK validates data against boolean expressions.
- DEFAULT provides automatic values when none specified.
- Named constraints are easier to manage and debug.
- Use both database and application validation.
- Constraints can be added/removed with ALTER TABLE.

---

# Stored Procedures and Functions

*SQL Fundamentals*

Encapsulate business logic in reusable database programs.

## The Big Picture

Stored procedures and functions are precompiled SQL programs stored in the database. They encapsulate business logic, reduce network traffic, improve security, and provide code reuse. Understanding when to use procedures versus functions is a common interview topic.

**Why This Matters in Interviews**

- "What is the difference between a stored procedure and a function?"
- "When would you use a stored procedure vs inline SQL?"
- "What are the advantages and disadvantages of stored procedures?"
- "Can you call a stored procedure from a SELECT statement?"

## Procedures vs Functions

**Stored Procedure**

- Action-oriented.
- May or may not return a value.
- Can have IN, OUT, INOUT parameters.
- Can modify database state (INSERT, UPDATE, DELETE).
- Called with CALL or EXECUTE.
- Cannot be used in SELECT statements.
- Can call other procedures and functions.

**Function**

- Value-oriented.
- Must return a value.
- Only IN parameters (typically).
- Should not modify database state.
- Called within SQL expressions.
- Can be used in SELECT, WHERE, etc.
- Can call other functions (not procedures).

| Aspect | Stored Procedure | Function |
|---|---|---|
| Return Value | Optional (via OUT params or result sets) | Required (single value or table) |
| Use in SELECT | No | Yes |
| Transaction Control | Can use COMMIT/ROLLBACK | Cannot (usually) |
| Side Effects | Allowed (modify data) | Should be avoided (pure) |
| Invocation | `CALL procedure_name()` | `SELECT function_name()` |

## Creating Stored Procedures

```sql
-- PostgreSQL / Standard SQL
CREATE OR REPLACE PROCEDURE transfer_funds(
    IN from_account INT,
    IN to_account INT,
    IN amount DECIMAL(10,2)
)
LANGUAGE plpgsql
AS $$
BEGIN
    -- Deduct from source account
    UPDATE accounts
    SET balance = balance - amount
    WHERE id = from_account;

    -- Add to destination account
    UPDATE accounts
    SET balance = balance + amount
    WHERE id = to_account;

    COMMIT;
END;
$$;

-- Call the procedure
CALL transfer_funds(1, 2, 500.00);
```

```sql
-- MySQL syntax
DELIMITER //
CREATE PROCEDURE get_employee_by_dept(
    IN dept_id INT,
    OUT emp_count INT
)
BEGIN
    SELECT COUNT(*) INTO emp_count
    FROM employees
    WHERE department_id = dept_id;

    SELECT * FROM employees
    WHERE department_id = dept_id;
END //
DELIMITER ;

-- Call with OUT parameter
CALL get_employee_by_dept(10, @count);
SELECT @count;
```

**Parameter Modes**

- **IN:** Input only (default). Value passed into procedure, changes not visible to caller.
- **OUT:** Output only. Procedure sets value, returned to caller.
- **INOUT:** Both input and output. Value passed in, can be modified and returned.

## Creating Functions

```sql
-- PostgreSQL scalar function
CREATE OR REPLACE FUNCTION calculate_tax(
    price DECIMAL(10,2),
    tax_rate DECIMAL(4,2) DEFAULT 0.08
)
RETURNS DECIMAL(10,2)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN price * tax_rate;
END;
$$;

-- Use in SELECT
SELECT
    product_name,
    price,
    calculate_tax(price) AS tax,
    price + calculate_tax(price) AS total
FROM products;
```

```sql
-- Table-returning function (PostgreSQL)
CREATE OR REPLACE FUNCTION get_high_value_orders(
    min_amount DECIMAL(10,2)
)
RETURNS TABLE (
    order_id INT,
    customer_name VARCHAR,
    total DECIMAL(10,2)
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT o.id, c.name, o.total
    FROM orders o
    JOIN customers c ON o.customer_id = c.id
    WHERE o.total >= min_amount;
END;
$$;

-- Use as a table source
SELECT * FROM get_high_value_orders(1000.00);
```

```sql
-- MySQL function
DELIMITER //
CREATE FUNCTION get_full_name(
    first_name VARCHAR(50),
    last_name VARCHAR(50)
)
RETURNS VARCHAR(101)
DETERMINISTIC
BEGIN
    RETURN CONCAT(first_name, ' ', last_name);
END //
DELIMITER ;

-- Use in query
SELECT get_full_name(first_name, last_name) AS name
FROM employees;
```

## Control Flow Statements

```sql
-- IF-THEN-ELSE
IF quantity > 100 THEN
    discount := 0.20;
ELSIF quantity > 50 THEN
    discount := 0.10;
ELSE
    discount := 0;
END IF;

-- CASE statement
CASE status
    WHEN 'pending' THEN
        process_pending();
    WHEN 'approved' THEN
        process_approved();
    ELSE
        log_unknown_status();
END CASE;

-- LOOP with EXIT
LOOP
    FETCH cur INTO row_data;
    EXIT WHEN NOT FOUND;
    -- Process row
END LOOP;

-- WHILE loop
WHILE counter < 10 LOOP
    counter := counter + 1;
END LOOP;

-- FOR loop
FOR i IN 1..10 LOOP
    total := total + i;
END LOOP;
```

## Error Handling

```sql
-- PostgreSQL exception handling
CREATE OR REPLACE PROCEDURE safe_transfer(
    from_acc INT, to_acc INT, amount DECIMAL
)
LANGUAGE plpgsql
AS $$
DECLARE
    from_balance DECIMAL;
BEGIN
    -- Check balance
    SELECT balance INTO from_balance
    FROM accounts WHERE id = from_acc;

    IF from_balance < amount THEN
        RAISE EXCEPTION 'Insufficient funds: % available', from_balance;
    END IF;

    -- Perform transfer
    UPDATE accounts SET balance = balance - amount WHERE id = from_acc;
    UPDATE accounts SET balance = balance + amount WHERE id = to_acc;

EXCEPTION
    WHEN others THEN
        RAISE NOTICE 'Transfer failed: %', SQLERRM;
        ROLLBACK;
END;
$$;
```

```sql
-- MySQL error handling
DELIMITER //
CREATE PROCEDURE safe_insert(
    IN p_email VARCHAR(255)
)
BEGIN
    DECLARE EXIT HANDLER FOR 1062
    BEGIN
        SELECT 'Duplicate email address' AS error_message;
    END;

    INSERT INTO users (email) VALUES (p_email);
    SELECT 'User created successfully' AS message;
END //
DELIMITER ;
```

## When to Use Stored Procedures

- **Complex Business Logic** — Multi-step operations that should be atomic and consistent across applications.
- **Security** — Grant execute permission without exposing table access. Prevent SQL injection.
- **Performance** — Reduce network round trips. Execution plan cached and reused.
- **Data Validation** — Enforce rules beyond what constraints can express.
- **Batch Operations** — Process large amounts of data efficiently server-side.
- **Audit Logging** — Automatically log changes with triggers calling procedures.

**When NOT to Use Stored Procedures**

- Simple CRUD: ORM or parameterized queries are often simpler.
- Portability needed: Syntax varies significantly between databases.
- Version control: Harder to track changes vs application code.
- Testing: More difficult to unit test than application code.
- Debugging: Limited debugging tools compared to application IDEs.

## Top 10 Interview Questions

Master these frequently asked questions about stored procedures and functions:

**1. What is the difference between a stored procedure and a function?**

A function must return a value and is designed to be used inline within SQL expressions (`SELECT`, `WHERE`, etc.) — it should be side-effect-free and typically can't commit/rollback transactions. A stored procedure is action-oriented: it may or may not return a value (often via OUT parameters or result sets), can freely modify data and manage transactions, but cannot be called from within a `SELECT` — it's invoked explicitly with `CALL`/`EXECUTE`.

**2. What are the advantages of stored procedures?**

Reduced network round trips (multiple statements execute server-side as one call), improved security (grant execute permission on a procedure without exposing direct table access, and parameterization reduces SQL injection risk), reusability and consistency (centralized business logic shared across every application/client that calls it), and performance (the execution plan is precompiled/cached and reused across calls).

**3. What are the disadvantages of stored procedures?**

Poor portability (syntax differs significantly across PostgreSQL, MySQL, SQL Server, Oracle), harder version control and code review compared to application code in a normal source repo, more difficult to unit test in isolation, and generally weaker debugging tooling compared to full application-language IDEs/debuggers. Logic split between application code and database procedures can also become harder to reason about as a whole system.

**4. Can you call a stored procedure from a SELECT statement?**

No — stored procedures are invoked with `CALL` (or `EXECUTE`, depending on the database) as a standalone statement, not embedded inside a `SELECT`. Only functions, which are required to return a value and designed for use in expressions, can be called from within a `SELECT`, `WHERE`, or other SQL expression context.

**5. What are IN, OUT, and INOUT parameters?**

`IN` parameters pass a value into the procedure; the procedure can use it but any changes to the parameter inside don't propagate back to the caller (this is the default and the only mode functions typically support). `OUT` parameters are output-only: the caller passes a placeholder variable, and the procedure sets its value, which the caller can read after the call. `INOUT` parameters do both — the caller's initial value is passed in, the procedure can read and modify it, and the final value is returned to the caller's variable.

**6. How do you handle errors in stored procedures?**

Most procedural SQL dialects provide exception/handler blocks: PostgreSQL's PL/pgSQL uses `EXCEPTION WHEN ... THEN ...` blocks within the procedure body (e.g. `WHEN others THEN RAISE NOTICE ...; ROLLBACK;`), while MySQL uses `DECLARE ... HANDLER FOR <condition>` to define what happens when a specific error code (like 1062 for duplicate key) occurs. You can also proactively raise custom errors with `RAISE EXCEPTION` (PostgreSQL) to enforce business-rule violations before they'd otherwise cause a less meaningful database error.

**7. What is the difference between DETERMINISTIC and non-deterministic functions?**

A `DETERMINISTIC` function always returns the same output given the same input arguments, with no dependency on changing external state (e.g. a function that just concatenates two strings). A non-deterministic function can return different results on different calls with identical arguments (e.g. one that reads the current timestamp or a random value, or queries continuously-changing table data). Marking a function `DETERMINISTIC` when it genuinely is lets the optimizer cache/reuse results and is required by some databases (like MySQL) for certain contexts (e.g. functions used in generated/indexed columns).

**8. How do stored procedures help with security?**

They let you grant users or application roles permission to `EXECUTE` a specific procedure without granting them direct `SELECT`/`INSERT`/`UPDATE` access to the underlying tables — the procedure acts as a controlled gateway that only performs the specific, vetted operations it was written to do. Because procedures use parameterized inputs rather than string-concatenated SQL, they also naturally reduce SQL injection risk compared to hand-built dynamic queries in application code.

**9. What is a cursor and when would you use one?**

A cursor is a database object that lets you iterate through a result set row by row within procedural code (e.g. inside a stored procedure), rather than operating on the whole set at once as normal SQL does. You'd use one when you need row-by-row procedural logic that can't be expressed as a single set-based SQL statement — e.g. complex conditional processing per row, or calling other procedures per row — though in general, set-based SQL operations should be preferred over cursors whenever possible since cursors are typically much slower.

**10. Should business logic be in stored procedures or application code?**

It depends on the trade-offs: stored procedures favor performance (fewer round trips), security (controlled access), and logic that must be enforced consistently regardless of which client touches the database — but at the cost of portability, testability, and version-control friendliness compared to application code. Many teams favor keeping business logic in application code (easier to test, review, deploy, and keep portable across databases) and reserve stored procedures for performance-critical batch operations, security-sensitive gateways, or logic that genuinely must live at the database layer (like triggers for audit logging).

## Key Takeaways

- Procedures perform actions; functions return values.
- Functions can be used in SELECT; procedures cannot.
- Procedures can have IN, OUT, INOUT parameters.
- Functions should be pure (no side effects).
- Use procedures for complex multi-step operations.
- Error handling with EXCEPTION/HANDLER blocks.
- Control flow: IF, CASE, LOOP, WHILE, FOR.
- Benefits: Security, performance, consistency.
- Drawbacks: Portability, testing, debugging.
- Consider trade-offs vs ORM/application code.

---

# JDBC (Java Database Connectivity) Interview Revision Guide

This comprehensive guide is designed to help you refresh and strengthen your understanding of JDBC concepts in preparation for technical interviews.

**In this chapter, you'll learn:**

- What is JDBC and Why It's Used in Java Applications
- JDBC Architecture and Core Components
- Steps to Establish a Database Connection
- Types of JDBC Drivers: Type 1, 2, 3, and 4
- Statement vs PreparedStatement: Key Differences
- Preventing SQL Injection with PreparedStatement
- Performing Batch Updates for Better Performance
- ResultSet Interface: Common Methods and Usage
- Transaction Management: Commit, Rollback, and Auto-Commit
- ACID Properties in JDBC Transactions
- DataSource and Connection Pooling
- Handling SQL Exceptions Effectively
- Best Practices for Resource Management (try-with-resources)

## What Is JDBC, and Why Is It Used in Java Applications?

JDBC (Java Database Connectivity) is an API (Application Programming Interface) in Java that enables Java applications to interact with databases. It provides a standard method for connecting to a database, sending SQL queries, and processing the results.

**What Is JDBC?**

1. **Java API:** JDBC is a part of the Java Standard Edition platform, consisting of a set of interfaces and classes that allow Java applications to interact with relational databases.
2. **Standardization:** It provides a standard API for database-agnostic interaction, meaning the same code can be used to connect to different types of databases (like MySQL, PostgreSQL, Oracle, etc.) with minimal changes.
3. **Components:** JDBC includes four main components:
   - **JDBC Drivers:** Vendor-specific implementations that enable Java applications to communicate with a specific database.
   - **Connection:** Represents a connection to a specific database.
   - **Statement:** Used for executing a static SQL statement and returning the results it produces.
   - **ResultSet:** Represents the result set of a query.

**Why Is JDBC Used in Java Applications?**

1. **Database Interaction:** JDBC provides a way for Java applications to perform database operations like querying, updating, and managing data.
2. **Platform Independence:** Java applications can connect to any database that has a JDBC driver, making the applications database-independent and highly portable.
3. **Standardized API:** It abstracts the complexities of database-specific communication, providing a uniform interface for interacting with different databases.
4. **Performance:** JDBC allows efficient processing of SQL statements, batch updates, and retrieval of large datasets.

**How JDBC Works**

```java
// Establish a Connection
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "username", "password");

// Create a Statement: Create a statement to execute SQL queries.
Statement stmt = conn.createStatement();

// Execute Queries: Execute SQL queries and retrieve results.
ResultSet rs = stmt.executeQuery("SELECT * FROM mytable");
while (rs.next()) {
    System.out.println("Column Value: " + rs.getString("columnname"));
}

// Close Connections: Close the connection and other resources to free up database resources.
rs.close();
stmt.close();
conn.close();
```

**Example Full Code:**

Here is a complete example demonstrating how to connect to a MySQL database, execute a query, and process the results:

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCExample {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "password";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            conn = DriverManager.getConnection(jdbcUrl, username, password);

            // Create a statement
            stmt = conn.createStatement();

            // Execute a query
            rs = stmt.executeQuery("SELECT * FROM mytable");

            // Process the result set
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
```

## Explain the Steps Involved in Establishing a Connection to a Database Using JDBC

Establishing a connection to a database using JDBC involves several key steps:

**1. Load the JDBC Driver**

The first step is to load the JDBC driver class. The driver class connects your application to the database. This is done using the `Class.forName()` method.

**2. Establish a Connection**

Next, you create a connection to the database using the `DriverManager.getConnection()` method. This method requires the database URL, username, and password.

**3. Create a Statement**

Once you have a connection, you can create a statement object. This object is used to execute SQL queries.

**4. Execute a Query**

Using the statement object, you can execute SQL queries. For example, to execute a SELECT query, you use the `executeQuery` method, which returns a `ResultSet` object containing the result of the query.

**5. Process the ResultSet**

If you executed a query that returns results (such as a SELECT query), you need to process the `ResultSet` to retrieve the data.

**6. Close Resources**

Finally, you should close all the resources to free up database connections and avoid potential memory leaks. This includes closing the `ResultSet`, `Statement`, and `Connection`.

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCExample {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "password";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Step 1: Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Establish the connection
            conn = DriverManager.getConnection(jdbcUrl, username, password);

            // Step 3: Create a statement
            stmt = conn.createStatement();

            // Step 4: Execute a query
            String sql = "SELECT * FROM mytable";
            rs = stmt.executeQuery(sql);

            // Step 5: Process the ResultSet
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Step 6: Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
```

## What Are the Different Types of JDBC Drivers, and How Do They Differ?

JDBC drivers facilitate communication between Java applications and databases. There are four main types:

**1. Type 1: JDBC-ODBC Bridge Driver**

- **Description:** Uses the JDBC-ODBC bridge to connect to databases that support ODBC (Open Database Connectivity).
- **Architecture:** Translates JDBC calls into ODBC calls, which are then executed by the ODBC driver.
- **Platform:** Typically platform-dependent and requires native ODBC libraries.
- **Advantages:** Allows Java applications to connect to any database that has an ODBC driver; provides JDBC access to non-Java databases.
- **Disadvantages:** Requires an ODBC driver installed on the client machine; limited in performance and portability.
- **Example:** `sun.jdbc.odbc.JdbcOdbcDriver` (Deprecated in Java 8).

**2. Type 2: Native-API Driver**

- **Description:** Converts JDBC calls directly into native API calls of the database.
- **Architecture:** Written partly in Java and partly in the native language of the database.
- **Platform:** Platform-dependent, as it relies on native code libraries.
- **Advantages:** Offers better performance compared to the Type 1 driver; does not require ODBC, as it communicates directly with the database.
- **Disadvantages:** Requires different drivers for different platforms; less portable compared to Type 4 drivers.
- **Example:** Oracle OCI (Oracle Call Interface) driver.

**3. Type 3: Network Protocol Driver (Middleware Driver)**

- **Description:** Uses middleware to convert JDBC calls into a database-independent network protocol.
- **Architecture:** The middleware server translates JDBC calls into the database-specific protocol.
- **Platform:** Platform-independent on the client side; requires middleware server.
- **Advantages:** Provides database independence; allows clients to connect to multiple types of databases through a single driver.
- **Disadvantages:** Relies on middleware server, which may introduce additional latency; configuration and maintenance of middleware server required.
- **Example:** JDBC-Net pure Java driver.

**4. Type 4: Thin Driver (Direct-to-Database Pure Java Driver)**

- **Description:** Converts JDBC calls directly into the network protocol used by the database.
- **Architecture:** Pure Java implementation that communicates directly with the database server.
- **Platform:** Platform-independent; does not require any additional software installation.
- **Advantages:** Highest performance among JDBC drivers; platform-independent and easy to deploy.
- **Disadvantages:** May not support all database features; may require client-side installation of additional JAR files.
- **Example:** Oracle Thin driver, MySQL Connector/J.

## What Is the Purpose of PreparedStatement in JDBC, and How Does It Differ from Statement?

The `PreparedStatement` interface in JDBC is used to execute parameterized SQL queries. It is a subinterface of the `Statement` interface and provides several advantages over it.

**Purpose of PreparedStatement:**

1. **Parameterized Queries:** `PreparedStatement` allows you to execute parameterized SQL queries, where placeholders (`?`) are used for dynamic values. This helps prevent SQL injection attacks and improves performance by reusing compiled queries.
2. **Improved Performance:** Prepared statements are precompiled by the database server, which can lead to improved performance when executing the same query multiple times with different parameters.
3. **Readability and Maintainability:** Using parameterized queries with `PreparedStatement` makes your code more readable and maintainable, as it separates SQL logic from data.
4. **Automatic Escaping:** `PreparedStatement` automatically handles escaping of special characters in parameters, reducing the risk of SQL injection vulnerabilities.

**Differences from Statement:**

1. **Precompilation:** `PreparedStatement` is precompiled by the database server when it is created, whereas `Statement` is compiled each time it is executed. This can result in better performance for `PreparedStatement` when the same query is executed multiple times with different parameters.
2. **Parameterized Queries:** `PreparedStatement` supports parameterized queries, allowing you to pass dynamic values to SQL statements. `Statement` does not support parameterized queries directly, which makes it more susceptible to SQL injection attacks.
3. **Execution Flexibility:** `Statement` is suitable for executing static SQL queries without parameters, while `PreparedStatement` is more flexible and efficient for executing parameterized queries.

**Example:**

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreparedStatementExample {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "username";
        String password = "password";
        String name = "John"; // Example parameter value

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            String sql = "SELECT * FROM mytable WHERE name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name); // Set parameter value

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String resultName = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println("ID: " + id + ", Name: " + resultName + ", Age: " + age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

In this example, the `PreparedStatement` object is created with a parameterized query (`SELECT * FROM mytable WHERE name = ?`). The parameter value is set using `setString()` before executing the query. This approach ensures better security and performance compared to executing a plain `Statement`.

## Describe How You Can Use JDBC to Perform Batch Updates

Using JDBC, you can perform batch updates to a database efficiently by executing multiple SQL statements in a single batch. This can significantly reduce the overhead of database communication and improve performance.

**1. Establish a Connection**

First, you need to establish a connection to your database using JDBC. You typically do this by loading the appropriate database driver and then creating a connection object using the `DriverManager.getConnection()` method.

```java
Connection connection = DriverManager.getConnection(url, username, password);
```

**2. Create a Statement or PreparedStatement Object**

You can create either a `Statement` or a `PreparedStatement` object to execute your SQL statements. `PreparedStatement` is preferred when using batch updates because it allows you to parameterize your SQL queries, which can improve performance and security.

```java
PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO table_name (column1, column2) VALUES (?, ?)");
```

**3. Add Batch Updates**

You can add multiple SQL statements to the batch using the `addBatch()` method of the `Statement` or `PreparedStatement` object.

```java
preparedStatement.setInt(1, value1);
preparedStatement.setString(2, value2);
preparedStatement.addBatch();
```

Repeat this step for each SQL statement you want to add to the batch.

**4. Execute Batch Updates**

Once you have added all the statements to the batch, you can execute them using the `executeBatch()` method. This will execute all the statements in the batch and return an array of integers representing the number of rows affected by each statement.

```java
int[] updateCounts = preparedStatement.executeBatch();
```

**5. Handle Results**

After executing the batch, you can iterate over the array of update counts to handle the results of each SQL statement.

```java
for (int updateCount : updateCounts) {
    // Handle update count
}
```

**6. Close Resources**

Finally, don't forget to close the `PreparedStatement`, `Connection`, and any other resources you used to interact with the database.

```java
preparedStatement.close();
connection.close();
```

## What Are the Common Methods Available in the ResultSet Interface, and How Are They Used?

The `ResultSet` interface in Java provides methods to retrieve and manipulate data from a database query result set.

**1. next()** — Moves the cursor to the next row in the result set. It returns `true` if there is a next row, and `false` if there are no more rows.

```java
while (resultSet.next()) {
    // Process current row
}
```

**2. getString(int columnIndex) / getString(String columnLabel)** — Retrieves the value of the specified column in the current row as a `String`.

```java
String name = resultSet.getString("name");
```

**3. getInt(int columnIndex) / getInt(String columnLabel)** — Retrieves the value of the specified column in the current row as an `int`.

```java
int age = resultSet.getInt("age");
```

**4. getDouble(int columnIndex) / getDouble(String columnLabel)** — Retrieves the value of the specified column in the current row as a `double`.

```java
double salary = resultSet.getDouble("salary");
```

**5. getDate(int columnIndex) / getDate(String columnLabel)** — Retrieves the value of the specified column in the current row as a `java.sql.Date` object.

```java
Date hireDate = resultSet.getDate("hire_date");
```

**6. getObject(int columnIndex) / getObject(String columnLabel)** — Retrieves the value of the specified column in the current row as a generic `Object`.

```java
Object value = resultSet.getObject("column_name");
```

**7. wasNull()** — Indicates whether the last column read was NULL.

```java
if (resultSet.wasNull()) {
    // Handle NULL value
}
```

**8. close()** — Closes the `ResultSet` object, releasing any resources it holds.

```java
resultSet.close();
```

## Explain How Transactions Are Managed in JDBC

In JDBC, transactions are managed using the `Connection` object. JDBC provides support for both manual and automatic transaction management.

**1. Starting a Transaction**

To start a transaction, you set the auto-commit mode of the `Connection` object to `false`. By default, auto-commit mode is enabled, meaning each SQL statement is treated as a separate transaction and is automatically committed immediately after it is executed.

```java
connection.setAutoCommit(false);
```

**2. Executing SQL Statements**

Once the auto-commit mode is disabled, you can execute multiple SQL statements as part of a single transaction using the same `Connection` object.

```java
Statement statement = connection.createStatement();
statement.executeUpdate("INSERT INTO table_name (column1, column2) VALUES (value1, value2)");
statement.executeUpdate("UPDATE table_name SET column1 = value WHERE condition");
// More SQL statements
```

**3. Committing a Transaction**

If all SQL statements within the transaction execute successfully and you want to make the changes permanent in the database, you commit the transaction using the `commit()` method.

```java
connection.commit();
```

**4. Rolling Back a Transaction**

If an error occurs during the execution of the SQL statements or if you want to discard the changes made in the transaction, you can roll back the transaction using the `rollback()` method.

```java
connection.rollback();
```

**5. Re-enabling Auto-Commit**

Once the transaction is completed (either committed or rolled back), you can re-enable the auto-commit mode by setting it back to `true`.

```java
connection.setAutoCommit(true);
```

By managing transactions manually, you have more control over the atomicity, consistency, isolation, and durability (ACID properties) of your database operations. It allows you to group multiple SQL statements into a single logical unit of work and ensures that either all the statements within the transaction are executed successfully and committed, or none of them are. This helps maintain data integrity and consistency in your database.

## What Is the Role of DataSource in JDBC, and How Does It Improve Database Connection Management?

The `DataSource` interface in JDBC plays a significant role in managing database connections efficiently. It provides a standard interface for obtaining a database connection and helps improve connection management in several ways:

1. **Connection Pooling:** One of the primary benefits of using a `DataSource` is connection pooling. Instead of creating a new database connection every time one is requested, a pool of pre-initialized connections is maintained by the `DataSource`. This minimizes the overhead of creating and destroying connections, as connections are reused from the pool.
2. **Optimized Connection Creation:** `DataSource` implementations typically initialize a set of database connections when they are created. These connections are ready for use and are kept alive within the connection pool. This eliminates the need to establish a new connection every time a database operation is performed, resulting in faster response times.
3. **Connection Configuration:** `DataSource` allows for configuring various properties related to database connections, such as maximum number of connections in the pool, timeout settings, and authentication credentials. These configurations can be adjusted based on application requirements and database constraints.
4. **Support for Connection Pooling Libraries:** Many JDBC drivers come with built-in support for `DataSource` implementations that integrate with popular connection pooling libraries such as Apache Commons DBCP (Database Connection Pooling) and HikariCP. These libraries offer advanced connection pooling features like connection validation, idle connection eviction, and dynamic resizing of connection pools.
5. **Abstraction of Connection Management:** `DataSource` abstracts the details of database connection management from the application code. Applications obtain database connections through the `DataSource` interface without needing to know the specifics of how connections are managed internally. This simplifies application development and makes it easier to switch between different database implementations or connection pooling mechanisms without changing application code.

In summary, the `DataSource` interface in JDBC improves database connection management by providing a standardized way to obtain database connections, supporting connection pooling, optimizing connection creation, allowing for flexible configuration, and abstracting the complexities of connection management from application code. This leads to better performance, scalability, and reliability of database operations in Java applications.

## What Is the Difference Between execute(), executeQuery(), and executeUpdate() Methods?

These three methods in JDBC are used to execute SQL statements, but they serve different purposes and return different types of results.

**1. executeQuery()**

- Returns a `ResultSet` containing the data retrieved by the query.
- Used exclusively for `SELECT` statements.
- Throws `SQLException` if used with non-`SELECT` statements.

```java
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery("SELECT id, name, salary FROM employees");

while (rs.next()) {
    System.out.println(rs.getInt("id") + " - " + rs.getString("name"));
}
```

**2. executeUpdate()**

- Returns an `int` representing the number of rows affected.
- Used for DML statements: `INSERT`, `UPDATE`, `DELETE`.
- Also used for DDL statements: `CREATE`, `ALTER`, `DROP` (returns 0).

```java
Statement stmt = conn.createStatement();

// INSERT example
int rowsInserted = stmt.executeUpdate("INSERT INTO employees (name, salary) VALUES ('John', 50000)");
System.out.println("Rows inserted: " + rowsInserted); // Output: Rows inserted: 1

// UPDATE example
int rowsUpdated = stmt.executeUpdate("UPDATE employees SET salary = 55000 WHERE name = 'John'");
System.out.println("Rows updated: " + rowsUpdated); // Output: Rows updated: 1

// DELETE example
int rowsDeleted = stmt.executeUpdate("DELETE FROM employees WHERE name = 'John'");
System.out.println("Rows deleted: " + rowsDeleted); // Output: Rows deleted: 1

// DDL example (returns 0)
int ddlResult = stmt.executeUpdate("CREATE TABLE temp_table (id INT)");
System.out.println("DDL result: " + ddlResult); // Output: DDL result: 0
```

**3. execute()**

- Returns `boolean`: `true` if result is a `ResultSet`, `false` if it's an update count.
- Used when SQL type is unknown at compile time or for stored procedures returning multiple results.
- More flexible but requires additional handling.

```java
Statement stmt = conn.createStatement();
String sql = "SELECT * FROM employees"; // Could be dynamic

boolean hasResultSet = stmt.execute(sql);

if (hasResultSet) {
    // It's a SELECT query
    ResultSet rs = stmt.getResultSet();
    while (rs.next()) {
        System.out.println(rs.getString("name"));
    }
} else {
    // It's an UPDATE/INSERT/DELETE/DDL
    int updateCount = stmt.getUpdateCount();
    System.out.println("Rows affected: " + updateCount);
}
```

## What Are ResultSet Types and Concurrency Modes in JDBC?

When creating a `Statement` or `PreparedStatement`, you can specify the type and concurrency mode of the `ResultSet`. These determine how you can navigate through the result set and whether you can update it.

**ResultSet Types**

**a) TYPE_FORWARD_ONLY**

- Cannot scroll (only forward).
- Not sensitive to DB changes.
- Default and most efficient.
- Use when you only need to read data once.

**b) TYPE_SCROLL_INSENSITIVE**

- Can scroll (forward, backward, random access).
- Does not reflect DB changes after `ResultSet` creation.
- Snapshot of data at query time.

**c) TYPE_SCROLL_SENSITIVE**

- Can scroll (forward, backward, random access).
- Reflects DB changes made by others.
- More overhead, depends on driver support.

**ResultSet Concurrency Modes**

**a) CONCUR_READ_ONLY**

- Cannot update data.
- Default mode.
- Best for simple read operations.

**b) CONCUR_UPDATABLE**

- Can update, insert, and delete rows via `ResultSet`.
- Requires proper SQL query and driver support.

```java
// Default: Forward-only, Read-only (most efficient)
Statement stmt1 = conn.createStatement();

// Scrollable, Read-only
Statement stmt2 = conn.createStatement(
    ResultSet.TYPE_SCROLL_INSENSITIVE,
    ResultSet.CONCUR_READ_ONLY
);

// Scrollable, Updatable
Statement stmt3 = conn.createStatement(
    ResultSet.TYPE_SCROLL_SENSITIVE,
    ResultSet.CONCUR_UPDATABLE
);
```

**Common Interview Question: Why is TYPE_FORWARD_ONLY the default?**

It's the most memory-efficient option. The database driver doesn't need to cache all rows in memory, and it can stream results row by row. Scrollable `ResultSet`s require caching all results, which consumes more memory and may not be supported efficiently by all database drivers.

## How Can You Handle SQL Exceptions in JDBC Effectively?

Handling SQL exceptions effectively in JDBC is crucial for robust error handling and ensuring the stability of database operations.

**1. Use try-with-resources**

When working with JDBC resources such as `Connection`, `Statement`, and `ResultSet`, use try-with-resources to automatically close these resources after use. This ensures that resources are properly released even if an exception occurs.

```java
try (Connection connection = DriverManager.getConnection(url, username, password);
     Statement statement = connection.createStatement()) {
    // Database operations
} catch (SQLException e) {
    // Exception handling
}
```

**2. Catch Specific Exceptions**

Catch specific `SQLException`s and handle them appropriately based on the nature of the exception. Common SQL exceptions include `SQLException`, `SQLTimeoutException`, `SQLIntegrityConstraintViolationException`, etc. Handling specific exceptions allows for more granular error handling.

```java
try {
    // Database operations
} catch (SQLTimeoutException e) {
    // Handle timeout exception
} catch (SQLIntegrityConstraintViolationException e) {
    // Handle constraint violation exception
} catch (SQLException e) {
    // Handle other SQL exceptions
}
```

**3. Log Exceptions**

Log SQL exceptions along with relevant information such as error codes, error messages, and SQL statements. This helps in troubleshooting and debugging issues during development and in production environments.

```java
catch (SQLException e) {
    logger.error("SQL Exception occurred: ", e);
}
```

**4. Rollback Transactions**

If an exception occurs during a transaction, roll back the transaction to ensure data consistency and integrity. This helps avoid partially completed transactions that could leave the database in an inconsistent state.

```java
try {
    // Start transaction
    connection.setAutoCommit(false);
    // Database operations
    connection.commit();
} catch (SQLException e) {
    // Rollback transaction
    connection.rollback();
    // Exception handling
} finally {
    // Reset auto-commit mode
    connection.setAutoCommit(true);
}
```

**5. Provide Meaningful Error Messages**

When catching and handling SQL exceptions, provide meaningful error messages or notifications to users or system administrators. This improves the usability of the application and helps users understand the cause of the error.

```java
catch (SQLException e) {
    // Display error message to user
    System.out.println("An error occurred while processing your request. Please try again later.");
}
```

---

# JPA (Java Persistence API) Interview Revision Guide

This comprehensive guide is designed to help you refresh and strengthen your understanding of JPA (Java Persistence API) concepts in preparation for technical interviews.

**In this chapter, you'll learn:**

Core JPA Concepts

- What is JPA and How It Simplifies Database Operations
- EntityManager vs EntityManagerFactory
- Persistence Context: Transaction-Scoped vs Extended
- Configuring JPA with persistence.xml

Entity Mapping

- Defining Entities with @Entity and @Table
- Key Annotations: @Id, @Column, @GeneratedValue, @Temporal, @Enumerated, @Lob, @Transient
- Entity Relationships: @OneToOne, @OneToMany, @ManyToOne, @ManyToMany
- Join Columns and Join Tables

Querying

- JPQL vs SQL: Key Differences
- Named Queries with @NamedQuery
- Pagination using setFirstResult() and setMaxResults()

Operations & Transactions

- CRUD Operations: persist(), find(), merge(), remove()
- Transaction Management: begin(), commit(), rollback()
- Entity Lifecycle Callbacks: @PrePersist, @PostPersist, @PreUpdate, @PostUpdate, @PreRemove, @PostRemove

Performance & Concurrency

- Lazy vs Eager Fetching (FetchType.LAZY vs FetchType.EAGER)
- Optimistic Locking with @Version

## What Is JPA, and How Does It Simplify Database Operations in Java Applications?

Java Persistence API (JPA) is a specification for object-relational mapping (ORM) in Java. It provides a framework for managing relational data in Java applications by mapping Java objects to database tables, and vice versa. JPA simplifies database operations through several key features:

**Key Features of JPA**

1. **Annotations and XML Configuration:** JPA allows you to define the mapping between Java classes and database tables using annotations or XML configuration. This declarative approach makes it easier to manage and maintain the mapping information.
2. **Entity Classes:** In JPA, a simple Java class annotated with `@Entity` becomes an entity class that maps to a database table. Each instance of the class corresponds to a row in the table.
3. **Entity Manager:** The `EntityManager` interface provides the core functionality for performing CRUD (Create, Read, Update, Delete) operations on the database. It handles the lifecycle of entities and transactions.
4. **JPQL (Java Persistence Query Language):** JPQL is a query language similar to SQL but operates on the entity objects rather than database tables. This allows you to write database queries using Java syntax and object-oriented principles.
5. **Automatic Schema Generation:** JPA can automatically generate the database schema based on the entity mappings. This reduces the need for manual SQL scripts to create and update tables.
6. **Transaction Management:** JPA supports both local and global transactions. It integrates seamlessly with Java Transaction API (JTA) to manage transactions across multiple resources.
7. **Cascading and Fetching:** JPA provides options for cascading operations (e.g., cascading persist, remove, etc.) and fetching strategies (e.g., eager or lazy loading) to optimize performance and manage relationships between entities.

Here is a simple example of a JPA entity class and basic CRUD operations:

```java
import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // Getters and setters
}

public class UserService {
    @PersistenceContext
    private EntityManager entityManager;

    public void createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        entityManager.persist(user);
    }

    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    public void updateUser(Long id, String name, String email) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            entityManager.merge(user);
        }
    }

    public void deleteUser(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
}
```

## Explain the Difference Between EntityManager and EntityManagerFactory in JPA

In JPA, `EntityManager` and `EntityManagerFactory` are core components used for managing the persistence of entities in a relational database. They serve different purposes and are used at different stages of the application lifecycle.

**EntityManagerFactory**

1. **Definition:** `EntityManagerFactory` is a factory class responsible for creating `EntityManager` instances.
2. **Lifecycle:** `EntityManagerFactory` is a heavyweight object designed to be created once and used throughout the application. It is typically created at application startup and closed when the application is shutting down.
3. **Configuration:** It is configured using persistence units defined in the `persistence.xml` file. This configuration includes database connection details, JPA provider properties, and class mappings.
4. **Resource Management:** Since it is an expensive object to create, `EntityManagerFactory` should be reused rather than recreated frequently. It handles resource-intensive tasks such as connection pooling and caching.
5. **Thread Safety:** `EntityManagerFactory` is thread-safe and can be shared across multiple threads without synchronization issues.

```java
EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
```

**EntityManager**

1. **Definition:** `EntityManager` is the primary interface used to interact with the persistence context. It provides methods for CRUD operations, querying, and transaction management.
2. **Lifecycle:** `EntityManager` is a lightweight, short-lived object. It is created by `EntityManagerFactory` and typically used within the scope of a single transaction or request.
3. **Persistence Context:** Each `EntityManager` instance is associated with a persistence context, which is a set of managed entity instances. The persistence context ensures that each entity is managed and synchronized with the database.
4. **Transaction Management:** `EntityManager` manages the lifecycle of entities within a transaction. It handles operations such as persisting, merging, removing, and querying entities.
5. **Thread Safety:** `EntityManager` is not thread-safe. Each thread or transaction should use its own `EntityManager` instance.

```java
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();
// perform operations
em.getTransaction().commit();
em.close();
```

## How Do You Define an Entity in JPA, and What Are the Key Annotations Used?

Defining an entity in JPA involves creating a plain Java class and using specific annotations to map it to a database table.

**Defining an Entity**

1. **Create a Java Class:** The class should be a simple POJO (Plain Old Java Object) with properties, getters, and setters.
2. **Annotate the Class with @Entity:** This annotation specifies that the class is an entity and will be mapped to a database table.
3. **Define the Primary Key with @Id:** Every entity must have a primary key, which is annotated with `@Id`.
4. **Specify the Table Name with @Table (Optional):** If the table name is different from the class name, use the `@Table` annotation to specify the table name.
5. **Map Fields to Columns with @Column (Optional):** Use the `@Column` annotation to customize the mapping between class fields and table columns.
6. **Generate Primary Key Values with @GeneratedValue (Optional):** To automatically generate primary key values, use the `@GeneratedValue` annotation.

**Key Annotations:**

```java
import javax.persistence.*;
import java.util.Date;

@Entity // Marks the class as an entity.
@Table(name = "users") // Specifies the table name
public class User {

    @Id // Specifies the primary key field.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies the primary key generation strategy.
    private Long id;

    @Column(name = "user_name", nullable = false, length = 50)
    private String name;

    @Column(name = "email", nullable = false, unique = true) // Maps a field to a column in the database
    private String email;

    @Temporal(TemporalType.DATE) // Specifies the temporal type (DATE, TIME, or TIMESTAMP)
    private Date birthDate;

    @Enumerated(EnumType.STRING) // Specifies that a field is an enumeration type.
    private Status status;

    @Lob // Specifies that a field should be treated as a large object (BLOB or CLOB).
    private byte[] profilePicture;

    @Transient // Indicates that a field is not persistent and should not be mapped to a database column.
    private String temporaryData;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getTemporaryData() {
        return temporaryData;
    }

    public void setTemporaryData(String temporaryData) {
        this.temporaryData = temporaryData;
    }
}

enum Status {
    ACTIVE,
    INACTIVE
}
```

## Describe the Concept of Persistence Context in JPA and How Do You Configure a JPA Application Using persistence.xml?

**Persistence Context in JPA**

**Concept:**

- The persistence context in JPA is essentially a set of entity instances in which for any persistent entity identity there is a unique entity instance. The `EntityManager` maintains a context that manages the lifecycle and state of entities.
- It ensures that changes to entity instances are tracked and synchronized with the database.

**Types:**

1. **Transaction-Scoped Persistence Context:** Tied to a JTA transaction. The persistence context is created and associated with the current transaction and is closed when the transaction ends.
2. **Extended Persistence Context:** Used primarily in stateful session beans. The persistence context spans multiple transactions and remains open for the duration of the extended scope.

**Benefits:**

- **Change Tracking:** Automatically tracks changes made to entities and synchronizes them with the database upon transaction commit.
- **Identity Management:** Ensures that within a persistence context, there is only one instance of an entity for a specific database row.
- **Lazy Loading:** Supports lazy loading of relationships, optimizing performance by loading related entities on demand.

**Configuring a JPA Application Using persistence.xml**

The `persistence.xml` file is the configuration file for JPA applications. It defines the persistence units and their properties.

**Structure of persistence.xml:**

1. **Basic Structure:** The file is placed in the `META-INF` directory of the classpath. Contains one or more `<persistence-unit>` elements that define configurations for different persistence contexts.

2. **Example Configuration:**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence
                                 http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd"
             version="2.1">
    <persistence-unit name="myPersistenceUnit" transaction-type="RESOURCE_LOCAL">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <class>com.example.User</class>
        <properties>
            <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/mydb"/>
            <property name="javax.persistence.jdbc.user" value="root"/>
            <property name="javax.persistence.jdbc.password" value="password"/>
            <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>

            <property name="hibernate.dialect" value="org.hibernate.dialect.MySQL5Dialect"/>
            <property name="hibernate.hbm2ddl.auto" value="update"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
        </properties>
    </persistence-unit>
</persistence>
```

**Elements and Attributes:**

1. **\<persistence\>:** Root element.
2. **\<persistence-unit\>:** Represents a persistence unit with attributes such as `name` and `transaction-type` (either JTA or `RESOURCE_LOCAL`).
3. **\<provider\>:** Specifies the JPA provider (e.g., Hibernate).
4. **\<class\>:** Lists the entity classes managed by this persistence unit.
5. **\<properties\>:** Contains configuration properties:
   - `javax.persistence.jdbc.url`: Database connection URL.
   - `javax.persistence.jdbc.user`: Database username.
   - `javax.persistence.jdbc.password`: Database password.
   - `javax.persistence.jdbc.driver`: JDBC driver class.

Provider-specific properties, e.g., Hibernate:

- `hibernate.dialect`: Specifies the SQL dialect.
- `hibernate.hbm2ddl.auto`: Schema generation settings (e.g., `update`, `create`, `create-drop`, `validate`).
- `hibernate.show_sql`: Displays generated SQL statements.
- `hibernate.format_sql`: Formats SQL statements for readability.

## What Are the Different Types of Relationships in JPA, and How Are They Mapped Using Annotations?

In JPA, relationships between entities can be mapped using annotations to define how entities interact with each other in terms of database relationships. The primary types of relationships are:

- One-to-One
- One-to-Many
- Many-to-One
- Many-to-Many

**1. One-to-One Relationship**

A one-to-one relationship occurs when one entity is associated with exactly one instance of another entity.

Annotations Used: `@OneToOne`, `@JoinColumn`

```java
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "parking_space_id")
    private ParkingSpace parkingSpace;

    // getters and setters
}

@Entity
public class ParkingSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    @OneToOne(mappedBy = "parkingSpace")
    private Employee employee;

    // getters and setters
}
```

- `@OneToOne`: Defines a one-to-one relationship.
- `@JoinColumn`: Specifies the foreign key column.

**2. One-to-Many Relationship**

A one-to-many relationship occurs when one entity is associated with multiple instances of another entity.

Annotations Used: `@OneToMany`, `@JoinColumn`

```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    // getters and setters
}

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // getters and setters
}
```

- `@OneToMany`: Defines a one-to-many relationship.
- `@ManyToOne`: Defines the other side of the relationship.
- `@JoinColumn`: Specifies the foreign key column in the child entity.

**3. Many-to-One Relationship**

A many-to-one relationship occurs when multiple instances of one entity are associated with a single instance of another entity.

Annotations Used: `@ManyToOne`, `@JoinColumn`

```java
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // getters and setters
}

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    // getters and setters
}
```

- `@ManyToOne`: Defines a many-to-one relationship.
- `@JoinColumn`: Specifies the foreign key column.

**4. Many-to-Many Relationship**

A many-to-many relationship occurs when multiple instances of one entity are associated with multiple instances of another entity.

Annotations Used: `@ManyToMany`, `@JoinTable`, `@JoinColumn`

```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    // getters and setters
}

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    // getters and setters
}
```

- `@ManyToMany`: Defines a many-to-many relationship.
- `@JoinTable`: Specifies the join table that holds the foreign keys.
- `@JoinColumn`: Specifies the join columns in the join table.

## Explain the Role of the @Entity and @Table Annotations in JPA

**@Entity Annotation**

**Role:** The `@Entity` annotation specifies that the class is an entity and is mapped to a database table. It marks the class as a JPA entity, meaning it will be managed by the JPA provider (e.g., Hibernate).

**Usage:**

- It is placed at the class level.
- Every class annotated with `@Entity` must have a primary key field, which is annotated with `@Id`.

```java
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private Long id;
    private String name;
    private String email;

    // getters and setters
}
```

**@Table Annotation**

**Role:** The `@Table` annotation specifies the details of the table that the entity is mapped to. While `@Entity` indicates that a class is an entity, `@Table` allows you to customize the table mapping.

**Usage:**

- It is placed at the class level, typically in conjunction with the `@Entity` annotation.
- It is optional; if not used, the default table name is the unqualified name of the entity class.

**Attributes:**

- `name`: Specifies the name of the table. If not specified, the class name is used by default.
- `catalog`: Specifies the catalog of the table.
- `schema`: Specifies the schema of the table.
- `uniqueConstraints`: Specifies unique constraints that should be applied to the table.
- `indexes`: Specifies the indexes on the table.

```java
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users", schema = "public")
public class User {
    @Id
    private Long id;
    private String name;
    private String email;

    // getters and setters
}
```

## What Is JPQL, and How Does It Differ from SQL? Provide an Example Query

JPQL (Java Persistence Query Language) is a query language used to perform database queries on entities mapped to the database tables in a JPA application. It is similar to SQL but operates on entity objects rather than directly on database tables.

**Differences Between JPQL and SQL:**

1. **Object-Oriented Queries:** JPQL operates on entity objects and their relationships, allowing developers to write queries using entity names, attributes, and relationships directly. SQL operates on database tables and columns, requiring knowledge of the underlying database schema.
2. **Platform Independence:** JPQL queries are platform-independent, meaning they are written in terms of entity objects and are translated by the JPA provider (e.g., Hibernate, EclipseLink) into native SQL queries for the underlying database. SQL queries are specific to the database platform and may need to be rewritten when switching between different database vendors.
3. **Type Safety:** JPQL queries are type-safe, as they are validated against entity mappings at compile-time. SQL queries are strings passed directly to the database, potentially leading to runtime errors if there are syntax errors or mismatches between the query and the database schema.
4. **Support for Inheritance:** JPQL supports querying entities and their subclasses, including polymorphic queries. SQL does not directly support querying across inheritance hierarchies.

**Example Query:**

Suppose we have an entity class named `Product` mapped to a database table. We want to retrieve the names of all products with a price greater than 100.

```java
// JPQL:
Query query = entityManager.createQuery(
    "SELECT p.name FROM Product p WHERE p.price > :price");
query.setParameter("price", 100);
List<String> productNames = query.getResultList();
```

```sql
-- Equivalent SQL Query
SELECT name FROM Product WHERE price > 100;
```

## Describe How You Can Perform CRUD Operations Using JPA

CRUD operations (Create, Read, Update, Delete) can be performed in JPA using the `EntityManager` interface.

**1. Create (Persist) Operation**

To create a new entity and persist it to the database:

```java
EntityManager entityManager = entityManagerFactory.createEntityManager();
entityManager.getTransaction().begin();

User user = new User();
user.setName("John Doe");
user.setEmail("john@example.com");

entityManager.persist(user);

entityManager.getTransaction().commit();
entityManager.close();
```

**2. Read (Retrieve) Operation**

To retrieve an entity from the database:

```java
EntityManager entityManager = entityManagerFactory.createEntityManager();
User user = entityManager.find(User.class, userId);
entityManager.close();
```

**3. Update (Merge) Operation**

To update an existing entity:

```java
EntityManager entityManager = entityManagerFactory.createEntityManager();
entityManager.getTransaction().begin();

User user = entityManager.find(User.class, userId);
if (user != null) {
    user.setName("Jane Doe");
    entityManager.merge(user);
}

entityManager.getTransaction().commit();
entityManager.close();
```

**4. Delete (Remove) Operation**

To delete an existing entity:

```java
EntityManager entityManager = entityManagerFactory.createEntityManager();
entityManager.getTransaction().begin();

User user = entityManager.find(User.class, userId);
if (user != null) {
    entityManager.remove(user);
}

entityManager.getTransaction().commit();
entityManager.close();
```

## What Is the Purpose of the @OneToMany and @ManyToOne Annotations, and How Are They Used?

**@OneToMany Annotation**

**Purpose:** The `@OneToMany` annotation is used to define a one-to-many relationship between two entities. It indicates that one instance of the source entity (parent) can be associated with multiple instances of the target entity (child).

**Usage:**

- It is placed on the field or getter method in the source entity class.
- It is typically used in conjunction with the `mappedBy` attribute to specify the corresponding many-to-one relationship in the target entity.

```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    // getters and setters
}
```

**@ManyToOne Annotation**

**Purpose:** The `@ManyToOne` annotation is used to define a many-to-one relationship between two entities. It indicates that multiple instances of the source entity (child) can be associated with one instance of the target entity (parent).

**Usage:**

- It is placed on the field or getter method in the child entity class.
- It is typically used in conjunction with the `@JoinColumn` annotation to specify the foreign key column in the child table.

```java
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // getters and setters
}
```

## Explain How You Can Handle Transactions in JPA

In JPA, transactions are used to group multiple database operations into an atomic unit of work, ensuring data consistency and integrity.

**1. Obtain an EntityManager**

```java
EntityManager entityManager = entityManagerFactory.createEntityManager();
```

**2. Begin a Transaction**

Before performing any database operations, start a transaction using the `begin()` method of the `EntityTransaction` interface.

```java
EntityTransaction transaction = entityManager.getTransaction();
transaction.begin();
```

**3. Perform Database Operations**

```java
// Example: persisting a new entity
User user = new User();
user.setName("John Doe");
user.setEmail("john@example.com");

entityManager.persist(user);
```

**4. Commit the Transaction**

```java
transaction.commit();
```

**5. Rollback on Failure**

```java
transaction.rollback();
```

**6. Close the EntityManager**

```java
entityManager.close();
```

**Best Practices**

1. **Transaction Scope:** Keep transactions as short as possible to minimize the time the database locks resources.
2. **Exception Handling:** Use try-catch-finally blocks to handle exceptions and ensure proper transaction management.
3. **Rollback on Failure:** Always rollback the transaction if an error occurs to maintain data consistency.
4. **Closing EntityManager:** Close the `EntityManager` after each transaction to release database connections.
5. **Propagation:** Understand transaction propagation rules if you have nested transactional methods.

## What Are the Lifecycle Callbacks in JPA, and How Can You Use Them?

Entity lifecycle callbacks allow you to execute custom logic at various stages of an entity's lifecycle. These callbacks provide hooks to perform actions such as initialization, validation, auditing, or cleanup.

**Standard Lifecycle Callbacks:**

1. **@PrePersist:** Executed before an entity is inserted into the database. Typically used for initializing fields or performing validation checks.
2. **@PostPersist:** Executed after an entity is inserted into the database. Useful for triggering additional actions after persistence, such as sending notifications or updating related entities.
3. **@PreUpdate:** Executed before an entity is updated in the database. Often used for auditing purposes or to enforce business rules before updating.
4. **@PostUpdate:** Executed after an entity is updated in the database. Similar to `@PostPersist`, but specifically for update operations.
5. **@PreRemove:** Executed before an entity is removed from the database. Commonly used for cleanup tasks or for enforcing referential integrity constraints.
6. **@PostRemove:** Executed after an entity is removed from the database. Useful for cascading deletion actions or logging.

**Usage:**

To use lifecycle callbacks, you annotate the methods you want to be invoked with the appropriate lifecycle annotation. These methods should be defined in your entity class.

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;

    @PrePersist
    public void prePersist() {
        // Custom logic before persisting the entity
        System.out.println("Preparing to persist user: " + username);
    }

    @PostPersist
    public void postPersist() {
        // Custom logic after persisting the entity
        System.out.println("User persisted: " + username);
    }

    // Other entity methods, getters, and setters
}
```

In this example, the `prePersist()` method is annotated with `@PrePersist` and is called before the entity is persisted, and `postPersist()` is annotated with `@PostPersist` and is called after the entity is persisted. You can define any custom logic within these methods.

## Describe How You Can Implement Optimistic Locking in JPA

Optimistic locking in JPA is a concurrency control mechanism used to prevent concurrent updates to the same entity from causing data inconsistency. It works by allowing multiple transactions to read and potentially update the same entity concurrently, but it detects and prevents conflicts at the time of transaction commit.

**Implementation of Optimistic Locking in JPA:**

1. **Version Attribute:** To implement optimistic locking, add a version attribute to the entity class. This attribute is typically a numeric or timestamp field that is incremented or updated automatically by the persistence provider.
2. **@Version Annotation:** Annotate the version attribute with `@Version` to indicate that it serves as the version for optimistic locking.
3. **Handling Concurrent Updates:** When multiple transactions attempt to update the same entity concurrently, JPA checks the version attribute's value. If the version value has changed since the entity was loaded into memory, it indicates that another transaction has modified the entity concurrently. This situation is referred to as a version conflict.
4. **Detecting Version Conflict:** JPA detects the version conflict during transaction commit. If a conflict is detected, JPA throws an `OptimisticLockException`.

**Example Implementation:**

```java
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;

    @Version
    private int version; // Version attribute for optimistic locking

    // Getters and setters
}
```

## What Is the Purpose of the @NamedQuery Annotation, and How Is It Used?

The `@NamedQuery` annotation in JPA is used to define named queries at the entity level. Named queries are pre-defined JPQL queries that can be executed multiple times with different parameters. They are particularly useful for complex queries that are used frequently within an application.

**Purpose:**

1. **Encapsulation of Query Logic:** Named queries encapsulate complex JPQL queries within the entity class, promoting code organization and maintainability.
2. **Improved Performance:** Named queries are parsed and validated at application startup, reducing the overhead of query compilation and validation during runtime.
3. **Type Safety:** Named queries provide compile-time type checking of query strings, reducing the likelihood of syntax errors or query mismatches.

**Usage:**

To use `@NamedQuery`, you annotate a method in the entity class with `@NamedQueries`, which is then followed by one or more `@NamedQuery` annotations. Each `@NamedQuery` annotation defines a named query with a unique name and a JPQL query string.

**Example:**

Consider an entity class `User` with a named query to retrieve users by their email:

```java
@Entity
@NamedQueries({
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // Getters and setters
}
```

## How Can You Achieve Pagination in JPA?

Pagination in JPA allows you to retrieve a subset of query results, typically to display data in chunks or pages in a user interface. You can achieve pagination using the `setFirstResult()` and `setMaxResults()` methods of the `Query` interface or `CriteriaQuery`.

**Using JPQL:**

1. **Create a JPQL Query:** Construct a JPQL query to retrieve the desired data.
2. **Set Pagination Parameters:** Use the `setFirstResult()` method to specify the starting index of the result set. Use the `setMaxResults()` method to specify the maximum number of results to return.
3. **Execute the Query:** Execute the query to retrieve the paginated data.

```java
EntityManager entityManager = entityManagerFactory.createEntityManager();

String jpql = "SELECT u FROM User u ORDER BY u.id";
Query query = entityManager.createQuery(jpql);

int pageNumber = 1;
int pageSize = 10;

query.setFirstResult((pageNumber - 1) * pageSize);
query.setMaxResults(pageSize);

List<User> users = query.getResultList();
```

**Using Criteria API:**

1. **Create a CriteriaQuery:** Use the Criteria API to construct a `CriteriaQuery` object for the desired entity type.
2. **Apply Pagination Parameters:** Use the `setFirstResult()` method of the `CriteriaQuery` object to specify the starting index of the result set. Use the `setMaxResults()` method to specify the maximum number of results to return.
3. **Execute the Query:** Execute the query to retrieve the paginated data.

```java
EntityManager entityManager = entityManagerFactory.createEntityManager();
CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

Root<User> root = criteriaQuery.from(User.class);
criteriaQuery.select(root);

int pageNumber = 1;
int pageSize = 10;

criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);

typedQuery.setFirstResult((pageNumber - 1) * pageSize);
typedQuery.setMaxResults(pageSize);

List<User> users = typedQuery.getResultList();
```

## Explain the Difference Between Lazy and Eager Fetching in JPA

Lazy and eager fetching are strategies used to define how related entities are loaded from the database when querying for an entity. These strategies determine whether associated entities are loaded immediately along with the parent entity or deferred until explicitly accessed.

**Lazy Fetching:**

- **Purpose:** Lazy fetching loads associated entities only when they are explicitly accessed by the application code.
- **Deferred Loading:** Associated entities are not loaded when the parent entity is fetched from the database.
- **Reduced Overhead:** Lazy fetching reduces the amount of data retrieved from the database, improving performance and reducing memory consumption.
- **Suitable for Large Associations:** Lazy fetching is suitable for associations with many related entities or when the associated data is not always needed.
- **FetchType.LAZY:** In JPA, lazy fetching is typically configured using the `FetchType.LAZY` annotation attribute.

**Eager Fetching:**

- **Purpose:** Eager fetching loads associated entities immediately along with the parent entity during the initial fetch operation.
- **Immediate Loading:** Associated entities are loaded along with the parent entity, resulting in a single database query.
- **Increased Overhead:** Eager fetching may result in loading unnecessary data, especially for large associations, leading to performance overhead.
- **Suitable for Small Associations:** Eager fetching is suitable for associations with a small number of related entities or when the associated data is frequently accessed.
- **FetchType.EAGER:** In JPA, eager fetching is typically configured using the `FetchType.EAGER` annotation attribute.

**Example:**

Consider a `Department` entity with a one-to-many association to `Employee` entities:

```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Employee> employees;

    // Getters and setters
}
```

In this example, the `employees` association is configured with lazy fetching (`FetchType.LAZY`), meaning that when a `Department` entity is fetched, its associated `Employee` entities are not loaded immediately. If the application accesses the `employees` collection, the associated `Employee` entities will be loaded from the database at that time.

```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    private List<Employee> employees;

    // Getters and setters
}
```

In this example, the `employees` association is configured with eager fetching (`FetchType.EAGER`), meaning that when a `Department` entity is fetched, its associated `Employee` entities are loaded immediately along with the parent entity.

---

# JDBC, JPA and Hibernate — Under the Hood

*JDBC, JPA and Hibernate*

Understanding the architecture from your code to the database.

## The Big Picture

When you write `userRepository.findById(1)`, what actually happens? Your code travels through multiple layers before reaching the database: Your Code → Spring Data JPA → JPA API → Hibernate → JDBC → Database Driver → Database. Understanding these layers helps you write better queries, debug performance issues, and make informed architectural decisions.

**Why So Many Layers?**

- **JDBC** — The foundation. Direct database access, but verbose and error-prone.
- **JPA** — A specification (interface). Defines how ORM should work, but has no implementation.
- **Hibernate** — The most popular JPA implementation. Does the actual work.
- **Spring Data JPA** — Reduces boilerplate further. Generates implementations from interface methods.

**The Persistence Stack (layer by layer)**

1. **Your Application Code** (`userRepository.findById(1)`) — Entry point.
2. **Spring Data JPA** — Generates query from method name.
3. **JPA (Jakarta Persistence API)** (`EntityManager.find(User.class, 1)`) — Specification.
4. **Hibernate** — Session, first-level cache, dirty checking — Implementation.
5. **JDBC** — Connection, PreparedStatement, ResultSet — Foundation.
6. **Database Driver** — MySQL Connector/J, PostgreSQL JDBC, etc. — Driver.
7. **Database** — MySQL, PostgreSQL, Oracle, etc. — Storage.

Each layer adds abstraction and functionality. Understanding what happens at each level helps you debug issues and optimize performance.

## JDBC: The Foundation

JDBC (Java Database Connectivity) is the lowest-level API for database access in Java. Everything else is built on top of it. Even when you use JPA, Hibernate eventually calls JDBC to execute SQL.

**Core JDBC Components**

- **DriverManager** — Manages database drivers and creates connections.
- **Connection** — A session with the database. Expensive to create.
- **Statement / PreparedStatement** — Executes SQL. `PreparedStatement` prevents SQL injection.
- **ResultSet** — Holds query results. Cursor-based iteration.

```java
// Raw JDBC - verbose but you see exactly what happens
String sql = "SELECT id, name, email FROM users WHERE id = ?";

try (Connection conn = dataSource.getConnection();
     PreparedStatement stmt = conn.prepareStatement(sql)) {

    stmt.setLong(1, userId);  // Set parameter (1-indexed)

    try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    }
}
// Connection, Statement, ResultSet all auto-closed
```

**Why We Moved Beyond JDBC**

- Manual mapping of `ResultSet` to objects (boilerplate).
- No caching — every query hits the database.
- No automatic dirty checking — manual UPDATE statements.
- No relationship management — manual JOINs and foreign keys.
- SQL is database-specific (MySQL vs PostgreSQL differences).

## JPA: The Specification

JPA (Jakarta Persistence API, formerly Java Persistence API) is a specification, not an implementation. It defines interfaces and annotations that ORM frameworks must implement.

**Why a Specification Matters**

Before JPA, you were locked into your ORM vendor. If you used Hibernate, your code was full of Hibernate-specific APIs. Switching to EclipseLink meant rewriting everything. JPA provides a standard API so you can switch implementations without changing your code.

Before JPA (Vendor Lock-in):

```java
// Hibernate-specific code
Session session = sessionFactory.openSession();
User user = session.get(User.class, 1L);
session.close();

// TopLink-specific code (different API)
UnitOfWork uow = session.acquireUnitOfWork();
User user = (User) uow.readObject(User.class);
```

With JPA (Portable):

```java
// Works with ANY JPA implementation
EntityManager em = emf.createEntityManager();
User user = em.find(User.class, 1L);
em.close();

// Switch from Hibernate to EclipseLink?
// Just change the dependency. Code stays the same.
```

**Key JPA Concepts**

- **Entity** — A Java class mapped to a database table (`@Entity`).
- **EntityManager** — The main API for CRUD operations.
- **Persistence Context** — A set of managed entities (first-level cache).
- **JPQL** — Java Persistence Query Language (object-oriented SQL).
- **EntityManagerFactory** — Creates `EntityManager`s (expensive, create once).

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orders;
}

// Using EntityManager
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();

User user = em.find(User.class, 1L);  // SELECT from database
user.setName("New Name");             // No SQL yet - just marks dirty
// Hibernate tracks changes automatically

em.getTransaction().commit();  // Now: UPDATE users SET name = ? WHERE id = ?
em.close();
```

## Hibernate: The Implementation

Hibernate is the most popular JPA implementation. It implements all JPA interfaces and adds powerful features on top.

**What Hibernate Adds Beyond JPA**

| Feature | JPA Standard | Hibernate Extra |
|---|---|---|
| Caching | First-level cache only | Second-level cache (EHCache, Infinispan) |
| Batch Operations | Basic | `@BatchSize`, `StatelessSession` |
| Fetching | LAZY/EAGER | `@Fetch` strategies, entity graphs |
| Query Language | JPQL | HQL (superset), Criteria API extensions |
| ID Generation | Basic strategies | Custom generators, pooled optimizers |

**Hibernate Session vs JPA EntityManager**

```java
// JPA standard way
EntityManager em = emf.createEntityManager();
User user = em.find(User.class, 1L);

// Hibernate native way (same thing internally)
Session session = sessionFactory.openSession();
User user = session.get(User.class, 1L);

// Getting Hibernate Session from JPA EntityManager
Session session = em.unwrap(Session.class);
```

**Which API Should You Use?**

Use JPA APIs (`EntityManager`) for portability. Only use Hibernate-specific APIs (`Session`) when you need features not in JPA, like `StatelessSession` for bulk operations or specific caching configurations.

## Connection Pooling Deep Dive

Creating a database connection is expensive: TCP handshake, authentication, session setup. This takes 50-200ms. Without pooling, a request taking 5ms to execute spends 95% of its time just getting a connection.

**HikariCP: The Default Choice**

Spring Boot uses HikariCP by default. It's the fastest connection pool available.

```properties
# application.properties - HikariCP configuration
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.idle-timeout=300000
spring.datasource.hikari.connection-timeout=20000
spring.datasource.hikari.max-lifetime=1200000

# Pool sizing formula (from HikariCP wiki):
# connections = ((core_count * 2) + effective_spindle_count)
# For SSD with 4 cores: (4 * 2) + 1 = 9 connections
```

**Connection Pool Sizing**

More connections is NOT better. Too many connections cause:

- Database CPU contention (context switching).
- Memory pressure on database server.
- Lock contention and deadlocks.

Start with 10 connections and measure before increasing. Most apps need fewer than you think.

## The N+1 Query Problem

The N+1 problem occurs when fetching a collection of entities, then accessing a lazy-loaded relationship on each one. You get 1 query for the parent + N queries for each child.

```java
// Entity with lazy-loaded orders
@Entity
public class User {
    @Id private Long id;
    private String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orders;  // LAZY by default for collections
}

// N+1 Problem - DON'T DO THIS
List<User> users = userRepository.findAll();  // 1 query: SELECT * FROM users
for (User user : users) {
    user.getOrders().size();  // N queries: SELECT * FROM orders WHERE user_id = ?
}
// Total: 1 + N queries (if 100 users = 101 queries!)
```

**Solutions**

**1. JOIN FETCH (Best for known access patterns)**

```java
@Query("SELECT u FROM User u JOIN FETCH u.orders")
List<User> findAllWithOrders();
// 1 query: SELECT u.*, o.* FROM users u JOIN orders o ON u.id = o.user_id
```

**2. @EntityGraph (Declarative)**

```java
@EntityGraph(attributePaths = {"orders"})
List<User> findAll();
```

**3. @BatchSize (Hibernate-specific)**

```java
@OneToMany(mappedBy = "user")
@BatchSize(size = 25)  // Loads 25 users' orders in one query
private List<Order> orders;
// Instead of N queries, uses N/25 queries with IN clause
```

**Detecting N+1 in Development**

Enable SQL logging to see the problem:

- `spring.jpa.show-sql=true` (basic)
- `logging.level.org.hibernate.SQL=DEBUG` (SQL only)
- `logging.level.org.hibernate.orm.jdbc.bind=TRACE` (with parameters)

## Key Takeaways

- JDBC is the foundation — all ORM frameworks use it underneath.
- JPA is a specification (interfaces), Hibernate is an implementation.
- Use JPA APIs for portability, Hibernate APIs only when needed.
- Connection pooling is essential — creating connections is expensive (50-200ms).
- HikariCP is the default and fastest pool, start with 10 connections.
- N+1 is the most common JPA performance bug — use JOIN FETCH or `@BatchSize`.
- Always enable SQL logging in development to catch N+1 early.

---

# JPA and Hibernate in Action

*JDBC, JPA and Hibernate*

6 essential patterns visualized step by step.

**Patterns Covered**

- **Entity Lifecycle** — Transient, Managed, Detached, Removed states.
- **Lazy vs Eager Loading** — When SQL actually fires.
- **Dirty Checking** — How Hibernate detects changes automatically.
- **Optimistic Locking** — Handling concurrent updates with `@Version`.
- **Batch Operations** — Performance comparison single vs batch.
- **Query Types** — JPQL vs Criteria vs Native SQL.

## Pattern 1: Entity Lifecycle

Every JPA entity exists in one of four states. Understanding these states tells you when changes sync to the database.

The state machine: `Transient` (`new User()`) → `Managed` (`em.persist()` / `em.find()`) → `Detached` (`em.detach()`) → back to `Managed` via `em.merge()`, or `Managed` → `Removed` (`em.remove()`).

| State | In Context | Tracked | Synced to DB |
|---|---|---|---|
| Transient | No | No | No |
| Managed | Yes | Yes | On flush |
| Detached | No | No | No |
| Removed | Yes | Yes | DELETE on flush |

**Common Mistake**

Modifying a detached entity and expecting it to save. You must call `em.merge(entity)` to re-attach it.

## Pattern 2: Lazy vs Eager Loading

LAZY loading defers SQL until you access the relationship. This prevents loading unnecessary data but can cause N+1 problems.

```java
@OneToMany(fetch = FetchType.LAZY)  // Default for collections
private List<Order> orders;

// When you need eager loading for a specific query:
@Query("SELECT u FROM User u JOIN FETCH u.orders")
List<User> findAllWithOrders();
```

**Best Practice**

Keep everything LAZY. Use `JOIN FETCH` or `@EntityGraph` when you need relations loaded.

## Pattern 3: Dirty Checking

Hibernate automatically detects changes to managed entities. No `save()` call needed — just modify and commit.

```java
@Transactional
public void updateName(Long id, String newName) {
    User user = repository.findById(id).orElseThrow();
    user.setName(newName);  // That's it! No save() needed
    // UPDATE generated automatically at commit
}
```

## Pattern 4: Optimistic Locking

Prevents lost updates when two users edit the same record. Uses a version column checked at update time.

```java
@Entity
public class Product {
    @Version
    private Integer version;  // Hibernate manages this
}

// Generated SQL:
// UPDATE products SET price=?, version=2 WHERE id=1 AND version=1
// If 0 rows updated -> OptimisticLockException
```

## Pattern 5: Batch Operations

Batching groups multiple INSERTs into fewer database calls, dramatically improving performance.

```properties
# application.properties
spring.jpa.properties.hibernate.jdbc.batch_size=50
spring.jpa.properties.hibernate.order_inserts=true
```

```java
// In code: flush periodically to avoid memory issues
for (int i = 0; i < 1000; i++) {
    em.persist(new User("User" + i));
    if (i % 50 == 0) {
        em.flush();
        em.clear();  // Clear persistence context
    }
}
```

---

# JPA and Hibernate Best Practices

*JDBC, JPA and Hibernate*

15 common pitfalls and how to avoid them.

## Why Best Practices Matter

JPA makes database access easy, but that simplicity hides complexity. A missing `@Transactional` silently reads stale data. An EAGER fetch multiplies your queries. A forgotten `flush()` causes `OutOfMemoryError` on batch inserts. These mistakes compile and often appear to work until production load exposes them.

## 1. Entity Design Best Practices

**1. Never Use EAGER on Collections**

EAGER loading on `@OneToMany` or `@ManyToMany` multiplies with other EAGER relations and loads data you may not need.

```java
// BAD: EAGER on Collection
@OneToMany(fetch = FetchType.EAGER)
private List<Order> orders;
// Every time you load a User, ALL orders load too — even if you just need the name!
```

```java
// GOOD: LAZY + Explicit Fetch
@OneToMany(fetch = FetchType.LAZY)
private List<Order> orders;

// When you need orders:
@Query("SELECT u FROM User u JOIN FETCH u.orders WHERE u.id = :id")
User findWithOrders(@Param("id") Long id);
```

**2. Use @Version for Optimistic Locking**

Without versioning, concurrent updates silently overwrite each other. Add `@Version` to detect conflicts.

```java
// BAD: No Version Field
@Entity
public class Product {
    @Id
    private Long id;
    private String name;
    private BigDecimal price;
    // Last write wins - data loss!
}
```

```java
// GOOD: Version Field Added
@Entity
public class Product {
    @Id
    private Long id;

    @Version
    private Integer version;

    private String name;
    private BigDecimal price;
}
```

**3. Override equals() and hashCode() Correctly**

Use business key or ID. Never use generated ID alone if entity might be in a `Set` before persisting.

```java
// BAD: Uses Generated ID
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;
    User user = (User) o;
    return id != null && id.equals(user.id);
}
// Problem: id is null before persist()
// Two new Users in Set = duplicates!
```

```java
// GOOD: Uses Business Key
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;
    User user = (User) o;
    return email != null &&
           email.equals(user.email);
}
// email is set before persist
// Works correctly in Sets
```

## 2. Query Best Practices

**4. Fix N+1 with JOIN FETCH or @BatchSize**

The N+1 problem is the most common JPA performance bug. Always check SQL logs in development.

```java
// BAD: N+1 Queries
List<User> users = userRepo.findAll();
for (User u : users) {
    u.getOrders().size(); // N queries!
}
// 1 + N queries total; 100 users = 101 queries
```

```java
// GOOD: JOIN FETCH
@Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.orders")
List<User> findAllWithOrders();
// 1 query total; all orders loaded with users
```

**5. Use DTOs for Read-Only Queries**

Loading full entities for display wastes memory and enables accidental modifications. Use projections.

```java
// BAD: Full Entity for Display
// Loads entire User entity, tracks in persistence context, wastes memory for read-only use
List<User> users = userRepo.findAll();
return users.stream()
    .map(u -> u.getName())
    .collect(toList());
```

```java
// GOOD: DTO Projection
// Only fetches what you need, not tracked in persistence context
@Query("SELECT new com.example.UserDTO(u.id, u.name, u.email) FROM User u")
List<UserDTO> findAllAsDTO();
```

**6. Avoid SELECT \* with Native Queries**

Explicit column selection prevents loading unnecessary data and makes schema changes safer.

```java
// BAD: SELECT *
@Query(value = "SELECT * FROM users WHERE status = 'ACTIVE'",
       nativeQuery = true)
List<User> findActive();
// Loads all columns; breaks if schema changes
```

```java
// GOOD: Explicit Columns
@Query(value = "SELECT id, name, email FROM users WHERE status = 'ACTIVE'",
       nativeQuery = true)
List<Object[]> findActive();
// Or use JPQL for type safety
```

## 3. Transaction Best Practices

**7. Always Use @Transactional for Write Operations**

Without a transaction, each repository call is a separate transaction. Changes may not be atomic.

```java
// BAD: No Transaction
public void transferMoney(Long from, Long to, BigDecimal amount) {
    accountRepo.debit(from, amount);
    // If this fails, debit already committed!
    accountRepo.credit(to, amount);
}
```

```java
// GOOD: Explicit Transaction
@Transactional
public void transferMoney(Long from, Long to, BigDecimal amount) {
    accountRepo.debit(from, amount);
    accountRepo.credit(to, amount);
    // Both succeed or both rollback
}
```

**8. Use Read-Only Transactions for Queries**

`readOnly = true` hints to Hibernate to skip dirty checking, improving performance.

```java
// BAD: Write Transaction for Read
@Transactional
public List<User> getAllUsers() {
    return userRepo.findAll();
}
// Hibernate tracks all entities, checks for changes at commit — wasted CPU cycles
```

```java
// GOOD: Read-Only Transaction
@Transactional(readOnly = true)
public List<User> getAllUsers() {
    return userRepo.findAll();
}
// Hibernate skips dirty checking — better performance
```

**9. Keep Transactions Short**

Long transactions hold database locks, reducing concurrency and risking timeouts.

```java
// BAD: External Call in Transaction
@Transactional
public void processOrder(Order order) {
    orderRepo.save(order);
    paymentService.charge(order); // HTTP call!
    // Holds DB connection during network I/O
    // Other requests wait for connection
}
```

```java
// GOOD: External Call Outside
public void processOrder(Order order) {
    saveOrder(order);  // Transaction 1
    paymentService.charge(order); // No transaction
    markPaid(order);   // Transaction 2
}

@Transactional
void saveOrder(Order order) { ... }
```

## 4. Performance Best Practices

**10. Use Batch Inserts with flush/clear**

Without periodic flush/clear, all entities accumulate in memory causing `OutOfMemoryError`.

```java
// BAD: No Flush/Clear
for (int i = 0; i < 100000; i++) {
    em.persist(new User("User" + i));
}
// 100,000 entities in memory - OutOfMemoryError likely
```

```java
// GOOD: Periodic Flush/Clear
for (int i = 0; i < 100000; i++) {
    em.persist(new User("User" + i));
    if (i % 50 == 0) {
        em.flush();  // Send to DB
        em.clear();  // Free memory
    }
}
```

**11. Configure Connection Pool Properly**

Too few connections cause request queuing. Too many overwhelm the database.

```properties
# application.properties - HikariCP (Spring Boot default)
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.idle-timeout=300000
spring.datasource.hikari.connection-timeout=20000

# Pool sizing formula:
# connections = (core_count * 2) + spindle_count
# For 4-core server with SSD: (4 * 2) + 1 = 9
```

**12. Enable SQL Logging in Development**

You cannot optimize what you cannot see. Always log SQL in development to catch N+1.

```properties
# application-dev.properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Better: Use logging (can set level)
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.orm.jdbc.bind=TRACE  # Shows parameters
```

## 5. Common Pitfalls to Avoid

**13. Handle LazyInitializationException Properly**

This exception means you accessed a lazy relation after the persistence context closed.

```java
// BAD: Access Outside Transaction
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) {
    User user = userService.findById(id);
    user.getOrders().size(); // BOOM!
    // Transaction ended in service
    // LazyInitializationException
}
```

```java
// GOOD: Fetch in Service Layer
// Service
@Transactional(readOnly = true)
public User findByIdWithOrders(Long id) {
    return userRepo.findByIdWithOrders(id);
}

// Repository
@Query("SELECT u FROM User u JOIN FETCH u.orders WHERE u.id = :id")
User findByIdWithOrders(@Param("id") Long id);
```

**14. Avoid Modifying Detached Entities Without Merge**

Changes to detached entities are not persisted. Use `merge()` to re-attach.

```java
// BAD: Modify Detached Entity
User user = userService.findById(id);
// Transaction ended, user is detached
user.setName("New Name");
// Changes NOT saved! User is detached from persistence context
```

```java
// GOOD: Merge or New Transaction
// Option 1: Merge
User detached = getDetachedUser();
detached.setName("New Name");
User managed = em.merge(detached);

// Option 2: Load and modify in transaction
@Transactional
public void updateName(Long id, String name) {
    User user = userRepo.findById(id).get();
    user.setName(name); // Auto-saved
}
```

**15. Use Spring Data Repository Methods Correctly**

`save()` on an existing entity does an unnecessary SELECT. Use direct updates for performance.

```java
// BAD: save() for Update
User user = new User();
user.setId(existingId);  // Set existing ID
user.setName("New Name");
userRepo.save(user);
// Does SELECT first to check if exists, then UPDATE - 2 queries!
```

```java
// GOOD: Direct Update Query
@Modifying
@Query("UPDATE User u SET u.name = :name WHERE u.id = :id")
int updateName(@Param("id") Long id, @Param("name") String name);
// Single UPDATE query, returns affected row count
```

## Quick Reference Checklist

Before every code review:

- All `@OneToMany` and `@ManyToMany` use `FetchType.LAZY`?
- Entities that need concurrent editing have `@Version`?
- Write operations wrapped in `@Transactional`?
- Read-only operations use `@Transactional(readOnly = true)`?
- No HTTP/external calls inside transactions?
- N+1 checked with SQL logging?
- Batch operations use `flush()`/`clear()` pattern?
- DTOs used for read-only queries instead of full entities?
- No lazy access outside transaction boundaries?

## Key Takeaways

- Keep everything LAZY, use JOIN FETCH when needed.
- Always enable SQL logging in development.
- Use `@Transactional` for writes, `readOnly=true` for reads.
- Keep transactions short — no external calls inside.
- Use DTOs for read-only queries to avoid tracking overhead.
- Add `@Version` to entities that might be concurrently edited.
- For batch operations, flush/clear every 50-100 entities.
