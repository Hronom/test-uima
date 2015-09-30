package com.github.hronom.test.uima.annotator.two;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestAnnotTwo extends JCasAnnotator_ImplBase {
    private Pattern mYorktownPattern = Pattern.compile("\\b[0-4]\\d-[0-2]\\d\\d\\b");
    private Pattern mHawthornePattern = Pattern.compile("\\b[G1-4][NS]-[A-Z]\\d\\d\\b");

    public TestAnnotTwo() {
    }

    public void initialize(UimaContext aContext) throws ResourceInitializationException {
        super.initialize(aContext);
    }

    public void process(JCas aJCas) {
        // get document text
        String docText = aJCas.getDocumentText();
        // search for Yorktown room numbers
        Matcher matcher = mYorktownPattern.matcher(docText);
        int pos = 0;
        while (matcher.find(pos)) {
            // found one - create annotation
            RoomNumber annotation = new RoomNumber(aJCas);
            annotation.setBegin(matcher.start());
            annotation.setEnd(matcher.end());
            annotation.setBuilding("Yorktown");
            annotation.addToIndexes();
            pos = matcher.end();
        }
        // search for Hawthorne room numbers
        matcher = mHawthornePattern.matcher(docText);
        pos = 0;
        while (matcher.find(pos)) {
            // found one - create annotation
            RoomNumber annotation = new RoomNumber(aJCas);
            annotation.setBegin(matcher.start());
            annotation.setEnd(matcher.end());
            annotation.setBuilding("Hawthorne");
            annotation.addToIndexes();
            pos = matcher.end();
        }
    }
}