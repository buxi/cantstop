Project Can't Stop
==================

This project aims to implement the Can't Stop Boardgame.

More info regarding to the board game: http://en.wikipedia.org/wiki/Can%27t_Stop_(board_game) 

Online version
--------------
http://cantstop-refs.rhcloud.com/CantStop/

Architecture
------------
The application is a full J2EE Multi-tier application. It supports i18n, lokalised for English and German. The architecture follows SOA (Service Oriented Architecture) principals. Test driven development during the implementation of the model, supported with JUnit and Mockito unittests.

Spring MVC supports the MVC architecture as follows:

Business Logic (Model): POJOs (Plain Old Java Objects)
Controller: Servlets, Spring MVC
View: JSP, HTML, CSS, (AJAX, jQuery)

Technology Overview
-------------------
Java 7, Spring 4.0, Spring MVC, TDD, Junit 4, Mockito, log4j, Eclipse, GIT, AJAX/jQuery, JSP/JSTL, Tomcat 7, JSON, Servlets, SOA

Design patterns
---------------
MVC, Factory, Singleton, Transfer Object, Composite View, Front Controller, Decorator
