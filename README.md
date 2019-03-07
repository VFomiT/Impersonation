# Impersonation
The program illustrates the capabilities of impersonation services for changing users when accessing resources.

This program creates two files, the first on behalf of the user who starts it, the second on behalf of the user, 
whose name and password are passed to the program via command line arguments. Files are created with the prefix userName_

The program must take three arguments:
  1. Path with file name
  2. The name of the user on whose behalf the second file will be created
  3. User password, on behalf of which the second file will be created

Possible usecase:

  Start the program on behalf of the administrator
 
    · java -jar Impersonation.jar file.txt userName userPassword
    creates two files in the current directory, the owner of one of them is the user who started the program, the second is the username

    · java -jar Impersonation.jar C: \ file.txt userName userPassword
    creates a file on behalf of the current user, and notifies that userName does not have sufficient rights to create the file in the specified path

*userName is a user without administrator rights
