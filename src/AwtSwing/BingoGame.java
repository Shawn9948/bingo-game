package AwtSwing;
import java.awt.*;
import java.awt.event.*;
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
	
	
	
	static class MyFrame extends JFrame{
		public MyFrame(String title) {
		super(title);
		this.setLayout(new BorderLayout()); //awtSwing layout은 보더 레이아웃이다. 
		this.setSize(400, 500);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close Operation코드가 있어야 실제로 창이 닫힌다.
		
		initUI( this ); //Screen UI Set. (스크린을 초기화 할수있는 메소드), this 는 MyFrame이다.
		
		this.pack(); //Pack Empth Space. 비어있는 공간의 자리를 정리한다.		
		
		
		} 
	}
	
	
	static void initUI(MyFrame myFrame) {
		panelNorth = new JPanel();
		panelNorth.setPreferredSize(new Dimension(400,100)); //setPreferredSize 코드를 통해 panel의 크기를 지정함
		panelNorth.setBackground(Color.blue); 
		
		labelMessage = new JLabel("Find Same Fruit!" + "Try 0"); //게임의 안내문구와 시도한 횟수를 텍스트로 출력한다.
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
		new MyFrame("Bingo Game"); //창 제목 설
		
	}


}