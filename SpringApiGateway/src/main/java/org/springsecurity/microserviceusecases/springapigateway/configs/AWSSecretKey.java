package org.springsecurity.microserviceusecases.springapigateway.configs;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springsecurity.microserviceusecases.springapigateway.dtos.AwsSecretManager;
import org.springsecurity.microserviceusecases.springapigateway.exceptions.AWSSecretKeyFetchException;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Component
public class AWSSecretKey {

    Logger logger = LoggerFactory.getLogger(AWSSecretKey.class);

    @Value("${cloud.aws.credentials.access-key-id}")
    private String accessKeyId;

    @Value("${cloud.aws.credentials.secret-access-key}")
    private String secretAccessKey;


    public String getSecret() {

        String secretName = "prod/auth/jwt";
        Region region = Region.of("us-east-1");

        // Create a Secrets Manager client
        logger.info("access key id: "+accessKeyId);
        logger.info("access secret key: "+secretAccessKey);
        AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey));

        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                .build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse getSecretValueResponse;

        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            // For a list of exceptions thrown, see
            // https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
            throw e;
        }

        String secret = getSecretValueResponse.secretString();
        logger.info(secret);
        Gson gson = new Gson();

        if (secret == null) {
            throw new AWSSecretKeyFetchException("null AWS secret key");
        }

        AwsSecretManager awsSecretManager = gson.fromJson(secret, AwsSecretManager.class);
        return awsSecretManager.getJwtSecretKey();

    }
}
