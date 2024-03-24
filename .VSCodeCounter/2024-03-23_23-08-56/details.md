# Details

Date : 2024-03-23 23:08:56

Directory d:\\基于web的项目管理系统\\Psm

Total : 106 files,  7369 codes, 262 comments, 577 blanks, all 8208 lines

[Summary](results.md) / Details / [Diff Summary](diff.md) / [Diff Details](diff-details.md)

## Files
| filename | language | code | comment | blank | total |
| :--- | :--- | ---: | ---: | ---: | ---: |
| [.idea/workspace.xml](/.idea/workspace.xml) | XML | 78 | 0 | 0 | 78 |
| [README.md](/README.md) | Markdown | 3 | 0 | 0 | 3 |
| [package-lock.json](/package-lock.json) | JSON | 6 | 0 | 1 | 7 |
| [psmback/pom.xml](/psmback/pom.xml) | XML | 80 | 0 | 5 | 85 |
| [psmback/psmback-common/pom.xml](/psmback/psmback-common/pom.xml) | XML | 95 | 4 | 4 | 103 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/config/DataSourceConfig.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/config/DataSourceConfig.java) | Java | 5 | 0 | 4 | 9 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/config/MyBatisConfig.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/config/MyBatisConfig.java) | Java | 14 | 3 | 3 | 20 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/config/RedisConfig.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/config/RedisConfig.java) | Java | 24 | 3 | 5 | 32 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/config/SecurityConfig.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/config/SecurityConfig.java) | Java | 39 | 2 | 4 | 45 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/config/WebConfig.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/config/WebConfig.java) | Java | 14 | 6 | 2 | 22 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/Authority.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/Authority.java) | Java | 11 | 0 | 4 | 15 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/LoginUser.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/LoginUser.java) | Java | 57 | 0 | 10 | 67 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/Project.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/Project.java) | Java | 15 | 0 | 3 | 18 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/Task.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/Task.java) | Java | 28 | 0 | 5 | 33 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/Team.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/Team.java) | Java | 19 | 0 | 3 | 22 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/User.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/User.java) | Java | 19 | 4 | 5 | 28 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/vo/ProjectAuthorities.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/vo/ProjectAuthorities.java) | Java | 9 | 0 | 4 | 13 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/vo/ProjectDetails.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/vo/ProjectDetails.java) | Java | 9 | 0 | 3 | 12 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/vo/TaskView.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/vo/TaskView.java) | Java | 10 | 0 | 4 | 14 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/filter/JwtAuthenticationTokenFilter.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/filter/JwtAuthenticationTokenFilter.java) | Java | 42 | 5 | 5 | 52 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/result/CommonResult.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/result/CommonResult.java) | Java | 26 | 23 | 4 | 53 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/result/ResponseCode.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/result/ResponseCode.java) | Java | 37 | 7 | 4 | 48 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/utils/FastJsonRedisSerializer.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/utils/FastJsonRedisSerializer.java) | Java | 44 | 36 | 15 | 95 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/utils/JwtUtils.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/utils/JwtUtils.java) | Java | 32 | 3 | 3 | 38 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/utils/PaginationUtils.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/utils/PaginationUtils.java) | Java | 11 | 0 | 4 | 15 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/utils/RedisUtils.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/utils/RedisUtils.java) | Java | 70 | 21 | 6 | 97 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/utils/psmAnnotation/AuthProject.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/utils/psmAnnotation/AuthProject.java) | Java | 10 | 0 | 3 | 13 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/utils/psmAspect/AuthProjectAspect.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/utils/psmAspect/AuthProjectAspect.java) | Java | 46 | 0 | 9 | 55 |
| [psmback/psmback-dao/pom.xml](/psmback/psmback-dao/pom.xml) | XML | 22 | 0 | 2 | 24 |
| [psmback/psmback-dao/src/main/java/com/zz/psmback/dao/ProjectDao.java](/psmback/psmback-dao/src/main/java/com/zz/psmback/dao/ProjectDao.java) | Java | 10 | 0 | 4 | 14 |
| [psmback/psmback-dao/src/main/java/com/zz/psmback/dao/TaskDao.java](/psmback/psmback-dao/src/main/java/com/zz/psmback/dao/TaskDao.java) | Java | 11 | 0 | 4 | 15 |
| [psmback/psmback-dao/src/main/java/com/zz/psmback/dao/TeamDao.java](/psmback/psmback-dao/src/main/java/com/zz/psmback/dao/TeamDao.java) | Java | 7 | 0 | 3 | 10 |
| [psmback/psmback-dao/src/main/java/com/zz/psmback/dao/UserDao.java](/psmback/psmback-dao/src/main/java/com/zz/psmback/dao/UserDao.java) | Java | 14 | 0 | 4 | 18 |
| [psmback/psmback-service/pom.xml](/psmback/psmback-service/pom.xml) | XML | 31 | 0 | 4 | 35 |
| [psmback/psmback-service/src/main/java/com/zz/psmback/service/LoginService.java](/psmback/psmback-service/src/main/java/com/zz/psmback/service/LoginService.java) | Java | 9 | 0 | 4 | 13 |
| [psmback/psmback-service/src/main/java/com/zz/psmback/service/ProjectService.java](/psmback/psmback-service/src/main/java/com/zz/psmback/service/ProjectService.java) | Java | 8 | 0 | 3 | 11 |
| [psmback/psmback-service/src/main/java/com/zz/psmback/service/TaskService.java](/psmback/psmback-service/src/main/java/com/zz/psmback/service/TaskService.java) | Java | 8 | 0 | 5 | 13 |
| [psmback/psmback-service/src/main/java/com/zz/psmback/service/UserService.java](/psmback/psmback-service/src/main/java/com/zz/psmback/service/UserService.java) | Java | 12 | 0 | 5 | 17 |
| [psmback/psmback-service/src/main/java/com/zz/psmback/service/impl/LoginServiceImpl.java](/psmback/psmback-service/src/main/java/com/zz/psmback/service/impl/LoginServiceImpl.java) | Java | 114 | 10 | 7 | 131 |
| [psmback/psmback-service/src/main/java/com/zz/psmback/service/impl/ProjectServiceImpl.java](/psmback/psmback-service/src/main/java/com/zz/psmback/service/impl/ProjectServiceImpl.java) | Java | 74 | 5 | 5 | 84 |
| [psmback/psmback-service/src/main/java/com/zz/psmback/service/impl/TaskServiceImpl.java](/psmback/psmback-service/src/main/java/com/zz/psmback/service/impl/TaskServiceImpl.java) | Java | 124 | 0 | 8 | 132 |
| [psmback/psmback-service/src/main/java/com/zz/psmback/service/impl/UserDetailsServiceImpl.java](/psmback/psmback-service/src/main/java/com/zz/psmback/service/impl/UserDetailsServiceImpl.java) | Java | 46 | 0 | 6 | 52 |
| [psmback/psmback-service/src/main/java/com/zz/psmback/service/impl/UserServiceImpl.java](/psmback/psmback-service/src/main/java/com/zz/psmback/service/impl/UserServiceImpl.java) | Java | 89 | 0 | 10 | 99 |
| [psmback/psmback-service/src/test/java/Atest.java](/psmback/psmback-service/src/test/java/Atest.java) | Java | 13 | 0 | 5 | 18 |
| [psmback/psmback-web/.mvn/wrapper/maven-wrapper.properties](/psmback/psmback-web/.mvn/wrapper/maven-wrapper.properties) | Properties | 2 | 0 | 1 | 3 |
| [psmback/psmback-web/mvnw.cmd](/psmback/psmback-web/mvnw.cmd) | Batch | 118 | 51 | 37 | 206 |
| [psmback/psmback-web/pom.xml](/psmback/psmback-web/pom.xml) | XML | 47 | 0 | 1 | 48 |
| [psmback/psmback-web/src/main/java/com/zz/psmback/web/PsmbackWebApplication.java](/psmback/psmback-web/src/main/java/com/zz/psmback/web/PsmbackWebApplication.java) | Java | 11 | 0 | 4 | 15 |
| [psmback/psmback-web/src/main/java/com/zz/psmback/web/controller/LoginController.java](/psmback/psmback-web/src/main/java/com/zz/psmback/web/controller/LoginController.java) | Java | 28 | 0 | 7 | 35 |
| [psmback/psmback-web/src/main/java/com/zz/psmback/web/controller/ProjectController.java](/psmback/psmback-web/src/main/java/com/zz/psmback/web/controller/ProjectController.java) | Java | 21 | 0 | 3 | 24 |
| [psmback/psmback-web/src/main/java/com/zz/psmback/web/controller/TaskController.java](/psmback/psmback-web/src/main/java/com/zz/psmback/web/controller/TaskController.java) | Java | 32 | 0 | 5 | 37 |
| [psmback/psmback-web/src/main/java/com/zz/psmback/web/controller/UserController.java](/psmback/psmback-web/src/main/java/com/zz/psmback/web/controller/UserController.java) | Java | 43 | 0 | 5 | 48 |
| [psmback/psmback-web/src/main/resources/application.yaml](/psmback/psmback-web/src/main/resources/application.yaml) | YAML | 27 | 1 | 9 | 37 |
| [psmback/psmback-web/src/main/resources/mapper/ProjectMapper.xml](/psmback/psmback-web/src/main/resources/mapper/ProjectMapper.xml) | XML | 15 | 0 | 1 | 16 |
| [psmback/psmback-web/src/main/resources/mapper/TaskMapper.xml](/psmback/psmback-web/src/main/resources/mapper/TaskMapper.xml) | XML | 13 | 0 | 2 | 15 |
| [psmback/psmback-web/src/main/resources/mapper/TeamMapper.xml](/psmback/psmback-web/src/main/resources/mapper/TeamMapper.xml) | XML | 5 | 0 | 2 | 7 |
| [psmback/psmback-web/src/main/resources/mapper/UserMapper.xml](/psmback/psmback-web/src/main/resources/mapper/UserMapper.xml) | XML | 31 | 0 | 4 | 35 |
| [psmback/psmback-web/src/test/java/com/zz/psmback/web/PsmbackWebApplicationTests.java](/psmback/psmback-web/src/test/java/com/zz/psmback/web/PsmbackWebApplicationTests.java) | Java | 49 | 27 | 8 | 84 |
| [psmfront/README.md](/psmfront/README.md) | Markdown | 11 | 0 | 8 | 19 |
| [psmfront/index.html](/psmfront/index.html) | HTML | 13 | 0 | 1 | 14 |
| [psmfront/package-lock.json](/psmfront/package-lock.json) | JSON | 2,896 | 0 | 1 | 2,897 |
| [psmfront/package.json](/psmfront/package.json) | JSON | 31 | 0 | 1 | 32 |
| [psmfront/public/vite.svg](/psmfront/public/vite.svg) | XML | 1 | 0 | 0 | 1 |
| [psmfront/src/App.vue](/psmfront/src/App.vue) | vue | 7 | 0 | 5 | 12 |
| [psmfront/src/api/interface.ts](/psmfront/src/api/interface.ts) | TypeScript | 32 | 0 | 0 | 32 |
| [psmfront/src/api/login.ts](/psmfront/src/api/login.ts) | TypeScript | 31 | 0 | 0 | 31 |
| [psmfront/src/api/project.ts](/psmfront/src/api/project.ts) | TypeScript | 11 | 0 | 0 | 11 |
| [psmfront/src/api/task.ts](/psmfront/src/api/task.ts) | TypeScript | 16 | 0 | 1 | 17 |
| [psmfront/src/api/user.ts](/psmfront/src/api/user.ts) | TypeScript | 41 | 1 | 2 | 44 |
| [psmfront/src/components/details/demand.vue](/psmfront/src/components/details/demand.vue) | vue | 148 | 0 | 15 | 163 |
| [psmfront/src/components/details/view.vue](/psmfront/src/components/details/view.vue) | vue | 96 | 0 | 12 | 108 |
| [psmfront/src/components/login.vue](/psmfront/src/components/login.vue) | vue | 211 | 0 | 35 | 246 |
| [psmfront/src/components/main-views/home.vue](/psmfront/src/components/main-views/home.vue) | vue | 169 | 0 | 33 | 202 |
| [psmfront/src/components/main-views/user.vue](/psmfront/src/components/main-views/user.vue) | vue | 176 | 0 | 22 | 198 |
| [psmfront/src/components/pages/file.vue](/psmfront/src/components/pages/file.vue) | vue | 22 | 0 | 2 | 24 |
| [psmfront/src/components/pages/index.vue](/psmfront/src/components/pages/index.vue) | vue | 13 | 0 | 3 | 16 |
| [psmfront/src/components/pages/personal.vue](/psmfront/src/components/pages/personal.vue) | vue | 314 | 0 | 33 | 347 |
| [psmfront/src/components/pages/project.vue](/psmfront/src/components/pages/project.vue) | vue | 94 | 0 | 3 | 97 |
| [psmfront/src/components/pages/project/createProject.vue](/psmfront/src/components/pages/project/createProject.vue) | vue | 124 | 0 | 10 | 134 |
| [psmfront/src/components/pages/project/details.vue](/psmfront/src/components/pages/project/details.vue) | vue | 69 | 0 | 6 | 75 |
| [psmfront/src/components/pages/project/projectView.vue](/psmfront/src/components/pages/project/projectView.vue) | vue | 81 | 0 | 5 | 86 |
| [psmfront/src/components/pages/task.vue](/psmfront/src/components/pages/task.vue) | vue | 39 | 0 | 2 | 41 |
| [psmfront/src/components/pages/task/taskUpdate.vue](/psmfront/src/components/pages/task/taskUpdate.vue) | vue | 94 | 0 | 5 | 99 |
| [psmfront/src/components/pages/task/taskView.vue](/psmfront/src/components/pages/task/taskView.vue) | vue | 36 | 0 | 6 | 42 |
| [psmfront/src/components/pages/team.vue](/psmfront/src/components/pages/team.vue) | vue | 25 | 0 | 2 | 27 |
| [psmfront/src/components/pages/team/member.vue](/psmfront/src/components/pages/team/member.vue) | vue | 11 | 0 | 4 | 15 |
| [psmfront/src/components/pages/team/teamView.vue](/psmfront/src/components/pages/team/teamView.vue) | vue | 8 | 0 | 5 | 13 |
| [psmfront/src/components/register.vue](/psmfront/src/components/register.vue) | vue | 157 | 0 | 19 | 176 |
| [psmfront/src/css/index.css](/psmfront/src/css/index.css) | CSS | 69 | 0 | 5 | 74 |
| [psmfront/src/main.ts](/psmfront/src/main.ts) | TypeScript | 14 | 1 | 3 | 18 |
| [psmfront/src/router/index.ts](/psmfront/src/router/index.ts) | TypeScript | 83 | 3 | 1 | 87 |
| [psmfront/src/router/intercept.ts](/psmfront/src/router/intercept.ts) | TypeScript | 22 | 0 | 1 | 23 |
| [psmfront/src/store/details.ts](/psmfront/src/store/details.ts) | TypeScript | 10 | 0 | 0 | 10 |
| [psmfront/src/store/index.ts](/psmfront/src/store/index.ts) | TypeScript | 5 | 0 | 1 | 6 |
| [psmfront/src/store/project.ts](/psmfront/src/store/project.ts) | TypeScript | 19 | 0 | 2 | 21 |
| [psmfront/src/store/step.ts](/psmfront/src/store/step.ts) | TypeScript | 14 | 0 | 1 | 15 |
| [psmfront/src/store/user.ts](/psmfront/src/store/user.ts) | TypeScript | 35 | 0 | 3 | 38 |
| [psmfront/src/style.css](/psmfront/src/style.css) | CSS | 70 | 0 | 10 | 80 |
| [psmfront/src/utils/event.ts](/psmfront/src/utils/event.ts) | TypeScript | 3 | 1 | 2 | 6 |
| [psmfront/src/utils/hasAuthority.ts](/psmfront/src/utils/hasAuthority.ts) | TypeScript | 9 | 21 | 5 | 35 |
| [psmfront/src/utils/request.ts](/psmfront/src/utils/request.ts) | TypeScript | 72 | 19 | 6 | 97 |
| [psmfront/src/utils/tools.ts](/psmfront/src/utils/tools.ts) | TypeScript | 4 | 0 | 0 | 4 |
| [psmfront/src/vite-env.d.ts](/psmfront/src/vite-env.d.ts) | TypeScript | 0 | 1 | 1 | 2 |
| [psmfront/tsconfig.json](/psmfront/tsconfig.json) | JSON with Comments | 45 | 2 | 1 | 48 |
| [psmfront/tsconfig.node.json](/psmfront/tsconfig.node.json) | JSON | 11 | 0 | 2 | 13 |
| [psmfront/vite.config.ts](/psmfront/vite.config.ts) | TypeScript | 20 | 2 | 2 | 24 |

[Summary](results.md) / Details / [Diff Summary](diff.md) / [Diff Details](diff-details.md)