package com.darkerbox.hacktools.dfquery;

import burp.IBurpExtenderCallbacks;
import com.darkerbox.hacktools.UIHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class DfqueryUIHandler implements UIHandler {
	private final String tabname = "杀软查询";
	private JPanel mainPanel;


	public JTextArea textArea;
	public JButton jbutton;
	public JTable jTable;
	public IBurpExtenderCallbacks callbacks;

	// 表格标题
	Vector tabletitle = new Vector();

	@Override
	public void init() {

	}

	@Override
	public JPanel getPanel(IBurpExtenderCallbacks callbacks) {
		this.callbacks = callbacks;
		// DfqueryTab
		mainPanel = new JPanel();

		// 设置垂直对齐方式。且0.0f表示起始坐标为左上角
		mainPanel.setAlignmentX(0.0f);

		GridBagLayout gridBagLayout = new GridBagLayout();
		//设置了总共有2列
		gridBagLayout.columnWidths = new int[]{0,0};
		//设置了总共有2行
		gridBagLayout.rowHeights = new int[]{0, 0};
		//设置了列的宽度，貌似没用
		gridBagLayout.columnWeights = new double[]{0.7,0.3};
		//第一行的高度占0.5，第二行占0.5
		gridBagLayout.rowWeights = new double[]{0.5,0.5};

		// 设置布局
		mainPanel.setLayout(gridBagLayout);

		// 获取需要的组件
		textArea = getJTextArea();
		jbutton = getJButton();
		jTable = getJScrollPane();

		GridBagConstraints constraints=new GridBagConstraints();

		// BOTH使组件完全填充该区域
		constraints.fill = GridBagConstraints.BOTH;
		// 组件起始x坐标
		constraints.gridx = 0;
		// 组件起始y坐标
		constraints.gridy = 0;
		// 组件宽占用的1个格子
		constraints.gridwidth = 1;
		// 组件高占用的2个格子
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		// Insets用来设置组件与其显示区域边缘之间的空间，感觉是内边距
//		constraints.insets = new Insets(0, 0, 5, 5);
		mainPanel.add(new JScrollPane(textArea),constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		mainPanel.add(jbutton,constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		mainPanel.add(new JScrollPane(jTable),constraints);

		return mainPanel;
	}

	@Override
	public String getTabName() {
		return this.tabname;
	}

	// 设置文本域
	public JTextArea getJTextArea() {
		JTextArea textarea = new JTextArea();

		// 设置自动换行
//		textarea.setLineWrap(true);
		// 设置大小
		Dimension preferredSize = new Dimension(1000,350);
		textarea.setPreferredSize(preferredSize );
//		textarea.setFont(new Font("Nimbus", 1, 16));
		return textarea;
	}

	// 设置按钮
	public JButton getJButton(){

		JButton button=new JButton ("查询");
		button.setAlignmentX(0.0f);
		//设置按钮尺寸
//		Dimension preferredSize = new Dimension(200,30);
//		button.setPreferredSize(preferredSize );


		// 添加点击事件
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String[] inputary= textArea.getText().split("\n");
				Vector data;
				DefaultTableModel model = new DefaultTableModel(new Vector(),tabletitle);
				// 查询操作
				PrintWriter aa = new PrintWriter(callbacks.getStdout(), true);
				// 定义已经查过的列表
				List<String> quchonglist = new ArrayList<String>();
				for (int inputstrnum = 0; inputstrnum < inputary.length; inputstrnum++) {

					java.util.List<String> resultary = Dfquery.query(inputary[inputstrnum]);

					if (resultary!=null && !quchonglist.contains(resultary.get(0))){
						quchonglist.add(resultary.get(0));
						data = new Vector();
						data.add(String.valueOf(inputstrnum));
						data.add(resultary.get(0));
						data.add(resultary.get(1));
						model.addRow(data);
					}
				}
				jTable.setModel(model);

			}
		});
		return button;
	}

	// 设置表格
	public JTable getJScrollPane(){
		// 初始化的时候表格的时候也可以设置行和列，也可根据模型来设置
		jTable = new JTable();

		// 初始化标题
		tabletitle.add("ID");
		tabletitle.add("进程");
		tabletitle.add("杀软");

		// 居中显示
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		jTable.setDefaultRenderer(Object.class, r);



		// 初始化表格模型，并且设置标题，并且初始化20行
		DefaultTableModel tableModel=new DefaultTableModel(tabletitle,20);
		// 设置模型
		jTable.setModel(tableModel);

		// 设置列边框
//		TableColumn column = jTable.getColumnModel().getColumn(0);
//		column.setWidth(5);


		jTable.setAlignmentX(0.0f);
		// 为了能够看到标题，您应该将表格放在 JScrollPane 中。
		return jTable;
	}


}
