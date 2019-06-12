package br.com.mpr.ws.conf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by wagner on 6/22/18.
 */
@Configuration
@ComponentScan(basePackages = "br.com.mpr")
@EntityScan(basePackages = "br.com.mpr.ws.entity")
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ImportResource(value = {"classpath:queries/queries.xml"})
public class WsConfig {}
