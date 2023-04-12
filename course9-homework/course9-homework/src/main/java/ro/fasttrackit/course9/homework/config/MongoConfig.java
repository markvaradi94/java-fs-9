package ro.fasttrackit.course9.homework.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig {
    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${mongodb.encryptedPassword}")
    private String encryptedPassword;

    @Value("${mongodb.encryptedUsername}")
    private String encryptedUsername;
    @Bean
    public StringEncryptor jasyptStringEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        encryptor.setIvGenerator(new RandomIvGenerator());
        encryptor.setPassword(System.getenv("JASYPT_ENCRYPTOR_PASSWORD"));
        return encryptor;
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(StringEncryptor jasyptStringEncryptor) {
        String decryptedPassword = jasyptStringEncryptor.decrypt(encryptedPassword);
        String decryptedUsername = jasyptStringEncryptor.decrypt(encryptedUsername);
        String uriWithUser = mongoUri.replace("<DECRYPTED_USER>", decryptedUsername);
        String connectionString = uriWithUser.replace("<DECRYPTED_PASSWORD>", decryptedPassword);
        MongoClient mongoClient = MongoClients.create(connectionString);
        return new SimpleMongoClientDatabaseFactory(mongoClient, database);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }
}
