package demo1;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemo {
    public static void main(String[] args) {
        Properties props = new Properties();
        // 服务器ip:端口号，集群用逗号分隔
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.104:9001");
        // 消费者指定组，名称可以随意，注意相同消费组中的消费者只能对同一个分区消费一次
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        // 是否启用自动提交，默认true
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        // 自动提交间隔时间1s
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        // key反序列化指定类
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // value反序列化指定类，注意生产者与消费者要保持一致，否则解析出问题
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());


        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("bingo"));
        while (true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100L));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, value = %s", record.offset(), record.value());
                System.out.println();
            }

        }

    }
}
