package com.uptang.cloud.score.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.uptang.cloud.score.exceptions.ExcelExceptions;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-02 21:02
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME
 */
public class ExcelOperator {

    /**
     * 导出Excel
     *
     * @param excelName Excel名称
     * @param sheetName sheet页名称
     * @param clazz     Excel要转换的类型
     * @param data      要导出的数据
     * @throws Exception <p/><pre>
     *                     List<ExportInfo> list = getList();
     *                     String fileName = "一个 Excel 文件";
     *                     String sheetName = "第一个 sheet";
     *                     ExcelUtils.export(fileName, sheetName, ExportInfo.class, list);
     *                   </pre>
     *                   <p>
     *                   public class ExportInfo extends BaseRowModel {
     * @ExcelProperty(value = "姓名" ,index = 0)
     * private String name;
     * @ExcelProperty(value = "年龄",index = 1)
     * private String age;
     * @ExcelProperty(value = "邮箱",index = 2)
     * private String email;
     * @ExcelProperty(value = "地址",index = 3)
     * private String address;
     * }
     */
    public static void export(String excelName, String sheetName, Class<?> clazz, List<?> data) {
        Http.Response.getCurrent().ifPresent(response -> {
            setHeaders(excelName, response);
            try (ServletOutputStream outputStream = response.getOutputStream()) {
                EasyExcel.write(outputStream, clazz).sheet(sheetName).doWrite(data);
            } catch (IOException e) {
                throw new ExcelExceptions(e);
            }
        });
    }


    /**
     * 下载Excel的Header
     *
     * @param excelName
     * @param response
     */
    private static void setHeaders(String excelName, HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "No-cache");
        response.setCharacterEncoding("utf-8");
        response.setDateHeader("Expires", 0);
        try {
            // 这里URLEncoder.encode可以防止中文乱码
            String newExcelName = URLEncoder.encode(excelName, "UTF-8");
            String attachment = String.format("attachment;filename=%s.%s", newExcelName, ExcelTypeEnum.XLSX.getValue());
            response.setHeader("Content-disposition", attachment);
        } catch (UnsupportedEncodingException e) {
            throw new ExcelExceptions(e);
        }
    }
}
