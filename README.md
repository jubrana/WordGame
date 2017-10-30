# WordGame

1) Clone repository

2) Build project with maven goal, for example **mvn test**, it will compile code and run all tests. ( maven surefire plugin is called implicitly by the maven lifecycle in the maven test phase )

3) After successful build application can be started with **mvn exec:java**

4) Console will expect input for board dimension, **Enter board dimension:**. Example if 6 is entered it means it will create 6x6 board.

5) If wrong input is entered (letters for example) it will exit application with message **Wrong input format for board dimension Exit!**

6) If correct input is entered it will expect input for board letters **Enter board letters:**. Board letters are entered in one line. So for example board dimension 3 (3x3) will have board letters like this **kindubela** (nine words in one line), 4 dimension board will have 16 letters and so on...

7) If wrong input is entered (numbers or special characters for example) or wrong number of letters (If board is 3x3 and there are 12 letters) it will exit application with **Wrong number of letters for board** or **Wrong input format for board letters**

8) After correct inputs it will calculate and search for words.

9) Output shuold look like this:

------------------------- Words -----------------------------
<br /> com
<br /> col
<br /> slom
<br /> lomim
<br /> cio
<br /> omi
<br /> mol
<br /> lom
<br /> mimo
<br /> umi
<br /> som
<br /> sol
<br /> os
<br /> mio
<br /> smo
<br /> slomi
<br /> omu
<br /> slomu
<br /> lomu
<br /> cimu
<br /> <br /> 
Completed in 5 ms. Found 28 unique words and 35 non-unique words on board. 
<br /> Moves: 415
