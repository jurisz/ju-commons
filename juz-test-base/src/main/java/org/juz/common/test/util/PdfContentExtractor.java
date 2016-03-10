package org.juz.common.test.util;

import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Throwables.propagate;

public class PdfContentExtractor {

	private static final Logger log = LoggerFactory.getLogger(PdfContentExtractor.class);

	public static String extractPdfContentFromFile(String pathToPdf) {
		try {
			FileInputStream pdfInputStream = new FileInputStream(pathToPdf);
			return extractContentFrom(pdfInputStream);
		} catch (IOException e) {
			throw propagate(e);
		}
	}

	public static String extractContentFrom(InputStream pdfInputStream) throws IOException {
		PDDocument pdfDocument = null;
		try {
			pdfDocument = PDDocument.load(pdfInputStream);
			return new PDFTextStripper().getText(pdfDocument);
		} finally {
			IOUtils.closeQuietly(pdfInputStream);
			closeQuietly(pdfDocument);
		}
	}

	private static void closeQuietly(PDDocument pdDocument) {
		try {
			if (pdDocument != null) {
				pdDocument.close();
			}
		} catch (IOException ioe) {
			log.warn("Could not close PDDocument");
		}
	}
}
