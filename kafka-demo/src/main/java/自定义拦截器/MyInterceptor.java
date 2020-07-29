package 自定义拦截器;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

//拦截器在properties中添加

public class MyInterceptor implements ProducerInterceptor<String,String> {

    //发送一条数据之后消息
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        //new 一个新的 record 然后重新设置value
        return new ProducerRecord<String, String>(producerRecord.topic(),producerRecord.partition(),
                producerRecord.key(),System.currentTimeMillis()+":"+producerRecord.value());
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }


    //producer close时调用
    @Override
    public void close() {
        System.out.println("procedure关闭了");
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
