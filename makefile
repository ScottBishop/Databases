database: Launcher.java GUI.java ProjectLauncher.java DB.java Signup.java Admin.java
	javac Launcher.java GUI.java ProjectLauncher.java DB.java Signup.java Admin.java
	java ProjectLauncher

setup:
	export CLASSPATH=./:./ojdbc6.jar
clean: 
	rm *.class