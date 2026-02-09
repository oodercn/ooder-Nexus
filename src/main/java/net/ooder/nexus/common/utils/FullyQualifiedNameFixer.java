package net.ooder.nexus.common.utils;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * 完全限定名修复工�? - 修复代码中使用完全限定名引用旧包的情�?
 */
public class FullyQualifiedNameFixer {

    // 完全限定名映�?: 旧完全限定名 -> 新完全限定名
    private static final Map<String, String> FQN_MAPPINGS = new LinkedHashMap<>();

    static {
        // model 迁移�? domain
        FQN_MAPPINGS.put("net.ooder.nexus.domain.network.model.NetworkDevice", "net.ooder.nexus.domain.network.model.NetworkDevice");
        FQN_MAPPINGS.put("net.ooder.nexus.domain.security.model.SecurityLog", "net.ooder.nexus.domain.security.model.SecurityLog");
    }

    public static void main(String[] args) throws IOException {
        String baseDir = "src/main/java/net/ooder/nexus";

        System.out.println("=== Fully Qualified Name Fixer ===\n");

        int fixedCount = 0;

        // 修复�?有Java文件
        Path directory = Paths.get(baseDir);

        List<Path> javaFiles = Files.walk(directory)
                .filter(Files::isRegularFile)
                .filter(p -> p.toString().endsWith(".java"))
                .collect(java.util.stream.Collectors.toList());

        for (Path file : javaFiles) {
            boolean fixed = fixFile(file);
            if (fixed) {
                fixedCount++;
                System.out.println("  Fixed: " + file.getFileName());
            }
        }

        System.out.println("\n=== Fix Summary ===");
        System.out.println("Total files fixed: " + fixedCount);
        System.out.println("\nNext step: Run 'mvn clean compile -s settings.xml' to verify");
    }

    private static boolean fixFile(Path file) throws IOException {
        String content = new String(Files.readAllBytes(file));
        String originalContent = content;

        // 按长度降序排序，避免部分匹配问题
        List<Map.Entry<String, String>> sortedMappings = new ArrayList<>(FQN_MAPPINGS.entrySet());
        sortedMappings.sort((a, b) -> Integer.compare(b.getKey().length(), a.getKey().length()));

        for (Map.Entry<String, String> mapping : sortedMappings) {
            String oldFQN = mapping.getKey();
            String newFQN = mapping.getValue();

            // 替换完全限定�?
            content = content.replace(oldFQN, newFQN);
        }

        // 如果有修改，写回文件
        if (!content.equals(originalContent)) {
            Files.write(file, content.getBytes());
            return true;
        }
        return false;
    }
}
