# chatgame

## The team working on this project consists of Spencer, Shanzeh, and Alex. Each member contributed in the following ways:

+ Spencer: Worked on implementing the username prompt and acceptance notification, as well as the list of possible commands for the client. Also helped with testing and debugging.

+ Shanzeh: Implemented the ability for the first client to become the host, as well as the instructions for the host on how to ask questions, assign points, and use the SCORES command. Also helped with testing and debugging.

+ Alex: Worked on implementing the multiple-choice or true/false question system, as well as the scoring and scoreboard functionality. Also helped with testing and debugging.

## To play the game, follow these steps

+ Start the server by running the ChatServer class.

+ Start the client by running the ChatClient class.

+ When prompted, enter a username. The username should be unique and not already in use by another client.

+ Once the username is accepted, a list of possible commands will be displayed. These commands can be used to interact with the server and participate in the game.

+ If the client enters the username "host", they will become the host of the game. The host will receive instructions on how to ask questions, assign points, and use the SCORES command.

+ The host will send a question to the other clients. The question can be a multiple-choice or true/false question. 

+ The first client to submit a correct answer will score points. The host can award points manually.

+ To view the scores of all clients, the host can type "SCORES". A message with the scores of each client will be sent to all the clients.

+ Note: The game is designed to be played with multiple clients. To fully experience the game, connect multiple clients to the server and follow the steps above.

## Identifying Information

* Name: Spencer Au
* Student ID: 2396486
* Email: spau@chapman.edu

* Name: Shanzeh Bandukda
* Student ID: 2379158
* Email: bandukda@chapman.edu

* Name: Alex Haberman
* Student ID: 2377885
* Email: ahaberman@chapman.edu

* Course: CPSC 353
* Assignment: PA4 - ChatGame

## Source Files

* Client.java
* ClientHandler.java
* ClientListener.java
* MtClient.java
* MtServer.java
* server.sh
* client.sh
* chatgame.sh

## References

*

## Known Errors

*

## Build Insructions

### To Compile

* javac *.java

## Execution Instructions

### For Server

* java MtServer
* ./server.sh

### For Client

* java MtClient
* ./client.sh

### Testing/Scripts

* chmod +x chatgame.sh
* chmod +x server.sh
* chmod +x client.sh

* ./chatgame.sh - opens 1 server and 2 clients
* ./server.sh - compiles and runs 1 server
* ./client.sh - compiles and runs 1 client
