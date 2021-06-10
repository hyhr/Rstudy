package com.r.study.elasticsearch.conditions.enums;

/**
 *          使用QueryBuilder
 *          * termQuery("key", obj) 完全匹配
 *          * termsQuery("key", obj1, obj2..)   一次匹配多个值
 *          * matchQuery("key", Obj) 单个匹配, field不支持通配符, 前缀具高级特性
 *          * multiMatchQuery("text", "field1", "field2"..);  匹配多个字段, field有通配符忒行
 *          * matchAllQuery();         匹配所有文件
 *
 *          1.must
 *          文档 必须 匹配这些条件才能被包含进来。相当于sql中的 and
 *
 *          2.must_not
 *          文档 必须不 匹配这些条件才能被包含进来。相当于sql中的 not
 *
 *          3.should
 *          如果满足这些语句中的任意语句，将增加 _score ，否则，无任何影响。它们主要用于修正每个文档的相关性得分。相当于sql中的or
 *
 *          4.filter
 *          必须 匹配，但它以不评分、过滤模式来进行。这些语句对评分没有贡献，只是根据过滤标准来排除或包含文档
 *
 *          注意：在使用test keyword的时候 要根据实际的情况而定 不能随便复制乱用，这可能会导致一些查询结果偏差问题
 *          比如 字段为test类型 由于该字段已分词 goodsCode=‘商品编码：123456’ 当你使用等值查询 123456时 会拿到该行数据
 *          如果 goodsCode 类型为keyword 你使用 123456则不会查询到数据 只能等值查询
 *
 * @author pengmuhua
 * @create 2018/8/20 11:08
 **/
public enum SearchType {

    MATCH(1,"单个匹配, 会对查询条件进行分词"),
    WILDCARD(2,"wildcard模糊查询 查询的时候需要自己给定查询的条件  如果 *手机* 两个星星要自己给 .wildcardQuery(\"title\", \"华*\");"),
    GT(3,"大于"),
    GTE(4,"大于等于"),
    LT(5,"小于"),
    LTE(6,"小于等于"),
    TERM_QUERY(7, "完全匹配equals text文本是不能用这个查询出来的"),
    TERMS_QUERY(8, "一次匹配多个值equals"),
    MULTI_MATCH_QUERY(9, "查询一个值 匹配多个字段, field有通配符忒行"),
    IN(10,"集合内"),
    NOT_IN(11,"集合外"),
    geoDistance(10,"找出与指定位置在给定距离内的点 geo查询坐标范围查询 value请转成 com.yuexin.search.emtity.search.SearchGeoDistance"),
    geoBoundingBox(10,"找出落在指定矩形框中的点 top_left:左上角的矩形起始点经纬度 bottom_right右下角的矩形结束点经纬度");

    private Integer code;

    private String desc;

    SearchType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
