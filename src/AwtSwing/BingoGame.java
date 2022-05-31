package AwtSwing;
import java.awt.*;
import java.awt.event.*;
import java.util.Random; 
import javax.swing.*;



public class BingoGame {
	//JPanel을 이용하여 UI를 구현하자
	static JPanel panelNorth; //Top view 기본적인 정보가 구현
	static JPanel PanelCenter; //Game View 실제적인 게임화면 구현
	static JLabel labelMessage; //화면에 띄울 텍스트는 JLable을 통해 띄운다.
	
	static JButton[] buttons = new JButton[16]; //4x4버튼 배열구현
	static String[] images = {
			"fruit01.png","fruit02.png","fruit03.png","fruit04.png",
			"fruit05.png","fruit06.png","fruit07.png","fruit08.png",
			"fruit01.png","fruit02.png","fruit03.png","fruit04.png",
			"fruit05.png","fruit06.png","fruit07.png","fruit08.png",
	};
	
	// 게임 로직관련 변수생성
	static int openCount = 0; //Opened Card Count: 0, 1, 2
	static int buttonIndexSave1 = 0; //Frist Opened Card Index: 0~15
	static int buttonIndexSave2 = 0; //Second Opened Card Index: 0~15
	static Timer timer; 
	static int tryCount = 0; //Try Count
	static int successCount = 0; //Bingo Count: 0~8
	
	
	
	static class MyFrame extends JFrame implements ActionListener{
		public MyFrame(String title) {
		super(title);
		this.setLayout(new BorderLayout()); //awtSwing layout은 보더 레이아웃이다. 
		this.setSize(400, 500);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close Operation코드가 있어야 실제로 창이 닫힌다.
		
		initUI( this ); //Screen UI Set. (스크린을 초기화 할수있는 메소드), this 는 MyFrame이다.
		mixCard(); // 카드를 섞어주는 메소드
		
		this.pack(); //Pack Empty Space. 비어있는 공간의 자리를 정리한다.		
		} 
		@Override //어노테이션을 하여 override 했다고 정의한다.
		public void actionPerformed(ActionEvent e) { //ActionListener가 작동되면 actionPerformed메소드가 실행된다.
			
			if(openCount == 2) {//카드를 open한 횟수가 2라면 
				return;
			}
			
			
			JButton btn = (JButton)e.getSource(); //실제로 눌려진 버튼은 btn으로 들어오고 
			int index = getButtonIndex(btn);  // 실제로 눌린 btn은 몇번 index인지 index변수에 담아준다.
			btn.setIcon(changeImage(images[index]));//실제로 눌린 버튼의 이미지를 변경해주는 코드이다. changeImage메소드에 인자값으로 Image[index]를 입력해주어서 메소드가 실행되도록 설정한것이다. 
		
			openCount++; //openCoun는 카드한장을 오픈할때 마다 1씩 중첩된다.
			if(openCount == 1) { //First Card 인가?
				buttonIndexSave1 = index; // 버튼의 인덱스를 정장해준다.
			}
			else if(openCount == 2) { //Second Card인가?
				buttonIndexSave2 = index;
				tryCount ++; // 시도한 횟수를 중첩시킨다.
				labelMessage.setText("Find Same Fruit! " + "Try " + tryCount); //시도한 횟수를 텍스트에 입력시켜준다.
			
				//판정 로직 Judge Logic
				// 데이터 타입을 boolean으로 하고 메소드를 입력한다. 그리고 이 메소드가 참이라면 빙고,불이라면 불을 출력한다.
				boolean isBingo = checkCard(buttonIndexSave1, buttonIndexSave2);
				if( isBingo == true) { //isBingo가 true라면 openCount를 0으로 만들고 successCount의 수를 올린다. 그뒤 successCount가 8이라면 Text로 게임종료 메세지를 남긴다. 
					openCount = 0;
					successCount++;
					if(successCount ==8) {
						labelMessage.setText("Game Over "+"Try "+tryCount);
					}
				}else { //isBingo가 false라면 backToQuestion메소드를 실행한다.
					backToQuestion();
				}
			}
		}
		
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
		
		
		public boolean checkCard(int index1, int index2) {
			if(index1 == index2) { // 첫번째 눌린카드와 두번째 눌린 카드의 값이 같다면 true
				return true;
			}
			if(images[index1].equals(images[index2])) { //첫번재 두번째 눌린카드의 이미지 index값이 같다면 true
				return true;
			}else { //그 이외는 모두 false
				return false;
			}
		}
		
		public int getButtonIndex(JButton btn) { //getButtonIndex의 메소드를 설정해준다.
			int indexNum = 0; //indexNum 변수생성
			for(int i =0 ; i<16 ; i++) {
				if(buttons[i] == btn) { //Same instance?
					indexNum= i;
				}
			}
			return indexNum;
		}
	}
	static void mixCard() {
		Random rand = new Random();
		for(int i=0; i<1000; i++) {
			int random = rand.nextInt(15) + 1; //1~15까지의 값이 나온다 random변수는 random번호이다. +1을 해준이유는 0~14까지말고 1~15까지 돌리기 위해서 +1을 해주었다.
			//swap
			String temp = images[0];
			images[0] = images[random];
			images[random] = temp;
		}
	}
	
	static void initUI(MyFrame myFrame) {
		panelNorth = new JPanel();
		panelNorth.setPreferredSize(new Dimension(400,100)); //setPreferredSize 코드를 통해 panel의 크기를 지정함
		panelNorth.setBackground(Color.blue); 
		
		labelMessage = new JLabel("Find Same Fruit! " + "Try 0"); //게임의 안내문구와 시도한 횟수를 텍스트로 출력한다.
		labelMessage.setPreferredSize(new Dimension(400,100));
		labelMessage.setForeground(Color.white); //foreground코드는 텍스트의 글씨색깔을 지정한다.
		labelMessage.setFont(new Font("Monaco",Font.BOLD, 20)); 
		labelMessage.setHorizontalAlignment(JLabel.CENTER); //setHorizontalAlignment는 위치선정을 하는 코드이다.
		
		panelNorth.add(labelMessage); //만든 labelMessage를 panel에 추가해주는 작업
		myFrame.add("North",panelNorth); //JFrame 상단에 PanelNorth를 출력시킨다. 
		
		
		PanelCenter = new JPanel();
		PanelCenter.setLayout(new GridLayout(4,4)); //GridLayout은 격자 레이아웃이라 부른다. 
		PanelCenter.setPreferredSize(new Dimension(400,400));
		for(int i=0; i<16; i++) {
			buttons[i] = new JButton();
			buttons[i].setPreferredSize(new Dimension(100,100)); // 버튼 하나의 크기를 지정한다.1개당 가로 세로 100을 가지면 총 400x400이 된다. 
			buttons[i].setIcon( changeImage("question.png")); //각각의 버튼마다 이미지를 넣어줄건데 메소드를 이용하여 집어넣어줄것이다. 처음 이미지는 question이미지이다.
			buttons[i].addActionListener(myFrame); //버튼을 눌렀을 액션 구현>JFrame이 이벤트를 받을수있도록 추가해주어야한다.
			
			PanelCenter.add(buttons[i]); //panelCenter에 반복문for을 이용하여 이미지들을 출력해준다.
		}
		myFrame.add( "Center", PanelCenter); //JFrame에 중간에 PanelCente을 출력시킨다.
	}
	
	static ImageIcon changeImage(String filename) {
		ImageIcon icon = new ImageIcon("./img/" + filename);
		Image originImage = icon.getImage();
		Image changedImage = originImage.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		ImageIcon icon_new = new ImageIcon(changedImage);
		return icon_new;
	}
	
	
	
	public static void main(String[] args) {
		new MyFrame("Bingo Game"); //창 제목 설정
		
	}


}