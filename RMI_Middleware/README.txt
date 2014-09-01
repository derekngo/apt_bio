Derek Ngo (dn5242)
Derek Ly (dtl398)

README.TXT - Middleware Project 3

---------------------------------------------------------------------------------------

These instructions assume that the correct versions of Java and JavaRMI are installed (and the correct classpaths are initalized)

We ran this program using Java 1.7 on a Mac terminal (Eclipse is no bueno, we have been converted)

---------------------------------------------------------------------------------------

Files Included:

- ChatRoomProvider.java
- ChatRoomProviderImpl.java
- SimpleChatClient.java
- SimpleChatClientImpl.java
- SimpleChatServer.java
- SimpleChatServerImpl.java
- TheRegistry
- TheRegistryImpl
- myPolicy

---------------------------------------------------------------------------------------

How to run our RMI Chat Program (using Mac terminal):

	">" indicates a command in terminal
	! indicates that you need to open a new terminal 

1)	! Change paths into the directory where all the files are
		> cd <path_to_directory>
2) 	Compile all the code
		> javac *.java
3)	start rmi registry in the background (in a new terminal)
		> rmiregistry &
4)	start the chat registry
		> java -Djava.security.policy=myPolicy TheRegistryImpl
5) 	! start a chat room provider 
		> java -Djava.security.policy=myPolicy ChatRoomProviderImpl localhost
6) 	! start a chat client (client1)
		> java -Djava.security.policy=myPolicy SimpleChatClient localhost
7)	! start another chat client (client2)
		> java -Djava.security.policy=myPolicy SimpleChatClient localhost
8)	Follow the prompt on the chat provider to create a chat room
9)	Follow the prompt on the chat client (client1) to join a chat room
10)	Follow the prompt on the chat client (client2) to join a chat room
11)	Continue following the prompts to test our program!

---------------------------------------------------------------------------------------

Notes:

- Multiple instances of the Chat Provider can be started. Each instance will be able to register and deregister any servers/chatroom in the Chat Registry