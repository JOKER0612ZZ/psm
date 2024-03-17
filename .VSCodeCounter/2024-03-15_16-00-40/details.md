# Details

Date : 2024-03-15 16:00:40

Directory d:\\基于web的项目管理系统\\Psm

Total : 85 files,  6345 codes, 243 comments, 450 blanks, all 7038 lines

[Summary](results.md) / Details / [Diff Summary](diff.md) / [Diff Details](diff-details.md)

## Files
| filename | language | code | comment | blank | total |
| :--- | :--- | ---: | ---: | ---: | ---: |
| [.idea/workspace.xml](/.idea/workspace.xml) | XML | 78 | 0 | 0 | 78 |
| [README.md](/README.md) | Markdown | 3 | 0 | 0 | 3 |
| [package-lock.json](/package-lock.json) | JSON | 6 | 0 | 1 | 7 |
| [psmback/pom.xml](/psmback/pom.xml) | XML | 80 | 0 | 5 | 85 |
| [psmback/psmback-common/pom.xml](/psmback/psmback-common/pom.xml) | XML | 85 | 3 | 2 | 90 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/config/RedisConfig.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/config/RedisConfig.java) | Java | 24 | 3 | 5 | 32 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/config/SecurityConfig.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/config/SecurityConfig.java) | Java | 38 | 0 | 3 | 41 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/config/WebConfig.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/config/WebConfig.java) | Java | 15 | 12 | 2 | 29 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/LoginUser.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/LoginUser.java) | Java | 50 | 0 | 9 | 59 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/Project.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/Project.java) | Java | 15 | 0 | 3 | 18 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/Task.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/Task.java) | Java | 15 | 0 | 3 | 18 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/Team.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/Team.java) | Java | 19 | 0 | 3 | 22 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/User.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/entity/User.java) | Java | 19 | 4 | 5 | 28 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/filter/JwtAuthenticationTokenFilter.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/filter/JwtAuthenticationTokenFilter.java) | Java | 42 | 5 | 5 | 52 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/result/CommonResult.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/result/CommonResult.java) | Java | 26 | 23 | 4 | 53 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/result/ResponseCode.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/result/ResponseCode.java) | Java | 29 | 6 | 4 | 39 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/utils/FastJsonRedisSerializer.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/utils/FastJsonRedisSerializer.java) | Java | 44 | 36 | 15 | 95 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/utils/JwtUtils.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/utils/JwtUtils.java) | Java | 32 | 3 | 3 | 38 |
| [psmback/psmback-common/src/main/java/com/zz/psmback/common/utils/RedisUtils.java](/psmback/psmback-common/src/main/java/com/zz/psmback/common/utils/RedisUtils.java) | Java | 70 | 21 | 6 | 97 |
| [psmback/psmback-dao/pom.xml](/psmback/psmback-dao/pom.xml) | XML | 22 | 0 | 2 | 24 |
| [psmback/psmback-dao/src/main/java/com/zz/psmback/dao/ProjectDao.java](/psmback/psmback-dao/src/main/java/com/zz/psmback/dao/ProjectDao.java) | Java | 9 | 0 | 4 | 13 |
| [psmback/psmback-dao/src/main/java/com/zz/psmback/dao/TaskDao.java](/psmback/psmback-dao/src/main/java/com/zz/psmback/dao/TaskDao.java) | Java | 7 | 0 | 3 | 10 |
| [psmback/psmback-dao/src/main/java/com/zz/psmback/dao/TeamDao.java](/psmback/psmback-dao/src/main/java/com/zz/psmback/dao/TeamDao.java) | Java | 7 | 0 | 3 | 10 |
| [psmback/psmback-dao/src/main/java/com/zz/psmback/dao/UserDao.java](/psmback/psmback-dao/src/main/java/com/zz/psmback/dao/UserDao.java) | Java | 8 | 0 | 4 | 12 |
| [psmback/psmback-service/pom.xml](/psmback/psmback-service/pom.xml) | XML | 31 | 0 | 4 | 35 |
| [psmback/psmback-service/src/main/java/com/zz/psmback/service/LoginService.java](/psmback/psmback-service/src/main/java/com/zz/psmback/service/LoginService.java) | Java | 9 | 0 | 4 | 13 |
| [psmback/psmback-service/src/main/java/com/zz/psmback/service/ProjectService.java](/psmback/psmback-service/src/main/java/com/zz/psmback/service/ProjectService.java) | Java | 8 | 0 | 3 | 11 |
| [psmback/psmback-service/src/main/java/com/zz/psmback/service/UserService.java](/psmback/psmback-service/src/main/java/com/zz/psmback/service/UserService.java) | Java | 12 | 0 | 5 | 17 |
| [psmback/psmback-service/src/main/java/com/zz/psmback/service/impl/LoginServiceImpl.java](/psmback/psmback-service/src/main/java/com/zz/psmback/service/impl/LoginServiceImpl.java) | Java | 95 | 10 | 6 | 111 |
| [psmback/psmback-service/src/main/java/com/zz/psmback/service/impl/ProjectServiceImpl.java](/psmback/psmback-service/src/main/java/com/zz/psmback/service/impl/ProjectServiceImpl.java) | Java | 72 | 5 | 5 | 82 |
| [psmback/psmback-service/src/main/java/com/zz/psmback/service/impl/UserDetailsServiceImpl.java](/psmback/psmback-service/src/main/java/com/zz/psmback/service/impl/UserDetailsServiceImpl.java) | Java | 31 | 0 | 5 | 36 |
| [psmback/psmback-service/src/main/java/com/zz/psmback/service/impl/UserServiceImpl.java](/psmback/psmback-service/src/main/java/com/zz/psmback/service/impl/UserServiceImpl.java) | Java | 78 | 0 | 8 | 86 |
| [psmback/psmback-service/src/test/java/Atest.java](/psmback/psmback-service/src/test/java/Atest.java) | Java | 13 | 0 | 5 | 18 |
| [psmback/psmback-web/.mvn/wrapper/maven-wrapper.properties](/psmback/psmback-web/.mvn/wrapper/maven-wrapper.properties) | Properties | 2 | 0 | 1 | 3 |
| [psmback/psmback-web/mvnw.cmd](/psmback/psmback-web/mvnw.cmd) | Batch | 118 | 51 | 37 | 206 |
| [psmback/psmback-web/pom.xml](/psmback/psmback-web/pom.xml) | XML | 47 | 0 | 1 | 48 |
| [psmback/psmback-web/src/main/java/com/zz/psmback/web/PsmbackWebApplication.java](/psmback/psmback-web/src/main/java/com/zz/psmback/web/PsmbackWebApplication.java) | Java | 12 | 0 | 4 | 16 |
| [psmback/psmback-web/src/main/java/com/zz/psmback/web/controller/LoginController.java](/psmback/psmback-web/src/main/java/com/zz/psmback/web/controller/LoginController.java) | Java | 28 | 0 | 4 | 32 |
| [psmback/psmback-web/src/main/java/com/zz/psmback/web/controller/ProjectController.java](/psmback/psmback-web/src/main/java/com/zz/psmback/web/controller/ProjectController.java) | Java | 21 | 0 | 3 | 24 |
| [psmback/psmback-web/src/main/java/com/zz/psmback/web/controller/UserController.java](/psmback/psmback-web/src/main/java/com/zz/psmback/web/controller/UserController.java) | Java | 36 | 0 | 3 | 39 |
| [psmback/psmback-web/src/main/resources/application.yaml](/psmback/psmback-web/src/main/resources/application.yaml) | YAML | 26 | 1 | 11 | 38 |
| [psmback/psmback-web/src/main/resources/mapper/ProjectMapper.xml](/psmback/psmback-web/src/main/resources/mapper/ProjectMapper.xml) | XML | 13 | 0 | 1 | 14 |
| [psmback/psmback-web/src/main/resources/mapper/TaskMapper.xml](/psmback/psmback-web/src/main/resources/mapper/TaskMapper.xml) | XML | 5 | 0 | 2 | 7 |
| [psmback/psmback-web/src/main/resources/mapper/TeamMapper.xml](/psmback/psmback-web/src/main/resources/mapper/TeamMapper.xml) | XML | 5 | 0 | 2 | 7 |
| [psmback/psmback-web/src/main/resources/mapper/UserMapper.xml](/psmback/psmback-web/src/main/resources/mapper/UserMapper.xml) | XML | 5 | 0 | 2 | 7 |
| [psmback/psmback-web/src/test/java/com/zz/psmback/web/PsmbackWebApplicationTests.java](/psmback/psmback-web/src/test/java/com/zz/psmback/web/PsmbackWebApplicationTests.java) | Java | 42 | 27 | 7 | 76 |
| [psmfront/README.md](/psmfront/README.md) | Markdown | 11 | 0 | 8 | 19 |
| [psmfront/index.html](/psmfront/index.html) | HTML | 13 | 0 | 1 | 14 |
| [psmfront/package-lock.json](/psmfront/package-lock.json) | JSON | 2,851 | 0 | 1 | 2,852 |
| [psmfront/package.json](/psmfront/package.json) | JSON | 30 | 0 | 1 | 31 |
| [psmfront/public/vite.svg](/psmfront/public/vite.svg) | XML | 1 | 0 | 0 | 1 |
| [psmfront/src/App.vue](/psmfront/src/App.vue) | vue | 7 | 0 | 4 | 11 |
| [psmfront/src/api/login.ts](/psmfront/src/api/login.ts) | TypeScript | 31 | 0 | 0 | 31 |
| [psmfront/src/api/project.ts](/psmfront/src/api/project.ts) | TypeScript | 11 | 0 | 1 | 12 |
| [psmfront/src/api/user.ts](/psmfront/src/api/user.ts) | TypeScript | 37 | 1 | 2 | 40 |
| [psmfront/src/components/details/projectDetails/demand.vue](/psmfront/src/components/details/projectDetails/demand.vue) | vue | 0 | 0 | 1 | 1 |
| [psmfront/src/components/details/projectDetails/overview.vue](/psmfront/src/components/details/projectDetails/overview.vue) | vue | 0 | 0 | 1 | 1 |
| [psmfront/src/components/login.vue](/psmfront/src/components/login.vue) | vue | 216 | 0 | 34 | 250 |
| [psmfront/src/components/main-views/home.vue](/psmfront/src/components/main-views/home.vue) | vue | 172 | 0 | 32 | 204 |
| [psmfront/src/components/main-views/user.vue](/psmfront/src/components/main-views/user.vue) | vue | 185 | 0 | 16 | 201 |
| [psmfront/src/components/pages/file.vue](/psmfront/src/components/pages/file.vue) | vue | 22 | 0 | 2 | 24 |
| [psmfront/src/components/pages/index.vue](/psmfront/src/components/pages/index.vue) | vue | 13 | 0 | 3 | 16 |
| [psmfront/src/components/pages/personal.vue](/psmfront/src/components/pages/personal.vue) | vue | 314 | 0 | 33 | 347 |
| [psmfront/src/components/pages/project.vue](/psmfront/src/components/pages/project.vue) | vue | 88 | 0 | 3 | 91 |
| [psmfront/src/components/pages/project/create.vue](/psmfront/src/components/pages/project/create.vue) | vue | 124 | 0 | 10 | 134 |
| [psmfront/src/components/pages/project/details.vue](/psmfront/src/components/pages/project/details.vue) | vue | 31 | 0 | 2 | 33 |
| [psmfront/src/components/pages/project/showProject.vue](/psmfront/src/components/pages/project/showProject.vue) | vue | 91 | 0 | 9 | 100 |
| [psmfront/src/components/pages/task.vue](/psmfront/src/components/pages/task.vue) | vue | 25 | 0 | 2 | 27 |
| [psmfront/src/components/pages/team.vue](/psmfront/src/components/pages/team.vue) | vue | 25 | 0 | 2 | 27 |
| [psmfront/src/components/register.vue](/psmfront/src/components/register.vue) | vue | 157 | 0 | 19 | 176 |
| [psmfront/src/css/index.css](/psmfront/src/css/index.css) | CSS | 69 | 0 | 5 | 74 |
| [psmfront/src/main.ts](/psmfront/src/main.ts) | TypeScript | 14 | 1 | 3 | 18 |
| [psmfront/src/router/index.ts](/psmfront/src/router/index.ts) | TypeScript | 67 | 1 | 5 | 73 |
| [psmfront/src/store/details.ts](/psmfront/src/store/details.ts) | TypeScript | 10 | 0 | 0 | 10 |
| [psmfront/src/store/index.ts](/psmfront/src/store/index.ts) | TypeScript | 5 | 0 | 1 | 6 |
| [psmfront/src/store/project.ts](/psmfront/src/store/project.ts) | TypeScript | 14 | 0 | 4 | 18 |
| [psmfront/src/store/step.ts](/psmfront/src/store/step.ts) | TypeScript | 14 | 0 | 1 | 15 |
| [psmfront/src/store/user.ts](/psmfront/src/store/user.ts) | TypeScript | 28 | 0 | 3 | 31 |
| [psmfront/src/style.css](/psmfront/src/style.css) | CSS | 70 | 0 | 10 | 80 |
| [psmfront/src/utils/event.ts](/psmfront/src/utils/event.ts) | TypeScript | 3 | 1 | 2 | 6 |
| [psmfront/src/utils/request.ts](/psmfront/src/utils/request.ts) | TypeScript | 73 | 19 | 6 | 98 |
| [psmfront/src/vite-env.d.ts](/psmfront/src/vite-env.d.ts) | TypeScript | 0 | 1 | 1 | 2 |
| [psmfront/tsconfig.json](/psmfront/tsconfig.json) | JSON with Comments | 40 | 7 | 1 | 48 |
| [psmfront/tsconfig.node.json](/psmfront/tsconfig.node.json) | JSON | 11 | 0 | 2 | 13 |
| [psmfront/vite.config.ts](/psmfront/vite.config.ts) | TypeScript | 20 | 2 | 2 | 24 |

[Summary](results.md) / Details / [Diff Summary](diff.md) / [Diff Details](diff-details.md)