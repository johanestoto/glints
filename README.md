# TicTacToe
1. Created using Java Spring Boot, so after downloading source code , It can be run on Your local machine and access through Web Browser on http://localhost:8080
2. The Tic Tac Toe game is configurable by supplying Board Dimension before playing game on the Web in any value greater than 3. (I restrict the board dimension greater than 3 to have a good experience on game). So you can freely input 3 x 3, 4 x 4, 5 x 5 and any value. Dimension is not harcoded, its dynamic.
3. The game has 2 option, which are COMPUTER opponent and HUMAN opponent
4. Choosing COMPUTER opponent, will let you play Tic Tac Toe with very simple Artificial Intelligence engine I have made in Java
5. Choosing HUMAN opponent, will let you play Tic Tac Toe with other Human Player. This 2 Human Player type has realtime experience, so You can run your move and view other player move without refreshing browser. The movement also synchronised with other player, you can only run your turn after other player run His turn. 
6. Game engine can evaluate the condition for WIN, LOSS and DRAW
7. Code architecture design using Object Oriented Programming approach to make reusable Game, Board, Square object and the code easy to maintain  
8. Technology stack is using Java, Springboot, Thymeleaf, Html and WebSocket realtime messaging.
