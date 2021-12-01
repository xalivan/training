//package com.example.training.integration;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ContextConfiguration(initializers = {UserRepositoryTCIntegrationTest.Initializer.class})
//public class UserRepositoryTCIntegrationTest extends UserRepositoryCommonIntegrationTests {
//
//    @ClassRule
//    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
//            .withDatabaseName("integration-tests-db")
//            .withUsername("sa")
//            .withPassword("sa");
//
//    static class Initializer
//            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
//            TestPropertyValues.of(
//                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
//                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
//                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
//            ).applyTo(configurableApplicationContext.getEnvironment());
//        }
//    }
//}
