# Football Training & Team Planner

This desktop application, developed in Java, serves football coaches for efficient planning of training sessions and management of the team roster. The application allows users to build a training plan from a database of exercises to perfectly fit a specified time limit, while also providing an overview of the current squad.

---

## Application Controls and Features

* **Main overview (Dashboard):** The main window is divided into two halves. The left side shows the currently built training plan and remaining time. The right side displays the current player roster.
* **Adding to training:** The `Open Exercise Database` button opens the exercise catalog. After selecting an exercise and clicking `Add to Training`, the item is moved to the training plan.
* **Creating custom data:**
  * **New exercise:** In the database window, you can create a custom exercise (name, duration, category) using the `Create New Exercise` button.
  * **New player:** In the main window, you can add a new team member (first name, last name, age) using the `Add Player` button.
* **Removing items (Double-click):** For quick editing, simply **double-click** on any exercise in the training plan, which instantly deletes the given item and frees up minutes in the training session.

---

## Basic Mechanics and Logic

* **Training capacity monitoring:** If the user attempts to add an exercise whose duration would exceed the set training limit, the application blocks the addition and displays a warning.
* **Data saving (JSON):** All newly created exercises and added players are not lost after closing the application. Data is automatically saved to the `exercises.json` and `players.json` files in a human-readable format.
* **Input security:** The application is protected against crashes caused by invalid or incorrect inputs.

---

## Technologies and Frameworks Used

* **Language:** Java
* **Graphical Interface:** Java Swing
* **Data Format:** JSON
* **Library:** [Google Gson](https://github.com/google/gson)
