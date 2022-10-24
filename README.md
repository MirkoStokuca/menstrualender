# 1) Einleitung, Ziele, Randbedingungen
## 1.1) Einleitung
Wenn sie eine einfacheren Weg suchen ihre Menstruationsdaten aufzuschreiben dann werden wir das perfekte Tool für sie erfinden. Einen Online Kalender der ihnen basierend auf früeheren Ergebnissen eine Vorhersage für ihren nächsten Zyklus machen wird.
## 1.2) Ziele
Das Ziel wird es sein ein Tool zu schaffen, das einfach von Gebrauch ist, aber das Ihnen auch im Alltag helfen. Keine Probleme mit dem vergessen von den vorherigen Zyklen. Andererseits auch ein Tool das ihre Daten geschützt behaltet und sie nach einer vorgegebenen Zeit löscht. 
## 1.3) Randbedingungen
Die Programmierung der Anwendung erfolgt in Java.
Das Projekt ist auf GitHub öffentlich einsehbar.
Das Projekt wurde getestet.
Das Projekt wurde mit dem Git-Workflow Vincent Driessen erstellt.
Die Build-Automatisierung erfolgt mittels Gradle.
Das Projekt wurde mit der SCRUM Methode durchgeführt. Dies beinhaltet Userstories und einen Releaseplan.
---
# 2) Build-Anleitung (checkout, mvn ...., java -jar xxxxx.jar)
1) Install Java
2) Set java environment variables
   1) It’s recommended to set an environment variable called JAVA_HOME that stores the path to where Java is installed on your machine.
   2) For Mac users: <pre><code>export JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk-18.jdk/Contents/Home"</code></pre>
3) Verify Java is installed:
   <pre><code>java -version</code></pre>
4) Install Maven
5) Set Maven environment variables
   1) Add the executable for Maven to your PATH variable so that your machine can execute Maven commands successfully.
   2) For Mac users:
   <pre><code>export MAVEN_ROOT="$HOME/path/to/maven/apache-maven-3.8.5"
   export PATH="$MAVEN_ROOT/bin:$PATH"</code></pre>
6) Verify Maven is installed
   1) For Mac users: <pre><code>mvn --version</code></pre>
7) Create a new directory and change into it
8) Generate project using an archetype
   <pre><code>mvn archetype:generate -DgroupId=ch.trinat.gauss -DartifactId=menstrualender -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false</code></pre>
9) Specify source and target of Java that is to be used. To do this, add the following properties to the pom.xml file: <pre><code>\<properties>
   <maven.compiler.source>18</maven.compiler.source>
   <maven.compiler.target>18</maven.compiler.target>
\</properties></code></pre>
10) To build and test the app, run the following command from the project root directory: <pre><code>mvn clean package</code></pre>
11) Run the app, by running the following command from the project root directory: <pre><code>java -cp target/classes ch.trinat.gauss.menstrualender</code></pre>
---
# 3) Kurze Bedienungsanleitung
---
# 4) User Stories inkl. Akzeptanzkriterium, Aufwandschätzung in Story-Points und Priorisierung
|User Stories|Namen|Beschreibung|Priorität|Story Points|Akzeptanzkriterien|
|----------|----------|----------|----------|----------|----------|
|1|Eingabe Datum|Als Frau möchte ich das Datum meiner letzten Menstruation eingeben können um zu festzuhalten, wann mein Zyklus begonnen hat.|1|3|Das Datum der letzten Menstruation kann eingeben werden.|
|2|Daten Speichern|Als Frau möchte ich, dass meine Daten gespeichert werden, ohne dass ich etwas machen muss dafür.|1|8|Die eingebenen Daten werde abgespeichert und auch sind nach erneutem schliessen/öffnen des Programms noch gespeichert.|
|3|Gespeicherte Daten aufrufen|Als Frau möchte ich, dass meine bisherigen Daten abrufbar sind|1|8|Die Daten können bis maximal ein Jahr in die Vergangenheit angezeigt werden.|
|4|Vorhersage nähster Zyklus|Als Frau will ich eine Vorhersage erhalten für den Anfang meiner nächsten Blutung, um meine Gesundheit überwachen zu können.|2|5|Es wird eine Vorhersage bezüglich des nächsten Blutung ausgegeben.|
|5|Zyklusdauer ermitteln|Als Frau möchte ich wissen wie lange mein Zyklus gedauert hat, um meine Gesundheit überwachen zu können.|2|5|Die Zyklusdauer wird ausgeben und dynamisch an den Benutzer, nach einer Vorlaufphase, angepasst.|
|6|Durchschnittliche Zyklusdauer ermitteln|Als Frau möchte ich wissen wie regelmässig meine Zyklen in den letzten 12 Monaten waren, um meine Gesundheit überwachen zu können.|2|5|Die durschnittliche Zyklusdauer kann berechnet werden und wird dem User angezeigt.|
|7|Blutungszeit Vorhersagung|Als Frau möchte ich, dass mir das Programm die länge meiner Blutung ermittelt um meine Gesundheit überprüfen zu können.|3|5|Die Blutungszeit kann geschätzt und ausgeben werden.|
|8|Fruchtbare Zeit ermitteln|Als Paar möchten wir Wissen wann wir fruchtbar sind um schwanger zu werden oder um zu verhüten.|3|8|Die Zeit, in welcher die Frau Fruchtbar ist, kann ermittelt werden.|
|9|Schwangerschafts/Verhütungsmodus|Als Frau will ich die Resultate entweder zum verhüten oder schwanger werden dargestellt bekommen, damit ich keine Fehler mache.|3|8|Eingabemöglichkeit ob ein Kind erwünscht wird oder nicht. Die Ausgabe wird dementsprechend angepasst.|
|10|Passwortschutz|Als Frau möchte ich meine sensiblen Daten mit Passwörtern schützen können, um meine Privatsphäre zu beschützen|1|13|Daten werden in einem Schreibgeschützten File abgesichert und sind ohne passwort nicht lesbar|
|11|Temperatur Erfassen und Auswerten|Als Frau möchte ich meine Gebärmuttertemperatur einfach angeben können um noch sicherer im verhüten bzw. schwanger werden zu sein.|3|5|Eingabefeld für die Körpertemperatur. Die daten werden gespeichert, können eingelesen und bearbeitet werden|
|12|Schleim Erfassen und Auswerten|Als Frau möchte ich die Konsistenz meines Schleims angeben können um noch sicherer im verhüten bzw. schwanger werden zu sein.|3|8|Dropdown menu zur beschreibung vom Schleim. Die daten werden gespeichert, können eingelesen werden und bearbeitet|
|13|Easteregg|Als Nutzer möchte ich überrascht werden mit einem Easter Egg, um das Interesse an der Applikation aufrecht zu erhalten.|3|13|Ein Kunde wird überrascht.|
|14|Graphische Ausgabe|Als Frau möchte ich die Resultate graphisch ausgegeben bekommen, um möglichst schnell einen geordneten Überblick über meine Gesundheit zu erhalten.|3|20|Alle Informationen werden Ansprechend und Übersichtlich angegeben. Die Benutzung verläuft intuitiv und wird simpel gehalten|
---
# 5) Releaseplan mit den Ausbaustufen
| test                                        | Sprint 1 (7.11.2022)                 | Sprint 2  (18.11.2022)                                       
|-------------------------------------------|--------------------------------------|--------------------------------------------------
 1                                         |User - Story 1: Eingabe Datum         | User - Story 7: Blutungszeit ermitteln
2 |User - Story 2: Daten Speichern       | User - Story 8: Fruchtare Zeit ermitteln
3 |User - Story 3: Daten einlesen        | User - Story 9: Schwangerschafts vs. Verhütungsmodus
 4 |User - Story 4: Zyklusdauer ermitteln | User - Story 10: Passwortschutz
 5 |User - Story 5: Vorhersage nächster Zyklus | User - Story 11: Temperatur erfassen und auswerten
 6 |User - Story 6: Zyklusdauer Durchschnitt | User - Story 12: Schleim erfassen und auswerten
 7 |                                      | User - Story 13: Easter Egg
 8 |                                      | User - Story 14: Graphische Ausgabe
9 | Velocitiy 34 | Veloscity |
---
# 6) Dokumentation Sprint 1
### a. Taskliste für die Umsetzung der User Story (Schätzung in Stunden) b. Anreicherung der User Stories für die Umsetzung
### c. UML Package, Klassen- und Sequenzdiagramm
### d. Dokumentation wichtiger Code Snippets
### e. Herleitung der Testfälle aus den Akzeptanzkriterien der User Stories
---
# 7) Dokumentation Sprint 2 (inkl. Punkte a-e
### a. Taskliste für die Umsetzung der User Story (Schätzung in Stunden) b. Anreicherung der User Stories für die Umsetzung
### c. UML Package, Klassen- und Sequenzdiagramm
### d. Dokumentation wichtiger Code Snippets
### e. Herleitung der Testfälle aus den Akzeptanzkriterien der User Stories
