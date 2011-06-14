@echo off
java -cp .;lib/ftldoc.jar;lib/jcmdline-1.0.1.jar;lib/freemarker.jar freemarker.tools.ftldoc.FtlDoc %*
