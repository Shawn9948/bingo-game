# bingo-game
**자바를 이용하여 만드는 빙고게임 프로젝트입니다.**
- javax.swing, java.awt, java,util.Random, java.awt.event를 import해서 만든 게임입니다. 
- JPanel을 이용하여 화면을 구성했고 버튼을 구성하여 for문으로 4x4사이즈로 출력해주었습니다. 
- 각각에 버튼에는 origin 이미지와 question이미지로 구성하였습니다. 
- 게임 로직관련으로는 각각에 이미지에 1~15번에 번호를 매긴후 섞어주었고, 클릭 이벤트를 넣어주었습니다.
- 클릭 이벤트를 통해서 최대 2번까지 누를수있게 설정하였고, 두번 누를 때 마다 tryCount를 하도록 설정했습니다. 
- 첫번째 카드값과 두번째 카드값이 동일하다면 카드를 question이미지로 변경하지 않고 그대로 놔두도록 하였습니다. 
- 만약 동일하지 않다면 이미지를 1초 후 question이미지로 변경하도록 만들었습니다. 
- 2번째 카드를 열어보면 3번째는 못열어보기때문에 각각에 if문에 카드를 오픈한 횟수를 0으로 초기화 시켜주어 게임을 진행할수있게 만들었습니다. 
- 만약 동일한 카드를 8개 다 찾으면 game안내 text가 변경되어 Game Over를 출력하게 해주었습니다. 

## 궁금한점
```java
public void backToQuestion() { 
 timer = new Timer(1000, new ActionListener() { 
			
	@Override
	public void actionPerformed(ActionEvent e) {
	 openCount = 0;
	 buttons[buttonIndexSave1].setIcon(changeImage("question.png"));
	 buttons[buttonIndexSave2].setIcon(changeImage("question.png"));
	 timer.stop();			
	 }
 });
 timer.start();
 }
		
```
이부분에서 new Timer의 인자값으로 1000, new ActionListener() {} 이렇게 주고 {}안에 overide를 해주었습니다.
근데 인자값으로 새로운 인스턴스가 들어갈수있는지에 대해서 처음보는 것이라 공부를 더 해보아야할것같습니다. 

## 해결
javax.swing.Timer는 객체를 생성할때 2가지의 인자값을 받는다. 하나는 지연시킬 밀리초이고 또하나는 지연시킨 밀리초 이후 실행시킬 작업이다. 
```java
 int delay = 1000; //milliseconds
  ActionListener taskPerformer = new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
          //...Perform a task...
      }
  };
```
위와 같이 사용하도록 정의되어 있다. 내가 궁금했던 인자값으로 생성자를 집어넣을수있는가에 대해서 답변이 가능할것같다. 생성자를 직접 인자값으로 넣은것은 실행시킬 작업을 오버라이딩해야하는데 새로운객체를 생성하고 오버라이딩하는게아니라 한번에 작업을 하게 되서 저런코드가 나온것같다. (앞으로 위와같은 형식의 코드들을 더많이 접해보아야겠다.)

https://docs.oracle.com/javase/7/docs/api/javax/swing/Timer.html 출처
