//package com.huilian.hlej.jet.common.utils;
//
//import java.io.File;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import org.mybatis.generator.api.MyBatisGenerator;
//import org.mybatis.generator.config.Configuration;
//import org.mybatis.generator.config.xml.ConfigurationParser;
//import org.mybatis.generator.exception.InvalidConfigurationException;
//import org.mybatis.generator.exception.XMLParserException;
//import org.mybatis.generator.internal.DefaultShellCallback;
//
//public class MyBatisGeneratorUtil
//{
//    
//    public static void main(String[] args)
//    {
//        MyBatisGeneratorUtil.generator();
//    }
//
//    public static void generator()
//    {
//        boolean overwrite = true;
//        generator(overwrite);
//    }
//    
//    private static void generator(boolean overwrite)
//    {
//        String CONFIG_PATH=null;
//        List<String> warnings = new ArrayList<String>();  
//        File file = new File(MyBatisGeneratorUtil.class.getResource("/").getFile());
//        String CONFIG_FILE = "generatorConfig.xml";
//        try
//        {
//            CONFIG_PATH = file.getCanonicalPath();
//        }
//        catch (IOException e1)
//        {
//            e1.printStackTrace();
//        }
//        CONFIG_PATH = CONFIG_PATH + File.separator + CONFIG_FILE;
//        File configFile = new File(CONFIG_PATH);
//        ConfigurationParser cp = new ConfigurationParser(warnings);  
//        try
//        {
//            Configuration config = cp.parseConfiguration(configFile);  
//            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
//            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);  
//            myBatisGenerator.generate(null);
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        catch (XMLParserException e)
//        {
//            e.printStackTrace();
//        }
//        catch (InvalidConfigurationException e)
//        {
//            e.printStackTrace();
//        }
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
//        catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }
//    }
//}
