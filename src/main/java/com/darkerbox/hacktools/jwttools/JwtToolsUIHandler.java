package com.darkerbox.hacktools.jwttools;

import burp.IBurpExtenderCallbacks;
import com.darkerbox.hacktools.UIHandler;
import com.darkerbox.hacktools.dfquery.Dfquery;
import com.darkerbox.utils.UiUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class JwtToolsUIHandler implements UIHandler {
	private final String tabname = "JwtUtils";
	private JPanel mainPanel;


	public JTextArea textArea;
	public JTextArea textAreaJwtToken;
	public JButton jbutton;
	public JTable jTable;
	public IBurpExtenderCallbacks callbacks;

	public JTextField jTextField1;
	public JTextField jTextField2;
	public JTextField jTextField3;

	public JLabel jLabel1;
	public JLabel jLabel2;
	public JLabel jLabel3;

	public JButton jbuttonEnc;
	public JButton jbuttonDec;
	public JButton jbuttonValidation;

	public JTextField jTextField4;

	@Override
	public void init() {

	}

	@Override
	public JPanel getPanel(IBurpExtenderCallbacks callbacks) {
		this.callbacks = callbacks;
		// Panel
		mainPanel = new JPanel();

		// 设置垂直对齐方式。且0.0f表示起始坐标为左上角
		mainPanel.setAlignmentX(0.0f);

		GridBagLayout gridBagLayout = new GridBagLayout();
		//设置了总共有2列
		gridBagLayout.columnWidths = new int[]{0,0,0,0,0};
		//设置了总共有2行
		gridBagLayout.rowHeights = new int[]{0,0,0,0};
		//设置了列的宽度，貌似没用
		gridBagLayout.columnWeights = new double[]{0.01,0.01,0.01,0.1,0.1,0.6};
		//第一行的高度占0.5，第二行占0.5
		gridBagLayout.rowWeights = new double[]{0.02,0.02,0.02,0.02,0.5};
		// 设置布局
		mainPanel.setLayout(gridBagLayout);

		// 获取需要的组件
		// 1.三个输入字段
		jTextField1 = getTextField(); // header
		jTextField2 = getTextField(); // payload
		jTextField3 = getTextField(); // 密钥
		// 2.三个Label
		jLabel1 = getLabel1();
		jLabel1.setText("  Jwt Header：");
		jLabel2 = getLabel1();
		jLabel2.setText("  Jwt Body：");
		jLabel3 = getLabel1();
		jLabel3.setText("  Jwt Sign：");
		// 3.加解密按钮、验证按钮
		jbuttonValidation = getJButtonValidation(); // 验证按钮
		jbuttonEnc = getJButtonEnc(); // 加密按钮
		jbuttonDec = getJButtonDec(); // 解密按钮
		// 文本输出
		textArea = getJTextArea(); // 文本输出区


		GridBagConstraints constraints=new GridBagConstraints();

		// BOTH使组件完全填充该区域
		constraints.fill = GridBagConstraints.BOTH;
		// 组件起始x坐标
		constraints.gridx = 0;
		// 组件起始y坐标
		constraints.gridy = 0;
		// 组件宽占用的1个格子
//		constraints.gridwidth = 1;
		// 组件高占用的2个格子
//		constraints.gridheight = 1;
		// Insets用来设置组件与其显示区域边缘之间的空间，感觉是内边距
		mainPanel.add(jLabel1,constraints);

		
		// 组件起始x坐标
		constraints.gridx = 0;
		// 组件起始y坐标
		constraints.gridy = 1;
		mainPanel.add(jLabel2,constraints);

		
		// 组件起始x坐标
		constraints.gridx = 0;
		// 组件起始y坐标
		constraints.gridy = 2;
		mainPanel.add(jLabel3,constraints);

		

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
//		constraints.gridheight = 1;
//		constraints.weightx = 1.0;
//		constraints.weighty = 1.0;
		constraints.insets = new Insets(5, 0, 5, 5);
		mainPanel.add(jTextField1,constraints);

		
		constraints.gridx = 1;
		constraints.gridy = 1;
		mainPanel.add(jTextField2,constraints);

		
		constraints.gridx = 1;
		constraints.gridy = 2;
		mainPanel.add(jTextField3,constraints);



		constraints.gridx = 4;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		mainPanel.add(jbuttonEnc,constraints);

		
		constraints.gridx = 4;
		constraints.gridy = 2;
//		mainPanel.add(jbuttonDec,constraints);

		constraints.gridx = 4;
		constraints.gridy = 0;
		mainPanel.add(jbuttonValidation,constraints);

		constraints.gridx = 5;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 3;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		mainPanel.add(textArea,constraints);


		// 分割线
		JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
		sep.setBackground(new Color(153,153,153));
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 6;
		mainPanel.add(sep,constraints);

//		textAreaJwtToken = getJTextArea();



		return mainPanel;
	}

	@Override
	public String getTabName() {
		return this.tabname;
	}

	// 设置文本域
	public JTextArea getJTextArea() {
		JTextArea textarea = new JTextArea();
		textarea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		// 设置自动换行
//		textarea.setLineWrap(true);
		// 设置大小
//		Dimension preferredSize = new Dimension(1000,350);
//		textarea.setPreferredSize(preferredSize );
//		textarea.setFont(new Font("Nimbus", 1, 16));
		return textarea;
	}

	// 设置按钮
	public JButton getJButtonEnc(){

		JButton button=new JButton ("加密 >>>");
		button.setAlignmentX(0.0f);
		//设置按钮尺寸
//		Dimension preferredSize = new Dimension(200,30);
//		button.setPreferredSize(preferredSize );


		// 添加点击事件
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


			}
		});
		return button;
	}
	public JButton getJButtonDec(){

		JButton button=new JButton ("<<< 解密");
		button.setAlignmentX(0.0f);
		//设置按钮尺寸
//		Dimension preferredSize = new Dimension(200,30);
//		button.setPreferredSize(preferredSize );


		// 添加点击事件
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {



			}
		});
		return button;
	}

	public JTextField getTextField() {
		JTextField textField = new JTextField();

		// 设置大小
		Dimension preferredSize = new Dimension(200,5);
		textField.setPreferredSize(preferredSize);
		textField.setSize(10,10);
//		textField.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		return textField;
	}

	public JButton getJButtonValidation(){

		JButton button=new JButton ("<<< 验证密钥");
		button.setAlignmentX(0.0f);
		//设置按钮尺寸
//		Dimension preferredSize = new Dimension(200,30);
//		button.setPreferredSize(preferredSize );


		// 添加点击事件
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


			}
		});
		return button;
	}

	public JLabel getLabel1(){
		JLabel label = new JLabel();
//		label.setFont(UiUtils.getSerifFont());
//		button.setText("解密");
//		button.setAlignmentX(0.0f);
		//设置按钮尺寸
//		Dimension preferredSize = new Dimension(100,50);
//		label.setPreferredSize(preferredSize);
//		label.setBorder(BorderFactory.createLineBorder(Color.CYAN));


		return label;
	}




}
