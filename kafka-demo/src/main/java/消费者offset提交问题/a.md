
过早提交offset，则消息没消费完，offset已经提交了，则会丢数据
过晚提交offset，则消息已经处理完了，但是consumer挂了，再次请求offset，则会造成消息重复消费


提交offset+消费=事务
要么 提交offset+消费全部成功  要么全部失败


