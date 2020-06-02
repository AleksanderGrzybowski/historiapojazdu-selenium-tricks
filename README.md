# historiapojazdu.gov.pl automatic vehicle checks

This project is a funny "cheat" on Polish Government website for checking vehicle history. It requires user to put plate number, VIN and registration date. However, registration date is often omitted by sellers on Polish car-selling websites. So this gizmo checks every possible date :)

To run, edit the configuration in main class, then `./gradlew run` and watch the logs. The program will exit once it finds the correct registration date.

Tech used:
* Java
* Selenium (via Selenide)

