package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI scoreOpenAPI() {
        return new OpenAPI().info(new Info().title("学生成绩管理系统接口文档").version("2.0")
                .description("实验二：内存存储，重启恢复示例数据。除删除成功返回 204 空响应外，"
                        + "响应均为 code/message/data；成功 code=0，失败 data=null。\n\n"
                        + "业务码：40001 参数不合法；40401 资源不存在；40901 重复录入；50000 服务器内部错误。\n\n"
                        + "初始数据已包含所有学生课程组合。测试创建成功时，先删除 id=1，再录入学号 2023010001、课程 C001。"));
    }

    @Bean
    public OpenApiCustomizer errorResponses() {
        return api -> api.getPaths().forEach((path, item) -> item.readOperationsMap().forEach((method, op) -> {
            op.getResponses().addApiResponse("500", error(50000, "服务器内部错误"));
            switch (method) {
                case POST -> {
                    ApiResponse success = op.getResponses().remove("200");
                    if (success != null) {
                        success.setDescription("录入成功，返回新生成的 id 和完整成绩记录");
                        op.getResponses().addApiResponse("201", success);
                    }
                    op.getResponses().addApiResponse("400", error(40001, "分数必须在 0 到 100 之间"));
                    op.getResponses().addApiResponse("404", error(40401, "学生不存在"));
                    op.getResponses().get("404").setDescription("学生不存在或课程不存在；业务码均为 40401");
                    op.getResponses().addApiResponse("409", error(40901, "该学生这门课程已录入成绩"));
                }
                case PUT -> {
                    op.getResponses().addApiResponse("400", error(40001, "分数必须在 0 到 100 之间"));
                    op.getResponses().addApiResponse("404", error(40401, "成绩不存在"));
                }
                case DELETE -> {
                    op.getResponses().remove("200");
                    op.getResponses().addApiResponse("204", new ApiResponse().description("删除成功，无响应体"));
                    op.getResponses().addApiResponse("400", error(40001, "请求参数不合法"));
                    op.getResponses().addApiResponse("404", error(40401, "成绩不存在"));
                }
                default -> { }
            }
        }));
    }

    private ApiResponse error(int code, String message) {
        Schema<?> schema = new ObjectSchema()
                .addProperty("code", new IntegerSchema())
                .addProperty("message", new StringSchema())
                .addProperty("data", new ObjectSchema().nullable(true));
        Map<String, Object> example = new LinkedHashMap<>();
        example.put("code", code);
        example.put("message", message);
        example.put("data", null);
        return new ApiResponse().description(message).content(new Content().addMediaType("application/json",
                new io.swagger.v3.oas.models.media.MediaType().schema(schema).example(example)));
    }
}
