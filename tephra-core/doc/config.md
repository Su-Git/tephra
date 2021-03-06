# core.tephra.config.properties
```properties
## 设置重载类根路径。
## 如果设置为空则表示不动态重载。
#tephra.bean.reload.class-path =

## 定时器执行实际设置
## 可设置为0 0 0 1 1 ?关闭定时器
## 每日定时器执行时机
#tephra.scheduler.date.cron = 30 30 4 * * ?
## 每小时定时器执行时机
#tephra.scheduler.hour.cron = 30 0 * * * ?
## 每分钟定时器执行时机
#tephra.scheduler.minute.cron = 0 * * * * ?
## 每秒钟定时器执行时机
#tephra.scheduler.seconds.cron = * * * * * ?

## 设置缓存接口类型，为空则表示使用默认实现；其他值参考如下：
## redis —— 使用redis缓存。
#tephra.cache.name =

## 设置缓存监听端口号。
## 设置为0或负数则不启动缓存监听器，此时将无法获得其他节点同步的缓存数据。
#tephra.cache.listen-port = 0

## 设置缓存监听处理器线程池最大线程数。
#tephra.cache.listener.max-thread = 5

## 设置缓存对象最大生存期，单位：分钟。
## 如果缓存对象最后一次访问时间超过此期限，将被自动移除。
## 如果不设置、或设置为0则表示不自动清理，即：缓存数据永久有效。
## 示例：
## tephra.cache.alive-time = 30
## 表示当缓存对象超过30分钟未被访问，将被从内存中被移除。
#tephra.cache.alive-time = 30

## 设置缓存的最大使用内存。
## 当缓存所使用的内存超过此设置时，将自动移除较长时间未被访问的缓存数据。
## 规则：数值+单位。
## 数值必须大于或等于1。
## 单位可选值为：k表示kb，m表示mb，g表示gb,t表示tb，单位不区分大小写。
#tephra.cache.max-memory = 1g

## 设置远程缓存节点搜索IP范围。
## 如设置为192.168.1.101-105则表示搜索以下IP地址：
## * 192.168.1.101
## * 192.168.1.102
## * 192.168.1.103
## * 192.168.1.104
## * 192.168.1.105
## 如设置为192.168.1.101则表示搜索以下IP地址：
## * 192.168.1.101
## 如果设置为空则表示不自动同步缓存数据。
#tephra.cache.remote.ips =
## 设置发送远程缓存最大线程数。
#tephra.cache.remote.max-thread = 5

## 设置Redis服务器地址。
#tephra.cache.redis.host = localhost
## 设置Redis最大连接数。
#tephra.cache.redis.max-total = 500
## 设置Redis最大闲置连接数。
#tephra.cache.redis.max-idle = 5
## 设置Redis最大等待时间，单位：毫秒。
#tephra.cache.redis.max-wait = 500

## 设置RSA执行目录。
#tephra.crypto.rsa.path = /WEB-INF/rsa

## 设置Freemarker模版文件根路径。
#tephra.freemarker.root = /WEB-INF/ftl
## 设置Freemarker模版文件后缀名。
#tephra.freemarker.suffix = .ftl

## 设置HTTP/S请求池最大实例数。
#tephra.util.http.pool.max = 256
## HTTP/S访问连接超时（毫秒）
#tephra.util.http.connect.time-out = 5000
## HTTP/S访问读取超时（毫秒）
#tephra.util.http.read.time-out = 20000

## 设置时间哈希有效时长，单位：秒。
## 如果设置为0或负数则表示不启用时间哈希验证（验证时总返回true）。
#tephra.util.time-hash.range = 0
```