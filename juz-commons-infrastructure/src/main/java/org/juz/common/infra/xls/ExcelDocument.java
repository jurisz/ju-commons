package org.juz.common.infra.xls;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.juz.common.util.CachedExpressions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ExcelDocument {

	private static final int autoFlushOnEveryNRecord = 300;

	// Very specific to XSSF
	public static final String dateFormat = "m/d/yy";
	public static final String dateTimeFormat = "m/d/yy h:mm";

	private SXSSFWorkbook wb;

	private SXSSFSheet sh;

	private CellStyle dateStyle;

	private CellStyle dateTimeStyle;

	private DataType[] columnTypes;

	private CachedExpressions expressions = new CachedExpressions();

	public ExcelDocument() {

		// turn off auto-flushing and accumulate all rows in memory
		wb = new SXSSFWorkbook(-1);
		wb.setCompressTempFiles(true);

		sh = (SXSSFSheet) wb.createSheet();

		CreationHelper createHelper = wb.getCreationHelper();
		dateStyle = wb.createCellStyle();
		dateStyle.setDataFormat(createHelper.createDataFormat().getFormat(dateFormat));

		dateTimeStyle = wb.createCellStyle();
		dateTimeStyle.setDataFormat(createHelper.createDataFormat().getFormat(dateTimeFormat));
	}

	public void writeHeaders(String[] headers) {
		Row row = sh.createRow(0);
		CellStyle titleStyle = wb.createCellStyle();
		//With gray need Foreground not Background!!!
		titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		for (int i = 0; i < headers.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(titleStyle);
			cell.setCellValue(headers[i]);
		}

		columnTypes = new DataType[headers.length];
	}

	/**
	 * supports doted properties, also null safe
	 * uses spring expressions
	 */
	public void writeRow(int rowNum, Object bean, String[] propertyNames) {
		if (bean == null) {
			return;
		}
		Object[] values = new Object[propertyNames.length];
		for (int i = 0; i < propertyNames.length; i++) {
			values[i] = expressions.getValue(bean, propertyNames[i]);
		}
		writeRow(rowNum, values);
	}

	public void writeRow(int rowNum, Object[] values) {
		Row row = sh.createRow(rowNum);
		for (int col = 0; col < values.length; col++) {
			Object val = values[col];
			if (val != null) {
				DataType type = getDataType(col, val);
				Cell cell = row.createCell(col);
				setCellValue(cell, type, val);
			}
		}

		if (rowNum % autoFlushOnEveryNRecord == 0) {
			flushRows();
		}
	}

	private DataType getDataType(int col, Object val) {
		DataType type = columnTypes[col];
		if (type == null) {
			type = getObjectType(val);
			columnTypes[col] = type;
		}
		return type;
	}

	private DataType getObjectType(Object val) {
		if (val instanceof LocalDate) {
			return DataType.DATE;
		} else if (val instanceof LocalDateTime) {
			return DataType.DATETIME;
		} else if (val instanceof Number) {
			return DataType.NUMBER;
		} else if (val instanceof Boolean) {
			return DataType.BOOLEAN;
		} else {
			return DataType.STRING;
		}
	}

	private void setCellValue(Cell cell, DataType dataType, Object val) {
		switch (dataType) {
			case STRING:
				cell.setCellValue(val.toString());
				break;
			case NUMBER:
				cell.setCellValue(((Number) val).doubleValue());
				break;
			case DATE:
				cell.setCellStyle(dateStyle);
				cell.setCellValue(java.sql.Date.valueOf((LocalDate) val));
				break;
			case DATETIME:
				cell.setCellStyle(dateTimeStyle);
				cell.setCellValue(Timestamp.valueOf((LocalDateTime) val));
				break;
			case BOOLEAN:
				cell.setCellValue((Boolean) val);
				break;
			default:
		}
	}

	private void flushRows() {
		try {
			sh.flushRows();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void exportToFile(File targetFile) {
		flushRows();

		try (FileOutputStream out = new FileOutputStream(targetFile)) {
			wb.write(out);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		dispose();
	}

	public void dispose() {
		wb.dispose();
	}

	public enum DataType {
		NUMBER, DATE, DATETIME, STRING, BOOLEAN
	}

}
