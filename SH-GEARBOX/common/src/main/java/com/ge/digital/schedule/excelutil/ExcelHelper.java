package com.ge.digital.schedule.excelutil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.ge.digital.spo.chain.controller.outputbean.AndonDetail;

/**
 * @author 212615492
 *
 */
/**
 * @author 212615492
 *
 */
public class ExcelHelper {
	private static final Logger logger = LoggerFactory.getLogger(ExcelHelper.class);

	/**
	 * 输出对象标题
	 * 
	 * @param clazz
	 *            Class
	 * @return List<ExcelHeader>
	 */
	private List<ExcelHeader> getHeader(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields(); // 反射获取实体上的所有方法
		String name = null;
		List<ExcelHeader> headers = new ArrayList<ExcelHeader>();
		headers = Arrays.asList(fields).stream()
				.filter(f -> f.getName() != null && f.isAnnotationPresent(ExcelSign.class)).map(f -> {
					ExcelSign sign = f.getAnnotation(ExcelSign.class); // 获取方法上的ExcelSign注解
					// method.getGenericReturnType():获取getxxx获取isxxx上的返回类型
					ExcelHeader header = new ExcelHeader(f.getName(), sign.title(), f.getGenericType(),
							sign.checkNull(), sign.numrangeCheck(), sign.contentCheck(), sign.timeunit());
					return header;
				}).collect(Collectors.toList());

		// Collections.sort(headers) ; //对List进行排序
		return headers;
	}

	/**
	 * 数据所在的位置
	 * 
	 * @param path
	 *            Excel所在的位置
	 * @param clazz
	 *            Class
	 * @param startLine
	 *            标题所在行(从1开始,startLine-1为标题行,startLine为数据开始行)
	 * @param tailLine
	 *            不是数据所占的行数
	 * @param hasClasspath
	 *            true:路径为classpath false:path为绝对路径
	 * @return 数据
	 */
	public <T> List<T> readExcel2Obj(File file, Class<T> clazz, int startLine, int tailLine, boolean hasClasspath) {
		Workbook workbook = null;
		InputStream stream = null;
		List<T> entitys = null;
		try {

			workbook = WorkbookFactory.create(file);

			entitys = getEntitys(workbook, clazz, startLine, tailLine);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stream != null) {
					stream.close();
					stream = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return entitys;
	}

	/**
	 * 读取模板头信息(标题信息)
	 */
	private Map<Integer, ExcelHeader> readHeader(Row row, Class<?> clazz) {
		List<ExcelHeader> headers = getHeader(clazz);
		Map<Integer, ExcelHeader> headerMap = new LinkedHashMap<Integer, ExcelHeader>();
		String value = null;
		for (Cell cell : row) {
			if (cell.getCellType() != Cell.CELL_TYPE_STRING)
				continue;
			value = cell.getStringCellValue().trim();
			for (ExcelHeader header : headers) {
				if (header.getTitle().equals(value)) {
					headerMap.put(cell.getColumnIndex(), header);
					break;
				}
			}
		}
		return headerMap;
	}

	/**
	 * 将数据转换成实体,此处会进行基本数据类型验证
	 * 
	 * @param workbook
	 *            Workbook
	 * @param clazz
	 *            Class
	 * @param startLine
	 *            标题所在行(从1开始,startLine-1为标题行,startLine为数据开始行)
	 * @param tailLine
	 *            不是数据所占的行数
	 * @return
	 */
	private <T> List<T> getEntitys(Workbook workbook, Class<T> clazz, int startLine, int tailLine) {
		List<T> entitys = null;
		Sheet sheet = workbook.getSheetAt(0);
		DataFormatter formatter = new DataFormatter();
		// 读取标题,这里输入标题行是从1开始不是从0开始所以startLine - 1
		Map<Integer, ExcelHeader> headers = readHeader(sheet.getRow(startLine - 1), clazz);
		int end = sheet.getPhysicalNumberOfRows() - tailLine;
		entitys = new ArrayList<T>();
		String type = null;

		try {
			Method addErrorMethod = clazz.getDeclaredMethod("addError", String.class);
			for (int i = startLine; i < end; i++) {
				Row row = sheet.getRow(i);
				T entity = clazz.newInstance(); // 反射new对象(要有空的构造方法)
				ExcelUploadSupport entityupload = (ExcelUploadSupport) entity;
//				if (row == null || row.getFirstCellNum() != 0) {
//					continue;
//				}
				for (Cell cell : row) {

					ExcelHeader header = headers.get(cell.getColumnIndex()); // 根据readHeader方法的映射关系获取对应的实体属性关系
					if (header == null) {
						throw new IllegalArgumentException("Please check the excel format is correct.");
					}
					if (header != null) {
						String methodName = header.getMethodName(); // 实体对象的字段属性名称
						methodName = "set" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
						type = header.getType().toString();
						Method method = getMethod(clazz, type, methodName);

						String formatCellValue = formatter.formatCellValue(cell);
						nullCheck(header, formatCellValue, addErrorMethod, entityupload);

						if (type.equals("class java.util.Date")) {
							try {
								Date celldate = cell.getDateCellValue();
								method.invoke(entity, celldate);
							} catch (IllegalStateException e) {
								// todo add date validate handler
								addErrorMethod.invoke(entityupload,
										header.getTitle() + " Column Error:Date format error " + formatCellValue);
								e.printStackTrace();
							}
						} else if (type.equals("class java.lang.Boolean") || type.equals("boolean")) {
							Boolean booleanv = false;
							if (formatCellValue.equals("Y") || formatCellValue.equals("TRUE")) {
								booleanv = true;
							} else if (formatCellValue.equals("N") || formatCellValue.equals("FALSE")) {
								booleanv = false;
							} else {
								addErrorMethod.invoke(entityupload, "Wrong boolean value for " + header.getTitle());
							}
							method.invoke(entity, booleanv);
						} else if (type.equals("class java.lang.Integer") || type.equals("int")) {
							int newnumber = 0;
							if (isNotNull(formatCellValue)) {
								try {
									newnumber = Integer.valueOf(formatCellValue);
								} catch (NumberFormatException e) {
									addErrorMethod.invoke(entityupload,
											"Wrong Integer value for " + header.getTitle() + ":" + formatCellValue);
								}
								numberRangeCheck(header, newnumber, addErrorMethod, entityupload);
								method.invoke(entity, newnumber);
							}
						} else if (type.equals("class java.lang.Double") || type.equals("double")) {
							method.invoke(entity, cell.getNumericCellValue());
						}else if (type.equals("class java.lang.Float") || type.equals("float")) {
							method.invoke(entity, Float.parseFloat(String.valueOf(cell.getNumericCellValue())));
						} 
						else if (type.equals("class java.lang.Long") || type.equals("long")) {
							changeTimeunitUpload(entity, header, cell, method, formatCellValue);
						} else if (type.equals("class java.util.Calendar")) {
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(cell.getDateCellValue());
							method.invoke(entity, calendar);
						} else {
							formatCellValue = formatCellValue.trim();
							method.invoke(entity, formatCellValue);
						}
					}
				}
				entitys.add(entity);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return entitys;
	}

	private <T> Method getMethod(Class<T> clazz, String type, String methodName) throws NoSuchMethodException {
		try {
			return clazz.getSuperclass().getDeclaredMethod(methodName, getFieldType(type));
		} catch (NoSuchMethodException e) {
			return clazz.getDeclaredMethod(methodName, getFieldType(type));
		}
	}

	/**
	 * 获取对象字段的类型Class
	 * 
	 * @param type
	 * @return
	 */
	private Class<?> getFieldType(String type) {
		if (type.equals("class java.util.Date")) {
			return Date.class;
		} else if (type.equals("class java.lang.Boolean")) {
			return Boolean.class;
		} else if (type.equals("boolean")) {
			return Boolean.TYPE;
		} else if (type.equals("class java.lang.Integer")) {
			return Integer.class;
		} else if (type.equals("class java.lang.Long")) {
			return Long.class;
		} else if (type.equals("long")) {
			return Long.TYPE;
		} else if (type.equals("int")) {
			return Integer.TYPE;
		} else if (type.equals("class java.lang.Double")) {
			return Double.class;
		} else if (type.equals("double")) {
			return Double.TYPE;
		} else if (type.equals("class java.util.Calendar")) {
			return Calendar.class;
		}else if (type.equals("class java.lang.Float")) {
			return Float.class;
		}
		return String.class;
	}

	private boolean isNotNull(String formatCellValue) {
		return formatCellValue != null && !formatCellValue.trim().equals("");
	}

	private boolean nullCheck(ExcelHeader header, String formatCellValue, Method addErrorMethod,
			ExcelUploadSupport entityupload)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (header.getCheckNull()) {
			if (!isNotNull(formatCellValue)) {
				addErrorMethod.invoke(entityupload, header.getTitle() + " Column Error:Can't assign null");
				return false;
			}
		}
		return true;
	}

	private boolean contentCheck(ExcelHeader header, String formatCellValue, Method addErrorMethod,
			ExcelUploadSupport entityupload)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String contentcheck = header.getContentCheck();
		if (!contentcheck.equals("") && !formatCellValue.equals("")) {

			boolean matched = false;
			String contents[] = contentcheck.split(",");
			for (String content : contents) {
				if (content.equalsIgnoreCase(formatCellValue)) {
					matched = true;
				}
			}
			if (!matched) {
				addErrorMethod.invoke(entityupload,
						header.getTitle() + " Column Error:Content mismatched " + formatCellValue);
				return false;
			}

		}
		return true;
	}

	private boolean numberRangeCheck(ExcelHeader header, int number, Method addErrorMethod,
			ExcelUploadSupport entityupload)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String numberrange = header.getNumrangeCheck();
		if (!numberrange.equals("")) {

			int minimum = -Integer.MAX_VALUE;
			int maximum = Integer.MAX_VALUE;
			String ranges[] = numberrange.split(",");
			if (!ranges[0].equals("N")) {
				minimum = Integer.parseInt(ranges[0]);
			}
			if (!ranges[1].equals("N")) {
				maximum = Integer.parseInt(ranges[1]);
			}
			if (number > maximum || number < minimum) {
				addErrorMethod.invoke(entityupload, header.getTitle() + " Column Number Range exceed:" + numberrange);
				return false;
			}

		}
		return true;
	}

	/**
	 * 根据路径将数据填充到Excel表中.
	 * 
	 * @param path
	 *            路径
	 * @param clazz
	 *            Class
	 * @param entitys
	 *            实体集合
	 * @param hasXLS
	 *            true:为Excel 2003版本 false:为Excel 2007以上版本
	 * @return ExcelUtil
	 */
	public File export2Obj(Class<?> clazz, List<?> entitys, boolean hasXLS) {
		Workbook workbook = export(clazz, entitys, hasXLS);
		OutputStream stream = null;
		File file = null;
		try {
			file = File.createTempFile(clazz.getSuperclass().getSimpleName() + "_download", ".xlsx");
			stream = new FileOutputStream(file.getAbsoluteFile());
			workbook.write(stream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stream != null) {
					stream.close();
					stream = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return file;
		}
	}

	/**
	 * @param clazz
	 * @param entitys
	 * @param hasXLS
	 * @return
	 */
	private Workbook export(Class<?> clazz, List<?> entitys, boolean hasXLS) {

		Workbook workbook = null;
		if (hasXLS) {
			workbook = new HSSFWorkbook();
		} else {
			workbook = new XSSFWorkbook();
		}
		CellStyle cellStyle = workbook.createCellStyle();

		DataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("yyyy/MM/dd HH:mm:ss"));
		try {
			Sheet sheet = workbook.createSheet();

			// 输出标题
			List<ExcelHeader> headers = getHeader(clazz);
			Row row = sheet.createRow(0);
			int count = headers.size();
			for (int i = 0; i < count; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(headers.get(i).getTitle());
			}

			// 输出数据
			int number = entitys.size();
			Method method = null;

			for (int i = 0; i < number; i++) {
				row = sheet.createRow(i + 1);
				Object obj = entitys.get(i);
				for (int j = 0; j < count; j++) {
					String methodName = headers.get(j).getMethodName();

					String methodNamefirst = "get" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

					String backupMethodName = "get" + methodName;

					String type = headers.get(j).getType().toString(); // 字符串的形式输出实体每个字段的类型
					try {
						method = clazz.getSuperclass().getDeclaredMethod(methodNamefirst); // 反射获取实体上的指定方法(根据方法的字符串名称)
					} catch (Exception e1) {
						try {
							method = clazz.getSuperclass().getDeclaredMethod(backupMethodName);
						} catch (Exception e2) {
							try {
								method = clazz.getDeclaredMethod(methodNamefirst);
							} catch (Exception e3) {
								method = clazz.getDeclaredMethod(backupMethodName);
							}
						}
					}
					Cell cell = row.createCell(j);
					if (method.invoke(obj) == null) {
						continue;
					}
					// jesse
					ExcelHeader header = headers.get(cell.getColumnIndex());
					// method.invoke(obj):获取调用方法(根据方法的getxxx)
					if (type.equals("class java.util.Date")) {
						cell.setCellStyle(cellStyle);
						Date dateraw = (Date) method.invoke(obj);
						cell.setCellValue(dateraw);
					} else if (type.equals("class java.lang.Boolean") || type.equals("boolean")) {
						cell.setCellValue((Boolean) method.invoke(obj));
					} else if (type.equals("class java.lang.Integer") || type.equals("int")) {
						cell.setCellValue((Integer) method.invoke(obj));
					} else if (type.equals("class java.lang.Double") || type.equals("double")) {
						cell.setCellValue((Double) method.invoke(obj));
					} else if (type.equals("class java.lang.Float") || type.equals("float")) {
						cell.setCellValue((Float) method.invoke(obj));
					} else if (type.equals("class java.util.Calendar")) {
						Date dateraw = (Date) method.invoke(obj);

						cell.setCellValue(dateraw);
					} else if (type.equals("class java.lang.Long") || type.equals("long")) {
						// jesse download
						changeTimeunitDownload(header, cell, method, obj);
					} else {
						cell.setCellValue((String) method.invoke(obj));
					}
				}
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return workbook;
	}

	private void changeTimeunitDownload(ExcelHeader header, Cell cell, Method method, Object obj)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Long objStr = (Long) method.invoke(obj);
		if (header.getTimeunit().equals("hour")) {
			long except = 60 * 60 * 1000;
			String exceptStr = String.valueOf(except);
			String value = divideBigDecimal(objStr.toString(), exceptStr, 2);
			cell.setCellValue(Double.parseDouble(value));
		} else if (header.getTimeunit().equals("min")) {
			long except = 60 * 1000;
			String exceptStr = String.valueOf(except);
			String value = divideBigDecimal(objStr.toString(), exceptStr, 2);
			cell.setCellValue(Double.parseDouble(value));
		} else {
			try {
				cell.setCellValue((Long) method.invoke(obj));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				logger.debug("Data error");
				e.printStackTrace();
			}
		}

	}

	private <T> void changeTimeunitUpload(T entity, ExcelHeader header, Cell cell, Method method,
			String formatCellValue) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (!isNotNull(formatCellValue)) {
			formatCellValue = null;
			method.invoke(entity, formatCellValue);
			return;
		}
		if (header.getTimeunit().equals("hour")) {
			Double except = 60 * 60 * 1000d;
			except = except * Double.parseDouble(formatCellValue);
			method.invoke(entity, except.longValue());
		} else if (header.getTimeunit().equals("min")) {
			Double except = 60 * 1000d;
			except = except * Double.parseDouble(formatCellValue);
			method.invoke(entity, except.longValue());
		} else {
			try {
				method.invoke(entity, new Long(formatCellValue));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				logger.debug("Data error");
				e.printStackTrace();
			}
		}

	}

	public static String divideBigDecimal(String str01, String str02, int mun) {
		BigDecimal bigDecimal = new BigDecimal(str01).divide(new BigDecimal(str02), mun, BigDecimal.ROUND_UP);
		return bigDecimal.toString();
	}

}
