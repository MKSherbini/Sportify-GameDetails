package com.example.gamedetails.config;

import com.example.gamedetails.models.orm.Game;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class InboundsIntegrationConfig {

    private final String queueName = "testQueueAuto";
//    private String queueName = "testQueue";

    @Bean
    public AbstractMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        messageListenerContainer.setQueueNames(queueName);
//        messageListenerContainer.setChannelTransacted(true);
//        messageListenerContainer.setTransactionManager(tr);
//        messageListenerContainer.setErrorHandler(new CustomErrorHandler());
        return messageListenerContainer;
    }

    @Bean
    public AmqpInboundChannelAdapter inboundChannelAdapter(AbstractMessageListenerContainer messageListenerContainer) {
        AmqpInboundChannelAdapter adapter = new AmqpInboundChannelAdapter(messageListenerContainer);
        adapter.setOutputChannelName("fromRabbit");
        return adapter;
    }

    @Bean
    public MessageChannel fromRabbit() {
        return new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "fromRabbit", outputChannel = "testChannel")
    public JsonToObjectTransformer jsonToObjectTransformer() {
        return new JsonToObjectTransformer(Game.class);
    }

    @Bean
    public MessageChannel testChannel() {
//        return new DirectChannel();
        return new ExecutorChannel(executor());
    }

    @Bean
    public MessageChannel returnTestChannel() {
//        return new ExecutorChannel(executor());
        return new PublishSubscribeChannel(executor());
    }

    @Bean
    public ThreadPoolTaskExecutor executor() {

        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(10);
        pool.setMaxPoolSize(10);
        pool.setThreadNamePrefix("TaskExecutor-");
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    // todo look up spring dsl
//    @Bean
//    public IntegrationFlow flow() {
//        return f -> f.handle()
//         .handle(...)
//         .transform(...)
//         .handle(...);
//    }


//    @Router(inputChannel = "failedEmailFetch",defaultOutputChannel = "errorChannel")
//    public String handleError(Message<AggregateMessageDeliveryException> message) {
//        log.info("{}",message.getPayload().getCause().getCause());
//        if( message.getPayload().getRootCause() instanceof MessageException)
//            return "customError";
//        else
//            return "errorChannel";
//    }

//    @Bean
//    @ServiceActivator(inputChannel = "failedEmailFetch")
//    public ErrorMessageExceptionTypeRouter handleError(MessageHandlingException messageHandlingException) {
//        ErrorMessageExceptionTypeRouter errorMessageExceptionTypeRouter = new ErrorMessageExceptionTypeRouter();
//        errorMessageExceptionTypeRouter.setChannelMapping("com.XXXXXX.exception.MessageException","customError");
//        errorMessageExceptionTypeRouter.setDefaultOutputChannelName("errorChannel");
//        return errorMessageExceptionTypeRouter;
//    }
}
