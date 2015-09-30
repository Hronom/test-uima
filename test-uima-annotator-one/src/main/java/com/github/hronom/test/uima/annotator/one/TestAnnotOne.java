package com.github.hronom.test.uima.annotator.one;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestAnnotOne extends JCasAnnotator_ImplBase {
    private Pattern wordPattern = Pattern.compile("\\b(\\w+)\\b");

    public TestAnnotOne() {
    }

    public void initialize(UimaContext aContext) throws ResourceInitializationException {
        super.initialize(aContext);
    }

    public void process(JCas aJCas) {
        String docText = aJCas.getDocumentText();
        Matcher matcher = wordPattern.matcher(docText);
        while (matcher.find()) {
            Word annotation = new Word(aJCas);
            annotation.setBegin(matcher.start());
            annotation.setEnd(matcher.end());
            annotation.setText(matcher.group());
            annotation.addToIndexes();
        }
    }
}