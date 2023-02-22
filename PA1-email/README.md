# email

+ The Email.java program we first store the sending address, receiving address, sending user, receiving user,
and subject by using the readLine() method for each String variable.
+ We then declare an String storing Array List named "body" which will store the body of the message.
+ We then declare a String variable named "temp" which we represent the current line being read.
+ The first line is read with readLine(), and we implement a while loop with the condition that the current line, "temp", is not equal to a single period, which is the terminating condition.
+ The while loop will repeatedly add the current line, "temp", to the body of the message and then use readLine() to store the next line.
+ We then connect to the server through "smtp.chapman.edu", port 25.
+ We then print out the welcome message from the server, and print all the corresponding exchange messages with all the SMTP commands between the Server and the Client using the String variables we have stored earlier.
+ Additionally, we print out to the server the body of the message through a for loop that iterates the number of time with however many lines the body has.
+ Finally, the socket is closed.

## Identifying Information

* Name: Ewan Shen
* Student ID: 2396486
* Email: ewshen@chapman.edu
*
* Name: Spencer Au
* Student ID: 2385256
* Email: spau@chapman.edu
*
* Course: CPSC 353
* Assignment: PA1 - Email Server

## Source Files

* Email.java

## References

* 

## Known Errors

*

## Build Insructions

* javac *.java

## Execution Instructions

* java Email < email.input
