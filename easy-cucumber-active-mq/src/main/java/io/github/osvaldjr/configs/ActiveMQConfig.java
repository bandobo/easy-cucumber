package io.github.osvaldjr.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.osvaldjr.objects.properties.ActiveMQProperties;
import io.github.osvaldjr.objects.properties.ApplicationProperties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class ActiveMQConfig {

  private final ObjectMapper objectMapper;
  private final ApplicationProperties applicationProperties;

  @Autowired
  public ActiveMQConfig(ObjectMapper objectMapper, ApplicationProperties applicationProperties) {
    this.objectMapper = objectMapper;
    this.applicationProperties = applicationProperties;
  }

  @Bean(name = "easyCucumberJmsTemplate")
  public JmsTemplate jmsTemplate() {
    JmsTemplate template = new JmsTemplate();
    template.setConnectionFactory(connectionFactory());
    template.setMessageConverter(messageConverter());
    return template;
  }

  public ActiveMQConnectionFactory connectionFactory() {
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
    ActiveMQProperties activemq = applicationProperties.getDependencies().getActivemq();
    connectionFactory.setBrokerURL(activemq.getBrokerUrl());
    if (StringUtils.isNotEmpty(activemq.getUser())) {
      connectionFactory.setUserName(activemq.getUser());
    }
    if (StringUtils.isNotEmpty(activemq.getPassword())) {
      connectionFactory.setPassword(activemq.getPassword());
    }
    return connectionFactory;
  }

  public MessageConverter messageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setObjectMapper(objectMapper);
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    return converter;
  }
}
