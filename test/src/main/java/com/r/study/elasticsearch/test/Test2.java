package com.r.study.elasticsearch.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ooxml.util.DocumentHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * date 2021-05-24 9:50
 *
 * @author HeYiHui
 **/
public class Test2 {

    public static final Logger log = LoggerFactory.getLogger(Test2.class);

    public static void main(String[] args) throws Exception {
        /**
         * 获取dependencyList
         */
        List<String> dependencyList = new ArrayList<>();
//        dependencyList = getDependencyList("https://dmg-nexus-test.infinitus.com.cn:8081/#browse/browse:maven-releases", "https://dmg-nexus-test.infinitus.com.cn:8081/#browse/browse:maven-releases:org%2Fspringblade/");
        String jsonStr = "{\"tid\":27,\"action\":\"coreui_Browse\",\"method\":\"read\",\"result\":{\"success\":true,\"data\":[{\"id\":\"org/springblade/blade-core-auto\",\"text\":\"blade-core-auto\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-core-boot\",\"text\":\"blade-core-boot\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-core-cloud\",\"text\":\"blade-core-cloud\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-core-context\",\"text\":\"blade-core-context\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-core-db\",\"text\":\"blade-core-db\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-core-launch\",\"text\":\"blade-core-launch\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-core-log4j2\",\"text\":\"blade-core-log4j2\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-core-oss\",\"text\":\"blade-core-oss\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-core-secure\",\"text\":\"blade-core-secure\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-core-sms\",\"text\":\"blade-core-sms\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-core-test\",\"text\":\"blade-core-test\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-core-tool\",\"text\":\"blade-core-tool\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-actuate\",\"text\":\"blade-starter-actuate\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-api-crypto\",\"text\":\"blade-starter-api-crypto\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-auth\",\"text\":\"blade-starter-auth\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-cache\",\"text\":\"blade-starter-cache\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-datascope\",\"text\":\"blade-starter-datascope\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-develop\",\"text\":\"blade-starter-develop\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-ehcache\",\"text\":\"blade-starter-ehcache\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-excel\",\"text\":\"blade-starter-excel\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-http\",\"text\":\"blade-starter-http\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-jwt\",\"text\":\"blade-starter-jwt\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-log\",\"text\":\"blade-starter-log\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-mongo\",\"text\":\"blade-starter-mongo\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-mybatis\",\"text\":\"blade-starter-mybatis\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-oss-aliyun\",\"text\":\"blade-starter-oss-aliyun\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-oss-all\",\"text\":\"blade-starter-oss-all\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-oss-minio\",\"text\":\"blade-starter-oss-minio\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-oss-qiniu\",\"text\":\"blade-starter-oss-qiniu\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-oss-tencent\",\"text\":\"blade-starter-oss-tencent\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-redis\",\"text\":\"blade-starter-redis\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-report\",\"text\":\"blade-starter-report\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-ribbon\",\"text\":\"blade-starter-ribbon\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-sms-aliyun\",\"text\":\"blade-starter-sms-aliyun\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-sms-all\",\"text\":\"blade-starter-sms-all\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-sms-qiniu\",\"text\":\"blade-starter-sms-qiniu\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-sms-tencent\",\"text\":\"blade-starter-sms-tencent\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-sms-yunpian\",\"text\":\"blade-starter-sms-yunpian\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-social\",\"text\":\"blade-starter-social\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-swagger\",\"text\":\"blade-starter-swagger\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-tenant\",\"text\":\"blade-starter-tenant\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-trace\",\"text\":\"blade-starter-trace\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/blade-starter-transaction\",\"text\":\"blade-starter-transaction\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/BladeX-Tool\",\"text\":\"BladeX-Tool\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/dmg-cloud\",\"text\":\"dmg-cloud\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false},{\"id\":\"org/springblade/platform\",\"text\":\"platform\",\"type\":\"folder\",\"leaf\":false,\"componentId\":null,\"assetId\":null,\"vulnerable\":false}]},\"type\":\"rpc\"}";
        JSONObject parse = JSON.parseObject(jsonStr);
        JSONArray jsonArray = parse.getJSONObject("result").getJSONArray("data");
        jsonArray.toJavaList(JSONObject.class).forEach( item -> {
            String artifactId = item.getString("text");
            String groupId = item.getString("id").replace("/", ".").replace("." + artifactId, "");
            String version = "2.7.0.RELEASE";
            String text = String.format("<dependency><groupId>%s</groupId><artifactId>%s</artifactId><version>%s</version></dependency>",
                    groupId, artifactId, version);
            dependencyList.add(text);
        });
        /**
         * 美化输出pom.xml文件（文件，控制台）
         */
        create(dependencyList);
    }

    /**
     * 获取私服 dependencyList
     *
     * @param url           要解析的第一页
     * @param dependencyUrl 点击.pom文件后页面路径有所变化，变化后的路径（去掉最后一层/xxx.pom）
     * @return
     * @throws Exception
     */
    public static List<String> getDependencyList(String url, String dependencyUrl) throws Exception {
        List<String> list = new ArrayList<>();
        //静态页面链接地址
        Document doc = Jsoup.connect(url).userAgent("Mozilla").timeout(4000).get();
        if (doc != null) {
            Elements elements = doc.select("table>tbody>tr>td>a");
            if (elements != null && elements.size() > 0) {
                for (Element element : elements) {
                    String uri = element.text() + "/";
                    getDependencyList(list, url, dependencyUrl, uri);
                }
            }
        }
        return list;
    }

    /**
     * 解析第二页开始的之后数个子页（递归），直到看见xxx.pom文件认为路径已经到底了
     *
     * @param list
     * @param url
     * @param dependencyUrl
     * @param uri
     * @throws Exception
     */
    private static void getDependencyList(List<String> list, String url, String dependencyUrl, String uri) throws Exception {
        try {
            Document doc = Jsoup.connect(url + uri).userAgent("Mozilla").timeout(4000).get();
            if (doc != null && doc.hasText()) {
                Elements elements = doc.select("table>tbody>tr>td>a");
                if (elements != null && elements.size() > 0) {
                    String pomUri = null;
                    Boolean flagPom = false;
                    Boolean flagJar = false;
                    for (int i = 1; i < elements.size(); i++) {
                        //查看当前页有无xxx.pom
                        if (elements.get(i).text() != null && elements.get(i).text().endsWith(".pom")) {
                            pomUri = elements.get(i).text();
                            flagPom = true;
                        }
                        //查看当前页有无xxx.jar，没有xxx.jar
                        if (elements.get(i).text() != null && elements.get(i).text().endsWith(".jar")) {
                            flagJar = true;
                        }
                    }
                    /*
                     * 有xxx.pom说明路径解析已经到底了，如果没有xxx.jar（少量）也不必解析xxx.pom了，下载不下来。
                     * 没有xxx.pom继续路径解析
                     */
                    if (flagPom) {
                        if (flagJar) {
                            if (pomUri != null) {
                                String pomUrl = dependencyUrl + uri + pomUri;
                                String dependency = getDependency(pomUrl);
                                list.add(dependency);
                            }
                        }
                    } else {
                        for (int i = 1; i < elements.size(); i++) {
                            getDependencyList(list, url, dependencyUrl, uri + elements.get(i).text() + "/");
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("无法解析：" + url + uri);
        }

    }

    /**
     * 解析xxx.pom，组装dependency字符串
     *
     * @param pomUrl
     * @return
     * @throws Exception
     */
    private static String getDependency(String pomUrl) throws Exception {

        Document pom = Jsoup.connect(pomUrl).userAgent("Mozilla").timeout(4000).get();

        /**
         * 多数情况下会在project的子节点找到dependency三点定位的属性值，version和groupId属性值可能在project子节点parent下。
         */

        Element artifactId = pom.select("project>artifactId").get(0);
        Element version = null;
        Elements versions = pom.select("project>version");
        if (versions != null && versions.size() > 0) {
            version = versions.get(0);
        } else {
            version = pom.select("project>parent>version").get(0);
        }

        Element groupId = null;
        Elements groupIds = pom.select("project>groupId");
        if (groupIds != null && groupIds.size() > 0) {
            groupId = groupIds.get(0);
        } else {
            groupId = pom.select("project>parent>groupId").get(0);
        }

        /**
         * 组装我们想要的字符串。
         */
        String dependencyFormat = "<dependency><groupId>{0}</groupId><artifactId>{1}</artifactId><version>{2}</version></dependency>";
        String dependency = MessageFormat.format(dependencyFormat, groupId.text(), artifactId.text(), version.text());
        log.info("dependency->{}", dependency);
        return dependency;
    }

    /**
     * 输出pom.xml结构（文件，控制台）
     *
     * @param dependencyList
     * @throws Exception
     */
    public static void create(List<String> dependencyList) throws Exception {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = DocumentHelper.createDocument();

        org.w3c.dom.Element project = document.createElement("project");
        project.setAttribute("xmlns", "http://maven.apache.org/POM/4.0.0");
        project.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        project.setAttribute("xsi:schemaLocation", "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd");


        org.w3c.dom.Element modelVersion = document.createElement("modelVersion");
        modelVersion.setTextContent("4.0.0");

        org.w3c.dom.Element dependencies = document.createElement("dependencies");

        for (String dependency : dependencyList) {
            org.w3c.dom.Document doc = builder.parse(new InputSource(new StringReader(dependency)));
            org.w3c.dom.Element element = doc.getDocumentElement();
            dependencies.appendChild(document.adoptNode(element));
        }
        project.appendChild(modelVersion);
        project.appendChild(dependencies);

        document.appendChild(project);

        /**
         * 创建转换工厂，然后将创建的document转换输出到文件中或控制台
         */
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        /**
         * 美化输出
         */
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        /**
         * 保存到文件
         */
        transformer.transform(new DOMSource(document), new StreamResult(new File("D:\\workspace\\R\\n-pom.xml")));

        /**
         * 将document中的信息转换为字符串输出到控制台中
         */
        StringWriter stringWriter = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
        System.out.println(stringWriter.toString());

    }
}
