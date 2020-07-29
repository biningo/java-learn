package hello;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class ConsumerDemo {
    public static void main(String[] args) {
        Properties props = new Properties();
        // 服务器ip:端口号，集群用逗号分隔
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.104:9001");
        // 消费者指定组，名称可以随意，注意相同消费组中的消费者只能对同一个分区消费一次
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test7");
        // 是否启用自动提交，默认true
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,true);
        // 自动提交间隔时间1s
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        // key反序列化指定类
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // value反序列化指定类，注意生产者与消费者要保持一致，否则解析出问题
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        //重置offset  【offset已经过期 或者换组了 换组后默认使用lastest消费接下来最新的数据，手动修改为earliest可以消费之前的数据】
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("bingo"));
        while (true){
            //拉去数据 延迟代表 当拉去数据等了延迟时间之后，就直接返回表示没有拉去到数据
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000L));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.key());
                System.out.printf("offset = %d, value = %s", record.offset(), record.value());
                System.out.println();
            }
            //当数据消费完后进行提交offset  同步提交 会阻塞 （也可以kafka自动提交 但是不好把控 任意容易丢数据）
            consumer.commitSync();

//            consumer.commitAsync(new OffsetCommitCallback() {
//                @Override
//                public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {
//
//                }
//            }); //异步提交

        }

    }
}
