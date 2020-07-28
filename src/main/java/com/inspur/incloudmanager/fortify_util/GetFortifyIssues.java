package com.inspur.incloudmanager.fortify_util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.sf.json.JSONObject;

public class GetFortifyIssues {

    // 使用本工具类的前置条件是：先使用postman调用Fortify接口另存为json格式的文件
    // 本工具类是导出issue的第二步，输入上述第一步的json文件，输出到csv中
    public static void main(String[] args) throws IOException {
        // 定义输入源文件
        File rFile = new File("D:\\fortify\\icks-all.json");
        FileReader fr = new FileReader(rFile);
        BufferedReader br = new BufferedReader(fr);

        // 定义输出文件
        FileWriter fw = new FileWriter("D:\\fortify\\icks-all.csv");
        BufferedWriter bw = new BufferedWriter(fw);

        try {
            // 1、输出表头
            String content = br.readLine();
            bw.write("line,");
            JSONObject jo = JSONObject.fromObject(content);
            for (Object key : jo.getJSONArray("data").getJSONObject(0).keySet()) {
                bw.write(key + ",");
                System.out.print(key + ",");
            }
            bw.write("\n");
            System.out.println();

            // 2、输出行数据
            int rowNum = 1;
            for (Object object : jo.getJSONArray("data")) {
                bw.write(rowNum + ",");
                JSONObject json = JSONObject.fromObject(object);
                for (Object key : json.keySet()) {
                    bw.write(json.get(key) + ",");
                    System.out.print(json.get(key) + ",");
                }
                bw.write("\n");
                System.out.println();
                rowNum++;
                bw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bw.close();
            br.close();
            fr.close();
        }
    }
}
