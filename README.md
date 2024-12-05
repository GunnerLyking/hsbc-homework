## 事件管理系统
### 项目简介：
本项目是一个简单的事件管理系统，环境为JDK17+springboot2.7.6。事件信息包括事件的 ID、类型、状态、创建时间等，所有数据都存储在内存中，无需持久化数据库。系统设计支持并发操作、异常处理、分页查询等常见功能，并具有一定的性能测试能力。

### 运行项目
请提前将环境JDK配置为17
```
mvn clean packag
java -jar target/incident-0.0.1-SNAPSHOT.jar
```
[点击这里查看首页](src/main/resources/static/index.html)

### 功能简介：
* 事件管理：实现事件的增、删、改、查操作，用户可以查询所有现有的事件，或根据事件 ID 获取单个事件的详细信息。
* 分页查询：支持分页查询，用户可以根据请求的页数和每页大小获取事件列表。
* 削峰处理：使用队列（LinkedBlockingQueue）实现事件添加的削峰处理，当并发请求过多时，系统能够保证顺序处理并避免超载。
* 异常处理：全局异常处理，确保系统在发生错误时能返回友好的错误信息，并且能够记录异常日志。
* 性能测试：通过 JUnit 集成压力测试，模拟高并发情况，测试系统在高负载下的表现。
* 内存数据管理：本系统使用内存数据结构（如 ConcurrentHashMap 和 AtomicInteger）模拟数据库操作，实现事件的存储和管理，便于快速开发和测试。
* 高并发支持：使用阻塞队列来处理高并发事件请求，确保系统稳定性和性能
### 项目结构：
* Controller层：负责接收用户请求，调用服务层逻辑，并将结果返回给用户。
* Service层：业务逻辑层，处理事件的增删改查，包含削峰队列的处理和分页查询。
* DAO层：数据访问层，模拟数据库操作，管理事件的存取。
* 异常处理层：全局异常捕获，保证系统的健壮性和一致性。
* 实体类：定义事件相关的属性（如 ID、状态、类型、创建时间）以及分页查询所需的 Page 类。
* 测试层：通过 JUnit 进行单元测试、性能测试，确保系统的稳定性。
