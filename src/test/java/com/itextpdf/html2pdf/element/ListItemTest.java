package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;

@Category(IntegrationTest.class)
public class ListItemTest extends ExtendedITextTest {

	public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/ListItemTest/";
	public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/ListItemTest/";

	@BeforeClass
	public static void beforeClass() {
		createDestinationFolder(destinationFolder);
	}

	@Test
	@LogMessages(messages = {@LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 14)})
	public void rtlListItemInsideLtrOrderedListTest() throws IOException, InterruptedException {
		String name = "rtlListItemInsideLtrOrderedListTest";
		HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"),
		                           new File(destinationFolder + name +".pdf"));
		Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf",
		                                                     sourceFolder + "cmp_" + name + ".pdf",
		                                                     destinationFolder, "diff01_"));
	}

	@Test
	@LogMessages(messages = {@LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 16)})
	public void listItemWithDifferentDirAndPositionInsideTest() throws IOException, InterruptedException {
		String name = "listItemWithDifferentDirAndPositionInsideTest";
		HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"),
		                           new File(destinationFolder + name +".pdf"));
		Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf",
		                                                     sourceFolder + "cmp_" + name + ".pdf",
		                                                     destinationFolder, "diff01_"));
	}

	@Test
	@LogMessages(messages = {@LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 12)})
	public void rtlListItemInsideLtrUnorderedListTest() throws IOException, InterruptedException {
		String name = "rtlListItemInsideLtrUnorderedListTest";
		HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"),
		                           new File(destinationFolder + name +".pdf"));
		Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf",
		                                                     sourceFolder + "cmp_" + name + ".pdf",
		                                                     destinationFolder, "diff01_"));
	}

	@Test
	@LogMessages(messages = {@LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 16)})
	public void differentListItemsInsideDifferentListsWithDifferentDirections() throws IOException, InterruptedException {
		String name = "differentListItemsInsideDifferentListsWithDifferentDirections";
 		HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"),
		                           new File(destinationFolder + name +".pdf"));
		Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf",
		                                                     sourceFolder + "cmp_" + name + ".pdf",
		                                                     destinationFolder, "diff01_"));
	}
}
