package gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import CRUD.BorrowFormCRUD;
import Execution.AccountEXE;
import Execution.BorrowFormEXE;
import gui.dialogs.EditBorrowForm;

public class BorrowFormPanel extends JPanel {
	
	private JTable jtblBorrowerForm;
	DefaultTableModel objtableModel;
	protected BorrowFormCRUD borrowFormCRUD;

	/**
	 * Create the panel.
	 */
	public BorrowFormPanel() {
		
		setBackground(new Color(255, 255, 255));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setPreferredSize(new Dimension(625, 400));
		setMinimumSize(new Dimension(625, 400));
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel jpnlHeader = new JPanel();
		jpnlHeader.setBackground(new Color(255, 255, 255));
		jpnlHeader.setAlignmentX(0.0f);
		jpnlHeader.setAlignmentY(0.0f);
		add(jpnlHeader);
		jpnlHeader.setLayout(new BoxLayout(jpnlHeader, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel = new JLabel("Borrow Form Panel");
		lblNewLabel.setForeground(new Color(128, 0, 128));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
		lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 24));
		lblNewLabel.setAlignmentY(0.0f);
		jpnlHeader.add(lblNewLabel);
		
		JPanel jpnlButtons = new JPanel();
		jpnlButtons.setBackground(new Color(255, 255, 255));
		jpnlButtons.setAlignmentX(0.0f);
		jpnlButtons.setAlignmentY(0.0f);
		jpnlButtons.setMaximumSize(new Dimension(32767, 75));
		FlowLayout flowLayout = (FlowLayout) jpnlButtons.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		jpnlHeader.add(jpnlButtons);
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAlignmentX(0.0f);
		add(scrollPane);
		
		jtblBorrowerForm = new JTable();
		jtblBorrowerForm.setRowHeight(25);
		jtblBorrowerForm.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		
		
		String[] arrColumnNames = {"Borrow Form ID", "Account ID", "ISBN", "Status", "Issue Date", "Due Date"};
		DefaultTableModel objtableModel = new DefaultTableModel(arrColumnNames, 0);
		BorrowFormEXE.ReadAccountTable(objtableModel);
		jtblBorrowerForm.setModel(objtableModel);
			
		
		scrollPane.setViewportView(jtblBorrowerForm);
		
		
		
		JButton jbtnUpdate = new JButton("Update");
		jbtnUpdate.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		jbtnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowIndex = jtblBorrowerForm.getSelectedRow();
				
				if(rowIndex == -1) {
					JOptionPane.showMessageDialog(
							null,
							"Please select a borrow form first before updating.",
							"Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				int borrowID = (int) objtableModel.getValueAt(rowIndex, 0);
				
				EditBorrowForm editBorrowDialog = new EditBorrowForm(borrowID);
				editBorrowDialog.setVisible(true);
				
				
			}
		});
		
		jpnlButtons.add(jbtnUpdate);
		
		JButton jbtnDelete = new JButton("Delete");
		jbtnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					int rowIndex = jtblBorrowerForm.getSelectedRow();
					
					if(rowIndex == -1) {
						JOptionPane.showMessageDialog(
								null,
								"Please select a student first before deleting.",
								"Warning",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					if(JOptionPane.showConfirmDialog(null, "Are you sure?") == JOptionPane.YES_OPTION) {
						int borrowId = (int) objtableModel.getValueAt(rowIndex, 0);
						 
						BorrowFormCRUD.DeleteBorrowForm(borrowId);
					//refresh the table
						refreshTable();
					}
			}
		});
		jbtnDelete.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		jpnlButtons.add(jbtnDelete);
		
	}
	public void refreshTable() {
		objtableModel.setRowCount(0);
		AccountEXE.ReadAccountTable(objtableModel);
	}
}
