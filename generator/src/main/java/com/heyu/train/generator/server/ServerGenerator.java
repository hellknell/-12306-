package com.heyu.train.generator.server;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.heyu.train.generator.util.DbUtil;
import com.heyu.train.generator.util.Field;
import com.heyu.train.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.util.*;

public class ServerGenerator {
    static boolean readOnly = false;
    static String vuePath = "E://IdeaProhect/train12306/web/src/views/main/daily/";
    static String pomPath = "generator\\pom.xml";
    static String servicePath = "[module]/src/main/java/com/heyu/train/[module]/";

    //    static String module = "";
//
//    static {
//        new File(serverPath).mkdirs();
//    }
    private static String getGeneratorPath() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Map<String, String> map = new HashMap<String, String>();
        map.put("pom", "http://maven.apache.org/POM/4.0.0");
        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);
        Document document = saxReader.read(pomPath);
        Node node = document.selectSingleNode("//pom:configurationFile");
        System.out.println(node.getText());
        return node.getText();
    }

    public static void main(String[] args) throws Exception {
        // 获取mybatis-generator
        String generatorPath = getGeneratorPath();

        String module = generatorPath.replace("src/main/resources/generatorConfig-", "").replace(".xml", "");
        String servicePathFinal = servicePath.replace("[module]", module);
        System.out.println(servicePathFinal);
        System.out.println("module:" + module);
        Document document = new SAXReader().read("generator/" + generatorPath);
        Node table = document.selectSingleNode("//table");
        Node tableName = table.selectSingleNode("@tableName");
        Node domainObjectName = table.selectSingleNode("@domainObjectName");
        System.out.println(tableName.getText() + "\n" + domainObjectName.getText());

        String Domain = domainObjectName.getText();
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        String do_main = tableName.getText().replace("_", "-");
        HashMap<String, Object> map = new HashMap<>();
        map.put("domain", domain);
        map.put("Domain", Domain);
        map.put("do_main", do_main);
        List<Field> list = DbUtil.getColumnByTableName(tableName.getText());
        String tableNameCh = DbUtil.getTableComment(tableName.getText());
        map.put("tableNameCn", tableNameCh);
        Set<String> typeSet = getJavaType(list);
        map.put("fieldList", list);
        map.put("module", module);
        map.put("typeSet", typeSet);
        map.put("readOnly", readOnly);
//
//        gen(servicePathFinal, domain, map, "service", "service");
//        gen(servicePathFinal, domain, map, "controller", "controller");
//        gen(servicePathFinal, domain, map, "req", "saveReq");
//        gen(servicePathFinal, domain, map, "req", "queryReq");
//        gen(servicePathFinal, domain, map, "resp", "queryResp");
        genVue(Domain, map);
    }

    private static void gen(String serverPath, String domain, HashMap<String, Object> map, String packageName, String target) throws IOException, TemplateException {
        FreemarkerUtil.initConfig(target + ".ftl");
        String dir = serverPath + StrUtil.lowerFirst(packageName) + "/";
        if (!FileUtil.isDirectory(dir)) {
            FileUtil.mkdir(dir);
        }
        System.out.println(serverPath + domain.substring(0, 1).toLowerCase() + domain.substring(1) + target + ".java");
        FreemarkerUtil.generator(dir + StrUtil.upperFirst(domain) + StrUtil.upperFirst(target) + ".java", map);
        System.out.println("生成成功！！");
    }

    public static void genVue(String Domain, Map<String, Object> map) throws IOException, TemplateException {
        FreemarkerUtil.initConfig("vue.ftl");
        String fileName = vuePath + Domain + "View.vue";
        FreemarkerUtil.generator(fileName, map);

    }

    public static Set<String> getJavaType(List<Field> list) {
        HashSet<String> typeSet = new HashSet<>();
        for (Field field : list) {
            String javaType = field.getJavaType();
            typeSet.add(javaType);
        }
        return typeSet;
    }


}