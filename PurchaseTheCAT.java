import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;

import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Purchase the Cat
 * 商品單價比較
 * @author yuhsuan
 * @version 1.0.0
 */
public class PurchaseTheCAT {
	
	//商品列表
	private ArrayList<ProductClass> productclass_list;
	
	//表格內容參數
	private static Object[][] table_data; //表格內容
	private static String[] headings; //表格欄位標題
	private static int row_num; //表格行數
	
	//呼叫表格內容參數(主要作為TableData類別使用)
	public static Object Table_data(int rowIndex, int columnIndex){
		return table_data[columnIndex][rowIndex];
	}
	public static String Headings(int columnIndex){
		return headings[columnIndex];
	}
	public static int Row_num(){
		return row_num;
	}
	
	//表格顯示控制
	private int current_class; //目前表格被點擊的欄位：商品類型
	private int current_item; //目前表格被點擊的欄位：商品項目
	private int show_key; //表格顯示的模式 0全部 >0只顯示第幾個商品類型
	
	//Panel的參數
	private JFrame frmPurchaseTheCat;
	private JTextField txtItem;
	private JTextField txtNumber;
	private JTextField txtUnit;
	private JTextField txtPrice;
	private JTextField txtItemclass;
	private JTable table;
	private JTextArea txtAreaNote;
	private JComboBox<String> comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PurchaseTheCAT window = new PurchaseTheCAT();
					window.frmPurchaseTheCat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PurchaseTheCAT() {
		
		initialize();
		
		//初始化商品列表
		productclass_list = new ArrayList<ProductClass>();
		
		//初始化表格標題
		headings = new String[] {"名次","單價","名稱","價錢","數量","備註"};
		
		//初始化表格顯示控制
		current_class = -1;
		current_item = -1;
		show_key = 0;
	}
	
	//======== start of initialize() 視窗設定部分開始 ========//
	/**
	 * Initialize the contents of the frame. 視窗設定
	 */
	private void initialize() {
		
		frmPurchaseTheCat = new JFrame();
		frmPurchaseTheCat.setTitle("Purchase the Cat");
		frmPurchaseTheCat.setBounds(100, 100, 900, 600);
		frmPurchaseTheCat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPurchaseTheCat.getContentPane().setLayout(new BorderLayout(0,0));
		
		JMenuBar menuBar = new JMenuBar();
		frmPurchaseTheCat.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("檔案");
		menuBar.add(mnFile);
		
		JMenuItem mntmInputfile = new JMenuItem("匯入檔案");
		//點擊匯入檔案
		mntmInputfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					InputFile();
				} catch (IOException e1) {
					ErrorPanel("錯誤",e1.getMessage());
				}
			}
		});
		mnFile.add(mntmInputfile);
		
		JMenuItem mntmOutputfile = new JMenuItem("儲存檔案");
		//點擊儲存檔案
		mntmOutputfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					OutputFile();
				} catch (IOException e1) {
					ErrorPanel("錯誤",e1.getMessage());
				}
			}
		});
		mnFile.add(mntmOutputfile);
		
		JMenuItem mntmFinish = new JMenuItem("結束");
		//點擊結束
		mntmFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExitClick();
			}
		});
		mnFile.add(mntmFinish);
		
		JMenu mnHelp = new JMenu("幫助");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("關於");
		//點擊關於
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutClick();
			}
		});
		mnHelp.add(mntmAbout);
		
		JPanel panelTitle = new JPanel();
		panelTitle.setBackground(new Color(255, 239, 213));
		panelTitle.setForeground(Color.BLACK);
		frmPurchaseTheCat.getContentPane().add(panelTitle, BorderLayout.NORTH);
		panelTitle.setLayout(new BorderLayout(0, 0));
		panelTitle.setBorder( new EmptyBorder( 10, 15, 3, 15 ) );
		
		JLabel label = new JLabel("Purchase the Cat");
		label.setForeground(new Color(233, 150, 122));
		panelTitle.add(label, BorderLayout.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Comic Sans MS", Font.PLAIN, 28));
		
		JLabel lblTitle = new JLabel("- 商品單位比價 -");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitle.add(lblTitle, BorderLayout.SOUTH);
		
		JLabel lblImg = new JLabel("");
		lblImg.setHorizontalAlignment(SwingConstants.RIGHT);
		lblImg.setIcon(LoadImageIcon("/cat1.png",40));
		lblImg.setPreferredSize(new Dimension(300, 0));
		panelTitle.add(lblImg, BorderLayout.WEST);
		
		JLabel lblImg_1 = new JLabel("");
		lblImg.setHorizontalAlignment(SwingConstants.RIGHT);
		lblImg_1.setIcon(LoadImageIcon("/cat.png",40));
		lblImg_1.setPreferredSize(new Dimension(300, 0));
		panelTitle.add(lblImg_1, BorderLayout.EAST);
		
		JPanel panelTool = new JPanel();
		panelTool.setBackground(new Color(255, 240, 245));
		frmPurchaseTheCat.getContentPane().add(panelTool, BorderLayout.WEST);
		panelTool.setLayout(new BorderLayout(15, 15));
		panelTool.setBorder( new EmptyBorder( 15, 15, 15, 15 ) );
		panelTool.setPreferredSize(new Dimension(300, 0));
		
		JLabel lblAddtitle = new JLabel("新增項目");
		lblAddtitle.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		panelTool.add(lblAddtitle, BorderLayout.NORTH);
		
		JPanel panelAdd = new JPanel();
		panelAdd.setBackground(new Color(255, 240, 245));
		panelTool.add(panelAdd, BorderLayout.CENTER);
		panelAdd.setLayout(null);
		
		JLabel lblItem = new JLabel("名稱");
		lblItem.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblItem.setBounds(6, 50, 61, 16);
		panelAdd.add(lblItem);
		
		txtItem = new JTextField();
		txtItem.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		txtItem.setBounds(56, 45, 182, 26);
		panelAdd.add(txtItem);
		txtItem.setColumns(10);
		
		JButton btnAdd = new JButton("新增");
		//點擊新增按鈕
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddClick(1); //1 for mode 新增
			}
		});
		btnAdd.setBounds(157, 288, 95, 29);
		panelAdd.add(btnAdd);
		
		JLabel lblNumber = new JLabel("數量");
		lblNumber.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblNumber.setBounds(6, 127, 38, 16);
		panelAdd.add(lblNumber);
		
		txtNumber = new JTextField();
		txtNumber.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		txtNumber.setText("1");
		txtNumber.setBounds(56, 127, 75, 26);
		panelAdd.add(txtNumber);
		txtNumber.setColumns(10);
		
		txtUnit = new JTextField();
		txtUnit.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		txtUnit.setText("個");
		txtUnit.setBounds(144, 127, 94, 26);
		panelAdd.add(txtUnit);
		txtUnit.setColumns(10);
		
		JLabel lblPrice = new JLabel("價錢");
		lblPrice.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblPrice.setBounds(6, 88, 61, 16);
		panelAdd.add(lblPrice);
		
		txtPrice = new JTextField();
		txtPrice.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		txtPrice.setBounds(56, 83, 106, 26);
		panelAdd.add(txtPrice);
		txtPrice.setColumns(10);
		
		JLabel lblNote = new JLabel("備註");
		lblNote.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblNote.setBounds(6, 167, 61, 16);
		panelAdd.add(lblNote);
		
		txtItemclass = new JTextField();
		//偵測類別輸入，找出相對應的單位
		txtItemclass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				FindUnit();
			}
		});
		txtItemclass.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		txtItemclass.setBounds(56, 7, 182, 26);
		panelAdd.add(txtItemclass);
		txtItemclass.setColumns(10);
		
		JLabel lblItemClass = new JLabel("類別");
		lblItemClass.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblItemClass.setBounds(6, 12, 38, 16);
		panelAdd.add(lblItemClass);
		
		txtAreaNote = new JTextArea();
		txtAreaNote.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		txtAreaNote.setBounds(56, 165, 182, 111);
		panelAdd.add(txtAreaNote);
		
		JButton btnClear = new JButton("清空");
		//點值清空按鈕，清空已填入的內容
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClearClick();
			}
		});
		btnClear.setBounds(6, 288, 78, 29);
		panelAdd.add(btnClear);
		
		JButton btnChange = new JButton("修改");
		//點擊修改按鈕
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddClick(2); // 2 for mode 修改
			}
		});
		btnChange.setBounds(82, 288, 75, 29);
		panelAdd.add(btnChange);
		
		JLabel lblDollar = new JLabel("元");
		lblDollar.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblDollar.setBounds(177, 89, 61, 16);
		panelAdd.add(lblDollar);
		
		JPanel panelStart = new JPanel();
		panelStart.setBackground(new Color(255, 240, 245));
		panelTool.add(panelStart, BorderLayout.SOUTH);
		panelStart.setPreferredSize(new Dimension(0, 75));
		panelStart.setLayout(null);
		
		JLabel lblStartTitle = new JLabel("顯示列表");
		lblStartTitle.setBounds(0, 0, 270, 22);
		lblStartTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		panelStart.add(lblStartTitle);
		
		JButton btnStart = new JButton("更新列表");
		btnStart.setBounds(174, 40, 96, 29);
		//點擊更新顯示表格
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClickShow(show_key);
			}
		});
		panelStart.add(btnStart);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(0, 40, 169, 29);
		//偵測拉霸選擇，偵測目前顯示的選項
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowSelect();
			}
		});
		comboBox.addItem("全部"); //加上全部顯示的選項
		panelStart.add(comboBox);
		
		JPanel panelShow = new JPanel();
		panelShow.setBackground(new Color(240, 248, 255));
		frmPurchaseTheCat.getContentPane().add(panelShow, BorderLayout.CENTER);
		panelShow.setLayout(new BorderLayout(15, 15));
		panelShow.setBorder( new EmptyBorder( 15, 15, 15, 15 ) );
		
		JScrollPane scrollPane = new JScrollPane();
		panelShow.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		//點擊表格，在輸入中顯示，方便修改
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ShowColumn();
			}
		});
		table.setRowHeight(30);
		table.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		scrollPane.setViewportView(table);
		
		JLabel lblShowTitle = new JLabel("商品列表");
		lblShowTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		panelShow.add(lblShowTitle, BorderLayout.NORTH);
		
		JPanel panelAdjust = new JPanel();
		panelAdjust.setBackground(new Color(240, 248, 255));
		FlowLayout flowLayout = (FlowLayout) panelAdjust.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panelShow.add(panelAdjust, BorderLayout.SOUTH);
		
		JButton btnDelete = new JButton("刪除");
		//點擊刪除
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteClick();
			}
		});
		panelAdjust.add(btnDelete);
		
		JButton btnAlldelete = new JButton("全部刪除");
		//點擊全部刪除
		btnAlldelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AllDeleteClick();
			}
		});
		panelAdjust.add(btnAlldelete);
	}
	//======== end of initialize() 視窗設定部分結束 ========//
	
	//======== menubar 動作 ========//

	/**
	 * 匯入檔案
	 * @throws IOException
	 */
	protected void InputFile() throws IOException{
		//警告詢問
		if (OptionPanel("訊息","匯入檔案會覆蓋原本的紀錄，確定要進行匯入？")==2) {
			return;
		}
		//還原資料庫
		productclass_list = new ArrayList<ProductClass>();
		comboBox.removeAllItems();
		comboBox.addItem("全部");
		
		//讀取資料
		File f = new File("商品清單.txt");
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		
		//每一行讀取，存入資料庫
		String line;
		while ((line=br.readLine()) != null) {
		  String[] data = line.split(",");
		  int data2 = Integer.parseInt(data[2]);
		  int data3 = Integer.parseInt(data[3]);
		  String item_class = data[0];
		  String data5;
		  if (data.length==5) {
			  data5 = "";
		  }else {
			  data5 = data[5];
		  }
		  Product pro = new Product(data[1],data2,data3,data[4],data5);
		
		  Add(item_class, pro);
		}
		
		fr.close();
		
		//顯示列表
		ClickShow(0);
		
		//顯示小視窗
		ConfirmPanel("訊息","匯入成功！");	
		
	}

	/**
	 * 匯出檔案
	 * @throws IOException
	 */
	protected void OutputFile() throws IOException {
		//開啟檔案
		File f = new File("商品清單.txt");
		FileWriter fw = new FileWriter(f);
		String str = "";
		
		int procla_list_size = productclass_list.size();
		
		//輸出商品細項清單
		if (procla_list_size!=0) {
			for (int i=0; i<procla_list_size-1; i++) {
				ProductClass procla = productclass_list.get(i);
				for (int j=0; j<procla.ShowProductListSize(); j++) {
					str += procla.ItemClass()+","+procla.PrintProductListElement(j)+"\n";
				}		
			}
			ProductClass procla = productclass_list.get(procla_list_size-1);
			for (int j=0; j<procla.ShowProductListSize()-1; j++) {
				str += procla.ItemClass()+","+procla.PrintProductListElement(j)+"\n";
			}
			str += procla.ItemClass()+","+procla.PrintProductListElement(procla.ShowProductListSize()-1);
		}
		
		fw.write(str);
		fw.close();
		
		ConfirmPanel("訊息","儲存成功！");	
	}

	/**
	 * 結束程式
	 */
	protected void ExitClick() {
		this.frmPurchaseTheCat.dispose();	
	}
	
	/**
	 * 顯示程式說明
	 */
	protected void AboutClick() {
		String about_title = "<br>Purchase the Cat<br>- 商品單位比價 -<br>";
		String about_content = "Purchase是一隻絕頂聰明的貓，會幫你計算單價最划算的商品！";
		String about_author = "version 1.0.0<br>發行者：R07222045 黃于瑄<br>發行日期：2019/12/30";
		String about = "<html>"+about_title+"<br>"+about_content+"<br><br>"+about_author+"<html>";
		JOptionPane.showMessageDialog(null, about,"關於", JOptionPane.PLAIN_MESSAGE, LoadImageIcon("/cat.png",100));
	}

	//======== input panel 動作 ========//

	/**
	 * 偵測類別的單位，自動設定單位在輸入欄
	 */
	protected void FindUnit() {
		//輸入的類別
		String item_class = this.txtItemclass.getText();
		
		//進入資料庫尋找是否有相同的類別
		int procla_list_size = productclass_list.size();
		for (int i=0; i<procla_list_size; i++) {
			if ( item_class.equals(productclass_list.get(i).ItemClass()) ) {
				String str = productclass_list.get(i).GetProductListElement(0).Unit();
				//找到後自動設定單位
				this.txtUnit.setText(str);
				//this.txtUnit.setEditable(false); //可以固定單位
			}
		}
	}
	
	/**
	 * 清除欄位內容
	 */
	protected void ClearClick() {
		//清除欄位
		this.txtItemclass.setText("");
		this.txtItem.setText("");
		this.txtPrice.setText("");
		this.txtNumber.setText("1");
		this.txtUnit.setText("個");
		this.txtAreaNote.setText("");
		//清除表格選取
		current_class = -1;
		current_item = -1;
	}

	/**
	 * 接收輸入並加入資料庫和輸出表格(重要功能)
	 * @param mode 1 新增; 2 修改
	 */
	protected void AddClick(int mode) {
		//進行修改時，確認有選取特定項目
		if(mode==2) {
			int row = table.getSelectedRow();
			if (row==-1) {
				ErrorPanel("錯誤","請選擇項目再進行修改");
				return;
			}
			for (int i=0; i<productclass_list.size(); i++) {
				row -= 1;
				if (row==-1) {
					ErrorPanel("錯誤","請選擇項目再進行修改");
					return;
				}
				else {
					for (int j=0; j<productclass_list.get(i).ShowProductListSize(); j++) {
						row -= 1;
						if (row<-1) break;
					}
				}	
			}
		}
		
		// 取得資料
		String item_class = this.txtItemclass.getText();
		String item = this.txtItem.getText();
		String str_price = this.txtPrice.getText();
		String str_number = this.txtNumber.getText();
		String unit = this.txtUnit.getText();
		String note = this.txtAreaNote.getText();
		
		//確認輸入無缺漏
		try {
			if (item_class.contentEquals("")) {
				throw new Exception("請輸入類別！");
			}
			if (item.contentEquals("")) {
				throw new Exception("請輸入名稱！");
			}
			if (str_price.contentEquals("")) {
				throw new Exception("請輸入價錢！");
			}
			if (str_number.contentEquals("")) {
				throw new Exception("請輸入數量！");
			}
			if (unit.contentEquals("")) {
				throw new Exception("請輸入單位！");
			}
		}catch(Exception e){
			ErrorPanel("錯誤", e.getMessage());
			return;
		}

		//將字串轉換成數值
		int price = 0;
		int number = 0;
		try {
			price = Integer.parseInt(str_price);
			
		} catch (java.lang.IllegalArgumentException e) {
			ErrorPanel("錯誤", " 價錢 請輸入數字！");
			return;
		}
		try {
			number = Integer.parseInt(str_number);
		} catch (java.lang.IllegalArgumentException e) {
			ErrorPanel("錯誤", " 數量 請輸入數字！");
			return;
		}
		
		//存進 Product 資料類型
		Product pro = new Product(item,price,number,unit,note);
		
		//修改模式下，先刪除舊資料
		if(mode==2) {
			productclass_list.get(current_class).Remove(current_item);
		}
		
		//新增至資料庫
		Add(item_class, pro);
		
		//顯示結果
		if(mode==1) {
			ClickShow(0);
			ConfirmPanel("訊息","新增成功！");
		}
		else if(mode==2) {
			ClickShow(0);
			ConfirmPanel("訊息","修改成功！");
		}
				
	}

	//======== show panel 動作 ========//	
	
	/**
	 * 偵測拉霸的選擇
	 */
	protected void ShowSelect() {
		show_key = comboBox.getSelectedIndex();	
	}

	/**
	 * 將資料輸出表格
	 * @param mode 顯示模式 0全部 >0只顯示第幾個類別
	 */
	protected void ClickShow(int mode) {
		//顯示全部
		if (mode==0) {
			//計算輸出的表格有幾行
			int procla_list_size = productclass_list.size();
			//System.out.println(productclass_list_size);
			row_num = procla_list_size;
			for (int i=0; i<procla_list_size;i++) {
				row_num += productclass_list.get(i).ShowProductListSize();
			}
			//System.out.println(row_num);
			
			//存入表個每一格的內容
			table_data = new Object[6][row_num];
				
			int row_iter = 0;
			for (int i=0; i<procla_list_size;i++) {
				table_data[0][row_iter] = (i+1)+". "+productclass_list.get(i).ItemClass();
				row_iter +=1;
				for (int j=0; j<productclass_list.get(i).ShowProductListSize();j++) {
					Product element = productclass_list.get(i).GetProductListElement(j);
					table_data[0][row_iter] = j+1;
					table_data[1][row_iter] = String.format("%.2f", element.Unit_Price())+"元/"+element.Unit();
					table_data[2][row_iter] = element.Item();
					table_data[3][row_iter] = element.Price();
					table_data[4][row_iter] = element.Number()+" "+element.Unit();
					table_data[5][row_iter] = element.Note();
					row_iter +=1;
				}
			}
		//顯示特定
		}else {
			int i = mode-1;
			row_num = productclass_list.get(i).ShowProductListSize()+1;
			table_data = new Object[6][row_num];
			table_data[0][0] = productclass_list.get(i).ItemClass();
			for (int j=0; j<productclass_list.get(i).ShowProductListSize();j++) {
				Product element = productclass_list.get(i).GetProductListElement(j);
				table_data[0][j+1] = j+1;
				table_data[1][j+1] = String.format("%.2f", element.Unit_Price())+"元/"+element.Unit();
				table_data[2][j+1] = element.Item();
				table_data[3][j+1] = element.Price();
				table_data[4][j+1] = element.Number()+" "+element.Unit();
				table_data[5][j+1] = element.Note();
			}
		}

		//輸出表格
		TableData td = new TableData();
		table.setModel(td);
		
	}

	//======== table panel 動作 ========//
	
	/**
	 * 點擊表格，在輸入欄位顯示
	 */
	protected void ShowColumn() {
		int row = table.getSelectedRow();
		String item_class = "";
		Product pro = new Product();
		//表格是全部顯示狀態
		if (show_key==0) {
			int procla_list_size = productclass_list.size();
			for (int i=0; i<procla_list_size; i++) {
				row -= 1;
				if (row==-1) {
					return;
				}
				else {
					for (int j=0; j<productclass_list.get(i).ShowProductListSize(); j++) {
						row -= 1;
						if (row==-1) {
							item_class = productclass_list.get(i).ItemClass();
							pro = productclass_list.get(i).GetProductListElement(j);
							current_class = i;
							current_item = j;
							break;
						}
						
					}
				}
			}
		//表格是單項顯示狀態
		} else {
			item_class = productclass_list.get(show_key-1).ItemClass();
			pro = productclass_list.get(show_key-1).GetProductListElement(row-1);
		}
		
		//顯示
		this.txtItemclass.setText(item_class);
		this.txtItem.setText(pro.Item());
		this.txtPrice.setText(Integer.toString(pro.Price()));
		this.txtNumber.setText(Integer.toString(pro.Number()));
		this.txtUnit.setText(pro.Unit());
		this.txtAreaNote.setText(pro.Note());
	}
	
	/**
	 * 點擊刪除
	 */
	protected void DeleteClick() {
		//表格是全部顯示狀態
		if (show_key==0) {
			int row = table.getSelectedRow();
			if (row==-1) {
				ErrorPanel("錯誤","請選擇一個項目！");	
				return;
			}
			int procla_list_size = productclass_list.size();
			for (int i=0; i<procla_list_size; i++) {
				row -= 1;
				if (row==-1) {
					if (OptionPanel("訊息","確認要刪除一個類別的商品？")==2) {
						return;
					}
					productclass_list.remove(i);
					comboBox.removeItemAt(i+1);
					break;
				}
				else {
					for (int j=0; j<productclass_list.get(i).ShowProductListSize(); j++) {
						row -= 1;
						if (row==-1) {
							if (OptionPanel("訊息","確定刪除？")==2) {
								return;
							}
							productclass_list.get(i).Remove(j);
							break;
						}
						
					}
				}
			}
			ClickShow(0);
			//顯示小視窗
			ConfirmPanel("訊息","刪除成功！");	
		} //表格是單項顯示狀態
		else {
			int row = table.getSelectedRow();
			if (row==-1) {
				ErrorPanel("錯誤","請選擇一個項目！");	
				return;
			}
			else if (row==0) {
				if (OptionPanel("訊息","確認要刪除一個類別的商品？")==2) {
					return;
				}
				productclass_list.remove(show_key-1);
				comboBox.removeItemAt(show_key);
				ClickShow(0);
				ConfirmPanel("訊息","刪除成功！");	
			}
			else {
				productclass_list.get(show_key-1).Remove(row-1);
				ClickShow(show_key);
				//顯示小視窗
				ConfirmPanel("訊息","刪除成功！");	
			}
		}
		
	}
		
	/**
	 * 點擊全部刪除
	 */
	protected void AllDeleteClick() {
		if (OptionPanel("訊息","確認要刪除所有資料？")==2) {
			return;
		}
		productclass_list.clear();
		comboBox.removeAllItems();
		comboBox.addItem("全部");
		ClickShow(0);
		ConfirmPanel("訊息","刪除成功！");
	}

	//======== 自定義method ========//
	
	/**
	 * 將商品加入productclass_list
	 * @param item_class 類別
	 * @param pro 商品資訊
	 */
	private void Add(String item_class, Product pro) {
		  //判斷新商品是不是已存在的類別
		  Boolean exist = false;
		  for (int i=0; i<productclass_list.size();i++) {
			  if ( item_class.equals(productclass_list.get(i).ItemClass()) ) {
			  	exist = true;
			  	//加入已知類別
				productclass_list.get(i).Add(pro);
				break;
			  }			
		  }
		  //建立新的類別
		  if (exist==false) {
		  	ProductClass pro_cla = new ProductClass(item_class, pro);
		  	productclass_list.add(pro_cla);
		  	comboBox.addItem(item_class);
		  }
	}
	
	/**
	 * 確認動作執行小視窗
	 * @param title 視窗標題
	 * @param content 內容
	 */
	protected void ConfirmPanel(String title, String content) {
		JOptionPane.showMessageDialog(null, content,title, JOptionPane.PLAIN_MESSAGE, LoadImageIcon("/cat.png",50));
	}
	
	/**
	 * 產生錯誤提醒小視窗
	 * @param title 視窗標題
	 * @param content 內容
	 */
	protected void ErrorPanel(String title, String content) {
		JOptionPane.showMessageDialog(null, content,title, JOptionPane.PLAIN_MESSAGE, LoadImageIcon("/ex.png",100,80));
	}
	
	/**
	 * 確認是否執行動作小視窗
	 * @param title 標題
	 * @param content 內容
	 * @return int 選擇結果 2取消 0確定
	 */
	protected int OptionPanel(String title, String content) {
		return JOptionPane.showConfirmDialog(null,content,title, JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE,LoadImageIcon("/ex.png",100,80));
	}
	
	/**
	 * 匯入圖片
	 * @param filename 圖片檔名
	 * @param size 圖片大小
	 * @return 圖片
	 */
	private ImageIcon LoadImageIcon(String filename, int size) {
		ImageIcon img = new ImageIcon(getClass().getResource(filename),"img1");
		Image img_img = img.getImage();
		Image img_img_small = img_img.getScaledInstance(size, size, Image.SCALE_DEFAULT);
		img = new ImageIcon(img_img_small);
		return img;
	}

	/**
	 * 匯入圖片 //overload：高與寬都進行調整
	 * @param filename 圖片檔名
	 * @param w 高
	 * @param h 寬
	 * @return 圖片
	 */
	private ImageIcon LoadImageIcon(String filename, int w,int h) {
		ImageIcon img = new ImageIcon(getClass().getResource(filename),"img1");
		Image img_img = img.getImage();
		Image img_img_small = img_img.getScaledInstance(w, h, Image.SCALE_DEFAULT);
		img = new ImageIcon(img_img_small);
		return img;
	}

}
