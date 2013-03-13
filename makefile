database: Launcher.java GUI.java ProjectLauncher.java DB.java Signup.java AdminView.java
	javac Launcher.java GUI.java ProjectLauncher.java DB.java Signup.java AdminView.java
	java ProjectLauncher

setup:
	export CLASSPATH=./:./ojdbc6.jar
clean: 
	rm *.class