package com.huilian.hlej.hcf.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.huilian.hlej.hcf.vo.ExcelVo;

public class ReadExcel {
	// 总行数
	private int totalRows = 0;
	// 总条数
	private int totalCells = 0;
	// 错误信息接收器
	private String errorMsg;

	// 构造方法
	public ReadExcel() {
	}

	// 获取总行数
	public int getTotalRows() {
		return totalRows;
	}

	// 获取总列数
	public int getTotalCells() {
		return totalCells;
	}

	// 获取错误信息
	public String getErrorInfo() {
		return errorMsg;
	}

	/**
	 * 读EXCEL文件，获取信息集合
	 * 
	 * @param fielName
	 * @return
	 */
	public List<ExcelVo> getExcelInfo(MultipartFile mFile) {
		String fileName = mFile.getOriginalFilename();// 获取文件名
		List<ExcelVo> userList = null;
		try {
			if (!validateExcel(fileName)) {// 验证文件名是否合格
				return null;
			}
			boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
			if (isExcel2007(fileName)) {
				isExcel2003 = false;
			}
			//CommonsMultipartFile cf= (CommonsMultipartFile)mFile;
			//DiskFileItem fi = (DiskFileItem)cf.getFileItem();
			//File file = fi.getStoreLocation();
			userList = createExcel(mFile.getInputStream(), isExcel2003);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}

	/**
	 * 根据excel里面的内容读取客户信息
	 * 
	 * @param is
	 *            输入流
	 * @param isExcel2003
	 *            excel是2003还是2007版本
	 * @return
	 * @throws InvalidFormatException 
	 * @throws IOException
	 */
	public List<ExcelVo> createExcel(InputStream is, boolean isExcel2003) throws InvalidFormatException, IOException {
		List<ExcelVo> userList = null;
		try {
			//Workbook wb = WorkbookFactory.create(is);
			Workbook wb = null;
			if (isExcel2003) {// 当excel是2003时,创建excel2003
				wb = new HSSFWorkbook(is);
			} else {// 当excel是2007时,创建excel2007
				wb = new XSSFWorkbook(is);
			}
			userList = readExcelValue(wb);// 读取Excel里面客户的信息
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userList;
	}

	/**
	 * 读取Excel里面客户的信息
	 * 
	 * @param wb
	 * @return
	 */
	private List<ExcelVo> readExcelValue(Workbook wb) {
		List<ExcelVo> list = new ArrayList<ExcelVo>();
		 for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
	            Sheet hssfSheet = wb.getSheetAt(numSheet);
	            if (hssfSheet == null) {
	                continue;
	            }
	            // 循环行Row
	            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
	                Row hssfRow = hssfSheet.getRow(rowNum);
	                if (hssfRow != null) {
	                	ExcelVo excelVo  = new ExcelVo();
	                    Cell no = hssfRow.getCell(0);
	                    Cell name = hssfRow.getCell(1);
	                    Cell age = hssfRow.getCell(2);
	                    Cell score = hssfRow.getCell(3);
	                    /*student.setNo(getValue(no));
	                    student.setName(getValue(name));
	                    student.setAge(getValue(age));
	                    student.setScore(Float.valueOf(getValue(score)));*/
	                    list.add(excelVo);
	                }
	            }
	        }
	        return list;
		
	        /*HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
	        ExcelVo excelVo = null;
	        List<ExcelVo> list = new ArrayList<ExcelVo>();
	        // 循环工作表Sheet
	        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
	            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
	            if (hssfSheet == null) {
	                continue;
	            }
	            // 循环行Row
	            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
	                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
	                if (hssfRow != null) {
	                	excelVo = new ExcelVo();
	                    HSSFCell no = hssfRow.getCell(0);
	                    HSSFCell name = hssfRow.getCell(1);
	                    HSSFCell age = hssfRow.getCell(2);
	                    HSSFCell score = hssfRow.getCell(3);
	                    student.setNo(getValue(no));
	                    student.setName(getValue(name));
	                    student.setAge(getValue(age));
	                    student.setScore(Float.valueOf(getValue(score)));
	                    list.add(student);
	                }
	            }
	        }
	        return list;*/
		
		/*// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);
		// 得到Excel的行数
		this.totalRows = sheet.getPhysicalNumberOfRows();
		// 得到Excel的列数(前提是有行数)
		if (totalRows > 1 && sheet.getRow(0) != null) {
			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}
		List<ExcelVo> userList = new ArrayList<ExcelVo>();
		// 循环Excel行数
		for (int r = 0; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			ExcelVo user = new ExcelVo();
			// 循环Excel的列
			for (int c = 0; c < this.totalCells; c++) {
				Cell cell = row.getCell(c);
				if (null != cell) {
					if (c == 0) {
						// 如果是纯数字,比如你写的是25,cell.getNumericCellValue()获得是25.0,通过截取字符串去掉.0获得25
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							String name = String.valueOf(cell.getNumericCellValue());
							//user.setName(name.substring(0, name.length() - 2 > 0 ? name.length() - 2 : 1));// 名称
						} else {
							//user.setName(cell.getStringCellValue());// 名称
						}
					} else if (c == 1) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							String sex = String.valueOf(cell.getNumericCellValue());
							//user.setSex(sex.substring(0, sex.length() - 2 > 0 ? sex.length() - 2 : 1));// 性别
						} else {
							//user.setSex(cell.getStringCellValue());// 性别
						}
					} else if (c == 2) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							String age = String.valueOf(cell.getNumericCellValue());
							//user.setAge(age.substring(0, age.length() - 2 > 0 ? age.length() - 2 : 1));// 年龄
						} else {
							//user.setAge(cell.getStringCellValue());// 年龄
						}
					}
				}
			}
			// 添加到list
			userList.add(user);
		}
		return userList;*/
	    
	        
	        
	}

	/**
	 * 验证EXCEL文件
	 * 
	 * @param filePath
	 * @return
	 */
	public boolean validateExcel(String filePath) {
		if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
			errorMsg = "文件名不是excel格式";
			return false;
		}
		return true;
	}

	// @描述：是否是2003的excel，返回true是2003
	public static boolean isExcel2003(String filePath) {
		return filePath.matches("^.+\\.(?i)(xls)$");
	}

	// @描述：是否是2007的excel，返回true是2007
	public static boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}
}
