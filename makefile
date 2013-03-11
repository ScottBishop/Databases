database: Launcher.java GUI.java ProjectLauncher.java DB.java
	javac Launcher.java GUI.java ProjectLauncher.java DB.java
	java ProjectLauncher

setup:
	export CLASSPATH=./:./ojdbc6.jar
clean: 
	rm *.class