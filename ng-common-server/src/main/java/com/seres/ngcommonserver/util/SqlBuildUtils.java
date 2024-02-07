package com.seres.ngcommonserver.util;

import com.seres.ngcommonserver.annotation.ClassAutoMapping;
import com.seres.ngcommonserver.annotation.FieldAutoMapping;
import com.seres.ngcommonserver.model.Edge;
import com.seres.ngcommonserver.model.ModelAndClass;
import com.seres.ngcommonserver.model.ProjectAttributeAndRelationship;
import com.seres.ngcommonserver.model.player.Player;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/5 17:12
 */
@Component
public class SqlBuildUtils {
    private static final String methodPre = "get";

    private static final String insertTagSqlTemplate = "insert vertex %s(%s) values \"%s\":(%s);";

    private static final String insertEdgeSqlTemplate = "insert edge %s(%s) values \"%s\" -> \"%s\":(%s);";

    private static final String deleteTagSqlTemplate = "delete tag %s from \"%s\";";

    private static final String deleteEdgeSqlTemplate = "delete edge %s \"%s\" -> \"%s\"@0;";

    private static final String deleteVertexSqlTemplate = "delete vertex \"%s\" with edge;";

    private static final String updateVertexSqlTemplate = "update vertex on %s \"%s\" set %s;";

    private static final String createTag = "create tag if not exists %s(id int64, name String,description String) comment = \"%s\";";

    private static final String insertDefaultVertex = "insert vertex if not exists %s(id,name,description) values \"%s\":(%s,\"%s\",\"%s\");";

    private static final String insertDefaultEdge = "insert edge %s(id,name) values \"%s\" -> \"%s\":(%s,\"%s\");";

    private static final String dropTagSqlTemplate = "drop tag if exists %s;";

    private static final String dropEdgeSqlTemplate = "drop edge if exists %s;";

    private static final String createEdgeSqlTemplate = "create edge if not exists %s(id int64,name String) comment = \"%s\"";

    public static <T> String buildInsert(T t) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SqlBuild tag = parse(t);
        return String.format(insertTagSqlTemplate, tag.getName(), tag.getField(), tag.getId(), tag.getValues());
    }

    public static <T extends Edge> String buildEdge(T t) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SqlBuild edge = parse(t);

        return String.format(insertEdgeSqlTemplate, edge.getName(), edge.getField(), t.getLeftVid(), t.getRightVid(), edge.getValues());
    }

    public static <T> String deleteTag(T t) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        SqlBuild tag = parse(t);
        return String.format(deleteTagSqlTemplate, tag.getName(), tag.getId());
    }

    public static String deleteEdge(String edgeName, Long pid, Long subId) {
        return String.format(deleteEdgeSqlTemplate, edgeName, pid, subId);
    }

    public static <T> String deleteVertex(Long id) {
        return String.format(deleteVertexSqlTemplate, id);
    }

    public static <T> String updateVertex(T t) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Class<?> clazz = t.getClass();
        ClassAutoMapping annotation = clazz.getAnnotation(ClassAutoMapping.class);
        String tagName = annotation.value();
        Field[] declaredFields = clazz.getDeclaredFields();
        StringBuilder set = new StringBuilder();
        Long id = null;
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            // 获取属性名称
            String name = declaredField.getName();
            // 获取自定义注解 FieldAutoMapping
            FieldAutoMapping autoMapping = declaredField.getAnnotation(FieldAutoMapping.class);
            if (null == autoMapping) {
                continue;
            }
            String methodName = autoMapping.method();
            String type = autoMapping.type();
            Method getMethod = clazz.getDeclaredMethod(methodName);
            Object value = getMethod.invoke(t);
            Object valueFormat = format(value, type);
            if ("id".equals(name)) {
                id = (Long) value;
                continue;
            }
            set.append(name).append("=").append(valueFormat);
            if (i != declaredFields.length - 1) {
                set.append(",");
            }
        }
        return String.format(updateVertexSqlTemplate, tagName, id, set);
    }

    public static String updateDefault(String tagName, Long vid, String name, String description) {
        String setCall = "name=" + format(name, "String") + ",description=" + format(description, "String");
        return String.format(updateVertexSqlTemplate, tagName, vid, setCall);
    }

    private static <T> SqlBuild parse(T t) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = t.getClass();
        ClassAutoMapping annotation = clazz.getAnnotation(ClassAutoMapping.class);
        String tagName = annotation.value();
        Field[] declaredFields = clazz.getDeclaredFields();
        StringBuilder filedString = new StringBuilder();
        StringBuilder valueString = new StringBuilder();
        Object id = null;
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            // 获取属性名称
            String name = declaredField.getName();
            // 获取自定义注解 FieldAutoMapping
            FieldAutoMapping autoMapping = declaredField.getAnnotation(FieldAutoMapping.class);
            if (null == autoMapping) {
                continue;
            }
            String methodName = autoMapping.method();
            String type = autoMapping.type();
            Method getMethod = clazz.getDeclaredMethod(methodName);
            Object value = getMethod.invoke(t);

            Object valueFormat = format(value, type);
            if ("id".equals(name)) {
                id = /*(Long) */value;
                continue;
            }
            filedString.append(name);
            valueString.append(valueFormat);
            if (i != declaredFields.length - 1) {
                filedString.append(",");
                valueString.append(",");
            }
        }
        return new SqlBuild(id, tagName, filedString.toString(), valueString.toString());
    }

    public static String createTag(String tagName, String comment) {
        return String.format(createTag, tagName, comment);
    }

    public static String createEdge(String edgeName, String comment) {
        return String.format(createEdgeSqlTemplate, edgeName, comment);
    }

    public static String insertDefaultVertex(String tagName, Long vid, String name, String description) {
        return String.format(insertDefaultVertex, tagName, vid, vid, name, description);
    }

    public static String dropTag(String tagName) {
        return String.format(dropTagSqlTemplate, tagName);
    }

    public static String dropEdge(String edgeName) {
        return String.format(dropEdgeSqlTemplate, edgeName);
    }

    public static String insertDefaultEdge(String edgeCode, Long pid, Long id, Long preId, String edgeName) {
        return String.format(insertDefaultEdge, edgeCode, pid, id, preId, edgeName);
    }

    /**
     * 首字母转大写
     *
     * @param name 字符串
     * @return 转大写
     */
    private static String firstCharacterToUppercase(String name) {
        String startName = name.substring(0, 1);
        String endName = name.substring(1);
        return startName.toUpperCase(Locale.ROOT) + endName;
    }

    private static Object format(Object value, String type) {
        if ("String".equals(type)) {
            String s = value + "";
            return "\"" + s + "\"";
        }
        return value;
    }

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // 测试点生成
       /* ModelAndClass modelAndClass = new ModelAndClass();
        modelAndClass.setId(123123123123L);
        modelAndClass.setName("模式");
        modelAndClass.setPid(132313231313123L);
        modelAndClass.setDescription("描述");
        String s = buildInsert(modelAndClass);
        System.out.println(s);
        // 测试边生成
        ProjectAttributeAndRelationship projectModelAndClass = new ProjectAttributeAndRelationship();
        projectModelAndClass.setStartId(181314024706908160L);
        projectModelAndClass.setEndId(123123123123L);
        projectModelAndClass.setLeftVid("181314024706908160");
        projectModelAndClass.setRightVid("123123123123");
        String s1 = buildEdge(projectModelAndClass);
        System.out.println(s1);*/

        Player player = new Player();
        player.setId("common-01");
        player.setName("common-01");
        player.setAge(22);
        String sql = buildInsert(player);
        System.out.println(sql);

    }
}
