package com.example.file_convertor;

import com.example.file_convertor.config.TestConfig;
import com.example.file_convertor.controller.FileController;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
public abstract class AbstractConfiguration {

    RequestSpecification requestSpecification;

    @Autowired
    TestConfig configurations;

    @Autowired
    FileController fileController;

    public final String TEST_TEMPLATE = "{\n" +
                            "  \"name\": \"string\",\n" +
                            "  \"price\": 0,\n" +
                            "  \"available\": true,\n" +
                            "  \"version\": 0,\n" +
                            "  \"functions\": [\n" +
                            "    \"string\"\n" +
                            "  ]\n" +
                            "}";


    @BeforeEach
    protected void setup(){
        RestAssured.baseURI = configurations.getBaseUri();
        RequestSpecBuilder builder = new RequestSpecBuilder();
        requestSpecification = builder.build();

    }


}
