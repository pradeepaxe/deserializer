package com.kafka.deserializer.deserializer;


import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

import dto.Order;

public class OrderConsumer {
   private static ConsumerRecords<String, Integer> orders;

public static void main(String[]args){
    Properties props = new Properties();
    props.setProperty("bootstrap.servers", "localhost:9092");
    props.setProperty("key.deserializer",StringDeserializer.class.getName());
    props.setProperty("value.deserializer",OrderDeserializer.class.getName());
    props.setProperty("groupId", "orderGroup");
    
    
    KafkaConsumer<String,Order> consumer = new KafkaConsumer<>(props);
    consumer.subscribe(Collections.singleton("OrderCSTopics"));
    ConsumerRecords<String, Order> records = consumer.poll(Duration.ofSeconds(20));
    for(ConsumerRecord<String, Order> record:records){
        String customerName = record.key();
        Order order = record.value();
        System.out.println("Customer name"+ customerName);
        System.out.println("order details:" + order.getProduct());        
    }
    consumer.close();

   } 
}
