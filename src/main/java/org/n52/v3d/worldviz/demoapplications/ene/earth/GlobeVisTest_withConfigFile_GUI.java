/**
 * Copyright (C) 2015-2015 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 as published
 * by the Free Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of
 * the following licenses, the combination of the program with the linked
 * library is not considered a "derivative work" of the program:
 *
 *  - Apache License, version 2.0
 *  - Apache Software License, version 1.0
 *  - GNU Lesser General Public License, version 3
 *  - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *  - Common Development and Distribution License (CDDL), version 1.0.
 *
 * Therefore the distribution of the program linked with libraries licensed
 * under the aforementioned licenses, is permitted by the copyright holders
 * if the distribution is compliant with both the GNU General Public
 * License version 2 and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 */
package org.n52.v3d.worldviz.demoapplications.ene.earth;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.n52.v3d.worldviz.helper.RelativePaths;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

/**
 *
 * @author Adhitya Kamakshidasan
 */
// This class has elements which have auto generated by Netbeans - This could be
// modified in the future. :)
public class GlobeVisTest_withConfigFile_GUI extends javax.swing.JFrame {

	public GlobeVisTest_withConfigFile_GUI() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jTextField1 = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		jButton1 = new javax.swing.JButton();
		jLabel2 = new javax.swing.JLabel();
		jTextField2 = new javax.swing.JTextField();
		jButton2 = new javax.swing.JButton();
		submit = new javax.swing.JButton();
		jLabel3 = new javax.swing.JLabel();
		jTextField3 = new javax.swing.JTextField();
		jButton3 = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jTextField1.setEditable(false);

		jLabel1.setText("Xml Dataset: ");

		jButton1.setText("Find");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jLabel2.setText("Configuration File: ");

		jTextField2.setEditable(false);

		jButton2.setText("Find");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		submit.setText("Submit");
		submit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				submitActionPerformed(evt);
			}
		});

		jLabel3.setText("Output Directory:");

		jTextField3.setEditable(false);

		jButton3.setText("Find");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});
		
		JLabel lblMappingAttributeName = new JLabel();
		lblMappingAttributeName.setText("Attribute \r\nName: ");
		
		textField = new JTextField();
		textField.setToolTipText("enter attribute name");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addGap(223)
							.addComponent(submit))
						.addGroup(layout.createSequentialGroup()
							.addGap(31)
							.addGroup(layout.createParallelGroup(Alignment.TRAILING)
								.addComponent(jLabel2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jLabel1, Alignment.LEADING)
								.addComponent(jLabel3, Alignment.LEADING)
								.addComponent(lblMappingAttributeName, Alignment.LEADING, 0, 0, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
									.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(jTextField2)
										.addComponent(jTextField3, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
										.addComponent(jTextField1))
									.addGap(21)
									.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(jButton3, GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
										.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
											.addComponent(jButton1, GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
											.addComponent(jButton2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 266, GroupLayout.PREFERRED_SIZE))))
					.addGap(33))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(55)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel1)
						.addComponent(jButton1))
					.addGap(26)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMappingAttributeName, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(jLabel2, Alignment.TRAILING)
						.addComponent(jTextField2, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jButton2, Alignment.TRAILING))
					.addGap(18)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jLabel3)
						.addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jButton3))
					.addGap(70)
					.addComponent(submit)
					.addGap(43))
		);
		getContentPane().setLayout(layout);

		pack();
		
	}// </editor-fold>//GEN-END:initComponents

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		JFileChooser fileChooser = new JFileChooser(RelativePaths.EARTH_SUB_FOLDER);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Data Files", "xml");
		fileChooser.setFileFilter(filter);
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			String path = file.getAbsolutePath();
			jTextField1.setText(path);
		}
	}// GEN-LAST:event_jButton1ActionPerformed

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
		JFileChooser fileChooser = new JFileChooser(RelativePaths.DATA_FOLDER);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Config File", "xml");
		fileChooser.setFileFilter(filter);
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			String path = file.getAbsolutePath();
			jTextField2.setText(path);
		}
	}// GEN-LAST:event_jButton2ActionPerformed

	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
		JFileChooser fileChooser = new JFileChooser(RelativePaths.TEST_FOLDER);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			String path = file.getAbsolutePath();
			jTextField3.setText(path);
		}
	}// GEN-LAST:event_jButton3ActionPerformed

	private void submitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_submitActionPerformed
		String configurationFile = jTextField2.getText();
		String outputPath = jTextField3.getText();
		String attributeName = textField.getText();
		String xmlDataset = jTextField1.getText();

		if (xmlDataset.isEmpty() || configurationFile.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please check your paths");
		}

		else {
			GlobeVisTest_withConfigFile app;
			app = new GlobeVisTest_withConfigFile();

			if (!outputPath.isEmpty()) {
				app.setOutputFile(outputPath);
			}
			app.setConfig(xmlDataset, configurationFile, attributeName);
			try {
				app.run();
				JOptionPane.showMessageDialog(null, "Done! File created at " + app.getOutputFile());
				// Close the frame!
				setVisible(false);
				dispose();
			} catch (Exception ex) {
				Logger.getLogger(GlobeVisTest_withConfigFile_GUI.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}// GEN-LAST:event_submitActionPerformed

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting
		// code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.
		 * html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(GlobeVisTest_withConfigFile_GUI.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(GlobeVisTest_withConfigFile_GUI.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(GlobeVisTest_withConfigFile_GUI.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(GlobeVisTest_withConfigFile_GUI.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GlobeVisTest_withConfigFile_GUI().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;
	private javax.swing.JTextField jTextField3;
	private javax.swing.JButton submit;
	private JTextField textField;
}
