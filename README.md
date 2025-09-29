# Queue Management Simulation App 🖥️

Welcome to the **Queue Management Simulation App**, a desktop application developed with **Java Swing**.
Its purpose is to simulate a queue management system for a set of clients and servers, allowing users to experiment with different scheduling strategies and observe real-time simulation logs in a graphical interface.

---

# Description 📖

Upon opening the app, users are greeted with a GUI that allows them to:

* **Set Simulation Parameters**: Number of clients, number of queues, simulation interval, and client arrival/service time ranges.
* **Start Simulation**: Begin the simulation with the chosen parameters.
* **View Real-Time Logs**: Monitor client arrivals, queue assignments, and service completions in a live log area.
* **Run Test Cases**: Optionally execute predefined test simulations for evaluation purposes.

Simulation behavior is managed by the **SimulationManager**, which handles client dispatching and queue management using the selected scheduling policy.

---

# Features 🪄

* **Simulation Parameter Input**: Customize number of clients, queues, simulation time, and service/arrival ranges.
* **Real-Time Logging**: Live updates displayed in a scrollable text area within the GUI.
* **Scheduling Strategies**: Supports policies like **Shortest Time** for client assignment.
* **Multithreaded Simulation**: Each simulation runs in a separate thread, ensuring responsive GUI.
* **Test Cases**: Predefined test simulations with different client and queue configurations.
* **User-Friendly Interface**: Simple, intuitive layout using **Java Swing**.

---

# Tech Stack 🛠

### Back-End:

* Java 17+
* Multithreading
* Scheduling Algorithms
* Object-Oriented Programming

### Front-End:

* Java Swing (GUI)

### Tools:

* IntelliJ IDEA (IDE)
* Maven (dependency management)

---

# Installation ⚙️

## Prerequisites:

* Java 17 or newer
* Maven
* IntelliJ IDEA or any Java IDE

## Steps:

1. Clone this repository:

```bash
git clone https://github.com/Ciprian-Popescu-03/Queue-Management-Application-with-Multithreading.git
```

2. Open the project in **IntelliJ IDEA**.
3. Build the project using **Maven**.
4. Run the main GUI class (`SimulationGUI`) to start the application.
