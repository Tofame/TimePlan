# 📅 Timetable Management System

A **Java Spring Boot** web application for managing and displaying timetables (activities as in lectures/lessons). This project demonstrates my learning curve of when I first time used REST API development && Spring Framework, deepened knowledge about MVC architecture, and basic frontend.

---

## 🔑 Features

- 🔐 **User Authentication**
  - Login page (`login.html`)
  - Uses generated jwt token for authorization

- 🗓️ **Timetable Display**
  - View personalized timetables (`timetable.html`)
  - Data fetched from REST API

- 🛠️ **Admin Panel**
  - Admin panel (`adminpanel.html`)
  - CRUD strategy - manage all data from database

---

## 🧰 Tech Stack

- **Backend:** Java 17+, Spring Boot
- **Frontend:** HTML5, CSS3
- **API:** RESTful web services
- **Database:** *H2 database*
- **Build Tool:** Maven

---

## 🖥️ Running

- Intellij's maven can generate .jar with 'package' option
- Otherwise the ``compile.bat``, uses spring boot command that also generates .jar
- Afterward using ``run.bat`` runs that .jar and that's all.
- The sites you can access when its running: /adminpanel.html, /login.html, /timetable.html (redirected to it from login)
---
