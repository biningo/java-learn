package hello;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerCollbackDemo {
    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.104:9001");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        properties.put(ProducerConfig.BATCH_SIZE_CONFIG,0);


        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        for (int i = 0; i < 10; i++) {
            //可以指定分区 也可以执行分区选择策略自动选择分区
            //也可以指定key，按key的hash值来选择分区
            //如果都没有指定，则进行系统分分区器来选择
            producer.send(new ProducerRecord<String, String>("first",i+"", "biningo-" + 1),

                    new Callback() {
                        public void onCompletion(RecordMetadata recordMetadata, Exception e) {

                            if(e==null){
                                System.out.println(recordMetadata.partition()+"--"+recordMetadata.offset());
                            }else{
                                e.printStackTrace();
                            }
                        }
                    }

            );


        }
        producer.close();

    }
}
