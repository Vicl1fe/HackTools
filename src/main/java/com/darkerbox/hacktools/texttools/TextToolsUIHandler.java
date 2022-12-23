package com.darkerbox.hacktools.texttools;

import burp.IBurpExtenderCallbacks;
import burp.ITab;
import com.darkerbox.hacktools.UIHandler;
import com.darkerbox.hacktools.dfquery.Dfquery;
import com.darkerbox.utils.StrUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.stream.Collectors;

public class TextToolsUIHandler implements UIHandler {
	private String tabname = "文本处理";
	public IBurpExtenderCallbacks callbacks;

	public JPanel mainPanel;
	public JTextArea inputJTextArea;
	public JTextArea outputJTextarea;
	public JTextField jTextField;


	@Override
	public void init() {

	}

	@Override
	public JPanel getPanel(IBurpExtenderCallbacks callbacks) {
		this.callbacks = callbacks;
		// DfqueryTab
		mainPanel = new JPanel();

		inputJTextArea = getInputJTextArea();
		outputJTextarea = getOutputJTextArea();
		jTextField = getTextField();
		// 去重按钮
		JButton removeDuplicateButton = getRemoveDuplicateButton();


		mainPanel.setAlignmentX(0.0f);
		GridBagLayout gridBagLayout = new GridBagLayout();
		//设置了总共有2列
		gridBagLayout.columnWidths = new int[]{0,0};
		//设置了总共有2行
		gridBagLayout.rowHeights = new int[]{0, 0,0,0,0,0,0,0,0,0};
		//设置了列的宽度，貌似没用
		gridBagLayout.columnWeights = new double[]{0.8,0.2};
		//第一行的高度占0.5，第二行占0.5
		gridBagLayout.rowWeights = new double[]{0.1,0.1,0.1,0.1,0.05,0.1,0.1,0.1,0.1,0.15};
		// 设置布局
		mainPanel.setLayout(gridBagLayout);


		GridBagConstraints constraints=new GridBagConstraints();
		// BOTH使组件完全填充该区域
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 0, 10, 0);
		// 组件起始x坐标
		constraints.gridx = 0;
		// 组件起始y坐标
		constraints.gridy = 0;
		// 组件宽占用的1个格子
		constraints.gridwidth = 1;
		// 组件高占用的2个格子
		constraints.gridheight = 5;
		constraints.weightx = 0;
		constraints.weighty = 0;
		mainPanel.add(new JScrollPane(inputJTextArea),constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 2;
		constraints.weightx = 0;
		constraints.weighty = 0;
		mainPanel.add(removeDuplicateButton,constraints);

//		constraints.gridx = 0;
//		constraints.gridy = 4;
//		constraints.gridwidth = 1;
//		constraints.gridheight = 1;
//		constraints.weightx = 0;
//		constraints.weighty = 0;
//		mainPanel.add(getResultJLabel(),constraints);

		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
//		mainPanel.add(jTextField,constraints);

		constraints.insets = new Insets(10, 0, 0, 0);
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 5;
		constraints.weightx = 0;
		constraints.weighty = 0;
		mainPanel.add(new JScrollPane(outputJTextarea),constraints);


		return mainPanel;
	}

	@Override
	public String getTabName() {
		return this.tabname;
	}

	public JTextArea getInputJTextArea() {
		JTextArea inputTextarea = new JTextArea();

		// 设置自动换行
		inputTextarea.setLineWrap(true);
		// 设置大小
		Dimension preferredSize = new Dimension(1000,350);
		inputTextarea.setPreferredSize(preferredSize);
//		inputTextarea.setSize(10,10);
		return inputTextarea;
	}

	public JTextArea getOutputJTextArea() {
		JTextArea outputTextarea = new JTextArea();


		// 设置自动换行
		outputTextarea.setLineWrap(true);
		// 设置大小
		Dimension preferredSize = new Dimension(1000,350);
		outputTextarea.setPreferredSize(preferredSize);
//		outputTextarea.setSize(10,10);
		return outputTextarea;
	}

	public JTextField getTextField() {
		JTextField textField = new JTextField();

		// 设置大小
		Dimension preferredSize = new Dimension(1000,122);
		textField.setPreferredSize(preferredSize);
//		textField.setSize(10,10);

		return textField;
	}

	// 去重按钮
	public JButton getRemoveDuplicateButton(){
		JButton button = new JButton();
		button.setText("去重");
		button.setAlignmentX(0.0f);
		//设置按钮尺寸
		Dimension preferredSize = new Dimension(150,30);
		button.setPreferredSize(preferredSize);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] input = inputJTextArea.getText().split("\n");
				java.util.List<String> res = StrUtils.removeDuplicationByStream(input);
				outputJTextarea.setText(StrUtils.connectStringOfOne(res,"\n"));
			}
		});

		return button;
	}

	public JLabel getResultJLabel(){
		JLabel label1 = new JLabel("");
		label1.setForeground(Color.BLACK);
		label1.setFont(new Font("Nimbus", 1, 20));
		label1.setAlignmentX(0.0f);
		return label1;
	}

}
