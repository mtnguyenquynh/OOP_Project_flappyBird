![image](https://user-images.githubusercontent.com/91582358/172048415-99cb70b4-d073-4a3d-b4e5-9802cbb21d4c.png)      
  
[![License](https://img.shields.io/badge/license-MIT-green)](./LICENSE) ![GitHub top language](https://img.shields.io/github/languages/top/mtnguyenquynh/OOP_Project_flappyBird) ![GitHub branch checks state](https://img.shields.io/github/checks-status/mtnguyenquynh/OOP_Project_flappyBird/main)
![GitHub last commit](https://img.shields.io/github/last-commit/mtnguyenquynh/OOP_Project_flappyBird)
  
# Table of contents :round_pushpin:
1. [Introduction](#Introduction)
2. [Game](#Game)
3. [Screenshot](#Screenshot)
4. [Play game](#Play)
5. [References](#References)

# OOP_Project_flappyBird  ![alt](https://i.imgur.com/aDQhdFK.png)
## Introduction <a name="Introduction"></a> :smile:
### Team name: BUILD SUCCESSFUL :star_struck:
### Our team member
|Index    | Name      | ID  | Email |
| :------------ |   :---:       | :--------: |:-----:|
| 1       | Nguyễn Quỳnh Mai Thanh        | ITITIU20309   | mtnguyenquynh@gmail.com |
| 2         | Nguyễn Trần Minh Trung       | ITITIU20332  | |
|3| Trần Quang Trí | ITITIU20325| ititiu20325@student.hcmiu.edu.vn |
|4| Nguyễn Huỳnh Anh Tú | ITITIU20337||

### Topic **Flappy Bird**  
Why we choose this topic :question:  
The reason we chose Flappy Bird is because it is a simple game that fully demonstrates the essence of object-oriented programming. At the same time, we are also very curious about how the game set the Guinness World Record 2016 in terms of downloads and the huge revenue earned from it. We hope from this simple yet addictive game we can develop more features for it and keep this fun game.  
  
### Tasks  
|Index| Task      | PersonInCharge | Note | Contribution|
|:---|   :-------------------------------------------------------| :--------: |:-----:|:-----:|
| 1  | Write Readme, Make Sound Effect, UML diagram, Design UI       | Mai Thanh  ||25|
| 2  | Handle bird class and it movement, Make Powerpoint| Minh Trung  | |25|
|3| Handle pipe class and it movement, UML diagram | Quang Trí||25|
|4| Design UI, Handle Collision and Score Class, Make Powerpoint | Anh Tú||25|  

## Play game <a name="Game"></a> :rocket:
### Requirement for software:  

### Environment :computer:
Language: Java  
Library: javax, Java Standard Library  
API: Abstract Window Toolkit

### How to play:  
The bird will be controlled by the player to move up and down (using the space or up button) to cross the pipes. Each pass will be scored, if it hits the pipe or falls to the ground, it will not be scored and the game ends.

### Game logic :bulb:
- The game will have 2 main threads: the system thread and the custom thread. The system flow is used to display the content on the frame and receive the window events that the user interacts with. Custom flow to run game elements (birds, columns, clouds, ...).
- Calculate collision: when the coordinates of the bird intersect with the coordinates of the pipe, so they collide.
- Piping logic: when the last element added to the container (the pipe contained in the pipe pool) is fully visible on the screen, the next pair will be added.
- The logic of creating the flying bird effect: the bird will be animated by loading 16 consecutive bird image frames.

### UML Class Diagrams  
Show your github project page:  
[OOP_Project_FlappyBird](https://github.com/mtnguyenquynh/OOP_Project_flappyBird.git)  
### README
[Readme](README.md)  

### Team Commits in github

### Feature:  
**Main Feature:**
- Use space press/ UP button to control bird speed and height
- Record score 
- Beautiful universe theme
- Check when bird collide with pipe
**Extra feature:**
- Different type of pipes: normal, hover, moving pipe
- Sound effects
**Future feature:**
- Different bird skins
- Ranking
- Boss fighter (after getting over 100 pipes)
- Different game maps
...

**Discuss challenges in the project:**
- The point of challenge was that we didn't know where to start, even though we figured out the logic to make the game work very early on. Also, putting the classroom theories into practice was a bit confusing for us at first.

**Result and Conclusion**  
-  Luckily, we found excellent and detailed references that helped us refine our original idea and add cool features. 
-  Through this project we believe we will be able to make better use of OOP.

## Screenshot <a name="Screenshot"></a> :star2:
### Bird  
![OOP_PROJECT-_FLAPPY-BIRD_-BUILD-SUCESSFUL-TEAM-2022-06-04-23-09-46](https://user-images.githubusercontent.com/91582358/172015395-1e57d717-a8db-4d59-a938-0e838ed451e7.gif)

### Pipe  
Normal pipe  
![](https://i.imgur.com/DuZzVLc.png)  
Moving Pipe

https://user-images.githubusercontent.com/91582358/172047633-87395e16-b474-420c-9e28-6970a6045491.mp4

### Demo :arrow_forward:
https://user-images.githubusercontent.com/91582358/171908586-65041e94-6ebb-41e9-b411-1fe3b69e6858.mp4

### What we have learned :pencil:
Inheritance  
Thread  
Probability  
UI Design  
Design Pattern
Animation

## Play game <a name="Play"></a> 
Step 1: Clone following repository to your local   
Step 2: Run game in "App" class   
Step 3: Enjoy the game and reach the high score

## References <a name="References"></a> :book:
Thanks to the incredibly rich resources that helped us make this project happen 
1. [kingyuluk](https://github.com/kingyuluk/FlappyBird.git)  
2. [Sound effect handling](https://stackoverflow.com/questions/11919009/using-javax-sound-sampled-clip-to-play-loop-and-stop-multiple-sounds-in-a-game)  
3. [Thread class in Java](https://www.google.com/search?q=thread+class+in+java&sxsrf=ALiCzsZyxjZMAs13ZMO1HoS2un5lXAWVwQ%3A1654275084982&ei=DDyaYrLPO4340gTE1JjQDA&ved=0ahUKEwiyhbT23pH4AhUNvJQKHUQqBsoQ4dUDCA4&uact=5&oq=thread+class+in+java&gs_lcp=Cgdnd3Mtd2l6EAMyBQgAEIAEMgYIABAeEAcyBggAEB4QBzIGCAAQHhAHMgYIABAeEAcyBggAEB4QBzIGCAAQHhAHMgYIABAeEAcyBggAEB4QBzIGCAAQHhAHOgcIABBHELADOgcIABCwAxBDOgQIABBDOgQIABANSgQIQRgASgQIRhgAUPwTWOwiYKMmaAFwAXgAgAHMAYgBhAiSAQUwLjUuMZgBAKABAcgBCsABAQ&sclient=gws-wiz)
4. [DataInputStream and DataOutputStream](https://www.geeksforgeeks.org/java-io-datainputstream-class-java-set-1/?ref=lbp)
5. [Design Patterns in Java](https://www.journaldev.com/1827/java-design-patterns-example-tutorial)
6. [Pygame Flappy Bird](https://codelearn.io/sharing/lam-game-flappy-bird-voi-pygame)
7. [Java BitSet intersects() method](https://www.javatpoint.com/post/java-bitset-intersects-method#:~:text=%E2%86%92%20%E2%86%90%20prev-,Java%20BitSet%20intersects()%20method,also%20true%20in%20this%20BitSet.)

