# Save Service - 文件存储服务

## 项目简介

Save Service 是一个基于 Spring Boot 的文件存储和管理服务，主要用于游戏存档文件的上传、存储和下载。该项目集成了阿里云 OSS 对象存储服务，提供了完整的文件管理解决方案。

## 主要功能

### 🎮 游戏存档管理
- 支持游戏存档文件的上传和下载
- 自动生成文件访问链接
- 文件类型识别和图标显示
- 按时间排序的存档列表

### 📁 文件存储
- 集成阿里云 OSS 对象存储
- 支持大文件上传（最大 100MB）
- 文件上传进度监听
- 自动生成唯一文件名

### 🔐 用户认证
- 基于 Token 的用户认证系统
- 登录状态验证
- 安全的用户会话管理

### 🌐 Web 界面
- 响应式 Web 界面
- 文件上传和下载功能
- 实时文件列表展示
- 用户友好的操作界面

## 技术栈

- **后端框架**: Spring Boot 3.3.5
- **数据库**: MySQL 8.0
- **ORM 框架**: MyBatis 3.0.3
- **模板引擎**: Thymeleaf
- **对象存储**: 阿里云 OSS
- **前端**: HTML5 + JavaScript
- **构建工具**: Maven
- **Java 版本**: 17

## 项目结构

```
src/
├── main/
│   ├── java/cn/lazylhxzzy/save_upload/
│   │   ├── config/          # 配置类
│   │   ├── controller/      # 控制器
│   │   ├── listener/        # 事件监听器
│   │   ├── mapper/          # MyBatis 映射器
│   │   ├── pojo/           # 实体类
│   │   ├── service/        # 业务逻辑层
│   │   └── utils/          # 工具类
│   └── resources/
│       ├── mappers/        # MyBatis XML 映射文件
│       ├── static/         # 静态资源
│       └── templates/      # Thymeleaf 模板
└── test/                   # 测试代码
```

## 核心特性

### 文件上传
- 支持多种文件格式
- 自动文件类型检测
- 上传进度实时反馈
- 文件大小限制控制

### 数据管理
- MySQL 数据库存储文件元信息
- 支持文件列表查询和排序
- 文件访问统计和记录

### 安全控制
- IP 白名单访问控制
- Token 认证机制
- 文件访问权限管理

## 配置说明

### 数据库配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/saves
    username: root
    password: your_password
```

### 阿里云 OSS 配置
```yaml
aliyun:
  oss:
    endpoint: oss-cn-beijing.aliyuncs.com
    access-key-id: your_access_key
    access-key-secret: your_secret_key
    bucket-name: your_bucket_name
```

## 快速开始

1. **环境要求**
   - JDK 17+
   - MySQL 8.0+
   - Maven 3.6+

2. **数据库初始化**
   ```sql
   CREATE DATABASE saves;
   -- 创建相关表结构
   ```

3. **配置文件**
   - 修改 `application.yml` 中的数据库连接信息
   - 配置阿里云 OSS 相关参数

4. **启动应用**
   ```bash
   mvn spring-boot:run
   ```

5. **访问应用**
   - 服务地址: http://localhost:13588
   - 登录页面: http://localhost:13588/index.html

## API 接口

### 文件管理
- `GET /save/` - 文件列表页面
- `POST /save/upload` - 文件上传
- `GET /save/latest-save.zip` - 下载最新存档

### 用户认证
- `POST /auth/login` - 用户登录
- `GET /auth/validate` - Token 验证

## 部署说明

项目支持多种部署方式：
- 本地开发环境运行
- Docker 容器化部署
- 云服务器部署

## 版本信息

- 当前版本: 0.0.2
- 构建时间: 2024年
- 维护状态: 活跃开发中

## 许可证

本项目采用开源许可证，具体信息请查看 LICENSE 文件。

---

**注意**: 请确保在生产环境中修改默认的数据库密码和 OSS 配置信息，以保证系统安全性。
