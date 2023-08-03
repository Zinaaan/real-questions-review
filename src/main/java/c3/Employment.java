package c3;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lzn
 * @date 2023/07/29 22:25
 * @description # in the following format:
 * # [
 * #   { "name": "Dev 1", "managedBy": "Manager 1" },
 * #   { "name": "Dev 2", "managedBy": "Manager 1" },
 * #   { "name": "Manager 1", "managedBy": "Director 1" },
 * #   { "name": "Dev 3", "managedBy": "Director 1" },
 * #   { "name": "Director 1", "managedBy": "CEO" },
 * #   { "name": "Director 2", "managedBy": "CEO" },
 * #   { "name": "CEO", "managedBy": "" }
 * # ]
 * # Some definitions:
 * # Manager = A person who manages other employees (can be developers, other managers, etc.)
 * # Non-manager = A person who manages nobody
 * # With this information, find the following information:
 * # 1. List all employees under a manager
 * # Example input: "Director 1"
 * # Example output: ["Manager 1", "Dev 1", "Dev 2", "Dev 3"]
 * # 2. Find all non managers under a manager
 * # Example input: "Director 1"
 * # Example output: ["Dev 1", "Dev 2", "Dev 3"]
 * # 3. Find all managers under a manager
 * # Example input: "Director 1"
 * # Example output: ["Manager 1"]
 * # 4. Find the direct line of management for any person to the CEO
 * # Example input: "Dev 1"
 * # Example output: "CEO -> Director 1 -> Manager 1 -> Dev 1"
 */
public class Employment {

    static JSONArray array = new JSONArray();
    static List<List<String>> directLineList = new ArrayList<>();

    public static void main(String[] args) {
        String content = "[{\"name\": \"Dev 1\", \"managedBy\": \"Manager 1\" },{ \"name\": \"Dev 2\", \"managedBy\": \"Manager 1\" }," +
                "{ \"name\": \"Manager 1\", \"managedBy\": \"Director 1\" },{ \"name\": \"Dev 3\", \"managedBy\": \"Director 1\" }," +
                "{ \"name\": \"Director 1\", \"managedBy\": \"CEO\" },{ \"name\": \"Director 2\", \"managedBy\": \"CEO\" },{ \"name\": \"CEO\", \"managedBy\": \"\" }]";
        array = JSONArray.parseArray(content);
        System.out.println("input: " + array);

        // question 1
        // 1. Group by managedBy field and put into a hash map, key -> manager, value: array of employees
        // Director 1: [Manager 1, Dev3], Manager 1: [Dev1, Dev2], CEO : [Director 1, Director 2]
        // 2. Loop the map values to collect the employees
        Map<String, List<String>> resultMap = array.stream()
                .collect(Collectors.groupingBy(
                        emp -> {
                            JSONObject employee = (JSONObject) emp;
                            return employee.getString("managedBy");
                        },
                        Collectors.mapping(emp -> {
                            JSONObject employee = (JSONObject) emp;
                            return employee.getString("name");
                        }, Collectors.toList())
                ));

        Set<Map.Entry<String, List<String>>> entries = resultMap.entrySet();
        for (Map.Entry<String, List<String>> entry : entries) {
            List<String> employees = new ArrayList<>(entry.getValue());
            employees.forEach(employee -> {
                if (resultMap.containsKey(employee)) {
                    entry.getValue().addAll(resultMap.get(employee));
                }
            });
        }

        System.out.println("all employees under a manager: " + resultMap);

        // question 2
//        Set<Map.Entry<String, List<String>>> entries1 = resultMap.entrySet();
//        for (Map.Entry<String, List<String>> entry : entries1) {
//            List<String> employees = new ArrayList<>(entry.getValue());
//            employees.forEach(employee -> {
//                if (resultMap.containsKey(employee)) {
//                    entry.getValue().remove(employee);
//                }
//            });
//        }

//        System.out.println("all non managers under a manager: " + resultMap);

        // question 3
//        Set<Map.Entry<String, List<String>>> entries2 = resultMap.entrySet();
//        for (Map.Entry<String, List<String>> entry : entries2) {
//            List<String> employees = new ArrayList<>(entry.getValue());
//            employees.forEach(employee -> {
//                if (!resultMap.containsKey(employee)) {
//                    entry.getValue().remove(employee);
//                }
//            });
//        }

//        System.out.println("all managers under a manager: " + resultMap);

        // question 4
        Set<Map.Entry<String, List<String>>> entries3 = resultMap.entrySet();
        for (Map.Entry<String, List<String>> entry : entries3) {
            String manager = entry.getKey();
            if (!"CEO".equals(manager)) {
                continue;
            }
            List<String> ans = new ArrayList<>();
            ans.add(manager);
            findDirectLine(resultMap, entry.getValue(), ans);
        }

        directLineList.forEach(directLine -> {
            System.out.println(String.join(" -> ", directLine));
        });
    }

    private static void findDirectLine(Map<String, List<String>> resultMap, List<String> deepEmployee, List<String> ans) {
        if (deepEmployee == null) {
            directLineList.add(new ArrayList<>(ans));
            return;
        }
        for (String employee : deepEmployee) {
            ans.add(employee);
            findDirectLine(resultMap, resultMap.get(employee), ans);
            ans.remove(ans.size() - 1);
        }
    }
}
