# udpclient
This repository contains a UdpServer.java program and a MyUdpClient.java program.
The Client prompts the user to type a sentence.
The Client sends the sentence to the server which returns a capitalized version of the sentence
The prompting will continue until the user types "Goodbye", at which time the Server will output "GOODBYE" and end the conection

The information is sent through UDP Datagrams on port 9876.


## Identifying Information

* Name: Spencer Au
* Student ID: 2385256
* Email: spau@chapman.edu

* Name: Ewan Shen
* Student ID: 2396486
* Email: ewshen@chapman.edu
*
* Course: CPSC 353
* Assignment: PA2 - udpclient


## Source Files  
* MyUdpClient.java
* UdpServer.java


## References

*

## Known Errors

*

## Build Insructions
* checkstyle MyUdpClient.java
* javac *.java


## Execution Instructions
* Server: java UdpServer
* Client: java MyUdpClient
* With Input: java MyUdpClient < udp.input
