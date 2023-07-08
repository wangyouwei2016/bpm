# agilebpm-basic

## 敏捷开发平台

全新开源的 vue3 TS 的前后台分离的开发平台

前端基于 `Vue3` 组合式API, `TypeScript`, `Element-plus`,`Pinia`,`Axios`,支持三种布局, 自定义主题风格
后端基于 `AgileBPM` 流程表单,`SpringBoot `,`MybatisPlus` ,`Activiti7` ,`Jackson` , `JDK17` ,`Hutool`  等主流技术栈

后端是 Maven模块化管理的SOA的 SpringBoot 单体架构，模块间低耦合，可选择模块组合成微服务架构。

# 技术交流群

q群3：108698205，如果进了技术群，请先看群公告再问问题，不方便QQ的可添加微信 agileBPM01 ，他会拉进微信群


## 起步

1. 下载 `agile-bpm-basic`项目,以 maven 项目引入工程 并 根目录下 执行 `mvn clean install -DskipTests`

2. 执行数据库脚本创建数据库 `/doc/sql/mysql/full/agilebpm_full.sql`

3. 修改数据库、Redis 等配置文件`\ab-spring-boot\ab-spring-boot-app\src\main\resources\application.yml`，配置文件会有详细注释，请自行修改。

4. 通过 Main 方法启动后端服务 `\ab-spring-boot\ab-spring-boot-app\src\main\java\com\dstz\AbSpringBootApp.java`，默认端口为 `8080`

5. 下载 前端工程 `agilebpm-ui` ，并在根目录下 执行 命令 `yarn install`

6. 完成安装后，在 `vite.config.ts`中修改后台服务地址，如 `http://localhost:8080/` 然后执行 ` npm run dev ` 启动前端项目。

默认访问 前端地址  `http://127.0.0.1:8088/` 即可体验项目


推荐 vscode 作为前端开发IDE，请务必安装一下插件
- local-history (opens new window)local-history]（可找回丢失代码）
- eslint (opens new window)eslint（建议开启 Eslint 保存时自动修复）
- stylelint (opens new window)stylelint
- Prettier - Code formatter 代码自动格式化
- volar (opens new window)vue3 开发必备


## 目前 `2.5` 版本涵盖了以下功能

- 个人办公： 待办事项、抄送传阅、办理历史、发起申请、申请历史、我的草稿

- 内容管理： 公告、新闻

- 组织管理：组织管理、用户管理、角色管理、 岗位管理。笔者十多年研发见过无数组织架构，它堪称最精简最完美的设计。

- 流程管理： 表单表单设计、流程设计、流程实例管理、任务管理、系统对话框管理。（目前基于AgileBPM的商业组件，如果觉得不合适可切换为其他框架的流程模块，目前没有能入眼的）

- 系统管理：字典分类管理、异常日志、系统资源、系统属性、常用脚本、短信邮件通知

## 系统界面预览



## 开发计划

### v 2.6 计划新增 OA 人事模块

规划包含 招聘需求、招聘面试、员工档案、转正、调岗、离职、 等功能

### 规划中排期待定的事项

- 人事中 请假、加班、出差、外勤打卡

- 资产管理 的资产信息、资产领用、资产转移、采购申请 

- 持续维护前端工具包，以及前端组件库，等组件库稳定后开放源码出来

- 维护系统中用的部分功能的 TS 类型定义，由于目前开发工作繁重，部分页面 暂未维护。

- 首页组件，如公告新闻、待办等

- 常用语，适配黑夜模式，全面支持国际化，站内消息通知

欢迎有兴趣的同学  pull request ,或者提需求



