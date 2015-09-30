package com.github.hronom.test.uima.gui;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.XMLInputSource;
import org.apache.uima.util.XmlCasSerializer;
import org.xml.sax.SAXException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.*;

public class App implements ActionListener {
    private static final String app_name = "Taggers_test";
    private final String start_text = "Hello world!";

    private final JTextArea input_text_area;
    private final JComboBox lang_combo_box;
    private final JTextArea output_text_area;

    public App() {
        JPanel main_panel;
        main_panel = new JPanel();
        main_panel.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE)
        );

        GridBagLayout layout = new GridBagLayout();
        main_panel.setLayout(layout);

        GridBagConstraints constraint = new GridBagConstraints();
        constraint.insets = new Insets(3, 3, 3, 3);
        constraint.weightx = 1;
        constraint.weighty = 0;
        constraint.gridwidth = 1;
        constraint.anchor = GridBagConstraints.CENTER;

        {
            input_text_area = new JTextArea(start_text);
            input_text_area.setWrapStyleWord(true);
            input_text_area.setAutoscrolls(true);

            JScrollPane scroll_pane = new JScrollPane(input_text_area);
            scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroll_pane.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE)
            );

            constraint.weightx = 1;
            constraint.weighty = 1;
            constraint.gridx = 0;
            constraint.gridy = 1;
            constraint.gridwidth = 4;
            constraint.gridheight = 1;
            constraint.fill = GridBagConstraints.BOTH;
            main_panel.add(scroll_pane, constraint);
        }

        {
            lang_combo_box = new JComboBox();
            lang_combo_box.setSelectedItem(null);

            constraint.weightx = 1;
            constraint.weighty = 0;
            constraint.gridx = 0;
            constraint.gridy = 2;
            constraint.gridwidth = 1;
            constraint.gridheight = 1;
            constraint.fill = GridBagConstraints.BOTH;
            main_panel.add(lang_combo_box, constraint);
        }

        {
            JButton translate_button;
            translate_button = new JButton("Process text");

            translate_button.addActionListener(this);

            constraint.weightx = 0;
            constraint.gridx = 4;
            constraint.gridy = 0;
            constraint.gridwidth = 1;
            constraint.gridheight = 2;
            constraint.fill = GridBagConstraints.HORIZONTAL;
            constraint.anchor = GridBagConstraints.BELOW_BASELINE;
            main_panel.add(translate_button, constraint);
        }

        {
            output_text_area = new JTextArea();
            output_text_area.setWrapStyleWord(true);
            output_text_area.setAutoscrolls(true);

            JScrollPane scroll_pane = new JScrollPane(output_text_area);
            scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroll_pane.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

            constraint.weightx = 1;
            constraint.weighty = 1;
            constraint.gridx = 0;
            constraint.gridy = 3;
            constraint.gridwidth = 5;
            constraint.gridheight = 1;
            constraint.fill = GridBagConstraints.BOTH;
            main_panel.add(scroll_pane, constraint);
        }

        JFrame frame = new JFrame(app_name);
        frame.setContentPane(main_panel);
        frame.setPreferredSize(new Dimension(640, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    // The entry-point of the application.
    public static void main(String args[]) {
        new App();
    }

    public void actionPerformed(ActionEvent e) {
        if (input_text_area.getText().isEmpty()) {
            return;
        }

        try {
            // http://uima.apache.org/d/uimaj-2.6.0/tutorials_and_users_guides.pdf
            // 3.2.1. Instantiating an Analysis Engine

            // get Resource Specifier from XML file
            XMLInputSource in;
            in
                = new XMLInputSource("MainDescriptor.xml");

            ResourceSpecifier specifier = UIMAFramework.getXMLParser().parseResourceSpecifier(in);
            // create AE here
            AnalysisEngine ae = UIMAFramework.produceAnalysisEngine(specifier);

            // create a CAS
            CAS aCasView = ae.newCAS();
            // analyze a document
            aCasView.setDocumentText(input_text_area.getText());
            ae.process(aCasView);

            File file;
            file = new File("output.xml");

            FileOutputStream fos;
            fos = new FileOutputStream(file);

            // doSomethingWithResults(aCasView);
            XmlCasSerializer.serialize(aCasView, fos);

            aCasView.reset();

            // done
            ae.destroy();
        } catch (IOException | AnalysisEngineProcessException | InvalidXMLException | ResourceInitializationException | SAXException e1) {
            e1.printStackTrace();
        }
    }
}
