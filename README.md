# Poker Evaluation
===================================

This sample project to evaluate poker cards on hand ,Compare between 2 on hand cards ,Which one higher
and why higher than another.
First player is represented to Somchai and Second player is represented to Somsak.

Basic card  
Each card has a suit which is one of Clubs, Diamonds, Hearts,
or Spades (denoted C, D, H, and S in the input data). Each card also has a value which is one
of 2, 3, 4, 5, 6, 7, 8, 9, 10, jack, queen, king, ace (denoted 2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, A).

App is quite rough UI ,Focus on Input and Output

Sample input/output  
Inputs ->  
Somchai: 2H 3D 5S 9C KD  
Somsak: 2C 3H 4S 8C AH

Outputs->  
Somsak wins. with high card: Ace


# Step to play
===================================
- In main page (first page) ,You will see 2 input text blocks and several buttons.
- Typing first player cards on text block and press DONE on keyboard to finish ,  
  You could type both capital and small letters ,You could input all of on hand cards or
  only few cards such as "2H 3D 5S" but you have to fill all cards before compare them.
  And you could input denoted either with space ("2H 3D 5S 9C KD") or without space ("2H3D5S9CKD").
  But if you put some wrong denoted ("XY YZ") ,App will show alert msg.
- Typing second player cards on text block with same condition of first player.
- Now you will see on hand cards of first player and second player.
  You can clear them by clear cards button if you want to fill another group of cards.
- Press Compare button to compare which one higher and let see the result but If someone haven't enough card,App will show alert msg
- Finally, You could play again by pressing New Game button on top of screen.


# Code structure and dependencies
===================================
- Kotlin language
- MPV on presentation layer
- Using RxJava
- UnitTest with mockito Kotlin and JUnit