# http.ctrl.tephra.config.properties
```properties
## HTTP请求URL根路径。
#tephra.ctrl.http.root =

## 设置是否允许执行JSP脚本。
## 如果设置为true则表示允许。
## 如果设置为false则表示不允许。
#tephra.ctrl.http.security.jsp.enable = false
## 设置是否自动过滤XSS攻击脚本数据。
## 如果设置为true则表示自动过滤XSS攻击脚本内容。
## 如果设置为false则表示不过滤。
#tephra.ctrl.http.security.xss = true
## 设置XSS过滤忽略URI地址集，多个间以逗号分隔。
#tephra.ctrl.http.security.xss.ignore =

## 设置是否忽略站点根。
## 如果设置为true，则访问站点根时不使用定义的服务进行处理。
## 如果设置为false，则访问站点根时使用定义的服务进行处理。
#tephra.ctrl.http.ignor.root = false
## 设置忽略URI地址前缀。多个设置值之间使用逗号进行分隔。
## 当访问的URI地址前缀与设置值相同时不使用定义的服务进行处理。
#tephra.ctrl.http.ignor.prefixes = /upload/
## 设置忽略URI地址文件名。多个设置值之间使用逗号进行分隔。
## 当访问的URI地址文件名与设置值相同时不使用定义的服务进行处理。
#tephra.ctrl.http.ignor.names =
## 设置忽略URI地址后缀。多个设置值之间使用逗号进行分隔。
## 当访问的URI地址后缀与设置值相同时不使用定义的服务进行处理。
#tephra.ctrl.http.ignor.suffixes = .ico,.js,.css,.html

## 设置上传配置文件路径。
## 此目录下的配置每分钟自动更新。
## 文件名为监听key+.json。
## 配置可参考key.json。
#tephra.ctrl.http.upload.json-configs = /WEB-INF/upload
## 设置文件上传最大文件大小。
#tephra.ctrl.http.upload.max-size = 1m

## 设置WebSocket最大连接客户端数，超过此设置将不再建立新的连接。
#tephra.ctrl.http.web-socket.max = 64
```